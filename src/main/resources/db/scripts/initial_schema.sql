create table games(
	id varchar(500) primary key,
	secret varchar(6) not null,
	created_at timestamp not null,
	active boolean not null default true
)