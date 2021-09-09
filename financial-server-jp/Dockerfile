ARG BASE_CONTAINER=tomcat:9.0.52-jdk11-openjdk-buster
FROM $BASE_CONTAINER

LABEL maintainer="Che-Hung Lin <edward730109@gmail.com>"

WORKDIR $CATALINA_HOME

RUN rm -rf webapps; \
    mv webapps.dist webapps; \
	sed -itmp "s#</tomcat-users>#<!--</tomcat-users>-->#g" ./conf/tomcat-users.xml; \
	echo "  <role rolename=\"manager-gui\"/>" >> ./conf/tomcat-users.xml; \
	echo "  <role rolename=\"manager-script\"/>" >> ./conf/tomcat-users.xml; \
	echo "  <user username=\"demouser\" password=\"demo!pass\" roles=\"manager-gui\"/>" >> ./conf/tomcat-users.xml; \
	echo "  <user username=\"demouser-text\" password=\"demo!pass\" roles=\"manager-script\"/>" >> ./conf/tomcat-users.xml; \
	echo "</tomcat-users>" >> ./conf/tomcat-users.xml; \
	sed -itmp "s#1|0:0:0:0:0:0:0:1#1|0:0:0:0:0:0:0:1|.*#g" ./webapps/manager/META-INF/context.xml; \
	sed -itmp "s#1|0:0:0:0:0:0:0:1#1|0:0:0:0:0:0:0:1|.*#g" ./webapps/host-manager/META-INF/context.xml;

COPY financial-web.war $CATALINA_HOME/webapps/financial.war

EXPOSE 8080
CMD ["catalina.sh", "run"]
