
# 🏥 API Turnos Médicos

## 🚀 Descripción

API REST diseñada para gestionar turnos médicos dentro de organizaciones, permitiendo administrar usuarios, roles y agendas de forma segura y escalable.

Este proyecto simula un entorno real de aplicación empresarial backend.

---

## 🧠 Funcionalidades

### 🔐 Autenticación & Seguridad

* Login con JWT
* Protección de endpoints
* Autorización basada en roles

### 👥 Gestión de Usuarios

* Registro de usuarios
* Roles:

  * Administrador
  * Secretario
  * Médico

### 📅 Gestión de Turnos

* Crear turnos
* Cancelar turnos
* Visualizar agenda médica
* Filtrar por organización

### 🏢 Multi-organización

* Separación de datos por organización
* Acceso restringido según usuario

---

## 🛠️ Tecnologías

* Java 17
* Spring Boot
* Spring Security
* JWT (JSON Web Token)
* JPA / Hibernate
* MySQL
* Maven
* Docker (opcional)

---

## 🏗️ Arquitectura

Arquitectura en capas basada en buenas prácticas:

```
Controller → Service → Repository → Database
```

### Detalle:

* **Controller:** Manejo de endpoints REST
* **Service:** Lógica de negocio
* **Repository:** Acceso a datos (JPA)
* **Security:** Configuración JWT + filtros

---

## 🔌 Endpoints principales

### 🔐 Auth

| Método | Endpoint       | Descripción      |
| ------ | -------------- | ---------------- |
| POST   | /auth/login    | Login usuario    |
| POST   | /auth/register | Registro usuario |

---

### 👥 Usuarios

| Método | Endpoint    | Descripción     |
| ------ | ----------- | --------------- |
| GET    | /users      | Listar usuarios |
| POST   | /users      | Crear usuario   |
| GET    | /users/{id} | Obtener usuario |

---

### 📅 Turnos

| Método | Endpoint            | Descripción       |
| ------ | ------------------- | ----------------- |
| GET    | /turnos             | Listar turnos     |
| POST   | /turnos             | Crear turno       |
| DELETE | /turnos/{id}        | Cancelar turno    |
| GET    | /turnos/medico/{id} | Turnos por médico |

---

## 🔑 Autenticación

Ejemplo de request:

```json
POST /auth/login
{
  "username": "admin",
  "password": "1234"
}
```

Respuesta:

```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

---

## ⚙️ Instalación y ejecución

### 1. Clonar repositorio

```bash
git clone https://github.com/SantiContreras/api-turnos-medicos-spring-boot.git
cd api-turnos-medicos-spring-boot
```

### 2. Configurar base de datos

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/turnos_db
spring.datasource.username=root
spring.datasource.password=tu_password
```

### 3. Ejecutar aplicación

```bash
mvn clean install
mvn spring-boot:run
```

### 4. Acceso

```
http://localhost:8080
```

---

## 📄 Documentación API (Swagger)

🔗 http://localhost:8080/swagger-ui/index.html

Permite:

* Explorar endpoints
* Ejecutar requests
* Probar autenticación JWT

---

## 🐳 Docker (opcional)

```bash
docker build -t turnos-api .
docker run -p 8080:8080 turnos-api
```

---

## 📸 Screenshots

> Agregar imágenes en carpeta `/screenshots`

Ejemplo:

```md
![Login](screenshots/login.png)
![Agenda](screenshots/agenda.png)
```

---

## 🧪 Testing

* JUnit
* Mockito

---

## 📈 Mejores prácticas

* Arquitectura en capas
* Separación de responsabilidades
* Seguridad con JWT
* Validaciones
* Manejo de excepciones

---

## 📌 Roadmap

* Refresh Token
* Tests completos
* Deploy en cloud (AWS / Render)
* CI/CD

---

## 👨‍💻 Autor

**Santiago Contreras**

* GitHub: https://github.com/SantiContreras
* LinkedIn: https://www.linkedin.com/in/santicontreras90/
