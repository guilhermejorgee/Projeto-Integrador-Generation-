package br.org.generation.ingressa.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.org.generation.ingressa.model.Tema;
import br.org.generation.ingressa.repository.TemaRepository;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/tema")
public class TemaController {

	@Autowired
	private TemaRepository repository;

	@GetMapping
	public ResponseEntity<List<Tema>> GetAll() {
		return ResponseEntity.ok(repository.findAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<Tema> GetById(@PathVariable long id) {
		return repository.findById(id).map(resp -> ResponseEntity.ok(resp)).orElse(ResponseEntity.notFound().build());
	}

	@GetMapping("/area/{area}")
	public ResponseEntity<List<Tema>> GetByArea(@PathVariable String area) {
		return ResponseEntity.ok(repository.findAllByAreaContainingIgnoreCase(area));
	}

	@GetMapping("/palavrachave/{palavrachave}")
	public ResponseEntity<List<Tema>> GetByPalavrachave(@PathVariable String palavrachave) {
		return ResponseEntity.ok(repository.findAllByPalavraChaveContainingIgnoreCase(palavrachave));
	}
	
	@GetMapping("/areaoupalavrachave/{pesquisa}")
	public ResponseEntity<List<Tema>> GetByPalavrachaveOuArea(@PathVariable String pesquisa) {
		return ResponseEntity.ok(repository.findAllByAreaContainingIgnoreCaseOrPalavraChaveContainingIgnoreCase(pesquisa, pesquisa));
	}

	@PostMapping
	public ResponseEntity<Tema> postTema(@RequestBody Tema tema) {
		return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(tema));
	}

	@PutMapping
	public ResponseEntity<Tema> putTema(@RequestBody Tema tema) {
		return ResponseEntity.ok(repository.save(tema));
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable long id) {
		repository.deleteById(id);
	}

}
