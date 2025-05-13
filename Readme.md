# 🍽️ Parrot Restaurant API

Este proyecto es una API REST desarrollada con **Spring Boot 3.2.4** y **Java 17** que proporciona un sistema de gestión para restaurantes. Permite administrar meseros, órdenes y productos, así como generar reportes de ventas. La API implementa autenticación JWT para proteger los endpoints y garantizar la seguridad de las operaciones.

---

## Requisitos Previos

- **Java 17**
- **Spring Boot 3.2.4**
- **Maven**
- **PostgreSQL**
- **Docker (opcional para el despliegue en contenedor)**

## Arquitectura y Stack Tecnológico

### Tecnologías Utilizadas:

![Java](https://img.shields.io/badge/Java-17-brightgreen.svg?style=for-the-badge&logo=java)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.4-brightgreen.svg?style=for-the-badge&logo=spring-boot)
![Spring Security](https://img.shields.io/badge/Spring%20Security-3.2.4-brightgreen.svg?style=for-the-badge&logo=spring-security)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-14-blue.svg?style=for-the-badge&logo=postgresql)
![JWT](https://img.shields.io/badge/JWT-Auth-blue.svg?style=for-the-badge&logo=json-web-tokens)
![Maven](https://img.shields.io/badge/Maven-C71A36?style=for-the-badge&logo=apache-maven&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-19+-brightgreen.svg?style=for-the-badge&logo=docker)
![Swagger](https://img.shields.io/badge/Swagger-OpenAPI%203.0-85EA2D?style=for-the-badge&logo=swagger&logoColor=white)

---

## Endpoints

### Autenticación

- 🔹 **Registro de Mesero**
  - **URL:** `/api/v1/auth/register`
  - **Método:** `POST`
  - **Descripción:** Registra un nuevo mesero en el sistema y devuelve un token JWT.
  - **Cuerpo de la solicitud:**
    ```json
    {
      "nombre": "Nombre del Mesero",
      "mail": "correo@ejemplo.com"
    }
    ```
  - **Respuesta exitosa:**
    ```json
    {
      "mesero": "Nombre del Mesero",
      "token": "jwt-token"
    }
    ```

- 🔹 **Inicio de Sesión**
  - **URL:** `/api/v1/auth/login`
  - **Método:** `POST`
  - **Descripción:** Autentica a un mesero existente y devuelve un token JWT.
  - **Cuerpo de la solicitud:**
    ```json
    {
      "mail": "correo@ejemplo.com"
    }
    ```
  - **Respuesta exitosa:**
    ```json
    {
      "mesero": "Nombre del Mesero",
      "token": "jwt-token"
    }
    ```

### Meseros

- 🔹 **Buscar Mesero por ID**
  - **URL:** `/api/v1/mesero/{id}`
  - **Método:** `GET`
  - **Descripción:** Obtiene la información de un mesero específico según su ID.
  - **Requiere autenticación:** Sí

- 🔹 **Crear Nuevo Mesero**
  - **URL:** `/api/v1/mesero`
  - **Método:** `POST`
  - **Descripción:** Registra un nuevo mesero en el sistema.
  - **Requiere autenticación:** Sí

- 🔹 **Buscar Mesero por Nombre**
  - **URL:** `/api/v1/mesero/nombre/{nombre}`
  - **Método:** `GET`
  - **Descripción:** Obtiene la información de un mesero específico según su nombre.
  - **Requiere autenticación:** Sí

### Órdenes

- 🔹 **Obtener Orden por ID**
  - **URL:** `/api/v1/orden/{id}`
  - **Método:** `GET`
  - **Descripción:** Recupera los detalles de una orden específica según su ID.
  - **Requiere autenticación:** Sí

- 🔹 **Crear Nueva Orden**
  - **URL:** `/api/v1/orden`
  - **Método:** `POST`
  - **Descripción:** Registra una nueva orden en el sistema con los productos seleccionados.
  - **Requiere autenticación:** Sí
  - **Cuerpo de la solicitud:**
    ```json
    {
      "nombre": "Nombre del Cliente",
      "productos": [
        {
          "nombre": "Nombre del Producto",
          "precio_unitario": 10.5,
          "cantidad": 2
        }
      ]
    }
    ```
  - **Respuesta exitosa:**
    ```
    UUID de la orden creada
    ```

---

## Características Principales

- **Autenticación JWT**: Implementación de seguridad basada en tokens JWT para proteger los endpoints.
- **Gestión de Meseros**: Registro y consulta de información de meseros.
- **Gestión de Órdenes**: Creación y consulta de órdenes con sus productos asociados.
- **Reportes**: Generación de reportes de ventas por período.
- **Documentación API**: Integración con Swagger/OpenAPI para documentación interactiva.
- **Manejo de Excepciones**: Sistema robusto de manejo de errores.
- **Pruebas Unitarias**: Cobertura de pruebas para garantizar la calidad del código.

---

## Despliegue con Docker

El proyecto incluye configuración para Docker y Docker Compose, lo que facilita su despliegue:

```bash
# Construir y ejecutar los contenedores
docker-compose up -d

# Detener los contenedores
docker-compose down
```

---

## Documentación de la API

La documentación completa de la API está disponible a través de Swagger UI:

- **URL:** `/swagger-ui/index.html`
- **Método:** `GET`
- **Descripción:** Interfaz interactiva para explorar y probar los endpoints de la API.

---

## Estructura del Proyecto

El proyecto sigue una arquitectura en capas:

- **Controller**: Manejo de solicitudes HTTP y respuestas.
- **Service**: Lógica de negocio.
- **Repository**: Acceso a datos.
- **Model**: Entidades y DTOs.
- **Security**: Configuración de seguridad y autenticación JWT.
- **Utils**: Clases de utilidad.

---

## Contribuciones

Las contribuciones son bienvenidas. Por favor, sigue estos pasos:

1. Haz un fork del repositorio
2. Crea una rama para tu característica (`git checkout -b feature/amazing-feature`)
3. Haz commit de tus cambios (`git commit -m 'Add some amazing feature'`)
4. Haz push a la rama (`git push origin feature/amazing-feature`)
5. Abre un Pull Request

---

## Licencia

Este proyecto está licenciado bajo la Licencia MIT - ver el archivo LICENSE para más detalles.
