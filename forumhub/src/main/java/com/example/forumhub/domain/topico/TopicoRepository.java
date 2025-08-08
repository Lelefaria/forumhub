package com.example.forumhub.domain.topico;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface TopicoRepository extends JpaRepository<com.forumhub.domain.topico.Topico, Long> {

    Optional<com.forumhub.domain.topico.Topico> findByTituloAndMensagem(String titulo, String mensagem);
}