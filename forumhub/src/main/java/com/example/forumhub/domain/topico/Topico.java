package com.forumhub.domain.topico;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Table(name = "topicos")
@Entity(name = "Topico")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Topico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private String mensagem;
    private LocalDateTime dataDeCriacao = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    private EstadoDoTopico estadoDoTopico = EstadoDoTopico.NAO_RESPONDIDO;

    private String autor;
    private String curso;

    public Topico(Dados.DadosCadastroTopico dados) {
        this.titulo = dados.titulo();
        this.mensagem = dados.mensagem();
        this.autor = dados.autor();
        this.curso = dados.curso();
    }

    public void atualizar(Dados.DadosAtualizacaoTopico dados) {
        if (dados.titulo() != null) {
            this.titulo = dados.titulo();
        }
        if (dados.mensagem() != null) {
            this.mensagem = dados.mensagem();
        }
        if (dados.estadoDoTopico() != null) {
            this.estadoDoTopico = dados.estadoDoTopico();
        }
    }
}