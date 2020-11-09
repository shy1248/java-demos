-- sqlite3 demos.db

create table user (
	id int primary key not null,
	names varchar(20),
	age int default 18
);
