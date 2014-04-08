## Create install script
http://askubuntu.com/questions/519/how-do-i-write-an-application-install-shell-script

## Install JVM
/opt/jdk/jdk1.7.0_25
## Install Tomcat
apache-tomcat-7.0.42
## Install postgresql
postgresql/9.1
## Install nginx
http://wiki.nginx.org/Install
http://wiki.nginx.org/Pitfalls
http://nginx.org/en/docs/http/ngx_http_limit_conn_module.html

## Configure nginx with tomcat
http://webapp.org.ua/sysadmin/setting-up-nginx-ssl-reverse-proxy-for-tomcat/


## Firewall setup
https://www.digitalocean.com/community/articles/how-to-setup-a-firewall-with-ufw-on-an-ubuntu-and-debian-cloud-server

## under "jni/native" (Optional)
http://tomcat.apache.org/native-doc/
http://www.sheroz.com/installing-apache-tomcat-native-linux-ubuntu-1204
http://skzr-org.iteye.com/blog/1131218
./configure --with-apr=/usr/local/apr --with-java-home=$JAVA_HOME --with-ssl=/usr/bin/openssl --prefix=/opt/tomcat

## Generate ssl certificate (Optional)
http://www.digicert.com/ssl.htm
http://www.akadia.com/services/ssh_test_certificate.html
(btw, for self-signed cert & key pairs generated on www.o2oee.com, password is "jdc168")

## Or
http://www.startssl.com/?app=1
