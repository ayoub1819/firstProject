package com.example.projectge.service;

import com.example.projectge.models.Ressource;
import java.util.*;

public interface RessourcessService {

    List<Ressource> findAll();

    Ressource findById(Long id);

}
