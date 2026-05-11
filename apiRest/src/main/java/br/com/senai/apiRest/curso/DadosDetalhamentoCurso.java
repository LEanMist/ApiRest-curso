package br.com.senai.apiRest.curso;

public record DadosDetalhamentoCurso(
        Long id,
        String nome,
        Curso.Periodo periodo,
        Boolean ativo
) {
}
