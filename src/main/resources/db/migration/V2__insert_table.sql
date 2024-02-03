INSERT INTO dishes (id, calories, proteins, fats, carbohydrates, name) VALUES
    (nextval('dishes_seq'), 139.3, 8.8, 6.2, 12.8, 'Беляш'),
    (nextval('dishes_seq'), 79.1, 4.2, 2.9, 9.7, 'Вареники с капустой'),
    (nextval('dishes_seq'), 266.2, 24.4, 15.2, 8.5, 'Зразы отбивные');

INSERT INTO products (id, calories, proteins, fats, carbohydrates, name) VALUES
    (nextval('products_seq'), 266.0, 8.85, 3.33, 46.2, 'Хлеб белый'),
    (nextval('products_seq'), 285.0, 7.9, 4.7, 47.6, 'Хлеб белый, приготовленный по рецепту, из маложирного (2%-ым) молока'),
    (nextval('products_seq'), 252.0, 12.45, 3.5, 36.71, 'Хлеб из цельного зерна'),
    (nextval('products_seq'), 548.0, 35.73, 43.27, 1.35, 'Бекон, печеный');

INSERT INTO users (id, archive, email, lastname, login, middle_name, name, password, phone, role, sex, weight, height) VALUES
    (nextval('user_seq'), false, 'user1@mail.ru', 'user1', 'user', 'middle_name', 'user', '$2a$10$0vNSN8i.XqVDHv4GUS6lxeS.KZGfvNw5MbNdAllLicejAlFGxUbh.', '7919257****', 'USER', 0, 70, 156),
    (nextval('user_seq'), false, 'admin@mail.ru', 'admin', 'admin', 'middle_name', 'admin', '$2a$10$0vNSN8i.XqVDHv4GUS6lxeS.KZGfvNw5MbNdAllLicejAlFGxUbh.', '7919257****', 'ADMIN', 1, 65, 180);

