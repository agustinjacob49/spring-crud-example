package com.ajacobsen.springboot.backend.apirest.models.service;

import java.util.List;

import com.ajacobsen.springboot.backend.apirest.models.entity.Cliente;

public interface IClienteService {

	public List <Cliente> findAll();
	
	public Cliente save(Cliente cliente);
	
	public Cliente findOne(Long id);
	
	public void delete (Long id);
	
}
