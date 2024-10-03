package com.ghdss.dev.ac_informatica.services;

import com.ghdss.dev.ac_informatica.model.Produto;
import com.ghdss.dev.ac_informatica.model.exception.ResourceNotFoundException;
import com.ghdss.dev.ac_informatica.repository.ProdutoRepository;
import com.ghdss.dev.ac_informatica.repository.ProdutoRepository_old;
import com.ghdss.dev.ac_informatica.shared.ProdutoDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    public List<ProdutoDTO> obterTodos() {

        List<Produto> produtos =  produtoRepository.findAll();

        return produtos.stream()
                .map(produto -> new ModelMapper().map(produto, ProdutoDTO.class))
                .collect(Collectors.toList());
    }

    public Optional<ProdutoDTO> obterPorId(Integer id) {
        Optional<Produto> produto = produtoRepository.findById(id);
        if (produto.isEmpty()) {
            throw new ResourceNotFoundException("Produto com id: " + id + " não encontrado");
        }
        ProdutoDTO dto = new ModelMapper().map(produto.get(), ProdutoDTO.class);
        return Optional.of(dto);
    }

    public ProdutoDTO adicionar(ProdutoDTO produtoDto) {

        ModelMapper mapper = new ModelMapper();
        Produto produto = mapper.map(produtoDto, Produto.class);
        produto = produtoRepository.save(produto);
        produtoDto.setId(produto.getId());
        System.out.println("ID após a inserção: " + produtoDto.getId()); // Verifica se o ID foi gerado
        return produtoDto;
    }



    public void deletar(Integer id) {

        Optional<Produto> produto = produtoRepository.findById(id);

        if(produto.isEmpty()) {

            throw new ResourceNotFoundException("Não foi possível deletar o produto com o id: " + id + "Produto não existe");
        }

        produtoRepository.deleteById(id);
    }

    public ProdutoDTO atualizar(Integer id, ProdutoDTO produtoDto) {
        if (id == null) {
            throw new ResourceNotFoundException("ID não pode ser nulo");
        }
        produtoDto.setId(id); // Garante que o ID está sendo setado
        ModelMapper mapper = new ModelMapper();
        Produto produto = mapper.map(produtoDto, Produto.class);
        produtoRepository.save(produto);
        return produtoDto;
    }

}
