package com.contato.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.contato.model.Contato;

@Repository
public interface ContatoRepository extends JpaRepository<Contato, Long> {
	
	public List<Contato> findAllByPrimeiroNomeContainingIgnoreCase(String primeiroNome);
	
	public List<Contato> findAllByUltimoNomeContainingIgnoreCase(String ultimoNome);
	
	public List<Contato> findAllByEmailContainingIgnoreCase(String email);
}
