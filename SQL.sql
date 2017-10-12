drop table JLOCATION;

create table JLOCATION (
key varchar2(100) not null primary key,
Name varchar2(100) not null,
Id number(10) not null,
Coo_Type varchar2(6) not null,
Coo_X number(10,6) not null,
Coo_Y number(10,6) not null
);

drop table JCONNECTION;


create table JCONNECTION (
CompKey varchar2(200),
FromName varchar2(100),
FromId number(10),
ToName varchar2(100),
ToId number(10)
);
