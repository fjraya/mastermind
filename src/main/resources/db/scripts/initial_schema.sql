create table games(
	id varchar(500) primary key,
	secret varchar(6) not null,
	created_at timestamp not null,
	active boolean not null default true
);

create table game_guesses (
	game_id varchar(255) NOT NULL,
	combination varchar(6) NOT NULL,
	black_pegs integer not null,
	white_pegs integer not null,
	created_at timestamp not null
);