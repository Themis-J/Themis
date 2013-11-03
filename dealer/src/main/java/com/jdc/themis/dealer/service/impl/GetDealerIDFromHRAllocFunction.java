package com.jdc.themis.dealer.service.impl;

import com.google.common.base.Function;
import com.jdc.themis.dealer.domain.DealerHRAllocationFact;

public enum GetDealerIDFromHRAllocFunction implements
		Function<DealerHRAllocationFact, Integer> {
	INSTANCE;

	@Override
	public Integer apply(final DealerHRAllocationFact item) {
		return item.getDealerID();
	}
}