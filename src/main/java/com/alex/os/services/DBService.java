package com.alex.os.services;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alex.os.domain.Cliente;
import com.alex.os.domain.OS;
import com.alex.os.domain.Tecnico;
import com.alex.os.domain.enums.Prioridade;
import com.alex.os.domain.enums.Status;
import com.alex.os.repositories.ClienteRepository;
import com.alex.os.repositories.OSRepository;
import com.alex.os.repositories.TecnicoRepository;

@Service
public class DBService {
	
	@Autowired
	private TecnicoRepository tecnicoRepository;
	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired
	private OSRepository osRepository;

	public void instanciaDB() {
		Tecnico t1 = new Tecnico(null, "Alex Santos", "499.282.980-50", "(19)991247679");
		Tecnico t2 = new Tecnico(null, "Henry Melo", "206.263.020-48", "(19)456325464");
		Cliente c1 = new Cliente(null, "Ana Elisa", "204.312.160-02", "(19)991247677");
		OS os1 = new OS(null, Prioridade.ALTA, "Teste Create OS", Status.ANDAMENTO, t1, c1);

		t1.getList().add(os1);
		c1.getList().add(os1);

		tecnicoRepository.saveAll(Arrays.asList(t1,t2));
		clienteRepository.saveAll(Arrays.asList(c1));
		osRepository.saveAll(Arrays.asList(os1));

	}

}
