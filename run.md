# Instrucciones para ejecutar y probar MercadoLibre API

## Requisitos
- Java 17+
- Maven

## Ejecución
1. Desde la raíz del proyecto, levanta la app:
    ```powershell
    mvn spring-boot:run
    ```
2. La API quedará en `http://localhost:8080`.

## Endpoints disponibles
Base: `http://localhost:8080/api/products`

- `GET /get` → Lista todos los productos
- `GET /get/{id}` → Obtiene producto por ID
- `POST /create` → Crea producto
- `PUT /update/{id}` → Actualiza producto
- `DELETE /delete/{id}` → Elimina producto

## Probar con PowerShell (Invoke-RestMethod)

- Listar productos:
   ```powershell
   irm http://localhost:8080/api/products/get | ConvertTo-Json -Depth 10
   ```

- Obtener por id:
   ```powershell
   irm http://localhost:8080/api/products/get/1 | ConvertTo-Json -Depth 10
   ```

- Crear:
   ```powershell
   $body = @{ name = 'Teclado'; category = 'Periféricos'; price = 150; stock = 5; description = 'Switch azul' } | ConvertTo-Json
   irm http://localhost:8080/api/products/create -Method POST -ContentType 'application/json' -Body $body | ConvertTo-Json -Depth 10
   ```

- Actualizar:
   ```powershell
   $body = @{ name = 'Teclado Pro'; category = 'Periféricos'; price = 200; stock = 8; description = 'Switch rojo' } | ConvertTo-Json
   irm http://localhost:8080/api/products/update/1 -Method PUT -ContentType 'application/json' -Body $body | ConvertTo-Json -Depth 10
   ```

- Eliminar:
   ```powershell
   irm http://localhost:8080/api/products/delete/1 -Method DELETE -SkipHttpErrorCheck
   ```

## Validaciones y errores
- Si faltan campos obligatorios o valores mínimos, la API devuelve 400 con detalle de errores.
- Si no existe el recurso, devuelve 404 con mensaje.

## Ejecutar tests
```powershell
mvn -q -DskipITs test
```

Nota: Los tests del servicio no escriben en `products.json` (sin efectos colaterales).
