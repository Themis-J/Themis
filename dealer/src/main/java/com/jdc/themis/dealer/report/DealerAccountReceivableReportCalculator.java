package com.jdc.themis.dealer.report;

import static com.jdc.themis.dealer.report.ReportUtils.calcReference;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Map;

import ch.lambdaj.Lambda;

import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.Maps;
import com.jdc.themis.dealer.domain.DealerAccountReceivableFact;
import com.jdc.themis.dealer.web.domain.DealerDetail;
import com.jdc.themis.dealer.web.domain.ReportDataDealerAccountReceivableDetail;
import com.jdc.themis.dealer.web.domain.ReportDataDetailAmount;
import com.jdc.themis.dealer.web.domain.ReportDealerAccountReceivableDataList;

/**
 * Calculate the overall income report numbers by dealer. 
 * 
 * The class is not thread-safe.
 * 
 * @author Kai Chen
 *
 */
public class DealerAccountReceivableReportCalculator {

	/**
	 * Mapping between dealer id and dealer report detail.
	 */
	private final Map<Integer, ReportDataDealerAccountReceivableDetail> dealerDetails = Maps.newHashMap();
	
	private Integer year;
	private Integer monthOfYear;
	
	public DealerAccountReceivableReportCalculator(final Collection<DealerDetail> dealers, final Integer year, final Integer monthOfYear) {
		for (final DealerDetail dealer : dealers) {
			// initialize dealer details map for all dealers
			dealerDetails.put(dealer.getId(), new ReportDataDealerAccountReceivableDetail());
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
	public ReportDealerAccountReceivableDataList getReportDetail() {
		final ReportDealerAccountReceivableDataList reportDetail = new ReportDealerAccountReceivableDataList();
		reportDetail.setYear(year);
		reportDetail.setMonth(monthOfYear);
		reportDetail.getDetail().addAll(dealerDetails.values());
		return reportDetail;
	}
	
	public DealerAccountReceivableReportCalculator calc(
			final ImmutableListMultimap<Integer, DealerAccountReceivableFact> facts) {
		for (final Integer dealerID : facts.keySet()) {
			// populate allocation per dealer
			final BigDecimal totalAlloc = Lambda.sumFrom(
					facts.get(dealerID),
					DealerAccountReceivableFact.class).getAmount();
			final ReportDataDetailAmount amount = new ReportDataDetailAmount();
			amount.setAmount(totalAlloc.doubleValue());
			dealerDetails.get(dealerID).setAmount(amount);
		}
		final Double reference = calcReference(Lambda.extract(dealerDetails.values(), 
				Lambda.on(ReportDataDealerAccountReceivableDetail.class).getAmount().getAmount()));
		Lambda.forEach(dealerDetails.values()).getAmount().setReference(reference);
		return this;
	}
	
}
