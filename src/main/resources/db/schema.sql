CREATE TABLE IF NOT EXISTS items (
  id serial primary key,
  name VARCHAR(50) NOT NULL,
  description VARCHAR(350) NOT NULL,
  create_time TIMESTAMP NOT NULL
);