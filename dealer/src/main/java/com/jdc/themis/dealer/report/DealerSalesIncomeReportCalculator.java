package com.jdc.themis.dealer.report;

import static com.jdc.themis.dealer.report.ReportUtils.calcReference;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Map;

import ch.lambdaj.Lambda;

import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.Maps;
import com.jdc.themis.dealer.domain.DealerIncomeExpenseFact;
import com.jdc.themis.dealer.domain.DealerIncomeRevenueFact;
import com.jdc.themis.dealer.web.domain.DealerDetail;
import com.jdc.themis.dealer.web.domain.ReportDataDealerSalesIncomeDetail;
import com.jdc.themis.dealer.web.domain.ReportDataDetailAmount;
import com.jdc.themis.dealer.web.domain.ReportDealerSalesIncomeDataList;

/**
 * Calculate the overall income report numbers by dealer. 
 * 
 * The class is not thread-safe.
 * 
 * @author Kai Chen
 *
 */
public class DealerSalesIncomeReportCalculator {

	/**
	 * Mapping between dealer id and dealer report detail.
	 */
	private final Map<Integer, ReportDataDealerSalesIncomeDetail> dealerDetails = Maps.newHashMap();
	
	private Integer year;
	private Integer monthOfYear;
	
	public DealerSalesIncomeReportCalculator(final Collection<DealerDetail> dealers, final Integer year, final Integer monthOfYear) {
		for (final DealerDetail dealer : dealers) {
			// initialize dealer details map for all dealers
			dealerDetails.put(dealer.getId(), new ReportDataDealerSalesIncomeDetail());
			dealerDetails.get(dealer.getId()).setId(dealer.getId());
			dealerDetails.get(dealer.getId()).setName(dealer.getName());
			dealerDetails.get(dealer.getId()).setCode(dealer.getCode());
		}
		
		this.year = year;
		this.monthOfYear = monthOfYear;
	}
	
	/**
	 * Populate and return a report details with time and dealer report numbers. 
	 * 
	 * @return
	 */
	public ReportDealerSalesIncomeDataList getReportDetail() {
		final ReportDealerSalesIncomeDataList reportDetail = new ReportDealerSalesIncomeDataList();
		reportDetail.setYear(year);
		reportDetail.setMonth(monthOfYear);
		reportDetail.getDetail().addAll(dealerDetails.values());
		return reportDetail;
	}
	
	/**
	 * Calculate and populate dealer expense details, including reference, amount and percentage if previous dealer info is provided. 
	 * 
	 * Amount could be "avg" or "sum" of the year's monthly amounts depending on parameter "op". 
	 * 
	 * @param dealerExpenseFacts
	 * @param op
	 * @return
	 */
	public DealerSalesIncomeReportCalculator calcExpenses(
			final ImmutableListMultimap<Integer, DealerIncomeExpenseFact> dealerExpenseFacts) {
		for (final Integer dealerID : dealerExpenseFacts.keySet()) {
			final BigDecimal totalExpense = Lambda.sumFrom(
					dealerExpenseFacts.get(dealerID),
					DealerIncomeExpenseFact.class).getAmount();
			final ReportDataDetailAmount amount = new ReportDataDetailAmount();
			amount.setAmount(totalExpense.doubleValue() / monthOfYear * 1.0);

			dealerDetails.get(dealerID).setExpense(amount);
		}

		final Double reference = calcReference(Lambda.extract(dealerDetails.values(), 
				Lambda.on(ReportDataDealerSalesIncomeDetail.class).getExpense().getAmount()));
		Lambda.forEach(dealerDetails.values()).getExpense().setReference(reference);
		return this;
	}
	
	/**
	 * Calculate the margin. 
	 * 
	 * @param dealerRevenueFacts
	 * @param op
	 * @return
	 */
	public DealerSalesIncomeReportCalculator calcMargins(
			final ImmutableListMultimap<Integer, DealerIncomeRevenueFact> dealerRevenueFacts) {
		for (final Integer dealerID : dealerRevenueFacts.keySet()) {
			final BigDecimal totalMargin = Lambda.sumFrom(
					dealerRevenueFacts.get(dealerID),
					DealerIncomeRevenueFact.class).getMargin();
			final ReportDataDetailAmount amount = new ReportDataDetailAmount();
			amount.setAmount(totalMargin.doubleValue() / monthOfYear * 1.0);

			dealerDetails.get(dealerID).setMargin(amount);
		}
		final Double reference = calcReference(Lambda.extract(dealerDetails.values(), 
												Lambda.on(ReportDataDealerSalesIncomeDetail.class).getMargin().getAmount()));
		Lambda.forEach(dealerDetails.values()).getMargin().setReference(reference);
		return this;
	}
	
	/**
	 * Calculate the margin. 
	 * 
	 * @param dealerRevenueFacts
	 * @param op
	 * @return
	 */
	public DealerSalesIncomeReportCalculator calcSalesMargins(
			final ImmutableListMultimap<Integer, DealerIncomeRevenueFact> dealerRevenueFacts) {
		for (final Integer dealerID : dealerRevenueFacts.keySet()) {
			final BigDecimal totalMargin = Lambda.sumFrom(
					dealerRevenueFacts.get(dealerID),
					DealerIncomeRevenueFact.class).getMargin();
			final ReportDataDetailAmount amount = new ReportDataDetailAmount();
			amount.setAmount(totalMargin.doubleValue() / monthOfYear * 1.0);

			dealerDetails.get(dealerID).setSalesMargin(amount);
		}
		final Double reference = calcReference(Lambda.extract(dealerDetails.values(), 
												Lambda.on(ReportDataDealerSalesIncomeDetail.class).getSalesMargin().getAmount()));
		Lambda.forEach(dealerDetails.values()).getSalesMargin().setReference(reference);
		return this;
	}
	
	/**
	 * Calculate the revenues.
	 * 
	 * @param dealerRevenueFacts
	 * @param op
	 * @return
	 */
	public DealerSalesIncomeReportCalculator calcRevenues(
			final ImmutableListMultimap<Integer, DealerIncomeRevenueFact> dealerRevenueFacts) {
		for (final Integer dealerID : dealerRevenueFacts.keySet()) {
			final BigDecimal totalAmount = Lambda.sumFrom(
					dealerRevenueFacts.get(dealerID),
					DealerIncomeRevenueFact.class).getAmount();
			final ReportDataDetailAmount amount = new ReportDataDetailAmount();
			amount.setAmount(totalAmount.doubleValue() / monthOfYear * 1.0);
			
			dealerDetails.get(dealerID).setRevenue(amount);
		}
		final Double reference = calcReference(Lambda.extract(dealerDetails.values(), 
				Lambda.on(ReportDataDealerSalesIncomeDetail.class).getRevenue().getAmount()));
		Lambda.forEach(dealerDetails.values()).getRevenue().setReference(reference);
		return this;
	}
	
	/**
	 * Calculate operational profit
	 * @return
	 */
	public DealerSalesIncomeReportCalculator calcOpProfit() {
		for (final Integer dealerID : dealerDetails.keySet()) {
			final ReportDataDetailAmount margin = dealerDetails.get(
					dealerID).getMargin();
			final ReportDataDetailAmount expense = dealerDetails.get(
					dealerID).getExpense();
			final ReportDataDetailAmount amount = new ReportDataDetailAmount();
			// Operational Profit = Margin - Expense
			amount.setAmount(margin.getAmount() - expense.getAmount());

			dealerDetails.get(dealerID).setOpProfit(amount);
		}
		final Double reference = calcReference(Lambda.extract(dealerDetails.values(), 
				Lambda.on(ReportDataDealerSalesIncomeDetail.class).getOpProfit().getAmount()));
		Lambda.forEach(dealerDetails.values()).getOpProfit().setReference(reference);
		return this;
	}

}
