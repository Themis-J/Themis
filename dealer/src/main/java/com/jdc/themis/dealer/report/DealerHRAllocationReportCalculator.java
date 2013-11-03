package com.jdc.themis.dealer.report;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Map;

import ch.lambdaj.Lambda;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimaps;
import com.jdc.themis.dealer.data.dao.ReportDAO;
import com.jdc.themis.dealer.domain.DealerHRAllocationFact;
import com.jdc.themis.dealer.domain.ReportItem;
import com.jdc.themis.dealer.service.RefDataQueryService;
import com.jdc.themis.dealer.web.domain.DealerDetail;
import com.jdc.themis.dealer.web.domain.DepartmentDetail;
import com.jdc.themis.dealer.web.domain.HumanResourceAllocationItemDetail;
import com.jdc.themis.dealer.web.domain.ReportDataDealerHRAllocDetail;
import com.jdc.themis.dealer.web.domain.ReportDataDealerHRAllocItemDetail;
import com.jdc.themis.dealer.web.domain.ReportDataDetailAmount;
import com.jdc.themis.dealer.web.domain.ReportDealerHRAllocDataList;

import fj.data.Option;

/**
 * Calculate the overall income report numbers by dealer. 
 * 
 * The class is not thread-safe.
 * 
 * @author Kai Chen
 *
 */
public class DealerHRAllocationReportCalculator {

	/**
	 * Mapping between dealer id and dealer report detail.
	 */
	private final Map<Integer, ReportDataDealerHRAllocDetail> dealerDetails = Maps.newHashMap();
	
	private Integer year;
	private Integer monthOfYear;
	private Option<GroupBy> groupByOption = Option.<GroupBy>none();
	
	public DealerHRAllocationReportCalculator(final Collection<DealerDetail> dealers, final Integer year, final Integer monthOfYear) {
		for (final DealerDetail dealer : dealers) {
			// initialize dealer details map for all dealers
			dealerDetails.put(dealer.getId(), new ReportDataDealerHRAllocDetail());
			dealerDetails.get(dealer.getId()).setId(dealer.getId());
			dealerDetails.get(dealer.getId()).setName(dealer.getName());
			dealerDetails.get(dealer.getId()).setCode(dealer.getCode());
		}
		
		this.year = year;
		this.monthOfYear = monthOfYear;
	}
	
	public enum GroupBy {
		DEPARTMENT, POSITION;
		
		public static Option<GroupBy> valueOf(int value) {
			for ( final GroupBy d : GroupBy.values() ) {
				if ( d.ordinal() == value ) {
					return Option.<GroupBy>some(d);
				}
			}
			return Option.<GroupBy>none();
		}
		
	}
	
	private enum GetDepartmentIDFromFactFunction implements
			Function<DealerHRAllocationFact, Integer> {
		INSTANCE;

		@Override
		public Integer apply(final DealerHRAllocationFact item) {
			return item.getDepartmentID();
		}
	}
	
	private enum GetItemIDFromFactFunction implements
			Function<DealerHRAllocationFact, Long> {
		INSTANCE;
		
		@Override
		public Long apply(final DealerHRAllocationFact item) {
			return item.getItemID();
		}
	}

	/**
	 * Populate and return a report details with time and dealer report numbers. 
	 * 
	 * @return
	 */
	public ReportDealerHRAllocDataList getReportDetail() {
		final ReportDealerHRAllocDataList reportDetail = new ReportDealerHRAllocDataList();
		reportDetail.setYear(year);
		reportDetail.setMonth(monthOfYear);
		reportDetail.getDetail().addAll(dealerDetails.values());
		return reportDetail;
	}
	
	public DealerHRAllocationReportCalculator withGroupBy(final Option<Integer> groupByOption) {
		if ( groupByOption.isSome() ) {
			this.groupByOption = GroupBy.valueOf(groupByOption.some());
		} 
		return this;
	}
	
	public DealerHRAllocationReportCalculator calcAllocations(
			final ImmutableListMultimap<Integer, DealerHRAllocationFact> dealerHRAllocFacts, 
			final RefDataQueryService refDataDAL, final ReportDAO reportDataDAL) {
		for (final Integer dealerID : dealerHRAllocFacts.keySet()) {
			// populate allocation per dealer
			final BigDecimal totalAlloc = Lambda.sumFrom(
					dealerHRAllocFacts.get(dealerID),
					DealerHRAllocationFact.class).getAllocation();
			final ReportDataDetailAmount amount = new ReportDataDetailAmount();
			amount.setAmount(totalAlloc.doubleValue());
			dealerDetails.get(dealerID).setAllocation(amount);
		}
		//check group by
		if ( this.groupByOption.isSome() ) {
			for ( final Integer dealerID : dealerDetails.keySet() ) {
				// if group by is "department", generate the allocation by department in fact
				if ( this.groupByOption.some().equals(GroupBy.DEPARTMENT) ) {
					final Collection<DepartmentDetail> departments = refDataDAL.getDepartments().getItems();
					final Map<Integer, ReportDataDealerHRAllocItemDetail> departmentDetails = Maps.newHashMap();
					final ImmutableListMultimap<Integer, DealerHRAllocationFact> departmentFacts = 
							Multimaps.index(dealerHRAllocFacts.get(dealerID), GetDepartmentIDFromFactFunction.INSTANCE);
					for (final DepartmentDetail department : departments) {
						departmentDetails.put(department.getId(), new ReportDataDealerHRAllocItemDetail());
						departmentDetails.get(department.getId()).setId(department.getId());
						departmentDetails.get(department.getId()).setName(department.getName());
						
						final BigDecimal departmentAlloc = Lambda.sumFrom(
								departmentFacts.get(department.getId()),
								DealerHRAllocationFact.class).getAllocation();
						final ReportDataDetailAmount departmentAmount = new ReportDataDetailAmount();
						departmentAmount.setAmount(departmentAlloc.doubleValue());
						departmentDetails.get(department.getId()).setAllocation(departmentAmount);
						
						dealerDetails.get(dealerID).getDetail().add(departmentDetails.get(department.getId()));
					}
					
				} else {
					// else, generate the allocation by item in fact
					// populate 'name' by dal objects
					final Collection<HumanResourceAllocationItemDetail> positions = refDataDAL.getHumanResourceAllocationItems().getItems();
					final Map<Integer, ReportDataDealerHRAllocItemDetail> positionDetails = Maps.newHashMap();
					final ImmutableListMultimap<Long, DealerHRAllocationFact> positionFacts = 
							Multimaps.index(dealerHRAllocFacts.get(dealerID), GetItemIDFromFactFunction.INSTANCE);
					for (final HumanResourceAllocationItemDetail position : positions) {
						positionDetails.put(position.getId(), new ReportDataDealerHRAllocItemDetail());
						positionDetails.get(position.getId()).setId(position.getId());
						positionDetails.get(position.getId()).setName(position.getName());
						
						final Option<ReportItem> reportItem = reportDataDAL.getReportItem(position.getId(), "HumanResourceAllocation");
						
						if (reportItem.isSome()) {
							final BigDecimal positionAlloc = Lambda.sumFrom(
									positionFacts.get(reportItem.some().getId()),
									DealerHRAllocationFact.class).getAllocation();
							final ReportDataDetailAmount positionAmount = new ReportDataDetailAmount();
							positionAmount.setAmount(positionAlloc.doubleValue());
							positionDetails.get(position.getId()).setAllocation(positionAmount);
						}
						dealerDetails.get(dealerID).getDetail().add(positionDetails.get(position.getId()));
					}
				}
			}
		}
		return this;
	}
	
}
