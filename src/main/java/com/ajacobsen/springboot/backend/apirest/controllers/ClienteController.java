package com.ajacobsen.springboot.backend.apirest.controllers;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.ajacobsen.springboot.backend.apirest.models.entity.Cliente;
import com.ajacobsen.springboot.backend.apirest.models.service.IClienteService;

//RequestMapping es para pasarle una ruta predefinida desde donde tomar los metodos o EndPoint
//Cuando llame a listar tengo que poner /api/listar .. 
@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/api/clientes")
@SessionAttributes("cliente")
public class ClienteController {

	protected final Log logger = LogFactory.getLog(this.getClass());

	// Cuando inyecto una interfaz o clase abstracta, spring busca la primer clase
	// que implementa esta interfaz,
	// En este caso va a ubicar al bean ClienteServiceImpl . Si tiene mas de una ->
	// Qualifier
	@Autowired
	private IClienteService clienteService;

	@GetMapping()
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody List<Cliente> listar() {
		return clienteService.findAll();
	}

	@GetMapping(value = "/{id}")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody Cliente ver(@PathVariable(value = "id") Long id) {
		return clienteService.findOne(id);
	}

	// Request a form con un cliente pasado por POST
	// Valid es para validadar que es un cliente con un formato valido
	@PostMapping()
	@ResponseStatus(HttpStatus.CREATED)
	public Cliente create(@Valid @RequestBody Cliente cliente, BindingResult result, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		if (!result.hasErrors()) {

			response.setStatus(200);

			// Sumarlo al header
			response.addHeader("Status", "200");

			// Mapeo el body
			Map<String, Object> body = new HashMap<String, Object>();

			// Coloco el cliente recien guardado
			body.put("Cliente", cliente);

			// Setear el tipo de contenido

			response.setContentType("application/json");

			return clienteService.save(cliente);
		} else {
			return null;
		}
	}

	// Request a form con un cliente pasado por POST
	// Valid es para validadar que es un cliente con un formato valido
	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Cliente update(@Valid @RequestBody Cliente cliente,BindingResult result ,@PathVariable(value = "id") Long id) {
			
			Cliente clienteAUpdatear = clienteService.findOne(id);
			
			if (clienteAUpdatear != null) {
				// Mapeo el body
				
				clienteAUpdatear.setNombre(cliente.getNombre());
				clienteAUpdatear.setApellido(cliente.getApellido());
				clienteAUpdatear.setEmail(cliente.getEmail());

				Map<String, Object> body = new HashMap<String, Object>();

				// Coloco el cliente recien guardado
				body.put("Cliente", clienteAUpdatear);

				// Setear el tipo de contenido

				return clienteService.save(clienteAUpdatear);
			}else {
				return null;
			}
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable(value = "id") Long id) {
			clienteService.delete(id);
	}
	
	
}
