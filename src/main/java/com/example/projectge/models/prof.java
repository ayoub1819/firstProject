package com.example.projectge.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("prof")
@Data @AllArgsConstructor @NoArgsConstructor
public class prof extends Membre_departement{

    private String laboratoire;
/*
    public prof() {

    }


    public prof(Long CNE, User compte, Departement departement, String nom, String prenom, String laboratoire) {
        super(CNE, compte, departement, nom, prenom);
        this.laboratoire = laboratoire;
    }

    public String getLaboratoire() {
        return laboratoire;
    }

    public void setLaboratoire(String laboratoire) {
        this.laboratoire = laboratoire;
    }

 */
}
