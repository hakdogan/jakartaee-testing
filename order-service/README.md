# Order Service

The `Order Service` is responsible for the persisting of the `Order Demand` object from the client request to the `Database` after the credit card number inside the object is verified by the `Validation Service`.

```bash
curl -XPOST -H "Content-Type: application/json" http://localhost:9082/api/order/save -d '{
"productId": 1,
"count": 1,
"orderNote": "",
"cardNumber": "3545434434453453"}'
```

## To run

```shell script
mvn liberty:run
```

## To execute the tests

```shell script
mvn verify
```