package com.demo.desafio.cheque;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChequeRepository extends JpaRepository<Cheque, Integer> {
    Cheque findByNumero(int numero);
}
