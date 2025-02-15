package com.acl.empleados.components;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.acl.empleados.entity.Empleado;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

@Service
public class EmpleadoService implements IEmpleadoService {
	
	private enum ORIGENES  {ANADIR,EDITAR}

	@PersistenceContext
	private EntityManager entityManager;

	EmpleadoRepository repo;
	
	public EmpleadoService(EmpleadoRepository repo) {
		this.repo = repo;
	}

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public Iterable<?> buscarTodos() {
		Iterable<?> result = null;
		try {
			result = repo.findAll();
		} catch (Exception e) {
			log.info("error en el metodo buscarTodos");
		}
		return result;
	}

	@Override
	public Iterable<?> buscarEmpleadroByNombre(String nombre) {
		Iterable<?> result = null;
		try {
			result = repo.findByNombre(nombre);
		} catch  (Exception e) {
			log.info("error en el metodo buscarEmpleadroByNombre");
		}
		return result;
	}

	@Override
	public Iterable<?> buscarEmpleadroByapellido(String apellido) {
		Iterable<?> result = null;
		try {
			result = repo.findByApellido(apellido);
		} catch (Exception e) {
			log.info("error en el metodo buscarEmpleadroByapellido");
		}
		return result;
	}

	@Override
	public Iterable<?> criteriaBuilder(Empleado emp) {
		Iterable<?> result = null;
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Empleado> cq = cb.createQuery(Empleado.class);

		Root<Empleado> empleado = cq.from(Empleado.class);
		List<Predicate> predicates = new ArrayList<>();

		predicates.add(cb.equal(empleado.get("nombre"), emp.getNombre()));

		predicates.add(cb.equal(empleado.get("apellido"), emp.getApellido()));

		predicates.add(cb.greaterThanOrEqualTo(empleado.get("alta"), emp.getAlta()));

		if (emp.getEdad() != 0) {
			predicates.add(cb.equal(empleado.get("edad"), emp.getEdad()));
		}
		if (emp.getBaja() != null) {
			predicates.add(cb.equal(empleado.get("baja"), emp.getBaja()));
		}
		
		cq.where(predicates.toArray(new Predicate[0]));
		try {
			result = entityManager.createQuery(cq).getResultList();
		} catch (Exception e) {
			log.info("error en el metodo criteriaBuider");
		}
		return result;
	}

	@Override
	public void aniadirEmpleado(Empleado emp) {
		aniadirEditarEmpleado(emp,ORIGENES.ANADIR.toString());
	}

	@Override
	public void editarEmpeado(Empleado emp) {
		aniadirEditarEmpleado(emp,ORIGENES.EDITAR.toString());
	}

	@Override
	public boolean borrarEmpleado(Integer id) {
		boolean result = false;
		try {
			this.repo.deleteById(id);
			result = true;
		} catch (Exception e) {
			log.info("error en el metodo editarEmpleado");
		}
		return result;
	}
	
	private Empleado aniadirEditarEmpleado(Empleado emp,String origen) {
		Empleado empNuevo = null;
		try  {
			empNuevo = repo.save(emp);
		} catch (Exception e) {
			if(origen.equals(ORIGENES.ANADIR.toString())) {
				log.info("error en el metodo aniadirEmpleado");
			} else {
				log.info("error en el metodo editarEmpleado");
			}
		}
		return empNuevo;
	}
		
}
