# 🎟️ Gestión de Eventos - Proyecto Full Stack en Kotlin
Este proyecto es una aplicación web desarrollada íntegramente en Kotlin, que permite gestionar eventos, notificaciones, participantes, tickets y lugares. Combina una API RESTful con una interfaz web sencilla, utilizando tecnologías modernas y buenas prácticas de desarrollo.

# 🚀 Características Principales
Gestión de Entidades: Permite crear, listar, actualizar y eliminar eventos, notificaciones, participantes, tickets y lugares.

Interfaz Web Intuitiva: Diseñada con Thymeleaf y Bootstrap para una experiencia de usuario amigable.

API RESTful: Endpoints claros y estructurados para interactuar con las diferentes entidades.

Base de Datos en Memoria: Utiliza H2 para facilitar el desarrollo y las pruebas.

Pruebas Automatizadas: Incluye pruebas unitarias e integrales para garantizar la calidad del código.
Baeldung

# 🧱 Arquitectura del Proyecto
El proyecto sigue una arquitectura modular y limpia, facilitando su mantenimiento y escalabilidad.

Backend: Construido con Ktor, ofrece una API RESTful para las operaciones CRUD.

Frontend: Utiliza Thymeleaf como motor de plantillas para renderizar las vistas en el servidor.

Persistencia: Implementada con Exposed ORM sobre una base de datos H2 en memoria.

Inyección de Dependencias: Gestionada con Koin para una configuración sencilla y flexible.

# 🛠️ Tecnologías Utilizadas
Lenguaje: Kotlin 1.9+

Framework Web: Ktor 2.x

Motor de Plantillas: Thymeleaf

ORM: Exposed (core + DAO)

Base de Datos: H2 en memoria

Inyección de Dependencias: Koin 3.5

Serialización: kotlinx-serialization JSON

Testing: JUnit, MockK, Ktor Server Tests

Logging y Manejo de Errores: CallLogging, StatusPages

# 🤝 Contribuciones
¡Las contribuciones son bienvenidas! Si deseas mejorar el proyecto, por favor sigue estos pasos:

Fork el repositorio.

Crea una nueva rama (git checkout -b feature-nueva).

Realiza tus cambios y haz commit (git commit -am 'Agrega nueva funcionalidad').

Push a la rama (git push origin feature-nueva).

Abre un Pull Request