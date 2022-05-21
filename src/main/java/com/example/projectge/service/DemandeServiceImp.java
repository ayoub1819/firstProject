package com.example.projectge.service;

import com.example.projectge.DAO.DemandeRepository;
import com.example.projectge.models.Besoin;
import com.example.projectge.models.Demande_departement;
import com.example.projectge.models.Departement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DemandeServiceImp implements DemandeService{
    @Autowired
    private DemandeRepository demandeRepository;
    @Autowired
    private BesionsServiceImp besionsServiceImp;
    @Override
    public void save(Demande_departement demande) {
        demandeRepository.save(demande);
    }

    @Override
    public List<Demande_departement> findAllDepartementDemandes(Departement departement) {
        return demandeRepository.findDemande_departementByDepartement(departement);
    }

    @Override
    public Demande_departement find(Long id) {
        return demandeRepository.findById(id).orElse(null);
    }
    @Override
    public String getDemandesResume(List<Besoin> besoins) {
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
}
