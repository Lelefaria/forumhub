package com.example.forumhub.controller;

import com.example.forumhub.domain.topico.Dados;
import com.forumhub.domain.topico.Dados;
import com.forumhub.domain.topico.Topico;
import com.forumhub.domain.topico.TopicoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import java.net.URI;
import java.util.Optional;
import static com.forumhub.domain.topico.Dados.*;

@RestController
@RequestMapping("/topicos")
public class TopicoController {

    @Autowired
    private TopicoRepository topicoRepository;

    @PostMapping
    @Transactional
    public ResponseEntity<Dados.DadosDetalhamentoTopico> cadastrar(@RequestBody @Valid Dados.DadosCadastroTopico dados, UriComponentsBuilder uriBuilder) {
        Optional<Topico> topicoExistente = topicoRepository.findByTituloAndMensagem(dados.titulo(), dados.mensagem());
        if (topicoExistente.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        Topico topico = new Topico(dados);
        topicoRepository.save(topico);

        URI uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
        return ResponseEntity.created(uri).body(new Dados.DadosDetalhamentoTopico(topico));
    }

    @GetMapping
    public ResponseEntity<Page<Dados.DadosListagemTopico>> listar(
            @PageableDefault(size = 10, sort = {"dataDeCriacao"}, direction = Sort.Direction.ASC) Pageable paginacao
    ) {
        Page<Dados.DadosListagemTopico> page = topicoRepository.findAll(paginacao).map(DadosListagemTopico::new);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Dados.DadosDetalhamentoTopico> detalhar(@PathVariable Long id) {
        Optional<Topico> topico = topicoRepository.findById(id);
        return topico.map(value -> ResponseEntity.ok(new Dados.DadosDetalhamentoTopico(value)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<Dados.DadosDetalhamentoTopico> atualizar(@PathVariable Long id, @RequestBody @Valid DadosAtualizacaoTopico dados) {
        Optional<Topico> topicoOptional = topicoRepository.findById(id);
        if (topicoOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Topico topico = topicoOptional.get();
        if (dados.titulo() != null && dados.mensagem() != null) {
            Optional<Topico> topicoDuplicado = topicoRepository.findByTituloAndMensagem(dados.titulo(), dados.mensagem());
            if (topicoDuplicado.isPresent() && !topicoDuplicado.get().getId().equals(topico.getId())) {
                return ResponseEntity.status(HttpStatus.CONFLICT).build();
            }
        }

        topico.atualizar(dados);
        topicoRepository.save(topico);
        return ResponseEntity.ok(new DadosDetalhamentoTopico(topico));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        if (!topicoRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        topicoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}