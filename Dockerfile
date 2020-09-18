FROM tomee:8-jre-7.0.8-plume
ADD /target/rest-api-1.0.war  /usr/local/tomee/webapps/
ADD /tomcat-users.xml /usr/local/tomee/conf/

EXPOSE 8080

CMD ["catalina.sh", "run"]