package br.com.senai.apiRest.curso;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface CursoRepository extends JpaRepository<Curso, Long> {
    boolean existsByNome(String nome);

    Page<Curso> findAllByAtivoTrue(Pageable pageable);
    Optional<Curso> findByIdAndAtivoTrue(Long id);

}
