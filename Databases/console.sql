CREATE TABLE ACCOUNTS_DETAILS
(
  ACCOUNT_ID INT not null ,
  ACCOUNT_PASSWORD VARCHAR(100) not null ,
  FIRST_NAME VARCHAR(100)NOT NULL,
  LAST_NAME VARCHAR(100) not null ,
  PHONE bigint not null ,
  Date_Of_Birth VARCHAR(100) not null ,
  EMAIL VARCHAR(100) not null,
  Address VARCHAR(125) not null,
  Balance BIGINT DEFAULT 0,
  Status BIT DEFAULT  0,
  Login_Message VARCHAR(256) DEFAULT 'Account has not been approved by manager'
)

drop table ACCOUNTS_DETAILS
select * from ACCOUNTS_DETAILS

CREATE PROCEDURE CREATE_ACCOUNT

 @ACCOUNT_ID int,
 @ACCOUNT_PASSWORD varchar(100),
 @FIRST_NAME varchar(100),
 @LAST_NAME varchar(100),
 @PHONE bigint,
 @Date_Of_Birth VARCHAR(100),
 @EMAIL varchar(100),
 @Address varchar(125),
 @Status BIT,
 @Message varchar(125)
as
  begin
    insert into ACCOUNTS_DETAILS
    (ACCOUNT_ID, ACCOUNT_PASSWORD, FIRST_NAME, LAST_NAME, PHONE,Date_Of_Birth, EMAIL,Address,Status,Login_Message)
    values
    (@ACCOUNT_ID,@ACCOUNT_PASSWORD,@FIRST_NAME,@LAST_NAME,@PHONE,@Date_Of_Birth,@EMAIL,@Address,@Status,@Message)

  end

DELETE ACCOUNTS_DETAILS where ACCOUNT_ID=1003
update ACCOUNTS_DETAILS set ACCOUNT_PASSWORD where ACCOUNT_ID=1001
SELECT * from ACCOUNTS_DETAILS where ACCOUNT_ID=1001
Drop TABLE  ACCOUNTS_DETAILS

Drop procedure Deposit

CREATE PROCEDURE Deposit
@Deposit_Amount BIGINT,
@ACCOUNT_ID Int
as
  begin
    update ACCOUNTS_DETAILS Set Balance=@Deposit_Amount WHERE ACCOUNT_ID=@ACCOUNT_ID
  end

select * from ACCOUNTS_DETAILS
where ACCOUNT_ID=1001
union
select * from ACCOUNTS_DETAILS
where ACCOUNT_ID=1002


CREATE TABLE TRANSACTION_HISTORY_Table
(
  Account_ID INT not null ,
  Previous_Balance BIGINT,
  Withdrawn_Balance BIGINT,
  Deposited_Balance BIGINT,
  Withdrawn_by_Transaction BIGINT,
  Deposited_by_Transaction BIGINT,
  Current_Balance BIGINT,
  Transfered_From_Account_ID int,
  Transferd_To_Account_ID int,
  Operation_Date_And_Time DATETIME,
  )

select * from TRANSACTION_HISTORY_Table

drop table TRANSACTION_HISTORY_Table

CREATE TABLE Manager_Account
(
  ACCOUNT_ID INT not null ,
  ACCOUNT_PASSWORD VARCHAR(100) not null ,
  FIRST_NAME VARCHAR(100)NOT NULL,
  LAST_NAME VARCHAR(100) not null ,
  PHONE bigint not null ,
  EMAIL VARCHAR(100) not null
)

CREATE PROCEDURE CREATE_ACCOUNT_Manager

  @ACCOUNT_ID int,
  @ACCOUNT_PASSWORD varchar(100),
  @FIRST_NAME varchar(100),
  @LAST_NAME varchar(100),
  @PHONE bigint,
  @EMAIL varchar(100)
as
begin
  insert into Manager_Account
  (ACCOUNT_ID, ACCOUNT_PASSWORD, FIRST_NAME, LAST_NAME, PHONE, EMAIL)
  values
  (@ACCOUNT_ID,@ACCOUNT_PASSWORD,@FIRST_NAME,@LAST_NAME,@PHONE,@EMAIL)

end

SELECT * From Manager_Account

UPDATE ACCOUNTS_DETAILS
set Status=0


CREATE TABLE LOAN
(
  ACCOUNT_ID INT NOT NULL ,
  LOAN_ID INT,
  LOAN_AMOUNT BIGINT NOT NULL,
  RATE_OF_INTEREST DOUBLE PRECISION ,
  LOAN_TENTURE INT NOT NULL,
  STATUS BIT NOT NULL DEFAULT 0,
  MESSAGE VARCHAR(125) DEFAULT 'Loan request have not been approved by manager'
)

SELECT * FROM LOAN

CREATE PROCEDURE UPDATE_LOAN
@ACCOUNT_ID INT,
@LOAN_AMOUNT BIGINT,
@LOAN_TENTURE INT
AS
  BEGIN
    INSERT INTO LOAN
    (ACCOUNT_ID, LOAN_AMOUNT,LOAN_TENTURE)
    VALUES
    (@ACCOUNT_ID,@LOAN_AMOUNT,@LOAN_TENTURE)
  end

UPDATE LOAN
set Status=0
