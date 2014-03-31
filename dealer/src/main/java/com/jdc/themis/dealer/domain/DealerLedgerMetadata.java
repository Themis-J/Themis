package com.jdc.themis.dealer.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDefs;
import org.hibernate.annotations.Filters;

@FilterDefs({ @org.hibernate.annotations.FilterDef(name = "dealerLedgerMetadataFilter", parameters = {
		@org.hibernate.annotations.ParamDef(name = "categoryID", type = "integer"),
		@org.hibernate.annotations.ParamDef(name = "dealerID", type = "integer") }), })
@Filters({ @Filter(name = "dealerLedgerMetadataFilter", condition = "dealerID = :dealerID and categoryID = :categoryID") })
@Entity
public class DealerLedgerMetadata implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public static final String FILTER = "dealerLedgerMetadataFilter";
	
	@Id
	private Integer id;
	
	private String name;
	
	private String type;
	
	private String options;
	
	private Integer dealerID;
	
	private Integer categoryID;
	
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getOptions() {
		return options;
	}

	public void setOptions(String options) {
		this.options = options;
	}

	public Integer getDealerID() {
		return dealerID;
	}

	public void setDealerID(Integer dealerID) {
		this.dealerID = dealerID;
	}

	public Integer getCategoryID() {
		return categoryID;
	}

	public void setCategoryID(Integer categoryID) {
		this.categoryID = categoryID;
	}

}
