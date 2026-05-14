package br.com.senai.apiRest.curso;

public record DadosListagemCurso(
        Long id,
        String nome,
        Curso.Periodo periodo
) {
    public DadosListagemCurso(Curso curso){
        this(
                curso.getId(),
                curso.getNome(),
                curso.getPeriodo());
    }
}
