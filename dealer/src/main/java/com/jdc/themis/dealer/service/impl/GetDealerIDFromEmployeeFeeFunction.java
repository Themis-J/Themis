package com.jdc.themis.dealer.service.impl;

import com.google.common.base.Function;
import com.jdc.themis.dealer.domain.DealerEmployeeFeeFact;

public enum GetDealerIDFromEmployeeFeeFunction implements
		Function<DealerEmployeeFeeFact, Integer> {

	INSTANCE;

	@Override
	public Integer apply(final DealerEmployeeFeeFact item) {
		return item.getDealerID();
	}

}