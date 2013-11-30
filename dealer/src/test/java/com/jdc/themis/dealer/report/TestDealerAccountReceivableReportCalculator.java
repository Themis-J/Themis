package com.jdc.themis.dealer.report;

import java.math.BigDecimal;
import java.util.Collection;

import javax.time.calendar.LocalDate;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimaps;
import com.jdc.themis.dealer.domain.DealerAccountReceivableFact;
import com.jdc.themis.dealer.domain.ReportTime;
import com.jdc.themis.dealer.service.impl.GetDealerIDFromAccountReceivableFunction;
import com.jdc.themis.dealer.web.domain.DealerDetail;
import com.jdc.themis.dealer.web.domain.GetDealerResponse;
import com.jdc.themis.dealer.web.domain.ReportDealerAccountReceivableDataList;

public class TestDealerAccountReceivableReportCalculator {

	private Collection<DealerDetail> dealers;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks( this );
		
		final DealerDetail dealer1 = new DealerDetail();
		dealer1.setId(1);
		dealer1.setName("Dealer1");
		final DealerDetail dealer2 = new DealerDetail();
		dealer2.setId(2);
		dealer2.setName("Dealer2");
		final DealerDetail dealer3 = new DealerDetail();
		dealer3.setId(3);
		dealer3.setName("Dealer3");
		final DealerDetail dealer4 = new DealerDetail();
		dealer4.setId(4);
		dealer4.setName("Dealer4");
		final DealerDetail dealer5 = new DealerDetail();
		dealer5.setId(5);
		dealer5.setName("Dealer5");
		final DealerDetail dealer6 = new DealerDetail();
		dealer6.setId(6);
		dealer6.setName("Dealer6");
		final DealerDetail dealer7 = new DealerDetail();
		dealer7.setId(7);
		dealer7.setName("Dealer7");
		final DealerDetail dealer8 = new DealerDetail();
		dealer8.setId(8);
		dealer8.setName("Dealer8");
		final DealerDetail dealer9 = new DealerDetail();
		dealer9.setId(9);
		dealer9.setName("Dealer9");
		final DealerDetail dealer10 = new DealerDetail();
		dealer10.setId(10);
		dealer10.setName("Dealer10");
		final DealerDetail dealer11 = new DealerDetail();
		dealer11.setId(11);
		dealer11.setName("Dealer11");
		final DealerDetail dealer12 = new DealerDetail();
		dealer12.setId(12);
		dealer12.setName("Dealer12");
		dealers = Lists.newArrayList(dealer1, dealer2, dealer3, dealer4,
				dealer5, dealer6, dealer7, dealer8, dealer9, dealer10,
				dealer11, dealer12);
		
	}

	@Test
	public void calc() {
		final DealerAccountReceivableReportCalculator calc = new DealerAccountReceivableReportCalculator(dealers, 2013, 8);
		final ReportTime time201308 = new ReportTime();
		time201308.setId(1L);
		time201308.setMonthOfYear(8);
		time201308.setYear(2013);
		time201308.setValidDate(LocalDate.of(2013, 8, 1));

		final DealerAccountReceivableFact fact1 = new DealerAccountReceivableFact();
		fact1.setTimeID(time201308.getId());
		fact1.setDealerID(1);
		fact1.setItemID(1L);
		fact1.setAmount(new BigDecimal("1000.0"));
		// skip timestamp and time end
		final DealerAccountReceivableFact fact2 = new DealerAccountReceivableFact();
		fact2.setTimeID(time201308.getId());
		fact2.setDealerID(1);
		fact2.setItemID(1L);
		fact2.setAmount(new BigDecimal("2000.0"));
		final DealerAccountReceivableFact fact3 = new DealerAccountReceivableFact();
		fact3.setTimeID(time201308.getId());
		fact3.setDealerID(2);
		fact3.setItemID(2L);
		fact3.setAmount(new BigDecimal("2020.0"));
		
		final GetDealerResponse dealersResponse = new GetDealerResponse();
		dealersResponse.getItems().addAll(dealers);
		
		final ImmutableListMultimap<Integer, DealerAccountReceivableFact> dealerFacts = Multimaps
				.index(Lists.newArrayList(fact1, fact2, fact3),
						GetDealerIDFromAccountReceivableFunction.INSTANCE);

		final ReportDealerAccountReceivableDataList detail = calc.calc(dealerFacts).getReportDetail();
		System.out.println(detail);
		Assert.assertNotNull(detail);
		Assert.assertEquals(fact1.getAmount().add(fact2.getAmount()).doubleValue(), detail.getDetail().get(0).getAmount().getAmount());
	}
	
	
}
