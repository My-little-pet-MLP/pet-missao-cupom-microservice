package br.unipar.petmissaocupom.models;

import br.unipar.petmissaocupom.enuns.TipoMissao;
import jakarta.persistence.*;

import java.util.Date;
import java.util.UUID;

@Entity(name = "MISSOES")
public class Missao {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String descricao;
    private boolean concluido;
    private Date dataGerada;
    private String userId;

    private TipoMissao tipo;
    private Long tempoLimite;
    private Boolean temporizadorAtivado;
    private String arquivoUrl;

    public Missao() {
    }

    public Missao(UUID id, String descricao, boolean concluido, Date dataGerada,
                  String userId, TipoMissao tipo, String arquivoUrl) {
        this.id = id;
        this.descricao = descricao;
        this.concluido = concluido;
        this.dataGerada = dataGerada;
        this.userId = userId;
        this.tipo = tipo;
        this.arquivoUrl = arquivoUrl;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public boolean isConcluido() {
        return concluido;
    }

    public void setConcluido(boolean concluido) {
        this.concluido = concluido;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Date getDataGerada() {
        return dataGerada;
    }

    public void setDataGerada(Date dataGerada) {
        this.dataGerada = dataGerada;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public TipoMissao getTipo() {
        return tipo;
    }

    public void setTipo(TipoMissao tipo) {
        this.tipo = tipo;
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

    public void concluir() {
        if (tipo == TipoMissao.TEMPO && !temporizadorAtivado) {
            throw new IllegalStateException("Temporizador precisa ser ativado para concluir a missão de tempo.");
        }
        if (tipo == TipoMissao.ARQUIVO && (arquivoUrl == null || arquivoUrl.isEmpty())) {
            throw new IllegalStateException("Arquivo necessário para concluir a missão de arquivo.");
        }
        this.concluido = true;
    }

}
