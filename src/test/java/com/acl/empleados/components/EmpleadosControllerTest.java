package com.acl.empleados.components;

import static org.junit.Assert.*; // Use JUnit 4 assertions
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import com.acl.empleados.entity.Empleado;

public class EmpleadosControllerTest {

	@Mock
	private EmpleadoService empleadoService;

	@Mock
	private BindingResult bindingResult;

	@InjectMocks
	private EmpleadosController empleadosController;

	private AutoCloseable closeable;

	@Before
	public void setUp() {
		closeable = MockitoAnnotations.openMocks(this);
	}

	@org.junit.After
	public void tearDown() throws Exception {
		if (closeable != null) {
			closeable.close();
		}
	}

	@Test
	public void testBuscarTodos_ReturnsOk() {
		List<Empleado> empleados = Arrays.asList(new Empleado(), new Empleado());
		when(empleadoService.buscarTodos()).thenReturn(empleados);

		ResponseEntity<?> response = empleadosController.buscarTodos(bindingResult);

		assertEquals(200, response.getStatusCode().value());
		assertSame(empleados, response.getBody());
	}

	@Test
	public void testBuscarTodos_ReturnsBadRequestKO() {
		when(empleadoService.buscarTodos()).thenReturn(null);

		ResponseEntity<?> response = empleadosController.buscarTodos(bindingResult);

		assertEquals(400, response.getStatusCode().value());
	}

	@Test
	public void testBuscarXNombre_ReturnsOk() {
		String nombre = "Juan";
		List<Empleado> empleados = Arrays.asList(new Empleado(), new Empleado());
		when(empleadoService.buscarEmpleadroByNombre(nombre)).thenReturn(empleados);

		ResponseEntity<List<Empleado>> response = empleadosController.buscarXNombre(nombre);

		assertEquals(200, response.getStatusCode().value());
		assertSame(empleados, response.getBody());
	}

	@Test
	public void testBuscarXNombre_ReturnsBadRequestKO() {
		String nombre = "NoExiste";
		when(empleadoService.buscarEmpleadroByNombre(nombre)).thenReturn(null);

		ResponseEntity<List<Empleado>> response = empleadosController.buscarXNombre(nombre);

		assertEquals(400, response.getStatusCode().value());
		assertNull(response.getBody());
	}

	@Test
	public void testBuscarEmpleadoByapellido_ReturnsOk() {
		String apellido = "García";
		List<Empleado> empleados = Arrays.asList(new Empleado(), new Empleado());
		when(empleadoService.buscarEmpleadoByapellido(apellido)).thenReturn(empleados);

		ResponseEntity<List<Empleado>> response = empleadosController.buscarEmpleadoByapellido(apellido);

		assertEquals(200, response.getStatusCode().value());
		assertSame(empleados, response.getBody());
	}

	@Test
	public void testBuscarEmpleadoByapellido_ReturnsBadRequestKO() {
		String apellido = "NoExiste";
		when(empleadoService.buscarEmpleadoByapellido(apellido)).thenReturn(null);

		ResponseEntity<List<Empleado>> response = empleadosController.buscarEmpleadoByapellido(apellido);

		assertEquals(400, response.getStatusCode().value());
		assertNull(response.getBody());
	}

	@Test
	public void testBuscarEmpleadoByapellido_NullApellido_ReturnsBadRequestKO() {
		when(empleadoService.buscarEmpleadoByapellido(null)).thenReturn(null);

		ResponseEntity<List<Empleado>> response = empleadosController.buscarEmpleadoByapellido(null);

		assertEquals(400, response.getStatusCode().value());
		assertNull(response.getBody());
	}

	@Test
	public void testAniadirEmpleado_Success_ReturnsCreatedPK() {
		Empleado empleadoInput = new Empleado();
		empleadoInput.setNombre("Juan");
		empleadoInput.setApellido("Pérez");

		Empleado empleadoSaved = new Empleado();
		empleadoSaved.setId(1);
		empleadoSaved.setNombre("Juan");
		empleadoSaved.setApellido("Pérez");

		when(empleadoService.aniadirEmpleado(empleadoInput)).thenReturn(empleadoSaved);

		ResponseEntity<Empleado> response = empleadosController.aniadirEmpleado(empleadoInput);

		assertEquals(201, response.getStatusCode().value());
		assertEquals(empleadoInput, response.getBody());
	}

	@Test
	public void testAniadirEmpleado_ServiceReturnsNull_ReturnsBadRequestKO() {
		Empleado empleadoInput = new Empleado();
		empleadoInput.setNombre("Test");

		when(empleadoService.aniadirEmpleado(empleadoInput)).thenReturn(null);

		ResponseEntity<Empleado> response = empleadosController.aniadirEmpleado(empleadoInput);

		assertEquals(400, response.getStatusCode().value());
		assertNull(response.getBody());
	}

	@Test
	public void testAniadirEmpleado_NullInput_ReturnsBadRequestKO() {
		when(empleadoService.aniadirEmpleado(null)).thenReturn(null);

		ResponseEntity<Empleado> response = empleadosController.aniadirEmpleado(null);

		assertEquals(400, response.getStatusCode().value());
		assertNull(response.getBody());
	}

	@Test
	public void testEditarEmpleado_Success_ReturnOK() {
		Empleado empleadoInput = new Empleado();
		empleadoInput.setNombre("Juan");
		empleadoInput.setApellido("Pérez");

		Empleado empleadoSaved = new Empleado();
		empleadoSaved.setId(1);
		empleadoSaved.setNombre("Juan");
		empleadoSaved.setApellido("Pérez");

		when(empleadoService.editarEmpleado(empleadoInput)).thenReturn(empleadoSaved);

		ResponseEntity<Empleado> response = empleadosController.editarEmpleado(empleadoInput);

		assertEquals(200, response.getStatusCode().value());
		assertEquals(empleadoInput, response.getBody());
	}

	@Test
	public void testEditarEmpleado_ServiceReturnsNull_ReturnsBadRequestKO() {
		Empleado empleadoInput = new Empleado();
		empleadoInput.setNombre("Test");

		when(empleadoService.aniadirEmpleado(empleadoInput)).thenReturn(null);

		ResponseEntity<Empleado> response = empleadosController.editarEmpleado(empleadoInput);

		assertEquals(400, response.getStatusCode().value());
		assertNull(response.getBody());
	}

	@Test
	public void testEditarEmpleado_NullInput_ReturnsBadRequestKO() {
		when(empleadoService.aniadirEmpleado(null)).thenReturn(null);

		ResponseEntity<Empleado> response = empleadosController.editarEmpleado(null);

		assertEquals(400, response.getStatusCode().value());
		assertNull(response.getBody());
	}

	/*@Test
	public void testCriteriaBuilder_ConResultados_RetornaOk() {
	    Empleado emp = new Empleado();
	    List<Empleado> empleados = Arrays.asList(new Empleado(), new Empleado());

		when(empleadoService.criteriaBuilder(emp)).thenReturn((Iterable<?>) empleados);

	    ResponseEntity<?> response = empleadosController.criteriaBuilder(emp, bindingResult);

	    assertEquals(200, response.getStatusCode().value());
	    assertEquals(empleados, response.getBody());
	}*/
	
	@Test
    public void testCriteriaBuilder_CallsValidarOnNullResultKO() {
        Empleado emp = new Empleado();
        List<ObjectError> errores = Arrays.asList(
            new ObjectError("empleado", "Error de validación")
        );

        when(empleadoService.criteriaBuilder(emp)).thenReturn(null);
        when(bindingResult.getAllErrors()).thenReturn(errores);

        ResponseEntity<?> response = empleadosController.criteriaBuilder(emp, bindingResult);

        assertEquals(400, response.getStatusCode().value());
        assertEquals(errores, response.getBody());
    }
	
	@Test
	public void testBorrarPacientePorId_Success_ReturnsOk() {
		Integer id = 1;

		when(empleadoService.borrarEmpleado(id)).thenReturn(true);

		ResponseEntity<Empleado> response = empleadosController.borrarPacientePorId(id);

		assertEquals(200, response.getStatusCode().value());
		assertNull(response.getBody());
	}

	@Test
	public void testBorrarPacientePorId_NotFound_ReturnsBadRequestKO() {
		Integer id = 999;

		when(empleadoService.borrarEmpleado(id)).thenReturn(false);

		ResponseEntity<Empleado> response = empleadosController.borrarPacientePorId(id);

		assertEquals(400, response.getStatusCode().value());
		assertNull(response.getBody());
	}

	@Test
	public void testBorrarPacientePorId_NullId_ReturnsBadRequestKO() {
		when(empleadoService.borrarEmpleado(null)).thenReturn(false);

		ResponseEntity<Empleado> response = empleadosController.borrarPacientePorId(null);

		assertEquals(400, response.getStatusCode().value());
		assertNull(response.getBody());
	}


}