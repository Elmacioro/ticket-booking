INSERT INTO ROOM (ID, NAME, ROWS_NUMBER, SEATS_IN_ROW_NUMBER) VALUES (1, 'Room A', 3, 3);

INSERT INTO MOVIE (ID, DESCRIPTION, TITLE) VALUES (1, 'Some Desc1', 'Pulp Fiction');
INSERT INTO MOVIE (ID, DESCRIPTION, TITLE) VALUES (2, 'Some Desc2', 'Hateful 8');

INSERT INTO SCREENING (ID, MOVIE_ID, ROOM_ID, START_TIME, END_TIME)
VALUES ('2469f6b5-5445-41ec-90ce-f0778ff6f45f', 2, 1, '2023-04-05T11:30:00', '2023-04-05T12:30:00');

INSERT INTO SCREENING (ID, MOVIE_ID, ROOM_ID, START_TIME, END_TIME)
VALUES ('b914549e-d288-4989-856e-e0ffa4ec7803', 2, 1, '2023-04-05T13:00:00', '2023-04-05T14:00:00');

INSERT INTO SCREENING (ID, MOVIE_ID, ROOM_ID, START_TIME, END_TIME)
VALUES ('c91e3ca5-f1b7-445c-9122-e5ad919632ef', 1, 1, '2023-04-05T14:30:00', '2023-04-05T15:30:00');

INSERT INTO SCREENING (ID, MOVIE_ID, ROOM_ID, START_TIME, END_TIME)
VALUES ('4d16d07e-29ed-43f1-898e-d750195078d7', 1, 1, '2023-04-05T16:00:00', '2023-04-05T17:00:00');

INSERT INTO SCREENING (ID, MOVIE_ID, ROOM_ID, START_TIME, END_TIME)
VALUES ('3f8a2ed7-14bd-44fe-84bc-90a90c682b0a', 2, 1, '2023-04-05T17:30:00', '2023-04-05T18:30:00');