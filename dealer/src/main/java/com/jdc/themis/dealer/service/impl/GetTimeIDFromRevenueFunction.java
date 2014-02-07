package com.jdc.themis.dealer.service.impl;

import com.google.common.base.Function;
import com.jdc.themis.dealer.domain.DealerIncomeRevenueFact;

public enum GetTimeIDFromRevenueFunction implements
		Function<DealerIncomeRevenueFact, Long> {

	INSTANCE;

	@Override
	public Long apply(final DealerIncomeRevenueFact item) {
		return item.getTimeID();
	}

}