# Error when git push

## Full error log:
Couldn't establish connection to proxy: Connection refused
Connection closed by UNKNOWN port 65535
fatal: Could not read from remote repository.

Please make sure you have the correct access rights
and the repository exists.

## Fix:
```
sudo nano ~/.ssh/config

// comment follow line
# ProxyCommand corkscrew host-proxy port-proxy %h %p
```
