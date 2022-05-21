package com.example.projectge.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Offre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private User user;
    private String descrption;
    private String montant;
    private Long idAppelOffre;
    private String etat;
    private boolean accepted;
    @ManyToOne
    private AppelOffre appelOffre;
}
