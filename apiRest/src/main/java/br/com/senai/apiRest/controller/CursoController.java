package br.com.senai.apiRest.controller;

import br.com.senai.apiRest.curso.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.data.domain.Pageable;
import java.util.Arrays;
import java.util.List;


@RestController
@RequestMapping("cursos")
public class CursoController {

    @Autowired
    private CursoRepository repository;

    @GetMapping
    public ResponseEntity <Page<DadosListagemCurso>> listarCurso(Pageable paginacao){
        var listar = repository.findAllByAtivoTrue(paginacao)
                .map(DadosListagemCurso::new);
        return ResponseEntity.ok(listar);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DadosDetalhamentoCurso> detalhar(@PathVariable Long id) {
        var curso = repository.findByIdAndAtivoTrue(id)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Curso não encontrado"
                        )
                );

        return ResponseEntity.ok(new DadosDetalhamentoCurso(curso));
    }

    @PostMapping
    @Transactional
    public ResponseEntity<DadosDetalhamentoCurso> cadastrarCurso(@RequestBody @Valid DadosCadastroCurso dados) {
        if (repository.existsByNome(dados.nome())) {

            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Já existe um curso com esse nome no sistema"
            );
        }
        Curso curso = new Curso(dados);

        repository.save(curso);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new DadosDetalhamentoCurso(curso));
    }

    @PutMapping
    @Transactional
    public ResponseEntity<DadosDetalhamentoCurso> atualizarCurso(@RequestBody @Valid DadosAtualizarCurso dados) {
        var curso = repository.findByIdAndAtivoTrue(dados.id())
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Curso não encontrado"
                        )
                );

        if (dados.nome() != null
                && !dados.nome().isBlank()
                && repository.existsByNome(dados.nome())
                && !curso.getNome().equalsIgnoreCase(dados.nome())) {

            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Já existe um curso com esse nome no sistema"
            );
        }
        curso.atualizarCurso(dados);

        return ResponseEntity.ok(new DadosDetalhamentoCurso(curso));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> excluirCurso(@PathVariable Long id) {
        var curso = repository.findByIdAndAtivoTrue(id)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Curso não encontrado"
                        )
                );
        curso.excluirCurso();

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/periodos")
    public ResponseEntity<List<Curso.Periodo>> listarPeriodos() {

        List<Curso.Periodo> periodos =
                Arrays.asList(Curso.Periodo.values());

        return ResponseEntity.ok(periodos);
    }
}
