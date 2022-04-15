package com.example.projectge.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class Panne {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Membre_departement membre_departement;
    private String explication;
    private String dateApparition;
    @Enumerated(EnumType.STRING)
    private EFrequance frequence;
    @Enumerated(EnumType.STRING)
    private EOrdre ordre;
    private String etat;
    private String constat;
    @ManyToOne
    private Ressource ressource;

}
