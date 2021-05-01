package com.contato.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ws.rs.QueryParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.contato.model.Contato;
import com.contato.repository.ContatoRepository;

@RestController
@RequestMapping("/contatos")
@CrossOrigin("*")
public class ContatoController {

	@Autowired
	private ContatoRepository repository;
	
	/**
	 * @author gabsschrodinger
	 * @return Retorna todos os contatos cadastrados no banco de dados
	 */
	@GetMapping
	public ResponseEntity<List<Contato>> verTodosProdutos() {
		return ResponseEntity.ok(repository.findAll());
	}
	
	/**
	 * @author gabsschrodinger
	 * @return Retorna uma lista de contatos filtrados por nome, sobrenome e email
	 */
	@GetMapping("/filtrar")
	public ResponseEntity<List<Contato>> verProdutosFiltrados(
			@QueryParam("nome") String nome, 
			@QueryParam("sobrenome") String sobrenome, 
			@QueryParam("email") String email
		) {
		List<Contato> listaFiltrada = new ArrayList<>();
		
		if(nome != null) {
			for(Contato i: repository.findAll()) {
				if(i.getPrimeiroNome().toLowerCase().equals(nome.toLowerCase())) {
					listaFiltrada.add(i);
				}
			}
		}
		
		if(sobrenome != null) {
			for(Contato j: repository.findAll()) {
				if(j.getUltimoNome().toLowerCase().equals(sobrenome.toLowerCase())) {
					listaFiltrada.add(j);
				}
			}
		}
		
		if(email != null) {
			for(Contato k: repository.findAll()) {
				if(k.getEmail().toLowerCase().equals(email.toLowerCase())) {
					listaFiltrada.add(k);
				}
			}
		}
		
		if(listaFiltrada.size() > 0) {
			return ResponseEntity.ok(listaFiltrada);
		} else {
			throw new RuntimeException("Não foram encontrados contatos com os parâmetros inseridos.");
		}
		
	}
	
	/**
	 * @author gabsschrodinger
	 * @param id
	 * @return Retorna um contato pelo id
	 */
	@GetMapping("/id/{id}")
	public ResponseEntity<Contato> verContatoPorId(@PathVariable long id) {
		return repository.findById(id).map(resp -> ResponseEntity.ok(resp))
				.orElse(ResponseEntity.notFound().build());
	}
	
	/**
	 * @author gabsschrodinger
	 * @param email
	 * @return Valida o e-mail do contato, verificando se há um "@", um domínimo e um "."
	 */
	public void validarEmailInvalido(String email) {
		String emailRegex = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";
		Pattern emailPat = Pattern.compile(emailRegex,Pattern.CASE_INSENSITIVE);
		Matcher matcher = emailPat.matcher(email);
		if(matcher.find() == false) {
			throw new RuntimeException("E-mail inválido.");
		}
	}
	
	/**
	 * @author gabsschrodinger
	 * @param telefones
	 * @return Valida se há, no mínimo UM número de telefone no contato
	 */
	public void validarTelefones(List<String> telefones) {
		if(telefones.size() == 0) {
			throw new RuntimeException("O contato deve conter, no mínimo, UM número de telefone.");
		}
	}
	
	/**
	 * @author gabsschrodinger
	 * @return Cadastra um novo contato no banco de dados
	 */
	@PostMapping("/cadastrar")
	public ResponseEntity<Contato> cadastrarContato(@RequestBody Contato contato) {
		
		validarEmailInvalido(contato.getEmail());
		
		validarTelefones(contato.getTelefones());
		
		return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(contato));
	}
	
	/**
	 * @author gabsschrodinger
	 * @return Faz alterações em um contato já existente
	 */
	@PutMapping("/editar")
	public ResponseEntity<Contato> editarContato(@RequestBody Contato contato) {
		
		validarEmailInvalido(contato.getEmail());
		
		validarTelefones(contato.getTelefones());
		
		return ResponseEntity.status(HttpStatus.OK).body(repository.save(contato));
	}
	
	/**
	 * @author gabsschrodinger
	 * @return Deleta um contato do banco de dados
	 */
	@DeleteMapping("/deletar/{id}")
	public void deletarContato(@PathVariable long id) {
		repository.deleteById(id);
	}
}
