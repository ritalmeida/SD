#!/usr/bin/env bash
#@REM ************************************************************************************
#@REM Description: run previously MAIL_TO_ADDR all batch files
#@REM Author: Rui S. Moreira
#@REM Date: 10/04/2018
#@REM pwd: /Users/rui/Documents/NetBeansProjects/SD/src/edu/ufp/sd/rabbitmqservices
#@REM ************************************************************************************

#@REM ======================== Use Shell Parameters ========================
#@REM Script usage: setenv <role> (where role should be: producer / consumer)
export SCRIPT_ROLE=$1

#@REM ======================== CHANGE BELOW ACCORDING YOUR PROJECT and PC SETTINGS ========================
#@REM ==== PC STUFF ====
export JDK=/Users/Ritaa/Library/Java/JavaVirtualMachines/corretto-16.0.2/Contents/Home
export NETBEANS=NetBeans
export INTELLIJ=IntelliJ
export CURRENT_IDE=${INTELLIJ}
#export CURRENT_IDE=¢{NETBEANS}
export USERNAME=rita

#@REM ==== JAVA NAMING STUFF ====
export JAVAPROJ_NAME=SD
export JAVAPROJ=/Users/Ritaa/Documents/FACULDADE/SistemasDistribuidos/SD
export RABBITMQ_SERVICES_FOLDER=edu/ufp/inf/sd/rabbitmqservices
export RABBITMQ_SERVICES_PACKAGE=edu.ufp.inf.sd.rabbitmqservices
export PACKAGE=_02_workqueues
export QUEUE_NAME_PREFIX=workqueues
export EXCHANGE_NAME_PREFIX=NA
export PRODUCER_CLASS_PREFIX=NewTask
export CONSUMER_CLASS_PREFIX=Worker

#@REM ==== NETWORK STUFF ====
export BROKER_HOST=localhost
export BROKER_PORT=5672
export BROKER_HTTP_PORT=15672

#@REM ==== SMTP ====
export JAVA_JAVAXMAIL_TOOLS=${JAVA_LIB_FOLDER}/javax.mail-1.6.2.jar:${JAVA_LIB_FOLDER}/javax.activation-1.2.0.jar
export CLASSPATH=${JAVAPROJ_CLASSES_FOLDER}:${JAVA_RABBITMQ_TOOLS}:${JAVA_JAVAXMAIL_TOOLS}

#@REM ======================== DO NOT CHANGE AFTER THIS POINT ========================
export JAVAPACKAGE=${RABBITMQ_SERVICES_PACKAGE}.${PACKAGE}
export JAVAPACKAGEROLE=${JAVAPACKAGE}.${SCRIPT_ROLE}
export JAVAPACKAGEROLEPATH=${RABBITMQ_SERVICES_FOLDER}/${PACKAGE}/${SCRIPT_ROLE}
export JAVASCRIPTSPATH=${RABBITMQ_SERVICES_FOLDER}/${PACKAGE}/runscripts
export BROKER_QUEUE=${QUEUE_NAME_PREFIX}_queue
export BROKER_EXCHANGE=${EXCHANGE_NAME_PREFIX}_exchange
export SERVICE_URL=http://${BROKER_HOST}:${BROKER_PORT}

export PATH=${PATH}:${JDK}/bin

if [[ "${CURRENT_IDE}" == "${NETBEANS}" ]]; then
    export JAVAPROJ_SRC=src
    export JAVAPROJ_CLASSES=build/classes/
    export JAVAPROJ_DIST=dist
    export JAVAPROJ_DIST_LIB=lib
elif [[ "${CURRENT_IDE}" == "${INTELLIJ}" ]]; then
    export JAVAPROJ_SRC=src
    export JAVAPROJ_CLASSES=out/production/${JAVAPROJ_NAME}/
    export JAVAPROJ_DIST=out/artifacts/${JAVAPROJ_NAME}
    export JAVAPROJ_DIST_LIB=lib
fi

export JAVAPROJ_CLASSES_FOLDER=${JAVAPROJ}/${JAVAPROJ_CLASSES}
export JAVAPROJ_JAR_FILE=${JAVAPROJ}/${JAVAPROJ_DIST}/${JAVAPROJ_NAME}.jar
export JAVA_LIB_FOLDER=${JAVAPROJ}/${JAVAPROJ_DIST_LIB}
export JAVA_RABBITMQ_TOOLS=${JAVA_LIB_FOLDER}/amqp-client-5.11.0.jar:${JAVA_LIB_FOLDER}/slf4j-api-1.7.30.jar:${JAVA_LIB_FOLDER}/slf4j-simple-1.7.30.jar

export CLASSPATH=${JAVAPROJ_CLASSES_FOLDER}:${JAVA_RABBITMQ_TOOLS}
#export CLASSPATH=${JAVAPROJ_JAR_FILE}
#export CLASSPATH=${JAVAPROJ_JAR_FILE}:${JAVA_RABBITMQ_TOOLS}
#export CLASSPATH=.:${JAVAPROJ_CLASSES_FOLDER}

export ABSPATH2CLASSES=${JAVAPROJ_CLASSES_FOLDER}
export ABSPATH2SRC=${JAVAPROJ}/${JAVAPROJ_SRC}





