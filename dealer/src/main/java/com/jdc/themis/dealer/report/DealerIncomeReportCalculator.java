package com.jdc.themis.dealer.report;

import static com.jdc.themis.dealer.report.ReportUtils.calcReference;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Map;

import ch.lambdaj.Lambda;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimaps;
import com.jdc.themis.dealer.domain.DealerIncomeExpenseFact;
import com.jdc.themis.dealer.domain.DealerIncomeRevenueFact;
import com.jdc.themis.dealer.service.RefDataQueryService;
import com.jdc.themis.dealer.web.domain.DealerDetail;
import com.jdc.themis.dealer.web.domain.DepartmentDetail;
import com.jdc.themis.dealer.web.domain.ReportDataDealerDetail;
import com.jdc.themis.dealer.web.domain.ReportDataDepartmentDetail;
import com.jdc.themis.dealer.web.domain.ReportDataDetailAmount;
import com.jdc.themis.dealer.web.domain.ReportDealerDataList;

import fj.data.Option;

/**
 * Calculate the overall income report numbers by dealer.
 * 
 * The class is not thread-safe.
 * 
 * @author Kai Chen
 * 
 */
public class DealerIncomeReportCalculator {

	/**
	 * Mapping between dealer id and dealer report detail.
	 */
	private final Map<Integer, ReportDataDealerDetail> dealerDetails = Maps
			.newHashMap();

	private Integer year;
	private Option<GroupBy> groupByOption = Option.<GroupBy> none();

	public DealerIncomeReportCalculator(final Collection<DealerDetail> dealers,
			final Collection<DepartmentDetail> departments, final Integer year) {
		for (final DealerDetail dealer : dealers) {
			// initialize dealer details map for all dealers
			dealerDetails.put(dealer.getId(), new ReportDataDealerDetail());
			dealerDetails.get(dealer.getId()).setId(dealer.getId());
			dealerDetails.get(dealer.getId()).setName(dealer.getName());
			dealerDetails.get(dealer.getId()).setCode(dealer.getCode());
			dealerDetails.get(dealer.getId()).setBrand(dealer.getBrand());
			
			final Map<Integer, ReportDataDepartmentDetail> departmentDetails = Maps
					.newHashMap();
			for (final DepartmentDetail department : departments) {
				departmentDetails.put(department.getId(),
						new ReportDataDepartmentDetail());
				departmentDetails.get(department.getId()).setId(
						department.getId());
				departmentDetails.get(department.getId()).setName(
						department.getName());

				dealerDetails.get(dealer.getId()).getDetail()
						.add(departmentDetails.get(department.getId()));
			}
		}

		this.year = year;
	}

	public enum GroupBy {
		DEPARTMENT;

		public static Option<GroupBy> valueOf(int value) {
			for (final GroupBy d : GroupBy.values()) {
				if (d.ordinal() == value) {
					return Option.<GroupBy> some(d);
				}
			}
			return Option.<GroupBy> none();
		}

	}

	/**
	 * Populate and return a report details with time and dealer report numbers.
	 * 
	 * @return
	 */
	public ReportDealerDataList getReportDetail() {
		final ReportDealerDataList reportDetail = new ReportDealerDataList();
		reportDetail.setYear(year);
		reportDetail.getDetail().addAll(dealerDetails.values());
		return reportDetail;
	}

	public DealerIncomeReportCalculator withGroupBy(
			final Option<Integer> groupByOption) {
		if (groupByOption.isSome()) {
			this.groupByOption = GroupBy.valueOf(groupByOption.some());
		}
		return this;
	}

	private enum GetDepartmentIDFromFactFunction implements
			Function<DealerIncomeExpenseFact, Integer> {
		INSTANCE;

		@Override
		public Integer apply(final DealerIncomeExpenseFact item) {
			return item.getDepartmentID();
		}
	}

	private enum GetDepartmentIDFromRevenueFactFunction implements
			Function<DealerIncomeRevenueFact, Integer> {
		INSTANCE;

		@Override
		public Integer apply(final DealerIncomeRevenueFact item) {
			return item.getDepartmentID();
		}
	}

	/**
	 * Calculate and populate dealer expense details, including reference,
	 * amount and percentage if previous dealer info is provided.
	 * 
	 * Amount could be "avg" or "sum" of the year's monthly amounts depending on
	 * parameter "op".
	 * 
	 * @param dealerExpenseFacts
	 * @param op
	 * @return
	 */
	public DealerIncomeReportCalculator calcExpenses(
			final ImmutableListMultimap<Integer, DealerIncomeExpenseFact> dealerExpenseFacts,
			final JournalOp op, final RefDataQueryService refDataDAL) {
		for (final Integer dealerID : dealerExpenseFacts.keySet()) {
			final BigDecimal totalExpense = Lambda.sumFrom(
					dealerExpenseFacts.get(dealerID),
					DealerIncomeExpenseFact.class).getAmount();
			final ReportDataDetailAmount amount = new ReportDataDetailAmount();
			amount.setAmount(totalExpense.doubleValue());

			dealerDetails.get(dealerID).setExpense(amount);
		}

		final Double reference = calcReference(Lambda.extract(
				dealerDetails.values(), Lambda.on(ReportDataDealerDetail.class)
						.getExpense().getAmount()));
		Lambda.forEach(dealerDetails.values()).getExpense()
				.setReference(reference);

		// check group by
		if (this.groupByOption.isSome()) {
			for (final Integer dealerID : dealerDetails.keySet()) {
				// if group by is "department", generate the allocation by
				// department in fact
				if (this.groupByOption.some().equals(GroupBy.DEPARTMENT)) {
					final Collection<DepartmentDetail> departments = refDataDAL
							.getDepartments().getItems();

					final ImmutableListMultimap<Integer, DealerIncomeExpenseFact> departmentFacts = Multimaps
							.index(dealerExpenseFacts.get(dealerID),
									GetDepartmentIDFromFactFunction.INSTANCE);
					for (final DepartmentDetail department : departments) {
						final BigDecimal departmentAlloc = Lambda.sumFrom(
								departmentFacts.get(department.getId()),
								DealerIncomeExpenseFact.class).getAmount();
						final ReportDataDetailAmount departmentAmount = new ReportDataDetailAmount();
						departmentAmount.setAmount(departmentAlloc
								.doubleValue());
						for (final ReportDataDepartmentDetail departmentDetial : dealerDetails
								.get(dealerID).getDetail()) {
							if (departmentDetial.getId().equals(
									department.getId())) {
								departmentDetial.setExpense(departmentAmount);
								break;
							}
						}
					}

				}
			}
		}
		return this;
	}

	/**
	 * Calculate the margin.
	 * 
	 * @param dealerRevenueFacts
	 * @param op
	 * @return
	 */
	public DealerIncomeReportCalculator calcMargins(
			final ImmutableListMultimap<Integer, DealerIncomeRevenueFact> dealerRevenueFacts,
			final JournalOp op, final RefDataQueryService refDataDAL) {
		for (final Integer dealerID : dealerRevenueFacts.keySet()) {
			final BigDecimal totalMargin = Lambda.sumFrom(
					dealerRevenueFacts.get(dealerID),
					DealerIncomeRevenueFact.class).getMargin();
			final ReportDataDetailAmount amount = new ReportDataDetailAmount();
			amount.setAmount(totalMargin.doubleValue());

			dealerDetails.get(dealerID).setMargin(amount);
		}
		final Double reference = calcReference(Lambda.extract(
				dealerDetails.values(), Lambda.on(ReportDataDealerDetail.class)
						.getMargin().getAmount()));
		Lambda.forEach(dealerDetails.values()).getMargin()
				.setReference(reference);

		// check group by
		if (this.groupByOption.isSome()) {
			for (final Integer dealerID : dealerDetails.keySet()) {
				// if group by is "department", generate the allocation by
				// department in fact
				if (this.groupByOption.some().equals(GroupBy.DEPARTMENT)) {
					final Collection<DepartmentDetail> departments = refDataDAL
							.getDepartments().getItems();

					final ImmutableListMultimap<Integer, DealerIncomeRevenueFact> departmentFacts = Multimaps
							.index(dealerRevenueFacts.get(dealerID),
									GetDepartmentIDFromRevenueFactFunction.INSTANCE);
					for (final DepartmentDetail department : departments) {
						final BigDecimal departmentAlloc = Lambda.sumFrom(
								departmentFacts.get(department.getId()),
								DealerIncomeRevenueFact.class).getMargin();
						final ReportDataDetailAmount departmentAmount = new ReportDataDetailAmount();
						departmentAmount.setAmount(departmentAlloc
								.doubleValue());
						for (final ReportDataDepartmentDetail departmentDetial : dealerDetails
								.get(dealerID).getDetail()) {
							if (departmentDetial.getId().equals(
									department.getId())) {
								departmentDetial.setMargin(departmentAmount);
								break;
							}
						}
					}

				}
			}
		}
		return this;
	}

	/**
	 * Calculate the revenues.
	 * 
	 * @param dealerRevenueFacts
	 * @param op
	 * @return
	 */
	public DealerIncomeReportCalculator calcRevenues(
			final ImmutableListMultimap<Integer, DealerIncomeRevenueFact> dealerRevenueFacts,
			final JournalOp op, final RefDataQueryService refDataDAL) {
		for (final Integer dealerID : dealerRevenueFacts.keySet()) {
			final BigDecimal totalAmount = Lambda.sumFrom(
					dealerRevenueFacts.get(dealerID),
					DealerIncomeRevenueFact.class).getAmount();
			final ReportDataDetailAmount amount = new ReportDataDetailAmount();
			amount.setAmount(totalAmount.doubleValue());

			dealerDetails.get(dealerID).setRevenue(amount);
		}
		final Double reference = calcReference(Lambda.extract(
				dealerDetails.values(), Lambda.on(ReportDataDealerDetail.class)
						.getRevenue().getAmount()));
		Lambda.forEach(dealerDetails.values()).getRevenue()
				.setReference(reference);

		// check group by
		if (this.groupByOption.isSome()) {
			for (final Integer dealerID : dealerDetails.keySet()) {
				// if group by is "department", generate the allocation by
				// department in fact
				if (this.groupByOption.some().equals(GroupBy.DEPARTMENT)) {
					final Collection<DepartmentDetail> departments = refDataDAL
							.getDepartments().getItems();

					final ImmutableListMultimap<Integer, DealerIncomeRevenueFact> departmentFacts = Multimaps
							.index(dealerRevenueFacts.get(dealerID),
									GetDepartmentIDFromRevenueFactFunction.INSTANCE);
					for (final DepartmentDetail department : departments) {
						final BigDecimal departmentAlloc = Lambda.sumFrom(
								departmentFacts.get(department.getId()),
								DealerIncomeRevenueFact.class).getAmount();
						final ReportDataDetailAmount departmentAmount = new ReportDataDetailAmount();
						departmentAmount.setAmount(departmentAlloc
								.doubleValue());
						for (final ReportDataDepartmentDetail departmentDetial : dealerDetails
								.get(dealerID).getDetail()) {
							if (departmentDetial.getId().equals(
									department.getId())) {
								departmentDetial.setRevenue(departmentAmount);
								break;
							}
						}
					}

				}
			}
		}
		return this;
	}

	/**
	 * Calculate operational profit
	 * 
	 * @return
	 */
	public DealerIncomeReportCalculator calcOpProfit() {
		for (final Integer dealerID : dealerDetails.keySet()) {
			final ReportDataDetailAmount margin = dealerDetails.get(dealerID)
					.getMargin();
			final ReportDataDetailAmount expense = dealerDetails.get(dealerID)
					.getExpense();
			final ReportDataDetailAmount amount = new ReportDataDetailAmount();
			// Operational Profit = Margin - Expense
			amount.setAmount(margin.getAmount() - expense.getAmount());

			dealerDetails.get(dealerID).setOpProfit(amount);
		}
		final Double reference = calcReference(Lambda.extract(
				dealerDetails.values(), Lambda.on(ReportDataDealerDetail.class)
						.getOpProfit().getAmount()));
		Lambda.forEach(dealerDetails.values()).getOpProfit()
				.setReference(reference);
		// check group by
		if (this.groupByOption.isSome()) {
			for (final Integer dealerID : dealerDetails.keySet()) {
				// if group by is "department", generate the allocation by
				// department in fact
				if (this.groupByOption.some().equals(GroupBy.DEPARTMENT)) {
					for (final ReportDataDepartmentDetail departmentDetial : dealerDetails
								.get(dealerID).getDetail()) {
						final ReportDataDetailAmount margin = departmentDetial.getMargin();
						final ReportDataDetailAmount expense = departmentDetial.getExpense();
						final ReportDataDetailAmount amount = new ReportDataDetailAmount();
						// Operational Profit = Margin - Expense
						amount.setAmount(margin.getAmount() - expense.getAmount());
						departmentDetial.setOpProfit(amount);
					}

				}
			}
		}
		return this;
	}

}
