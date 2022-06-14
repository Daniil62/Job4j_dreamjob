CREATE TABLE IF NOT EXISTS post (
id SERIAL PRIMARY KEY,
name TEXT,
description TEXT,
created DATE,
visible BOOLEAN,
city_id INT
);

CREATE TABLE IF NOT EXISTS candidate (
id SERIAL PRIMARY KEY,
name TEXT,
description TEXT,
created DATE,
photo bytea
);

CREATE TABLE IF NOT EXISTS users (
id SERIAL PRIMARY KEY,
email VARCHAR UNIQUE,
password TEXT,
name TEXT
);