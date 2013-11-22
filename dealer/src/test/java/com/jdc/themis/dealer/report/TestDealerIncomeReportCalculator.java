package com.jdc.themis.dealer.report;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Collection;

import javax.time.calendar.LocalDate;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimaps;
import com.jdc.themis.dealer.domain.DealerIncomeExpenseFact;
import com.jdc.themis.dealer.domain.DealerIncomeRevenueFact;
import com.jdc.themis.dealer.domain.ReportTime;
import com.jdc.themis.dealer.service.RefDataQueryService;
import com.jdc.themis.dealer.web.domain.DealerDetail;
import com.jdc.themis.dealer.web.domain.DepartmentDetail;
import com.jdc.themis.dealer.web.domain.GetDepartmentResponse;
import com.jdc.themis.dealer.web.domain.ReportDealerDataList;

import fj.data.Option;

public class TestDealerIncomeReportCalculator {

	private Collection<DealerDetail> dealers;
	private Collection<DepartmentDetail> departments;
	@Mock
	private RefDataQueryService refDataDAL;
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks( this );
		refDataDAL = mock(RefDataQueryService.class);
		
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
		
		final DepartmentDetail department1 = new DepartmentDetail();
		department1.setId(1);
		department1.setName("Department1");
		final DepartmentDetail department2 = new DepartmentDetail();
		department2.setId(2);
		department2.setName("Department2");
		final DepartmentDetail department3 = new DepartmentDetail();
		department3.setId(3);
		department3.setName("Department3");
		final DepartmentDetail department4 = new DepartmentDetail();
		department4.setId(4);
		department4.setName("Department4");
		final DepartmentDetail department5 = new DepartmentDetail();
		department5.setId(5);
		department5.setName("Department5");
		final DepartmentDetail department6 = new DepartmentDetail();
		department6.setId(6);
		department6.setName("Department6");
		final DepartmentDetail department7 = new DepartmentDetail();
		department7.setId(7);
		department7.setName("Department7");
		departments = Lists.newArrayList(department1, department2, department3, department4, department5, department6, department7);
	}

	@Test
	public void calcMarginForYearReport() {
		final GetDepartmentResponse departmentsResponse = new GetDepartmentResponse();
		departmentsResponse.getItems().addAll(departments);
		when(refDataDAL.getDepartments()).thenReturn(departmentsResponse);
		final DealerIncomeReportCalculator calc = new DealerIncomeReportCalculator(dealers, refDataDAL.getDepartments().getItems(), 2013);
		calc.withGroupBy(Option.<Integer>some(0));
		final ReportTime time201308 = new ReportTime();
		time201308.setId(1L);
		time201308.setMonthOfYear(8);
		time201308.setYear(2013);
		time201308.setValidDate(LocalDate.of(2013, 8, 1));

		final ReportTime time201307 = new ReportTime();
		time201307.setId(2L);
		time201307.setMonthOfYear(7);
		time201307.setYear(2013);
		time201307.setValidDate(LocalDate.of(2013, 7, 1));
		final DealerIncomeRevenueFact fact1 = new DealerIncomeRevenueFact();
		fact1.setTimeID(time201308.getId());
		fact1.setDealerID(1);
		fact1.setDepartmentID(1);
		fact1.setItemID(1L);
		fact1.setAmount(new BigDecimal("1000.0"));
		fact1.setMargin(new BigDecimal("2000.0"));
		fact1.setCount(20);
		// skip timestamp and time end
		final DealerIncomeRevenueFact fact2 = new DealerIncomeRevenueFact();
		fact2.setTimeID(time201308.getId());
		fact2.setDealerID(1);
		fact2.setDepartmentID(2);
		fact2.setItemID(1L);
		fact2.setAmount(new BigDecimal("2000.0"));
		fact2.setMargin(new BigDecimal("3000.0"));
		fact2.setCount(30);
		final DealerIncomeRevenueFact fact3 = new DealerIncomeRevenueFact();
		fact3.setTimeID(time201307.getId());
		fact3.setDealerID(2);
		fact3.setDepartmentID(2);
		fact3.setItemID(2L);
		fact3.setAmount(new BigDecimal("2020.0"));
		fact3.setMargin(new BigDecimal("3030.0"));
		fact3.setCount(40);

		final ImmutableListMultimap<Integer, DealerIncomeRevenueFact> dealerRevenueFacts = Multimaps
				.index(Lists.newArrayList(fact1, fact2, fact3),
						GetDealerIDFromRevenueFunction.INSTANCE);

		final ReportDealerDataList detail = calc.calcMargins(dealerRevenueFacts, JournalOp.SUM, refDataDAL).getReportDetail();
		System.out.println(detail);
		Assert.assertNotNull(detail);
		Assert.assertEquals(fact1.getMargin().add(fact2.getMargin()).doubleValue(), detail.getDetail().get(0).getMargin().getAmount());
		Assert.assertEquals((fact3.getMargin().doubleValue()) / 9.0, detail.getDetail().get(0).getMargin().getReference());
	}
	
	@Test
	public void calcRevenueForYearReport() {
		final GetDepartmentResponse departmentsResponse = new GetDepartmentResponse();
		departmentsResponse.getItems().addAll(departments);
		when(refDataDAL.getDepartments()).thenReturn(departmentsResponse);
		final DealerIncomeReportCalculator calc = new DealerIncomeReportCalculator(dealers, refDataDAL.getDepartments().getItems(), 2013);
		calc.withGroupBy(Option.<Integer>some(0));
		final ReportTime time201308 = new ReportTime();
		time201308.setId(1L);
		time201308.setMonthOfYear(8);
		time201308.setYear(2013);
		time201308.setValidDate(LocalDate.of(2013, 8, 1));

		final ReportTime time201307 = new ReportTime();
		time201307.setId(2L);
		time201307.setMonthOfYear(7);
		time201307.setYear(2013);
		time201307.setValidDate(LocalDate.of(2013, 7, 1));
		final DealerIncomeRevenueFact fact1 = new DealerIncomeRevenueFact();
		fact1.setTimeID(time201308.getId());
		fact1.setDealerID(1);
		fact1.setDepartmentID(1);
		fact1.setItemID(1L);
		fact1.setAmount(new BigDecimal("1000.0"));
		fact1.setMargin(new BigDecimal("2000.0"));
		fact1.setCount(20);
		// skip timestamp and time end
		final DealerIncomeRevenueFact fact2 = new DealerIncomeRevenueFact();
		fact2.setTimeID(time201308.getId());
		fact2.setDealerID(1);
		fact2.setDepartmentID(2);
		fact2.setItemID(1L);
		fact2.setAmount(new BigDecimal("2000.0"));
		fact2.setMargin(new BigDecimal("3000.0"));
		fact2.setCount(30);
		final DealerIncomeRevenueFact fact3 = new DealerIncomeRevenueFact();
		fact3.setTimeID(time201307.getId());
		fact3.setDealerID(2);
		fact3.setDepartmentID(2);
		fact3.setItemID(2L);
		fact3.setAmount(new BigDecimal("2020.0"));
		fact3.setMargin(new BigDecimal("3030.0"));
		fact3.setCount(40);

		final ImmutableListMultimap<Integer, DealerIncomeRevenueFact> dealerRevenueFacts = Multimaps
				.index(Lists.newArrayList(fact1, fact2, fact3),
						GetDealerIDFromRevenueFunction.INSTANCE);

		final ReportDealerDataList detail = calc.calcRevenues(dealerRevenueFacts, JournalOp.SUM, refDataDAL).getReportDetail();
		System.out.println(detail);
		Assert.assertNotNull(detail);
		Assert.assertEquals(fact1.getAmount().add(fact2.getAmount()).doubleValue(), detail.getDetail().get(0).getRevenue().getAmount());
		Assert.assertEquals((fact3.getAmount().doubleValue()) / 9.0, detail.getDetail().get(0).getRevenue().getReference());
	}
	
	@Test
	public void calcExpenseForYearReport() {
		final GetDepartmentResponse departmentsResponse = new GetDepartmentResponse();
		departmentsResponse.getItems().addAll(departments);
		when(refDataDAL.getDepartments()).thenReturn(departmentsResponse);
		final DealerIncomeReportCalculator calc = new DealerIncomeReportCalculator(dealers, refDataDAL.getDepartments().getItems(), 2013);
		calc.withGroupBy(Option.<Integer>some(0));
		final ReportTime time201308 = new ReportTime();
		time201308.setId(1L);
		time201308.setMonthOfYear(8);
		time201308.setYear(2013);
		time201308.setValidDate(LocalDate.of(2013, 8, 1));

		final ReportTime time201307 = new ReportTime();
		time201307.setId(2L);
		time201307.setMonthOfYear(7);
		time201307.setYear(2013);
		time201307.setValidDate(LocalDate.of(2013, 7, 1));
		final DealerIncomeExpenseFact fact1 = new DealerIncomeExpenseFact();
		fact1.setTimeID(time201308.getId());
		fact1.setDealerID(1);
		fact1.setDepartmentID(1);
		fact1.setItemID(1L);
		fact1.setAmount(new BigDecimal("1000.0"));
		// skip timestamp and time end
		final DealerIncomeExpenseFact fact2 = new DealerIncomeExpenseFact();
		fact2.setTimeID(time201308.getId());
		fact2.setDealerID(1);
		fact2.setDepartmentID(2);
		fact2.setItemID(1L);
		fact2.setAmount(new BigDecimal("2000.0"));
		final DealerIncomeExpenseFact fact3 = new DealerIncomeExpenseFact();
		fact3.setTimeID(time201307.getId());
		fact3.setDealerID(2);
		fact3.setDepartmentID(2);
		fact3.setItemID(2L);
		fact3.setAmount(new BigDecimal("2020.0"));
		
		final ImmutableListMultimap<Integer, DealerIncomeExpenseFact> dealerExpenseFacts = Multimaps
				.index(Lists.newArrayList(fact1, fact2, fact3),
						GetDealerIDFromExpenseFunction.INSTANCE);

		final ReportDealerDataList detail = calc.calcExpenses(dealerExpenseFacts, JournalOp.SUM, refDataDAL).getReportDetail();
		System.out.println(detail);
		Assert.assertNotNull(detail);
		Assert.assertEquals(fact1.getAmount().add(fact2.getAmount()).doubleValue(), detail.getDetail().get(0).getExpense().getAmount());
		Assert.assertEquals((fact3.getAmount().doubleValue()) / 9.0, detail.getDetail().get(0).getExpense().getReference());
	}
	
	@Test
	public void calcOpProfitForYearReport() {
		final GetDepartmentResponse departmentsResponse = new GetDepartmentResponse();
		departmentsResponse.getItems().addAll(departments);
		when(refDataDAL.getDepartments()).thenReturn(departmentsResponse);
		final DealerIncomeReportCalculator calc = new DealerIncomeReportCalculator(dealers, refDataDAL.getDepartments().getItems(), 2013);
		calc.withGroupBy(Option.<Integer>some(0));
		final ReportTime time201308 = new ReportTime();
		time201308.setId(1L);
		time201308.setMonthOfYear(8);
		time201308.setYear(2013);
		time201308.setValidDate(LocalDate.of(2013, 8, 1));

		final ReportTime time201307 = new ReportTime();
		time201307.setId(2L);
		time201307.setMonthOfYear(7);
		time201307.setYear(2013);
		time201307.setValidDate(LocalDate.of(2013, 7, 1));
		final DealerIncomeExpenseFact fact1 = new DealerIncomeExpenseFact();
		fact1.setTimeID(time201308.getId());
		fact1.setDealerID(1);
		fact1.setDepartmentID(1);
		fact1.setItemID(1L);
		fact1.setAmount(new BigDecimal("1000.0"));
		// skip timestamp and time end
		final DealerIncomeExpenseFact fact2 = new DealerIncomeExpenseFact();
		fact2.setTimeID(time201308.getId());
		fact2.setDealerID(1);
		fact2.setDepartmentID(2);
		fact2.setItemID(1L);
		fact2.setAmount(new BigDecimal("2000.0"));
		final DealerIncomeExpenseFact fact3 = new DealerIncomeExpenseFact();
		fact3.setTimeID(time201307.getId());
		fact3.setDealerID(2);
		fact3.setDepartmentID(2);
		fact3.setItemID(2L);
		fact3.setAmount(new BigDecimal("2020.0"));
		
		final DealerIncomeRevenueFact fact11 = new DealerIncomeRevenueFact();
		fact11.setTimeID(time201308.getId());
		fact11.setDealerID(1);
		fact11.setDepartmentID(1);
		fact11.setItemID(1L);
		fact11.setAmount(new BigDecimal("1000.0"));
		fact11.setMargin(new BigDecimal("2000.0"));
		fact11.setCount(20);
		// skip timestamp and time end
		final DealerIncomeRevenueFact fact21 = new DealerIncomeRevenueFact();
		fact21.setTimeID(time201308.getId());
		fact21.setDealerID(1);
		fact21.setDepartmentID(2);
		fact21.setItemID(1L);
		fact21.setAmount(new BigDecimal("2000.0"));
		fact21.setMargin(new BigDecimal("3000.0"));
		fact21.setCount(30);
		final DealerIncomeRevenueFact fact31 = new DealerIncomeRevenueFact();
		fact31.setTimeID(time201307.getId());
		fact31.setDealerID(2);
		fact31.setDepartmentID(2);
		fact31.setItemID(2L);
		fact31.setAmount(new BigDecimal("2020.0"));
		fact31.setMargin(new BigDecimal("3030.0"));
		fact31.setCount(40);
		
		final ImmutableListMultimap<Integer, DealerIncomeRevenueFact> dealerRevenueFacts = Multimaps
				.index(Lists.newArrayList(fact11, fact21, fact31),
						GetDealerIDFromRevenueFunction.INSTANCE);
		final ImmutableListMultimap<Integer, DealerIncomeExpenseFact> dealerExpenseFacts = Multimaps
				.index(Lists.newArrayList(fact1, fact2, fact3),
						GetDealerIDFromExpenseFunction.INSTANCE);

		final ReportDealerDataList detail = 
				calc.calcRevenues(dealerRevenueFacts, JournalOp.SUM, refDataDAL)
					.calcExpenses(dealerExpenseFacts, JournalOp.SUM, refDataDAL)
					.calcMargins(dealerRevenueFacts, JournalOp.SUM, refDataDAL)
					.calcOpProfit().getReportDetail();
		System.out.println(detail);
		Assert.assertNotNull(detail);
		Assert.assertEquals(fact1.getAmount().add(fact2.getAmount()).doubleValue(), detail.getDetail().get(0).getExpense().getAmount());
		Assert.assertEquals((fact3.getAmount().doubleValue()) / 9.0, detail.getDetail().get(0).getExpense().getReference());
		
		Assert.assertEquals(fact11.getMargin().add(fact21.getMargin()).doubleValue() - fact1.getAmount().add(fact2.getAmount()).doubleValue(), 
				detail.getDetail().get(0).getOpProfit().getAmount());
	}
	
	private enum GetDealerIDFromRevenueFunction implements
			Function<DealerIncomeRevenueFact, Integer> {
		INSTANCE;

		@Override
		public Integer apply(final DealerIncomeRevenueFact item) {
			return item.getDealerID();
		}
	}
	

	private enum GetDealerIDFromExpenseFunction implements
			Function<DealerIncomeExpenseFact, Integer> {
		INSTANCE;

		@Override
		public Integer apply(final DealerIncomeExpenseFact item) {
			return item.getDealerID();
		}
	}
}
