package com.example.gerardt_info.nowaste.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Utilisateur implements Serializable {

    @SerializedName("idUtilisateur")
    @Expose
    private String idUtilisateur;
    @SerializedName("nom")
    @Expose
    private String nom;
    @SerializedName("prenom")
    @Expose
    private String prenom;
    @SerializedName("numero")
    @Expose
    private String numero;
    @SerializedName("email")
    @Expose
    private String email;

    public String getIdUtilisateur() {
        return idUtilisateur;
    }

    public void setIdUtilisateur(String idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
