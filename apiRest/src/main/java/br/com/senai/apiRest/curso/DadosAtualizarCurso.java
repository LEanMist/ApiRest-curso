package br.com.senai.apiRest.curso;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;

public record DadosAtualizarCurso(
        Long id,

        @Size(min = 3,max = 60)
        String nome,

        Curso.Periodo periodo
) {
}
