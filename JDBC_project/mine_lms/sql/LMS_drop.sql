ALTER TABLE student
    DROP FOREIGN KEY fk_book_id;

ALTER TABLE book
    DROP FOREIGN KEY fk_issued_by;

DROP TABLE book;
DROP TABLE librarian;
DROP TABLE student;
DROP TABLE admin;

DROP DATABASE lmsdb;