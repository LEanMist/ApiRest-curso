package br.com.senai.apiRest.curso;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record DadosAtualizarCurso(
        @NotNull
        Long id,

        @Size(min = 3,max = 100)
        String nome,

        Curso.Periodo periodo
) {
}
