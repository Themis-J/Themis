package com.jdc.themis.dealer.data.dao.hibernate;

import java.util.Collection;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.Multimaps;
import com.jdc.themis.dealer.data.dao.RefDataDAO;
import com.jdc.themis.dealer.domain.AccountReceivableDurationItem;
import com.jdc.themis.dealer.domain.Dealer;
import com.jdc.themis.dealer.domain.Department;
import com.jdc.themis.dealer.domain.Duration;
import com.jdc.themis.dealer.domain.EmployeeFeeItem;
import com.jdc.themis.dealer.domain.EmployeeFeeSummaryItem;
import com.jdc.themis.dealer.domain.EnumType;
import com.jdc.themis.dealer.domain.EnumValue;
import com.jdc.themis.dealer.domain.GeneralJournalCategory;
import com.jdc.themis.dealer.domain.GeneralJournalItem;
import com.jdc.themis.dealer.domain.InventoryDurationItem;
import com.jdc.themis.dealer.domain.JobPosition;
import com.jdc.themis.dealer.domain.Menu;
import com.jdc.themis.dealer.domain.MenuHierachy;
import com.jdc.themis.dealer.domain.SalesServiceJournalCategory;
import com.jdc.themis.dealer.domain.SalesServiceJournalItem;
import com.jdc.themis.dealer.domain.TaxJournalItem;
import com.jdc.themis.dealer.domain.Vehicle;
import com.jdc.themis.dealer.utils.Performance;

import fj.P1;
import fj.data.Option;

/**
 * Hibernate implementation for reference data access layer.
 * 
 * @author Kai Chen
 * 
 */
@Service
public class RefDataDAOImpl implements RefDataDAO {
	private final static Logger logger = LoggerFactory
			.getLogger(RefDataDAOImpl.class);

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public List<Menu> getMenus() {
		logger.info("Fetching menu list");
		final Session session = sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		final List<Menu> list = session.createCriteria(Menu.class).list();
		return ImmutableList.copyOf(list);
	}

	@Override
	public List<MenuHierachy> getMenuHierachys() {
		final Session session = sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		final List<MenuHierachy> list = session.createCriteria(
				MenuHierachy.class).list();
		return ImmutableList.copyOf(list);
	}

	private enum GetParentIDFunction implements Function<MenuHierachy, Integer> {
		INSTANCE;

		@Override
		public Integer apply(MenuHierachy hierachy) {
			return hierachy.getMenuHierachyID().getParentID();
		}
	}

	@Override
	public Menu getMenu(Integer id) {
		final Session session = sessionFactory.getCurrentSession();
		final Menu menu = (Menu) session.load(Menu.class, id);
		return menu;
	}

	@Override
	public Integer getParentMenuID(final Integer id) {
		// Kai: try out functional java here...
		final Collection<MenuHierachy> list = fj.data.List.iterableList(getMenuHierachys()).filter(new fj.F<MenuHierachy, Boolean>() {

			@Override
			public Boolean f(MenuHierachy a) {
				return ((MenuHierachy) a).getMenuHierachyID().getChildID().equals(id);
			}
			
		}).toCollection();
		if ( list.size() == 0 ) {
			return null;
		}
		return list.iterator().next().getMenuHierachyID().getParentID();
	}

	@Override
	public List<Vehicle> getVehicles(Option<Integer> categoryID) {
		final Session session = sessionFactory.getCurrentSession();
		if ( categoryID.isSome() ) {
			session.enableFilter(Vehicle.FILTER).setParameter("categoryID", categoryID.some());
		}
		@SuppressWarnings("unchecked")
		final List<Vehicle> list = session.createCriteria(Vehicle.class).setCacheable(true).list();
		if ( categoryID.isSome() ) {
			session.disableFilter(Vehicle.FILTER);
		}
		return ImmutableList.copyOf(list);
	}

	@Override
	public List<Dealer> getDealers() {
		final Session session = sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		final List<Dealer> list = session.createCriteria(Dealer.class).list();
		return ImmutableList.copyOf(list);
	}

	@Override
	public List<TaxJournalItem> getTaxJournalItems() {
		final Session session = sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		final List<TaxJournalItem> list = session.createCriteria(
				TaxJournalItem.class).list();
		return ImmutableList.copyOf(list);
	}

	@Override
	public List<SalesServiceJournalItem> getSalesServiceJournalItems(Option<Integer> categoryID) {
		final Session session = sessionFactory.getCurrentSession();
		if ( categoryID.isSome() ) {
			session.enableFilter(SalesServiceJournalItem.FILTER).setParameter("categoryID", categoryID.some());
		}
		@SuppressWarnings("unchecked")
		final List<SalesServiceJournalItem> list = session.createCriteria(
				SalesServiceJournalItem.class).list();
		if ( categoryID.isSome() ) {
			session.disableFilter(SalesServiceJournalItem.FILTER);
		}
		return ImmutableList.copyOf(list);
	}

	@Override
	public List<SalesServiceJournalCategory> getSalesServiceJournalCategorys() {
		final Session session = sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		final List<SalesServiceJournalCategory> list = session.createCriteria(
				SalesServiceJournalCategory.class).list();
		return ImmutableList.copyOf(list);
	}

	/*
	 * parent to child menu mapping
	 */
	private Collection<MenuHierachy> getParentMenuMapping(Integer id) {
		final List<MenuHierachy> menuHierachy = getMenuHierachys();
		final ImmutableListMultimap<Integer, MenuHierachy> parentIDToMenuHierachy = Multimaps
				.index(menuHierachy, GetParentIDFunction.INSTANCE);
		return parentIDToMenuHierachy.asMap().get(id);
	}

	@Override
	public Collection<MenuHierachy> getChildMenus(Integer id) {
		return getParentMenuMapping(id);
	}

	@Override
	public List<EnumType> getEnumTypes() {
		final Session session = sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		final List<EnumType> list = session.createCriteria(EnumType.class)
				.list();
		return ImmutableList.copyOf(list);
	}

	private Option<EnumType> getEnumType(String name) {
		final Session session = sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		final List<EnumType> enumTypes = session.createCriteria(EnumType.class)
														.add(Restrictions.eq("name", name)).list();
		return Option.<EnumType> iif(!enumTypes.isEmpty(),
				new P1<EnumType>() {

					@Override
					public EnumType _1() {
						return enumTypes.get(0);
					}

				});
	}
	
	@Override
	public List<EnumValue> getEnumValues() {
		final Session session = sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		final List<EnumValue> list = session.createCriteria(EnumValue.class)
				.list();
		return ImmutableList.copyOf(list);
	}

	@Override
	@Performance
	public Option<EnumValue> getEnumValue(String enumType, Integer enumValue) {
		final EnumType type = getEnumType(enumType).some();
		final Session session = sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		final List<EnumValue> enumValues = session.createCriteria(EnumValue.class)
				.add(Restrictions.eq("typeID", type.getId()))
				.add(Restrictions.eq("value", enumValue))
				.setCacheable(true)
				.list();
		return Option.<EnumValue> iif(!enumValues.isEmpty(),
			new P1<EnumValue>() {
			
			@Override
			public EnumValue _1() {
				return enumValues.get(0);
			}
		
		});
	}

	@Override
	@Performance
	public Option<EnumValue> getEnumValue(String enumType, String enumValue) {
		final EnumType type = getEnumType(enumType).some();
		final Session session = sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		final List<EnumValue> enumValues = session.createCriteria(EnumValue.class)
				.add(Restrictions.eq("typeID", type.getId()))
				.add(Restrictions.eq("name", enumValue))
				.setCacheable(true)
				.list();
		return Option.<EnumValue> iif(!enumValues.isEmpty(),
			new P1<EnumValue>() {
			
			@Override
			public EnumValue _1() {
				return enumValues.get(0);
			}
		
		});
	}

	@Override
	public List<GeneralJournalItem> getGeneralJournalItems(Option<Integer> categoryID) {
		final Session session = sessionFactory.getCurrentSession();
		
		if ( categoryID.isSome() ) {
			session.enableFilter(GeneralJournalItem.FILTER).setParameter("categoryID", categoryID.some());
		}
		@SuppressWarnings("unchecked")
		final List<GeneralJournalItem> list = session.createCriteria(
				GeneralJournalItem.class).list();
		if ( categoryID.isSome() ) {
			session.disableFilter(GeneralJournalItem.FILTER);
		}
		return ImmutableList.copyOf(list);
	}

	@Override
	public List<JobPosition> getJobPositions() {
		final Session session = sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		final List<JobPosition> list = session
				.createCriteria(JobPosition.class).list();
		return ImmutableList.copyOf(list);
	}

	@Override
	public List<AccountReceivableDurationItem> getAccountReceivableDurationItems() {
		final Session session = sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		final List<AccountReceivableDurationItem> list = session
				.createCriteria(AccountReceivableDurationItem.class).list();
		return ImmutableList.copyOf(list);
	}

	@Override
	public List<Duration> getDurations() {
		final Session session = sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		final List<Duration> list = session.createCriteria(Duration.class)
				.list();
		return ImmutableList.copyOf(list);
	}

	@Override
	public List<EmployeeFeeItem> getEmployeeFeeItems() {
		final Session session = sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		final List<EmployeeFeeItem> list = session.createCriteria(
				EmployeeFeeItem.class).list();
		return ImmutableList.copyOf(list);
	}

	@Override
	public List<EmployeeFeeSummaryItem> getEmployeeFeeSummaryItems() {
		final Session session = sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		final List<EmployeeFeeSummaryItem> list = session.createCriteria(
				EmployeeFeeSummaryItem.class).list();
		return ImmutableList.copyOf(list);
	}

	@Override
	public Option<Vehicle> getVehicle(Integer id) {
		final Session session = sessionFactory.getCurrentSession();
		final Vehicle result = (Vehicle) session.get(Vehicle.class, id);
		return Option.<Vehicle>fromNull(result);
	}
	
	@Override
	public Option<SalesServiceJournalItem> getSalesServiceJournalItem(Integer id) {
		final Session session = sessionFactory.getCurrentSession();
		final SalesServiceJournalItem result = (SalesServiceJournalItem) session.get(SalesServiceJournalItem.class, id);
		return Option.<SalesServiceJournalItem>fromNull(result);
	}
	
	@Override
	public Option<SalesServiceJournalItem> getSalesServiceJournalItem(final String name, final Integer categoryID) {
		final Session session = sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		final List<SalesServiceJournalItem> list = session.createCriteria(
				SalesServiceJournalItem.class)
				.add(Restrictions.eq("name", name))
				.add(Restrictions.eq("categoryID", categoryID))
				.list();

		return Option.<SalesServiceJournalItem>iif(!list.isEmpty(), new P1<SalesServiceJournalItem>() {

			@Override
			public SalesServiceJournalItem _1() {
				return list.get(0);
			}
			
		});
	}

	@Override
	public Option<SalesServiceJournalCategory> getSalesServiceJournalCategory(final Integer id) {
		final Session session = sessionFactory.getCurrentSession();
		final SalesServiceJournalCategory category = (SalesServiceJournalCategory) session.get(SalesServiceJournalCategory.class, id);
		return Option.<SalesServiceJournalCategory>fromNull(category);
	}
	
	@Override
	public Option<SalesServiceJournalCategory> getSalesServiceJournalCategory(final String name) {
		final Session session = sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		final List<SalesServiceJournalCategory> list = session.createCriteria(
				SalesServiceJournalCategory.class)
				.add(Restrictions.eq("name", name))
				.list();

		return Option.<SalesServiceJournalCategory>iif(!list.isEmpty(), new P1<SalesServiceJournalCategory>() {

			@Override
			public SalesServiceJournalCategory _1() {
				return list.get(0);
			}
			
		});
	}

	@Override
	public Option<Dealer> getDealer(final Integer dealerID) {
		final Session session = sessionFactory.getCurrentSession();
		final Dealer dealer = (Dealer) session.get(Dealer.class, dealerID);
		return Option.<Dealer>fromNull(dealer);
	}

	@Override
	public List<Department> getDepartments() {
		final Session session = sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		final List<Department> list = session.createCriteria(
				Department.class).list();
		return ImmutableList.copyOf(list);
	}

	@Override
	public Option<Department> getDepartment(Integer departmentID) {
		final Session session = sessionFactory.getCurrentSession();
		final Department department = (Department) session.get(Department.class, departmentID);
		return Option.<Department>fromNull(department);
	}
	
	@Override
	public Option<Department> getDepartment(String name) {
		final Session session = sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		final List<Department> departments = session.createCriteria(Department.class)
														.add(Restrictions.eq("name", name)).list();
		return Option.<Department> iif(!departments.isEmpty(),
				new P1<Department>() {

					@Override
					public Department _1() {
						return departments.get(0);
					}

				});
	}

	@Override
	public Option<JobPosition> getJobPosition(Integer positionID) {
		final Session session = sessionFactory.getCurrentSession();
		final JobPosition position = (JobPosition) session.get(JobPosition.class, positionID);
		return Option.<JobPosition>fromNull(position);
	}

	@Override
	public Option<AccountReceivableDurationItem> getAccountReceivableDurationItem(Integer itemID) {
		final Session session = sessionFactory.getCurrentSession();
		final AccountReceivableDurationItem item = (AccountReceivableDurationItem) session.get(AccountReceivableDurationItem.class, itemID);
		return Option.<AccountReceivableDurationItem>fromNull(item);
	}

	@Override
	public Option<Duration> getDuration(Integer durationID) {
		final Session session = sessionFactory.getCurrentSession();
		final Duration item = (Duration) session.get(Duration.class, durationID);
		return Option.<Duration>fromNull(item);
	}

	@Override
	public Option<EmployeeFeeItem> getEmployeeFeeItem(Integer itemID) {
		final Session session = sessionFactory.getCurrentSession();
		final EmployeeFeeItem item = (EmployeeFeeItem) session.get(EmployeeFeeItem.class, itemID);
		return Option.<EmployeeFeeItem>fromNull(item);
	}

	@Override
	public Option<EmployeeFeeSummaryItem> getEmployeeFeeSummaryItem(Integer itemID) {
		final Session session = sessionFactory.getCurrentSession();
		final EmployeeFeeSummaryItem item = (EmployeeFeeSummaryItem) session.get(EmployeeFeeSummaryItem.class, itemID);
		return Option.<EmployeeFeeSummaryItem>fromNull(item);
	}

	@Override
	public List<InventoryDurationItem> getInventoryDurationItems() {
		final Session session = sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		final List<InventoryDurationItem> list = session.createCriteria(
				InventoryDurationItem.class).list();
		return ImmutableList.copyOf(list);
	}

	@Override
	public Option<InventoryDurationItem> getInventoryDurationItem(Integer itemID) {
		final Session session = sessionFactory.getCurrentSession();
		final InventoryDurationItem item = (InventoryDurationItem) session.get(InventoryDurationItem.class, itemID);
		return Option.<InventoryDurationItem>fromNull(item);
	}

	@Override
	public Option<GeneralJournalItem> getGeneralJournalItem(Integer itemID) {
		final Session session = sessionFactory.getCurrentSession();
		final GeneralJournalItem result = (GeneralJournalItem) session.get(GeneralJournalItem.class, itemID);
		return Option.<GeneralJournalItem>fromNull(result);
	}
	
	@Override
	public Option<GeneralJournalCategory> getGeneralJournalCategory(Integer itemID) {
		final Session session = sessionFactory.getCurrentSession();
		final GeneralJournalCategory result = (GeneralJournalCategory) session.get(GeneralJournalCategory.class, itemID);
		return Option.<GeneralJournalCategory>fromNull(result);
	}

	@Override
	public List<GeneralJournalCategory> getGeneralJournalCategorys() {
		final Session session = sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		final List<GeneralJournalCategory> list = session.createCriteria(
				GeneralJournalCategory.class).list();
		return ImmutableList.copyOf(list);
	}

	@Override
	public Option<TaxJournalItem> getTaxJournalItem(Integer itemID) {
		final Session session = sessionFactory.getCurrentSession();
		final TaxJournalItem item = (TaxJournalItem) session.get(TaxJournalItem.class, itemID);
		return Option.<TaxJournalItem>fromNull(item);
	}

}
