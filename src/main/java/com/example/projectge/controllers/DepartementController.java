package com.example.projectge.controllers;

import com.example.projectge.DAO.DepartementRepository;
import com.example.projectge.DAO.Membre_departementRepository;
import com.example.projectge.models.Departement;
import com.example.projectge.models.Membre_departement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@Controller
public class DepartementController {
    @Autowired
    private DepartementRepository Deprepo;
    @Autowired
    private Membre_departementRepository mbrrepo;
    @GetMapping({"/ajouterDepartement"})
    public String vAjouterDep(){
        return "/ajouter_departement";
    }
    @RequestMapping(value={"/ajouterDepartement"},method= RequestMethod.POST)
    public String ajouterDepartement(@RequestParam(value = "IdDep") Long Id,
                                     @RequestParam(value = "nomDep") String nomDep
                                    ){
        Departement Dep=new Departement(Id,nomDep);
        Deprepo.save(Dep);

        return "redirect:/ajouterDepartement";
    }
    @GetMapping(path="/gestionDepartement")
    public String gestionDepartement(Model model){
        List<Departement> deps=Deprepo.findAll();
        model.addAttribute("depas",deps);
        return "/gestion_departement";
    }
    @GetMapping(path="/modifierDepartement")
    public String modifierDepV(Model model,@RequestParam(value = "id",required = false) Long id){
        model.addAttribute("id",id);
        return "/modifierDepartement";
    }
    @RequestMapping(path="/modifierDep")
    public String modifierDepartement(@RequestParam(value = "id",required = false) Long id,@RequestParam(value = "nomDepM",required = false) String nomDep)
    {
       Deprepo.update(nomDep,id);

        return "redirect:/gestionDepartement";
    }

    @RequestMapping(path="/supprimerDep")
    public String supprimerDep(@RequestParam(value = "Id",required = false)Long id){
        Deprepo.deleteById(id);
        return  "redirect:/gestionDepartement";
    }

    @GetMapping(path="/listerMembres")
    public String listerMembres(Model model){
        List<Membre_departement> mbrs=mbrrepo.findAll();
        model.addAttribute("mbrs",mbrs);
        return "/lister_membres";
    }
}
