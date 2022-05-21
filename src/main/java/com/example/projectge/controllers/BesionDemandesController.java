package com.example.projectge.controllers;

import com.example.projectge.models.Besoin;
import com.example.projectge.models.Demande_departement;
import com.example.projectge.models.Departement;
import com.example.projectge.service.BesionsServiceImp;
import com.example.projectge.service.DemandeService;
import com.example.projectge.service.DepartementService;
import com.example.projectge.service.MembreDepartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class BesionDemandesController {
    @Autowired
    private BesionsServiceImp besionsServiceImp;
    @Autowired
    private MembreDepartService membreDepartService;
    @Autowired
    private DepartementService departementService;
    @Autowired
    private DemandeService demandeService;


    @GetMapping(path = "/besoin")
    public String sendBesoinForm(Model model,Principal principal){
        var membre = membreDepartService.findMembreByUserName(principal.getName());
        var isChef = membre.getCompte().getRole().stream().anyMatch(r -> r.getRoleName().equals("CHEF_DEP"));
        Besoin memBesoin = besionsServiceImp.getMemberBesoin(membre.getId());
        Besoin depBesoin = besionsServiceImp.getDepartementBesoin(membre.getDepartement());
        var depRessources = besionsServiceImp.test(depBesoin);
        var ressources = besionsServiceImp.test(memBesoin);
        if (isChef){
            model.addAttribute("depbesoin",depBesoin);
            model.addAttribute("depressources",depRessources);
        }
        model.addAttribute("besoin",memBesoin);
        model.addAttribute("ressources",ressources);
        return "/ofresEtBesoin/besoins";
    }
    @PostMapping(path = "/addBesoin")
    @ResponseBody
    public String addBesoin(@RequestBody Besoin besoin, Principal principal){
        var membre = membreDepartService.findMembreByUserName(principal.getName());
        var isChef = membre.getCompte().getRole().stream().anyMatch(r -> r.getRoleName().equals("CHEF_DEP"));
        besoin.setDeparetement(membre.getDepartement());
        if (isChef && besoin.getMembre() == null)  return besionsServiceImp.save(besoin).toString();
        besoin.setMembre(membre);
        return besionsServiceImp.save(besoin).toString();
    }
    @PostMapping(path = "/addManagedBesoin")
    @ResponseBody
    public String addManagedBesoin(@RequestBody Besoin besoin){
        return besionsServiceImp.save(besoin).toString();
    }

    @GetMapping(path = "/Chefbesoin")
    public String ChefBesoin(Model model,Principal principal){
        var membre = membreDepartService.findMembreByUserName(principal.getName());
        var members = membreDepartService.findByDep(membre.getDepartement());
        Map<String,String> postes = new HashMap<>();
        Map<String,Boolean> etat = new HashMap<>();
        members.forEach(mem -> {
            etat.put(mem.getCin(),besionsServiceImp.isMemHasNeed(mem));
            postes.put(mem.getCin(),membreDepartService.memberPost(mem));
        });
        model.addAttribute("membres",members);
        model.addAttribute("etat",etat);
        model.addAttribute("post",postes);
        return "/ofresEtBesoin/besoinsChefDepartemen";
    }
    @PostMapping(path = "/deleteBesoin")
    @ResponseBody
    public String deleteBesoin(@RequestBody Long id){
        besionsServiceImp.delete(id);
        return "succ";
    }
    @PostMapping(path = "/editBesoinModalForm")
    @ResponseBody
    public String getBesoinFormModal(@RequestBody Long id){
        Besoin besoin = besionsServiceImp.getMemberBesoin(id);
        var ressourece = besionsServiceImp.test(besoin);
        String repeaterList = "";
        String repeaterItem = """
                <div data-repeater-item>
                                                <div class="row d-flex align-items-end">
                                                    <div class="select-type col-md-4 col-12">
                                                        <div class="mb-1">
                                                            <select  name="type" class="type-rsc form-select" id="ressource-type">
                                                                <option value="0" >Type de ressource</option>
                                                                <option value="1" %s>Oridinateur</option>
                                                                <option value="2" %s>Imprimente</option>
                                                            </select>
                                                        </div>
                                                    </div>
                                                    <div class="col-md-4 col-12">
                                                        <div class="mb-1">
                                                            <label class="form-label" for="quantite">quantité</label>
                                                            <input type="text" value="%s" name="quantite" class="form-control" id="quantite" aria-describedby="itemname" placeholder="1" />
                                                        </div>
                                                    </div>
                                                    <!-- PC -->
                                                    <div  class="pc  col-md-4 col-12 %s" >
                                                        <div class="mb-1">
                                                            <label class="form-label" for="CPU">CPU</label>
                                                            <input type="text" value="%s" name="cpu"  class="form-control" id="CPU" aria-describedby="itemname" placeholder="Vuexy Admin Template" />
                                                        </div>
                                                    </div>
                                                    <div class="pc  col-md-4 col-12 %s">
                                                        <div class="mb-1">
                                                            <label class="form-label" for="ecran">ecran</label>
                                                            <input type="text" value="%s" name="ecran" class="form-control" id="ecran" aria-describedby="itemname" placeholder="Vuexy Admin Template" />
                                                        </div>
                                                    </div>
                                                    <div class="pc  col-md-4 col-12 %s">
                                                        <div class="mb-1">
                                                            <label class="form-label" for="ram">RAM</label>
                                                            <input type="text" value="%s" name="ram" class="form-control" id="ram" aria-describedby="itemname" placeholder="Vuexy Admin Template" />
                                                        </div>
                                                    </div>
                                                    <div class="pc  col-md-4 col-12 %s">
                                                        <div class="mb-1">
                                                            <label class="form-label" for="disque_dur">disque dur</label>
                                                            <input type="text" value="%s" name="disque_dur" class="form-control" id="disque_dur" aria-describedby="itemname" placeholder="Vuexy Admin Template" />
                                                        </div>
                                                    </div>
                                                    <!-- PC -->
                                                    <!-- imp -->
                                                    <div class="%s imp  col-md-4 col-12">
                                                        <div class="mb-1">
                                                            <label class="form-label" for="resolition">Resolition</label>
                                                            <input type="text" value="%s" name="resolition" class="form-control" id="resolition" aria-describedby="itemname" placeholder="Vuexy Admin Template" />
                                                        </div>
                                                    </div>
                                                    <div class="%s imp  col-md-4 col-12">
                                                        <div class="mb-1">
                                                            <label class="form-label" for="vitesse">Vitesse</label>
                                                            <input type="text" value="%s" name="vitesse" class="form-control" id="vitesse" aria-describedby="itemname" placeholder="Vuexy Admin Template" />
                                                        </div>
                                                    </div>
                                                   
                                                    <!-- imp -->
                                                    <div class="col-md-2 col-12 mb-50">
                                                        <div class="mb-1">
                                                            <button class="btn btn-outline-danger text-nowrap px-1" data-repeater-delete type="button">
                                                                <i data-feather="x" class="me-25"></i>
                                                                <span>Delete</span>
                                                            </button>
                                                        </div>
                                                    </div>
                                                </div>
                                                <hr />
                                            </div>
                """;
        String out = """
                                        <input type="hidden" name="id_dep" value="%d">
                                        <input type="hidden" name="id_mem_dep" value="%d">
                                        <input type="hidden" name="id_besoin" value="%d">
                                        <input type="hidden" name="etat_besoin"  value="false">
                                        
                                        <div data-repeater-list="ressource" >
                                            %s
                                        </div>
                                        <div class="row">
                                            <div class="col-12 text-center">
                                                <button class="btn btn-icon btn-primary" type="button" data-repeater-create>
                                                    <i data-feather="plus" class="me-25"></i>
                                                    <span>Add New</span>
                                                </button>
                                            </div>
                                        </div>
                                    
                """;
        for (String[] item : ressourece) {
            String vars = "select-pc select-imp quantité d-none-pc cpu d-none-pc ecran d-none-pc ram d-none-pc disq d-none-imp resoul d-none-imp vitess ";
            String selectedPc = (item[0].equals("pc"))? "selected":"";
            String selectedImp = (item[0].equals("imp"))? "selected":"";
            String dNonePc = (item[0].equals("pc"))? "":"d-none";
            String dNoneImp = (item[0].equals("imp"))? "":"d-none";
            String quantite = item[1];
            String cpu = (item[0].equals("pc"))? item[3]:"";
            String ecran = (item[0].equals("pc"))? item[2]:"";
            String ram = (item[0].equals("pc"))? item[4]:"";
            String disq = (item[0].equals("pc"))? item[5]:"";
            String resoulution = (item[0].equals("imp"))? item[2]:"";
            String vitess = (item[0].equals("imp"))? item[3]:"";
            repeaterList += String.format(repeaterItem,selectedPc,selectedImp,quantite,
                    dNonePc,cpu,dNonePc,ecran,dNonePc,ram,dNonePc,disq,
                    dNoneImp,resoulution,dNoneImp,vitess);
        }
        return String.format(out,besoin.getDeparetement().getId_departement(),besoin.getMembre().getId(),besoin.getId(),repeaterList);
    }

    @PostMapping(path = "/envoyerDemandeModalForm")
    @ResponseBody
    public String getDemandeFormModal(@RequestBody Long id){
        List<Besoin> besoins = besionsServiceImp.getAllDepartementNeed(id);
        return demandeService.getDemandesResume(besoins);
    }

    @PostMapping(path = "/envoyerDemande")
    @ResponseBody
    public String envoyerDemande(@RequestBody Long id_dep){
        Departement departement = departementService.find(id_dep);
        List<Besoin> besoins = besionsServiceImp.getAllDepartementNeed(id_dep);
        besoins.forEach(besoin -> besoin.setEtat(true));
        Demande_departement demandeDepartement = new Demande_departement();
        demandeDepartement.setDate(String.valueOf(new Date()));
        demandeDepartement.setEtat("en attente");
        demandeDepartement.setDepartement(departement);
        demandeDepartement.setBesoin(besoins);
        demandeService.save(demandeDepartement);
        return "sucess";
    }

    @GetMapping(path = "/suivreDemande")
    public String suivreDemande(Model model,Principal principal){
        var membre = membreDepartService.findMembreByUserName(principal.getName());
        var demandes = demandeService.findAllDepartementDemandes(membre.getDepartement());
        model.addAttribute("demandes",demandes);
        return "/ofresEtBesoin/suivreDemande";
    }

    @PostMapping(path = "/suivreDemandeDetail")
    @ResponseBody
    public String getSuivreDemandeResume(@RequestBody Long id){
        List<Besoin> besoins = demandeService.find(id).getBesoin();
        return demandeService.getDemandesResume(besoins);
    }


/*
    public String getString(List<Besoin> besoins) {
        List<String[]> demandeResume = besionsServiceImp.ressource_resume(besoins);
        demandeResume.forEach(item -> System.out.println(item[0]+" "+item[1]));
        int pcCount = 0;
        int impCount = 0;
        int ressourceCount = 0;
        String ressourceDesc = "";
        String imp_svg = "<svg xmlns=\"http://www.w3.org/2000/svg\" width=\"24\" height=\"24\" viewBox=\"0 0 24 24\" fill=\"none\" stroke=\"currentColor\" stroke-width=\"2\" stroke-linecap=\"round\" stroke-linejoin=\"round\" class=\"feather feather-printer me-1\"><polyline points=\"6 9 6 2 18 2 18 9\"></polyline><path d=\"M6 18H4a2 2 0 0 1-2-2v-5a2 2 0 0 1 2-2h16a2 2 0 0 1 2 2v5a2 2 0 0 1-2 2h-2\"></path><rect x=\"6\" y=\"14\" width=\"12\" height=\"8\"></rect></svg>";
        String pc_svg = " <svg xmlns=\"http://www.w3.org/2000/svg\" width=\"24\" height=\"24\" viewBox=\"0 0 24 24\" fill=\"none\" stroke=\"currentColor\" stroke-width=\"2\" stroke-linecap=\"round\" stroke-linejoin=\"round\" class=\"feather feather-monitor me-1\"><rect x=\"2\" y=\"3\" width=\"20\" height=\"14\" rx=\"2\" ry=\"2\"></rect><line x1=\"8\" y1=\"21\" x2=\"16\" y2=\"21\"></line><line x1=\"12\" y1=\"17\" x2=\"12\" y2=\"21\"></line></svg>";
        String svg ="";
        String li = """
                <li class="list-group-item d-flex align-items-center">
                            %s
                            <span> %s</span>
                            <span class="badge bg-primary rounded-pill ms-auto">%s</span>
                </li>
                """;
        String ul = "";
        for (String[] item : demandeResume) {
            ressourceCount = Integer.parseInt(item[1]);
            ressourceDesc = item[0].split(",")[1];
            boolean isPc = item[0].split(",")[0].equals("pc");
            if (isPc){
                pcCount += ressourceCount;
                svg = pc_svg;
            }
            else {
                impCount += ressourceCount;
                svg = imp_svg;
            }
            ul += String.format(li,svg,ressourceDesc,ressourceCount);
        }
        String modalBody = """
                <div class="col-12">
                    <div class=" card-statistics">
                        <div class="card-body statistics-body">
                            <div class="row">
                                
                                <div class="col-xl-3 col-sm-6 col-12 mb-2 mb-xl-0">
                                    <div class="d-flex flex-row">
                                        <div class="avatar bg-light-primary me-2">
                                            <div class="avatar-content">
                                                <svg xmlns="http://www.w3.org/2000/svg" width="14" height="14" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-monitor font-medium-3"><rect x="2" y="3" width="20" height="14" rx="2" ry="2"></rect><line x1="8" y1="21" x2="16" y2="21"></line><line x1="12" y1="17" x2="12" y2="21"></line></svg>
                                            </div>
                                        </div>
                                        <div class="my-auto">
                                            <h4 class="fw-bolder mb-0">%d</h4>
                                            <p class="card-text font-small-3 mb-0">Ordinteur</p>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-xl-3 col-sm-6 col-12 mb-2 mb-xl-0">
                                    <div class="d-flex flex-row">
                                        <div class="avatar bg-light-info me-2">
                                            <div class="avatar-content">
                                                <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-printer font-medium-3"><polyline points="6 9 6 2 18 2 18 9"></polyline><path d="M6 18H4a2 2 0 0 1-2-2v-5a2 2 0 0 1 2-2h16a2 2 0 0 1 2 2v5a2 2 0 0 1-2 2h-2"></path><rect x="6" y="14" width="12" height="8"></rect></svg>
                                            </div>
                                        </div>
                                        <div class="my-auto">
                                            <h4 class="fw-bolder mb-0">%d</h4>
                                            <p class="card-text font-small-3 mb-0">Imprimentes</p>
                                        </div>
                                    </div>
                                </div>
                                
                            </div>
                        </div>
                    </div>
                </div>
                <!-- ::::::::::::::::::::::::::::::::::::::::::::::::::: -->
                <div class="card-body">
                    <div class="alert alert-warning" role="alert">
                        <div class="alert-body d-flex align-items-center">
                            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="font-medium-3 me-1"><path d="M10.29 3.86L1.82 18a2 2 0 0 0 1.71 3h16.94a2 2 0 0 0 1.71-3L13.71 3.86a2 2 0 0 0-3.42 0z"></path><line x1="12" y1="9" x2="12" y2="13"></line><line x1="12" y1="17" x2="12.01" y2="17"></line></svg>
                            <span> notez bien que si vous envoyez la demande vous pourez pas modifier les besoins.</span>
                        </div>
                    </div>
                    <ul class="list-group">
                        %s
                    </ul>
                </div>
                """;
        return String.format(modalBody,pcCount,impCount,ul);
    }
    */
}
