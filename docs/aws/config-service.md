### copy .jar to ec2

1. scp ./target/waltmann-api-0.0.1-SNAPSHOT.jar ec2-user@00.000.000.00:/home/ec2-user
2. create service file: sudo nano /etc/systemd/system/waltmann-api.service
3. config service:
```
[Unit]
Description=Waltmann API
After=network.target

[Service]
User=ec2-user
ExecStart=/usr/bin/java -jar /home/ec2-user/waltmann-api-0.0.1-SNAPSHOT.jar
Restart=always

[Install]
WantedBy=multi-user.target
```
4. start service: sudo systemctl start waltmann-api.service
5. enable service: sudo systemctl enable waltmann-api.service
6. daemon-reload: sudo systemctl daemon-reload