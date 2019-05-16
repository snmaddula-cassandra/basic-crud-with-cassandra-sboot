CREATE KEYSPACE IF NOT EXISTS ALPHA WITH REPLICATION = { 'class': 'SimpleStrategy', 'replication_factor': 1}

CREATE TABLE IF NOT EXISTS PRODUCTS (
	id bigint,
	title text,
	description text,
	price double,
	primary key(id, title)
);
	