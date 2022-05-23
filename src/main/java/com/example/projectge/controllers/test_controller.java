package com.example.projectge.controllers;


import com.example.projectge.DAO.*;
import com.example.projectge.DAO.FournisseurRepository;
import com.example.projectge.DAO.UserRepository;
import com.example.projectge.models.*;
import com.example.projectge.service.AccountServiceImp;
import com.example.projectge.service.AffectationServiceImp;

import com.example.projectge.service.MembreDepartService;
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
import java.util.*;

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

    @Autowired
    private AffectationServiceImp affectationServiceImp;

    @Autowired
    private MembreDepartService membreDepartService;

    @Autowired
    private DepartementRepository departementRepository;

    @Autowired
    private RessourceRepository re;

    @Autowired
    private AccountServiceImp accountServiceImp;


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


        List<Membre_departement> membres =  membre.findAll();
        model.addAttribute("membres",membres);


        List<Departement> departements=  dep.findAll();
        model.addAttribute("departements",departements);

        List<Imprimente> imprimenteslist2 = new ArrayList<>();
        List<Ordinateur> ordinateurlist2=new ArrayList<>();
        List<Imprimente> imprimenteslist=imprimentRepository.findAll();
        List<Ordinateur> ordinateurlist=ordinateurRepository.findAll();
        ressources.forEach(orr->{
            imprimenteslist.forEach(im ->{
                if(orr.getId()==im.getId()){
                    imprimenteslist2.add(im);
                }
            });

            ordinateurlist.forEach(o ->{
                if(orr.getId()==o.getId()){
                    ordinateurlist2.add(o);
                }
            });

              });

        model.addAttribute("impriment",imprimenteslist2);
        model.addAttribute("ordinateur",ordinateurlist2);


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


    @GetMapping(path="/gestionDesAffectation")
    public String gestionDesAffectation(Model model,@RequestParam(value = "id",required = false) Long id){

        List<Affectation> affect=affectationServiceImp.findAll();
        List<Departement> departements = departementRepository.findAll();
        List<Membre_departement> membres = membreDepartService.findAll();
        List<Ressource> ressource=re.findAll();

        Map<Long,String[]> posts = new HashMap<>();

        affect.forEach(affec -> {

            var post_lab = new String[4];
            Departement de;
            Membre_departement me;
            Ressource r;


            r=re.getById(affec.getRessource());



            if(affec.getDepartement()!= null){
                de=departementRepository.getById(affec.getDepartement());
                post_lab[0]=de.getNom_departement();}
            else{ post_lab[0]=""; }

            if(affec.getMemmbre() != null){
                me=membreDepartService.findById(affec.getMemmbre());
                post_lab[1]=me.getPrenom();}
            else{ post_lab[1]=""; }


            if (r instanceof Imprimente) {
                post_lab[2]="Imprimente";
                System.out.println("hii imp");
            }
            else if (r instanceof Ordinateur) {
                post_lab[2] = "Ordinateur";
                System.out.println("hehhh ordinat");
            }

            post_lab[3]=affec.getDate();

            posts.put(affec.getId(),post_lab);
                });



        model.addAttribute("aff",affect);
        model.addAttribute("posts",posts);



        return "/gestionDesAffectation";
    }

    @RequestMapping("/supprimerAffectation")
    public String supprimerAffectation(@RequestParam(value = "stockId",required = false) Long id){


        Affectation affec=affectationServiceImp.getById(id);
        Ressource r;

        r=re.getById(affec.getRessource());
        r.setAffecte(false);
        affectationServiceImp.delete(affec);

        return "redirect:/gestionDesAffectation";
    }

    @GetMapping("/modifieraffectation")
    public String modifieraffectation1(@RequestParam(value = "id",required = false) Long id,Model model){




        //Affectation affec=affectationServiceImp.getById(id);
        List<Affectation> affect=affectationServiceImp.findAll();
//////////////////////////////////

        Map<Long, String[]> posts = new HashMap<>();

        affect.forEach(affec -> {

                    if(affec.getId()==id) {

                        var post_lab = new String[7];
                        Departement de;
                        Membre_departement me;
                        Ressource r;


                        r = re.getById(affec.getRessource());

                        List<Imprimente> im=imprimentRepository.findAll();

                        im.forEach(imm ->{
                            if(imm.getId()==affec.getRessource()){
                                post_lab[2]="Imprimente "+imm.getMarque();
                                post_lab[4]= String.valueOf(affec.getRessource());
                                System.out.println("hiihh imp");
                            }
                        });

                        List<Ordinateur> or=ordinateurRepository.findAll();

                        or.forEach(orr->{
                            if(orr.getId()==affec.getRessource()){
                                post_lab[2]="Ordinateur "+orr.getMarque();
                                post_lab[4]= String.valueOf(affec.getRessource());
                                System.out.println("hiihh ord");
                            }
                        });

                        if (affec.getDepartement() != null) {
                            de = departementRepository.getById(affec.getDepartement());
                            post_lab[0] = de.getNom_departement();
                            post_lab[5]= String.valueOf(affec.getDepartement());
                        } else {
                            post_lab[0] = "";
                            post_lab[5]= String.valueOf(affec.getDepartement());
                        }

                        if (affec.getMemmbre() != null) {
                            me = membreDepartService.findById(affec.getMemmbre());
                            post_lab[1] = me.getPrenom();
                            post_lab[6]= String.valueOf(affec.getMemmbre());
                        } else {
                            post_lab[1] = "";
                            post_lab[6]= String.valueOf(affec.getMemmbre());
                        }

/*
                        if (r instanceof Imprimente) {
                            post_lab[2] = "Imprimente";
                            System.out.println("hi impri");

                        } else if (r instanceof Ordinateur) {

                            post_lab[2] = "Ordinateur";
                            System.out.println("hi ordina");
                        }
*/
                        post_lab[3] = affec.getDate();

                        posts.put(affec.getId(), post_lab);
                    }

                });

        ///////////////////////////////////////////////////////

        model.addAttribute("aff",affect);
        model.addAttribute("posts",posts);

        List<Ressource> ressources=  ressourceRepository.findByAffecte(false);
        List<Imprimente> imprimenteslist=imprimentRepository.findAll();
        List<Ordinateur> ordinateurlist=ordinateurRepository.findAll();
        List<Imprimente> imprimenteslist2 = new ArrayList<>();
        List<Ordinateur> ordinateurlist2=new ArrayList<>();

        ressources.forEach(orr->{
            imprimenteslist.forEach(im ->{
                if(orr.getId()==im.getId()){
                    imprimenteslist2.add(im);
                }
            });

            ordinateurlist.forEach(o ->{
                if(orr.getId()==o.getId()){
                    ordinateurlist2.add(o);
                }
            });

        });


        model.addAttribute("ressources",ressources);

        model.addAttribute("impriment",imprimenteslist2);
        model.addAttribute("ordinateur",ordinateurlist2);

        List<Membre_departement> membres =  membre.findAll();
        model.addAttribute("membres",membres);

        List<Departement> departements=  dep.findAll();
        model.addAttribute("departements",departements);

        model.addAttribute("id", id);
        System.out.println(posts.get(id)[2]);
        return "/modifierAffectation";

    }


    @RequestMapping(value = {"/modifieraffectation"},method = RequestMethod.POST)
    public String modifierAffectation(@RequestParam(value = "id_R",required = false) String id_R,
                                     @RequestParam(value = "id_M",required = false) String id_M,
                                     @RequestParam(value = "id_D",required = false) String id_D,
                                     @RequestParam(value = "affId",required = false) String id_a,
                                     @RequestParam(value = "resId",required = false) String id_or){

        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        String strDate = dateFormat.format(date);


        Ressource r= ressourceRepository.findById(Long.valueOf(Integer.valueOf(id_or))).get();
        r.setAffecte(false);
        ressourceRepository.save(r);

        Ressource rr= ressourceRepository.findById(Long.valueOf(Integer.valueOf(id_R))).get();
        rr.setAffecte(true);
        ressourceRepository.save(r);

        Affectation aff=affectationServiceImp.getById(Long.valueOf(Integer.valueOf(id_a)));



        if(Long.valueOf(id_M) == null){

            aff.setDepartement(Long.valueOf(id_D));
            aff.setRessource(Long.valueOf(id_R));
            affectationServiceImp.save(aff);

        }else {
            aff.setRessource(Long.valueOf(id_R));
            aff.setMemmbre(Long.valueOf(id_M));
            affectationServiceImp.save(aff);
        }



        return "redirect:/gestionDesAffectation";
    }

    @RequestMapping(value={"/deleteAcount"})
    public String supprimerCompte(@RequestParam(value = "id",required = false) Long id){


       Membre_departement m= membreDepartService.getById(id);
       User u=m.getCompte();
       List<Affectation> a=affectationServiceImp.findAffectationByMembre(id);

        a.forEach(orr->{
            Ressource r= ressourceRepository.findById(orr.getRessource()).get();
            r.setAffecte(false);
            ressourceRepository.save(r);
            });

        a.forEach(orr->{
            affectationServiceImp.delete(orr);
        });

        membreDepartService.delete(m);
        accountServiceImp.delete(u);





        return "redirect:/accountManagement";
    }

    @RequestMapping(value={"/editAcount"})
    public String modifierCompte(@RequestParam(value = "id",required = false) Long id,Model model){

        System.out.println("tetetetetetetetetetetet");
        Membre_departement m= membreDepartService.getById(id);
        User u=m.getCompte();

        model.addAttribute("compte",u);
        model.addAttribute("id", id);


        return "/modifierComptE";


    }

    @RequestMapping(value={"/edittAcountt"})
    public String modifierCompte1(@RequestParam(value = "IId",required = false) Long id,
                                  @RequestParam(value = "userna",required = false) String username,
                                  @RequestParam(value = "emaill",required = false) String emaill,
                                  @RequestParam(value = "passw",required = false) String passw){


        Membre_departement m= membreDepartService.getById(id);
        User u=m.getCompte();


        u.setPassword(passw);
        u.setEmail(emaill);
        u.setUsername(username);
        accountServiceImp.saveUser(u);





        return "redirect:/accountManagement";
    }


}
