package com.cibertec.employeeapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

import com.cibertec.employeeapp.service.DepartamentoService;
import com.cibertec.model.Departamento;

@Controller
@RequestMapping("/departamentos")
public class DepartamentoController {

    @Autowired
    private DepartamentoService departamentoService;

    @GetMapping
    public String listaDepartamentos(Model model) {
       model.addAttribute("departamentos", departamentoService.obtenerDepartamentos());
       return "lista_departamentos";
    }
    
    @GetMapping("/agregar")
    public String mostrarFormularioAgregar(Model model) {
        model.addAttribute("departamento", new Departamento());
        return "departamento_form";
    }
    
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable("id") Long id, Model model) {
        Departamento departamento = departamentoService.obtenerDepartamentoPorId(id);
        model.addAttribute("departamento", departamento);
        return "departamento_edit";
    }
    
    @GetMapping("/eliminar/{id}")
    public String eliminarDepartamento(@PathVariable("id") Long id) {
        departamentoService.eliminarDepartamentoPorId(id);
        return "redirect:/departamentos"; // Redirige a la lista de departamentos
    }

    @PostMapping("/guardar")
    public String guardarDepartamento(@ModelAttribute("departamento") Departamento departamento) {
        departamentoService.guardarDepartamento(departamento);
        return "redirect:/departamentos";
    }
    
//    @PostMapping("/guardar")
//    public ResponseEntity<String> agregarDepartamento(@ModelAttribute("departamento") Departamento departamento) {
//        departamentoService.guardarDepartamento(departamento);
//        return ResponseEntity.status(HttpStatus.CREATED).body("Departamento guardado con éxito.");
//    }
    
   
    
    @GetMapping("/")
    public RedirectView redireccionarDepartamentosConBarraFinal() { 
        return new RedirectView("/departamentos"); 
    }
}
