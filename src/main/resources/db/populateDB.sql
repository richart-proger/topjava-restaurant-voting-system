DELETE
FROM user_roles;
DELETE
FROM dishes;
DELETE
FROM restaurants;
DELETE
FROM users;
DELETE
FROM votes;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password'),
       ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id)
VALUES ('USER', 100000),
       ('ADMIN', 100001);

INSERT INTO restaurants (name)
VALUES ('Italian restaurant'),
       ('Japan restaurant'),
       ('French restaurant');

INSERT INTO dishes (restaurant_id, name, price)
VALUES (100002, 'Pizza', 600),
       (100002, 'Fettuccine', 700),
       (100003, 'Sushi Set', 1350),
       (100003, 'Sashimi Set', 1100),
       (100004, 'Ratatouille', 700),
       (100004, 'Onion soup', 500);
--
-- INSERT INTO dishes (restaurant_id, name, price, date)
-- VALUES (100002, 'Pizza', 600, DATE_ADD(CURRENT_DATE, INTERVAL 2 DAY)),
--        (100002, 'Fettuccine', 700, DATE_ADD(CURRENT_DATE, INTERVAL 2 DAY));

INSERT INTO votes (user_id, restaurant_id, booking_date)
VALUES (100000, 100002, DATE_ADD(CURRENT_DATE, INTERVAL 1 DAY)),
       (100001, 100004, DATE_ADD(CURRENT_DATE, INTERVAL 2 DAY)),
       (100000, 100002, DATE_SUB(CURRENT_DATE, INTERVAL 3 DAY));