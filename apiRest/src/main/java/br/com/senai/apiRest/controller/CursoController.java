package br.com.senai.apiRest.controller;

import br.com.senai.apiRest.curso.*;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@Tag(
        name = "Cursos",
        description = "Gerenciamento dos cursos"
)
@OpenAPIDefinition(tags = {
        @Tag(name = "Cadastrar Curso", description = "Cadastrar novo curso"),
        @Tag(name = "Listar Cursos", description = "Listar cursos ativos"),
        @Tag(name = "Buscar Curso", description = "Buscar curso por ID"),
        @Tag(name = "Atualizar Curso", description = "Atualizar curso"),
        @Tag(name = "Excluir Curso", description = "Exclusão lógica"),
        @Tag(name = "Períodos", description = "Listar períodos")
})
public class CursoController {

    @Autowired
    private CursoRepository repository;

    @GetMapping
    @Operation(summary = "Listar cursos ativos")
    @Tag(name = "Listar Cursos")
    @ApiResponses(value = {
        @ApiResponse(
                responseCode = "200",
                description = "Lista de cursos retornada com sucesso",
                content = {
                        @Content(
                                mediaType = "application/json",
                                schema = @Schema(
                                        implementation = DadosDetalhamentoCurso.class
                                )
                        )
                }
        )
})
    public ResponseEntity <Page<DadosListagemCurso>> listarCurso(Pageable paginacao){
        var listar = repository.findAllByAtivoTrue(paginacao)
                .map(DadosListagemCurso::new);
        return ResponseEntity.ok(listar);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar curso por ID")
    @Tag(name = "Buscar Curso")
    @ApiResponses(value = {
            @ApiResponse(
                responseCode = "200",
                description = "Curso encontrado",
                content = {
                        @Content(
                                mediaType = "application/json",
                                schema = @Schema(
                                        implementation = DadosDetalhamentoCurso.class
                                )
                        )
                }
        ),
        @ApiResponse(
                responseCode = "404",
                description = "Curso não encontrado",
                content = @Content
        )
    })
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
    @Operation(summary = "Cadastrar novo curso")
    @Tag(name = "Cadastrar Curso")
    @ApiResponses(value = {
            @ApiResponse(
                responseCode = "201",
                description = "Curso cadastrado com sucesso",
                content = {
                        @Content(
                                mediaType = "application/json",
                                schema = @Schema(
                                        implementation = DadosDetalhamentoCurso.class
                                )
                        )
                }
        ),
        @ApiResponse(
                responseCode = "409",
                description = "Já existe um curso com esse nome no sistema",
                content = @Content
        ),
        @ApiResponse(
                responseCode = "400",
                description = "Dados inválidos",
                content = @Content
        )
    })
    public ResponseEntity<DadosDetalhamentoCurso> cadastrarCurso(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                content = @Content(
                        mediaType = "application/json",

                        schema = @Schema(
                                implementation = DadosCadastroCurso.class
                        ),

                        examples = @ExampleObject(
                                value = """
                                {
                                  "nome": "TESTE",
                                  "periodo": "NOTURNO"
                                }
                                """
                        )
                )
            )
            @RequestBody @Valid DadosCadastroCurso dados) {
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
    @Operation(summary = "Atualizar curso")
    @Tag(name = "Atualizar Curso")
    @ApiResponses(value = {
            @ApiResponse(
                responseCode = "200",
                description = "Curso atualizado com sucesso",
                content = {
                        @Content(
                                mediaType = "application/json",
                                schema = @Schema(
                                        implementation = DadosDetalhamentoCurso.class
                                )
                        )
                }
        ),
        @ApiResponse(
                responseCode = "404",
                description = "Curso não encontrado",
                content = @Content
        ),
        @ApiResponse(
                responseCode = "409",
                description = "Já existe um curso com esse nome no sistema",
                content = @Content
        )
    })
    public ResponseEntity<DadosDetalhamentoCurso> atualizarCurso(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                content = @Content(
                        mediaType = "application/json",

                        schema = @Schema(
                                implementation = DadosAtualizarCurso.class
                        ),

                        examples = @ExampleObject(
                                value = """
                                {
                                  "id": 1,
                                  "nome": "TESTE",
                                  "periodo": "INTEGRAL"
                                }
                                """
                        )
                )
            )
            @RequestBody @Valid DadosAtualizarCurso dados) {
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
    @Operation(summary = "Excluir curso")
    @Tag(name = "Excluir Curso")
    @ApiResponses(value = {
        @ApiResponse(
                responseCode = "204",
                description = "Curso excluído com sucesso",
                content = @Content
        ),
        @ApiResponse(
                responseCode = "404",
                description = "Curso não encontrado",
                content = @Content
        )
    })
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
    @Operation(summary = "Listar períodos disponíveis")
    @Tag(name = "Períodos")
    @ApiResponse(
            responseCode = "200",
            description = "Períodos listados com sucesso",
            content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(
                            value = """
                            [
                              "MATUTINO",
                              "VESPERTINO",
                              "NOTURNO",
                              "INTEGRAL"
                            ]
                            """
                    )
            )
    )
    public ResponseEntity<List<Curso.Periodo>> listarPeriodos() {

        List<Curso.Periodo> periodos =
                Arrays.asList(Curso.Periodo.values());

        return ResponseEntity.ok(periodos);
    }
}
