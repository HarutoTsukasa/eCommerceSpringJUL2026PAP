package com.sena.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sena.ecommerce.model.Producto;

public interface IProductoRepository extends JpaRepository<Producto, Integer> {

}
