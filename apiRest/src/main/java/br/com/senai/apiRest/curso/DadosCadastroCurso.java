package br.com.senai.apiRest.curso;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record DadosCadastroCurso(
        @NotBlank
        @Size(min = 3, max = 100)
        String nome,

        @NotNull
        Curso.Periodo periodo
) {
}
