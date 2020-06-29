# Validation Service

The `Validation Service` is responsible for validating the credit card number that passed by the `Order Service`. 

```bash
curl -XGET -H "Content-Type: application/json" http://localhost:9080/api/validation/3545434434453453
```

## To run

```shell script
mvn liberty:run
```

## To execute the tests

```shell script
mvn verify
```
