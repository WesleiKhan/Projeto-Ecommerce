package com.example.Ecommerce.user.entity;

import java.time.LocalDate;

import com.example.Ecommerce.vendedor.entity.Vendedor;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private String id;

    @Column(name = "primeiro_nome")
    private String primeiro_nome;

    @Column(name = "sobrenome")
    private String sobrenome;

    @Column(name = "username")
    private String username;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "data_nascimento")
    private LocalDate data_nascimento;

    @Column(name = "tipo_user")
    @Enumerated(EnumType.STRING)
    private TypeUserEnum tipo_user = TypeUserEnum.USER;

    @Column(name = "password")
    private String password;

    @OneToOne(mappedBy = "nome", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Vendedor cadastro_vendedor;
    

    public User() {
    }


    public User(String primeiro_nome, String sobrenome, String username, String email, LocalDate data_nascimento,
            String password) {
        this.primeiro_nome = primeiro_nome;
        this.sobrenome = sobrenome;
        this.username = username;
        this.email = email;
        this.data_nascimento = data_nascimento;
        this.password = password;
    }


    public String getId() {
        return id;
    }

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

    public TypeUserEnum getTipo_user() {
        return tipo_user;
    }

    public void setTipo_user(TypeUserEnum tipo_user) {
        this.tipo_user = tipo_user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
     
}
