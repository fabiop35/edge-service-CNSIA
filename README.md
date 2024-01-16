Spring Cloud API Gateway: Circuit Breaker, Spring Session, Filters, Retry filter, Resielience4j, Rate limiter, Routes, Redis session data.


#create the project
curl https://start.spring.io/starter.zip -d groupId=com.cnsia -d artifactId=edge-service-CNSIA -d name=edge-service-CNSIA -d packageName=com.cnsia.edgeservice -d groupId=cnsia -d dependencies=cloud-gateway,devtools,actuator -d javaVersion=17 -d bootVersion=3.2.0 -d type=gradle-project -o edge-service-CNSIA.zip


https://github.com/ThomasVitale/cloud-native-spring-in-action/tree/sb-3-main

#Test circuit breaker
ab -n 21 -c 1 -m POST http://localhost:9000/orders

#Call a GET endpoint which both retries and fallback have been configure
ab -n 21 -c 1 -m GET http://localhost:9000/books
 
#start Redis DB - prerequisite
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

# KEYCLOAK #
#start Keycloak
KEYCLOAK_ADMIN=user KEYCLOAK_ADMIN_PASSWORD=password ./bin/kc.sh start-dev
/opt/keycloak-23.0.4/bin/kc.sh start-dev 

#start an authenticated session
./kcadm.sh config credentials --server http://localhost:8080 --realm master --user user --password password

#Create a realm
./kcadm.sh create realms -s realm=PolarBookshop -s enabled=true

#create roles
./kcadm.sh create roles -r PolarBookshop -s name=employee
./kcadm.sh create roles -r PolarBookshop -s name=customer

#create users
./kcadm.sh create users -r PolarBookshop -s username=isabelle -s firstName=Isabelle -s lastName=Dahl -s enabled=true

./kcadm.sh add-roles -r PolarBookshop --uusername isabelle --rolename employee --rolename customer

./kcadm.sh create users -r PolarBookshop -s username=bjorn -s firstName=Bjorn -s lastName=Vinterberg -s enabled=true

./kcadm.sh add-roles -r PolarBookshop --uusername bjorn --rolename customer

#Register Edge Service as an OAuth2 Client in the PolarBookshop realm
./kcadm.sh create clients -r PolarBookshop -s clientId=edge-service -s enabled=true -s publicClient=false -s secret=polar-keycloak-secret -s 'redirectUris=["http://localhost:9000", "http://localhost:9000/login/oauth2/code/*"]' 
