package com.jdc.themis.dealer.service;


import org.springframework.transaction.annotation.Transactional;

import com.jdc.themis.dealer.web.domain.ImportReportDataRequest;
import com.jdc.themis.dealer.web.domain.QueryDealerAccountReceivableResponse;
import com.jdc.themis.dealer.web.domain.QueryDealerAftersaleExpenseResponse;
import com.jdc.themis.dealer.web.domain.QueryDealerAftersaleIncomeResponse;
import com.jdc.themis.dealer.web.domain.QueryDealerExpensePercentageResponse;
import com.jdc.themis.dealer.web.domain.QueryDealerHRAllocationResponse;
import com.jdc.themis.dealer.web.domain.QueryDealerIncomeResponse;
import com.jdc.themis.dealer.web.domain.QueryDealerSalesIncomeResponse;
import com.jdc.themis.dealer.web.domain.QueryDealerSalesResponse;
import com.jdc.themis.dealer.web.domain.QueryDepartmentIncomeResponse;

import fj.data.Option;

public interface DealerIncomeReportService {

	@Transactional
	void importReportData(ImportReportDataRequest request);
	
	@Transactional(readOnly=true)
	QueryDealerIncomeResponse queryOverallIncomeReport(
			Integer year, Option<Integer> monthOfYear, Option<Integer> departmentID, Option<Integer> denominator, Option<Integer> groupBy);
	
	@Transactional(readOnly=true)
	QueryDealerIncomeResponse queryPostSalesOverallIncomeReport(
			Integer year, Option<Integer> monthOfYear, Option<Integer> denominator);
	
	@Transactional(readOnly=true)
	QueryDealerIncomeResponse queryNonRecurrentPNLReport(Integer year);
	
	@Transactional(readOnly=true)
	QueryDealerIncomeResponse queryNonSalesProfitReport(Integer year);
	
	@Transactional(readOnly=true)
	QueryDealerExpensePercentageResponse queryOverallExpensePercentageReport(
			Integer year, Integer monthOfYear, Integer denominator, Option<String> category, Option<String> itemName);
	
	@Transactional(readOnly=true)
	QueryDepartmentIncomeResponse queryDepartmentIncomeReport(
			Integer year, Option<Integer> monthOfYear, Option<Integer> dealerID, Option<Integer> departmentID);
	
	@Transactional(readOnly=true)
	QueryDealerSalesResponse queryDealerSalesReport(
			Integer year, Option<Integer> monthOfYear, Option<Integer> departmentID, Option<Integer> denominator);
	
	@Transactional(readOnly=true)
	QueryDealerSalesIncomeResponse queryDealerSalesIncomeReport(
			Integer year, Integer monthOfYear);
	
	@Transactional(readOnly=true)
	QueryDealerHRAllocationResponse queryDealerHRAllocationReport(
			Integer year, Integer monthOfYear, Option<Integer> departmentID, Option<Integer> positionID, Option<Integer> groupBy);
	
	@Transactional(readOnly=true)
	QueryDealerAccountReceivableResponse queryDealerAccountReceivableReport(
			Integer year, Integer monthOfYear, Option<String> itemName);
	
	@Transactional(readOnly=true)
	QueryDealerAccountReceivableResponse queryDealerAccountReceivablePercentageReport(
			Integer year, Integer monthOfYear);

	@Transactional(readOnly = true)
	QueryDealerAftersaleIncomeResponse queryDealerAftersaleIncomeReport(
			Integer year, Integer monthOfYear, Option<Integer> groupBy);
	
	@Transactional(readOnly = true)
	QueryDealerAftersaleExpenseResponse queryDealerAftersaleExpenseReport(
			Integer year, Integer monthOfYear, Option<Integer> groupBy,
			Option<String> itemCategory);
	
}
