package com.jdc.themis.dealer.report;

import static com.jdc.themis.dealer.report.ReportUtils.calcReference;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Map;

import ch.lambdaj.Lambda;

import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimaps;
import com.jdc.themis.dealer.domain.DealerIncomeExpenseFact;
import com.jdc.themis.dealer.domain.DealerIncomeRevenueFact;
import com.jdc.themis.dealer.report.DealerIncomeReportCalculator.GroupBy;
import com.jdc.themis.dealer.service.RefDataQueryService;
import com.jdc.themis.dealer.service.impl.GetDepartmentIDFromExpenseFunction;
import com.jdc.themis.dealer.service.impl.GetDepartmentIDFromRevenueFunction;
import com.jdc.themis.dealer.web.domain.DealerDetail;
import com.jdc.themis.dealer.web.domain.DepartmentDetail;
import com.jdc.themis.dealer.web.domain.ReportDataDealerPostSalesDetail;
import com.jdc.themis.dealer.web.domain.ReportDataDepartmentDetail;
import com.jdc.themis.dealer.web.domain.ReportDataDetailAmount;
import com.jdc.themis.dealer.web.domain.ReportDealerPostSalesDataList;

import fj.data.Option;

public class DealerPostSalesIncomeReportCalculator {

	/**
	 * Mapping between dealer id and dealer report detail.
	 */
	private final Map<Integer, ReportDataDealerPostSalesDetail> dealerDetails = Maps
			.newHashMap();

	private Integer year;
	private Integer monthOfYear;
	private Option<GroupBy> groupByOption = Option.<GroupBy> none();

	public DealerPostSalesIncomeReportCalculator(
			final Collection<DealerDetail> dealers,
			final Collection<DepartmentDetail> departments, final Integer year,
			final Integer monthOfYear) {
		for (final DealerDetail dealer : dealers) {
			// initialize dealer details map for all dealers
			dealerDetails.put(dealer.getId(),
					new ReportDataDealerPostSalesDetail());
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
		this.monthOfYear = monthOfYear;
	}

	/**
	 * Populate and return a report details with time and dealer report numbers.
	 * 
	 * @return
	 */
	public ReportDealerPostSalesDataList getReportDetail() {
		final ReportDealerPostSalesDataList reportDetail = new ReportDealerPostSalesDataList();
		reportDetail.setYear(year);
		reportDetail.setMonth(monthOfYear);
		reportDetail.getDetail().addAll(dealerDetails.values());
		return reportDetail;
	}

	public DealerPostSalesIncomeReportCalculator withGroupBy(
			final Option<Integer> groupByOption) {
		if (groupByOption.isSome()) {
			this.groupByOption = GroupBy.valueOf(groupByOption.some());
		}
		return this;
	}

	public DealerPostSalesIncomeReportCalculator calcRevenues(
			final ImmutableListMultimap<Integer, DealerIncomeRevenueFact> dealerRevenueFacts,
			final RefDataQueryService refDataDAL) {
		for (final Integer dealerID : dealerRevenueFacts.keySet()) {
			final BigDecimal totalAmount = Lambda.sumFrom(
					dealerRevenueFacts.get(dealerID),
					DealerIncomeRevenueFact.class).getAmount();
			final ReportDataDetailAmount amount = new ReportDataDetailAmount();
			amount.setAmount(totalAmount.doubleValue()
					/ monthOfYear.doubleValue());

			dealerDetails.get(dealerID).setRevenue(amount);
		}
		final Double reference = calcReference(Lambda.extract(
				dealerDetails.values(),
				Lambda.on(ReportDataDealerPostSalesDetail.class).getRevenue()
						.getAmount()));
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
									GetDepartmentIDFromRevenueFunction.INSTANCE);
					for (final DepartmentDetail department : departments) {
						final BigDecimal departmentRevenue = Lambda.sumFrom(
								departmentFacts.get(department.getId()),
								DealerIncomeRevenueFact.class).getAmount();
						final ReportDataDetailAmount departmentAmount = new ReportDataDetailAmount();
						departmentAmount.setAmount(departmentRevenue
								.doubleValue() / monthOfYear.doubleValue());
						for (final ReportDataDepartmentDetail departmentDetail : dealerDetails
								.get(dealerID).getDetail()) {
							if (departmentDetail.getId().equals(
									department.getId())) {
								departmentDetail.setRevenue(departmentAmount);
								break;
							}
						}
					}

				}
			}
		}
		return this;
	}

	public DealerPostSalesIncomeReportCalculator calcMargins(
			final ImmutableListMultimap<Integer, DealerIncomeRevenueFact> dealerRevenueFacts,
			final RefDataQueryService refDataDAL) {
		for (final Integer dealerID : dealerRevenueFacts.keySet()) {
			final BigDecimal totalMargin = Lambda.sumFrom(
					dealerRevenueFacts.get(dealerID),
					DealerIncomeRevenueFact.class).getMargin();
			final ReportDataDetailAmount amount = new ReportDataDetailAmount();
			amount.setAmount(totalMargin.doubleValue()
					/ monthOfYear.doubleValue());

			dealerDetails.get(dealerID).setMargin(amount);
		}
		final Double reference = calcReference(Lambda.extract(
				dealerDetails.values(),
				Lambda.on(ReportDataDealerPostSalesDetail.class).getMargin()
						.getAmount()));
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
									GetDepartmentIDFromRevenueFunction.INSTANCE);
					for (final DepartmentDetail department : departments) {
						final BigDecimal departmentMargin = Lambda.sumFrom(
								departmentFacts.get(department.getId()),
								DealerIncomeRevenueFact.class).getMargin();
						final ReportDataDetailAmount departmentAmount = new ReportDataDetailAmount();
						departmentAmount.setAmount(departmentMargin
								.doubleValue() / monthOfYear.doubleValue());
						for (final ReportDataDepartmentDetail departmentDetail : dealerDetails
								.get(dealerID).getDetail()) {
							if (departmentDetail.getId().equals(
									department.getId())) {
								departmentDetail.setMargin(departmentAmount);
								break;
							}
						}
					}

				}
			}
		}
		return this;
	}

	public DealerPostSalesIncomeReportCalculator calcExpenses(
			final ImmutableListMultimap<Integer, DealerIncomeExpenseFact> dealerExpenseFacts,
			final RefDataQueryService refDataDAL) {
		for (final Integer dealerID : dealerExpenseFacts.keySet()) {
			final BigDecimal totalAmount = Lambda.sumFrom(
					dealerExpenseFacts.get(dealerID),
					DealerIncomeExpenseFact.class).getAmount();
			final ReportDataDetailAmount amount = new ReportDataDetailAmount();
			amount.setAmount(totalAmount.doubleValue()
					/ monthOfYear.doubleValue());

			dealerDetails.get(dealerID).setExpense(amount);
		}
		final Double reference = calcReference(Lambda.extract(
				dealerDetails.values(),
				Lambda.on(ReportDataDealerPostSalesDetail.class).getExpense()
						.getAmount()));
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
									GetDepartmentIDFromExpenseFunction.INSTANCE);
					for (final DepartmentDetail department : departments) {
						final BigDecimal departmentExpense = Lambda.sumFrom(
								departmentFacts.get(department.getId()),
								DealerIncomeExpenseFact.class).getAmount();
						final ReportDataDetailAmount departmentAmount = new ReportDataDetailAmount();
						departmentAmount.setAmount(departmentExpense
								.doubleValue() / monthOfYear.doubleValue());
						for (final ReportDataDepartmentDetail departmentDetail : dealerDetails
								.get(dealerID).getDetail()) {
							if (departmentDetail.getId().equals(
									department.getId())) {
								departmentDetail.setExpense(departmentAmount);
								break;
							}
						}
					}

				}
			}
		}
		return this;
	}

	public DealerPostSalesIncomeReportCalculator calcOpProfit() {
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
				dealerDetails.values(),
				Lambda.on(ReportDataDealerPostSalesDetail.class).getOpProfit()
						.getAmount()));
		Lambda.forEach(dealerDetails.values()).getOpProfit()
				.setReference(reference);
		// check group by
		if (this.groupByOption.isSome()) {
			for (final Integer dealerID : dealerDetails.keySet()) {
				// if group by is "department", generate the allocation by
				// department in fact
				if (this.groupByOption.some().equals(GroupBy.DEPARTMENT)) {
					for (final ReportDataDepartmentDetail departmentDetail : dealerDetails
							.get(dealerID).getDetail()) {
						final ReportDataDetailAmount margin = departmentDetail
								.getMargin();
						final ReportDataDetailAmount expense = departmentDetail
								.getExpense();
						final ReportDataDetailAmount amount = new ReportDataDetailAmount();
						// Operational Profit = Margin - Expense
						amount.setAmount(margin.getAmount()
								- expense.getAmount());
						departmentDetail.setOpProfit(amount);
					}
				}
			}
		}
		return this;
	}

}
