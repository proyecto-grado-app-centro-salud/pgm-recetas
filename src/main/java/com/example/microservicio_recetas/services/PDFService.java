package com.example.microservicio_recetas.services;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.microservicio_recetas.repository.EspecialidadesRepositoryJPA;
import com.example.microservicio_recetas.repository.HistoriaClinicaRepositoryJPA;
import com.example.microservicio_recetas.repository.RecetasRepository;
import com.example.microservicio_recetas.repository.UsuariosRepositoryJPA;
import com.example.microservicio_recetas.model.EspecialidadesEntity;
import com.example.microservicio_recetas.model.HistoriaClinicaEntity;
import com.example.microservicio_recetas.model.RecetasEntity;
import com.example.microservicio_recetas.model.UsuarioEntity;
import com.example.microservicio_recetas.model.dto.RecetaDto;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Service
public class PDFService {
    @Autowired
    UsuariosRepositoryJPA usuariosRepositoryJPA;
    @Autowired
    HistoriaClinicaRepositoryJPA historiaClinicaRepositoryJPA;
    @Autowired
    EspecialidadesRepositoryJPA especialidadesRepositoryJPA;
    @Autowired
    RecetasRepository recetasRepository;

    public byte[] generarPdfReporteReceta(RecetaDto recetaDto) throws JRException {
        Optional<RecetasEntity> recetaEntityOptional=(recetaDto.getId()!=null)?recetasRepository.findById(recetaDto.getId()):Optional.empty();
        if(recetaEntityOptional.isPresent()){
            recetaDto=new RecetaDto().convertirRecetasEntityARecetasDto(recetaEntityOptional.get());
        }else{
            recetaDto.setCreatedAt(new Date());
            recetaDto.setUpdatedAt(new Date());
        }
        InputStream jrxmlInputStream = getClass().getClassLoader().getResourceAsStream("reports/receta.jrxml");
        HistoriaClinicaEntity historiaClinicaEntity = historiaClinicaRepositoryJPA.findById(recetaDto.getIdHistoriaClinica()).orElseThrow(() -> new RuntimeException("Historia clinica no encontrada"));
        UsuarioEntity pacienteEntity = usuariosRepositoryJPA.findById(historiaClinicaEntity.getPaciente().getIdUsuario()).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        UsuarioEntity medicoEntity = usuariosRepositoryJPA.findById(recetaDto.getIdMedico()).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        EspecialidadesEntity especialidadesEntity = especialidadesRepositoryJPA.findById(historiaClinicaEntity.getEspecialidad().getIdEspecialidad()).orElseThrow(() -> new RuntimeException("Especialidad no encontrada"));
        if (jrxmlInputStream == null) {
            throw new JRException("No se pudo encontrar el archivo .jrxml en el classpath.");
        }
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        JasperReport jasperReport = JasperCompileManager.compileReport(jrxmlInputStream);
        Map<String, Object> parameters = new HashMap<>();
        String fechaVencimiento="",fechaCreacion="";
        if(recetaDto.getFechaVencimiento()!=null){
            fechaVencimiento=formato.format(recetaDto.getFechaVencimiento());
        }
        parameters.put("nombreGenericoMedicamentoPreescrito",recetaDto.getNombreGenericoMedicamentoPreescrito());
        parameters.put("viaCuidadosEspecialesAdmnistracion",recetaDto.getViaCuidadoEspecialesAdministracion());
        parameters.put("concentracionDosificacion",recetaDto.getConcentracionDosificacion());
        parameters.put("frecuenciaAdministracion24hrs",recetaDto.getFrecuenciaAdministracion24hrs());
        parameters.put("duracionTratamiento",recetaDto.getDuracionTratamiento() );
        parameters.put("precaucionesEspeciales",recetaDto.getPrecaucionesEspeciales());
        parameters.put("indicacionesEspeciales",recetaDto.getIndicacionesEspeciales() );
       
        parameters.put("apellidoPaterno", pacienteEntity.getApellidoPaterno());
        parameters.put("apellidoMaterno", pacienteEntity.getApellidoMaterno());
        parameters.put("nombres", pacienteEntity.getNombres());
        parameters.put("nhc", historiaClinicaEntity.getIdHistoriaClinica()+"");
        parameters.put("edad", pacienteEntity.getEdad()+"");
        parameters.put("sexo", pacienteEntity.getSexo());
        parameters.put("estadoCivil", pacienteEntity.getEstadoCivil());
        parameters.put("unidad", especialidadesEntity.getNombre());



        parameters.put("fecha", formato.format(recetaDto.getUpdatedAt()));
        parameters.put("nombreCompletoPaciente", pacienteEntity.getNombres()+" "+pacienteEntity.getApellidoPaterno());
        parameters.put("nombreCompletoMedico", medicoEntity.getNombres()+" "+medicoEntity.getApellidoPaterno());
        parameters.put("firmaPaciente", "");
        parameters.put("firmaMedico", "");

        parameters.put("IMAGE_PATH", getClass().getClassLoader().getResource("images/logo.jpeg").getPath());
        List<RecetaDto> list = new ArrayList<>();
        list.add(recetaDto);

        JRBeanCollectionDataSource emptyDataSource = new JRBeanCollectionDataSource(list);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, emptyDataSource);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);

       return outputStream.toByteArray();
    }
}
