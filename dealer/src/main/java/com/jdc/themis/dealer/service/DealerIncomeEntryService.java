package com.jdc.themis.dealer.service;

import javax.time.Instant;

import org.springframework.transaction.annotation.Transactional;

import com.jdc.themis.dealer.web.domain.GetAccountReceivableDurationResponse;
import com.jdc.themis.dealer.web.domain.GetDealerEntryItemStatusResponse;
import com.jdc.themis.dealer.web.domain.GetDealerLedgerMetadataResponse;
import com.jdc.themis.dealer.web.domain.GetDealerLedgerResponse;
import com.jdc.themis.dealer.web.domain.GetDealerPostSalesLedgerResponse;
import com.jdc.themis.dealer.web.domain.GetDealerVehicleSalesLedgerResponse;
import com.jdc.themis.dealer.web.domain.GetEmployeeFeeResponse;
import com.jdc.themis.dealer.web.domain.GetEmployeeFeeSummaryResponse;
import com.jdc.themis.dealer.web.domain.GetGeneralJournalResponse;
import com.jdc.themis.dealer.web.domain.GetHumanResourceAllocationResponse;
import com.jdc.themis.dealer.web.domain.GetInventoryDurationResponse;
import com.jdc.themis.dealer.web.domain.GetSalesServiceJournalResponse;
import com.jdc.themis.dealer.web.domain.GetTaxResponse;
import com.jdc.themis.dealer.web.domain.GetVehicleSalesJournalResponse;
import com.jdc.themis.dealer.web.domain.SaveAccountReceivableDurationRequest;
import com.jdc.themis.dealer.web.domain.SaveDealerEntryItemStatusRequest;
import com.jdc.themis.dealer.web.domain.SaveDealerPostSalesLedgerRequest;
import com.jdc.themis.dealer.web.domain.SaveDealerVehicleSalesLedgerRequest;
import com.jdc.themis.dealer.web.domain.SaveEmployeeFeeRequest;
import com.jdc.themis.dealer.web.domain.SaveEmployeeFeeSummaryRequest;
import com.jdc.themis.dealer.web.domain.SaveGeneralJournalRequest;
import com.jdc.themis.dealer.web.domain.SaveHumanResourceAllocationRequest;
import com.jdc.themis.dealer.web.domain.SaveInventoryDurationRequest;
import com.jdc.themis.dealer.web.domain.SaveSalesServiceRevenueRequest;
import com.jdc.themis.dealer.web.domain.SaveTaxRequest;
import com.jdc.themis.dealer.web.domain.SaveVehicleSalesJournalRequest;

import fj.data.Option;

/**
 * Dealer income entry service layer.
 * 
 * Define database transaction behavior here as well.
 * 
 * @author Kai Chen
 * 
 */
public interface DealerIncomeEntryService {
    
    /**
     * Save a list of vehicle sales revenue.
     * 
     * @param request
     * @return
     */
    @Transactional
    Instant saveVehicleSalesRevenue(final SaveVehicleSalesJournalRequest request);

    /**
     * Get a list of vehicle sales revenue per vehicle type.
     * 
     * @param dealerID
     *            Dealer company id
     * @param validDate
     *            Date to query report
     * @return
     */
    @Transactional(readOnly = true)
    GetVehicleSalesJournalResponse getVehicleSalesRevenue(Integer dealerID, Option<Integer> departmentID,
            String validDate, Option<Integer> categoryID);

    /**
     * Save a list of sales & service revenue.
     * 
     * @param request
     * @return
     */
    @Transactional
    Instant saveSalesServiceRevenue(final SaveSalesServiceRevenueRequest request);

    /**
     * Get a list sales & service revenue per sales item.
     * 
     * @param dealerID
     *            Dealer company id
     * @param departmentID
     *            Department id
     * @param validDate
     *            Date to query report
     * @return
     */
    @Transactional(readOnly = true)
    GetSalesServiceJournalResponse getSalesServiceRevenue(Integer dealerID, Integer departmentID, String validDate,
            Option<Integer> categoryID);

    /**
     * Save income tax.
     * 
     * @param request
     * @return
     */
    @Transactional
    Instant saveIncomeTax(final SaveTaxRequest request);

    /**
     * Get a list sales & service revenue per sales item.
     * 
     * @param dealerID
     *            Dealer company id
     * @param departmentID
     *            Department id
     * @param validDate
     *            Date to query report
     * @return
     */
    @Transactional(readOnly = true)
    GetTaxResponse getIncomeTax(Integer dealerID, String validDate);

    /**
     * Save dealer entry item status.
     * 
     * @param request
     * @return
     */
    @Transactional
    Instant saveDealerEntryItemStatus(final SaveDealerEntryItemStatusRequest request);

    /**
     * Get dealer entry item status.
     * 
     * @param dealerID
     *            Dealer company id
     * @param validDate
     *            Date to query report
     * @return
     */
    @Transactional
    GetDealerEntryItemStatusResponse getDealerEntryItemStatus(Integer dealerID, String validDate);

    /**
     * Save a list of general income journals.
     * 
     * @param request
     * @return
     */
    @Transactional
    Instant saveGeneralIncome(final SaveGeneralJournalRequest request);

    /**
     * Get a list of general income journals.
     * 
     * @param dealerID
     *            Dealer company id
     * @param departmentID
     *            Department id
     * @param validDate
     *            Date to query report
     * @return
     */
    @Transactional(readOnly = true)
    GetGeneralJournalResponse getGeneralIncome(Integer dealerID, Integer departmentID, String validDate,
            Option<Integer> categoryID);

    /**
     * Save a list of account receivable durations.
     * 
     * @param request
     * @return
     */
    @Transactional
    Instant saveAccountReceivableDuration(final SaveAccountReceivableDurationRequest request);

    /**
     * Get a list of general income journals.
     * 
     * @param dealerID
     *            Dealer company id
     * @param departmentID
     *            Department id
     * @param validDate
     *            Date to query report
     * @return
     */
    @Transactional(readOnly = true)
    GetAccountReceivableDurationResponse getAccountReceivableDuration(Integer dealerID, String validDate);

    /**
     * Save a list of inventory durations.
     * 
     * @param request
     * @return
     */
    @Transactional
    Instant saveInventoryDuration(final SaveInventoryDurationRequest request);

    /**
     * Get a list of inventory durations.
     * 
     * @param dealerID
     *            Dealer company id
     * @param departmentID
     *            Department id
     * @param validDate
     *            Date to query report
     * @return
     */
    @Transactional(readOnly = true)
    GetInventoryDurationResponse getInventoryDuration(Integer dealerID, Integer departmentID, String validDate);

    /**
     * Save a list of employee fees.
     * 
     * @param request
     * @return
     */
    @Transactional
    Instant saveEmployeeFee(final SaveEmployeeFeeRequest request);

    /**
     * Get a list of employee fee.
     * 
     * @param dealerID
     *            Dealer company id
     * @param departmentID
     *            Department id
     * @param validDate
     *            Date to query report
     * @return
     */
    @Transactional(readOnly = true)
    GetEmployeeFeeResponse getEmployeeFee(Integer dealerID, Integer departmentID, String validDate);

    /**
     * Save a list of employee fees summary.
     * 
     * @param request
     * @return
     */
    @Transactional
    Instant saveEmployeeFeeSummary(final SaveEmployeeFeeSummaryRequest request);

    /**
     * Get a list of employee fee summary.
     * 
     * @param dealerID
     *            Dealer company id
     * @param departmentID
     *            Department id
     * @param validDate
     *            Date to query report
     * @return
     */
    @Transactional(readOnly = true)
    GetEmployeeFeeSummaryResponse getEmployeeFeeSummary(Integer dealerID, Integer departmentID, String validDate);

    /**
     * Save a list of employee fees summary.
     * 
     * @param request
     * @return
     */
    @Transactional
    Instant saveHumanResourceAllocation(final SaveHumanResourceAllocationRequest request);

    /**
     * Get a list of employee fee summary.
     * 
     * @param dealerID
     *            Dealer company id
     * @param departmentID
     *            Department id
     * @param validDate
     *            Date to query report
     * @return
     */
    @Transactional(readOnly = true)
    GetHumanResourceAllocationResponse getHumanResourceAllocation(Integer dealerID, String validDate);

    /**
     * Get two lists of metadata for dealer vehicle sales ledger and post sales ledger
     * 
     * @param dealerID
     * @return the lists of ledger metadata
     */
    @Transactional(readOnly = true)
    GetDealerLedgerMetadataResponse getDealerLedgerMetadata(final Integer dealerID);

    /**
     * @param contractNo
     * @return
     */
    @Transactional(readOnly = true)
    GetDealerVehicleSalesLedgerResponse getDealerVehicleSalesLedger(final String contractNo);

    /**
     * @param request
     * @return
     */
    @Transactional
    Instant saveDealerVehicleSalesLedger(final SaveDealerVehicleSalesLedgerRequest request);

    /**
     * @param contractNo
     * @param model
     * @param type
     * @param color
     * @param lpNumber
     * @param frameNo
     * @param manufacturerDebitDate
     * @param warehousingDate
     * @param salesDate
     * @param guidingPrice
     * @param customerName
     * @param identificationNo
     * @param salesConsultant
     * @param customerType
     * @param dealerID
     * @param marker
     * @param limit
     * @return
     */
    @Transactional(readOnly = true)
    GetDealerLedgerResponse queryDealerVehicleSalesLedger(String contractNo, String model, String type, String color,
            String lpNumber, String frameNo, String manufacturerDebitDate, String warehousingDate, String salesDate,
            Double guidingPrice, String customerName, String identificationNo, String salesConsultant,
            String customerType, Integer dealerID, Integer marker, Integer limit);

    /**
     * @param workOrderNo
     * @return
     */
    @Transactional(readOnly = true)
    GetDealerPostSalesLedgerResponse getDealerPostSalesLedger(final String workOrderNo);

    /**
     * @param request
     * @return
     */
    @Transactional
    Instant saveDealerPostSalesLedger(final SaveDealerPostSalesLedgerRequest request);

    /**
     * @param workOrderNo
     * @param purchaseDate
     * @param mileage
     * @param lpNumber
     * @param customerName
     * @param color
     * @param frameNo
     * @param model
     * @param enterFactoryDate
     * @param exitFactoryDate
     * @param customerType
     * @param insuranceAgency
     * @param insuranceDueDate
     * @param insuranceClaimNumber
     * @param dealerID
     * @param marker
     * @param limit
     * @return
     */
    @Transactional(readOnly = true)
    GetDealerLedgerResponse queryDealerPostSalesLedger(String workOrderNo, String purchaseDate, Double mileage,
            String lpNumber, String customerName, String color, String frameNo, String model, String enterFactoryDate,
            String exitFactoryDate, String customerType, String insuranceAgency, String insuranceDueDate,
            Integer insuranceClaimNumber, Integer dealerID, Integer marker, Integer limit);

}
