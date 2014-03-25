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

@FilterDefs({ @org.hibernate.annotations.FilterDef(name = "dealerPostSalesLedgerFilterSingleItem", parameters = {
		@org.hibernate.annotations.ParamDef(name = "referenceTime", type = "com.jdc.themis.dealer.data.hibernate.type.PersistentTimestamp"),
		@org.hibernate.annotations.ParamDef(name = "workOrderNo", type = "integer") }), })
@Filters({ @Filter(name = "dealerPostSalesLedgerFilterSingleItem", condition = "workOrderNo = :workOrderNo and timestamp < :referenceTime and timeEnd >= :referenceTime") })
@TypeDefs({ @TypeDef(name = "datetime", typeClass = PersistentTimestamp.class),
		@TypeDef(name = "localdate", typeClass = PersistentLocalDate.class) })
@Entity
public class DealerPostSalesLedger implements Serializable, TemporalEntity {

	private static final long serialVersionUID = 1L;

	public static final String FILTER_SINGLEITEM = "dealerPostSalesLedgerFilterSingleItem";

	@Id
	@Type(type = "datetime")
	private Instant timestamp;

	@Type(type = "datetime")
	private Instant timeEnd;

	@Type(type = "localdate")
	private LocalDate validDate;

	@Id
	private Integer workOrderNo;

	@Id
	private Integer dealerID;

	@Type(type = "localdate")
	private LocalDate salesDate;

	private Double mileage = 0D;

	private String lpNumber;

	private String customerName;

	private String color;

	private String frameNo;

	private String model;

	@Type(type = "localdate")
	private LocalDate enterFactoryDate;

	@Type(type = "localdate")
	private LocalDate exitFactoryDate;

	private String customerType;

	private String insuranceAgengcy;

	@Type(type = "localdate")
	private LocalDate insuranceDueDate;

	private Integer insuranceClaimNumber = 0;

	private String maintenancePostSalesConsultant;

	private String maintenanceTechnician;

	private String maintenanceType;

	private String insuranceType;

	private String bodyShopPostSalesConsultant;

	private String sheetMetalTechinician;

	private String paintSprayTechinician;

	private Boolean mineralEngineOil = Boolean.FALSE;

	private Boolean synthesizedEngineOil = Boolean.FALSE;

	private Boolean airFilterElement = Boolean.FALSE;

	private Boolean airConditionerFilterElement = Boolean.FALSE;

	private Boolean gasolineFilterElement = Boolean.FALSE;

	private Boolean brakeFluid = Boolean.FALSE;

	private Boolean sparkPlug = Boolean.FALSE;

	private Boolean directionEngineOil = Boolean.FALSE;

	private Boolean transmissionOil = Boolean.FALSE;

	private Boolean coolingFluid = Boolean.FALSE;

	private Boolean timingChain = Boolean.FALSE;

	private Boolean lubricationSystemClean = Boolean.FALSE;

	private Boolean transmissionClean = Boolean.FALSE;

	private Boolean directionSystemClean = Boolean.FALSE;

	private Boolean coolingSystemClean = Boolean.FALSE;

	private Boolean airConditionerClean = Boolean.FALSE;

	private Boolean fuelSystemClean = Boolean.FALSE;

	private Boolean otherSystemClean = Boolean.FALSE;

	private Integer maintenanceProductAmount = 0;

	private Integer tyreCount = 0;

	private Integer batteryCount = 0;

	private Integer sparkPlugCount = 0;

	private Integer wiperBladeCount = 0;

	private Double maintenanceManHourRevenuePaidPart = 0D;

	private Double maintenanceManHourDiscountPaidPart = 0D;

	private Double maintenanceManHourCouponPaidPart = 0D;

	private Double maintenanceManHourCostPaidPart = 0D;

	private Double maintenanceManHourMarginPaidPart = 0D;

	private Double maintenanceManHourRevenueUnpaidPart = 0D;

	private Double maintenanceManHourCostUnpaidPart = 0D;

	private Double maintenanceManHourMarginUnpaidPart = 0D;

	private Double sheetMetalManHourRevenue = 0D;

	private Double paintSprayManHourRevenue = 0D;

	private Double bodyShopManHourDiscount = 0D;

	private Double bodyShopManHourCoupon = 0D;

	private Double bodyShopManHourCost = 0D;

	private Double bodyShopManHourMargin = 0D;

	private Double accessoryManHourRevenue = 0D;

	private Double accessoryManHourDiscount = 0D;

	private Double accessoryManHourCoupon = 0D;

	private Double accessoryManHourCost = 0D;

	private Double accessoryManHourMargin = 0D;

	private Double otherManHourRevenue = 0D;

	private Double otherManHourDiscount = 0D;

	private Double otherManHourCoupon = 0D;

	private Double otherManHourCost = 0D;

	private Double otherManHourMargin = 0D;

	private Double discountedManHourRevenue = 0D;

	private Double manHourMargin = 0D;

	private Double maintenancePartsRevenue = 0D;

	private Double maintenancePartsDiscount = 0D;

	private Double maintenancePartsCoupon = 0D;

	private Double maintenancePartsCost = 0D;

	private Double maintenancePartsMargin = 0D;

	private Double accessoryPargsRevenue = 0D;

	private Double accessoryPartsDiscount = 0D;

	private Double accessoryPartsCoupon = 0D;

	private Double accessoryPartsCost = 0D;

	private Double accessoryPartsMargin = 0D;

	private Double maintenanceProductRevenue = 0D;

	private Double maintenanceProductDiscount = 0D;

	private Double maintenanceProductCoupon = 0D;

	private Double maintenanceProductCost = 0D;

	private Double maintenanceProductMargin = 0D;

	private Double discountedPartsRevenue = 0D;

	private Double partsMargin = 0D;

	private Double vehicleDecorationRevenue = 0D;

	private Double vehicleDecorationDiscount = 0D;

	private Double vehicleDecorationCoupon = 0D;

	private Double vehicleDecorationCost = 0D;

	private Double vehicleDecorationMargin = 0D;

	private Double postSalesMargin = 0D;

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

	public Integer getWorkOrderNo() {
		return workOrderNo;
	}

	public void setWorkOrderNo(Integer workOrderNo) {
		this.workOrderNo = workOrderNo;
	}

	public Integer getDealerID() {
		return dealerID;
	}

	public void setDealerID(Integer dealerID) {
		this.dealerID = dealerID;
	}

	public LocalDate getSalesDate() {
		return salesDate;
	}

	public void setSalesDate(LocalDate salesDate) {
		this.salesDate = salesDate;
	}

	public Double getMileage() {
		return mileage;
	}

	public void setMileage(Double mileage) {
		this.mileage = mileage;
	}

	public String getLpNumber() {
		return lpNumber;
	}

	public void setLpNumber(String lpNumber) {
		this.lpNumber = lpNumber;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getFrameNo() {
		return frameNo;
	}

	public void setFrameNo(String frameNo) {
		this.frameNo = frameNo;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public LocalDate getEnterFactoryDate() {
		return enterFactoryDate;
	}

	public void setEnterFactoryDate(LocalDate enterFactoryDate) {
		this.enterFactoryDate = enterFactoryDate;
	}

	public LocalDate getExitFactoryDate() {
		return exitFactoryDate;
	}

	public void setExitFactoryDate(LocalDate exitFactoryDate) {
		this.exitFactoryDate = exitFactoryDate;
	}

	public String getCustomerType() {
		return customerType;
	}

	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}

	public String getInsuranceAgengcy() {
		return insuranceAgengcy;
	}

	public void setInsuranceAgengcy(String insuranceAgengcy) {
		this.insuranceAgengcy = insuranceAgengcy;
	}

	public LocalDate getInsuranceDueDate() {
		return insuranceDueDate;
	}

	public void setInsuranceDueDate(LocalDate insuranceDueDate) {
		this.insuranceDueDate = insuranceDueDate;
	}

	public Integer getInsuranceClaimNumber() {
		return insuranceClaimNumber;
	}

	public void setInsuranceClaimNumber(Integer insuranceClaimNumber) {
		this.insuranceClaimNumber = insuranceClaimNumber;
	}

	public String getMaintenancePostSalesConsultant() {
		return maintenancePostSalesConsultant;
	}

	public void setMaintenancePostSalesConsultant(
			String maintenancePostSalesConsultant) {
		this.maintenancePostSalesConsultant = maintenancePostSalesConsultant;
	}

	public String getMaintenanceTechnician() {
		return maintenanceTechnician;
	}

	public void setMaintenanceTechnician(String maintenanceTechnician) {
		this.maintenanceTechnician = maintenanceTechnician;
	}

	public String getMaintenanceType() {
		return maintenanceType;
	}

	public void setMaintenanceType(String maintenanceType) {
		this.maintenanceType = maintenanceType;
	}

	public String getInsuranceType() {
		return insuranceType;
	}

	public void setInsuranceType(String insuranceType) {
		this.insuranceType = insuranceType;
	}

	public String getBodyShopPostSalesConsultant() {
		return bodyShopPostSalesConsultant;
	}

	public void setBodyShopPostSalesConsultant(
			String bodyShopPostSalesConsultant) {
		this.bodyShopPostSalesConsultant = bodyShopPostSalesConsultant;
	}

	public String getSheetMetalTechinician() {
		return sheetMetalTechinician;
	}

	public void setSheetMetalTechinician(String sheetMetalTechinician) {
		this.sheetMetalTechinician = sheetMetalTechinician;
	}

	public String getPaintSprayTechinician() {
		return paintSprayTechinician;
	}

	public void setPaintSprayTechinician(String paintSprayTechinician) {
		this.paintSprayTechinician = paintSprayTechinician;
	}

	public Boolean getMineralEngineOil() {
		return mineralEngineOil;
	}

	public void setMineralEngineOil(Boolean mineralEngineOil) {
		this.mineralEngineOil = mineralEngineOil;
	}

	public Boolean getSynthesizedEngineOil() {
		return synthesizedEngineOil;
	}

	public void setSynthesizedEngineOil(Boolean synthesizedEngineOil) {
		this.synthesizedEngineOil = synthesizedEngineOil;
	}

	public Boolean getAirFilterElement() {
		return airFilterElement;
	}

	public void setAirFilterElement(Boolean airFilterElement) {
		this.airFilterElement = airFilterElement;
	}

	public Boolean getAirConditionerFilterElement() {
		return airConditionerFilterElement;
	}

	public void setAirConditionerFilterElement(
			Boolean airConditionerFilterElement) {
		this.airConditionerFilterElement = airConditionerFilterElement;
	}

	public Boolean getGasolineFilterElement() {
		return gasolineFilterElement;
	}

	public void setGasolineFilterElement(Boolean gasolineFilterElement) {
		this.gasolineFilterElement = gasolineFilterElement;
	}

	public Boolean getBrakeFluid() {
		return brakeFluid;
	}

	public void setBrakeFluid(Boolean brakeFluid) {
		this.brakeFluid = brakeFluid;
	}

	public Boolean getSparkPlug() {
		return sparkPlug;
	}

	public void setSparkPlug(Boolean sparkPlug) {
		this.sparkPlug = sparkPlug;
	}

	public Boolean getDirectionEngineOil() {
		return directionEngineOil;
	}

	public void setDirectionEngineOil(Boolean directionEngineOil) {
		this.directionEngineOil = directionEngineOil;
	}

	public Boolean getTransmissionOil() {
		return transmissionOil;
	}

	public void setTransmissionOil(Boolean transmissionOil) {
		this.transmissionOil = transmissionOil;
	}

	public Boolean getCoolingFluid() {
		return coolingFluid;
	}

	public void setCoolingFluid(Boolean coolingFluid) {
		this.coolingFluid = coolingFluid;
	}

	public Boolean getTimingChain() {
		return timingChain;
	}

	public void setTimingChain(Boolean timingChain) {
		this.timingChain = timingChain;
	}

	public Boolean getLubricationSystemClean() {
		return lubricationSystemClean;
	}

	public void setLubricationSystemClean(Boolean lubricationSystemClean) {
		this.lubricationSystemClean = lubricationSystemClean;
	}

	public Boolean getTransmissionClean() {
		return transmissionClean;
	}

	public void setTransmissionClean(Boolean transmissionClean) {
		this.transmissionClean = transmissionClean;
	}

	public Boolean getDirectionSystemClean() {
		return directionSystemClean;
	}

	public void setDirectionSystemClean(Boolean directionSystemClean) {
		this.directionSystemClean = directionSystemClean;
	}

	public Boolean getCoolingSystemClean() {
		return coolingSystemClean;
	}

	public void setCoolingSystemClean(Boolean coolingSystemClean) {
		this.coolingSystemClean = coolingSystemClean;
	}

	public Boolean getAirConditionerClean() {
		return airConditionerClean;
	}

	public void setAirConditionerClean(Boolean airConditionerClean) {
		this.airConditionerClean = airConditionerClean;
	}

	public Boolean getFuelSystemClean() {
		return fuelSystemClean;
	}

	public void setFuelSystemClean(Boolean fuelSystemClean) {
		this.fuelSystemClean = fuelSystemClean;
	}

	public Boolean getOtherSystemClean() {
		return otherSystemClean;
	}

	public void setOtherSystemClean(Boolean otherSystemClean) {
		this.otherSystemClean = otherSystemClean;
	}

	public Integer getMaintenanceProductAmount() {
		return maintenanceProductAmount;
	}

	public void setMaintenanceProductAmount(Integer maintenanceProductAmount) {
		this.maintenanceProductAmount = maintenanceProductAmount;
	}

	public Integer getTyreCount() {
		return tyreCount;
	}

	public void setTyreCount(Integer tyreCount) {
		this.tyreCount = tyreCount;
	}

	public Integer getBatteryCount() {
		return batteryCount;
	}

	public void setBatteryCount(Integer batteryCount) {
		this.batteryCount = batteryCount;
	}

	public Integer getSparkPlugCount() {
		return sparkPlugCount;
	}

	public void setSparkPlugCount(Integer sparkPlugCount) {
		this.sparkPlugCount = sparkPlugCount;
	}

	public Integer getWiperBladeCount() {
		return wiperBladeCount;
	}

	public void setWiperBladeCount(Integer wiperBladeCount) {
		this.wiperBladeCount = wiperBladeCount;
	}

	public Double getMaintenanceManHourRevenuePaidPart() {
		return maintenanceManHourRevenuePaidPart;
	}

	public void setMaintenanceManHourRevenuePaidPart(
			Double maintenanceManHourRevenuePaidPart) {
		this.maintenanceManHourRevenuePaidPart = maintenanceManHourRevenuePaidPart;
	}

	public Double getMaintenanceManHourDiscountPaidPart() {
		return maintenanceManHourDiscountPaidPart;
	}

	public void setMaintenanceManHourDiscountPaidPart(
			Double maintenanceManHourDiscountPaidPart) {
		this.maintenanceManHourDiscountPaidPart = maintenanceManHourDiscountPaidPart;
	}

	public Double getMaintenanceManHourCouponPaidPart() {
		return maintenanceManHourCouponPaidPart;
	}

	public void setMaintenanceManHourCouponPaidPart(
			Double maintenanceManHourCouponPaidPart) {
		this.maintenanceManHourCouponPaidPart = maintenanceManHourCouponPaidPart;
	}

	public Double getMaintenanceManHourCostPaidPart() {
		return maintenanceManHourCostPaidPart;
	}

	public void setMaintenanceManHourCostPaidPart(
			Double maintenanceManHourCostPaidPart) {
		this.maintenanceManHourCostPaidPart = maintenanceManHourCostPaidPart;
	}

	public Double getMaintenanceManHourMarginPaidPart() {
		return maintenanceManHourMarginPaidPart;
	}

	public void setMaintenanceManHourMarginPaidPart(
			Double maintenanceManHourMarginPaidPart) {
		this.maintenanceManHourMarginPaidPart = maintenanceManHourMarginPaidPart;
	}

	public Double getMaintenanceManHourRevenueUnpaidPart() {
		return maintenanceManHourRevenueUnpaidPart;
	}

	public void setMaintenanceManHourRevenueUnpaidPart(
			Double maintenanceManHourRevenueUnpaidPart) {
		this.maintenanceManHourRevenueUnpaidPart = maintenanceManHourRevenueUnpaidPart;
	}

	public Double getMaintenanceManHourCostUnpaidPart() {
		return maintenanceManHourCostUnpaidPart;
	}

	public void setMaintenanceManHourCostUnpaidPart(
			Double maintenanceManHourCostUnpaidPart) {
		this.maintenanceManHourCostUnpaidPart = maintenanceManHourCostUnpaidPart;
	}

	public Double getMaintenanceManHourMarginUnpaidPart() {
		return maintenanceManHourMarginUnpaidPart;
	}

	public void setMaintenanceManHourMarginUnpaidPart(
			Double maintenanceManHourMarginUnpaidPart) {
		this.maintenanceManHourMarginUnpaidPart = maintenanceManHourMarginUnpaidPart;
	}

	public Double getSheetMetalManHourRevenue() {
		return sheetMetalManHourRevenue;
	}

	public void setSheetMetalManHourRevenue(Double sheetMetalManHourRevenue) {
		this.sheetMetalManHourRevenue = sheetMetalManHourRevenue;
	}

	public Double getPaintSprayManHourRevenue() {
		return paintSprayManHourRevenue;
	}

	public void setPaintSprayManHourRevenue(Double paintSprayManHourRevenue) {
		this.paintSprayManHourRevenue = paintSprayManHourRevenue;
	}

	public Double getBodyShopManHourDiscount() {
		return bodyShopManHourDiscount;
	}

	public void setBodyShopManHourDiscount(Double bodyShopManHourDiscount) {
		this.bodyShopManHourDiscount = bodyShopManHourDiscount;
	}

	public Double getBodyShopManHourCoupon() {
		return bodyShopManHourCoupon;
	}

	public void setBodyShopManHourCoupon(Double bodyShopManHourCoupon) {
		this.bodyShopManHourCoupon = bodyShopManHourCoupon;
	}

	public Double getBodyShopManHourCost() {
		return bodyShopManHourCost;
	}

	public void setBodyShopManHourCost(Double bodyShopManHourCost) {
		this.bodyShopManHourCost = bodyShopManHourCost;
	}

	public Double getBodyShopManHourMargin() {
		return bodyShopManHourMargin;
	}

	public void setBodyShopManHourMargin(Double bodyShopManHourMargin) {
		this.bodyShopManHourMargin = bodyShopManHourMargin;
	}

	public Double getAccessoryManHourRevenue() {
		return accessoryManHourRevenue;
	}

	public void setAccessoryManHourRevenue(Double accessoryManHourRevenue) {
		this.accessoryManHourRevenue = accessoryManHourRevenue;
	}

	public Double getAccessoryManHourDiscount() {
		return accessoryManHourDiscount;
	}

	public void setAccessoryManHourDiscount(Double accessoryManHourDiscount) {
		this.accessoryManHourDiscount = accessoryManHourDiscount;
	}

	public Double getAccessoryManHourCoupon() {
		return accessoryManHourCoupon;
	}

	public void setAccessoryManHourCoupon(Double accessoryManHourCoupon) {
		this.accessoryManHourCoupon = accessoryManHourCoupon;
	}

	public Double getAccessoryManHourCost() {
		return accessoryManHourCost;
	}

	public void setAccessoryManHourCost(Double accessoryManHourCost) {
		this.accessoryManHourCost = accessoryManHourCost;
	}

	public Double getAccessoryManHourMargin() {
		return accessoryManHourMargin;
	}

	public void setAccessoryManHourMargin(Double accessoryManHourMargin) {
		this.accessoryManHourMargin = accessoryManHourMargin;
	}

	public Double getOtherManHourRevenue() {
		return otherManHourRevenue;
	}

	public void setOtherManHourRevenue(Double otherManHourRevenue) {
		this.otherManHourRevenue = otherManHourRevenue;
	}

	public Double getOtherManHourDiscount() {
		return otherManHourDiscount;
	}

	public void setOtherManHourDiscount(Double otherManHourDiscount) {
		this.otherManHourDiscount = otherManHourDiscount;
	}

	public Double getOtherManHourCoupon() {
		return otherManHourCoupon;
	}

	public void setOtherManHourCoupon(Double otherManHourCoupon) {
		this.otherManHourCoupon = otherManHourCoupon;
	}

	public Double getOtherManHourCost() {
		return otherManHourCost;
	}

	public void setOtherManHourCost(Double otherManHourCost) {
		this.otherManHourCost = otherManHourCost;
	}

	public Double getOtherManHourMargin() {
		return otherManHourMargin;
	}

	public void setOtherManHourMargin(Double otherManHourMargin) {
		this.otherManHourMargin = otherManHourMargin;
	}

	public Double getDiscountedManHourRevenue() {
		return discountedManHourRevenue;
	}

	public void setDiscountedManHourRevenue(Double discountedManHourRevenue) {
		this.discountedManHourRevenue = discountedManHourRevenue;
	}

	public Double getManHourMargin() {
		return manHourMargin;
	}

	public void setManHourMargin(Double manHourMargin) {
		this.manHourMargin = manHourMargin;
	}

	public Double getMaintenancePartsRevenue() {
		return maintenancePartsRevenue;
	}

	public void setMaintenancePartsRevenue(Double maintenancePartsRevenue) {
		this.maintenancePartsRevenue = maintenancePartsRevenue;
	}

	public Double getMaintenancePartsDiscount() {
		return maintenancePartsDiscount;
	}

	public void setMaintenancePartsDiscount(Double maintenancePartsDiscount) {
		this.maintenancePartsDiscount = maintenancePartsDiscount;
	}

	public Double getMaintenancePartsCoupon() {
		return maintenancePartsCoupon;
	}

	public void setMaintenancePartsCoupon(Double maintenancePartsCoupon) {
		this.maintenancePartsCoupon = maintenancePartsCoupon;
	}

	public Double getMaintenancePartsCost() {
		return maintenancePartsCost;
	}

	public void setMaintenancePartsCost(Double maintenancePartsCost) {
		this.maintenancePartsCost = maintenancePartsCost;
	}

	public Double getMaintenancePartsMargin() {
		return maintenancePartsMargin;
	}

	public void setMaintenancePartsMargin(Double maintenancePartsMargin) {
		this.maintenancePartsMargin = maintenancePartsMargin;
	}

	public Double getAccessoryPargsRevenue() {
		return accessoryPargsRevenue;
	}

	public void setAccessoryPargsRevenue(Double accessoryPargsRevenue) {
		this.accessoryPargsRevenue = accessoryPargsRevenue;
	}

	public Double getAccessoryPartsDiscount() {
		return accessoryPartsDiscount;
	}

	public void setAccessoryPartsDiscount(Double accessoryPartsDiscount) {
		this.accessoryPartsDiscount = accessoryPartsDiscount;
	}

	public Double getAccessoryPartsCoupon() {
		return accessoryPartsCoupon;
	}

	public void setAccessoryPartsCoupon(Double accessoryPartsCoupon) {
		this.accessoryPartsCoupon = accessoryPartsCoupon;
	}

	public Double getAccessoryPartsCost() {
		return accessoryPartsCost;
	}

	public void setAccessoryPartsCost(Double accessoryPartsCost) {
		this.accessoryPartsCost = accessoryPartsCost;
	}

	public Double getAccessoryPartsMargin() {
		return accessoryPartsMargin;
	}

	public void setAccessoryPartsMargin(Double accessoryPartsMargin) {
		this.accessoryPartsMargin = accessoryPartsMargin;
	}

	public Double getMaintenanceProductRevenue() {
		return maintenanceProductRevenue;
	}

	public void setMaintenanceProductRevenue(Double maintenanceProductRevenue) {
		this.maintenanceProductRevenue = maintenanceProductRevenue;
	}

	public Double getMaintenanceProductDiscount() {
		return maintenanceProductDiscount;
	}

	public void setMaintenanceProductDiscount(Double maintenanceProductDiscount) {
		this.maintenanceProductDiscount = maintenanceProductDiscount;
	}

	public Double getMaintenanceProductCoupon() {
		return maintenanceProductCoupon;
	}

	public void setMaintenanceProductCoupon(Double maintenanceProductCoupon) {
		this.maintenanceProductCoupon = maintenanceProductCoupon;
	}

	public Double getMaintenanceProductCost() {
		return maintenanceProductCost;
	}

	public void setMaintenanceProductCost(Double maintenanceProductCost) {
		this.maintenanceProductCost = maintenanceProductCost;
	}

	public Double getMaintenanceProductMargin() {
		return maintenanceProductMargin;
	}

	public void setMaintenanceProductMargin(Double maintenanceProductMargin) {
		this.maintenanceProductMargin = maintenanceProductMargin;
	}

	public Double getDiscountedPartsRevenue() {
		return discountedPartsRevenue;
	}

	public void setDiscountedPartsRevenue(Double discountedPartsRevenue) {
		this.discountedPartsRevenue = discountedPartsRevenue;
	}

	public Double getPartsMargin() {
		return partsMargin;
	}

	public void setPartsMargin(Double partsMargin) {
		this.partsMargin = partsMargin;
	}

	public Double getVehicleDecorationRevenue() {
		return vehicleDecorationRevenue;
	}

	public void setVehicleDecorationRevenue(Double vehicleDecorationRevenue) {
		this.vehicleDecorationRevenue = vehicleDecorationRevenue;
	}

	public Double getVehicleDecorationDiscount() {
		return vehicleDecorationDiscount;
	}

	public void setVehicleDecorationDiscount(Double vehicleDecorationDiscount) {
		this.vehicleDecorationDiscount = vehicleDecorationDiscount;
	}

	public Double getVehicleDecorationCoupon() {
		return vehicleDecorationCoupon;
	}

	public void setVehicleDecorationCoupon(Double vehicleDecorationCoupon) {
		this.vehicleDecorationCoupon = vehicleDecorationCoupon;
	}

	public Double getVehicleDecorationCost() {
		return vehicleDecorationCost;
	}

	public void setVehicleDecorationCost(Double vehicleDecorationCost) {
		this.vehicleDecorationCost = vehicleDecorationCost;
	}

	public Double getVehicleDecorationMargin() {
		return vehicleDecorationMargin;
	}

	public void setVehicleDecorationMargin(Double vehicleDecorationMargin) {
		this.vehicleDecorationMargin = vehicleDecorationMargin;
	}

	public Double getPostSalesMargin() {
		return postSalesMargin;
	}

	public void setPostSalesMargin(Double postSalesMargin) {
		this.postSalesMargin = postSalesMargin;
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
