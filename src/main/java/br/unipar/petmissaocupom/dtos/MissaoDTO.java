package br.unipar.petmissaocupom.dtos;


import br.unipar.petmissaocupom.enuns.TipoMissao;

public class MissaoDTO {

    private TipoMissao tipo;
    private String descricao;
    private String userId;
    private Long tempoLimite;
    private boolean temporizadorAtivado;
    private String arquivoUrl;

    public TipoMissao getTipo() {
        return tipo;
    }

    public void setTipo(TipoMissao tipo) {
        this.tipo = tipo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Long getTempoLimite() {
        return tempoLimite;
    }

    public void setTempoLimite(Long tempoLimite) {
        this.tempoLimite = tempoLimite;
    }

    public boolean isTemporizadorAtivado() {
        return temporizadorAtivado;
    }

    public void setTemporizadorAtivado(boolean temporizadorAtivado) {
        this.temporizadorAtivado = temporizadorAtivado;
    }

    public String getArquivoUrl() {
        return arquivoUrl;
    }

    public void setArquivoUrl(String arquivoUrl) {
        this.arquivoUrl = arquivoUrl;
    }

}
