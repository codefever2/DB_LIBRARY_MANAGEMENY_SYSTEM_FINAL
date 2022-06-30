create schema Library_Management_system;
use Library_Management_system;
desc admininfo;
create table Language_list(LanguageID varchar(30) not null,LanguageName varchar(30) not null,primary key(LanguageID));
create table user_history_table(userId varchar(30),userPassword varchar(30),userName varchar(30),userMobileNumber bigint,userBorrowedBookCount int,userLateFee float);
ALTER TABLE `Library_Management_system`.`Language_list` 
CHANGE COLUMN `LanguageID` `LanguageID` INT NOT NULL ;
Insert into Language_list values(1,'ENGLISH');
Insert into Language_list values(2,'TAMIL');
Insert into Language_list values(3,'HINDI');
select * from Language_list;
Insert into Category_list values(1,'NON_FICTION');
Insert into Category_list values(2,'FICTION');
Insert into Category_list values(3,'HISTORY');
Insert into Category_list values(4,'CHILDREN');
Insert into Category_list values(5,'CRIME');
Insert into Category_list values(6,'FANTASY');
select * from category_list;
Insert into Publisher_list values(1,'JAICO');
Insert into Publisher_list values(2,'PRATHAM');
Insert into Publisher_list values(3,'KARADI TALES');
Insert into Publisher_list values(4,'ARIHANT');
Insert into Publisher_list values(5,'PENGUIN');
Insert into Publisher_list values(6,'HACHETTE');
select * from publisher_list;
insert into userinfo values('U1','J@1996s','kaviya',9791198505,0,0.0);
insert into userinfo values('U2','P@1996s','praveen',8807878718,0,0.0);
desc userinfo;
select * from userinfo;
select * from user_history_table;
desc admininfo;
ALTER TABLE `Library_Management_system`.`admininfo`
CHANGE COLUMN `adminmobilenumber` `adminmobilenumber` bigINT NOT NULL ;
insert into admininfo values('A1','N@1995k','Nandha Kumar',9600192437,0);
insert into admininfo values('A2','K@1995n','Karthikeyan',9790849259,0);
select * from admininfo;
desc book_list;
insert into book_list value(1,'Ponniyin selvan',2,'Kalki',2,3,0,1,1,NULL,null,'ISBN4');
insert into book_list value(2,'The secret Place',1,'Dan Brown',1,3,0,1,1,NULL,null);
insert into book_list value(3,'The Secret Place 2',1,'Dan Brown',1,3,0,1,1,NULL,null);
insert into book_list value(4,'Ammachis Investigation',1,'Sudeep Roy',1,4,0,1,1,NULL,null);
select * from book_list;
select case when exists( select * from userinfo where userid='U1' and userpassword ='J@1996s')
then 'true'
else 'false'
end;
create table StatusUpdate(userId varchar(100));
drop table StatusUpdate;
select * from statusUpdate;

show processlist;
set global event_scheduler=on;

show events;
drop event updateLateFee;
drop event updatestatustable;
drop event deletestatustable;
drop event updatestatus;

create Event updateLateFee on schedule every 1 day 
do
insert into statusupdate(userId)
select BookBorrowedByUserID  from book_list  where NOW()>BookExpectedReturndate and Eventstatus = 0 ;

create Event updatestatus on schedule every 1 day
do
update book_list set EventStatus=1  where  NOW()>BookExpectedReturndate and Eventstatus = 0;

create Event updateStatusTable on schedule every 1 day
do
update  userinfo set  userLatefee= userLatefee+40 where userId in(select userId from statusUpdate);

create Event deletestatustable on schedule every 1 day 
do
delete  from statusupdate;
show variables like "max_connections";
set global max_connections = 100000;

select * from admininfo;
create table TotalUSER_ADMIN_BOOK_BASECount(ID int,COUNT long,primary Key(ID));
select * from TotalUSER_ADMIN_BOOK_BASECount;
delete from admininfo where adminId='A0';