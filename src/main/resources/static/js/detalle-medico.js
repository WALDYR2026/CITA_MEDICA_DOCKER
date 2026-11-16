document.addEventListener('DOMContentLoaded',()=>{
 const srv=document.getElementById('codigoServ');
 const s=srv||document.querySelector('select[name="codigoServ"]');
 const medico=document.getElementById('medicoDetalle');
 const label=document.getElementById('especialidadDetalleInfo');
 if(!s||!medico)return;
 const opciones=[...medico.options].filter(o=>o.value).map(o=>o.cloneNode(true));
 function filtrar(){
  const selected=s.selectedOptions[0];const esp=selected?.dataset.especialidad;
  const value=medico.value;
  medico.replaceChildren(new Option(esp?'Seleccione médico...':'Seleccione servicio primero...',''));
  for(const op of opciones)if(esp&&op.dataset.especialidad===esp)medico.append(op.cloneNode(true));
  medico.value=[...medico.options].some(o=>o.value===value)?value:'';
  medico.disabled=!esp;
  label.textContent=esp?'Especialidad automática: '+(selected.dataset.especialidadNombre||esp):'Seleccione primero un servicio.';
 }
 s.addEventListener('change',filtrar);filtrar();
});
