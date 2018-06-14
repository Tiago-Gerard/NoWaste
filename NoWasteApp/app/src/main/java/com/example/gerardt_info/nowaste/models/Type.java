/*
 * Projet  : No Waste
 * Auteur  : Tiago Gerard
 * Version : 1.0
 * Fichier : Type.java
 * */
package com.example.gerardt_info.nowaste.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Type {

    @SerializedName("idType")
    @Expose
    private String idType;
    @SerializedName("nom")
    @Expose
    private String nom;

    public String getIdType() {
        return idType;
    }

    public void setIdType(String idType) {
        this.idType = idType;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }


}
