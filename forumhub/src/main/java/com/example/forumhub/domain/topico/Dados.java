package com.example.forumhub.domain.topico;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public final class Dados {

    public record DadosCadastroTopico(
            @NotBlank
            String titulo,
            @NotBlank
            String mensagem,
            @NotBlank
            String autor,
            @NotBlank
            String curso
    ) {}

    public record DadosListagemTopico(
            Long id,
            String titulo,
            String mensagem,
            LocalDateTime dataDeCriacao,
            String autor,
            String curso
    ) {
        public DadosListagemTopico(com.forumhub.domain.topico.Topico topico) {
            this(
                    topico.getId(),
                    topico.getTitulo(),
                    topico.getMensagem(),
                    topico.getDataDeCriacao(),
                    topico.getAutor(),
                    topico.getCurso()
            );
        }
    }

    public record DadosDetalhamentoTopico(
            Long id,
            String titulo,
            String mensagem,
            LocalDateTime dataDeCriacao,
            EstadoDoTopico estadoDoTopico,
            String autor,
            String curso
    ) {
        public DadosDetalhamentoTopico(com.forumhub.domain.topico.Topico topico) {
            this(
                    topico.getId(),
                    topico.getTitulo(),
                    topico.getMensagem(),
                    topico.getDataDeCriacao(),
                    topico.getEstadoDoTopico(),
                    topico.getAutor(),
                    topico.getCurso()
            );
        }
    }

    public record DadosAtualizacaoTopico(
            @NotNull
            Long id,
            String titulo,
            String mensagem,
            EstadoDoTopico estadoDoTopico
    ) {}
}