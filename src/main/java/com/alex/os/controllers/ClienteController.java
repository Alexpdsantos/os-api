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

import com.alex.os.domain.Cliente;
import com.alex.os.dtos.ClienteDTO;
import com.alex.os.services.ClienteService;

@CrossOrigin("*")
@RestController
@RequestMapping(value = "/clientes")
public class ClienteController {

	@Autowired
	private ClienteService service;

	// Pegando um tecnico via ID
	@GetMapping(value = "/{id}")
	public ResponseEntity<ClienteDTO> findById(@PathVariable Integer id) {
		ClienteDTO objDTO = new ClienteDTO(service.findById(id));
		return ResponseEntity.ok().body(objDTO);
	}

	// fazendo a requisição de uma lista de técnicos na base de dados
	@GetMapping
	public ResponseEntity<List<ClienteDTO>> findAll() {
		/*
		 * List<Cliente> list = service.findAll(); //precisamos fazer a conversão da
		 * lista de técnicos para lista de ClienteDTO //aproveitando para implementar um
		 * novo ArrayList para n ter o problema de NullPointedException List<ClienteDTO>
		 * listDTO = new ArrayList<>(); //ForEach pegando cada tecnico e adicionando à
		 * listDTO for(Cliente obj : list) { listDTO.add(new ClienteDTO(obj)); }
		 * 
		 * // Forma mais simplificada da anterior //list.forEach(obj -> listDTO.add(new
		 * ClienteDTO(obj)));
		 * 
		 * return ResponseEntity.ok().body(listDTO);
		 */

		// Melhor forma de fazer essa Requisição
		List<ClienteDTO> listDTO = service.findAll().stream().map(obj -> new ClienteDTO(obj))
				.collect(Collectors.toList());
		return ResponseEntity.ok().body(listDTO);
	}

	@PostMapping
	public ResponseEntity<ClienteDTO> create(@Valid @RequestBody ClienteDTO objDTO) {
		Cliente newObj = service.create(objDTO);

		// quando o novo obj é criado na base de dados, deve-se por boas práticas passar
		// a URI de acesso a este novo obj
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newObj.getId()).toUri();

		return ResponseEntity.created(uri).build();

	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<ClienteDTO> update(@PathVariable Integer id, @Valid @RequestBody ClienteDTO objDTO) {
		ClienteDTO newObj = new ClienteDTO(service.update(id, objDTO));
		return ResponseEntity.ok().body(newObj);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
}