package com.jdc.themis.dealer.service.impl;

import java.util.Collection;

import com.google.common.collect.Lists;
import com.jdc.themis.dealer.data.dao.ReportDAO;
import com.jdc.themis.dealer.domain.DealerAccountReceivableFact;
import com.jdc.themis.dealer.domain.DealerHRAllocationFact;
import com.jdc.themis.dealer.domain.DealerIncomeExpenseFact;
import com.jdc.themis.dealer.domain.DealerIncomeRevenueFact;
import com.jdc.themis.dealer.domain.DealerInventoryFact;

import fj.data.Option;

public class DealerIncomeFactsQueryBuilder {

	private ReportDAO reportDAL;
	private Integer year;
	private Collection<Integer> monthOfYear = Lists.newArrayList();
	private Option<Integer> lessThanMonthOfYear = Option.<Integer>none();
	private Collection<Integer> departmentID = Lists.newArrayList();
	private Collection<Integer> itemSource = Lists.newArrayList();
	private Collection<String> itemCategory = Lists.newArrayList();
	private Collection<String> itemName = Lists.newArrayList();
	private Collection<Long> itemID = Lists.newArrayList();
	private Option<Integer> positionID = Option.<Integer>none();
	private Option<Integer> durationID = Option.<Integer>none();
	private Collection<Integer> dealerID = Lists.newArrayList();
	
	public DealerIncomeFactsQueryBuilder(final ReportDAO reportDAL){
		this.reportDAL = reportDAL;
	}
	
	public DealerIncomeFactsQueryBuilder withYear(final Integer year) {
		this.year = year;
		return this;
	}
	
	public DealerIncomeFactsQueryBuilder withMonthOfYear(final Integer monthOfYear) {
		this.monthOfYear.add(monthOfYear);
		return this;
	}
	
	public DealerIncomeFactsQueryBuilder withLessThanMonthOfYear(final Integer monthOfYear) {
		this.lessThanMonthOfYear = Option.fromNull(monthOfYear);
		return this;
	}
	
	public DealerIncomeFactsQueryBuilder withPosition(final Integer id) {
		this.positionID = Option.fromNull(id);
		return this;
	} 
	
	public DealerIncomeFactsQueryBuilder withDuration(final Integer id) {
		this.durationID = Option.fromNull(id);
		return this;
	} 
	
	public DealerIncomeFactsQueryBuilder withDepartmentID(final Integer departmentID) {
		this.departmentID.add(departmentID);
		return this;
	}
	
	public DealerIncomeFactsQueryBuilder withItemSource(final Integer itemSource) {
		this.itemSource.add(itemSource);
		return this;
	}
	
	public DealerIncomeFactsQueryBuilder withItemCategory(final String itemCategory) {
		this.itemCategory.add(itemCategory);
		return this;
	}
	public DealerIncomeFactsQueryBuilder withItemName(final String itemName) {
		this.itemName.add(itemName);
		return this;
	}
	
	public DealerIncomeFactsQueryBuilder withItemID(final Long itemID) {
		this.itemID.add(itemID);
		return this;
	}
	
	public DealerIncomeFactsQueryBuilder withDealerID(final Integer dealerID) {
		this.dealerID.add(dealerID);
		return this;
	}
	
	public DealerIncomeFactsQueryBuilder clear() {
		this.itemCategory.clear();
		this.itemSource.clear();
		this.itemName.clear();
		this.itemID.clear();
		this.positionID = Option.<Integer>none();
		this.durationID = Option.<Integer>none();
		return this;
	}
	
	public Collection<DealerIncomeRevenueFact> queryRevenues() {
		if ( lessThanMonthOfYear.isSome() ) {
			return this.reportDAL.getDealerIncomeRevenueFacts(year,
					lessThanMonthOfYear, departmentID, itemSource, itemCategory, itemID, dealerID);
		}
		return this.reportDAL.getDealerIncomeRevenueFacts(year,
				monthOfYear, departmentID, itemSource, itemCategory, itemID, dealerID);
	}
	
	public Collection<DealerIncomeExpenseFact> queryExpenses() {
		if ( lessThanMonthOfYear.isSome() ) {
			return this.reportDAL.getDealerIncomeExpenseFacts(year,
					lessThanMonthOfYear, departmentID, itemSource, itemCategory, itemName, itemID, dealerID);
		}
		return this.reportDAL.getDealerIncomeExpenseFacts(year,
				monthOfYear, departmentID, itemSource, itemCategory, itemName, itemID, dealerID);
	}
	
	public Collection<DealerHRAllocationFact> queryHRAllocations() {
		return this.reportDAL.getDealerHRAllocationFacts(year,
				monthOfYear.iterator().next(), departmentID.isEmpty() ? Option.<Integer>none() : Option.fromNull(departmentID.iterator().next()), positionID, dealerID);
	}
	
	public Collection<DealerAccountReceivableFact> queryAccountReceivables() {
		return this.reportDAL.getDealerAccountReceivableFacts(year,
				monthOfYear.iterator().next(), durationID, itemName, dealerID);
	}
	
	public Collection<DealerInventoryFact> queryInventoryDurations() {
		return this.reportDAL.getDealerInventoryFacts(year,
				monthOfYear.iterator().next(), departmentID.isEmpty() ? Option.<Integer>none() : Option.fromNull(departmentID.iterator().next()), durationID, itemName, dealerID);
	}
}
