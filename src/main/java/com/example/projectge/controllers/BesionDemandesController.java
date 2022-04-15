package com.example.projectge.controllers;

import com.example.projectge.models.Besoin;
import com.example.projectge.service.BesionsServiceImp;
import com.example.projectge.service.MembreDepartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@Controller
public class BesionDemandesController {
    @Autowired
    private BesionsServiceImp besionsServiceImp;
    @Autowired
    private MembreDepartService membreDepartService;

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
}
