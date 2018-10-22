DELETE FROM user_roles;
DELETE FROM meals;
DELETE FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password) VALUES
  ('User', 'user@yandex.ru', 'password'),
  ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id) VALUES
  ('ROLE_USER', 100000),
  ('ROLE_ADMIN', 100001);

INSERT INTO meals (description, datetime, user_id) VALUES
('meal_user','2018-8-22 23:00:29', 100000),
('meal_user','2018-9-22 23:00:29', 100000),
('meal_user','2018-10-22 23:00:29', 100000),
('meal_user','2018-11-22 23:00:29', 100000),
('meal_user','2018-8-22 21:00:29', 100000),
('meal_user','2018-9-22 21:00:29', 100000),
('meal_user','2018-10-22 21:00:29', 100000),
('meal_user','2018-11-22 21:00:29', 100000),
('meal_admin','2018-8-22 23:00:29', 100001),
('meal_admin','2018-9-22 23:00:29', 100001),
('meal_admin','2018-10-22 23:00:29', 100001),
('meal_admin','2018-11-22 23:00:29', 100001),
('meal_admin','2018-12-22 23:00:29', 100001);