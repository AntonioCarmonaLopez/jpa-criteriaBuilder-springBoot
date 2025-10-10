package com.acl.empleados.components;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.acl.empleados.entity.Empleado;

@Repository
public interface EmpleadoRepository extends JpaRepository<Empleado, Integer>{
	List<Empleado> findByNombre(String nombre);
	List<Empleado> findByApellido(String apellido);

}
