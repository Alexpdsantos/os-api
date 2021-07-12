package com.alex.os.services;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alex.os.domain.Pessoa;
import com.alex.os.domain.Cliente;
import com.alex.os.dtos.ClienteDTO;
import com.alex.os.repositories.PessoaRepository;
import com.alex.os.repositories.ClienteRepository;
import com.alex.os.services.exceptions.DataIntegratyViolationException;
import com.alex.os.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository repository;
	
	@Autowired
	private PessoaRepository pessoaRepository;

	public Cliente findById(Integer id) {
		Optional<Cliente> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				("Objeto Não Encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName())));
	}

	// Está sendo implementado um método que retorna uma lista de todos os técnicos
	// existentes na base de dados
	public List<Cliente> findAll() {
		return repository.findAll();
	}

	// Está sendo implementado método para adicionar um novo técnico na base
	public Cliente create(ClienteDTO objDTO) {

		if (findByCPF(objDTO) != null) {
			throw new DataIntegratyViolationException("CPF já cadastrado!");
		}

		/*
		 * //modelo amplo pra adicionar novo Cliente na base Cliente newObj = new
		 * Cliente(null, objDTO.getNome(), objDTO.getCpf(), objDTO.getTelefone());
		 * return repository.save(newObj);
		 */
		// forma mais abreviada
		return repository.save(new Cliente(null, objDTO.getNome(), objDTO.getCpf(), objDTO.getTelefone()));

	}

	public Cliente update(Integer id, @Valid ClienteDTO objDTO) {
		Cliente oldObj = findById(id);
		if (findByCPF(objDTO) != null && findByCPF(objDTO).getId() != id) {
			throw new DataIntegratyViolationException("CPF já cadastrado!");
		}
		oldObj.setNome(objDTO.getNome());
		oldObj.setTelefone(objDTO.getTelefone());
		oldObj.setCpf(objDTO.getCpf());

		return repository.save(oldObj);

	}

	public void delete(Integer id) {
		Cliente obj = findById(id);
		if (obj.getList().size() > 0) {
			throw new DataIntegratyViolationException("Pessoa Possui Ordens de serviço e não pode ser Deletado!");
		}

		repository.deleteById(id);

	}

	private Pessoa findByCPF(ClienteDTO objDTO) {
		Pessoa obj = pessoaRepository.findByCPF(objDTO.getCpf());
		if (obj != null) {
			return obj;
		}
		return null;

	}

}