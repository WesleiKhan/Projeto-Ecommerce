package com.example.Ecommerce.user.service;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;

public class UserEntryDTO {

    @NotBlank
    private String primeiro_nome;

    @NotBlank
    private String sobrenome;

    @NotBlank
    private String username;

    @NotBlank
    private String email;

    private LocalDate data_nascimento;

    @NotBlank
    private String password;

    public String getPrimeiro_nome() {
        return primeiro_nome;
    }

    public void setPrimeiro_nome(String primeiro_nome) {
        this.primeiro_nome = primeiro_nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getData_nascimento() {
        return data_nascimento;
    }

    public void setData_nascimento(LocalDate data_nascimento) {
        this.data_nascimento = data_nascimento;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
