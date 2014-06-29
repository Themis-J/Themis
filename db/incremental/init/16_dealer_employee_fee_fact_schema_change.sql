ALTER TABLE DealerEmployeeFeeFact ADD feeTypeID integer NOT NULL;
ALTER TABLE DealerEmployeeFeeFact DROP CONSTRAINT DEFF_PK;
ALTER TABLE DealerEmployeeFeeFact ADD CONSTRAINT DEFF_PK PRIMARY KEY (timestamp, timeID, dealerID, departmentID, itemID, feeTypeID);
ALTER TABLE DealerEmployeeFeeFact DROP CONSTRAINT DEFF_Unique;
ALTER TABLE DealerEmployeeFeeFact ADD CONSTRAINT DEFF_Unique UNIQUE (timeEnd, timeID, dealerID, departmentID, itemID, feeTypeID, version);