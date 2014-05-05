package com.jdc.themis.dealer.web.domain;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class DealerVehicleSalesLedgerDetail implements Serializable {

	private static final long serialVersionUID = 1L;

	private String contractNo;

	private Integer dealerID;

	private String model;

	private String type;

	private String color;

	private String lpNumber;

	private String frameNo;

	private String manufacturerDebitDate;

	private String warehousingDate;

	private String salesDate;

	private Double guidingPrice;

	private String customerName;

	private String identificationNo;

	private String salesConsultant;

	private String customerType;

	private Double vehicleInvoiceAmount;

	private Double accessoryInvoiceAmount;

	private Double vehicleMarkup;

	private String invoiceType;

	private String invoiceDate;

	private String posBank;

	private Double posCharge;

	private Double cash;

	private Double cheque;

	private Double transfer;

	private Double paidSubscription;

	private Double paidRemainder;

	private Double refund;

	private Double vehicleSalesRevenue;

	private Double vehicleSalesCost;

	private Double usedVehicleCharge;

	private Double usedVehicleCost;

	private Double usedVehicleSellingPrice;

	private String distributor;

	private Double usedVehicleManufacturerSubsidy;

	private Double accessorySalesRevenue;

	private Double accessorySalesCostBuyerPart;

	private Double accessorySalesCostGiftPart;

	private Double insuranceRebateMargin;

	private Double insuranceCost;

	private String insuranceAgency;

	private Double lpRevenue;

	private Double lpCost;

	private Double installmentCharge;

	private Double fcCommission;

	private Double installmentManufacturerSubsidy;

	private Double installmentOthers;

	private String installmentAgency;

	private Double extendedWarrantyRevenue;

	private Double extendedWarrantyCost;

	private Double financialLeasingMargin;

	private Double exitWarehouseMargin;

	private Double inspectionMargin;

	private Double memberCardRevenue;

	private Double memberCardCost;

	private Double otherMargin;

	private Double postSalesVoucherManHourPart;

	private Double postSalesVoucherDecorationPart;

	private Double postSalesVoucherAccessoryPart;

	private Double fuelCard;

	private Double gasoline;

	private Double otherGift;

	private Double inventoryDays;

	private Double financingRate;

	private Double managementFee;

	private Double monthlyRebate;

	private Double quarterlyRebate;

	private Double satisfactionRebate;

	private Double pickupRebate;

	private Double biddingSubsidy;

	private Double terminalSalesBonus;

	private Double otherRebate;

	private Double rebateAdjustment;

	private Double vehiclePushMoney;

	private Double accessoryPushMoney;

	private Double insurancePushMoney;

	private Double lpPushMoney;

	private Double installmentPushMoney;

	private Double extendedWarrantyPushMoney;

	private Double financialLeasingPushMoney;

	private Double memberCardPushMoney;

	private Double exchangePushMoney;

	private Double otherPushMoney;

	private Double pushMoneyDeduction;

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
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

	public String getManufacturerDebitDate() {
		return manufacturerDebitDate;
	}

	public void setManufacturerDebitDate(String manufacturerDebitDate) {
		this.manufacturerDebitDate = manufacturerDebitDate;
	}

	public String getWarehousingDate() {
		return warehousingDate;
	}

	public void setWarehousingDate(String warehousingDate) {
		this.warehousingDate = warehousingDate;
	}

	public String getSalesDate() {
		return salesDate;
	}

	public void setSalesDate(String salesDate) {
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

	public String getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(String invoiceDate) {
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

	public void setAccessorySalesCostBuyerPart(Double accessorySalesCostBuyerPart) {
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

	public void setPostSalesVoucherManHourPart(Double postSalesVoucherManHourPart) {
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

}
