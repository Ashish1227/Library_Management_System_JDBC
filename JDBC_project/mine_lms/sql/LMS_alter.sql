ALTER TABLE student
    ADD CONSTRAINT fk_book_id FOREIGN KEY (book_id) REFERENCES book(book_id);

ALTER TABLE book
    ADD CONSTRAINT fk_issued_by FOREIGN KEY (issued_by) REFERENCES student(student_id);