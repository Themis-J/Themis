package com.jdc.themis.dealer.web.domain;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.google.common.collect.Lists;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ReportDataDealerAftersaleExpenseDetail implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer id;
	private String name;
	private String code;
	private String brand;
	private ReportDataDetailAmount expense = new ReportDataDetailAmount();
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

	public ReportDataDetailAmount getExpense() {
		return expense;
	}

	public void setExpense(ReportDataDetailAmount expense) {
		this.expense = expense;
	}

	public List<ReportDataDepartmentDetail> getDetail() {
		return detail;
	}

	public void setDetail(List<ReportDataDepartmentDetail> detail) {
		this.detail = detail;
	}

}
