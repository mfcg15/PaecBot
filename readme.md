
# PaecBot

## Explicación del proyecto
Resumen de lo que hace este proyecto

## Video

[![Watch the video](https://i.ibb.co/Rjdm7zD/youtube.jpg)](https://www.youtube.com/watch?v=fURgvzGF0E0)

## Stack Tecnológico 
Define que stack usaste, tanto en backend como en frontend

## Documentación del codigo
### Endpoints API
Define como funciona tu API

#### Get all items

```http
  GET /api/items
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `api_key` | `string` | **Required**. Your API key |

#### Get item

```http
  GET /api/items/${id}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `id`      | `string` | **Required**. Id of item to fetch |

#### add(num1, num2)

Takes two numbers and returns the sum.
