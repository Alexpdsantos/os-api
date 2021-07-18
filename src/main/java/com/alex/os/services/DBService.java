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
		Tecnico t3 = new Tecnico(null, "André Silva", "265.334.200-62", "(19)991247679");
		Tecnico t4 = new Tecnico(null, "Antônio Sauro", "117.917.530-19", "(19)991247679");
		Tecnico t5 = new Tecnico(null, "Rodrigo Souza", "031.952.080-32", "(19)991247679");
		Tecnico t6 = new Tecnico(null, "Creiton Horácio", "760.949.370-82", "(19)991247679");
		Cliente c1 = new Cliente(null, "Ana Elisa", "204.312.160-02", "(19)991247677");
		Cliente c2 = new Cliente(null, "Sivinha", "174.177.910-34", "(19)991247671");
		OS os1 = new OS(null, Prioridade.ALTA, "Teste Create OS", Status.ANDAMENTO, t1, c1);
		OS os2 = new OS(null, Prioridade.BAIXA, "Teste Create OS", Status.ABERTO, t3, c2);
		OS os3 = new OS(null, Prioridade.MEDIA, "Teste Create OS", Status.ENCERRADO, t5, c1);

		t1.getList().add(os1);
		c1.getList().add(os1);
		
		t3.getList().add(os2);
		c2.getList().add(os2);
		
		t5.getList().add(os3);
		c1.getList().add(os3);

		tecnicoRepository.saveAll(Arrays.asList(t1,t2,t3,t4,t5,t6));
		clienteRepository.saveAll(Arrays.asList(c1,c2));
		osRepository.saveAll(Arrays.asList(os1,os2,os3));

	}
}
