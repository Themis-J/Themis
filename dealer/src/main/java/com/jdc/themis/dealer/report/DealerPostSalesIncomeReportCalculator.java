package com.jdc.themis.dealer.report;

import static com.jdc.themis.dealer.report.ReportUtils.calcReference;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Map;

import ch.lambdaj.Lambda;

import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimaps;
import com.jdc.themis.dealer.domain.DealerIncomeRevenueFact;
import com.jdc.themis.dealer.report.DealerIncomeReportCalculator.GroupBy;
import com.jdc.themis.dealer.service.RefDataQueryService;
import com.jdc.themis.dealer.service.impl.GetDepartmentIDFromRevenueFunction;
import com.jdc.themis.dealer.web.domain.DealerDetail;
import com.jdc.themis.dealer.web.domain.DepartmentDetail;
import com.jdc.themis.dealer.web.domain.ReportDataDealerPostSalesIncomeDetail;
import com.jdc.themis.dealer.web.domain.ReportDataDepartmentDetail;
import com.jdc.themis.dealer.web.domain.ReportDataDetailAmount;
import com.jdc.themis.dealer.web.domain.ReportDealerPostSalesIncomeDataList;

import fj.data.Option;

public class DealerPostSalesIncomeReportCalculator {

	/**
	 * Mapping between dealer id and dealer report detail.
	 */
	private final Map<Integer, ReportDataDealerPostSalesIncomeDetail> dealerDetails = Maps
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
					new ReportDataDealerPostSalesIncomeDetail());
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
	public ReportDealerPostSalesIncomeDataList getReportDetail() {
		final ReportDealerPostSalesIncomeDataList reportDetail = new ReportDealerPostSalesIncomeDataList();
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

	/**
	 * Calculate the revenues.
	 * 
	 * @param dealerRevenueFacts
	 * @param op
	 * @return
	 */
	public DealerPostSalesIncomeReportCalculator calcRevenues(
			final ImmutableListMultimap<Integer, DealerIncomeRevenueFact> dealerRevenueFacts,
			final RefDataQueryService refDataDAL) {
		for (final Integer dealerID : dealerRevenueFacts.keySet()) {
			final BigDecimal totalAmount = Lambda.sumFrom(
					dealerRevenueFacts.get(dealerID),
					DealerIncomeRevenueFact.class).getAmount();
			final ReportDataDetailAmount amount = new ReportDataDetailAmount();
			amount.setAmount(totalAmount.doubleValue() / (monthOfYear * 1.0));

			dealerDetails.get(dealerID).setRevenue(amount);
		}
		final Double reference = calcReference(Lambda.extract(
				dealerDetails.values(),
				Lambda.on(ReportDataDealerPostSalesIncomeDetail.class)
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
									GetDepartmentIDFromRevenueFunction.INSTANCE);
					for (final DepartmentDetail department : departments) {
						final BigDecimal departmentRevenue = Lambda.sumFrom(
								departmentFacts.get(department.getId()),
								DealerIncomeRevenueFact.class).getAmount();
						final ReportDataDetailAmount departmentAmount = new ReportDataDetailAmount();
						departmentAmount.setAmount(departmentRevenue
								.doubleValue() / (monthOfYear * 1.0));
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
			amount.setAmount(totalMargin.doubleValue() / (monthOfYear * 1.0));

			dealerDetails.get(dealerID).setMargin(amount);
		}
		final Double reference = calcReference(Lambda.extract(
				dealerDetails.values(),
				Lambda.on(ReportDataDealerPostSalesIncomeDetail.class)
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
									GetDepartmentIDFromRevenueFunction.INSTANCE);
					for (final DepartmentDetail department : departments) {
						final BigDecimal departmentMargin = Lambda.sumFrom(
								departmentFacts.get(department.getId()),
								DealerIncomeRevenueFact.class).getMargin();
						final ReportDataDetailAmount departmentAmount = new ReportDataDetailAmount();
						departmentAmount.setAmount(departmentMargin
								.doubleValue() / (monthOfYear * 1.0));
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

}
