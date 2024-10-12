package com.cibertec.employeeapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cibertec.model.Departamento;

public interface DepartamentoRepository extends JpaRepository<Departamento, Long> {

}
