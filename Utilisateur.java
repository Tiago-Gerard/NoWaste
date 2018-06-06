package com.example;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
"idUtilisateur",
"nom",
"prenom",
"numero",
"email"
})
public class Utilisateur {

@JsonProperty("idUtilisateur")
private String idUtilisateur;
@JsonProperty("nom")
private String nom;
@JsonProperty("prenom")
private String prenom;
@JsonProperty("numero")
private String numero;
@JsonProperty("email")
private String email;
@JsonIgnore
private Map<String, Object> additionalProperties = new HashMap<String, Object>();

@JsonProperty("idUtilisateur")
public String getIdUtilisateur() {
return idUtilisateur;
}

@JsonProperty("idUtilisateur")
public void setIdUtilisateur(String idUtilisateur) {
this.idUtilisateur = idUtilisateur;
}

@JsonProperty("nom")
public String getNom() {
return nom;
}

@JsonProperty("nom")
public void setNom(String nom) {
this.nom = nom;
}

@JsonProperty("prenom")
public String getPrenom() {
return prenom;
}

@JsonProperty("prenom")
public void setPrenom(String prenom) {
this.prenom = prenom;
}

@JsonProperty("numero")
public String getNumero() {
return numero;
}

@JsonProperty("numero")
public void setNumero(String numero) {
this.numero = numero;
}

@JsonProperty("email")
public String getEmail() {
return email;
}

@JsonProperty("email")
public void setEmail(String email) {
this.email = email;
}

@JsonAnyGetter
public Map<String, Object> getAdditionalProperties() {
return this.additionalProperties;
}

@JsonAnySetter
public void setAdditionalProperty(String name, Object value) {
this.additionalProperties.put(name, value);
}

}