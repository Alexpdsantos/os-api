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
		Tecnico t1 = new Tecnico(null, "Alex Santos", "499.282.980-50", "(19)991237670");
		Tecnico t2 = new Tecnico(null, "André Silva", "265.334.200-62", "(19)991287671");
		
		Cliente c1 = new Cliente(null, "Maria Clara", "204.312.160-02", "(19)991245697");
		Cliente c2 = new Cliente(null, "Pedro Souza", "174.177.910-34", "(19)991257671");
		OS os1 = new OS(null, Prioridade.ALTA, "Teste Create OS", Status.ABERTO, t1, c1);
		OS os2 = new OS(null, Prioridade.BAIXA, "Teste Create OS- 2", Status.ABERTO, t2, c2);

		t1.getList().add(os1);
		c1.getList().add(os1);
		
		t2.getList().add(os2);
		c2.getList().add(os2);

		tecnicoRepository.saveAll(Arrays.asList(t1,t2));
		clienteRepository.saveAll(Arrays.asList(c1,c2));
		osRepository.saveAll(Arrays.asList(os1,os2));
	}
}
