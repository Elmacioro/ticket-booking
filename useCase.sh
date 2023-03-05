printf "1. Fetch movie screenings that start between 15:20 and 18:00 on 05.04.2023: \n" &&
curl --location --request GET 'localhost:8080/api/screening?start=2023-04-05T15:20:00&end=2023-04-05T18:00:00' &&
printf "\n\n2. Fetch Django screening details with an id of 4941fe3f-611b-48a2-b31d-ed2fc551069f: \n" &&
curl --location --request GET 'localhost:8080/api/screening/4941fe3f-611b-48a2-b31d-ed2fc551069f' &&
printf "\n\n3. Book 3 seats for the screening. One adult, one child and one student: \n" &&
curl --location --request POST 'localhost:8080/api/reservation' \
--header 'Content-Type: application/json' \
--data-raw '{

"screeningId" : "4941fe3f-611b-48a2-b31d-ed2fc551069f",
"firstname" : "Jan",
"surname" : "Wesoły-Wolny",
"tickets" : [
    {
        "row" : 2,
        "seatNumber" : 1,
        "ticketTypeName" : "student"
    },
    {
        "row" : 2,
        "seatNumber" : 2,
        "ticketTypeName" : "child"
    },
    {
        "row" : 2,
        "seatNumber" : 3,
        "ticketTypeName" : "adult"
    }
]

}'
