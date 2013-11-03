package com.jdc.themis.dealer.data.dao.hibernate;

import java.math.BigDecimal;
import java.util.Collection;

import javax.time.calendar.LocalDate;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.jdc.themis.dealer.data.dao.IncomeJournalDAO;
import com.jdc.themis.dealer.data.dao.ReportDAO;
import com.jdc.themis.dealer.domain.DealerHRAllocationFact;
import com.jdc.themis.dealer.domain.DealerIncomeExpenseFact;
import com.jdc.themis.dealer.domain.DealerIncomeRevenueFact;
import com.jdc.themis.dealer.domain.GeneralJournal;
import com.jdc.themis.dealer.domain.HumanResourceAllocation;
import com.jdc.themis.dealer.domain.ReportItem;
import com.jdc.themis.dealer.domain.ReportTime;
import com.jdc.themis.dealer.domain.SalesServiceJournal;
import com.jdc.themis.dealer.domain.TaxJournal;
import com.jdc.themis.dealer.domain.VehicleSalesJournal;

import fj.data.Option;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
    "classpath:test-database-config.xml" })
@Transactional
public class TestReportDAOImpl {
	@Autowired
	private ReportDAO reportDAL;
	@Autowired
	private IncomeJournalDAO incomeJournalDAL;

	@Test
	public void importVehicleSalesJournal() {
		final VehicleSalesJournal status = new VehicleSalesJournal();
		status.setDealerID(10);
		status.setId(1);
		status.setAmount(new BigDecimal("1234.343"));
		status.setMargin(new BigDecimal("2234.343"));
		status.setCount(12);
		status.setDepartmentID(1);
		status.setValidDate(LocalDate.of(2013, 8, 1));
		status.setUpdatedBy("test");
		incomeJournalDAL.saveVehicleSalesJournal(10, 1, Lists.newArrayList(status));
		
		final VehicleSalesJournal status2 = new VehicleSalesJournal();
		status2.setDealerID(11);
		status2.setId(1);
		status2.setAmount(new BigDecimal("5234.343"));
		status2.setMargin(new BigDecimal("2234.343"));
		status2.setCount(1123432);
		status2.setDepartmentID(3);
		status2.setValidDate(LocalDate.of(2013, 8, 1));
		status2.setUpdatedBy("test2");
		incomeJournalDAL.saveVehicleSalesJournal(11, 3, Lists.newArrayList(status2));
		
		final VehicleSalesJournal status3 = new VehicleSalesJournal();
		status3.setDealerID(10);
		status3.setId(2);
		status3.setAmount(new BigDecimal("335"));
		status3.setMargin(new BigDecimal("22"));
		status3.setCount(12);
		status3.setDepartmentID(3);
		status3.setValidDate(LocalDate.of(2013, 7, 1));
		status3.setUpdatedBy("test");
		incomeJournalDAL.saveVehicleSalesJournal(10, 3, Lists.newArrayList(status3));
		
		reportDAL.importVehicleSalesJournal(LocalDate.of(2013, 8, 1));
		reportDAL.importVehicleSalesJournal(LocalDate.of(2013, 7, 1));
		// force to populate twice
		reportDAL.importVehicleSalesJournal(LocalDate.of(2013, 8, 1));
		reportDAL.importVehicleSalesJournal(LocalDate.of(2013, 7, 1));
		
		int hasJournal = 0;
		for (final DealerIncomeRevenueFact journal : 
			reportDAL.getDealerIncomeRevenueFacts(2013, 
					Lists.newArrayList(new Integer[]{8}), 
					Lists.newArrayList(new Integer[]{}), 
					Lists.newArrayList(new Integer[]{}), 
					Lists.newArrayList(new String[]{}), 
					Lists.newArrayList(new Long[]{}), 
					Lists.newArrayList(new Integer[]{}))) {
			hasJournal++;
			System.err.println(journal);
			Assert.assertNotNull(journal);
		} 
		Assert.assertEquals(2, hasJournal);
		
		hasJournal = 0;
		for (final DealerIncomeRevenueFact journal : 
			reportDAL.getDealerIncomeRevenueFacts(2013, 
					Lists.newArrayList(new Integer[]{7}), 
					Lists.newArrayList(new Integer[]{}), 
					Lists.newArrayList(new Integer[]{}), 
					Lists.newArrayList(new String[]{}), 
					Lists.newArrayList(new Long[]{}), 
					Lists.newArrayList(new Integer[]{}))) {
			hasJournal++;
			System.err.println(journal);
			Assert.assertNotNull(journal);
		} 
		Assert.assertEquals(1, hasJournal);
		
		int hasReportItem = 0;
		for (final ReportItem journal : 
			reportDAL.getAllReportItem()) {
			hasReportItem++;
			System.err.println(journal);
			Assert.assertNotNull(journal);
		} 
		Assert.assertEquals(2, hasReportItem);
		
		int hasReportTime = 0;
		for (final ReportTime journal : 
			reportDAL.getAllReportTime()) {
			hasReportTime++;
			System.err.println(journal);
			Assert.assertNotNull(journal);
		} 
		Assert.assertEquals(2, hasReportTime);
	} 
	
	@Test
	public void importSalesServiceJournal() {
		final SalesServiceJournal status4 = new SalesServiceJournal();
		status4.setDealerID(10);
		status4.setId(1);
		status4.setAmount(new BigDecimal("1234.343"));
		status4.setMargin(new BigDecimal("2234.343"));
		status4.setCount(12);
		status4.setDepartmentID(1);
		status4.setValidDate(LocalDate.of(2013, 8, 1));
		status4.setUpdatedBy("test");
		incomeJournalDAL.saveSalesServiceJournal(10, 1, Lists.newArrayList(status4));
		
		final SalesServiceJournal status5 = new SalesServiceJournal();
		status5.setDealerID(11);
		status5.setId(1);
		status5.setAmount(new BigDecimal("5234.343"));
		status5.setMargin(new BigDecimal("2234.343"));
		status5.setCount(1123432);
		status5.setDepartmentID(3);
		status5.setValidDate(LocalDate.of(2013, 8, 1));
		status5.setUpdatedBy("test2");
		incomeJournalDAL.saveSalesServiceJournal(11, 3, Lists.newArrayList(status5));
		
		final SalesServiceJournal status6 = new SalesServiceJournal();
		status6.setDealerID(10);
		status6.setId(2);
		status6.setAmount(new BigDecimal("335"));
		status6.setMargin(new BigDecimal("22"));
		status6.setCount(12);
		status6.setDepartmentID(3);
		status6.setValidDate(LocalDate.of(2013, 7, 1));
		status6.setUpdatedBy("test");
		incomeJournalDAL.saveSalesServiceJournal(10, 3, Lists.newArrayList(status6));
		
		final SalesServiceJournal status7 = new SalesServiceJournal();
		status7.setDealerID(10);
		status7.setId(3); // this is a expense item
		status7.setAmount(BigDecimal.ZERO);
		status7.setMargin(new BigDecimal("335"));
		status7.setCount(0);
		status7.setDepartmentID(3);
		status7.setValidDate(LocalDate.of(2013, 7, 1));
		status7.setUpdatedBy("test");
		incomeJournalDAL.saveSalesServiceJournal(10, 3, Lists.newArrayList(status7));
		
		reportDAL.importSalesServiceJournal(LocalDate.of(2013, 8, 1));
		reportDAL.importSalesServiceJournal(LocalDate.of(2013, 7, 1));
		// force to populate twice
		reportDAL.importSalesServiceJournal(LocalDate.of(2013, 8, 1));
		reportDAL.importSalesServiceJournal(LocalDate.of(2013, 7, 1));
		
		int hasJournal = 0;
		for (final DealerIncomeRevenueFact journal : 
			reportDAL.getDealerIncomeRevenueFacts(2013, 
					Lists.newArrayList(new Integer[]{8}), 
					Lists.newArrayList(new Integer[]{}), 
					Lists.newArrayList(new Integer[]{}), 
					Lists.newArrayList(new String[]{}), 
					Lists.newArrayList(new Long[]{}), 
					Lists.newArrayList(new Integer[]{}))) {
			hasJournal++;
			System.err.println(journal);
			Assert.assertNotNull(journal);
		} 
		Assert.assertEquals(2, hasJournal);
		
		hasJournal = 0;
		for (final DealerIncomeExpenseFact journal : 
			reportDAL.getDealerIncomeExpenseFacts(2013, Lists.newArrayList(new Integer[]{7}), 
					Lists.newArrayList(new Integer[]{}), 
					Lists.newArrayList(new Integer[]{}), 
					Lists.newArrayList(new String[]{}), 
					Lists.newArrayList(new String[]{}), 
					Lists.newArrayList(new Long[]{}), 
					Lists.newArrayList(new Integer[]{}))) {
			hasJournal++;
			System.err.println(journal);
			Assert.assertNotNull(journal);
			Assert.assertEquals(new BigDecimal("335"), journal.getAmount());
		} 
		Assert.assertEquals(1, hasJournal);
		
		hasJournal = 0;
		for (final DealerIncomeRevenueFact journal : 
			reportDAL.getDealerIncomeRevenueFacts(2013, 
					Lists.newArrayList(new Integer[]{7}), 
					Lists.newArrayList(new Integer[]{}), 
					Lists.newArrayList(new Integer[]{}), 
					Lists.newArrayList(new String[]{}), 
					Lists.newArrayList(new Long[]{}), 
					Lists.newArrayList(new Integer[]{}))) {
			hasJournal++;
			System.err.println(journal);
			Assert.assertNotNull(journal);
		} 
		Assert.assertEquals(1, hasJournal);
		
		int hasReportItem = 0;
		for (final ReportItem journal : 
			reportDAL.getAllReportItem()) {
			hasReportItem++;
			System.err.println(journal);
			Assert.assertNotNull(journal);
		} 
		Assert.assertEquals(3, hasReportItem);
		
		int hasReportTime = 0;
		for (final ReportTime journal : 
			reportDAL.getAllReportTime()) {
			hasReportTime++;
			System.err.println(journal);
			Assert.assertNotNull(journal);
		} 
		Assert.assertEquals(2, hasReportTime);
	} 
	
	@Test
	public void importGeneralJournal() {
		final GeneralJournal status4 = new GeneralJournal();
		status4.setDealerID(10);
		status4.setId(1);
		status4.setAmount(new BigDecimal("1234.343"));
		status4.setDepartmentID(1);
		status4.setValidDate(LocalDate.of(2013, 8, 1));
		status4.setUpdatedBy("test");
		incomeJournalDAL.saveGeneralJournal(10, 1, Lists.newArrayList(status4));
		
		final GeneralJournal status5 = new GeneralJournal();
		status5.setDealerID(11);
		status5.setId(1);
		status5.setAmount(new BigDecimal("5234.343"));
		status5.setDepartmentID(3);
		status5.setValidDate(LocalDate.of(2013, 8, 1));
		status5.setUpdatedBy("test2");
		incomeJournalDAL.saveGeneralJournal(11, 3, Lists.newArrayList(status5));
		
		final GeneralJournal status6 = new GeneralJournal();
		status6.setDealerID(10);
		status6.setId(1);
		status6.setAmount(new BigDecimal("335"));
		status6.setDepartmentID(3);
		status6.setValidDate(LocalDate.of(2013, 7, 1));
		status6.setUpdatedBy("test");
		incomeJournalDAL.saveGeneralJournal(10, 3, Lists.newArrayList(status6));
		
		final GeneralJournal status7 = new GeneralJournal();
		status7.setDealerID(10);
		status7.setId(2);
		status7.setAmount(new BigDecimal("335"));
		status7.setDepartmentID(3);
		status7.setValidDate(LocalDate.of(2013, 7, 1));
		status7.setUpdatedBy("test");
		incomeJournalDAL.saveGeneralJournal(10, 3, Lists.newArrayList(status7));
		
		reportDAL.importGeneralJournal(LocalDate.of(2013, 8, 1));
		reportDAL.importGeneralJournal(LocalDate.of(2013, 7, 1));
		// force to populate twice
		reportDAL.importGeneralJournal(LocalDate.of(2013, 8, 1));
		reportDAL.importGeneralJournal(LocalDate.of(2013, 7, 1));
		
		int hasJournal = 0;
		for (final DealerIncomeRevenueFact journal : 
			reportDAL.getDealerIncomeRevenueFacts(2013, 
					Lists.newArrayList(new Integer[]{8}), 
					Lists.newArrayList(new Integer[]{}), 
					Lists.newArrayList(new Integer[]{}), 
					Lists.newArrayList(new String[]{}), 
					Lists.newArrayList(new Long[]{}), 
					Lists.newArrayList(new Integer[]{}))) {
			hasJournal++;
			System.err.println(journal);
			Assert.assertNotNull(journal);
		} 
		Assert.assertEquals(2, hasJournal);
		
		hasJournal = 0;
		for (final DealerIncomeRevenueFact journal : 
			reportDAL.getDealerIncomeRevenueFacts(2013, 
					Lists.newArrayList(new Integer[]{8}), 
					Lists.newArrayList(new Integer[]{3}), 
					Lists.newArrayList(new Integer[]{}), 
					Lists.newArrayList(new String[]{}), 
					Lists.newArrayList(new Long[]{}), 
					Lists.newArrayList(new Integer[]{}))) {
			hasJournal++;
			System.err.println(journal);
			Assert.assertNotNull(journal);
		} 
		Assert.assertEquals(1, hasJournal);
		
		hasJournal = 0;
		for (final DealerIncomeRevenueFact journal : 
			reportDAL.getDealerIncomeRevenueFacts(2013, 
					Lists.newArrayList(new Integer[]{7}), 
					Lists.newArrayList(new Integer[]{}), 
					Lists.newArrayList(new Integer[]{}), 
					Lists.newArrayList(new String[]{}), 
					Lists.newArrayList(new Long[]{}), 
					Lists.newArrayList(new Integer[]{}))) {
			hasJournal++;
			System.err.println(journal);
			Assert.assertNotNull(journal);
		} 
		Assert.assertEquals(1, hasJournal);
		
		hasJournal = 0;
		for (final DealerIncomeExpenseFact journal : 
			reportDAL.getDealerIncomeExpenseFacts(2013, Lists.newArrayList(new Integer[]{7}), 
					Lists.newArrayList(new Integer[]{}), 
					Lists.newArrayList(new Integer[]{}), 
					Lists.newArrayList(new String[]{}), 
					Lists.newArrayList(new String[]{}), 
					Lists.newArrayList(new Long[]{}), 
					Lists.newArrayList(new Integer[]{}))) {
			hasJournal++;
			System.err.println(journal);
			Assert.assertNotNull(journal);
		} 
		Assert.assertEquals(1, hasJournal);
		
		hasJournal = 0;
		for (final DealerIncomeExpenseFact journal : 
			reportDAL.getDealerIncomeExpenseFacts(2013, Lists.newArrayList(new Integer[]{7}), 
					Lists.newArrayList(new Integer[]{}), 
					Lists.newArrayList(new Integer[]{}), 
					Lists.newArrayList(new String[]{}), 
					Lists.newArrayList(new String[]{}), 
					Lists.newArrayList(new Long[]{}), 
					Lists.newArrayList(new Integer[]{}))) {
			hasJournal++;
			System.err.println(journal);
			Assert.assertNotNull(journal);
		} 
		Assert.assertEquals(1, hasJournal);
		
		int hasReportItem = 0;
		for (final ReportItem journal : 
			reportDAL.getAllReportItem()) {
			hasReportItem++;
			System.err.println(journal);
			Assert.assertNotNull(journal);
		} 
		Assert.assertEquals(2, hasReportItem);
		
		Assert.assertEquals("GeneralJournalItem1", reportDAL.getReportItem(1, "GeneralJournal").some().getName());
		
		final ReportItem item = reportDAL.getReportItem(1, "GeneralJournal").some();
		Assert.assertEquals("GeneralJournalItem1", reportDAL.getReportItem(item.getId()).some().getName());
		
		int hasReportTime = 0;
		for (final ReportTime journal : 
			reportDAL.getAllReportTime()) {
			hasReportTime++;
			System.err.println(journal);
			Assert.assertNotNull(journal);
		} 
		Assert.assertEquals(2, reportDAL.getReportTime(2013, Option.<Integer>none()).size());
		Assert.assertEquals(1, reportDAL.getReportTime(2013, Option.<Integer>some(8)).size());
		Assert.assertEquals(2, hasReportTime);
	} 
	
	@Test
	public void importTaxJournal() {
		final TaxJournal status = new TaxJournal();
		status.setDealerID(10);
		status.setId(1);
		status.setAmount(new BigDecimal("1234.343"));
		status.setValidDate(LocalDate.of(2013, 8, 1));
		status.setUpdatedBy("test");
		incomeJournalDAL.saveTaxJournal(10, Lists.newArrayList(status));
		final TaxJournal status2 = new TaxJournal();
		status2.setDealerID(11);
		status2.setId(1);
		status2.setAmount(new BigDecimal("1234.343"));
		status2.setValidDate(LocalDate.of(2013, 8, 1));
		status2.setUpdatedBy("test");
		incomeJournalDAL.saveTaxJournal(11, Lists.newArrayList(status2));
		
		reportDAL.importTaxJournal(LocalDate.of(2013, 8, 1));
		reportDAL.importTaxJournal(LocalDate.of(2013, 8, 1));

		int hasJournal = 0;
		for (final DealerIncomeExpenseFact journal : 
			reportDAL.getDealerIncomeExpenseFacts(2013, 
					Lists.newArrayList(new Integer[]{8}), 
					Lists.newArrayList(new Integer[]{}), 
					Lists.newArrayList(new Integer[]{}), 
					Lists.newArrayList(new String[]{}), 
					Lists.newArrayList(new String[]{}), 
					Lists.newArrayList(new Long[]{}), 
					Lists.newArrayList(new Integer[]{}))) {
			hasJournal++;
			System.err.println(journal);
			Assert.assertNotNull(journal);
		} 
		Assert.assertEquals(3, hasJournal);
		
		Assert.assertEquals("IncomeTax", reportDAL.getReportItem(1, "TaxJournal").some().getName());
		
		final ReportItem item = reportDAL.getReportItem(1, "TaxJournal").some();
		Assert.assertEquals("IncomeTax", reportDAL.getReportItem(item.getId()).some().getName());
		
		int hasReportTime = 0;
		for (final ReportTime journal : 
			reportDAL.getAllReportTime()) {
			hasReportTime++;
			System.err.println(journal);
			Assert.assertNotNull(journal);
		} 
		Assert.assertEquals(1, reportDAL.getReportTime(2013, Option.<Integer>none()).size());
		Assert.assertEquals(1, reportDAL.getReportTime(2013, Option.<Integer>some(8)).size());
		Assert.assertEquals(1, hasReportTime);
	} 
	
	@Test
	public void importTaxJournal2() {
		final TaxJournal status = new TaxJournal();
		status.setDealerID(10);
		status.setId(1);
		status.setAmount(new BigDecimal("1234.343"));
		status.setValidDate(LocalDate.of(2013, 8, 1));
		status.setUpdatedBy("test");
		incomeJournalDAL.saveTaxJournal(10, Lists.newArrayList(status));
		final TaxJournal status2 = new TaxJournal();
		status2.setDealerID(11);
		status2.setId(1);
		status2.setAmount(new BigDecimal("1234.343"));
		status2.setValidDate(LocalDate.of(2013, 9, 1));
		status2.setUpdatedBy("test");
		incomeJournalDAL.saveTaxJournal(11, Lists.newArrayList(status2));
		
		reportDAL.importTaxJournal(LocalDate.of(2013, 8, 1));
		reportDAL.importTaxJournal(LocalDate.of(2013, 9, 1));

		int hasJournal = 0;
		for (final DealerIncomeExpenseFact journal : 
			reportDAL.getDealerIncomeExpenseFacts(2013, 
					Option.<Integer>some(9), 
					Lists.newArrayList(new Integer[]{}), 
					Lists.newArrayList(new Integer[]{}), 
					Lists.newArrayList(new String[]{}), 
					Lists.newArrayList(new String[]{}), 
					Lists.newArrayList(new Long[]{}), 
					Lists.newArrayList(new Integer[]{}))) {
			hasJournal++;
			System.err.println(journal);
			Assert.assertNotNull(journal);
		} 
		Assert.assertEquals(3, hasJournal);
		
		Assert.assertEquals("IncomeTax", reportDAL.getReportItem(1, "TaxJournal").some().getName());
		
		final ReportItem item = reportDAL.getReportItem(1, "TaxJournal").some();
		Assert.assertEquals("IncomeTax", reportDAL.getReportItem(item.getId()).some().getName());
		
		int hasReportTime = 0;
		for (final ReportTime journal : 
			reportDAL.getAllReportTime()) {
			hasReportTime++;
			System.err.println(journal);
			Assert.assertNotNull(journal);
		} 
		Assert.assertEquals(2, reportDAL.getReportTime(2013, Option.<Integer>none()).size());
		Assert.assertEquals(1, reportDAL.getReportTime(2013, Option.<Integer>some(8)).size());
		Assert.assertEquals(2, hasReportTime);
	} 
	
	@Test
	public void addReportItem() {
		final ReportItem newItem = reportDAL.addReportItem(100, "Test", "VehicleSalesJournal", "NewCategory").some();
		System.out.println(newItem);
		Assert.assertEquals(100, newItem.getSourceItemID().intValue());
		Assert.assertEquals("Test", newItem.getName());
		Assert.assertEquals("NewCategory", newItem.getItemCategory());
		
		ReportItem oldItem = reportDAL.getReportItem(100, "VehicleSalesJournal").some();
		Assert.assertEquals(100, oldItem.getSourceItemID().intValue());
		Assert.assertEquals("Test", oldItem.getName());
		Assert.assertEquals("NewCategory", oldItem.getItemCategory());
		
		oldItem = reportDAL.getReportItem("Test", "VehicleSalesJournal").some();
		Assert.assertEquals(100, oldItem.getSourceItemID().intValue());
		Assert.assertEquals("Test", oldItem.getName());
		Assert.assertEquals("NewCategory", oldItem.getItemCategory());
	}
	
	@Test
	public void addReportItem2() {
		final ReportItem item1 = reportDAL.addReportItem(100, "Test", "VehicleSalesJournal", "NewCategory").some();
		System.out.println(item1);
		Assert.assertEquals(100, item1.getSourceItemID().intValue());
		Assert.assertEquals("Test", item1.getName());
		Assert.assertEquals("NewCategory", item1.getItemCategory());
		
		final ReportItem item2 = reportDAL.addReportItem(101, "Test2", "VehicleSalesJournal", "NewCategory").some();
		System.out.println(item2);
		Assert.assertEquals(101, item2.getSourceItemID().intValue());
		Assert.assertEquals("Test2", item2.getName());
		Assert.assertEquals("NewCategory", item2.getItemCategory());
		
		final ReportItem item3 = reportDAL.addReportItem(102, "Test3", "VehicleSalesJournal", "NewCategory2").some();
		System.out.println(item3);
		Assert.assertEquals(102, item3.getSourceItemID().intValue());
		Assert.assertEquals("Test3", item3.getName());
		Assert.assertEquals("NewCategory2", item3.getItemCategory());
		final Collection<String> categories = Lists.newArrayList();
		categories.add("NewCategory");
		final Collection<Integer> sources = Lists.newArrayList();
		final Collection<String> items = Lists.newArrayList();
		
		Assert.assertEquals(2, reportDAL.getReportItem(categories, items, sources).size());
	}
	
	@Test
	public void addReportTime() {
		reportDAL.addReportTime(LocalDate.of(2013, 10, 1));
		reportDAL.addReportTime(LocalDate.of(2013, 9, 1));
		reportDAL.addReportTime(LocalDate.of(2013, 8, 1));
		
		final Option<ReportTime> rt = reportDAL.getReportTime(LocalDate.of(2013, 10, 1));
		Assert.assertTrue(rt.isSome());
		final Collection<ReportTime> rts = reportDAL.getReportTime(2013, Option.<Integer>none());
		Assert.assertEquals(3, rts.size());
		final Collection<ReportTime> rts2 = reportDAL.getReportTime(2013, Option.<Integer>some(9));
		Assert.assertEquals(1, rts2.size());
		
		final Collection<ReportTime> rts3 = reportDAL.getReportTimeLessThanGivenMonth(2013, Option.<Integer>some(9));
		Assert.assertEquals(2, rts3.size());
		final Collection<ReportTime> rts4 = reportDAL.getReportTimeLessThanGivenMonth(2013, Option.<Integer>some(10));
		Assert.assertEquals(3, rts4.size());
	}
	
	@Test
	public void importHRAllocation() {
		final HumanResourceAllocation status4 = new HumanResourceAllocation();
		status4.setDealerID(10);
		status4.setId(1);
		status4.setAllocation(new BigDecimal("1234.343"));
		status4.setDepartmentID(1);
		status4.setValidDate(LocalDate.of(2013, 8, 1));
		status4.setUpdatedBy("test");
		incomeJournalDAL.saveHumanResourceAllocation(10, 1, Lists.newArrayList(status4));
		
		final HumanResourceAllocation status5 = new HumanResourceAllocation();
		status5.setDealerID(11);
		status5.setId(1);
		status5.setAllocation(new BigDecimal("5234.343"));
		status5.setDepartmentID(3);
		status5.setValidDate(LocalDate.of(2013, 8, 1));
		status5.setUpdatedBy("test2");
		incomeJournalDAL.saveHumanResourceAllocation(11, 3, Lists.newArrayList(status5));
		
		final HumanResourceAllocation status6 = new HumanResourceAllocation();
		status6.setDealerID(10);
		status6.setId(1);
		status6.setAllocation(new BigDecimal("335"));
		status6.setDepartmentID(3);
		status6.setValidDate(LocalDate.of(2013, 7, 1));
		status6.setUpdatedBy("test");
		incomeJournalDAL.saveHumanResourceAllocation(10, 3, Lists.newArrayList(status6));
		
		final HumanResourceAllocation status7 = new HumanResourceAllocation();
		status7.setDealerID(10);
		status7.setId(2);
		status7.setAllocation(new BigDecimal("335"));
		status7.setDepartmentID(3);
		status7.setValidDate(LocalDate.of(2013, 7, 1));
		status7.setUpdatedBy("test");
		incomeJournalDAL.saveHumanResourceAllocation(10, 3, Lists.newArrayList(status7));
		
		reportDAL.importHRAllocation(LocalDate.of(2013, 8, 1));
		reportDAL.importHRAllocation(LocalDate.of(2013, 7, 1));
		// force to populate twice
		reportDAL.importHRAllocation(LocalDate.of(2013, 8, 1));
		reportDAL.importHRAllocation(LocalDate.of(2013, 7, 1));
		
		int hasJournal = 0;
		for (final DealerHRAllocationFact journal : 
			reportDAL.getDealerHRAllocationFacts(2013, 
					8, 
					Option.<Integer>none(), 
					Option.<Integer>none(), 
					Lists.newArrayList(new Integer[]{}))) {
			hasJournal++;
			System.err.println(journal);
			Assert.assertNotNull(journal);
		} 
		Assert.assertEquals(2, hasJournal);
		
		hasJournal = 0;
		for (final DealerHRAllocationFact journal : 
			reportDAL.getDealerHRAllocationFacts(2013, 
					8, 
					Option.<Integer>none(), 
					Option.<Integer>some(1), 
					Lists.newArrayList(new Integer[]{}))) {
			hasJournal++;
			System.err.println(journal);
			Assert.assertNotNull(journal);
		} 
		Assert.assertEquals(2, hasJournal);
		
		int hasReportItem = 0;
		for (final ReportItem journal : 
			reportDAL.getAllReportItem()) {
			hasReportItem++;
			System.err.println(journal);
			Assert.assertNotNull(journal);
		} 
		Assert.assertEquals(2, hasReportItem);
		
	} 
}
