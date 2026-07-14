package com.sena.ecommerce.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.sena.ecommerce.model.Orden;
import com.sena.ecommerce.model.Usuario;
import com.sena.ecommerce.repository.IOrdenRepository;

@Service
public class OrdenServiceImplement implements IOrdenService {

	private final IOrdenRepository ordenRepository;

	OrdenServiceImplement(IOrdenRepository ordenRepository) {
		this.ordenRepository = ordenRepository;
	}

	@Override
	public Orden save(Orden orden) {
		// TODO Auto-generated method stub
		return ordenRepository.save(orden);
	}

	@Override
	public List<Orden> findAll() {
		// TODO Auto-generated method stub
		return ordenRepository.findAll();
	}

	@Override
	public String generarNumeroOrden() {
		// TODO Auto-generated method stub
		List<Orden> ordenes = findAll();

		int numero = ordenes.stream().map(Orden::getNumero).mapToInt(Integer::parseInt).max().orElse(0) + 1;
		return String.format("%013d", numero);
	}

	@Override
	public List<Orden> findByUsuario(Usuario usuario) {
		// TODO Auto-generated method stub
		return ordenRepository.findByUsuario(usuario);
	}

	@Override
	public Optional<Orden> findById(Integer id) {
		// TODO Auto-generated method stub
		return ordenRepository.findById(id);
	}

}
