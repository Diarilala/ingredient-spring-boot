# :bento: Ingredients and Dishes with Spring :fried_shrimp:
A project about Dishes and Ingredients with the SRP method for better maintanability
## :policeman: All the endpoints in the present time(might change in the future) :policewoman:

| Endpoint                                                | Description                                                                     |
|---------------------------------------------------------|---------------------------------------------------------------------------------|
| `GET /ingredients`                                      | return a list of JSON objects(ingredients)                                      |
| `GET /ingredients/{id}`                                 | return a JSON object with the matching id(ingredient)                           |
| `GET /ingredients/{id}/stock?at={temporal}&unit={unit}` | return a JSON object with the matching id at a given time and with a given unit |
| `GET /dishes`                                           | return a list of JSON object(dishes)                                            |
| `PUT /dishes/{id}/ingredients`                          | modify a dish with the given ingredient data given in the body                  |

## :hammer_and_wrench: Built With
- Java
- Spring Boot