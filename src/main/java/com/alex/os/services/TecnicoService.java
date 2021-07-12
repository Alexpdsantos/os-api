package com.alex.os.services;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alex.os.domain.Pessoa;
import com.alex.os.domain.Tecnico;
import com.alex.os.dtos.TecnicoDTO;
import com.alex.os.repositories.PessoaRepository;
import com.alex.os.repositories.TecnicoRepository;
import com.alex.os.services.exceptions.DataIntegratyViolationException;
import com.alex.os.services.exceptions.ObjectNotFoundException;

@Service
public class TecnicoService {

	@Autowired
	private TecnicoRepository repository;
	
	@Autowired
	private PessoaRepository pessoaRepository;

	public Tecnico findById(Integer id) {
		Optional<Tecnico> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				("Objeto Não Encontrado! Id: " + id + ", Tipo: " + Tecnico.class.getName())));
	}

	// Está sendo implementado um método que retorna uma lista de todos os técnicos
	// existentes na base de dados
	public List<Tecnico> findAll() {
		return repository.findAll();
	}

	// Está sendo implementado método para adicionar um novo técnico na base
	public Tecnico create(TecnicoDTO objDTO) {

		if (findByCPF(objDTO) != null) {
			throw new DataIntegratyViolationException("CPF já cadastrado!");
		}

		/*
		 * //modelo amplo pra adicionar novo Tecnico na base Tecnico newObj = new
		 * Tecnico(null, objDTO.getNome(), objDTO.getCpf(), objDTO.getTelefone());
		 * return repository.save(newObj);
		 */
		// forma mais abreviada
		return repository.save(new Tecnico(null, objDTO.getNome(), objDTO.getCpf(), objDTO.getTelefone()));

	}

	public Tecnico update(Integer id, @Valid TecnicoDTO objDTO) {
		Tecnico oldObj = findById(id);
		if (findByCPF(objDTO) != null && findByCPF(objDTO).getId() != id) {
			throw new DataIntegratyViolationException("CPF já cadastrado!");
		}
		oldObj.setNome(objDTO.getNome());
		oldObj.setTelefone(objDTO.getTelefone());
		oldObj.setCpf(objDTO.getCpf());

		return repository.save(oldObj);

	}

	public void delete(Integer id) {
		Tecnico obj = findById(id);
		if (obj.getList().size() > 0) {
			throw new DataIntegratyViolationException("Técnico Possui Ordens de serviço e não pode ser Deletado!");
		}

		repository.deleteById(id);

	}

	private Pessoa findByCPF(TecnicoDTO objDTO) {
		Pessoa obj = pessoaRepository.findByCPF(objDTO.getCpf());
		if (obj != null) {
			return obj;
		}
		return null;

	}

}