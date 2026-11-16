# Laboratorio CD: CITA_MEDICA (6 tablas) con Docker Compose

## 1. Lo verificado del ZIP original
- Spring Boot 4.1.1, Java 17 en el POM original (compilacion del contenedor con JDK 21, compatible con bytecode Java 17).
- `spring.jpa.hibernate.ddl-auto=validate`: las tablas DEBEN existir antes del arranque.
- Script disponible: `sql/01_instalacion_nueva_6_tablas.sql`.
- Puerto original MySQL: 3307, conexion SOLO de desarrollo. Dentro de Compose: `mysql:3306`.
- Tablas: ESPECIALIDAD, PACIENTE, MEDICO, SERVICIO, CITA, DETALLE_CITA.
- El password embebido fue retirado del archivo `application.properties` en esta COPIA del proyecto. Si se publicó en algún repositorio, rotar la contraseña original.

## 2. Archivos y responsabilidad
| Archivo | Proposito |
| --- | --- |
| `Dockerfile` | Genera JAR con Maven y ejecuta Spring Boot en JVM compacta |
| `compose.yaml` | Contenedor app + MySQL 8.4 + red + volumen |
| `.env.example` | Variables que cada alumno copia a `.env` |
| `.dockerignore` | Excluye compilados, secretos y archivos irrelevantes del build |
| `.github/workflows/ci-cita-medica.yml` | Prueba schema + Spring Boot con MySQL efimero en GitHub |
| `.github/workflows/cd-cita-medica.yml` | CD manual en Docker host con runner self-hosted seguro |

## 3. Ejecucion local (Windows, Docker Desktop)
En la raiz de `cita-medica`:

```powershell
Copy-Item .env.example .env
notepad .env
# Cambie MYSQL_ROOT_PASSWORD y MYSQL_APP_PASSWORD; use distintas contrasenas fuertes.
docker compose --env-file .env config --quiet
docker compose --env-file .env up -d --build
docker compose ps
docker compose logs -f app
```

Abrir http://localhost:8080/. Ver las tablas:

```powershell
docker compose --env-file .env exec mysql sh -c 'MYSQL_PWD="$MYSQL_PASSWORD" mysql -ucitas_app CITA_MEDICA -e "SHOW TABLES"'
```

El SQL se importa UNICAMENTE al inicializar un volumen nuevo. No ejecutar `docker compose down -v` si hay datos que conservar.

## 4. Verificacion y diagnostico
```powershell
docker compose --env-file .env ps
docker compose --env-file .env logs --tail=150 mysql
docker compose --env-file .env logs --tail=150 app
docker compose --env-file .env exec mysql sh -c 'MYSQL_PWD="$MYSQL_PASSWORD" mysql -ucitas_app CITA_MEDICA -e "SHOW TABLES"'
```

## 5. GitHub Actions CI
Publicar este proyecto con el workflow `.github/workflows/ci-cita-medica.yml` y ejecutar en `main`. CI crea MySQL temporal, carga las 6 tablas, prueba Maven y compila la imagen Docker. No persiste datos ni publica el backend. Las credenciales de CI son SOLO para la base temporal.

## 6. GitHub Actions CD con un servidor Docker (opcional)
El archivo `cd-cita-medica.yml` es un ejemplo de despliegue REAL solo si existe un servidor Linux siempre encendido con Docker + Compose y runner GitHub Actions self-hosted, etiquetado `cita-medica-deploy`. Ese servidor DEBE tener `/srv/cita-medica/.env` con credenciales no versionadas. Configure el environment `production` y aprobación. No registre runners self-hosted de producción accesibles para PR no confiables y no instale un runner en su computadora personal sin evaluar riesgos. El runner usa permisos del usuario local sobre Docker: trátelos como privilegios elevados.

Desde GitHub > Actions > CD - CITA_MEDICA Docker Host > Run workflow, en rama main. El CD vuelve a verificar CI antes de desplegar y no elimina el volumen de MySQL.

ATENCION: cada workflow tiene su propio checkout. La carpeta del checkout NO es la ubicación persistente de datos; el nombre de proyecto Compose `cita-medica` mantiene estable la identidad del volumen.

## 7. Produccion
- Para acceso público configure reverse proxy HTTPS (Caddy/Nginx) y dominio; el `compose.yaml` inicial solo expone la aplicación en `127.0.0.1:8080` para pruebas locales.
- En un VPS debe configurar firewall, TLS, backups fuera del servidor, rotación de secretos, monitoreo y control de actualizaciones MySQL.
- Para modificar tablas con datos ya existentes, use migraciones Flyway/Liquibase o un procedimiento controlado, no scripts de inicialización.
- CI/CD en GitHub **no** aloja el backend de forma permanente.
- Este ZIP NO contiene una instancia MySQL con datos previos, sino el esquema SQL de seis tablas del proyecto original.

## 8. Alcance de validacion
Se validaron formato YAML, estructura ZIP, correspondencia del SQL y configuración de variables. No se ejecutaron contenedores Docker ni pruebas Maven por falta de Docker y Maven en el entorno de generación. Verifique `docker compose up -d --build` y Github Actions antes de producción.
