package br.unipar.petmissaocupom.models;

import jakarta.persistence.*;

import java.util.Date;
import java.util.UUID;

@Entity(name = "MISSOES")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_missao", discriminatorType = DiscriminatorType.STRING)
public abstract class Missao {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String descricao;
    private boolean concluido;
    private Date dataGerada;
    private String userId;

    public Missao() {
    }

    public Missao(UUID id, String descricao, boolean concluido, Date dataGerada, String userId) {
        this.id = id;
        this.descricao = descricao;
        this.concluido = concluido;
        this.dataGerada = dataGerada;
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

    public abstract void executarMissao();

}
