package com.jdc.themis.dealer.report;

import static com.jdc.themis.dealer.report.ReportUtils.calcReference;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Map;

import ch.lambdaj.Lambda;

import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.Maps;
import com.jdc.themis.dealer.domain.DealerIncomeRevenueFact;
import com.jdc.themis.dealer.service.RefDataQueryService;
import com.jdc.themis.dealer.web.domain.DealerDetail;
import com.jdc.themis.dealer.web.domain.ReportDataDealerPostSalesDepartmentIncomeDetail;
import com.jdc.themis.dealer.web.domain.ReportDataDetailAmount;
import com.jdc.themis.dealer.web.domain.ReportDealerPostSalesDepartmentIncomeDataList;

public class DealerPostSalesDepartmentIncomeReportCalculator {

	/**
	 * Mapping between dealer id and dealer report detail.
	 */
	private final Map<Integer, ReportDataDealerPostSalesDepartmentIncomeDetail> dealerDetails = Maps
			.newHashMap();

	private Integer year;
	private Integer monthOfYear;

	public DealerPostSalesDepartmentIncomeReportCalculator(
			final Collection<DealerDetail> dealers, final Integer year,
			final Integer monthOfYear) {
		for (final DealerDetail dealer : dealers) {
			// initialize dealer details map for all dealers
			dealerDetails.put(dealer.getId(),
					new ReportDataDealerPostSalesDepartmentIncomeDetail());
			dealerDetails.get(dealer.getId()).setId(dealer.getId());
			dealerDetails.get(dealer.getId()).setName(dealer.getName());
			dealerDetails.get(dealer.getId()).setCode(dealer.getCode());
			dealerDetails.get(dealer.getId()).setBrand(dealer.getBrand());
		}
		this.year = year;
		this.monthOfYear = monthOfYear;
	}

	/**
	 * Populate and return a report details with time and dealer report numbers.
	 * 
	 * @return
	 */
	public ReportDealerPostSalesDepartmentIncomeDataList getReportDetail() {
		final ReportDealerPostSalesDepartmentIncomeDataList reportDetail = new ReportDealerPostSalesDepartmentIncomeDataList();
		reportDetail.setYear(year);
		reportDetail.setMonth(monthOfYear);
		reportDetail.getDetail().addAll(dealerDetails.values());
		return reportDetail;
	}

	/**
	 * Calculate the revenues.
	 * 
	 * @param dealerRevenueFacts
	 * @param op
	 * @return
	 */
	public DealerPostSalesDepartmentIncomeReportCalculator calcRevenues(
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
		final Double reference = calcReference(Lambda
				.extract(
						dealerDetails.values(),
						Lambda.on(
								ReportDataDealerPostSalesDepartmentIncomeDetail.class)
								.getRevenue().getAmount()));
		Lambda.forEach(dealerDetails.values()).getRevenue()
				.setReference(reference);
		return this;
	}

	public DealerPostSalesDepartmentIncomeReportCalculator calcMargins(
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
		final Double reference = calcReference(Lambda
				.extract(
						dealerDetails.values(),
						Lambda.on(
								ReportDataDealerPostSalesDepartmentIncomeDetail.class)
								.getMargin().getAmount()));
		Lambda.forEach(dealerDetails.values()).getMargin()
				.setReference(reference);
		return this;
	}

	public DealerPostSalesDepartmentIncomeReportCalculator calcCount(
			final ImmutableListMultimap<Integer, DealerIncomeRevenueFact> dealerRevenueFacts,
			final RefDataQueryService refDataDAL) {
		for (final Integer dealerID : dealerRevenueFacts.keySet()) {
			final Integer totalCount = Lambda.sumFrom(
					dealerRevenueFacts.get(dealerID),
					DealerIncomeRevenueFact.class).getCount();
			final ReportDataDetailAmount amount = new ReportDataDetailAmount();
			amount.setAmount(Math.round(totalCount.doubleValue()
					/ (monthOfYear * 1.0)) * 1.0);

			dealerDetails.get(dealerID).setCount(amount);
		}
		final Double reference = calcReference(Lambda
				.extract(
						dealerDetails.values(),
						Lambda.on(
								ReportDataDealerPostSalesDepartmentIncomeDetail.class)
								.getCount().getAmount()));
		Lambda.forEach(dealerDetails.values()).getCount()
				.setReference(reference);
		return this;
	}

}
