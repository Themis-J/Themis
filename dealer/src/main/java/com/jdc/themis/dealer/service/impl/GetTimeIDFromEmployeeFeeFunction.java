package com.jdc.themis.dealer.service.impl;

import com.google.common.base.Function;
import com.jdc.themis.dealer.domain.DealerEmployeeFeeFact;

public enum GetTimeIDFromEmployeeFeeFunction implements
		Function<DealerEmployeeFeeFact, Long> {

	INSTANCE;

	@Override
	public Long apply(final DealerEmployeeFeeFact item) {
		return item.getTimeID();
	}

}