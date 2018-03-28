Camel Spring Project techlab-fuse-amqp
===========================

This project shows how to configure Fuse 6.3.0 on Karaf to use AMQP protocol 1.0 with AMQ 7 Broker or AMQ 6 Broker

## For AMQ 6 Broker embedded in Fuse Karaf Server
- [Download jboss-fuse-karaf-6.3.0.redhat-xxx.zip here](https://developers.redhat.com/products/fuse/download/)
- Unzip package

      unzip jboss-fuse-karaf-6.3.0.redhat-xxx.zip

- Add user admin admin
- Edit etc/activemq.xml on the transport connector part to enable AMQP protocol

      <transportConnectors>
        <transportConnector name="openwire" uri="tcp://${bindAddress}:${bindPort}"/>
        <transportConnector name="amqp" uri="amqp://0.0.0.0:5672"/>
      </transportConnectors>

## For AMQ 7 Broker

- Have AMQ 7 installed and running
	- [Download AMQ version 7.0.0+ here](https://developers.redhat.com/products/amq/download/)
	- unzip package `unzip amq-broker-7.1.0-bin.zip`
	- create a test instance

			cd amq-broker-7.1/bin
			./artemis create  --user admin --password admin --allow-anonymous Y ./../instances/eventbrk
			cd ./../instances/eventbrk/bin
			./artemis run

    - create a test topic

			./artemis address create --no-anycast --multicast --name test

## Build and run project

To build this project use

    mvn package

To run this project with Maven use

    mvn camel:run

To deploy this project on Fuse Karaf Server

- Log into Fuse console and type the following commands
- Install camel-amqp feature

      features:install camel-amqp

- Install project bundle

      install -s file:_PROJECTPATH_/techlab-fuse-amqp/target/techlab-fuse-amqp-1.0-SNAPSHOT.jar

- View

      log:tail
