CREATE TABLE post (
id SERIAL PRIMARY KEY,
name TEXT,
description TEXT,
created DATE,
visible BOOLEAN,
city_id INT
);