package com.sena.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sena.ecommerce.model.DetalleOrden;

public interface IDetalleOrdenRepository extends JpaRepository<DetalleOrden, Integer> {

}
