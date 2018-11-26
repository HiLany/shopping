# shopping

This project is aim at learn that how to use spring cloud.

## Modules

```
|--shopping
	|--shopping-eureka-server 
	|--shopping-zuul
	|--shopping-config-server
	|--shopping-trubine
	|--shopping-product-server
	|--shopping-product-deal
	|--shopping-product-description
```



## Introduce of Modules

> shopping-eureka-server

It's Service Registry. you can access [localhost:8761](localhost:8761) and check what services had registried after you launched module.
 
> shopping-zuul

The main functions of this module are route forwarding and filters. The routing function is part of the microservice. such as `/shopping-product-server/**` forward requestes to `shopping-product-server` service.
 
> shopping-config-server

It's implement unified management of configuration files ,and real-time update files.

you can fetch configuration file with :

```
localhost:8888/{application}/{profile}/{label}
localhost:8888/{application}-{profile}.yml
localhost:8888/{label}/{application}-{profile}.yml
localhost:8888/{application}-{profile}.properties
localhost:8888/{label}/{application}-{profile}.properties
```

> shopping-trubine

This is an application that aggregates all of the relevant /hystrix.stream endpoints into a combined /turbine.stream for use in the Hystrix Dashboard.

> shopping-product-server （not complete yet）

It provided management of product ,include the addition,deletion and alteration of product.

> shopping-product-deal (not complete yet)

> shopping-product-description  (not complete yet)


 