package com.example.projectge.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppelOffre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToMany
    private List<Demande_departement> demande_departements;
    private String datePublication;
    @OneToMany(mappedBy = "appelOffre")
    private List<Offre> offres;
    private boolean isAffected;
}
