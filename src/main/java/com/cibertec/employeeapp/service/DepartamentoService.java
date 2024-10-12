package com.cibertec.employeeapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cibertec.employeeapp.repository.DepartamentoRepository;
import com.cibertec.model.Departamento;

@Service
public class DepartamentoService {

    @Autowired
    private DepartamentoRepository departamentoRepository;

    public List<Departamento> obtenerDepartamentos() {
        return departamentoRepository.findAll();
    }
    
    public void eliminarDepartamentoPorId(Long id) {
        departamentoRepository.deleteById(id);
    }
    
    
    
    public void guardarDepartamento(Departamento departamento) {
        departamentoRepository.save(departamento);
    }
    
    public Departamento obtenerDepartamentoPorId(Long id) {
        return departamentoRepository.findById(id).orElse(null);
    }
    
}
