package com.daw.daw_task.persistence.repositories;

import org.springframework.data.repository.ListCrudRepository;

import com.daw.daw_task.persistence.entities.Tarea;
import com.daw.daw_task.persistence.entities.enums.Estado;

public interface TareaRepositorio extends ListCrudRepository<Tarea, Integer> {

	long countByEstado(Estado estado);

}
