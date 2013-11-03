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

import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimaps;
import com.jdc.themis.dealer.data.dao.ReportDAO;
import com.jdc.themis.dealer.domain.DealerHRAllocationFact;
import com.jdc.themis.dealer.domain.ReportItem;
import com.jdc.themis.dealer.domain.ReportTime;
import com.jdc.themis.dealer.service.RefDataQueryService;
import com.jdc.themis.dealer.service.impl.GetDealerIDFromHRAllocFunction;
import com.jdc.themis.dealer.web.domain.DealerDetail;
import com.jdc.themis.dealer.web.domain.DepartmentDetail;
import com.jdc.themis.dealer.web.domain.GetDealerResponse;
import com.jdc.themis.dealer.web.domain.GetDepartmentResponse;
import com.jdc.themis.dealer.web.domain.GetHumanResourceAllocationItemResponse;
import com.jdc.themis.dealer.web.domain.HumanResourceAllocationItemDetail;
import com.jdc.themis.dealer.web.domain.ReportDealerHRAllocDataList;

import fj.data.Option;

public class TestDealerHRAllocReportCalculator {

	private Collection<DealerDetail> dealers;
	private Collection<HumanResourceAllocationItemDetail> positions;
	private Collection<DepartmentDetail> departments;
	@Mock
	private ReportDAO dal;
	@Mock
	private RefDataQueryService refDataDAL;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks( this );
		dal = mock(ReportDAO.class);
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
		
		final HumanResourceAllocationItemDetail position1 = new HumanResourceAllocationItemDetail();
		position1.setId(1);
		position1.setName("JobPosition1");
		
		final HumanResourceAllocationItemDetail position2 = new HumanResourceAllocationItemDetail();
		position2.setId(2);
		position2.setName("JobPosition2");
		
		final HumanResourceAllocationItemDetail position3 = new HumanResourceAllocationItemDetail();
		position3.setId(3);
		position3.setName("JobPosition3");
		positions = Lists.newArrayList(position1, position2, position3);
		
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
	public void calcAlloc() {
		final DealerHRAllocationReportCalculator calc = new DealerHRAllocationReportCalculator(dealers, 2013, 8);
		final ReportTime time201308 = new ReportTime();
		time201308.setId(1L);
		time201308.setMonthOfYear(8);
		time201308.setYear(2013);
		time201308.setValidDate(LocalDate.of(2013, 8, 1));

		final DealerHRAllocationFact fact1 = new DealerHRAllocationFact();
		fact1.setTimeID(time201308.getId());
		fact1.setDealerID(1);
		fact1.setDepartmentID(1);
		fact1.setItemID(1L);
		fact1.setAllocation(new BigDecimal("1000.0"));
		// skip timestamp and time end
		final DealerHRAllocationFact fact2 = new DealerHRAllocationFact();
		fact2.setTimeID(time201308.getId());
		fact2.setDealerID(1);
		fact2.setDepartmentID(2);
		fact2.setItemID(1L);
		fact2.setAllocation(new BigDecimal("2000.0"));
		final DealerHRAllocationFact fact3 = new DealerHRAllocationFact();
		fact3.setTimeID(time201308.getId());
		fact3.setDealerID(2);
		fact3.setDepartmentID(2);
		fact3.setItemID(2L);
		fact3.setAllocation(new BigDecimal("2020.0"));
		
		final GetDealerResponse dealersResponse = new GetDealerResponse();
		dealersResponse.getItems().addAll(dealers);
		when(refDataDAL.getDealers()).thenReturn(dealersResponse);
		
		final GetHumanResourceAllocationItemResponse positionsResponse = new GetHumanResourceAllocationItemResponse();
		positionsResponse.getItems().addAll(positions);
		when(refDataDAL.getHumanResourceAllocationItems()).thenReturn(positionsResponse);
		
		final ReportItem item1 = new ReportItem();
		item1.setId(1L);
		item1.setSourceItemID(1);
		item1.setName("JobPosition1");
		when(dal.getReportItem(1, "HumanResourceAllocation")).thenReturn(Option.<ReportItem>some(item1));
		
		final ReportItem item2 = new ReportItem();
		item2.setId(2L);
		item2.setSourceItemID(2);
		item2.setName("JobPosition2");
		when(dal.getReportItem(2, "HumanResourceAllocation")).thenReturn(Option.<ReportItem>some(item2));
		
		final ImmutableListMultimap<Integer, DealerHRAllocationFact> dealerFacts = Multimaps
				.index(Lists.newArrayList(fact1, fact2, fact3),
						GetDealerIDFromHRAllocFunction.INSTANCE);

		final ReportDealerHRAllocDataList detail = calc.calcAllocations(dealerFacts, refDataDAL, dal).getReportDetail();
		System.out.println(detail);
		Assert.assertNotNull(detail);
		Assert.assertEquals(fact1.getAllocation().add(fact2.getAllocation()).doubleValue(), detail.getDetail().get(0).getAllocation().getAmount());
	}
	
	@Test
	public void calcAllocByDepartment() {
		final DealerHRAllocationReportCalculator calc = new DealerHRAllocationReportCalculator(dealers, 2013, 8);
		calc.withGroupBy(Option.<Integer>some(0));
		final ReportTime time201308 = new ReportTime();
		time201308.setId(1L);
		time201308.setMonthOfYear(8);
		time201308.setYear(2013);
		time201308.setValidDate(LocalDate.of(2013, 8, 1));

		final DealerHRAllocationFact fact1 = new DealerHRAllocationFact();
		fact1.setTimeID(time201308.getId());
		fact1.setDealerID(1);
		fact1.setDepartmentID(1);
		fact1.setItemID(1L);
		fact1.setAllocation(new BigDecimal("1000.0"));
		// skip timestamp and time end
		final DealerHRAllocationFact fact2 = new DealerHRAllocationFact();
		fact2.setTimeID(time201308.getId());
		fact2.setDealerID(1);
		fact2.setDepartmentID(2);
		fact2.setItemID(1L);
		fact2.setAllocation(new BigDecimal("2000.0"));
		final DealerHRAllocationFact fact3 = new DealerHRAllocationFact();
		fact3.setTimeID(time201308.getId());
		fact3.setDealerID(2);
		fact3.setDepartmentID(2);
		fact3.setItemID(2L);
		fact3.setAllocation(new BigDecimal("2020.0"));
		
		final GetDealerResponse dealersResponse = new GetDealerResponse();
		dealersResponse.getItems().addAll(dealers);
		when(refDataDAL.getDealers()).thenReturn(dealersResponse);
		
		final GetDepartmentResponse departmentsResponse = new GetDepartmentResponse();
		departmentsResponse.getItems().addAll(departments);
		when(refDataDAL.getDepartments()).thenReturn(departmentsResponse);
		
		final GetHumanResourceAllocationItemResponse positionsResponse = new GetHumanResourceAllocationItemResponse();
		positionsResponse.getItems().addAll(positions);
		when(refDataDAL.getHumanResourceAllocationItems()).thenReturn(positionsResponse);
		
		final ReportItem item1 = new ReportItem();
		item1.setId(1L);
		item1.setSourceItemID(1);
		item1.setName("JobPosition1");
		when(dal.getReportItem(1, "HumanResourceAllocation")).thenReturn(Option.<ReportItem>some(item1));
		
		final ReportItem item2 = new ReportItem();
		item2.setId(2L);
		item2.setSourceItemID(2);
		item2.setName("JobPosition2");
		when(dal.getReportItem(2, "HumanResourceAllocation")).thenReturn(Option.<ReportItem>some(item2));
		
		final ImmutableListMultimap<Integer, DealerHRAllocationFact> dealerFacts = Multimaps
				.index(Lists.newArrayList(fact1, fact2, fact3),
						GetDealerIDFromHRAllocFunction.INSTANCE);

		final ReportDealerHRAllocDataList detail = calc.calcAllocations(dealerFacts, refDataDAL, dal).getReportDetail();
		System.out.println(detail);
		Assert.assertNotNull(detail);
		Assert.assertEquals(fact1.getAllocation().add(fact2.getAllocation()).doubleValue(), detail.getDetail().get(0).getAllocation().getAmount());
	}
	
	@Test
	public void calcAllocByPosition() {
		final DealerHRAllocationReportCalculator calc = new DealerHRAllocationReportCalculator(dealers, 2013, 8);
		calc.withGroupBy(Option.<Integer>some(1));
		final ReportTime time201308 = new ReportTime();
		time201308.setId(1L);
		time201308.setMonthOfYear(8);
		time201308.setYear(2013);
		time201308.setValidDate(LocalDate.of(2013, 8, 1));

		final DealerHRAllocationFact fact1 = new DealerHRAllocationFact();
		fact1.setTimeID(time201308.getId());
		fact1.setDealerID(1);
		fact1.setDepartmentID(1);
		fact1.setItemID(1L);
		fact1.setAllocation(new BigDecimal("1000.0"));
		// skip timestamp and time end
		final DealerHRAllocationFact fact2 = new DealerHRAllocationFact();
		fact2.setTimeID(time201308.getId());
		fact2.setDealerID(1);
		fact2.setDepartmentID(2);
		fact2.setItemID(1L);
		fact2.setAllocation(new BigDecimal("2000.0"));
		final DealerHRAllocationFact fact3 = new DealerHRAllocationFact();
		fact3.setTimeID(time201308.getId());
		fact3.setDealerID(2);
		fact3.setDepartmentID(2);
		fact3.setItemID(2L);
		fact3.setAllocation(new BigDecimal("2020.0"));
		
		final GetDealerResponse dealersResponse = new GetDealerResponse();
		dealersResponse.getItems().addAll(dealers);
		when(refDataDAL.getDealers()).thenReturn(dealersResponse);
		
		final GetDepartmentResponse departmentsResponse = new GetDepartmentResponse();
		departmentsResponse.getItems().addAll(departments);
		when(refDataDAL.getDepartments()).thenReturn(departmentsResponse);
		
		final GetHumanResourceAllocationItemResponse positionsResponse = new GetHumanResourceAllocationItemResponse();
		positionsResponse.getItems().addAll(positions);
		when(refDataDAL.getHumanResourceAllocationItems()).thenReturn(positionsResponse);
		
		final ReportItem item1 = new ReportItem();
		item1.setId(1L);
		item1.setSourceItemID(1);
		item1.setName("JobPosition1");
		when(dal.getReportItem(1, "HumanResourceAllocation")).thenReturn(Option.<ReportItem>some(item1));
		
		final ReportItem item2 = new ReportItem();
		item2.setId(2L);
		item2.setSourceItemID(2);
		item2.setName("JobPosition2");
		when(dal.getReportItem(2, "HumanResourceAllocation")).thenReturn(Option.<ReportItem>some(item2));
		
		final ReportItem item3 = new ReportItem();
		item3.setId(3L);
		item3.setSourceItemID(3);
		item3.setName("JobPosition3");
		when(dal.getReportItem(3, "HumanResourceAllocation")).thenReturn(Option.<ReportItem>some(item3));
		
		final ImmutableListMultimap<Integer, DealerHRAllocationFact> dealerFacts = Multimaps
				.index(Lists.newArrayList(fact1, fact2, fact3),
						GetDealerIDFromHRAllocFunction.INSTANCE);

		final ReportDealerHRAllocDataList detail = calc.calcAllocations(dealerFacts, refDataDAL, dal).getReportDetail();
		System.out.println(detail);
		Assert.assertNotNull(detail);
		Assert.assertEquals(fact1.getAllocation().add(fact2.getAllocation()).doubleValue(), detail.getDetail().get(0).getAllocation().getAmount());
	}
	
}
