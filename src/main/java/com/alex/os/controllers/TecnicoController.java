package com.alex.os.controllers;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.alex.os.domain.Tecnico;
import com.alex.os.dtos.TecnicoDTO;
import com.alex.os.services.TecnicoService;

@CrossOrigin("*")
@RestController
@RequestMapping(value = "/tecnicos")
public class TecnicoController {

	@Autowired
	private TecnicoService service;

	// Pegando um tecnico via ID
	@GetMapping(value = "/{id}")
	public ResponseEntity<TecnicoDTO> findById(@PathVariable Integer id) {
		TecnicoDTO objDTO = new TecnicoDTO(service.findById(id));
		return ResponseEntity.ok().body(objDTO);
	}

	// fazendo a requisição de uma lista de técnicos na base de dados
	@GetMapping
	public ResponseEntity<List<TecnicoDTO>> findAll() {
		/*
		 * List<Tecnico> list = service.findAll(); //precisamos fazer a conversão da
		 * lista de técnicos para lista de TecnicoDTO //aproveitando para implementar um
		 * novo ArrayList para n ter o problema de NullPointedException List<TecnicoDTO>
		 * listDTO = new ArrayList<>(); //ForEach pegando cada tecnico e adicionando à
		 * listDTO for(Tecnico obj : list) { listDTO.add(new TecnicoDTO(obj)); }
		 * 
		 * // Forma mais simplificada da anterior //list.forEach(obj -> listDTO.add(new
		 * TecnicoDTO(obj)));
		 * 
		 * return ResponseEntity.ok().body(listDTO);
		 */

		// Melhor forma de fazer essa Requisição
		List<TecnicoDTO> listDTO = service.findAll().stream().map(obj -> new TecnicoDTO(obj))
				.collect(Collectors.toList());
		return ResponseEntity.ok().body(listDTO);
	}

	@PostMapping
	public ResponseEntity<TecnicoDTO> create(@Valid @RequestBody TecnicoDTO objDTO) {
		Tecnico newObj = service.create(objDTO);

		// quando o novo obj é criado na base de dados, deve-se por boas práticas passar
		// a URI de acesso a este novo obj
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newObj.getId()).toUri();

		return ResponseEntity.created(uri).build();

	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<TecnicoDTO> update(@PathVariable Integer id, @Valid @RequestBody TecnicoDTO objDTO) {
		TecnicoDTO newObj = new TecnicoDTO(service.update(id, objDTO));
		return ResponseEntity.ok().body(newObj);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
}