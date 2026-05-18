package br.com.senai.apiRest.curso;

public record DadosListagemCurso(
        String nome,
        Curso.Periodo periodo
) {
    public DadosListagemCurso(Curso curso){
        this(
                curso.getNome(),
                curso.getPeriodo());
    }
}
