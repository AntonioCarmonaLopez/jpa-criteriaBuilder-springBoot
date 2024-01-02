package com.acl.empleados.components;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.acl.empleados.entity.Empleado;

@Repository
public interface EmpleadoRepository extends JpaRepository<Empleado, Integer>{
	Iterable<?> findByNombre(String nombre);
	Iterable<?> findByApellido(String apellido);

}
