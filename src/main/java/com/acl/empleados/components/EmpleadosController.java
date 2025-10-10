package com.acl.empleados.components;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.acl.empleados.entity.Empleado;

@RestController
@RequestMapping(value = "/empleado")
public class EmpleadosController {

	@Autowired
	EmpleadoService srv;

	@GetMapping
	public String getTestData() {
		return "Hello world";
	}

	@GetMapping("/buscartodos")
	public ResponseEntity<List<Empleado>> buscarTodos(BindingResult bindingResult) {
		ResponseEntity<List<Empleado>> responseEntity = null;
		List<Empleado> lista = null;

		lista = this.srv.buscarTodos();
		if (lista != null) {
			responseEntity = ResponseEntity.ok(lista);
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
		return responseEntity;
	}

	@GetMapping("/buscarxnombre")
	public ResponseEntity<List<Empleado>> buscarXNombre(String nombre) {
		ResponseEntity<List<Empleado>> responseEntity = null;
		List<Empleado> lista = null;

		lista = this.srv.buscarEmpleadroByNombre(nombre);
		if (lista != null) {
			responseEntity = ResponseEntity.ok(lista);
		} else {
			responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}

		return responseEntity;
	}

	@GetMapping("/buscarxapellido")
	public ResponseEntity<List<Empleado>> buscarEmpleadoByapellido(String apellido) {
		ResponseEntity<List<Empleado>> responseEntity = null;
		List<Empleado> lista = null;

		lista = this.srv.buscarEmpleadoByapellido(apellido);
		if (lista != null) {
			responseEntity = ResponseEntity.ok(lista);
		} else {
			responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}

		return responseEntity;
	}

	@PostMapping("/criteriabuilder")
	public ResponseEntity<?> criteriaBuilder(@RequestBody Empleado emp, BindingResult bindingResult) {
		ResponseEntity<?> responseEntity = null;
		Iterable<?> lista = null;
		lista = this.srv.criteriaBuilder(emp);
		if (lista != null) {
			responseEntity = ResponseEntity.ok(lista);
		} else {
			responseEntity = validar(bindingResult);
		}

		return responseEntity;
	}

	@PostMapping("/aniadirempleado")
	public ResponseEntity<Empleado> aniadirEmpleado(@RequestBody Empleado emp) {
		ResponseEntity<Empleado> responseEntity = null;
		Empleado empleado = this.srv.aniadirEmpleado(emp);
		if(empleado != null) {
			responseEntity = ResponseEntity.status(HttpStatus.CREATED).body(emp);
		} else {
			responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
		
		return responseEntity;
	}

	@PostMapping("/editarempleado")
	public ResponseEntity<Empleado> editarEmpleado(@RequestBody Empleado emp) {
		ResponseEntity<Empleado> responseEntity = null;

		Empleado empleado = this.srv.editarEmpleado(emp);
		if(empleado != null) {
			responseEntity = ResponseEntity.status(HttpStatus.OK).body(emp);
		} else {
			responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
		return responseEntity;
	}

	@DeleteMapping("/borrarempleado")
	public ResponseEntity<Empleado> borrarPacientePorId(Integer id) {
		ResponseEntity<Empleado> responseEntity = null;

		if (this.srv.borrarEmpleado(id)) {
			responseEntity = ResponseEntity.ok().build();
		} else {
			responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

		}
		return responseEntity;
	}

	private ResponseEntity<?> validar(BindingResult bindingResult) {
		ResponseEntity<?> responseEntity = null;
		List<ObjectError> listaErrores = null;

		listaErrores = bindingResult.getAllErrors();
		responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(listaErrores);

		return responseEntity;
	}
	
	
}
