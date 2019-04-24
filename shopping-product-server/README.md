# env command:

```
docker run --name docker-mysql -e MYSQL_ROOT_PASSWORD=123456 -e MYSQL_DATABASE=shopping -p 3306:3306 -d mysql:5.5.62
```

```
docker run --name docker-mongo -p 27017:27017 -e MONGO_INITDB_ROOT_USERNAME=mongoadmin -e MONGO_INITDB_ROOT_PASSWORD=123456   -d mongo:latest
```

```
db.createUser(
  {
    user: "lanyang",
    pwd: "123456",
    roles: [ { role: "userAdminAnyDatabase", db: "admin" }, "readWriteAnyDatabase" ]
  }
)
```

```
db.adminCommand({setParameter: 1, internalQueryExecMaxBlockingSortBytes:100302864});
```