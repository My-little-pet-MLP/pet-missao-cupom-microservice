package br.unipar.petmissaocupom.models;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

import java.util.Date;
import java.util.UUID;

@Entity
@DiscriminatorValue("TEMPO")
public class MissaoTempo extends Missao {

    private long tempoLimite;
    private boolean temporizadorAtivado;

    public MissaoTempo() {
    }

    public MissaoTempo(UUID id, String descricao, Date dataGerada,
                       String userId, long tempoLimite, boolean temporizadorAtivado) {
        super(id, descricao, false, dataGerada, userId);
        this.tempoLimite = tempoLimite;
        this.temporizadorAtivado = temporizadorAtivado;
    }

    public long getTempoLimite() {
        return tempoLimite;
    }

    public void setTempoLimite(long tempoLimite) {
        this.tempoLimite = tempoLimite;
    }

    public boolean isTemporizadorAtivado() {
        return temporizadorAtivado;
    }

    public void setTemporizadorAtivado(boolean temporizadorAtivado) {
        this.temporizadorAtivado = temporizadorAtivado;
    }

    @Override
    public void concluir() {
        if (this.temporizadorAtivado) {
            this.setConcluido(true);
        } else {
            throw new IllegalStateException("Temporizador precisa ser ativado para concluir a missão.");
        }
    }
}
