INSERT INTO Member (nick_name, password, created_date)
VALUES ('user1', 'password123', NOW());

INSERT INTO Account (amount, member_id)
VALUES (10000, 1);

INSERT INTO Performance (name, description, performance_start_date, performance_end_date)
VALUES ('Concert A', 'Amazing concert', '2023-09-01 19:00:00', '2023-09-01 22:00:00');

INSERT INTO Ticket (grade, fixed_price, seat_count, performance_id)
VALUES ('VIP', 50000, 50, 1),
       ('R', 40000, 100, 1),
       ('S', 30000, 150, 1);

INSERT INTO Member_Ticket (member_id, account_id, real_price)
VALUES (1, 1, 50000);
