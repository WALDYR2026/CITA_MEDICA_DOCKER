# CITA_MEDICA — versión integral de seis tablas (proyecto único)

## Propósito
Aplicación de gestión de citas médicas con panel y seis CRUD, sin AJAX/iframe.
Cada cita tiene una cabecera y uno o más servicios en DETALLE_CITA.
Cada detalle almacena CODIGO_SERV, CODIGO_MEDICO, CANTIDAD, FECHA_SERVICIO y HORA_SERV.
La especialidad se obtiene automáticamente desde SERVICIO; no hay combo de especialidad en el formulario de citas.
El servidor verifica que la especialidad del médico coincida con la asociada al servicio, sin depender de un campo oculto del navegador.

## Tecnología
Java 17; Spring Boot 4.1.1; Maven Wrapper; Spring MVC + Thymeleaf + Bootstrap 5;
Spring Data JPA / Hibernate; MySQL puerto 3307, base CITA_MEDICA.

## Modelo físico final
PACIENTE (1) -> (N) CITA (1) -> (N) DETALLE_CITA;
SERVICIO (1) -> (N) DETALLE_CITA; MEDICO (1) -> (N) DETALLE_CITA;
ESPECIALIDAD (1) -> (N) SERVICIO y ESPECIALIDAD (1) -> (N) MEDICO.
Clave de DETALLE_CITA: (CODIGO_CIT,CODIGO_SERV).
**Advertencia:** El modelo no permite repetir el mismo servicio en una cita.
El diagrama proporcionado corta el nombre de la columna del médico;
esta entrega usa CODIGO_MEDICO, igual que la PK de MEDICO. Confirmar con SHOW CREATE TABLE.

## Uso con base de datos existente
NO ejecute el script de creación sobre la base ya construida.
Ejecute `sql/02_verificar_modelo_y_citas.sql` y confirme `CODIGO_MEDICO`, `HORA_SERV`,
`FECHA_SERVICIO` en `DETALLE_CITA`, conectándose al mismo MySQL de la aplicación.
El script `sql/01_instalacion_nueva_6_tablas.sql` sirve solo en instalaciones nuevas.
La aplicación usa `spring.jpa.hibernate.ddl-auto=validate`: no modifica la base.

## Inicio en VS Code (PowerShell)
1. Abrir la carpeta `cita-medica` que contiene `pom.xml`.
2. Verificar que MySQL atienda `localhost:3307` y que exista `CITA_MEDICA`.
3. Definir la contraseña local en la sesión PowerShell:
   `$env:MYSQL_PASSWORD="TU_CLAVE_MYSQL"`
4. Ejecutar `./mvnw.cmd clean test` y `./mvnw.cmd spring-boot:run`.
5. Abrir http://localhost:8080/.

## Módulos
`/especialidades`, `/medicos`, `/servicios`, `/pacientes`, `/citas`, `/detalles`.
Registrar primero especialidades, médicos, servicios y pacientes. En `/citas/nuevo`,
ingresar código, buscar paciente, elegir servicio y luego médico filtrado por la especialidad del servicio, indicar
cantidad, fecha/hora de atención y agregar líneas. Guardar todo en un POST.
La fecha y hora de cabecera se crean en el servidor con hora de Perú y no se editan.
Alta rápida de paciente: modal Bootstrap + POST tradicional que vuelve a Nueva cita;
registre al paciente antes de preparar la lista de servicios (no conserva borradores).

## Integridad
- Cada cita nueva exige al menos un detalle; cabecera y líneas se guardan en una transacción.
- Medico y servicio deben pertenecer a la misma especialidad.
- La clave primaria compuesta evita servicio duplicado en una misma cita.
- Se conservan los valores NULL históricos para fecha/hora o médico en filas anteriores.
- No se implementa control de solapamientos de horario / disponibilidad médica.
- No se guarda precio histórico en DETALLE_CITA; el total usa precios actuales.

## Diagnóstico
`missing column [codigo_medico] in table [detalle_cita]`: revisar conexión 3307
  y SHOW CREATE TABLE CITA_MEDICA.DETALLE_CITA; comprobar nombre real.
`missing column [fecha_servicio]`: revisar el mismo esquema físico y puerto.
`Access denied`: variable MYSQL_PASSWORD no establecida o contraseña incorrecta.
`Communications link failure`: MySQL no ejecutándose, puerto equivocado.

## Comprobación
Use `sql/02_verificar_modelo_y_citas.sql` para ver médicos y servicios por cita.
No se afirma compilación o integración validada si no se ejecutó Maven y MySQL.
