package com.acl.empleados.components;


import com.acl.empleados.entity.Empleado;


public interface IEmpleadoService {

	void aniadirEmpleado(Empleado emp);
	Iterable<?> buscarTodos();
	Iterable<?> buscarEmpleadroByNombre(String nombre);
	void editarEmpeado(Empleado emp);
	boolean borrarEmpleado(Integer id);
	Iterable<?> buscarEmpleadroByapellido(String apellido);
	Iterable<?> criteriaBuilder(Empleado emp);
	
}
