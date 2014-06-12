UPDATE GeneralJournalCategory SET name = '非经营性损益销项', timestamp = current_timestamp WHERE id = 7;

UPDATE GeneralJournalItem SET name = '销售顾问佣金', timestamp = current_timestamp WHERE id = 1;
UPDATE GeneralJournalItem SET name = '非销售\服务顾问员工薪酬（不含经理）', timestamp = current_timestamp WHERE id = 5;
UPDATE GeneralJournalItem SET name = '库存维护、修护费用', timestamp = current_timestamp WHERE id = 8;
UPDATE GeneralJournalItem SET name = '员工差旅、培训费用', timestamp = current_timestamp WHERE id = 10;
UPDATE GeneralJournalItem SET name = '差旅费、招待费', timestamp = current_timestamp WHERE id = 23;
UPDATE GeneralJournalItem SET name = '通讯费（电话、传真、网络）', timestamp = current_timestamp WHERE id = 29;
UPDATE GeneralJournalItem SET name = '其它（含安全生产费用等）', timestamp = current_timestamp WHERE id = 30;
UPDATE GeneralJournalItem SET name = '房产税（按受益期归集）', timestamp = current_timestamp WHERE id = 35;
UPDATE GeneralJournalItem SET name = '营业外支出（非捐赠）', timestamp = current_timestamp WHERE id = 51;

INSERT INTO GeneralJournalItem SELECT 59, '销售\服务顾问薪酬', 2, 1, current_timestamp;
INSERT INTO GeneralJournalItem SELECT 60, '金融保险费用-其它', 2, 1, current_timestamp;

UPDATE SalesServiceJournalCategory SET name = '新SUV/MPV等大型车零售', timestamp = current_timestamp WHERE id = 2;

UPDATE SalesServiceJournalItem SET name = '他店销售', timestamp = current_timestamp WHERE id = 4;
UPDATE SalesServiceJournalItem SET name = '其它附加产品收入', timestamp = current_timestamp WHERE id = 10;
UPDATE SalesServiceJournalItem SET name = '二手车零售', timestamp = current_timestamp WHERE id = 11;
UPDATE SalesServiceJournalItem SET name = '二手车翻新', timestamp = current_timestamp WHERE id = 12;
UPDATE SalesServiceJournalItem SET name = '延保+续保', timestamp = current_timestamp WHERE id = 25;
UPDATE SalesServiceJournalItem SET name = '客户付费工单备件', timestamp = current_timestamp WHERE id = 26;
UPDATE SalesServiceJournalItem SET name = '保修工单备件', timestamp = current_timestamp WHERE id = 27;
UPDATE SalesServiceJournalItem SET name = '内部工单备件', timestamp = current_timestamp WHERE id = 28;
UPDATE SalesServiceJournalItem SET name = '客户付费钣喷工单备件', timestamp = current_timestamp WHERE id = 30;
UPDATE SalesServiceJournalItem SET name = '其它营业外收入（废旧件、废油收入）', timestamp = current_timestamp WHERE id = 33;
UPDATE SalesServiceJournalItem SET name = '客户付费钣喷工单工时', timestamp = current_timestamp WHERE id = 38;
UPDATE SalesServiceJournalItem SET name = '保修钣喷工单工时', timestamp = current_timestamp WHERE id = 39;
UPDATE SalesServiceJournalItem SET name = '内部钣喷工单工时', timestamp = current_timestamp WHERE id = 40;
UPDATE SalesServiceJournalItem SET name = '技工薪酬以及未使用工时', timestamp = current_timestamp WHERE id = 44;

INSERT INTO SalesServiceJournalItem SELECT 48, '精品收入', 3, 0, '', current_timestamp;
INSERT INTO SalesServiceJournalItem SELECT 49, '技工薪酬', 5, 0, '', current_timestamp;

DELETE FROM SalesServiceJournalItem WHERE id = 13;
DELETE FROM SalesServiceJournalItem WHERE id = 14;
DELETE FROM SalesServiceJournalItem WHERE id = 41;
DELETE FROM SalesServiceJournalItem WHERE id = 42;
DELETE FROM SalesServiceJournalItem WHERE id = 43;

UPDATE AccountReceivableDurationItem SET name = '售后部门应收账款', timestamp = current_timestamp WHERE id = 1;
UPDATE AccountReceivableDurationItem SET name = '售前部门应收账款', timestamp = current_timestamp WHERE id = 2;
UPDATE AccountReceivableDurationItem SET name = '主机厂应收账款', timestamp = current_timestamp WHERE id = 3;
UPDATE AccountReceivableDurationItem SET name = '租赁部门应收账款', timestamp = current_timestamp WHERE id = 4;

UPDATE EmployeeFeeItem SET name = '美容工时', timestamp = current_timestamp WHERE id = 6;

UPDATE JobPosition SET name = '投资人/总经理', timestamp = current_timestamp WHERE id = 1;
UPDATE JobPosition SET name = '金融保险销售人员', timestamp = current_timestamp WHERE id = 5;