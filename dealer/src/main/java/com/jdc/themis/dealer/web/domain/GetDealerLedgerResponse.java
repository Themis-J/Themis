package com.jdc.themis.dealer.web.domain;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.google.common.collect.Lists;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class GetDealerLedgerResponse implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private final List<List<String>> summaries = Lists
			.newArrayList();

	public List<List<String>> getSummaries() {
		return summaries;
	}

}
