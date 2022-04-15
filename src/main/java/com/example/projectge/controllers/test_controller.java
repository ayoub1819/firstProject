package com.example.projectge.controllers;


import com.example.projectge.DAO.*;
import com.example.projectge.DAO.FournisseurRepository;
import com.example.projectge.DAO.UserRepository;
import com.example.projectge.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Controller
public class test_controller {

    private RessourceDAO rd=new RessourceDAO();

    @Autowired
    private UserRepository repo;

    @Autowired
    private FournisseurRepository Frepo;

    @Autowired
    private RessourceRepository ressourceRepository;

    @Autowired
    private AffectationRepository affec;

    @Autowired
    private Membre_departementRepository membre;

    @Autowired
    private DepartementRepository dep;

    @Autowired
    private OrdinateurRepository ordinateurRepository;

    @Autowired
    private ImprimentRepository imprimentRepository;

    @GetMapping(path="/home")
    public String home(){
        return"/auth-login-basic";
    }

    @GetMapping(path="/registre")
    public String getregistreform(Model module){
        module.addAttribute("fournisseur",new Fournisseur());
        return "/auth-register-basic";}


    /*
    public String processRegistre(Fournisseur fornisseur){

        Frepo.save(fornisseur);
        return "/auth-login-basic";
    }

     */

    @RequestMapping(value="/process_register", method = RequestMethod.POST)
    public String ProcessRegistre (@RequestParam("register-username") String username, @RequestParam("register-password") String password
           , @RequestParam("register-societe") String societe, @RequestParam("register-email") String email,
            @RequestParam("register-gerant") String gerant, @RequestParam("register-cin") String cin,
            @RequestParam("register-adresse") String adresse, @RequestParam("register-tele") Long tele) {

/*
        BCryptPasswordEncoder encoder =new BCryptPasswordEncoder();
        String encoderpassword = encoder.encode(password);
             User user=new User(username,email,encoderpassword);
             user.setRoles(ERole.ROLE_FOURNISSEUR);
             repo.save(user);
             Frepo.save(new Fournisseur(cin,gerant,adresse,user,societe,tele));
*/


        return "/auth-login-basic";
    }

    @GetMapping("/admin")
    public String viewadmin(){

        return "admin_page";
    }



    @GetMapping("/ajouteAffectation")
    public String AjouteAffectation(Model model){
        List<Ressource> ressources=  ressourceRepository.findByAffecte(false);
        model.addAttribute("ressources",ressources);
        for (Ressource resso: ressources) {
            System.out.println(resso.getId());
        }

      List<Membre_departement> membres =  membre.findAll();
        model.addAttribute("membres",membres);
        for (Ressource resso: ressources) {
            //System.out.println(resso.getCIN());
        }

        List<Departement> departements=  dep.findAll();
        model.addAttribute("departements",departements);
        for (Ressource resso: ressources) {
            //System.out.println(resso.getCIN());
        }

        return "/index";
    }

    @RequestMapping(value = {"/ajouterAffectation"},method = RequestMethod.POST)
    public String ajouterAffectation(@RequestParam(value = "id_R",required = false) Long id_R,
                                     @RequestParam(value = "id_M",required = false) Long id_M,
                                     @RequestParam(value = "id_D",required = false) Long id_D){

        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        String strDate = dateFormat.format(date);


            Ressource r= ressourceRepository.findById(id_R).get();
            r.setAffecte(true);
            ressourceRepository.save(r);

        if(id_M == null){

            affec.save(new Affectation(id_D, id_R,strDate));
        }else {
            affec.save(new Affectation(id_R, strDate, id_M));

        }



            return "/index";
    }


    ////////////

    @GetMapping({"/ajouteRessource"})
    public String home(Model model){
        List<Fournisseur> fournisseur=  Frepo.findAll();
        model.addAttribute("fournisseurs",fournisseur);
        return "/ajoute_ressource";
    }

    ////////



    /////////////

    @RequestMapping(value = {"/ajouterRessourceP"},method = RequestMethod.POST)
    public String ajouterRessource(
            @RequestParam(value = "CIN",required = false) String CIN
            ,@RequestParam(value = "type",required = false) String type,
            @RequestParam(value = "dliv",required = false) String dliv,
            @RequestParam(value = "garantie",required = false) String garantie,
            @RequestParam(value = "marque",required = false) String marque,
            @RequestParam(value = "ecran",required = false) String ecran,
            @RequestParam(value = "ram",required = false) Long ram,
            @RequestParam(value = "disque-dur",required = false) Long disque_dur,
            @RequestParam(value = "cpu",required = false) String cpu,
            @RequestParam(value = "marqueI",required = false) String marqueI,
            @RequestParam(value = "resolution",required = false) Integer resolution,
            @RequestParam(value = "vitesse",required = false) Long vitesse
    ){

        if(type.equals("Ordinateur")){

            Ordinateur ordinateur=new Ordinateur(dliv,garantie,CIN,cpu,disque_dur,ecran,marque,ram);

            ressourceRepository.save(ordinateur);
        }else if(type.equals("Imprimente")) {

            Imprimente imprimente=new Imprimente(dliv,garantie,CIN,marqueI,resolution,vitesse);

            ressourceRepository.save(imprimente);

        }
        return "redirect:/ajouteRessource";
    }

    @GetMapping(path="/gestionRessource")
    public String gestionDesRessources(Model model,@RequestParam(value = "id",required = false) Long id){
        List <Ressource> ressources= ressourceRepository.findAll();
        List<Pair<Ressource, String>> types=rd.findRessourceType(ressourceRepository);
        //model.addAttribute("ressources",ressources);
        model.addAttribute("types",types);
        // resourcesRepository.deleteById(id);
        return "/gestionDesRessource";
    }

    @RequestMapping("/supprimerRessource")
    public String supprimerRessource(@RequestParam(value = "stockId",required = false) Long id){
        System.out.println("hiii");
        ressourceRepository.deleteById(id);
        return "redirect:/gestionRessource";
    }

    @GetMapping("/modifierRessource")
    public String MR(Model model,@RequestParam(value = "id",required = false) Long id,@RequestParam(value = "type",required = false) String type){
        List<Fournisseur> fournisseur= Frepo.findAll();
        model.addAttribute("id",id);
        model.addAttribute("typeM",type);
        model.addAttribute("fournisseurs",fournisseur);
        Ressource ressource= ressourceRepository.findById(id).get();
        model.addAttribute("rs",ressource);
        if(type.equals("Imprimente")){
            Imprimente imprimente= (Imprimente) imprimentRepository.findById(id).get();
            System.out.println(imprimente.getVitesse());
            model.addAttribute("ImprimenteCase",imprimente);
        }
        else if(type.equals("Ordinateur")){
            Ordinateur ordinateur=(Ordinateur) ordinateurRepository.findById(id).get();
            model.addAttribute("OrdinateurCase",ordinateur);
        }
        return "/modifierRessource";
    }

    @RequestMapping("/modifierRessourceCase")
    public String modifierRessourceCase(
            @RequestParam(value = "CIN",required = false) String CIN
            ,@RequestParam(value = "type",required = false) String type,
            @RequestParam(value = "dliv",required = false) String dliv,
            @RequestParam(value = "garantie",required = false) String garantie,
            @RequestParam(value = "marque",required = false) String marque,
            @RequestParam(value = "ecran",required = false) String ecran,
            @RequestParam(value = "ram",required = false) Long ram,
            @RequestParam(value = "disque-dur",required = false) Long disque_dur,
            @RequestParam(value = "cpu",required = false) String cpu,
            @RequestParam(value = "marqueI",required = false) String marqueI,
            @RequestParam(value = "resolution",required = false) Integer resolution,
            @RequestParam(value = "vitesse",required = false) Long vitesse,
            @RequestParam(value = "id",required = false) Long id

    ){
        System.out.println(type);
        if(type.equals("Ordinateur")){

            Ordinateur ordinateur= ordinateurRepository.findById(id).get();
            ordinateur.setCPU(cpu);
            ordinateur.setDisque_dur(disque_dur);
            ordinateur.setDate_liv(dliv);
            ordinateur.setGarantie(garantie);
            ordinateur.setEcran(ecran);
            ordinateur.setRAM(ram);
            ordinateur.setMarque(marque);
            ordinateur.setId_fournisseur(CIN);
            ordinateurRepository.save(ordinateur);
            ressourceRepository.save(ordinateur);
        }else if(type.equals("Imprimente")) {

            Imprimente imprimente= imprimentRepository.findById(id).get();
            imprimente.setVitesse(vitesse);
            imprimente.setMarque(marqueI);
            imprimente.setDate_liv(dliv);
            imprimente.setGarantie(garantie);
            imprimente.setId_fournisseur(CIN);
            imprimente.setResolution(resolution);
            ressourceRepository.save(imprimente);

        }
        return "redirect:/gestionRessource";
    }

}
