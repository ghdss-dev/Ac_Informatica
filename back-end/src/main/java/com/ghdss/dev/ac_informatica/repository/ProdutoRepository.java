package com.ghdss.dev.ac_informatica.repository;

import com.ghdss.dev.ac_informatica.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Integer> {
}
