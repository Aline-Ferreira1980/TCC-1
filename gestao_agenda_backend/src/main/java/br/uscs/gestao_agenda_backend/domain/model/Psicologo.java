package br.uscs.gestao_agenda_backend.domain.model;

import lombok.*;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
public class Psicologo extends User{

    private String especialidade;
    private String turno;
    private Date anoRef;
    private String semestreEmCurso;
    private String cursaPsico;
    private String orientador;


    @Builder
    public Psicologo(String nomeSocial, String email, Date dataNascimento,String genero, String estadoCivil,
                     String etniaRacial,String senha, String especialidade, String turno, Date anoRef, String semestreEmCurso, String cursaPsico, String orientador,UserRole role){
        super(nomeSocial, email, dataNascimento,genero,estadoCivil,etniaRacial,senha,role);
        this.turno = turno;
        this.anoRef= anoRef;
        this.semestreEmCurso= semestreEmCurso;
        this.cursaPsico=cursaPsico;
        this.orientador = orientador;
        this.especialidade = especialidade;
    }

    @OneToMany(mappedBy = "psicologo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<HorarioTrabalho> horariosTrabalho;



}
