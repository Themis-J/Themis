package com.jdc.themis.dealer.web.domain;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.google.common.collect.Lists;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ReportDataDealerPostSalesDetail implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer id;
	private String name;
	private String code;
	private String brand;
	private ReportDataDetailAmount revenue = new ReportDataDetailAmount();
	private ReportDataDetailAmount margin = new ReportDataDetailAmount();
	private ReportDataDetailAmount expense = new ReportDataDetailAmount();
	private ReportDataDetailAmount opProfit = new ReportDataDetailAmount();
	private List<ReportDataDepartmentDetail> detail = Lists.newArrayList();

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

	public ReportDataDetailAmount getExpense() {
		return expense;
	}

	public void setExpense(ReportDataDetailAmount expense) {
		this.expense = expense;
	}

	public ReportDataDetailAmount getOpProfit() {
		return opProfit;
	}

	public void setOpProfit(ReportDataDetailAmount opProfit) {
		this.opProfit = opProfit;
	}

	public List<ReportDataDepartmentDetail> getDetail() {
		return detail;
	}

	public void setDetail(List<ReportDataDepartmentDetail> detail) {
		this.detail = detail;
	}

}
