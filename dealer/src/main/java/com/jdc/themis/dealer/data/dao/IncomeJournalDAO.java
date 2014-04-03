package com.jdc.themis.dealer.data.dao;

import java.util.Collection;

import javax.time.Instant;
import javax.time.calendar.LocalDate;

import com.jdc.themis.dealer.domain.AccountReceivableDuration;
import com.jdc.themis.dealer.domain.DealerEntryItemStatus;
import com.jdc.themis.dealer.domain.DealerLedgerMetadata;
import com.jdc.themis.dealer.domain.DealerPostSalesLedger;
import com.jdc.themis.dealer.domain.DealerVehicleSalesLedger;
import com.jdc.themis.dealer.domain.EmployeeFee;
import com.jdc.themis.dealer.domain.EmployeeFeeSummary;
import com.jdc.themis.dealer.domain.GeneralJournal;
import com.jdc.themis.dealer.domain.HumanResourceAllocation;
import com.jdc.themis.dealer.domain.InventoryDuration;
import com.jdc.themis.dealer.domain.SalesServiceJournal;
import com.jdc.themis.dealer.domain.TaxJournal;
import com.jdc.themis.dealer.domain.VehicleSalesJournal;

/**
 * Income journal data access layer.
 * 
 * @author Kai Chen
 * 
 */
public interface IncomeJournalDAO {

    Instant saveTaxJournal(Integer dealerID, Collection<TaxJournal> journals);

    Collection<TaxJournal> getTaxJournal(Integer dealerID, LocalDate validDate);

    Collection<TaxJournal> getTaxJournal(LocalDate validDate, Instant timestamp);

    Instant saveDealerEntryItemStatus(Integer dealerID, Collection<DealerEntryItemStatus> journals);

    Collection<DealerEntryItemStatus> getDealerEntryItemStatus(Integer dealerID, LocalDate validDate);

    Instant saveVehicleSalesJournal(Integer dealerID, Integer departmentID, Collection<VehicleSalesJournal> journals);

    Collection<VehicleSalesJournal> getVehicleSalesJournal(Integer dealerID, Integer departmentID, LocalDate validDate);

    Collection<VehicleSalesJournal> getVehicleSalesJournal(LocalDate validDate, Instant timestamp);

    Instant saveSalesServiceJournal(Integer dealerID, Integer departmentID, Collection<SalesServiceJournal> journals);

    Collection<SalesServiceJournal> getSalesServiceJournal(Integer dealerID, Integer departmentID, LocalDate validDate);

    Collection<SalesServiceJournal> getSalesServiceJournal(LocalDate validDate, Instant timestamp);

    Instant saveGeneralJournal(Integer dealerID, Integer departmentID, Collection<GeneralJournal> journals);

    Collection<GeneralJournal> getGeneralJournal(Integer dealerID, Integer departmentID, LocalDate validDate);

    Collection<GeneralJournal> getGeneralJournal(LocalDate validDate, Instant timestamp);

    Instant saveAccountReceivableDuration(Integer dealerID, Collection<AccountReceivableDuration> journals);

    Collection<AccountReceivableDuration> getAccountReceivableDuration(Integer dealerID, LocalDate validDate);

    Collection<AccountReceivableDuration> getAccountReceivableDuration(LocalDate validDate, Instant timestamp);

    Instant saveHumanResourceAllocation(Integer dealerID, Integer departmentID,
            Collection<HumanResourceAllocation> journals);

    Collection<HumanResourceAllocation> getHumanResourceAllocation(Integer dealerID, LocalDate validDate);

    Collection<HumanResourceAllocation> getHumanResourceAllocation(LocalDate validDate, Instant timestamp);

    Instant saveInventoryDuration(Integer dealerID, Integer departmentID, Collection<InventoryDuration> journals);

    Collection<InventoryDuration> getInventoryDuration(Integer dealerID, Integer departmentID, LocalDate validDate);

    Collection<InventoryDuration> getInventoryDuration(LocalDate validDate, Instant timestamp);

    Instant saveEmployeeFee(Integer dealerID, Integer departmentID, Collection<EmployeeFee> journals);

    Collection<EmployeeFee> getEmployeeFee(Integer dealerID, Integer departmentID, LocalDate validDate);

    Collection<EmployeeFee> getEmployeeFee(LocalDate validDate, Instant timestamp);

    Instant saveEmployeeFeeSummary(Integer dealerID, Integer departmentID, Collection<EmployeeFeeSummary> journals);

    Collection<EmployeeFeeSummary> getEmployeeFeeSummary(Integer dealerID, Integer departmentID, LocalDate validDate);

    Collection<DealerLedgerMetadata> getDealerLedgerMetadata(Integer dealerID, Integer dealerLedgerMetadataCategoryID);

    Instant saveDealerVehicleSalesLedger(DealerVehicleSalesLedger dealerVehicleSalesLedger);

    Collection<DealerVehicleSalesLedger> queryDealerVehicleSalesLedger(Integer contractNo, String model, String type,
            String color, String lpNumber, String frameNo, String manufacturerDebitDate, String warehousingDate,
            String salesDate, Double guidingPrice, String customerName, String identificationNo,
            String salesConsultant, String customerType, Integer dealerID, Integer marker, Integer limit);

    Collection<DealerVehicleSalesLedger> getDealerVehicleSalesLedger(Integer contractNo);

    Instant saveDealerPostSalesLedger(DealerPostSalesLedger dealerPostSalesLedger);

    Collection<DealerPostSalesLedger> queryDealerPostSalesLedger(Integer workOrderNo, String salesDate, Double mileage,
            String lpNumber, String customerName, String color, String frameNo, String model, String enterFactoryDate,
            String exitFactoryDate, String customerType, String insuranceAgengcy, String insuranceDueDate,
            Integer insuranceClaimNumber, Integer dealerID, Integer marker, Integer limit);

    Collection<DealerPostSalesLedger> getDealerPostSalesLedger(Integer workOrderNo);

}