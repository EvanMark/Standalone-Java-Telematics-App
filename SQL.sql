drop table JLOCATION;

create table JLOCATION (
Jkey varchar(100) not null primary key,
Name varchar(100) not null,
Id numeric(10) not null,
Coo_Type varchar(6) not null,
Coo_X numeric(10,6) not null,
Coo_Y numeric(10,6) not null
);

drop table JCONNECTION;


create table JCONNECTION (
CompKey varchar(200),
FromName varchar(100),
FromId numeric(10),
ToName varchar(100),
ToId numeric(10)
);
