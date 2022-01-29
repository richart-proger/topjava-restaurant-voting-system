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

INSERT INTO dishes (restaurant_id, name, price, date)
VALUES (100003, 'Sushi Optimal Set', 1350, DATE_SUB(CURRENT_DATE, INTERVAL 5 DAY)),
       (100003, 'Sashimi Salmon Set', 1100, DATE_SUB(CURRENT_DATE, INTERVAL 5 DAY)),

       (100002, 'Pizza Margarita', 600, DATE_SUB(CURRENT_DATE, INTERVAL 3 DAY)),
       (100002, 'Fettuccine', 700, DATE_SUB(CURRENT_DATE, INTERVAL 3 DAY)),
       (100002, 'Lasagne', 950, DATE_SUB(CURRENT_DATE, INTERVAL 3 DAY)),

       (100004, 'Ratatouille', 700, DATE_SUB(CURRENT_DATE, INTERVAL 2 DAY)),
       (100004, 'Onion soup', 500, DATE_SUB(CURRENT_DATE, INTERVAL 2 DAY)),

       (100005, 'Tacos', 800, DATE_SUB(CURRENT_DATE, INTERVAL 2 DAY)),
       (100005, 'Burritos', 1050, DATE_SUB(CURRENT_DATE, INTERVAL 2 DAY)),

       (100004, 'Boeuf Bourguignon', 1850, DATE_SUB(CURRENT_DATE, INTERVAL 1 DAY)),
       (100004, 'Bouillabaisse', 1600, DATE_SUB(CURRENT_DATE, INTERVAL 1 DAY)),

       (100005, 'Burritos', 1050, DATE_SUB(CURRENT_DATE, INTERVAL 1 DAY)),
       (100005, 'Tamales', 1300, DATE_SUB(CURRENT_DATE, INTERVAL 1 DAY)),
       (100005, 'Quesadilla', 900, DATE_SUB(CURRENT_DATE, INTERVAL 1 DAY)),

       (100002, 'Pizza Red Diablo', 1000, CURRENT_DATE),
       (100002, 'Pappardelle', 800, CURRENT_DATE),

       (100003, 'Sushi Imperator Set', 1350, CURRENT_DATE),
       (100003, 'Sashimi Tuna Set', 1100, CURRENT_DATE),

       (100004, 'Ratatouille', 700, CURRENT_DATE),
       (100004, 'Onion soup', 500, CURRENT_DATE);

INSERT INTO votes (user_id, restaurant_id, booking_date)
VALUES (100000, 100003, DATE_SUB(CURRENT_DATE, INTERVAL 5 DAY)),
       (100000, 100002, DATE_SUB(CURRENT_DATE, INTERVAL 3 DAY)),
       (100001, 100004, DATE_SUB(CURRENT_DATE, INTERVAL 2 DAY)),
       (100001, 100004, DATE_SUB(CURRENT_DATE, INTERVAL 1 DAY)),
       (100000, 100004, CURRENT_DATE);
