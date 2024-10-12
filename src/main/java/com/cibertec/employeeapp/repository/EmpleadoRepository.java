package com.cibertec.employeeapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cibertec.model.Empleado;

public interface EmpleadoRepository extends JpaRepository<Empleado, Long>{

	List<Empleado> findByNombreContainingIgnoreCase(String nombre);

//	@Query("SELECT e FROM Empleado e WHERE e.nombre = :nombre")
//	List<Empleado> buscarEmpleadoPorNombre(String nombre);
	
	
	//@Query(value="SELECT * FROM empleado WHERE nombre LIKE %:nombre%", nativeQuery = true)
	//@Query(value = "SELECT * FROM empleado WHERE nombre = :nombre", nativeQuery = true)
	@Query(value = "SELECT * FROM empleado WHERE nombre LIKE CONCAT('%', :nombre, '%')", nativeQuery = true)
	List<Empleado> buscarEmpleadoPorNombre(String nombre);
}
