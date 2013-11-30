package com.jdc.themis.dealer.data.dao;

import java.util.Collection;
import javax.time.calendar.LocalDate;

import com.jdc.themis.dealer.domain.DealerAccountReceivableFact;
import com.jdc.themis.dealer.domain.DealerHRAllocationFact;
import com.jdc.themis.dealer.domain.DealerIncomeExpenseFact;
import com.jdc.themis.dealer.domain.DealerIncomeRevenueFact;
import com.jdc.themis.dealer.domain.DealerInventoryFact;
import com.jdc.themis.dealer.domain.ReportItem;
import com.jdc.themis.dealer.domain.ReportTime;

import fj.data.Option;

public interface ReportDAO {

	void saveDealerIncomeRevenueFacts(Collection<DealerIncomeRevenueFact> journals);
	
	Collection<DealerIncomeRevenueFact> getDealerIncomeRevenueFacts(
			Integer year,
			Collection<Integer> monthOfYear, 
			Collection<Integer> departmentID,
			Collection<Integer> itemSource,
			Collection<String> itemCategory, 
			Collection<Long> itemID, 
			Collection<Integer> dealerID);
	
	Collection<DealerIncomeRevenueFact> getDealerIncomeRevenueFacts(
			Integer year,
			Option<Integer> lessThanMonthOfYear, 
			Collection<Integer> departmentID,
			Collection<Integer> itemSource,
			Collection<String> itemCategory, 
			Collection<Long> itemID, 
			Collection<Integer> dealerID);
	
	void saveDealerIncomeExpenseFacts(Collection<DealerIncomeExpenseFact> journals);
	
	Collection<DealerIncomeExpenseFact> getDealerIncomeExpenseFacts(
			Integer year,
			Collection<Integer> monthOfYear, 
			Collection<Integer> departmentID, 
			Collection<Integer> itemSource,
			Collection<String> itemCategory, 
			Collection<String> itemName, 
			Collection<Long> itemID, 
			Collection<Integer> dealerID);
	
	Collection<DealerIncomeExpenseFact> getDealerIncomeExpenseFacts(
			Integer year,
			Option<Integer> lessThanMonthOfYear, 
			Collection<Integer> departmentID, 
			Collection<Integer> itemSource,
			Collection<String> itemCategory, 
			Collection<String> itemName, 
			Collection<Long> itemID, 
			Collection<Integer> dealerID);
	
	void saveDealerHRAllocationFacts(Collection<DealerHRAllocationFact> journals);
	
	Collection<DealerHRAllocationFact> getDealerHRAllocationFacts(
			Integer year,
			Integer monthOfYear, 
			Option<Integer> departmentID, 
			Option<Integer> itemID, 
			Collection<Integer> dealerID);
	
	void saveDealerAccountReceivableFacts(Collection<DealerAccountReceivableFact> journals);
	
	Collection<DealerAccountReceivableFact> getDealerAccountReceivableFacts(
			Integer year,
			Integer monthOfYear, 
			Option<Integer> durationID, 
			Collection<String> itemName, 
			Collection<Integer> dealerID);
	
	void saveDealerInventoryFacts(Collection<DealerInventoryFact> journals);
	
	Collection<DealerInventoryFact> getDealerInventoryFacts(
			Integer year,
			Integer monthOfYear, 
			Option<Integer> departmentID, 
			Option<Integer> durationID, 
			Collection<String> itemName, 
			Collection<Integer> dealerID);
	
	void importVehicleSalesJournal(LocalDate validDate);
	
	void importSalesServiceJournal(LocalDate validDate);
	
	void importGeneralJournal(LocalDate validDate);
	
	void importTaxJournal(LocalDate validDate);
	
	void importHRAllocation(LocalDate validDate);
	
	void importAccountReceivable(LocalDate validDate);
	
	void importInventory(LocalDate validDate);
	
	Option<ReportTime> getReportTime(LocalDate validDate);
	
	Collection<ReportTime> getReportTime(Integer year, Option<Integer> monthOfYear);
	
	Collection<ReportTime> getReportTimeLessThanGivenMonth(Integer year,
			Option<Integer> lessThanMonthOfYear);
	
	Collection<ReportTime> getAllReportTime();
	
	Option<ReportTime> addReportTime(LocalDate validDate);
	
	Option<ReportItem> addReportItem(Integer itemID, String itemName, String source, String category);
	
	Option<ReportItem> getReportItem(Integer itemID, String source);
	
	Option<ReportItem> getReportItem(String itemName, String source);
	
	Collection<ReportItem> getReportItem(Collection<String> itemCategory, Collection<String> itemNames, Collection<Integer> itemSources);
	
	Option<ReportItem> getReportItem(Long id);
	
	Collection<ReportItem> getAllReportItem();
}
