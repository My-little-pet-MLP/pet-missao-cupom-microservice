package br.unipar.petmissaocupom.models;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

import java.util.Date;
import java.util.UUID;

@Entity
@DiscriminatorValue("ARQUIVO")
public class MissaoArquivo extends Missao {

    private String arquivoUrl;

    public MissaoArquivo() {
    }

    public MissaoArquivo(UUID id, String descricao, Date dataGerada,
                         String userId, String arquivoUrl) {
        super(id, descricao, false,dataGerada, userId);
        this.arquivoUrl = arquivoUrl;
    }

    public String getArquivoUrl() {
        return arquivoUrl;
    }

    public void setArquivoUrl(String arquivoUrl) {
        this.arquivoUrl = arquivoUrl;
    }

    @Override
    public void concluir() {
        if (this.arquivoUrl != null && !this.arquivoUrl.isEmpty()) {
            this.setConcluido(true);
        } else {
            throw new IllegalStateException("Arquivo necessário para concluir a missão.");
        }
    }



}