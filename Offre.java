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
"id",
"lien",
"description",
"date",
"idType",
"distance",
"contact"
})
public class Offre {

@JsonProperty("id")
private String id;
@JsonProperty("lien")
private String lien;
@JsonProperty("description")
private String description;
@JsonProperty("date")
private String date;
@JsonProperty("idType")
private String idType;
@JsonProperty("distance")
private String distance;
@JsonProperty("contact")
private String contact;
@JsonIgnore
private Map<String, Object> additionalProperties = new HashMap<String, Object>();

@JsonProperty("id")
public String getId() {
return id;
}

@JsonProperty("id")
public void setId(String id) {
this.id = id;
}

@JsonProperty("lien")
public String getLien() {
return lien;
}

@JsonProperty("lien")
public void setLien(String lien) {
this.lien = lien;
}

@JsonProperty("description")
public String getDescription() {
return description;
}

@JsonProperty("description")
public void setDescription(String description) {
this.description = description;
}

@JsonProperty("date")
public String getDate() {
return date;
}

@JsonProperty("date")
public void setDate(String date) {
this.date = date;
}

@JsonProperty("idType")
public String getIdType() {
return idType;
}

@JsonProperty("idType")
public void setIdType(String idType) {
this.idType = idType;
}

@JsonProperty("distance")
public String getDistance() {
return distance;
}

@JsonProperty("distance")
public void setDistance(String distance) {
this.distance = distance;
}

@JsonProperty("contact")
public String getContact() {
return contact;
}

@JsonProperty("contact")
public void setContact(String contact) {
this.contact = contact;
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
Use this tool offline: Maven plugin Gradle plugin Ant task CLI Java API
© 2012-2016 Joe Littlejohn Bugs?

