# ticket-booking

---

### Additional assumptions
- Each row has the same number of seats
- Reservation expiration date is set to 5 minutes after booking the seats

---

### How to build and run

To build and run the app you can use provided bash script
```
./buildAndRun.sh
```
Or you can use mvnw directly
```
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev
```

System uses in-memory database with initialized dummy data.

---
You can use useCase.sh bash script to execute required use case.
It performs 3 REST calls and prints responses to the console.
```
./useCase.sh
```

Example output:

```json
1. Fetch movie screenings that start between 15:20 and 18:00 on 05.04.2023: 
[{"movieId":3,"movieTitle":"Django","screeningTimes":[{"screeningId":5,"start":"2023-04-05T16:30:00","end":"2023-04-05T18:00:00"}]},{"movieId":2,"movieTitle":"Hateful 8","screeningTimes":[{"screeningId":3,"start":"2023-04-05T15:30:00","end":"2023-04-05T17:00:00"},{"screeningId":4,"start":"2023-04-05T17:30:00","end":"2023-04-05T19:00:00"}]},{"movieId":1,"movieTitle":"Pulp Fiction","screeningTimes":[{"screeningId":2,"start":"2023-04-05T16:00:00","end":"2023-04-05T17:30:00"}]}]

2. Fetch Hateful 8 screening details with an id of 5: 
{"roomName":"Room C","roomRowsNumber":5,"roomSeatsInRowNumber":5,"bookedSeats":[{"rowNumber":1,"seatInRowNumber":1},{"rowNumber":1,"seatInRowNumber":2},{"rowNumber":1,"seatInRowNumber":3},{"rowNumber":2,"seatInRowNumber":1},{"rowNumber":2,"seatInRowNumber":2},{"rowNumber":2,"seatInRowNumber":3}],"freeSeats":[{"rowNumber":1,"seatInRowNumber":4},{"rowNumber":1,"seatInRowNumber":5},{"rowNumber":2,"seatInRowNumber":4},{"rowNumber":2,"seatInRowNumber":5},{"rowNumber":3,"seatInRowNumber":1},{"rowNumber":3,"seatInRowNumber":2},{"rowNumber":3,"seatInRowNumber":3},{"rowNumber":3,"seatInRowNumber":4},{"rowNumber":3,"seatInRowNumber":5},{"rowNumber":4,"seatInRowNumber":1},{"rowNumber":4,"seatInRowNumber":2},{"rowNumber":4,"seatInRowNumber":3},{"rowNumber":4,"seatInRowNumber":4},{"rowNumber":4,"seatInRowNumber":5},{"rowNumber":5,"seatInRowNumber":1},{"rowNumber":5,"seatInRowNumber":2},{"rowNumber":5,"seatInRowNumber":3},{"rowNumber":5,"seatInRowNumber":4},{"rowNumber":5,"seatInRowNumber":5}]}

3. Book 3 seats for the screening. One adult, one child and one student: 
{"dateTime":"2023-03-01T17:18:35.192924318","description":"Provided seats have been already booked by another client"}
```
