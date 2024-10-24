package br.unipar.petmissaocupom.models;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

import java.util.Date;
import java.util.UUID;

@Entity
@DiscriminatorValue("TEMPO")
public class MissaoTempo extends Missao {

    private long tempoLimite;

    public MissaoTempo() {
    }

    public MissaoTempo(UUID id, String descricao, boolean concluido,
                       Date dataGerada, String userId, long tempoLimite) {
        super(id, descricao, concluido, dataGerada, userId);
        this.tempoLimite = tempoLimite;
    }

    public long getTempoLimite() {
        return tempoLimite;
    }

    public void setTempoLimite(long tempoLimite) {
        this.tempoLimite = tempoLimite;
    }

    @Override
    public void executarMissao() {
        Date dataLimite = new Date(getDataGerada().getTime() + tempoLimite);
        Date agora = new Date();

        if (agora.before(dataLimite)) {
            setConcluido(true);
            System.out.println("Missão de tempo completada dentro do prazo!");
        } else {
            System.out.println("Prazo da missão de tempo expirou.");
        }
    }

}
