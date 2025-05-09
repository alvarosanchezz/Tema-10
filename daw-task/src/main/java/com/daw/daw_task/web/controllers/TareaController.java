package com.daw.daw_task.web.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.daw.daw_task.persistence.entities.Tarea;
import com.daw.daw_task.persistence.entities.enums.Estado;
import com.daw.daw_task.services.dto.TareaServicio;
import com.daw.daw_task.services.exceptions.TareaException;
import com.daw.daw_task.services.exceptions.TareaNotFoundException;

@RestController
@RequestMapping("/tareas")
public class TareaController {

	@Autowired
	private TareaServicio tareaServicio;

	@GetMapping
	public ResponseEntity<List<Tarea>> list() {
		return ResponseEntity.status(HttpStatus.OK).body(this.tareaServicio.findAll());
	}

	@GetMapping("/{idTarea}")
	public ResponseEntity<?> findById(@PathVariable int idTarea) {
		try {
			return ResponseEntity.ok(this.tareaServicio.findById(idTarea));
		} catch (TareaNotFoundException ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
		}
	}

	@DeleteMapping("/{idTarea}")
	public ResponseEntity<?> delete(@PathVariable int idTarea) {
		try {
			this.tareaServicio.deleteById(idTarea);
			return ResponseEntity.ok().build();
		} catch (TareaNotFoundException ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El ID inicado no existe");
		}
	}

	@PostMapping
	public ResponseEntity<Tarea> create(@RequestBody Tarea tarea) {
		return ResponseEntity.status(HttpStatus.CREATED).body(this.tareaServicio.create(tarea));
	}

	@PutMapping("/{idTarea}")
	public ResponseEntity<?> update(@PathVariable int idTarea, @RequestBody Tarea tarea) {
		try {
			return ResponseEntity.ok(this.tareaServicio.update(idTarea, tarea));
		} catch (TareaNotFoundException ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
		}
	}

	@PutMapping("/{idTarea}/iniciar")
	public ResponseEntity<?> iniciarTarea(@PathVariable int idTarea) {
		try {
			return ResponseEntity.ok(tareaServicio.iniciarTarea(idTarea));
		} catch (TareaException | TareaNotFoundException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
		}
	}

	@PutMapping("/{idTarea}/completar")
	public ResponseEntity<?> completarTarea(@PathVariable int idTarea) {
		try {
			return ResponseEntity.ok(tareaServicio.completarTarea(idTarea));
		} catch (TareaException | TareaNotFoundException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
		}
	}

	@GetMapping("/buscar/{texto}")
	public ResponseEntity<List<Tarea>> buscarPorTitulo(@PathVariable String texto) {
		return ResponseEntity.ok(tareaServicio.buscarPorTitulo(texto));
	}

	@GetMapping("/estado/{estado}")
	public ResponseEntity<List<Tarea>> tareasPorEstado(@PathVariable Estado estado) {
		return ResponseEntity.ok(tareaServicio.obtenerPorEstado(estado));
	}

	@GetMapping("/vencidas")
	public ResponseEntity<List<Tarea>> tareasVencidas() {
		return ResponseEntity.ok(tareaServicio.tareasVencidas());
	}

	@GetMapping("/noVencidas")
	public ResponseEntity<List<Tarea>> tareasNoVencidas() {
		return ResponseEntity.ok(tareaServicio.tareasNoVencidas());
	}

}
