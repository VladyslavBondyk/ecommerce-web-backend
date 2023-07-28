-- Passwords r in format Password<UserLetter>123
-- Encrypted using https://www.javainuse.com/onlineBcrypt
INSERT INTO local_user (email, first_name, last_name, password, username, email_verified)
    VALUES ('UserA@Junit.com', 'UserA-FirstName', 'UserA-LastName', '$2a$10$WrXlpuelArrep8KSAHlnou5uIh4J4NSs8FsscZM2Lu6DR6QXHWA.m', 'UserA', true)
          , ('UserB@Junit.com', 'UserB-FirstName', 'UserB-LastName', '$2a$10$dVE7C0XCqU8qTSO.qm.z3eRv4u2juCLT2tUzGvkNXCyY5XPSmNfDy', 'UserB', false)