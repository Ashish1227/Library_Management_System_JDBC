CREATE DATABASE lmsdb;
USE lmsdb;

GRANT ALL PRIVILEGES ON lmsdb.* TO 'ashishgatsy'@'localhost';

CREATE TABLE student (
    student_id VARCHAR(100) NOT NULL,
    student_name VARCHAR(100) NOT NULL,
    student_password VARCHAR(100) NOT NULL,
    book_id VARCHAR(100),
    CONSTRAINT pk_student PRIMARY KEY (student_id)
);

CREATE TABLE book (
    book_id VARCHAR(100) NOT NULL,
    book_pbyear INTEGER NOT NULL,
    book_name VARCHAR(100) NOT NULL,
    book_author VARCHAR(100) NOT NULL,
    issued_by VARCHAR(100),
    CONSTRAINT pk_book PRIMARY KEY (book_id)
);

CREATE TABLE librarian (
    librarian_id VARCHAR(100) NOT NULL,
    librarian_name VARCHAR(100) NOT NULL,
    librarian_password VARCHAR(100) NOT NULL,
    CONSTRAINT pk_librarian PRIMARY KEY (librarian_id)
);

CREATE TABLE admin (
    admin_id VARCHAR(100) NOT NULL,
    admin_name VARCHAR(100) NOT NULL,
    admin_password VARCHAR(100) NOT NULL,
    CONSTRAINT pk_admin PRIMARY KEY (admin_id)
);
