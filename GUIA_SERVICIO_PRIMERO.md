# Guía: registro de citas SIN combo de especialidades

Esta es una versión completa de los 6 CRUD; no ejecutar migraciones ni parches en MySQL.

**Regla:** `SERVICIO.CODIGO_ESPECIALIDAD` identifica la especialidad, que sirve para filtrar `MEDICO.CODIGO_ESPECIALIDAD`.
Al agregar líneas, solamente se envían servicio, médico, cantidad, fecha y hora. Spring Boot verifica su compatibilidad.

## Paso a paso
1. En VS Code abrir `cita-medica` (`pom.xml` en la raíz).
2. Comprobar la base existente con `sql/02_verificar_modelo_y_citas.sql`.
3. Configurar contraseña sin guardarla en código: `$env:MYSQL_PASSWORD="..."`.
4. Ejecutar `./mvnw.cmd clean test` y luego `./mvnw.cmd spring-boot:run`.
5. Abrir http://localhost:8080/ -> Citas -> Nueva cita.
6. Ingresar código de ocho caracteres; fecha y hora de cabecera se asignan automáticamente.
7. Buscar y seleccionar paciente por nombre; si no existe usar Nuevo paciente (POST tradicional).
8. Seleccionar el servicio: la especialidad aparece como información y se filtran los médicos.
9. Seleccionar médico, cantidad, fecha y hora del servicio. Agregar al grid. Repetir para más servicios.
10. Guardar cita; verificar una fila en CITA y varias en DETALLE_CITA.

## Notas
- Mantener `ESPECIALIDAD`, `SERVICIO` y `MEDICO` como tablas distintas.
- Debe existir al menos un servicio en el catálogo para la atención solicitada.
- Si el médico no aparece, confirmar que tenga el mismo CODIGO_ESPECIALIDAD del servicio.
- No hay AJAX ni iframe; el filtrado usa JavaScript con datos incrustados en las opciones Thymeleaf.
- En DETALLE_CITA la columna de FK al médico debe llamarse `CODIGO_MEDICO`; confirmar en Workbench.
- La clave compuesta impide repetir un mismo servicio en una cita.
