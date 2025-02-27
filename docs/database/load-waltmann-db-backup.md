### Create backup from production waltmann db and up localy

1. docker exec -t waltmann-db pg_dumpall -c -U postgres > dump_`date +%Y-%m-%d"_"%H_%M_%S`.sql

2. scp root@000.00.00.00:~/dump_YYYY-MM-DD_HH_MM_SS.sql ./dump_YYYY-MM-DD_HH_MM_SS.sql

3. exit

4. If you already has a created database:
4.1 docker exec -it waltmann-db bash
4.2 psql -h localhost postgres postgres
4.3 DROP DATABASE waltmann;
4.4 CREATE DATABASE waltmann;
4.5 exit 
4.6 exit 

5. docker cp ./dump_YYYY-MM-DD_HH_MM_SS.sql waltmann-db:/dump_YYYY-MM-DD_HH_MM_SS.sql

6. docker exec -it waltmann-db psql -U postgres -d waltmann -f dump_2025-02-27_17_50_17.sql