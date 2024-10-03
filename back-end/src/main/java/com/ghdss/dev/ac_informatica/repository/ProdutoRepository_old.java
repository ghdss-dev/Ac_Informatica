package com.ghdss.dev.ac_informatica.repository;

import com.ghdss.dev.ac_informatica.model.Produto;
import com.ghdss.dev.ac_informatica.model.exception.ResourceNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class ProdutoRepository_old {

    private ArrayList<Produto> produtos = new ArrayList<Produto>();
    private Integer ultimoId = 0;

    public List<Produto> obterTodos() {
        return produtos;
    }

    public Optional<Produto> obterPorId (Integer id) {

        return produtos
                .stream()
                .filter(produto -> produto.getId() == id)
                .findFirst();
    }

    public Produto adicionar(Produto produto) {

        ultimoId ++;
        produto.setId(ultimoId);
        produtos.add(produto);

        return produto;
    }

    public void deletar(Integer id) {

        produtos.removeIf(produto -> produto.getId() == id);
    }

    public Produto atualizar(Produto produto) {

        Optional<Produto> produtoEncontrado = obterPorId(produto.getId());

        if (produtoEncontrado.isEmpty()) {
            throw new ResourceNotFoundException("Produto não encontrado");
        }

        deletar(produto.getId());

        produtos.add(produto);

        return produto;
    }
}
