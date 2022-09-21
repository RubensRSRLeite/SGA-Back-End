package com.backend.sga.rest;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.backend.sga.model.Ambiente;
import com.backend.sga.model.Erro;
import com.backend.sga.model.Sucesso;
import com.backend.sga.repository.AmbienteRepository;

//CrossOrigin serve para que o projeto receba JSON
@CrossOrigin
@RestController
@RequestMapping("/api/ambiente")
public class AmbienteRestController {

	@Autowired
	private AmbienteRepository ambienteRepository;
	
	//metodo para criar o ambiente
	@RequestMapping(value = "", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> criarAmbiente (@RequestBody Ambiente ambiente, HttpServletRequest request){
			if(ambiente != null) {
				ambiente.setAtivo(true); // setando o ativo como true (padrão)
				ambienteRepository.save(ambiente); // salvando o ambiente no banco de dados
				Sucesso sucesso = new Sucesso(HttpStatus.OK, "Sucesso");
				return new ResponseEntity<Object>(sucesso, HttpStatus.OK);
			}else {
				Erro erro = new Erro(HttpStatus.INTERNAL_SERVER_ERROR, "Não foi possivel cadastrar um ambiente", null);
				return new ResponseEntity<Object>(erro, HttpStatus.INTERNAL_SERVER_ERROR);
			}
	}
	
	//metodo para excluir por 'ID'
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Object> desativarAmbiente (@PathVariable("id") Long id, Ambiente ambiente,HttpServletRequest request){// Verificando se o id do 'ambiente' é igual ao do passado
		if(ambiente.getId() == id) {
			ambiente.setAtivo(false); // setando o Ativo como false, para estar desativado
			ambienteRepository.save(ambiente); // salvando o ambiente
			Sucesso sucesso = new Sucesso(HttpStatus.OK, "Sucesso");
			return new ResponseEntity<Object>(sucesso, HttpStatus.OK);
		}else {
			Erro erro = new Erro(HttpStatus.INTERNAL_SERVER_ERROR, "Não foi possivel cadastrar um ambiente", null);
			return new ResponseEntity<Object>(erro, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	//Buscando todos os dados no banco
	@RequestMapping(value = "", method = RequestMethod.GET)
	public Iterable<Ambiente> listaAmbiente (Ambiente ambiente){
		return ambienteRepository.findAll();
	}
	
	//metodo para alterar
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> atualizarAmbiente(@PathVariable("id") Long id, @RequestBody Ambiente ambiente, HttpServletRequest request){
		if (ambiente.getId() != id) {
			Erro erro = new Erro(HttpStatus.INTERNAL_SERVER_ERROR, "ID inválido", null);
			return new ResponseEntity<Object>(erro, HttpStatus.INTERNAL_SERVER_ERROR);
		}else {
			ambienteRepository.save(ambiente);
			Sucesso sucesso = new Sucesso(HttpStatus.OK, "Sucesso");
			return new ResponseEntity<Object>(sucesso, HttpStatus.OK);
		}
	}
	
}
