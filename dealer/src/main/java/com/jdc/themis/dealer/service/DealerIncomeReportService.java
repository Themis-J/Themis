package com.jdc.themis.dealer.service;


import org.springframework.transaction.annotation.Transactional;

import com.jdc.themis.dealer.web.domain.ImportReportDataRequest;
import com.jdc.themis.dealer.web.domain.QueryDealerAccountReceivableResponse;
import com.jdc.themis.dealer.web.domain.QueryDealerExpensePercentageResponse;
import com.jdc.themis.dealer.web.domain.QueryDealerHRAllocationResponse;
import com.jdc.themis.dealer.web.domain.QueryDealerIncomeResponse;
import com.jdc.themis.dealer.web.domain.QueryDealerMaintenanceIncomeResponse;
import com.jdc.themis.dealer.web.domain.QueryDealerPostSalesResponse;
import com.jdc.themis.dealer.web.domain.QueryDealerSalesIncomeResponse;
import com.jdc.themis.dealer.web.domain.QueryDealerSalesResponse;
import com.jdc.themis.dealer.web.domain.QueryDealerSheetSprayIncomeResponse;
import com.jdc.themis.dealer.web.domain.QueryDealerSparePartIncomeResponse;
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
	
	@Transactional(readOnly = true)
	QueryDealerIncomeResponse queryNewVehicleRetailSalesReport(Integer year);
	
	@Transactional(readOnly = true)
	QueryDealerIncomeResponse queryNewVehicleSalesReport(Integer year);
	
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
	QueryDealerPostSalesResponse queryDealerPostSalesIncomeReport(Integer year,
			Integer monthOfYear, Option<Integer> groupBy);

	@Transactional(readOnly = true)
	QueryDealerPostSalesResponse queryDealerPostSalesExpenseReport(
			Integer year, Integer monthOfYear, Option<Integer> groupBy,
			Option<String> itemCategory);

	@Transactional(readOnly = true)
	QueryDealerPostSalesResponse queryDealerPostSalesOpProfitReport(
			Integer year, Integer monthOfYear, Option<Integer> groupBy);

	@Transactional(readOnly = true)
	QueryDealerMaintenanceIncomeResponse queryDealerMaintenanceIncomeReport(
			Integer year, Integer monthOfYear, String itemName);

	@Transactional(readOnly = true)
	QueryDealerMaintenanceIncomeResponse queryDealerMaintenanceWorkOrderReport(
			Integer year, Integer monthOfYear);

	@Transactional(readOnly = true)
	QueryDealerSparePartIncomeResponse queryDealerSparePartIncomeReport(
			Integer year, Integer monthOfYear, String itemName);

	@Transactional(readOnly = true)
	QueryDealerSheetSprayIncomeResponse queryDealerSheetSprayIncomeReport(
			Integer year, Integer monthOfYear, String itemName);

	@Transactional(readOnly = true)
	QueryDealerSheetSprayIncomeResponse queryDealerSheetSprayWorkOrderReport(
			Integer year, Integer monthOfYear);
	
}
