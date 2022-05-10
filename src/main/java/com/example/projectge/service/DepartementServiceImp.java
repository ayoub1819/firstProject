package com.example.projectge.service;

import com.example.projectge.DAO.DepartementRepository;
import com.example.projectge.models.Departement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DepartementServiceImp implements DepartementService{
    @Autowired
    DepartementRepository departementRepository;
    @Override
    public Departement find(Long id) {
        return departementRepository.getById(id);
    }
}
