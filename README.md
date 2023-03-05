# ticket-booking


### Additional assumptions
- Requested time interval for point 1. of the scenario can be at max 1 week.
- Search for movie screenings for point 1. of the scenario can apply only to future screenings.
- Each row has the same number of seats
- Reservation expiration date is set to 5 minutes after booking the seats
- All tickets for single reservation must have the same currency

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
[{"movieTitle":"Django","screeningTimes":[{"screeningId":"4941fe3f-611b-48a2-b31d-ed2fc551069f","start":"2023-04-05T16:30:00","end":"2023-04-05T18:00:00"}]},{"movieTitle":"Hateful 8","screeningTimes":[{"screeningId":"cd174523-4630-4f36-9611-5a78d663e15a","start":"2023-04-05T15:30:00","end":"2023-04-05T17:00:00"},{"screeningId":"fd612a91-db41-4bc3-a794-f8c1a3d8e1d2","start":"2023-04-05T17:30:00","end":"2023-04-05T19:00:00"}]},{"movieTitle":"Pulp Fiction","screeningTimes":[{"screeningId":"a80be8de-0c61-4e0a-a3e5-95b055845c18","start":"2023-04-05T16:00:00","end":"2023-04-05T17:30:00"}]}]

2. Fetch Hateful 8 screening details with an id of 4941fe3f-611b-48a2-b31d-ed2fc551069f: 
{"roomName":"Room C","rowsNumber":5,"seatsInRowNumber":5,"availableSeats":[{"rowNumber":1,"seatNumber":4},{"rowNumber":1,"seatNumber":5},{"rowNumber":2,"seatNumber":1},{"rowNumber":2,"seatNumber":2},{"rowNumber":2,"seatNumber":3},{"rowNumber":2,"seatNumber":4},{"rowNumber":2,"seatNumber":5},{"rowNumber":3,"seatNumber":1},{"rowNumber":3,"seatNumber":2},{"rowNumber":3,"seatNumber":3},{"rowNumber":3,"seatNumber":4},{"rowNumber":3,"seatNumber":5},{"rowNumber":4,"seatNumber":1},{"rowNumber":4,"seatNumber":2},{"rowNumber":4,"seatNumber":3},{"rowNumber":4,"seatNumber":4},{"rowNumber":4,"seatNumber":5},{"rowNumber":5,"seatNumber":1},{"rowNumber":5,"seatNumber":2},{"rowNumber":5,"seatNumber":3},{"rowNumber":5,"seatNumber":4},{"rowNumber":5,"seatNumber":5}]}

3. Book 3 seats for the screening. One adult, one child and one student: 
{"reservationId":"ee5660c8-8bbf-4ffa-be35-32983f541720","priceDto":{"currency":"PLN","amount":55.50},"expirationTime":"2023-03-05T12:28:15.956369874"}
```
