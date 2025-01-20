CREATE TABLE IF NOT EXISTS clients
(
    client_id  SERIAL PRIMARY KEY,
    name       VARCHAR(255) NOT NULL,
    birth_date DATE         NOT NULL
);

CREATE TABLE IF NOT EXISTS books
(
    book_id SERIAL PRIMARY KEY,
    title   VARCHAR(255)       NOT NULL,
    author  VARCHAR(255)       NOT NULL,
    isbn    VARCHAR(13) UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS borrowed_books
(
    id        SERIAL PRIMARY KEY,
    client_id INT NOT NULL REFERENCES clients (client_id),
    book_id   INT NOT NULL REFERENCES books (book_id),
    borrowed_at TIMESTAMP,
    CONSTRAINT uq_client_book UNIQUE (client_id, book_id)
);

CREATE INDEX IF NOT EXISTS idx_borrowings_client_id ON borrowed_books (client_id);
CREATE INDEX IF NOT EXISTS idx_borrowings_book_id ON borrowed_books (book_id);

-- покрывает запросы, где оба поля участвуют вместе
CREATE INDEX IF NOT EXISTS idx_borrowings_client_book ON borrowed_books (client_id, book_id);
