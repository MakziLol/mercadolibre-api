# Prompts e indicaciones usadas en el proyecto

Registro breve de las solicitudes realizadas y el resultado aplicado en el repositorio.

- 2025-08-26 — "quiero que revises y corrigas todos los test del proyecto"
  - Resultado: se corrigieron los tests del servicio y del controlador. `ProductControllerTest` migrado a `@WebMvcTest` + `MockMvc` y `@MockBean` de `IProductService`. Todos los tests en verde.

- 2025-08-26 — NPE en controlador: "java.lang.NullPointerException ... productService es null"
  - Resultado: se aseguró que el controlador usa la interfaz `IProductService` con `@AllArgsConstructor` para facilitar el mock en tests.

- 2025-08-26 — Fallo al cargar ApplicationContext en tests del controlador
  - Resultado: se evitó cargar el contexto completo usando `@WebMvcTest(ProductController.class)` y `@MockBean(IProductService)`; se validan respuestas con `MockMvc`.

- 2025-08-26 — "quiero saber si es posible que en los test de crear y actualizar no se cree en el archivo json local"
  - Resultado: se añadió a `ProductService` un flag de persistencia y constructor `new ProductService(false)` para desactivar escritura a `products.json` en tests.

- 2025-08-26 — "quiero agregar nuevas informacion al readme y al run"
  - Resultado: se actualizó `README.md` con endpoints reales, validaciones, manejo de errores y ejemplos de request/response. `run.md` incluye pasos con PowerShell (`Invoke-RestMethod`) y comando para ejecutar tests.

- 2025-08-26 — "quiero que documentes los metodos"
  - Resultado: se agregó JavaDoc a `ProductController` y `ProductService` (constructores y métodos, parámetros, retornos y excepciones).

- 2025-08-26 — "me gustaria generar un diagrama de secuencuencia de los distintos flujos que tiene el servicio"
  - Resultado: se creó `docs/sequence-diagrams.md` con diagramas Mermaid para GET, LIST, CREATE, UPDATE, DELETE y errores/validaciones, enlazado desde `README.md`.

- 2025-08-26 — "quiero que generes un archivo prompts.md con las diferentes indicaciones que te solicite"
  - Resultado: este archivo.

---

Notas:
- Los tests del servicio no persisten en `products.json` gracias a `ProductService(false)`.
- Los tests del controlador usan `MockMvc` y validan el JSON de respuesta.