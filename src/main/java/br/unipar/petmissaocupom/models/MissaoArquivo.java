package br.unipar.petmissaocupom.models;

import java.util.Date;
import java.util.UUID;

public class MissaoArquivo extends Missao{

    private String arquivoUrl;

    public MissaoArquivo() {
    }

    public MissaoArquivo(UUID id, String descricao, boolean concluido, Date dataGerada, String userId, String arquivoUrl) {
        super(id, descricao, concluido, dataGerada, userId);
        this.arquivoUrl = arquivoUrl;
    }

    public String getArquivoUrl() {
        return arquivoUrl;
    }

    public void setArquivoUrl(String arquivoUrl) {
        this.arquivoUrl = arquivoUrl;
    }

    @Override
    public void executarMissao() {
        if (arquivoUrl != null && !arquivoUrl.isEmpty()) {
            setConcluido(true);
            System.out.println("Arquivo enviado com sucesso. Missão completada!");
        } else {
            System.out.println("Nenhum arquivo foi enviado.");
        }
    }

}
