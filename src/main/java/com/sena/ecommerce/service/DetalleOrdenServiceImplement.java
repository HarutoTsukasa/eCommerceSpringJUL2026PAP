package com.sena.ecommerce.service;

import org.springframework.stereotype.Service;

import com.sena.ecommerce.model.DetalleOrden;
import com.sena.ecommerce.repository.IDetalleOrdenRepository;

@Service
public class DetalleOrdenServiceImplement implements IDetalleOrdenService {

	private final IDetalleOrdenRepository detalleOrdenRepository;

	DetalleOrdenServiceImplement(IDetalleOrdenRepository detalleOrdenRepository) {
		this.detalleOrdenRepository = detalleOrdenRepository;
	}

	@Override
	public DetalleOrden save(DetalleOrden detalleOrden) {
		// TODO Auto-generated method stub
		return detalleOrdenRepository.save(detalleOrden);
	}

}
