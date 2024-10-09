package br.unipar.petmissaocupom.models;

import br.unipar.petmissaocupom.enuns.TipoMissao;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity(name = "MISSOES")
public class Missao {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String descricao;
    private boolean concluido;
    private LocalDate dataGerada;
    private TipoMissao tipo;
    private String userId;

    public Missao() {
    }

    public Missao(UUID id, String descricao, boolean concluido, LocalDate dataGerada, TipoMissao tipo, String userId) {
        this.id = id;
        this.descricao = descricao;
        this.concluido = concluido;
        this.dataGerada = dataGerada;
        this.tipo = tipo;
        this.userId = userId;
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

    public LocalDate getDataGerada() {
        return dataGerada;
    }

    public void setDataGerada(LocalDate dataGerada) {
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
}
