<<<<<<< HEAD
# mercadolibre-api
Servicio de gestion de productos - prueba tecnica para ingresar a mercado libre
=======
# MercadoLibre API (Demo)

## Descripción
API REST en **Spring Boot** que gestiona productos tipo MercadoLibre.  
Los datos se almacenan en el archivo `products.json` (persistencia en archivo, sin base de datos).

## Endpoints
Base: `/api/products`

- `GET /get` → Lista todos los productos.
- `GET /get/{id}` → Obtiene un producto por su ID.
- `POST /create` → Crea un producto.
- `PUT /update/{id}` → Actualiza un producto.
- `DELETE /delete/{id}` → Elimina un producto.

### Ejemplo de request (crear/actualizar)
Body JSON:

```json
{
	"name": "Teclado mecánico",
	"category": "Periféricos",
	"price": 150,
	"stock": 10,
	"description": "Switch azul"
}
```

### Ejemplos de respuesta
- 200/201 OK/CREATED → Objeto `ProductResponseDto` con el `id` asignado.
- 204 NO CONTENT → En delete.

## Validaciones
`ProductResponseDto` aplica:
- `name`: requerido (no vacío)
- `category`: requerido (no vacío)
- `price`: `>= 100` y positivo
- `stock`: `>= 1` y positivo

## Manejo de errores (GlobalExceptionHandler)
- 404 Not Found

```json
{ "codigo": 404, "mensaje": "Producto no encontrado" }
```

- 400 Bad Request (validación)

```json
{
	"codigo": 400,
	"mensaje": "Datos inválidos",
	"errores": [ { "campo": "price", "mensaje": "El precio debe ser un valor positivo" } ]
}
```

- 500 Internal Server Error

```json
{ "codigo": 500, "mensaje": "Error inesperado", "detalles": "..." }
```

## Ejecución rápida
```bash
mvn spring-boot:run
```

## Notas técnicas
- Stack: Java 17, Spring Boot 3, Jackson.
- Persistencia en archivo: se escribe en `src/main/resources/products.json` en ejecución normal.
- En tests, el servicio se configura para NO escribir en el archivo (sin efectos colaterales).

## Estrategia técnica
- Se usó GenAI para acelerar generación de modelos/servicios/controladores.
- Buenas prácticas REST y manejo de errores centralizado.
- Revisa `run.md` para ejemplos de prueba con PowerShell y cómo ejecutar tests.

## Diagramas de secuencia
Consulta los flujos principales en `docs/sequence-diagrams.md` (Mermaid).

## Colleción de postman
Curl con las distintas consultas del servicio en `docs\Mercado Libre.postman_collection.json`.
>>>>>>> c436b74 (create product management service)
