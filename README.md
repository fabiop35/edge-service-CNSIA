Spring Cloud API Gateway: Circuit Breaker, Spring Session, Filters, Retry filter, Resielience4j, Rate limiter, Routes, Redis session data.


#create the project
curl https://start.spring.io/starter.zip -d groupId=com.cnsia -d artifactId=edge-service-CNSIA -d name=edge-service-CNSIA -d packageName=com.cnsia.edgeservice -d groupId=cnsia -d dependencies=cloud-gateway,devtools,actuator -d javaVersion=17 -d bootVersion=3.2.0 -d type=gradle-project -o edge-service-CNSIA.zip


https://github.com/ThomasVitale/cloud-native-spring-in-action/tree/sb-3-main

#Test circuit breaker
ab -n 21 -c 1 -m POST http://localhost:9000/orders

#Call a GET endpoint which both retries and fallback have been configure
ab -n 21 -c 1 -m GET http://localhost:9000/books
 
 #start Redis DB
 redis-server

### Request Rate Limiter ###
>_ http :9000/books
 HTTP/1.1 200 OK
 Content-Length: 0
 Content-Type: text/plain;charset=UTF-8
 X-RateLimit-Burst-Capacity: 20
 X-RateLimit-Remaining: -1
 X-RateLimit-Replenish-Rate: 10
 X-RateLimit-Requested-Tokens: 1

ToDo: gradlew test --tests EdgeServiceApplicationTests

#Test Catalog service
curl http://localhost:9000/books
