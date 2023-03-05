INSERT INTO ROOM (NAME, ROWS_NUMBER, SEATS_IN_ROW_NUMBER)VALUES ('Room A', 3, 3);
INSERT INTO ROOM (NAME, ROWS_NUMBER, SEATS_IN_ROW_NUMBER) VALUES ('Room B', 4, 4);
INSERT INTO ROOM (NAME, ROWS_NUMBER, SEATS_IN_ROW_NUMBER) VALUES ('Room C', 5, 5);

INSERT INTO MOVIE (DESCRIPTION, TITLE) VALUES ('Some Desc1', 'Pulp Fiction');
INSERT INTO MOVIE (DESCRIPTION, TITLE) VALUES ('Some Desc2', 'Hateful 8');
INSERT INTO MOVIE (DESCRIPTION, TITLE) VALUES ('Some Desc3', 'Django');

INSERT INTO TICKET_TYPE (NAME, PRICE, CURRENCY) VALUES ('adult', 25, 'PLN');
INSERT INTO TICKET_TYPE (NAME, PRICE, CURRENCY) VALUES ('student', 18, 'PLN');
INSERT INTO TICKET_TYPE (NAME, PRICE, CURRENCY) VALUES ('child', 12.50, 'PLN');

INSERT INTO SCREENING (ID, MOVIE_ID, ROOM_ID, START_TIME, END_TIME)
VALUES ('179d0fe6-0b84-4e7d-b8fe-64eeed12db88', 1, 1, '2023-04-05T14:00:00', '2023-04-05T15:30:00');

INSERT INTO SCREENING (ID, MOVIE_ID, ROOM_ID, START_TIME, END_TIME)
VALUES ('a80be8de-0c61-4e0a-a3e5-95b055845c18', 1, 1, '2023-04-05T16:00:00', '2023-04-05T17:30:00');

INSERT INTO SCREENING (ID, MOVIE_ID, ROOM_ID, START_TIME, END_TIME)
VALUES ('cd174523-4630-4f36-9611-5a78d663e15a', 2, 2, '2023-04-05T15:30:00', '2023-04-05T17:00:00');

INSERT INTO SCREENING (ID, MOVIE_ID, ROOM_ID, START_TIME, END_TIME)
VALUES ('fd612a91-db41-4bc3-a794-f8c1a3d8e1d2', 2, 2, '2023-04-05T17:30:00', '2023-04-05T19:00:00');

INSERT INTO SCREENING (ID, MOVIE_ID, ROOM_ID, START_TIME, END_TIME)
VALUES ('4941fe3f-611b-48a2-b31d-ed2fc551069f', 3, 3, '2023-04-05T16:30:00', '2023-04-05T18:00:00');

INSERT INTO SCREENING (ID, MOVIE_ID, ROOM_ID, START_TIME, END_TIME)
VALUES ('bea17adf-9c7a-42b1-8f3a-8540655b5b45', 3, 3, '2023-04-05T18:30:00', '2023-04-05T20:00:00');

INSERT INTO RESERVATION (ID, EXPIRATION_DATE, FIRSTNAME, SURNAME, IS_PAID, SCREENING_ID)
VALUES ('6d00c415-777e-4d1d-a7f0-a3d5344c96ff', '2026-10-31T11:30:00', 'Jan', 'Wzięty-Późny', false, '4941fe3f-611b-48a2-b31d-ed2fc551069f');

INSERT INTO TICKET (ROW_NUMBER, SEAT_NUMBER, RESERVATION_ID, TICKET_TYPE_ID) VALUES (1, 1, '6d00c415-777e-4d1d-a7f0-a3d5344c96ff', 1);
INSERT INTO TICKET (ROW_NUMBER, SEAT_NUMBER, RESERVATION_ID, TICKET_TYPE_ID) VALUES (1, 2, '6d00c415-777e-4d1d-a7f0-a3d5344c96ff', 2);
INSERT INTO TICKET (ROW_NUMBER, SEAT_NUMBER, RESERVATION_ID, TICKET_TYPE_ID) VALUES (1, 3, '6d00c415-777e-4d1d-a7f0-a3d5344c96ff', 3);
