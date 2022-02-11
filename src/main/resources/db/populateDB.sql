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
       ('French restaurant'),
       ('Mexican restaurant');

INSERT INTO dishes (restaurant_id, restaurant_name, name, price, date)
VALUES (100003, 'Japan restaurant', 'Sushi Optimal Set', 1350, DATE_SUB(CURRENT_DATE, INTERVAL 5 DAY)),
       (100003, 'Japan restaurant', 'Sashimi Salmon Set', 1100, DATE_SUB(CURRENT_DATE, INTERVAL 5 DAY)),

       (100002, 'Italian restaurant', 'Pizza Margarita', 600, DATE_SUB(CURRENT_DATE, INTERVAL 3 DAY)),
       (100002, 'Italian restaurant', 'Fettuccine', 700, DATE_SUB(CURRENT_DATE, INTERVAL 3 DAY)),
       (100002, 'Italian restaurant', 'Lasagne', 950, DATE_SUB(CURRENT_DATE, INTERVAL 3 DAY)),

       (100004, 'French restaurant', 'Ratatouille', 700, DATE_SUB(CURRENT_DATE, INTERVAL 2 DAY)),
       (100004, 'French restaurant', 'Onion soup', 500, DATE_SUB(CURRENT_DATE, INTERVAL 2 DAY)),

       (100005, 'Mexican restaurant', 'Tacos', 800, DATE_SUB(CURRENT_DATE, INTERVAL 2 DAY)),
       (100005, 'Mexican restaurant', 'Burritos', 1050, DATE_SUB(CURRENT_DATE, INTERVAL 2 DAY)),

       (100004, 'French restaurant', 'Boeuf Bourguignon', 1850, DATE_SUB(CURRENT_DATE, INTERVAL 1 DAY)),
       (100004, 'French restaurant', 'Bouillabaisse', 1600, DATE_SUB(CURRENT_DATE, INTERVAL 1 DAY)),

       (100005, 'Mexican restaurant', 'Burritos', 1050, DATE_SUB(CURRENT_DATE, INTERVAL 1 DAY)),
       (100005, 'Mexican restaurant', 'Tamales', 1300, DATE_SUB(CURRENT_DATE, INTERVAL 1 DAY)),
       (100005, 'Mexican restaurant', 'Quesadilla', 900, DATE_SUB(CURRENT_DATE, INTERVAL 1 DAY)),

       (100002, 'Italian restaurant', 'Pizza Red Diablo', 1000, CURRENT_DATE),
       (100002, 'Italian restaurant', 'Pappardelle', 800, CURRENT_DATE),

       (100003, 'Japan restaurant', 'Sushi Imperator Set', 1350, CURRENT_DATE),
       (100003, 'Japan restaurant', 'Sashimi Tuna Set', 1100, CURRENT_DATE),

       (100004, 'French restaurant', 'Ratatouille', 700, CURRENT_DATE),
       (100004, 'French restaurant', 'Onion soup', 500, CURRENT_DATE);

INSERT INTO votes (user_id, user_email, restaurant_id, restaurant_name, booking_date)
VALUES (100000, 'user@yandex.ru', 100003, 'Japan restaurant', DATE_SUB(CURRENT_DATE, INTERVAL 5 DAY)),
       (100000, 'user@yandex.ru', 100002, 'Italian restaurant', DATE_SUB(CURRENT_DATE, INTERVAL 3 DAY)),
       (100001, 'admin@gmail.com', 100004, 'French restaurant', DATE_SUB(CURRENT_DATE, INTERVAL 2 DAY)),
       (100001, 'admin@gmail.com', 100004, 'French restaurant', DATE_SUB(CURRENT_DATE, INTERVAL 1 DAY)),
       (100000, 'user@yandex.ru', 100004, 'French restaurant', CURRENT_DATE);