package com.example.projectge.DAO;

import com.example.projectge.models.Imprimente;
import com.example.projectge.models.Ordinateur;
import com.example.projectge.models.Ressource;
import org.springframework.data.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class RessourceDAO {


    public List<Pair<Ressource, String>> findRessourceType(RessourceRepository resourcesRepository) {
        List<Pair<Ressource, String>> types = new ArrayList<>();

        List<Ressource> ressource_r = resourcesRepository.findAll();
        for (Ressource a : ressource_r) {
            if (a instanceof Imprimente) {
                Pair p = Pair.of(a, "Imprimente");
                types.add(p);
            } else if (a instanceof Ordinateur) {

                Pair p = Pair.of(a, "Ordinateur");
                types.add(p);
            }

        }
        return types;
    }
}