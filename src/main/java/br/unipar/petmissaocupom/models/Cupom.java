package br.unipar.petmissaocupom.models;

import br.unipar.petmissaocupom.enuns.TipoMissao;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDate;
import java.util.UUID;

@Entity(name = "CUPONS")
public class Cupom {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String codigo;
    private double porcentagem;
    private LocalDate dataGerado;
    private LocalDate dataVencimento;
    private boolean utilizado;
    private String userId;

    public Cupom() {
    }

    public Cupom(UUID id, String codigo, double porcentagem,
                 LocalDate dataGerado, LocalDate dataVencimento, boolean utilizado, String userId) {
        this.id = id;
        this.codigo = codigo;
        this.porcentagem = porcentagem;
        this.dataGerado = dataGerado;
        this.dataVencimento = dataVencimento;
        this.utilizado = utilizado;
        this.userId = userId;
    }

    public LocalDate getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(LocalDate dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public LocalDate getDataGerado() {
        return dataGerado;
    }

    public void setDataGerado(LocalDate dataGerado) {
        this.dataGerado = dataGerado;
    }

    public boolean isUtilizado() {
        return utilizado;
    }

    public void setUtilizado(boolean utilizado) {
        this.utilizado = utilizado;
    }

    public double getPorcentagem() {
        return porcentagem;
    }

    public void setPorcentagem(double porcentagem) {
        this.porcentagem = porcentagem;
    }
}
