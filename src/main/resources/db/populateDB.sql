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

INSERT INTO meals (description, datetime, calories, user_id) VALUES
('meal_user1'  ,'2018-8-22 12:00:29' ,  500 , 100000),
('meal_user2'  ,'2018-8-22 23:00:29' ,  100 , 100000),
('meal_user3'  ,'2018-11-22 12:00:29',  800 , 100000),
('meal_user4'  ,'2018-11-22 23:00:29',  400 , 100000),
('meal_admin1' ,'2018-9-22 23:00:29' ,  200 , 100001),
('meal_admin2' ,'2018-12-22 12:00:29',  500 , 100001);