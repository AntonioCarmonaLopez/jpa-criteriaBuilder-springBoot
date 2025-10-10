package com.acl.empleados.exception;

public class EmpleadoNotFoundException extends Exception{

    private static final long serialVersionUID = 1L;

	public EmpleadoNotFoundException(String message) {
        super(message);
    }
}
