# Docker MySQL Setup para API Books

## Requisitos previos
- Docker instalado
- Docker Compose instalado

## Instrucciones de uso

### 1. Levantar la base de datos MySQL

Desde la raíz del proyecto (donde está el archivo `docker-compose.yml`), ejecuta:

```bash
docker-compose up -d
```

Este comando:
- Descarga la imagen de MySQL 8.0 (si no la tienes)
- Crea un contenedor llamado `mysql_books_db`
- Crea la base de datos `db_books`
- Ejecuta el script de inicialización `init.sql` que crea las tablas
- Expone el puerto 3306 para que tu aplicación Spring pueda conectarse

### 2. Verificar que el contenedor está corriendo

```bash
docker-compose ps
```

### 3. Ver los logs del contenedor

```bash
docker-compose logs -f mysql
```

### 4. Conectarse a MySQL desde la terminal

```bash
docker exec -it mysql_books_db mysql -uroot -p1234
```

### 5. Detener el contenedor

```bash
docker-compose down
```

### 6. Detener y eliminar los datos (volúmenes)

```bash
docker-compose down -v
```

## Configuración de la aplicación Spring

Tu `application.properties` ya está configurado correctamente para conectarse a MySQL:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/db_books?useSSl=false&useLegacyDatetimeCode=false&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=1234
```

## Información de la base de datos

- **Host**: localhost
- **Puerto**: 3306
- **Base de datos**: db_books
- **Usuario root**: root
- **Password root**: 1234
- **Usuario alternativo**: user_books
- **Password alternativo**: books123

## Estructura de tablas

### Tabla `autor`
- id (BIGINT, AUTO_INCREMENT, PRIMARY KEY)
- nombre (VARCHAR 255)
- apellido (VARCHAR 255)
- nacionalidad (VARCHAR 255)

### Tabla `categoria`
- id (BIGINT, AUTO_INCREMENT, PRIMARY KEY)
- nombre (VARCHAR 40, NOT NULL)
- descripcion (VARCHAR 40, NOT NULL)

### Tabla `libros`
- id (BIGINT, AUTO_INCREMENT, PRIMARY KEY)
- titulo (VARCHAR 40, NOT NULL)
- descripcion (VARCHAR 40, NOT NULL)
- autor (VARCHAR 40, NOT NULL)
- imagen_bytes (LONGBLOB)
- categoria_id (BIGINT, FOREIGN KEY -> categoria.id)

## Troubleshooting

### El contenedor no inicia
- Verifica que el puerto 3306 no esté ocupado: `netstat -ano | findstr :3306`
- Si está ocupado, detén el servicio MySQL local o cambia el puerto en docker-compose.yml

### No se puede conectar desde Spring
- Verifica que el contenedor esté corriendo: `docker-compose ps`
- Verifica los logs: `docker-compose logs mysql`
- Asegúrate de que Spring tenga el driver MySQL en el pom.xml

### Resetear la base de datos
```bash
docker-compose down -v
docker-compose up -d
```
