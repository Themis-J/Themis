package com.jdc.themis.dealer.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Version;
import javax.time.Instant;
import javax.time.calendar.LocalDate;

import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDefs;
import org.hibernate.annotations.Filters;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import com.jdc.themis.dealer.data.hibernate.type.PersistentLocalDate;
import com.jdc.themis.dealer.data.hibernate.type.PersistentTimestamp;

@FilterDefs({ @org.hibernate.annotations.FilterDef(name = "dealerVehicleSalesLedgerFilterSingleItem", parameters = {
		@org.hibernate.annotations.ParamDef(name = "referenceTime", type = "com.jdc.themis.dealer.data.hibernate.type.PersistentTimestamp"),
		@org.hibernate.annotations.ParamDef(name = "contractNo", type = "integer") }), })
@Filters({ @Filter(name = "dealerVehicleSalesLedgerFilterSingleItem", condition = "contractNo = :contractNo and timestamp < :referenceTime and timeEnd >= :referenceTime") })
@TypeDefs({ @TypeDef(name = "datetime", typeClass = PersistentTimestamp.class),
		@TypeDef(name = "localdate", typeClass = PersistentLocalDate.class) })
@Entity
public class DealerVehicleSalesLedger implements Serializable, TemporalEntity {

	private static final long serialVersionUID = 1L;

	public static final String FILTER_SINGLEITEM = "dealerVehicleSalesLedgerFilterSingleItem";

	@Id
	@Type(type = "datetime")
	private Instant timestamp;

	@Type(type = "datetime")
	private Instant timeEnd;

	@Type(type = "localdate")
	private LocalDate validDate;

	@Id
	private Integer contractNo;

	private Integer dealerID;

	private String model;

	private String type;

	private String color;

	private String lpNumber;

	private String frameNo;

	@Type(type = "localdate")
	private LocalDate manufacturerDebitDate;

	@Type(type = "localdate")
	private LocalDate warehousingDate;

	@Type(type = "localdate")
	private LocalDate salesDate;

	private Double guidingPrice = 0D;

	private String customerName;

	private String identificationNo;

	private String salesConsultant;

	private String customerType;

	private Double vehicleInvoiceAmount = 0D;

	private Double accessoryInvoiceAmount = 0D;

	private Double vehicleMarkup = 0D;

	private String invoiceType;

	@Type(type = "localdate")
	private LocalDate invoiceDate;

	private String posBank;

	private Double posCharge = 0D;

	private Double cash = 0D;

	private Double cheque = 0D;

	private Double transfer = 0D;

	private Double paidSubscription = 0D;

	private Double paidRemainder = 0D;

	private Double refund = 0D;

	private Double vehicleSalesRevenue = 0D;

	private Double vehicleSalesCost = 0D;

	private Double usedVehicleCharge = 0D;

	private Double usedVehicleCost = 0D;

	private Double usedVehicleSellingPrice = 0D;

	private String distributor;

	private Double usedVehicleManufacturerSubsidy = 0D;

	private Double accessorySalesRevenue = 0D;

	private Double accessorySalesCostBuyerPart = 0D;

	private Double accessorySalesCostGiftPart = 0D;

	private Double insuranceRebateMargin = 0D;

	private Double insuranceCost = 0D;

	private String insuranceAgency;

	private Double lpRevenue = 0D;

	private Double lpCost = 0D;

	private Double installmentCharge = 0D;

	private Double fcCommission = 0D;

	private Double installmentManufacturerSubsidy = 0D;

	private Double installmentOthers = 0D;

	private String installmentAgency;

	private Double extendedWarrantyRevenue = 0D;

	private Double extendedWarrantyCost = 0D;

	private Double financialLeasingMargin = 0D;

	private Double exitWarehouseMargin = 0D;

	private Double inspectionMargin = 0D;

	private Double memberCardRevenue = 0D;

	private Double memberCardCost = 0D;

	private Double otherMargin = 0D;

	private Double postSalesVoucherManHourPart = 0D;

	private Double postSalesVoucherDecorationPart = 0D;

	private Double postSalesVoucherAccessoryPart = 0D;

	private Double fuelCard = 0D;

	private Double gasoline = 0D;

	private Double otherGift = 0D;

	private Double inventoryDays = 0D;

	private Double financingRate = 0D;

	private Double managementFee = 0D;

	private Double monthlyRebate = 0D;

	private Double quarterlyRebate = 0D;

	private Double satisfactionRebate = 0D;

	private Double pickupRebate = 0D;

	private Double biddingSubsidy = 0D;

	private Double terminalSalesBonus = 0D;

	private Double otherRebate = 0D;

	private Double rebateAdjustment = 0D;

	private Double vehiclePushMoney = 0D;

	private Double accessoryPushMoney = 0D;

	private Double insurancePushMoney = 0D;

	private Double lpPushMoney = 0D;

	private Double installmentPushMoney = 0D;

	private Double extendedWarrantyPushMoney = 0D;

	private Double financialLeasingPushMoney = 0D;

	private Double memberCardPushMoney = 0D;

	private Double exchangePushMoney = 0D;

	private Double otherPushMoney = 0D;

	private Double pushMoneyDeduction = 0D;

	@Version
	private Integer version;

	private String updatedBy;

	public Instant getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Instant timestamp) {
		this.timestamp = timestamp;
	}

	public Instant getTimeEnd() {
		return timeEnd;
	}

	public void setTimeEnd(Instant timeEnd) {
		this.timeEnd = timeEnd;
	}

	public LocalDate getValidDate() {
		return validDate;
	}

	public void setValidDate(LocalDate validDate) {
		this.validDate = validDate;
	}

	public Integer getContractNo() {
		return contractNo;
	}

	public void setContractNo(Integer contractNo) {
		this.contractNo = contractNo;
	}

	public Integer getDealerID() {
		return dealerID;
	}

	public void setDealerID(Integer dealerID) {
		this.dealerID = dealerID;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getLpNumber() {
		return lpNumber;
	}

	public void setLpNumber(String lpNumber) {
		this.lpNumber = lpNumber;
	}

	public String getFrameNo() {
		return frameNo;
	}

	public void setFrameNo(String frameNo) {
		this.frameNo = frameNo;
	}

	public LocalDate getManufacturerDebitDate() {
		return manufacturerDebitDate;
	}

	public void setManufacturerDebitDate(LocalDate manufacturerDebitDate) {
		this.manufacturerDebitDate = manufacturerDebitDate;
	}

	public LocalDate getWarehousingDate() {
		return warehousingDate;
	}

	public void setWarehousingDate(LocalDate warehousingDate) {
		this.warehousingDate = warehousingDate;
	}

	public LocalDate getSalesDate() {
		return salesDate;
	}

	public void setSalesDate(LocalDate salesDate) {
		this.salesDate = salesDate;
	}

	public Double getGuidingPrice() {
		return guidingPrice;
	}

	public void setGuidingPrice(Double guidingPrice) {
		this.guidingPrice = guidingPrice;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getIdentificationNo() {
		return identificationNo;
	}

	public void setIdentificationNo(String identificationNo) {
		this.identificationNo = identificationNo;
	}

	public String getSalesConsultant() {
		return salesConsultant;
	}

	public void setSalesConsultant(String salesConsultant) {
		this.salesConsultant = salesConsultant;
	}

	public String getCustomerType() {
		return customerType;
	}

	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}

	public Double getVehicleInvoiceAmount() {
		return vehicleInvoiceAmount;
	}

	public void setVehicleInvoiceAmount(Double vehicleInvoiceAmount) {
		this.vehicleInvoiceAmount = vehicleInvoiceAmount;
	}

	public Double getAccessoryInvoiceAmount() {
		return accessoryInvoiceAmount;
	}

	public void setAccessoryInvoiceAmount(Double accessoryInvoiceAmount) {
		this.accessoryInvoiceAmount = accessoryInvoiceAmount;
	}

	public Double getVehicleMarkup() {
		return vehicleMarkup;
	}

	public void setVehicleMarkup(Double vehicleMarkup) {
		this.vehicleMarkup = vehicleMarkup;
	}

	public String getInvoiceType() {
		return invoiceType;
	}

	public void setInvoiceType(String invoiceType) {
		this.invoiceType = invoiceType;
	}

	public LocalDate getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(LocalDate invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	public String getPosBank() {
		return posBank;
	}

	public void setPosBank(String posBank) {
		this.posBank = posBank;
	}

	public Double getPosCharge() {
		return posCharge;
	}

	public void setPosCharge(Double posCharge) {
		this.posCharge = posCharge;
	}

	public Double getCash() {
		return cash;
	}

	public void setCash(Double cash) {
		this.cash = cash;
	}

	public Double getCheque() {
		return cheque;
	}

	public void setCheque(Double cheque) {
		this.cheque = cheque;
	}

	public Double getTransfer() {
		return transfer;
	}

	public void setTransfer(Double transfer) {
		this.transfer = transfer;
	}

	public Double getPaidSubscription() {
		return paidSubscription;
	}

	public void setPaidSubscription(Double paidSubscription) {
		this.paidSubscription = paidSubscription;
	}

	public Double getPaidRemainder() {
		return paidRemainder;
	}

	public void setPaidRemainder(Double paidRemainder) {
		this.paidRemainder = paidRemainder;
	}

	public Double getRefund() {
		return refund;
	}

	public void setRefund(Double refund) {
		this.refund = refund;
	}

	public Double getVehicleSalesRevenue() {
		return vehicleSalesRevenue;
	}

	public void setVehicleSalesRevenue(Double vehicleSalesRevenue) {
		this.vehicleSalesRevenue = vehicleSalesRevenue;
	}

	public Double getVehicleSalesCost() {
		return vehicleSalesCost;
	}

	public void setVehicleSalesCost(Double vehicleSalesCost) {
		this.vehicleSalesCost = vehicleSalesCost;
	}

	public Double getUsedVehicleCharge() {
		return usedVehicleCharge;
	}

	public void setUsedVehicleCharge(Double usedVehicleCharge) {
		this.usedVehicleCharge = usedVehicleCharge;
	}

	public Double getUsedVehicleCost() {
		return usedVehicleCost;
	}

	public void setUsedVehicleCost(Double usedVehicleCost) {
		this.usedVehicleCost = usedVehicleCost;
	}

	public Double getUsedVehicleSellingPrice() {
		return usedVehicleSellingPrice;
	}

	public void setUsedVehicleSellingPrice(Double usedVehicleSellingPrice) {
		this.usedVehicleSellingPrice = usedVehicleSellingPrice;
	}

	public String getDistributor() {
		return distributor;
	}

	public void setDistributor(String distributor) {
		this.distributor = distributor;
	}

	public Double getUsedVehicleManufacturerSubsidy() {
		return usedVehicleManufacturerSubsidy;
	}

	public void setUsedVehicleManufacturerSubsidy(
			Double usedVehicleManufacturerSubsidy) {
		this.usedVehicleManufacturerSubsidy = usedVehicleManufacturerSubsidy;
	}

	public Double getAccessorySalesRevenue() {
		return accessorySalesRevenue;
	}

	public void setAccessorySalesRevenue(Double accessorySalesRevenue) {
		this.accessorySalesRevenue = accessorySalesRevenue;
	}

	public Double getAccessorySalesCostBuyerPart() {
		return accessorySalesCostBuyerPart;
	}

	public void setAccessorySalesCostBuyerPart(
			Double accessorySalesCostBuyerPart) {
		this.accessorySalesCostBuyerPart = accessorySalesCostBuyerPart;
	}

	public Double getAccessorySalesCostGiftPart() {
		return accessorySalesCostGiftPart;
	}

	public void setAccessorySalesCostGiftPart(Double accessorySalesCostGiftPart) {
		this.accessorySalesCostGiftPart = accessorySalesCostGiftPart;
	}

	public Double getInsuranceRebateMargin() {
		return insuranceRebateMargin;
	}

	public void setInsuranceRebateMargin(Double insuranceRebateMargin) {
		this.insuranceRebateMargin = insuranceRebateMargin;
	}

	public Double getInsuranceCost() {
		return insuranceCost;
	}

	public void setInsuranceCost(Double insuranceCost) {
		this.insuranceCost = insuranceCost;
	}

	public String getInsuranceAgency() {
		return insuranceAgency;
	}

	public void setInsuranceAgency(String insuranceAgency) {
		this.insuranceAgency = insuranceAgency;
	}

	public Double getLpRevenue() {
		return lpRevenue;
	}

	public void setLpRevenue(Double lpRevenue) {
		this.lpRevenue = lpRevenue;
	}

	public Double getLpCost() {
		return lpCost;
	}

	public void setLpCost(Double lpCost) {
		this.lpCost = lpCost;
	}

	public Double getInstallmentCharge() {
		return installmentCharge;
	}

	public void setInstallmentCharge(Double installmentCharge) {
		this.installmentCharge = installmentCharge;
	}

	public Double getFcCommission() {
		return fcCommission;
	}

	public void setFcCommission(Double fcCommission) {
		this.fcCommission = fcCommission;
	}

	public Double getInstallmentManufacturerSubsidy() {
		return installmentManufacturerSubsidy;
	}

	public void setInstallmentManufacturerSubsidy(
			Double installmentManufacturerSubsidy) {
		this.installmentManufacturerSubsidy = installmentManufacturerSubsidy;
	}

	public Double getInstallmentOthers() {
		return installmentOthers;
	}

	public void setInstallmentOthers(Double installmentOthers) {
		this.installmentOthers = installmentOthers;
	}

	public String getInstallmentAgency() {
		return installmentAgency;
	}

	public void setInstallmentAgency(String installmentAgency) {
		this.installmentAgency = installmentAgency;
	}

	public Double getExtendedWarrantyRevenue() {
		return extendedWarrantyRevenue;
	}

	public void setExtendedWarrantyRevenue(Double extendedWarrantyRevenue) {
		this.extendedWarrantyRevenue = extendedWarrantyRevenue;
	}

	public Double getExtendedWarrantyCost() {
		return extendedWarrantyCost;
	}

	public void setExtendedWarrantyCost(Double extendedWarrantyCost) {
		this.extendedWarrantyCost = extendedWarrantyCost;
	}

	public Double getFinancialLeasingMargin() {
		return financialLeasingMargin;
	}

	public void setFinancialLeasingMargin(Double financialLeasingMargin) {
		this.financialLeasingMargin = financialLeasingMargin;
	}

	public Double getExitWarehouseMargin() {
		return exitWarehouseMargin;
	}

	public void setExitWarehouseMargin(Double exitWarehouseMargin) {
		this.exitWarehouseMargin = exitWarehouseMargin;
	}

	public Double getInspectionMargin() {
		return inspectionMargin;
	}

	public void setInspectionMargin(Double inspectionMargin) {
		this.inspectionMargin = inspectionMargin;
	}

	public Double getMemberCardRevenue() {
		return memberCardRevenue;
	}

	public void setMemberCardRevenue(Double memberCardRevenue) {
		this.memberCardRevenue = memberCardRevenue;
	}

	public Double getMemberCardCost() {
		return memberCardCost;
	}

	public void setMemberCardCost(Double memberCardCost) {
		this.memberCardCost = memberCardCost;
	}

	public Double getOtherMargin() {
		return otherMargin;
	}

	public void setOtherMargin(Double otherMargin) {
		this.otherMargin = otherMargin;
	}

	public Double getPostSalesVoucherManHourPart() {
		return postSalesVoucherManHourPart;
	}

	public void setPostSalesVoucherManHourPart(
			Double postSalesVoucherManHourPart) {
		this.postSalesVoucherManHourPart = postSalesVoucherManHourPart;
	}

	public Double getPostSalesVoucherDecorationPart() {
		return postSalesVoucherDecorationPart;
	}

	public void setPostSalesVoucherDecorationPart(
			Double postSalesVoucherDecorationPart) {
		this.postSalesVoucherDecorationPart = postSalesVoucherDecorationPart;
	}

	public Double getPostSalesVoucherAccessoryPart() {
		return postSalesVoucherAccessoryPart;
	}

	public void setPostSalesVoucherAccessoryPart(
			Double postSalesVoucherAccessoryPart) {
		this.postSalesVoucherAccessoryPart = postSalesVoucherAccessoryPart;
	}

	public Double getFuelCard() {
		return fuelCard;
	}

	public void setFuelCard(Double fuelCard) {
		this.fuelCard = fuelCard;
	}

	public Double getGasoline() {
		return gasoline;
	}

	public void setGasoline(Double gasoline) {
		this.gasoline = gasoline;
	}

	public Double getOtherGift() {
		return otherGift;
	}

	public void setOtherGift(Double otherGift) {
		this.otherGift = otherGift;
	}

	public Double getInventoryDays() {
		return inventoryDays;
	}

	public void setInventoryDays(Double inventoryDays) {
		this.inventoryDays = inventoryDays;
	}

	public Double getFinancingRate() {
		return financingRate;
	}

	public void setFinancingRate(Double financingRate) {
		this.financingRate = financingRate;
	}

	public Double getManagementFee() {
		return managementFee;
	}

	public void setManagementFee(Double managementFee) {
		this.managementFee = managementFee;
	}

	public Double getMonthlyRebate() {
		return monthlyRebate;
	}

	public void setMonthlyRebate(Double monthlyRebate) {
		this.monthlyRebate = monthlyRebate;
	}

	public Double getQuarterlyRebate() {
		return quarterlyRebate;
	}

	public void setQuarterlyRebate(Double quarterlyRebate) {
		this.quarterlyRebate = quarterlyRebate;
	}

	public Double getSatisfactionRebate() {
		return satisfactionRebate;
	}

	public void setSatisfactionRebate(Double satisfactionRebate) {
		this.satisfactionRebate = satisfactionRebate;
	}

	public Double getPickupRebate() {
		return pickupRebate;
	}

	public void setPickupRebate(Double pickupRebate) {
		this.pickupRebate = pickupRebate;
	}

	public Double getBiddingSubsidy() {
		return biddingSubsidy;
	}

	public void setBiddingSubsidy(Double biddingSubsidy) {
		this.biddingSubsidy = biddingSubsidy;
	}

	public Double getTerminalSalesBonus() {
		return terminalSalesBonus;
	}

	public void setTerminalSalesBonus(Double terminalSalesBonus) {
		this.terminalSalesBonus = terminalSalesBonus;
	}

	public Double getOtherRebate() {
		return otherRebate;
	}

	public void setOtherRebate(Double otherRebate) {
		this.otherRebate = otherRebate;
	}

	public Double getRebateAdjustment() {
		return rebateAdjustment;
	}

	public void setRebateAdjustment(Double rebateAdjustment) {
		this.rebateAdjustment = rebateAdjustment;
	}

	public Double getVehiclePushMoney() {
		return vehiclePushMoney;
	}

	public void setVehiclePushMoney(Double vehiclePushMoney) {
		this.vehiclePushMoney = vehiclePushMoney;
	}

	public Double getAccessoryPushMoney() {
		return accessoryPushMoney;
	}

	public void setAccessoryPushMoney(Double accessoryPushMoney) {
		this.accessoryPushMoney = accessoryPushMoney;
	}

	public Double getInsurancePushMoney() {
		return insurancePushMoney;
	}

	public void setInsurancePushMoney(Double insurancePushMoney) {
		this.insurancePushMoney = insurancePushMoney;
	}

	public Double getLpPushMoney() {
		return lpPushMoney;
	}

	public void setLpPushMoney(Double lpPushMoney) {
		this.lpPushMoney = lpPushMoney;
	}

	public Double getInstallmentPushMoney() {
		return installmentPushMoney;
	}

	public void setInstallmentPushMoney(Double installmentPushMoney) {
		this.installmentPushMoney = installmentPushMoney;
	}

	public Double getExtendedWarrantyPushMoney() {
		return extendedWarrantyPushMoney;
	}

	public void setExtendedWarrantyPushMoney(Double extendedWarrantyPushMoney) {
		this.extendedWarrantyPushMoney = extendedWarrantyPushMoney;
	}

	public Double getFinancialLeasingPushMoney() {
		return financialLeasingPushMoney;
	}

	public void setFinancialLeasingPushMoney(Double financialLeasingPushMoney) {
		this.financialLeasingPushMoney = financialLeasingPushMoney;
	}

	public Double getMemberCardPushMoney() {
		return memberCardPushMoney;
	}

	public void setMemberCardPushMoney(Double memberCardPushMoney) {
		this.memberCardPushMoney = memberCardPushMoney;
	}

	public Double getExchangePushMoney() {
		return exchangePushMoney;
	}

	public void setExchangePushMoney(Double exchangePushMoney) {
		this.exchangePushMoney = exchangePushMoney;
	}

	public Double getOtherPushMoney() {
		return otherPushMoney;
	}

	public void setOtherPushMoney(Double otherPushMoney) {
		this.otherPushMoney = otherPushMoney;
	}

	public Double getPushMoneyDeduction() {
		return pushMoneyDeduction;
	}

	public void setPushMoneyDeduction(Double pushMoneyDeduction) {
		this.pushMoneyDeduction = pushMoneyDeduction;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

}
