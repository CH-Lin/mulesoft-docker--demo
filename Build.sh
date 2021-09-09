#!/bin/bash

FINANCIAL=financial
EMPLOYEE=employee

build_employee() {
    cd ${EMPLOYEE}
    echo "Build Employee Server..."
    rm ../employee-server/employee-web.war
    mvn clean package
    mv web/target/employee-web.war ../employee-server/employee-web.war
    echo "Build Employee Server...done!"
    cd ..
}

build_financial() {
    cd ${FINANCIAL}
    ip=$1
    target=$2
    echo "Build Financial Server "${target}"..."
    rm ../financial-server-${target}/financial-web.war
    if [[ -f "web/src/main/resources/application.properties" ]]; then
        mv "web/src/main/resources/application.properties" "web/src/main/resources/application.properties_bk"
    fi
    cp "web/src/main/resources/application.properties.template" "web/src/main/resources/application.properties"
    sed -itmp "s#xx#${ip}#g" "web/src/main/resources/application.properties"
    mvn clean package
    mv web/target/financial-web.war ../financial-server-${target}/financial-web.war
    if [[ -f "web/src/main/resources/application.properties_bk" ]]; then
        rm "web/src/main/resources/application.properties"
        mv "web/src/main/resources/application.properties_bk" "web/src/main/resources/application.properties"
    fi
    rm web/src/main/resources/application.propertiestmp
    echo "Build Financial Server "${target}"...done!"
    cd ..
}

build_employee
build_financial 10 "jp"
build_financial 11 "usa"
exit 0
