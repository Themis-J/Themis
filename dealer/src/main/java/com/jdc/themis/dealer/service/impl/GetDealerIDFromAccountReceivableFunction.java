package com.jdc.themis.dealer.service.impl;

import com.google.common.base.Function;
import com.jdc.themis.dealer.domain.DealerAccountReceivableFact;

public enum GetDealerIDFromAccountReceivableFunction implements
		Function<DealerAccountReceivableFact, Integer> {
	INSTANCE;

	@Override
	public Integer apply(final DealerAccountReceivableFact item) {
		return item.getDealerID();
	}
}