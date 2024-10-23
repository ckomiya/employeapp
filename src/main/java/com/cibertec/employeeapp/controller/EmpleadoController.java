package com.cibertec.employeeapp.controller;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cibertec.employeeapp.service.DepartamentoService;
import com.cibertec.employeeapp.service.EmpleadoService;
import com.cibertec.model.Departamento;
import com.cibertec.model.Empleado;

import jakarta.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Controller
@RequestMapping("/empleados")
public class EmpleadoController {

	@Autowired
	private EmpleadoService empleadoService;

	@Autowired
	private DepartamentoService departamentoService;

	@GetMapping
	public String listaEmpleados(Model model) {
		model.addAttribute("empleados", empleadoService.obtenerEmpleados());
		return "lista_empleados";
	}

	@GetMapping("/buscar")
	public String buscarEmpleados(Model model) {
		model.addAttribute("empleados", empleadoService.obtenerEmpleados());
		return "busq_empleados";
	}

	@GetMapping("/agregar")
	public String mostrarFormularioAgregar(Model model) {
		model.addAttribute("empleado", new Empleado());
		model.addAttribute("departamentos", departamentoService.obtenerDepartamentos()); // Obtener la lista de
																							// departamentos
		return "empleado_form"; // Retorna la vista del formulario
	}

	@PostMapping("/guardar")
	public String guardarEmpleado(@ModelAttribute("empleado") Empleado empleado) {
		empleadoService.guardarEmpleado(empleado);
		return "redirect:/empleados";
	}

	@GetMapping("/buscarxnombre")
	public String buscarEmpleados(@RequestParam("nombre") String nombre, Model model) {
		List<Empleado> empleados = empleadoService.buscarEmpleadosPorNombre(nombre);
		model.addAttribute("empleados", empleados);

		return "busq_empleados";
	}

	@GetMapping("/buscar2")
	public String buscarEmpleadosPorDepartamento(Model model) {
		model.addAttribute("departamentos", departamentoService.obtenerDepartamentos());
		model.addAttribute("departamentoFilter", new Departamento());
		return "busq_empleados_por_dep";
	}

	@GetMapping("/buscar_empleado_por_dep")
	public String buscarEmpleadosPorDepartamaento(@RequestParam("id") Long depId, Model model) {
		List<Empleado> empleados = empleadoService.obtenerEmpleadoPorDepartamento(new Departamento(depId));
		model.addAttribute("empleados", empleados);
		model.addAttribute("departamentoFilter", new Departamento(depId));
		model.addAttribute("departamentos", departamentoService.obtenerDepartamentos());
		return "busq_empleados_por_dep";
	}

	@GetMapping("/editar/{id}")
	public String mostrarFormularioEditar(@PathVariable("id") Long id, Model model) {
		Empleado empleado = empleadoService.obtenerEmpleadoPorId(id);
		model.addAttribute("empleado", empleado);
		model.addAttribute("departamentos", departamentoService.obtenerDepartamentos()); // Para llenar el select
		return "empleado_edit";
	}

//    @GetMapping("/reporte")
//    public void generateReport(HttpServletResponse response) throws Exception {
//        // Obtener la lista de empleados
//        List<Empleado> empleados = empleadoService.obtenerEmpleados();
//
//        // Cargar el archivo Jasper compilado
//        InputStream reportStream = getClass().getResourceAsStream("/reports/reporte_empleados.jrxml");
//
//        // Llenar el reporte con los datos de los empleados
//        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(empleados);
//        Map<String, Object> parameters = new HashMap<String, Object>();
//
//        // Agregar la imagen como parámetro
//        String imagePath = getClass().getResource("/images/cherry.jpg").getPath();
//        parameters.put("imagePath", imagePath); // 'imagePath' es el nombre del parámetro en el informe
//
//        // Compilar el archivo .jrxml
//        JasperReport jasperReport = JasperCompileManager.compileReport(reportStream);
//
//        // Llenar el reporte
//        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
//
//        // Establecer la respuesta HTTP para generar el PDF
//        response.setContentType("application/pdf");
//        response.setHeader("Content-Disposition", "attachment; filename=reporte_empleados.pdf");
//
//        // Exportar el reporte a PDF
//        JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());
//    }
	
	
	@GetMapping("/reporte")
	public void generateReport(HttpServletResponse response) throws Exception {
		// Obtener la lista de empleados
		List<Empleado> empleados = empleadoService.obtenerEmpleados();

		// Cargar el archivo Jasper compilado
		InputStream reportStream = getClass().getResourceAsStream("/reports/reporte_empleados.jrxml");

		// Llenar el reporte con los datos de los empleados
		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(empleados);
		Map<String, Object> parameters = new HashMap<String, Object>();

		// Agregar la imagen como parámetro
		String imagePath = getClass().getResource("/images/cherry.jpg").getPath();
		parameters.put("imagePath", imagePath); // 'imagePath' es el nombre del parámetro en el informe

		// Compilar el archivo .jrxml
		JasperReport jasperReport = JasperCompileManager.compileReport(reportStream);

		// Llenar el reporte
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

		// Establecer la respuesta HTTP para generar el PDF
		response.setContentType("application/pdf");
		response.setHeader("Content-Disposition", "attachment; filename=reporte_empleado.pdf");

		// Exportar el reporte a PDF
		JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());
	}

//	@GetMapping("/reporteGrafico")
//	public void generateReporteGrafico(HttpServletResponse response) throws Exception {
//
//		List<Empleado> empleados = empleadoService.obtenerEmpleados();
//
//		InputStream reportStream = getClass().getResourceAsStream("/reports/reporte_grafico.jrxml");
//
//		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(empleados);
//		Map<String, Object> parameters = new HashMap<>();
//		parameters.put("ReportTitle", "Reporte de Empleados");
//
//		JasperReport jasperReport = JasperCompileManager.compileReport(reportStream);
//
//		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
//
//		response.setContentType("application/pdf");
//		response.setHeader("Content-Disposition", "attachment; filename=reporte_grafico.pdf");
//
//		JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());
//	}
	
	@GetMapping("/reporteGrafico")
	public void generateReporteGrafico(HttpServletResponse response) throws Exception {
	    
	    // Obtén la lista de empleados (debería estar disponible a través de un servicio o repositorio)
	    List<Empleado> empleados = empleadoService.obtenerEmpleados();

	    // Cargar el archivo .jrxml
	    InputStream reportStream = getClass().getResourceAsStream("/reports/reporte_salarios.jrxml");

	    // Fuente de datos para el reporte
	    JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(empleados);

	    // Parámetros del reporte
	    Map<String, Object> parameters = new HashMap<>();
	    parameters.put("ReportTitle", "Reporte de Salarios");

	    // Compilar el reporte
	    JasperReport jasperReport = JasperCompileManager.compileReport(reportStream);

	    // Llenar el reporte con los datos
	    JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

	    // Configurar la respuesta HTTP para la descarga del PDF
	    response.setContentType("application/pdf");
	    response.setHeader("Content-Disposition", "attachment; filename=reporte_salarios.pdf");

	    // Exportar el reporte a PDF
	    JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());
	}

}
