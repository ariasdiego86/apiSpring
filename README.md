# API REST Books - Spring Boot

## 📖 Descripción del Proyecto

API REST para la gestión de una biblioteca que permite administrar libros, autores y categorías. El proyecto está construido con Spring Boot y utiliza Spring Security con autenticación JWT (JSON Web Tokens) para securizar los endpoints. La aplicación incluye documentación interactiva con Swagger UI.

## 🛠️ Tecnologías y Versiones

- **Java**: 17
- **Spring Boot**: 3.3.5
- **Spring Security**: JWT Authentication
- **Base de datos**: MySQL 8.0
- **ORM**: Spring Data JPA / Hibernate
- **Documentación API**: Swagger/OpenAPI (springdoc-openapi 2.1.0)
- **JWT**: jjwt 0.11.5
- **Lombok**: 1.18.30
- **Maven**: Gestor de dependencias

## 📋 Prerrequisitos

- JDK 17 o superior
- Maven 3.6+
- Docker y Docker Compose (recomendado para la base de datos)
- Puerto 8080 libre para la aplicación
- Puerto 3306 libre para MySQL

## 🚀 Instalación y Configuración

### 1. Clonar el repositorio

```bash
git clone <url-del-repositorio>
cd apiSpring
```

### 2. Configurar la Base de Datos

#### Opción A: Usando Docker (Recomendado)

Ejecuta el siguiente comando en la raíz del proyecto:

```bash
docker-compose up -d
```

Esto creará:
- Un contenedor MySQL 8.0
- La base de datos `db_books`
- Las tablas `users` y `authorities` con usuarios precargados
- Las tablas de entidades (autor, categoria, libros) se crearán automáticamente al iniciar Spring Boot

Para verificar que el contenedor está correcto:

```bash
docker ps
docker logs mysql_books_db
```

#### Opción B: MySQL local

Si prefieres usar MySQL instalado localmente:
1. Crea la base de datos `db_books`
2. Ejecuta el script SQL ubicado en `DB/spring-security-schema-bcrypt.sql`
3. Actualiza las credenciales en `src/main/resources/application.properties` si es necesario

### 3. Compilar y ejecutar la aplicación

```bash
# En Windows (PowerShell)
.\mvnw.cmd clean install
.\mvnw.cmd spring-boot:run

# En Linux/Mac
./mvnw clean install
./mvnw spring-boot:run
```

La aplicación estará disponible en: `http://localhost:8080`

## 📚 Documentación API - Swagger

Una vez que la aplicación esté en ejecución, accede a la documentación interactiva de Swagger:

**Swagger UI**: `http://localhost:8080/swagger-ui.html`

Desde Swagger podrás:
- Ver todos los endpoints disponibles
- Probar las peticiones directamente desde el navegador
- Ver los modelos de datos
- Obtener un token JWT para autenticarte

## 🔐 Autenticación

### Obtener Token JWT

Para usar la API, primero debes obtener un token JWT:

**Endpoint**: `POST /api/token`

**Body (JSON)**:
```json
{
  "username": "Resio",
  "password": "Resio123"
}
```

**Respuesta**:
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

### Usar el Token

En Swagger UI:
1. Haz clic en el botón **"Authorize"** (candado verde)
2. Ingresa el token en el formato: `Bearer <tu-token>`
3. Haz clic en "Authorize"

En herramientas como Postman o cURL:
```bash
curl -H "Authorization: Bearer <tu-token>" http://localhost:8080/api/v1/categorias
```

## 👥 Usuarios y Roles Disponibles

El sistema viene con 3 usuarios precargados:

| Usuario | Contraseña | Roles | Descripción |
|---------|-----------|-------|-------------|
| **BigResio** | `BigResio123` | `ROLE_BOSS` | Administrador con todos los permisos |
| **Resio** | `Resio123` | `ROLE_EMPLOYEE`, `ROLE_SCHOLAR` | Usuario con permisos de empleado y académico |
| **MiniResio** | `MiniResio123` | `ROLE_SCHOLAR` | Usuario con permisos básicos de académico |

### Permisos por Rol

- **ROLE_BOSS**: Acceso completo a todos los endpoints (crear, leer, actualizar, eliminar)
- **ROLE_EMPLOYEE**: Permisos de gestión y edición
- **ROLE_SCHOLAR**: Permisos principalmente de lectura

## 🔗 Endpoints Principales

### Autenticación
- `POST /api/token` - Obtener token JWT

### Categorías
- `GET /api/v1/categorias` - Listar todas las categorías
- `GET /api/v1/categorias/{id}` - Obtener categoría por ID
- `POST /api/v1/categorias` - Crear nueva categoría
- `PUT /api/v1/categorias/{id}` - Actualizar categoría
- `DELETE /api/v1/categorias/{id}` - Eliminar categoría

### Autores
- `GET /api/v1/autores` - Listar todos los autores
- `GET /api/v1/autores/{id}` - Obtener autor por ID
- `POST /api/v1/autores` - Crear nuevo autor
- `PUT /api/v1/autores/{id}` - Actualizar autor
- `DELETE /api/v1/autores/{id}` - Eliminar autor

### Libros
- `GET /api/v1/libros` - Listar todos los libros
- `GET /api/v1/libros/{id}` - Obtener libro por ID
- `POST /api/v1/libros` - Crear nuevo libro
- `PUT /api/v1/libros/{id}` - Actualizar libro
- `DELETE /api/v1/libros/{id}` - Eliminar libro

*Consulta Swagger UI para ver todos los endpoints y sus detalles completos*

## 🗄️ Configuración de Base de Datos

El archivo `application.properties` contiene la configuración de conexión:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/db_books
spring.datasource.username=root
spring.datasource.password=1234
spring.jpa.hibernate.ddl-auto=update
```

Si usas Docker Compose, estas configuraciones ya están correctas. Si usas tu propia instalación de MySQL, ajusta las credenciales según sea necesario.

## 🧪 Ejecutar Tests

```bash
.\mvnw.cmd test
```

## 📁 Estructura del Proyecto

```
apiSpring/
├── src/main/java/com/company/books/backend/
│   ├── config/              # Configuración de seguridad
│   ├── controllers/         # Controladores REST
│   ├── exceptions/          # Manejo de excepciones
│   ├── filter/              # Filtros JWT
│   ├── model/               # Entidades JPA
│   ├── request/             # DTOs de peticiones
│   ├── response/            # DTOs de respuestas
│   └── service/             # Lógica de negocio
├── src/main/resources/
│   └── application.properties
├── DB/
│   └── spring-security-schema-bcrypt.sql
├── docker-compose.yml
└── pom.xml
```

## 🐛 Solución de Problemas

### Error de conexión a la base de datos
- Verifica que el contenedor Docker esté corriendo: `docker ps`
- Verifica los logs: `docker logs mysql_books_db`
- Asegúrate de que el puerto 3306 no esté ocupado

### Error al compilar
- Verifica que tienes Java 17 instalado: `java -version`
- Limpia el proyecto: `.\mvnw.cmd clean`

### Token JWT inválido o expirado
- Genera un nuevo token usando el endpoint `/api/token`
- Verifica que estés usando el formato correcto: `Bearer <token>`

## 📝 Notas Adicionales

- Las contraseñas en la base de datos están encriptadas con BCrypt
- El token JWT tiene una duración configurada (revisar `JwtService.java` para ajustarla)
- Spring Boot creará automáticamente las tablas de entidades (autor, categoria, libros) al iniciar
- El modo `ddl-auto=update` preserva los datos existentes entre reinicios

## 📄 Licencia

Este proyecto es de código abierto y está disponible bajo la licencia correspondiente.

## 👨‍💻 Autor

**Diego Arias** - Proyecto de aprendizaje de Spring Boot y Spring Security.
