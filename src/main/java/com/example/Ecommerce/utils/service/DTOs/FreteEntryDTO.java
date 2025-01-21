package com.example.Ecommerce.utils.service.DTOs;

public class FreteEntryDTO {
    
    private String cep_origem;

    private double altura;
    
    private double largura;

    private double comprimento;

    private double peso;

    

    public FreteEntryDTO(String cep_origem, double altura, double largura, double comprimento, double peso) {
        this.cep_origem = cep_origem;
        this.altura = altura;
        this.largura = largura;
        this.comprimento = comprimento;
        this.peso = peso;
    }

    public String getCep_origem() {
        return cep_origem;
    }

    public void setCep_origem(String cep_origem) {
        this.cep_origem = cep_origem;
    }

    public double getAltura() {
        return altura;
    }

    public void setAltura(double altura) {
        this.altura = altura;
    }

    public double getLargura() {
        return largura;
    }

    public void setLargura(double largura) {
        this.largura = largura;
    }

    public double getComprimento() {
        return comprimento;
    }

    public void setComprimento(double comprimento) {
        this.comprimento = comprimento;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    
}
