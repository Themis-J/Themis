package com.jdc.themis.dealer.service.rest;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jdc.themis.dealer.service.DealerIncomeReportService;
import com.jdc.themis.dealer.utils.Constant;
import com.jdc.themis.dealer.utils.RestServiceErrorHandler;
import com.jdc.themis.dealer.utils.Utils;
import com.jdc.themis.dealer.web.domain.GeneralResponse;
import com.jdc.themis.dealer.web.domain.ImportReportDataRequest;

import fj.data.Option;

@Service
@RolesAllowed({Constant.HEAD_ROLE})
public class DealerReportRestService {

	@Autowired
	private DealerIncomeReportService dealerIncomeReportService;
	
	public void setDealerIncomeReportService(
			DealerIncomeReportService dealerIncomeReportService) {
		this.dealerIncomeReportService = dealerIncomeReportService;
	}

	/**
	 * Import report data for given year and month.
	 * 
	 * @param request
	 * @return
	 */
	@POST
	@Produces({ "application/json", "application/xml" })
	@Consumes({ "application/json", "application/xml" })
	@Path("/import")
	@RolesAllowed({Constant.HEAD_ROLE, Constant.ADMIN_ROLE, Constant.SUPER_ROLE})
	@RestServiceErrorHandler
	public Response importReportData(final ImportReportDataRequest request) {
		final GeneralResponse response = new GeneralResponse();
		response.setErrorMsg("");
		response.setSuccess(true);
		dealerIncomeReportService
			.importReportData(request);
		response.setTimestamp(Utils.currentTimestamp());
		return Response.ok(response).status(Status.CREATED).build();
	}

	/**
	 * Query overall income report.
	 * 
	 * @param year
	 * @return
	 */
	@GET
	@Path("/query/overallIncomeReport")
	@Produces({ "application/json", "application/xml" })
	@RestServiceErrorHandler
	public Response queryDealerOverallIncomeReport(
			@QueryParam("year") Integer year, 
			@QueryParam("monthOfYear") Integer monthOfYear, 
			@QueryParam("departmentID") Integer departmentID, 
			@QueryParam("denominator") Integer denominator, 
			@QueryParam("groupBy") Integer groupBy) {
		return Response.ok(
				dealerIncomeReportService.queryOverallIncomeReport(
						year, 
						Option.fromNull(monthOfYear), 
						Option.fromNull(departmentID),
						Option.fromNull(denominator),
						Option.fromNull(groupBy))).build();
	}
	
	/**
	 * Query overall income report.
	 * 
	 * @param year
	 * @return
	 */
	@GET
	@Path("/query/postSalesOverallIncomeReport")
	@Produces({ "application/json", "application/xml" })
	@RestServiceErrorHandler
	public Response queryDealerPostSalesOverallIncomeReport(
			@QueryParam("year") Integer year,  
			@QueryParam("monthOfYear") Integer monthOfYear, 
			@QueryParam("denominator") Integer denominator) {
		return Response.ok(
				dealerIncomeReportService.queryPostSalesOverallIncomeReport(
						year, 
						Option.fromNull(monthOfYear), 
						Option.fromNull(denominator))).build();
	}
	
	/**
	 * Query overall income report.
	 * 
	 * @param year
	 * @return
	 */
	@GET
	@Path("/query/nonRecurrentPNLReport")
	@Produces({ "application/json", "application/xml" })
	@RestServiceErrorHandler
	public Response queryDealerNonRecurrentPNLReport(
			@QueryParam("year") Integer year) {
		return Response.ok(
				dealerIncomeReportService.queryNonRecurrentPNLReport(year)).build();
	}
	
	@GET
	@Path("/query/nonSalesProfitReport")
	@Produces({ "application/json", "application/xml" })
	@RestServiceErrorHandler
	public Response queryDealerNonSalesProfitReport(
			@QueryParam("year") Integer year) {
		return Response.ok(
				dealerIncomeReportService.queryNonSalesProfitReport(year)).build();
	}
	
	@GET
	@Path("/query/newVehicleRetailSalesReport")
	@Produces({ "application/json", "application/xml" })
	@RestServiceErrorHandler
	public Response queryDealerNewVehicleRetailSalesReport(
			@QueryParam("year") Integer year) {
		return Response.ok(
				dealerIncomeReportService
						.queryNewVehicleRetailSalesReport(year)).build();
	}

	@GET
	@Path("/query/newVehicleSalesReport")
	@Produces({ "application/json", "application/xml" })
	@RestServiceErrorHandler
	public Response queryDealerNewVehicleSalesReport(
			@QueryParam("year") Integer year) {
		return Response.ok(
				dealerIncomeReportService.queryNewVehicleSalesReport(year))
				.build();
	}

	@GET
	@Path("/query/newVehicleRetailMarginReport")
	@Produces({ "application/json", "application/xml" })
	@RestServiceErrorHandler
	public Response queryDealerNewVehicleRetailMarginReport(
			@QueryParam("year") Integer year) {
		return Response.ok(
				dealerIncomeReportService
						.queryNewVehicleRetailMarginReport(year)).build();
	}

	@GET
	@Path("/query/overallExpensePercentageReport")
	@Produces({ "application/json", "application/xml" })
	@RestServiceErrorHandler
	public Response queryDealerOverallExpensePercentageReport(
			@QueryParam("year") Integer year, 
			@QueryParam("monthOfYear") Integer monthOfYear, 
			@QueryParam("denominator") Integer denominator, 
			@QueryParam("itemCategory") String itemCategory, 
			@QueryParam("itemName") String itemName) {
		return Response.ok(
				dealerIncomeReportService.queryOverallExpensePercentageReport(
						year, 
						monthOfYear, 
						denominator,
						Option.fromNull(itemCategory), 
						Option.fromNull(itemName))).build();
	}
	
	@GET
	@Path("/query/overallHRAllocReport")
	@Produces({ "application/json", "application/xml" })
	@RestServiceErrorHandler
	public Response queryDealerOverallHRAllocReport(
			@QueryParam("year") Integer year, 
			@QueryParam("monthOfYear") Integer monthOfYear, 
			@QueryParam("departmentID") Integer departmentID, 
			@QueryParam("positionID") Integer positionID, 
			@QueryParam("groupBy") Integer groupBy) {
		return Response.ok(
				dealerIncomeReportService
					.queryDealerHRAllocationReport(year, monthOfYear, Option.fromNull(departmentID), Option.fromNull(positionID), Option.fromNull(groupBy)))
					.build();
	}
	
	@GET
	@Path("/query/accountReceivableReport")
	@Produces({ "application/json", "application/xml" })
	@RestServiceErrorHandler
	public Response queryDealerAccountReceivableReport(
			@QueryParam("year") Integer year, 
			@QueryParam("monthOfYear") Integer monthOfYear, 
			@QueryParam("itemName") String itemName) {
		return Response.ok(
				dealerIncomeReportService
					.queryDealerAccountReceivableReport(year, monthOfYear, Option.fromNull(itemName))).build();
	}
	
	@GET
	@Path("/query/accountReceivablePercentageReport")
	@Produces({ "application/json", "application/xml" })
	@RestServiceErrorHandler
	public Response queryDealerAccountReceivablePercentageReport(
			@QueryParam("year") Integer year, 
			@QueryParam("monthOfYear") Integer monthOfYear) {
		return Response.ok(
				dealerIncomeReportService
					.queryDealerAccountReceivablePercentageReport(year, monthOfYear)).build();
	}
	
	/**
	 * Query department income report.
	 * 
	 * @param year
	 * @param dealer id
	 * @return
	 */
	@GET
	@Path("/query/departmentIncomeReport")
	@Produces({ "application/json", "application/xml" })
	@RestServiceErrorHandler
	public Response queryDealerDepartmentIncomeReport(
			@QueryParam("year") Integer year, 
			@QueryParam("monthOfYear") Integer monthOfYear, 
			@QueryParam("dealerID") Integer dealerID,
			@QueryParam("departmentID") Integer departmentID) {
		return Response.ok(
				dealerIncomeReportService.queryDepartmentIncomeReport(
						year, 
						Option.fromNull(monthOfYear),
						Option.fromNull(dealerID), 
						Option.fromNull(departmentID))).build();
	}
	
	/**
	 * Query sales report.
	 * 
	 * @param year
	 * @param dealer id
	 * @return
	 */
	@GET
	@Path("/query/salesReport")
	@Produces({ "application/json", "application/xml" })
	@RestServiceErrorHandler
	public Response queryDealerSalesReport(
			@QueryParam("year") Integer year, 
			@QueryParam("monthOfYear") Integer monthOfYear, 
			@QueryParam("denominator") Integer denominator, 
			@QueryParam("departmentID") Integer departmentID) {
		return Response.ok(
				dealerIncomeReportService.queryDealerSalesReport(
						year, 
						Option.fromNull(monthOfYear), 
						Option.fromNull(departmentID), 
						Option.fromNull(denominator))).build();
	}
	
	/**
	 * Query sales income report.
	 * 
	 * @param year
	 * @param dealer id
	 * @return
	 */
	@GET
	@Path("/query/salesIncomeReport")
	@Produces({ "application/json", "application/xml" })
	@RestServiceErrorHandler
	public Response queryDealerSalesIncomeReport(
			@QueryParam("year") Integer year, 
			@QueryParam("monthOfYear") Integer monthOfYear) {
		return Response.ok(
				dealerIncomeReportService.queryDealerSalesIncomeReport(
						year, 
						monthOfYear)).build();
	}
	
	/**
	 * Query post sales income report.
	 * 
	 * @param year
	 * @param monthOfYear
	 * @param groupBy
	 * @return
	 */
	@GET
	@Path("/query/postSalesIncomeReport")
	@Produces({ "application/json", "application/xml" })
	@RestServiceErrorHandler
	public Response queryDealerPostSalesIncomeReport(
			@QueryParam("year") Integer year,
			@QueryParam("monthOfYear") Integer monthOfYear,
			@QueryParam("groupBy") Integer groupBy) {
		return Response.ok(
				dealerIncomeReportService.queryDealerPostSalesIncomeReport(
						year, monthOfYear, Option.fromNull(groupBy))).build();
	}

	/**
	 * Query post sales expense report.
	 * 
	 * @param year
	 * @param monthOfYear
	 * @param groupBy
	 * @param itemCategory
	 * @return
	 */
	@GET
	@Path("/query/postSalesExpenseReport")
	@Produces({ "application/json", "application/xml" })
	@RestServiceErrorHandler
	public Response queryDealerPostSalesExpenseReport(
			@QueryParam("year") Integer year,
			@QueryParam("monthOfYear") Integer monthOfYear,
			@QueryParam("groupBy") Integer groupBy,
			@QueryParam("itemCategory") String itemCategory) {
		return Response.ok(
				dealerIncomeReportService.queryDealerPostSalesExpenseReport(
						year, monthOfYear, Option.fromNull(groupBy),
						Option.fromNull(itemCategory))).build();
	}

	/**
	 * Query post sales op profit report.
	 * 
	 * @param year
	 * @param monthOfYear
	 * @param groupBy
	 * @return
	 */
	@GET
	@Path("/query/postSalesOpProfitReport")
	@Produces({ "application/json", "application/xml" })
	@RestServiceErrorHandler
	public Response queryDealerPostSalesOpProfitReport(
			@QueryParam("year") Integer year,
			@QueryParam("monthOfYear") Integer monthOfYear,
			@QueryParam("groupBy") Integer groupBy) {
		return Response.ok(
				dealerIncomeReportService.queryDealerPostSalesOpProfitReport(
						year, monthOfYear, Option.fromNull(groupBy))).build();
	}

	/**
	 * Query maintenance department income report.
	 * 
	 * @param year
	 * @param monthOfYear
	 * @param itemName
	 * @return
	 */
	@GET
	@Path("/query/maintenanceIncomeReport")
	@Produces({ "application/json", "application/xml" })
	@RestServiceErrorHandler
	public Response queryDealerMaintenanceIncomeReport(
			@QueryParam("year") Integer year,
			@QueryParam("monthOfYear") Integer monthOfYear,
			@QueryParam("itemName") String itemName) {
		return Response.ok(
				dealerIncomeReportService.queryDealerMaintenanceIncomeReport(
						year, monthOfYear, itemName)).build();
	}

	/**
	 * Query maintenance department work order report.
	 * 
	 * @param year
	 * @param monthOfYear
	 * @return
	 */
	@GET
	@Path("/query/maintenanceWorkOrderReport")
	@Produces({ "application/json", "application/xml" })
	@RestServiceErrorHandler
	public Response queryDealerMaintenanceWorkOrderReport(
			@QueryParam("year") Integer year,
			@QueryParam("monthOfYear") Integer monthOfYear) {
		return Response.ok(
				dealerIncomeReportService
						.queryDealerMaintenanceWorkOrderReport(year,
								monthOfYear)).build();
	}

	/**
	 * Query spare part department income report.
	 * 
	 * @param year
	 * @param monthOfYear
	 * @param itemName
	 * @return
	 */
	@GET
	@Path("/query/sparePartIncomeReport")
	@Produces({ "application/json", "application/xml" })
	@RestServiceErrorHandler
	public Response queryDealerSparePartIncomeReport(
			@QueryParam("year") Integer year,
			@QueryParam("monthOfYear") Integer monthOfYear,
			@QueryParam("itemName") String itemName) {
		return Response.ok(
				dealerIncomeReportService.queryDealerSparePartIncomeReport(
						year, monthOfYear, itemName)).build();
	}

	/**
	 * Query sheet spray department income report.
	 * 
	 * @param year
	 * @param monthOfYear
	 * @param itemName
	 * @return
	 */
	@GET
	@Path("/query/sheetSprayIncomeReport")
	@Produces({ "application/json", "application/xml" })
	@RestServiceErrorHandler
	public Response queryDealerSheetSprayIncomeReport(
			@QueryParam("year") Integer year,
			@QueryParam("monthOfYear") Integer monthOfYear,
			@QueryParam("itemName") String itemName) {
		return Response.ok(
				dealerIncomeReportService.queryDealerSheetSprayIncomeReport(
						year, monthOfYear, itemName)).build();
	}

	/**
	 * Query sheet spray department work order report.
	 * 
	 * @param year
	 * @param monthOfYear
	 * @return
	 */
	@GET
	@Path("/query/sheetSprayWorkOrderReport")
	@Produces({ "application/json", "application/xml" })
	@RestServiceErrorHandler
	public Response queryDealerSheetSprayWorkOrderReport(
			@QueryParam("year") Integer year,
			@QueryParam("monthOfYear") Integer monthOfYear) {
		return Response.ok(
				dealerIncomeReportService.queryDealerSheetSprayWorkOrderReport(
						year, monthOfYear)).build();
	}
	
}
