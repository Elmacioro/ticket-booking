# ticket-booking


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
```agsl
1. Fetch movie screenings that start between 15:20 and 18:00 on 05.04.2023: 
[{"movieId":3,"movieTitle":"Django","screeningTimes":[{"screeningId":5,"start":"2023-04-05T16:30:00","end":"2023-04-05T18:00:00"}]},{"movieId":2,"movieTitle":"Hateful 8","screeningTimes":[{"screeningId":3,"start":"2023-04-05T15:30:00","end":"2023-04-05T17:00:00"},{"screeningId":4,"start":"2023-04-05T17:30:00","end":"2023-04-05T19:00:00"}]},{"movieId":1,"movieTitle":"Pulp Fiction","screeningTimes":[{"screeningId":2,"start":"2023-04-05T16:00:00","end":"2023-04-05T17:30:00"}]}]

2. Fetch Hateful 8 screening details with an id of 5: 
{"roomName":"Room C","rowsNumber":5,"seatsInRowNumber":5,"bookedSeats":[{"rowNumber":1,"seatNumber":1},{"rowNumber":1,"seatNumber":2},{"rowNumber":1,"seatNumber":3}],"availableSeats":[{"rowNumber":1,"seatNumber":4},{"rowNumber":1,"seatNumber":5},{"rowNumber":2,"seatNumber":1},{"rowNumber":2,"seatNumber":2},{"rowNumber":2,"seatNumber":3},{"rowNumber":2,"seatNumber":4},{"rowNumber":2,"seatNumber":5},{"rowNumber":3,"seatNumber":1},{"rowNumber":3,"seatNumber":2},{"rowNumber":3,"seatNumber":3},{"rowNumber":3,"seatNumber":4},{"rowNumber":3,"seatNumber":5},{"rowNumber":4,"seatNumber":1},{"rowNumber":4,"seatNumber":2},{"rowNumber":4,"seatNumber":3},{"rowNumber":4,"seatNumber":4},{"rowNumber":4,"seatNumber":5},{"rowNumber":5,"seatNumber":1},{"rowNumber":5,"seatNumber":2},{"rowNumber":5,"seatNumber":3},{"rowNumber":5,"seatNumber":4},{"rowNumber":5,"seatNumber":5}]}

3. Book 3 seats for the screening. One adult, one child and one student: 
{"reservationId":2,"priceDto":{"currency":"PLN","amount":55.50},"expirationTime":"2023-03-04T12:57:10.094815284"}
```
