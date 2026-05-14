package br.com.senai.apiRest.curso;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name="cursos")
@Entity(name="Curso")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of="id")
public class Curso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String nome;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Periodo periodo;

    @Column(nullable = false)
    private boolean ativo;

    public enum Periodo{
        MATUTINO,
        VESPERTINO,
        NOTURNO,
        INTEGRAL
    }

    public Curso(DadosCadastroCurso dados){
        this.nome = dados.nome();
        this.periodo = dados.periodo();
        this.ativo = true;
    }

    public void atualizarCurso(DadosAtualizarCurso dados){
        if(dados.nome() !=null && !dados.nome().isBlank())
            this.nome = dados.nome();
        if(dados.periodo() !=null)
            this.periodo = dados.periodo();
    }

    public void excluirCurso(){
        this.ativo = false;
    }
}
