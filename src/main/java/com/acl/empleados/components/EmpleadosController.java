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
	public ResponseEntity<?> buscarTodos(BindingResult bindingResult) {
		ResponseEntity<?> responseEntity = null;
		Iterable<?> lista = null;

		lista = this.srv.buscarTodos();
		if (lista != null) {
			responseEntity = ResponseEntity.ok(lista);
		} else {
			responseEntity = validar(bindingResult);
		}
		return responseEntity;
	}

	@GetMapping("/buscarxnombre")
	public ResponseEntity<?> buscarXNombre(String nombre) {
		ResponseEntity<?> responseEntity = null;
		Iterable<?> lista = null;

		lista = this.srv.buscarEmpleadroByNombre(nombre);
		if (lista != null) {
			responseEntity = ResponseEntity.ok(lista);
		} else {
			responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);
		}

		return responseEntity;
	}

	@GetMapping("/buscarxapellido")
	public ResponseEntity<?> buscarEmpleadroByapellido(String apellido) {
		ResponseEntity<?> responseEntity = null;
		Iterable<?> lista = null;

		lista = this.srv.buscarEmpleadroByapellido(apellido);
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
	public ResponseEntity<?> aniadirEmpleado(@RequestBody Empleado emp, BindingResult bindingResult) {
		ResponseEntity<?> responseEntity = null;

		if (bindingResult.hasErrors()) {
			responseEntity = validar(bindingResult);
		} else {
			this.srv.aniadirEmpleado(emp);
			responseEntity = ResponseEntity.status(HttpStatus.CREATED).body(emp);
		}
		return responseEntity;
	}

	@PostMapping("/editarempleado")
	public ResponseEntity<?> editarEmpeado(@RequestBody Empleado emp, BindingResult bindingResult) {
		ResponseEntity<?> responseEntity = null;
		this.srv.editarEmpeado(emp);

		if (bindingResult.hasErrors()) {
			responseEntity = validar(bindingResult);
		} else {
			this.srv.editarEmpeado(emp);
			responseEntity = ResponseEntity.status(HttpStatus.CREATED).body(emp);
		}
		return responseEntity;
	}

	@DeleteMapping("/borrarempleado")
	public ResponseEntity<?> borrarPacientePorId(Integer id) {
		ResponseEntity<?> responseEntity = null;

		if(this.srv.borrarEmpleado(id)){
			responseEntity = ResponseEntity.ok().build();
		} else {
			responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);

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
