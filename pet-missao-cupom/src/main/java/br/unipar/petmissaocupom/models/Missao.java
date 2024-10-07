package br.unipar.petmissaocupom.models;

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
    private Date dataDoComeco;
    private String userId;

    @ManyToOne
    private Pet pet;

    public Missao() {
    }

    public Missao(UUID id, String descricao, boolean concluido,
                  Date dataDoComeco, Pet pet, String userId) {
        this.id = id;
        this.descricao = descricao;
        this.concluido = concluido;
        this.dataDoComeco = dataDoComeco;
        this.pet = pet;
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

    public Date getDataDoComeco() {
        return dataDoComeco;
    }

    public void setDataDoComeco(Date dataDoComeco) {
        this.dataDoComeco = dataDoComeco;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Pet getPet() {
        return pet;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }
}
