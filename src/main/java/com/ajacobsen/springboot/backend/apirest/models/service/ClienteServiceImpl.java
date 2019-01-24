package com.ajacobsen.springboot.backend.apirest.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ajacobsen.springboot.backend.apirest.models.dao.IClienteDao;
import com.ajacobsen.springboot.backend.apirest.models.entity.Cliente;

@Service
public class ClienteServiceImpl implements IClienteService{
	
	//Inyeccion de dependencia, es como hacer
	/*
	 * 
	 * ClienteServiceImpl (IClienteDao _clienteDao){
	 * this.clienteDao = _clienteDao;
	 * 
	 */
	@Autowired 
	IClienteDao clienteDao;
	
	//El transactional no es necesario por estar en CrudRepository
	@Override
	@Transactional(readOnly = true)
	public List<Cliente> findAll() {
		// TODO Auto-generated method stub
		return (List<Cliente>) clienteDao.findAll();
	}

	@Override
	public Cliente save(Cliente cliente) {
		// TODO Auto-generated method stub
		return clienteDao.save(cliente);
	}

	@Override
	@Transactional(readOnly = true)
	public Cliente findOne(Long id) {
		return clienteDao.findById(id).orElse(null);
	}

	@Override
	public void delete(Long id) {
		clienteDao.deleteById(id);
	}

}
