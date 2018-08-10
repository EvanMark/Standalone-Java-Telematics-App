drop table JLOCATION;

create table JLOCATION (
  Jkey varchar(100) not null primary key ,
  Name varchar(100) not null ,
  Id numeric(10) not null ,
  Coo_Type varchar(6) not null ,
  Coo_X numeric(10,6) not null ,
  Coo_Y numeric(10,6) not NULL
);

drop table JCONNECTION;

create table JCONNECTION (
  CompKey varchar(200),
  FromName varchar(100),
  FromId numeric(10),
  ToName varchar(100),
  ToId numeric(10)
);

select *
from JLOCATION;

select *
from JCONNECTION;

create user 'go_swiss_user'@'localhost' identified by 'FSvM79k2';

drop user 'go_swiss_user'@'localhost';

grant CREATE on go_swiss.* to 'go_swiss_user'@'localhost';
grant drop on go_swiss.* to 'go_swiss_user'@'localhost';
grant insert on go_swiss.* to 'go_swiss_user'@'localhost';
grant select on go_swiss.* to 'go_swiss_user'@'localhost';
grant delete on go_swiss.* to 'go_swiss_user'@'localhost';
grant update on go_swiss.* to 'go_swiss_user'@'localhost';

flush privileges;

show grants for go_swiss_user;

SELECT * FROM mysql.user;
