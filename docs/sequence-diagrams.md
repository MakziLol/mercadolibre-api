# Diagramas de secuencia

A continuación se describen los principales flujos de la API usando diagramas de secuencia (Mermaid).

Nota: Las validaciones con `@Valid` pueden disparar `MethodArgumentNotValidException`, gestionada por `GlobalExceptionHandler` con respuesta 400.

## 1) Obtener producto por ID (GET /api/products/get/{id})

```mermaid
sequenceDiagram
    autonumber
    actor Client
    participant Controller as ProductController
    participant Service as ProductService (IProductService)
    Client->>Controller: GET /api/products/get/{id}
    Controller->>Service: getProductById(id)
    alt Producto existe
        Service-->>Controller: ProductResponseDto
        Controller-->>Client: 200 OK (JSON)
    else No existe
        Service-->>Controller: throws NoSuchElementException
        Controller-->>Client: 404 Not Found (GlobalExceptionHandler)
    end
```

## 2) Listar productos (GET /api/products/get)

```mermaid
sequenceDiagram
    autonumber
    actor Client
    participant Controller as ProductController
    participant Service as ProductService (IProductService)
    Client->>Controller: GET /api/products/get
    Controller->>Service: getAllProducts()
    Service-->>Controller: List<ProductResponseDto>
    Controller-->>Client: 200 OK (JSON)
```

## 3) Crear producto (POST /api/products/create)

```mermaid
sequenceDiagram
    autonumber
    actor Client
    participant Controller as ProductController
    participant Service as ProductService (IProductService)
    participant FS as FileSystem (products.json)

    Client->>Controller: POST /api/products/create (body ProductResponseDto)
    note over Controller: @Valid valida campos requeridos y mínimos
    alt Datos inválidos
        Controller-->>Client: 400 Bad Request (GlobalExceptionHandler)
    else Datos válidos
        Controller->>Service: createProduct(dto)
        Service->>Service: calcular nextId y setear
        Service->>FS: saveProducts() (escritura JSON) 
        FS-->>Service: OK
        Service-->>Controller: ProductResponseDto (con id)
        Controller-->>Client: 201 Created (JSON)
    end
```

## 4) Actualizar producto (PUT /api/products/update/{id})

```mermaid
sequenceDiagram
    autonumber
    actor Client
    participant Controller as ProductController
    participant Service as ProductService (IProductService)
    participant FS as FileSystem (products.json)

    Client->>Controller: PUT /api/products/update/{id} (body ProductResponseDto)
    note over Controller: @Valid valida payload
    alt Producto no existe
        Controller->>Service: updateProduct(id, dto)
        Service-->>Controller: throws NoSuchElementException
        Controller-->>Client: 404 Not Found (GlobalExceptionHandler)
    else Producto existe y datos válidos
        Controller->>Service: updateProduct(id, dto)
        Service->>Service: set dto.id = id y reemplazar en lista
        Service->>FS: saveProducts() (escritura JSON)
        FS-->>Service: OK
        Service-->>Controller: ProductResponseDto actualizado
        Controller-->>Client: 200 OK (JSON)
    end
```

## 5) Eliminar producto (DELETE /api/products/delete/{id})

```mermaid
sequenceDiagram
    autonumber
    actor Client
    participant Controller as ProductController
    participant Service as ProductService (IProductService)
    participant FS as FileSystem (products.json)

    Client->>Controller: DELETE /api/products/delete/{id}
    alt Producto no existe
        Controller->>Service: deleteProduct(id)
        Service-->>Controller: throws NoSuchElementException
        Controller-->>Client: 404 Not Found (GlobalExceptionHandler)
    else Producto existe
        Controller->>Service: deleteProduct(id)
        Service->>Service: remover de lista
        Service->>FS: saveProducts() (escritura JSON)
        FS-->>Service: OK
        Service-->>Controller: void
        Controller-->>Client: 204 No Content
    end
```

## Notas de tests
- En tests, `ProductService(false)` deshabilita `saveProducts()` para no escribir en `products.json`.
- Los tests del controlador usan `@WebMvcTest` + `@MockBean(IProductService)` y validan el JSON de respuesta.
