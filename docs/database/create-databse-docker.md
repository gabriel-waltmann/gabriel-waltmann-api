docker run 
    --name waltmann-db 
    -e POSTGRES_PASSWORD=1234 
    -p 5432:5432 
    -d postgres

docker exec -it waltmann-db bash

psql -U postgres

DATABASE CREATE waltmann-db;

exit