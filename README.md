# Spring boot Prometheus Exporter
Open source prometheus exporter library for Springboot apps. Exports Springboot actuator metrics into Prometheus format and exposes them at /prometheus endpoint.

## How to use

Clone this repository
 ```
 git clone https://github.com/tshumal/spring-boot-prometheus-exporter.git
 ```

 Then install as maven library
 ```
 mvn clean install
 ```

 Then include the dependency in your pom.xml
 ```
<dependency>
    <groupId>io.linx.prometheus</groupId>
    <artifactId>spring-boot-prometheus-exporter</artifactId>
    <version>0.0.1-SNAPSHOT</version>
</dependency>
```

When you run your springboot app you should be able to see Prometheus metrics at the /prometheus endpoint.
