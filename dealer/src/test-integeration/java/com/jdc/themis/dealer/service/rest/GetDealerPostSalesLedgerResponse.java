package com.jdc.themis.dealer.service.rest;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.google.common.collect.Lists;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class GetDealerPostSalesLedgerResponse implements Serializable {

	private static final long serialVersionUID = 1L;

	private final List<DealerPostSalesLedgerDetail> postSalesLedger = Lists
			.newArrayList();

	public List<DealerPostSalesLedgerDetail> getPostSalesLedger() {
		return postSalesLedger;
	}

}
