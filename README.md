# Book store API

A simple book store RESTful web service with books CRUD & checkout books with promotion.

## Build

###  With Docker

```bash
git clone https://github.com/Adel-Ewis/book-store-api.git
cd book-store-api
docker build -t bookstore . 
docker run -p 8000:8000 bookstore
```

### With maven

```bash
./mvnw spring-boot:run
```



## Testing

``` 
./mvnw test
```

### APIs

Open API documentation is provided at `http://localhost:8000/swagger-ui/index.html`

also postman collection is provided in project files.


**Samples :**  

checkout API with promotion

```
curl --location 'http://localhost:8000/api/v1/checkout' \
--header 'Content-Type: application/json' \
--data '{
    "books": [
        {
            "id": "c697a29d-5e08-4832-9b6c-2538435184d1",
            "quantity": 2,
            "name": "The Adventures of Alice"
        }
    ],
    "promotion": "FICTION50"
}'
```

