package com.acl.empleados.components;

import java.util.List;

import com.acl.empleados.entity.Empleado;


public interface IEmpleadoService {

	Empleado aniadirEmpleado(Empleado emp);
	List<Empleado> buscarTodos();
	List<Empleado> buscarEmpleadroByNombre(String nombre);
	Empleado editarEmpleado(Empleado emp);
	boolean borrarEmpleado(Integer id);
	Iterable<?> buscarEmpleadoByapellido(String apellido);
	Iterable<?> criteriaBuilder(Empleado emp);
	
}
