package com.jdc.themis.dealer.web.domain;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.google.common.collect.Lists;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class GetDealerLedgerMetadataResponse implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Integer dealerID;
	
	private final List<DealerLedgerMetadataDetail> vehicleSalesLedger = Lists.newArrayList();
	
	private final List<DealerLedgerMetadataDetail> postSalesLedger = Lists.newArrayList();

	public Integer getDealerID() {
		return dealerID;
	}

	public void setDealerID(Integer dealerID) {
		this.dealerID = dealerID;
	}

	public List<DealerLedgerMetadataDetail> getVehicleSalesLedger() {
		return vehicleSalesLedger;
	}

	public List<DealerLedgerMetadataDetail> getPostSalesLedger() {
		return postSalesLedger;
	}

}
