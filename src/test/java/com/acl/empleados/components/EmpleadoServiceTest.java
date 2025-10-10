package com.acl.empleados.components;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.acl.empleados.entity.Empleado;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

public class EmpleadoServiceTest {

	@Mock
	private EmpleadoRepository repo;
	

@Mock
private EntityManager entityManager;

@Mock
private CriteriaBuilder criteriaBuilder;

@Mock
private CriteriaQuery<Empleado> criteriaQuery;

@Mock
private Root<Empleado> root;

@Mock
private Predicate predicate;

@Mock
private TypedQuery<Empleado> typedQuery;


	@InjectMocks
	private EmpleadoService empleadoService;

	@Before
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void testBuscarTodos_ReturnsListOK() {
		List<Empleado> empleados = Arrays.asList(new Empleado(), new Empleado());
		when(repo.findAll()).thenReturn(empleados);

		List<Empleado> result = empleadoService.buscarTodos();

		assertNotNull(result);
		assertEquals(2, result.size());
		assertEquals(empleados, result);
	}

	@Test
	public void testBuscarTodos_Exception_ReturnsNullKO() {
		when(repo.findAll()).thenThrow(new RuntimeException("DB error"));

		List<Empleado> result = empleadoService.buscarTodos();

		assertNull(result);
	}

	@Test
	public void testBuscarEmpleadroByNombre_ReturnsListOK() {
		String nombre = "Juan";
		List<Empleado> empleados = Arrays.asList(new Empleado(), new Empleado());
		when(repo.findByNombre(nombre)).thenReturn(empleados);

		List<Empleado> result = empleadoService.buscarEmpleadroByNombre(nombre);

		assertNotNull(result);
		assertEquals(2, result.size());
		assertEquals(empleados, result);
	}

	@Test
	public void testBuscarEmpleadroByNombre_EmptyList_ReturnsNullKO() {
		String nombre = "NoExiste";
		when(repo.findByNombre(nombre)).thenReturn(Collections.emptyList());

		List<Empleado> result = empleadoService.buscarEmpleadroByNombre(nombre);

		assertNotNull(result);
		assertTrue(result.isEmpty()); // Verifica que retorna lista vacía
		assertEquals(0, result.size());
	}

	@Test
	public void testBuscarEmpleadroByNombre_Exception_ReturnsNullKO() {
		String nombre = "Error";
		when(repo.findByNombre(nombre)).thenThrow(new RuntimeException("DB error"));

		List<Empleado> result = empleadoService.buscarEmpleadroByNombre(nombre);

		assertNull(result);
	}

	@Test
	public void testBuscarEmpleadoByapellido_ReturnsListOK() {
		String apellido = "García";
		List<Empleado> empleados = Arrays.asList(new Empleado(), new Empleado());
		when(repo.findByApellido(apellido)).thenReturn(empleados);

		List<Empleado> result = empleadoService.buscarEmpleadoByapellido(apellido);

		assertNotNull(result);
		assertEquals(2, result.size());
		assertEquals(empleados, result);
	}

	@Test
	public void testBuscarEmpleadoByapellido_EmptyList_ReturnsEmptyListOK() {
		String apellido = "NoExiste";
		when(repo.findByApellido(apellido)).thenReturn(Collections.emptyList());

		List<Empleado> result = empleadoService.buscarEmpleadoByapellido(apellido);

		assertNotNull(result);
		assertTrue(result.isEmpty());
		assertEquals(0, result.size());
	}

	@Test
	public void testAniadirEmpleado_Success_ReturnsEmpleadoOK() {
		Empleado empleadoInput = new Empleado();
		empleadoInput.setNombre("Juan");
		empleadoInput.setApellido("Pérez");

		Empleado empleadoSaved = new Empleado();
		empleadoSaved.setId(1);
		empleadoSaved.setNombre("Juan");
		empleadoSaved.setApellido("Pérez");

		when(repo.save(empleadoInput)).thenReturn(empleadoSaved);

		Empleado result = empleadoService.aniadirEmpleado(empleadoInput);

		assertNotNull(result);
		assertEquals(1, result.getId()); // Cast explícito
		assertEquals("Juan", result.getNombre());
		assertEquals("Pérez", result.getApellido());
	}

	@Test
	public void testAniadirEmpleado_Exception_ReturnsNullKO() {
		Empleado empleadoInput = new Empleado();
		empleadoInput.setNombre("Test");

		when(repo.save(empleadoInput)).thenThrow(new RuntimeException("DB error"));

		Empleado result = empleadoService.aniadirEmpleado(empleadoInput);

		assertNull(result);
	}

	@SuppressWarnings("null")
	@Test
	public void testAniadirEmpleado_NullInput_ReturnsNullKO() {
		// Mock para que save retorne null cuando reciba null
		when(repo.save(null)).thenReturn(null);

		Empleado result = empleadoService.aniadirEmpleado(null);

		assertNull(result);
	}

	@Test
	public void testEditarEmpleado_Success_ReturnsUpdatedEmpleadoOK() {
		Empleado empleadoInput = new Empleado();
		empleadoInput.setId(1);
		empleadoInput.setNombre("Juan");
		empleadoInput.setApellido("Pérez");

		Empleado empleadoUpdated = new Empleado();
		empleadoUpdated.setId(1);
		empleadoUpdated.setNombre("Juan");
		empleadoUpdated.setApellido("Pérez");

		when(repo.save(empleadoInput)).thenReturn(empleadoUpdated);

		Empleado result = empleadoService.editarEmpleado(empleadoInput);

		assertNotNull(result);
		assertEquals(1, result.getId());
		assertEquals("Juan", result.getNombre());
		assertEquals("Pérez", result.getApellido());
		verify(repo).save(empleadoInput);
	}

	@Test
	public void testEditarEmpleado_Exception_ReturnsNullKO() {
		Empleado empleadoInput = new Empleado();
		empleadoInput.setId(1);
		empleadoInput.setNombre("Test");

		when(repo.save(empleadoInput)).thenThrow(new RuntimeException("DB error"));

		Empleado result = empleadoService.editarEmpleado(empleadoInput);

		assertNull(result);
		verify(repo).save(empleadoInput);
	}

	@SuppressWarnings("null")
	@Test
	public void testEditarEmpleado_NullInput_ReturnsNullKO() {
		when(repo.save(null)).thenReturn(null);

		Empleado result = empleadoService.editarEmpleado(null);

		assertNull(result);
		verify(repo).save(null);
	}

	@Test
	public void testBorrarEmpleado_Success_ReturnsOK() {
		Integer id = 1;

		// No necesitamos configurar mock ya que deleteById no retorna valor
		// El método funciona si no se lanza excepción

		boolean result = empleadoService.borrarEmpleado(id);

		assertTrue(result);
		verify(repo).deleteById(id);
	}

	@Test
	public void testBorrarEmpleado_Exception_ReturnsKO() {
		Integer id = 999;

		doThrow(new RuntimeException("DB error")).when(repo).deleteById(id);

		boolean result = empleadoService.borrarEmpleado(id);

		assertFalse(result);
		verify(repo).deleteById(id);
	}

	@SuppressWarnings("null")
	@Test
	public void testBorrarEmpleado_NullId_ReturnsKO() {
		doThrow(new IllegalArgumentException("ID cannot be null")).when(repo).deleteById(null);

		boolean result = empleadoService.borrarEmpleado(null);

		assertFalse(result);
		verify(repo).deleteById(null);
	}

	@Test
	public void testCriteriaBuilder_Sin_Fecha_baja_OK() {
	    Empleado empleadoInput = new Empleado();
	    empleadoInput.setNombre("Juan");
	    empleadoInput.setApellido("Pérez");
	    empleadoInput.setEdad(30);

	    List<Empleado> expectedResult = Arrays.asList(new Empleado(), new Empleado());

	    // Mock del EntityManager y sus componentes
	    when(entityManager.getCriteriaBuilder()).thenReturn(criteriaBuilder);
	    when(criteriaBuilder.createQuery(Empleado.class)).thenReturn(criteriaQuery);
	    when(criteriaQuery.from(Empleado.class)).thenReturn(root);
	    when(criteriaBuilder.equal(any(), any())).thenReturn(predicate);

	    when(entityManager.createQuery(criteriaQuery)).thenReturn(typedQuery);
	    when(typedQuery.getResultList()).thenReturn(expectedResult);

	    Iterable<?> result = empleadoService.criteriaBuilder(empleadoInput);

	    assertNotNull(result);
	    assertEquals(expectedResult, result);
	    verify(entityManager).getCriteriaBuilder();
	    verify(entityManager).createQuery(criteriaQuery);
	}

	@Test
	public void testCriteriaBuilder_Con_fecha_baja_OK() {
	    Empleado empleadoInput = new Empleado();
	    empleadoInput.setNombre("Juan");
	    empleadoInput.setBaja(LocalDateTime.now()); // Asegura que baja no es null

	    List<Empleado> expectedResult = Arrays.asList(new Empleado());

	    when(entityManager.getCriteriaBuilder()).thenReturn(criteriaBuilder);
	    when(criteriaBuilder.createQuery(Empleado.class)).thenReturn(criteriaQuery);
	    when(criteriaQuery.from(Empleado.class)).thenReturn(root);
	    when(criteriaBuilder.equal(any(), any())).thenReturn(predicate);
	    // Si hay lógica específica para baja, mockéala aquí
	    when(entityManager.createQuery(criteriaQuery)).thenReturn(typedQuery);
	    when(typedQuery.getResultList()).thenReturn(expectedResult);

	    Iterable<?> result = empleadoService.criteriaBuilder(empleadoInput);

	    assertNotNull(result);
	    assertEquals(expectedResult, result);
	    verify(entityManager).getCriteriaBuilder();
	    verify(entityManager).createQuery(criteriaQuery);
	} 
	
	@Test(expected = NullPointerException.class)
	public void testCriteriaBuilder_WithNullInput_ThrowsNullPointerExceptionKO() {
	    Empleado empleadoInput = null;

	    when(entityManager.getCriteriaBuilder()).thenReturn(criteriaBuilder);
	    when(criteriaBuilder.createQuery(Empleado.class)).thenReturn(criteriaQuery);
	    when(criteriaQuery.from(Empleado.class)).thenReturn(root);

	    empleadoService.criteriaBuilder(empleadoInput);
	}

}