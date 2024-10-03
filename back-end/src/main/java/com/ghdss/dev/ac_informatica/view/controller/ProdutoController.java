package com.ghdss.dev.ac_informatica.view.controller;

import com.ghdss.dev.ac_informatica.model.Produto;
import com.ghdss.dev.ac_informatica.services.ProdutoService;
import com.ghdss.dev.ac_informatica.shared.ProdutoDTO;
import com.ghdss.dev.ac_informatica.view.model.ProdutoRequest;
import com.ghdss.dev.ac_informatica.view.model.ProdutoResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @GetMapping
    public ResponseEntity<List<ProdutoResponse>> obterTodos() {

        List<ProdutoDTO> produtos =  produtoService.obterTodos();

        ModelMapper mapper = new ModelMapper();

        List<ProdutoResponse> resposta = produtos.stream()
                .map(produtoDTO -> mapper.map(produtoDTO, ProdutoResponse.class))
                .collect(Collectors.toList());

        return new ResponseEntity<>(resposta, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoResponse> obterPorId(@PathVariable Integer id) {
        try {
            Optional<ProdutoDTO> dto = produtoService.obterPorId(id);

            if (dto.isPresent()) {
                // Mapeia o ProdutoDTO para ProdutoResponse
                ProdutoResponse produto = new ModelMapper().map(dto.get(), ProdutoResponse.class);

                // Retorna o ProdutoResponse com status 200 (OK)
                return new ResponseEntity<>(produto, HttpStatus.OK);
            } else {
                // Retorna 204 (No Content) caso o produto não seja encontrado
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

        } catch (Exception e) {
            // Em caso de erro, retorna 500 (Internal Server Error)
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping
    public ProdutoResponse adicionar(@RequestBody ProdutoRequest produtoReq) {

        ModelMapper mapper = new ModelMapper();

        ProdutoDTO produtoDTO = mapper.map(produtoReq, ProdutoDTO.class);

        produtoDTO = produtoService.adicionar(produtoDTO);

        return new ResponseEntity<>(mapper.map(produtoDTO, ProdutoResponse.class), HttpStatus.CREATED).getBody();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Integer id) {

        produtoService.deletar(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProdutoResponse> atualizar(@RequestBody ProdutoRequest produtoReq, @PathVariable Integer id) {

       ModelMapper mapper = new ModelMapper();
       ProdutoDTO produtoDto = mapper.map(produtoReq, ProdutoDTO.class);

        produtoDto = produtoService.atualizar(id, produtoDto);

        return new ResponseEntity<>(
                mapper.map(produtoDto, ProdutoResponse.class),
                HttpStatus.OK
        );

    }
}
