package com.jdc.themis.dealer.web.domain;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ReportDataDealerPostSalesDepartmentIncomeDetail implements
		Serializable {

	private static final long serialVersionUID = 1L;
	private Integer id;
	private String name;
	private String code;
	private String brand;
	private ReportDataDetailAmount revenue = new ReportDataDetailAmount();
	private ReportDataDetailAmount margin = new ReportDataDetailAmount();
	private ReportDataDetailAmount count = new ReportDataDetailAmount();

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public ReportDataDetailAmount getRevenue() {
		return revenue;
	}

	public void setRevenue(ReportDataDetailAmount revenue) {
		this.revenue = revenue;
	}

	public ReportDataDetailAmount getMargin() {
		return margin;
	}

	public void setMargin(ReportDataDetailAmount margin) {
		this.margin = margin;
	}

	public ReportDataDetailAmount getCount() {
		return count;
	}

	public void setCount(ReportDataDetailAmount count) {
		this.count = count;
	}

}
