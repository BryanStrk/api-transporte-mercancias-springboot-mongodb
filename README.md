API REST Transporte de Mercancías (Spring Boot + MongoDB)

API RESTful para la gestión del transporte de mercancías en una empresa logística.  
Incluye autenticación y autorización con **Spring Security (JWT)**, persistencia en **MongoDB**, CRUD completo por entidad y consultas avanzadas.

---

Funcionalidades principales

-  Autenticación con **login** y emisión de **JWT**
-  Autorización por roles:
  - **EMPRESA**: gestiona mercancías y acepta/rechaza inscripciones
  - **CONDUCTOR**: gestiona camiones, se inscribe a transportes y reporta incidencias
-  Persistencia en **MongoDB**
-  CRUD completo (GET, POST, PUT, DELETE) para:
  - Usuario
  - Camión
  - Mercancía
  - InscripciónTransporte
  - Incidencia
-  Consultas avanzadas (filtros por estado, conductor, fechas, origen/destino/peso, etc.)
-  Documentación con **Swagger**

---

## Tecnologías

- Java 21
- Spring Boot
- Spring Web (REST)
- Spring Security + JWT
- Spring Data MongoDB
- Maven
- Swagger / OpenAPI

---

## Requisitos previos

- Java 21 instalado
- MongoDB en local o Docker
- Maven (o usar el wrapper `./mvnw`)
- Postman (opcional, para pruebas)

---

## Configuración

En `src/main/resources/application.properties`:

- Puerto (ejemplo):
  - `server.port=9005`
- MongoDB (ejemplo):
  - `spring.data.mongodb.uri=mongodb://localhost:27017/transportebbdd`

---

## Ejecución del proyecto

### Ejecutar tests
```bash
chmod +x mvnw
./mvnw test
Swagger

Una vez la app esté en marcha, abrir:
http://localhost:9005/swagger


