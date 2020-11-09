-- sqlite3 demos.db

create table girl (
	id int primary key not null,
	cupsize varchar(1),
	age int default 18
);
