/* Catálogos HTML Thymeleaf, sin AJAX; el servidor vuelve a validar todas las relaciones. */
(()=>{'use strict';
const $=id=>document.getElementById(id);
const pacientes=[...document.querySelectorAll('.paciente')].map(e=>({codigo:e.dataset.codigo,nombre:e.dataset.nombre,edad:e.dataset.edad}));
const srvOptions=[...$('servicioSelect').options].filter(o=>o.value);
const medOptions=[...$('medicoSelect').options].filter(o=>o.value);
const initial=[...document.querySelectorAll('.linea-inicial')].map(e=>({codigo:e.dataset.codigo,cantidad:Number(e.dataset.cantidad),fecha:e.dataset.fecha||'',hora:e.dataset.hora||'',medico:e.dataset.medico||'',especialidad:e.dataset.especialidad||''}));
const grid=new Map();const dinero=x=>'S/ '+Number(x).toFixed(2);
const normal=x=>String(x||'').trim().toLocaleLowerCase('es');
function elegirPaciente(p){$('codigoPac').value=p.codigo;$('buscarPaciente').value=p.nombre;$('pacienteInfo').textContent=`Paciente: ${p.nombre} | Edad: ${p.edad} años | Código: ${p.codigo}`;$('coincidencias').replaceChildren();}
function pintarPacientes(){const q=normal($('buscarPaciente').value),box=$('coincidencias');box.replaceChildren();if(!q)return;const matches=pacientes.filter(p=>normal(p.nombre).includes(q)||normal(p.codigo).startsWith(q)).slice(0,12);for(const p of matches){const b=document.createElement('button');b.type='button';b.className='list-group-item list-group-item-action';b.textContent=`${p.nombre} (${p.edad} años)`;b.addEventListener('click',()=>elegirPaciente(p));box.append(b);}if(!matches.length){const d=document.createElement('div');d.className='list-group-item text-muted';d.textContent='Paciente no encontrado: utilice Nuevo paciente.';box.append(d);}}
$('buscarPaciente').addEventListener('input',()=>{$('codigoPac').value='';$('pacienteInfo').textContent='Seleccione un paciente.';pintarPacientes();});const p0=pacientes.find(p=>p.codigo===$('codigoPac').value);if(p0)elegirPaciente(p0);
function servicioCambio(){
 const srv=srvOptions.find(o=>o.value===$('servicioSelect').value);
 const medico=$('medicoSelect');const previo=medico.value;
 medico.replaceChildren(new Option(srv?'Seleccione médico...':'Seleccione servicio primero...',''));
 $('especialidadInfo').textContent=srv?(srv.dataset.especialidadNombre||srv.dataset.especialidad):'Se obtiene del servicio';
 for(const o of medOptions){if(srv && o.dataset.especialidad===srv.dataset.especialidad)medico.append(o.cloneNode(true));}
 medico.disabled=!srv;medico.value=[...medico.options].some(o=>o.value===previo)?previo:'';
 if(srv && medico.options.length===1){$('avisoLinea').textContent='Este servicio no tiene médicos asociados a su especialidad.';}
 else $('avisoLinea').textContent='';
}
$('servicioSelect').addEventListener('change',servicioCambio);
servicioCambio();
function render(){const tbody=$('grid'),hidden=$('lineasHidden');tbody.replaceChildren();hidden.replaceChildren();let total=0,n=0;for(const [codigo,linea] of grid){const srv=srvOptions.find(o=>o.value===codigo),med=medOptions.find(o=>o.value===linea.medico);if(!srv)continue;const price=Number(srv.dataset.precio),sub=price*linea.cantidad;total+=sub;const tr=document.createElement('tr');for(const v of [codigo,srv.dataset.nombre,med?med.textContent:'Pendiente',String(linea.cantidad),linea.fecha,linea.hora,dinero(price),dinero(sub)]){const td=document.createElement('td');td.textContent=v;tr.append(td);}const td=document.createElement('td'),btn=document.createElement('button');btn.type='button';btn.className='btn btn-sm btn-outline-danger';btn.textContent='Quitar';btn.addEventListener('click',()=>{grid.delete(codigo);render();});td.append(btn);tr.append(td);tbody.append(tr);
for(const [field,value] of Object.entries({codigoServ:codigo,codigoMedico:linea.medico,cantidad:linea.cantidad,fechaServicio:linea.fecha,horaServicio:linea.hora})){const inp=document.createElement('input');inp.type='hidden';inp.name=`lineas[${n}].${field}`;inp.value=value;hidden.append(inp);}n++;}$('total').textContent=dinero(total);}
$('agregar').addEventListener('click',()=>{const codigo=$('servicioSelect').value,medico=$('medicoSelect').value,cantidad=Number($('cantidad').value),fecha=$('fechaServicio').value,hora=$('horaServicio').value;const srv=srvOptions.find(o=>o.value===codigo),med=medOptions.find(o=>o.value===medico);
if(!srv||!med||srv.dataset.especialidad!==med.dataset.especialidad){$('avisoLinea').textContent='Seleccione un servicio y un médico de su especialidad.';return;}
if(!Number.isInteger(cantidad)||cantidad<1||!/^[0-9]{4}-[0-9]{2}-[0-9]{2}$/.test(fecha)||!/^([01][0-9]|2[0-3]):[0-5][0-9]$/.test(hora)){$('avisoLinea').textContent='Ingrese cantidad, fecha y hora válidas.';return;}
if(grid.has(codigo)){$('avisoLinea').textContent='Este servicio ya está en la cita; quite su fila antes de modificarlo.';return;}
grid.set(codigo,{cantidad,fecha,hora,medico});$('avisoLinea').textContent='';render();});
for(const l of initial)if(l.codigo&&l.cantidad>0)grid.set(l.codigo,l);render();
$('citaForm').addEventListener('submit',e=>{if(!$('codigoPac').value){e.preventDefault();alert('Seleccione un paciente de la búsqueda.');return;}if(grid.size===0){e.preventDefault();alert('Agregue al menos un servicio.');return;}if([...grid.values()].some(l=>!l.medico)){e.preventDefault();alert('Asigne médico a todas las líneas; para líneas históricas sin médico, quite y vuelva a agregarlas.');}});
})();
