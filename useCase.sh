printf "1. Fetch movie screenings that start between 15:20 and 18:00 on 05.04.2023: \n" &&
curl --location --request GET 'localhost:8080/api/screening?start=2023-04-05T15:20:00&end=2023-04-05T18:00:00' &&
printf "\n\n2. Fetch Hateful 8 screening details with an id of 5: \n" &&
curl --location --request GET 'localhost:8080/api/screening/5'