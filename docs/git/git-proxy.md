# Add proxy from git
```
git config --global http.proxy http://proxy-host:proxy-port
export http_proxy=http://proxy-host:proxy-port
export https_proxy=$http_proxy
```


# remove proxy from git:
```
git config --global --unset https.proxy
git config --global --unset http.proxy
unset http_proxy
unset https_proxy
```