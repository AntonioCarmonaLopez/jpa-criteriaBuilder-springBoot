package com.acl.empleados.entity;


import java.time.LocalDateTime;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="empleados")
public class Empleado {

	@Column(name="id")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name="nombre")
	@NotNull
	@NotBlank(message = "Nombre requerido")
	@Size(min = 3, max = 20)
	private String nombre;
	
	@Column(name="apellido")
	@NotNull
	@NotBlank(message = "Apellido requerido")
	@Size(min = 3, max = 20)
	private String apellido;
	
	@Column(name="edad")
	@NotNull
	@NotBlank(message = "Edad requerida")
	@Min(0)
	@Max(130)
	private int edad;
	
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@Column(name="alta")
	@NotBlank(message = "Fecha alta requerida")
	@NotNull
	private LocalDateTime alta;
	
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@Column(name="baja")
	private LocalDateTime baja;
	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public int getEdad() {
		return edad;
	}

	public void setEdad(int edad) {
		this.edad = edad;
	}

	public LocalDateTime getAlta() {
		return alta;
	}

	public void setAlta(LocalDateTime alta) {
		this.alta = alta;
	}

	public LocalDateTime getBaja() {
		return baja;
	}

	public void setBaja(LocalDateTime baja) {
		this.baja = baja;
	}
}
