# Add network proxy to Maven

1. Edit maven config file
    - At linux:
   ```sudo nano ~/.m2/settings.xml```

2. Add proxy
    ```
    <settings>
      <proxies>
       <proxy>
          <id>my-proxy</id>
          <active>true</active>
          <protocol>http</protocol>
          <host>proxy-host</host>
          <port>proxy-port</port>
        </proxy>
      </proxies>
    </settings>
   ```