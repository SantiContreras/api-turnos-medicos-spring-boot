
🚀 Descripción

API REST diseñada para gestionar turnos médicos dentro de organizaciones, permitiendo administrar usuarios, roles y agendas de forma segura y escalable.

Este proyecto simula un entorno real de aplicación empresarial backend.

🧠 Funcionalidades
🔐 Autenticación & Seguridad
Login con JWT
Protección de endpoints
Autorización basada en roles

👥 Gestión de Usuarios
Registro de usuarios
Roles:
Administrador
Secretario
Médico

📅 Gestión de Turnos
Crear turnos
Cancelar turnos
Visualizar agenda médica
Filtrar por organización

🏢 Multi-organización
Separación de datos por organización
Acceso restringido según usuario
🛠️ Tecnologías
Java 17
Spring Boot
Spring Security
JWT (JSON Web Token)
JPA / Hibernate
MySQL
Maven
Docker (opcional)
🏗️ Arquitectura

Arquitectura en capas basada en buenas prácticas:

Controller → Service → Repository → Database
Detalle:
Controller: Manejo de endpoints REST
Service: Lógica de negocio
Repository: Acceso a datos (JPA)
Security: Configuración JWT + filtros
🔌 Endpoints principales

🔐 Auth
Método	Endpoint	Descripción
POST	/auth/login	Login usuario
POST	/auth/register	Registro usuario

👥 Usuarios
Método	Endpoint	Descripción
GET	/users	Listar usuarios
POST	/users	Crear usuario
GET	/users/{id}	Obtener usuario

📅 Turnos
Método	Endpoint	Descripción
GET	/turnos	Listar turnos
POST	/turnos	Crear turno
DELETE	/turnos/{id}	Cancelar turno
GET	/turnos/medico/{id}	Turnos por médico

🔑 Autenticación

El sistema utiliza JWT.

Ejemplo de request:

POST /auth/login
{
  "username": "admin",
  "password": "1234"
}

Respuesta:

{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
⚙️ Instalación y ejecución
1. Clonar repositorio
git clone https://github.com/SantiContreras/api-turnos-medicos-spring-boot.git
cd api-turnos-medicos-spring-boot
2. Configurar base de datos

Editar application.properties:

spring.datasource.url=jdbc:mysql://localhost:3306/turnos_db
spring.datasource.username=root
spring.datasource.password=tu_password
3. Ejecutar aplicación
mvn clean install
mvn spring-boot:run
4. Acceso
http://localhost:8080

🐳 Docker (opcional)
docker build -t turnos-api .
docker run -p 8080:8080 turnos-api
📸 Screenshots

JUnit
Mockito

(Recomendado: agregar tests unitarios para services)

📈 Mejores prácticas aplicadas
Arquitectura en capas
Separación de responsabilidades
Seguridad con JWT
Uso de DTOs (si aplica)
Validaciones
Manejo de excepciones

📌 Roadmap (mejoras futuras)
Refresh Token
Documentación con Swagger http://localhost:8080/swagger-ui/index.html
Tests completos
Deploy en cloud (AWS / Render)
CI/CD
👨‍💻 Autor

Santiago Contreras

GitHub: https://github.com/SantiContreras
LinkedIn: https://www.linkedin.com/in/santicontreras90/
