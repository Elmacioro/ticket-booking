INSERT INTO ROOM (ID, NAME, ROWS_NUMBER, SEATS_IN_ROW_NUMBER) VALUES (1, 'Room A', 5, 5);

INSERT INTO MOVIE (ID, DESCRIPTION, TITLE) VALUES (1, 'Some Desc1', 'Pulp Fiction');

INSERT INTO SCREENING (ID, MOVIE_ID, ROOM_ID, START_TIME, END_TIME)
VALUES ('d4ca0e71-ba14-4fcf-b449-e00bb5b2d91e', 1, 1, '2023-04-05T11:30:00', '2023-04-05T12:30:00');

INSERT INTO TICKET_TYPE (ID, NAME, PRICE, CURRENCY) VALUES (1, 'adult', 25, 'PLN');

INSERT INTO RESERVATION (ID, EXPIRATION_DATE, FIRSTNAME, SURNAME, IS_PAID, SCREENING_ID)
VALUES ('cce89643-97ab-4aef-9205-65e69d29cc41', '2023-04-05T09:30:00', 'Jan', 'Wzięty-Późny', false, 'd4ca0e71-ba14-4fcf-b449-e00bb5b2d91e');

INSERT INTO RESERVATION (ID, EXPIRATION_DATE, FIRSTNAME, SURNAME, IS_PAID, SCREENING_ID)
VALUES ('9b6428e7-1392-4e2a-8d4f-4d7a0f217ba8', '2023-04-05T10:00:00', 'Anna', 'Fąkier', true, 'd4ca0e71-ba14-4fcf-b449-e00bb5b2d91e');

INSERT INTO RESERVATION (ID, EXPIRATION_DATE, FIRSTNAME, SURNAME, IS_PAID, SCREENING_ID)
VALUES ('cb2a5b84-7669-4bd1-8065-bc4d1f629017', '2023-04-05T10:15:00', 'Karol', 'Chłodny-Wesoły', false, 'd4ca0e71-ba14-4fcf-b449-e00bb5b2d91e');


INSERT INTO TICKET (ID, ROW_NUMBER, SEAT_NUMBER, RESERVATION_ID, TICKET_TYPE_ID) VALUES (1, 1, 1, 'cce89643-97ab-4aef-9205-65e69d29cc41', 1);
INSERT INTO TICKET (ID, ROW_NUMBER, SEAT_NUMBER, RESERVATION_ID, TICKET_TYPE_ID) VALUES (2, 1, 2, 'cce89643-97ab-4aef-9205-65e69d29cc41', 1);
INSERT INTO TICKET (ID, ROW_NUMBER, SEAT_NUMBER, RESERVATION_ID, TICKET_TYPE_ID) VALUES (3, 1, 3, 'cce89643-97ab-4aef-9205-65e69d29cc41', 1);
INSERT INTO TICKET (ID, ROW_NUMBER, SEAT_NUMBER, RESERVATION_ID, TICKET_TYPE_ID) VALUES (4, 3, 1, '9b6428e7-1392-4e2a-8d4f-4d7a0f217ba8', 1);
INSERT INTO TICKET (ID, ROW_NUMBER, SEAT_NUMBER, RESERVATION_ID, TICKET_TYPE_ID) VALUES (5, 3, 2, '9b6428e7-1392-4e2a-8d4f-4d7a0f217ba8', 1);
INSERT INTO TICKET (ID, ROW_NUMBER, SEAT_NUMBER, RESERVATION_ID, TICKET_TYPE_ID) VALUES (6, 5, 5, 'cb2a5b84-7669-4bd1-8065-bc4d1f629017', 1);
