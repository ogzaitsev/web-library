-- Добавление клиентов
INSERT INTO clients (name, birth_date)
VALUES ('Анна Петровна Иванова', '1990-05-15'),
       ('Петр Михайлович Смирнов', '1985-12-03'),
       ('Мария Александровна Козлова', '1995-08-22'),
       ('Сергей Дмитриевич Попов', '1988-02-28');

-- Добавление книг
INSERT INTO books (title, author, isbn)
VALUES ('Война и мир', 'Лев Толстой', '9785171147730'),
       ('Преступление и наказание', 'Федор Достоевский', '9785171358839'),
       ('Мастер и Маргарита', 'Михаил Булгаков', '9785170878895'),
       ('Java. Полное руководство', 'Герберт Шилдт', '9785907144606'),
       ('Spring в действии', 'Крейг Уоллс', '9785446107322'),
       ('Отцы и дети', 'Иван Тургенев', '9785170902601'),
       ('Анна Каренина', 'Лев Толстой', '9785171147556'),
       ('Идиот', 'Федор Достоевский', '9785171358846'),
       ('Тихий Дон', 'Михаил Шолохов', '9785170959969'),
       ('Мертвые души', 'Николай Гоголь', '9785171153588'),
       ('Герой нашего времени', 'Михаил Лермонтов', '9785171153663'),
       ('Вишневый сад', 'Антон Чехов', '9785171153670'),
       ('Обломов', 'Иван Гончаров', '9785171153687'),
       ('Белая гвардия', 'Михаил Булгаков', '9785170878901'),
       ('Spring Boot в действии', 'Крейг Уоллс', '9785446110797'),
       ('Чистый код', 'Роберт Мартин', '9785446107971'),
       ('Head First. Паттерны проектирования', 'Эрик Фримен', '9785496000871'),
       ('Java concurrency на практике', 'Брайан Гетц', '9785932861912'),
       ('Алгоритмы на Java', 'Роберт Седжвик', '9785446109234'),
       ('Java. Эффективное программирование', 'Джошуа Блох', '9785370036330'),
       ('Философия Java', 'Брюс Эккель', '9785496011273');

-- Добавление записей о взятых книгах
INSERT INTO borrowed_books (client_id, book_id, borrowed_at)
VALUES (1, 1, '2024-09-21 00:24:13'),
       (1, 3, '2024-10-26 00:25:11'),
       (2, 2, '2024-11-20 00:23:44'),
       (3, 4, '2024-11-22 00:24:49'),
       (4, 5, '2024-01-20 00:25:23'),
       (2, 6, '2024-07-26 00:25:03');