# Sistema CITA_MEDICA — médicos por detalle

**No eliminar SERVICIO.** SERVICIO identifica la prestación y su tarifa; ESPECIALIDAD clasifica; MEDICO registra al profesional, y DETALLE_CITA identifica la asignación efectiva del médico a la prestación.

## Migración
1. Hacer copia de seguridad y verificar `SELECT @@port, DATABASE(); SHOW COLUMNS FROM CITA_MEDICA.DETALLE_CITA;`.
2. Como la estructura de la captura carece de `CODIGO_MEDICO`, ejecutar UNA SOLA VEZ `sql/05_asignacion_medico_detalle.sql`.
3. No ejecutar los otros scripts de creación en bases con datos.
4. Registrar médicos de la especialidad apropiada antes de asignarlos.
5. En VS Code: `$env:MYSQL_PASSWORD="SU_CLAVE"`; `./mvnw.cmd clean spring-boot:run`.
6. Visitar `/` → Citas → Nueva cita. Seleccionar paciente, especialidad, médico, servicio, fecha, hora y agregar al grid.
7. Verificar SQL:
```sql
SELECT d.CODIGO_CIT, s.NOMBRE_SERV, e.NOMBRE_ESPECIALIDAD,
       m.NOMBRE_MEDICO,d.FECHA_SERVICIO,d.HORA_SERV
FROM DETALLE_CITA d JOIN SERVICIO s ON s.CODIGO_SERV=d.CODIGO_SERV
JOIN ESPECIALIDAD e ON e.CODIGO_ESPECIALIDAD=s.CODIGO_ESPECIALIDAD
LEFT JOIN MEDICO m ON m.CODIGO_MEDICO=d.CODIGO_MEDICO;
```

**Integridad**: el backend verifica que servicio y médico pertenezcan a la especialidad seleccionada. `CODIGO_MEDICO` es `NULL` en líneas existentes hasta asignarlo. No hay validación de choques de agenda ni una segunda aparición del mismo servicio en la misma cita debido a la PK compuesta `(CODIGO_CIT,CODIGO_SERV)`.
