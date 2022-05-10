package com.example.projectge.service;

import com.example.projectge.DAO.BesionRepository;
import com.example.projectge.DAO.Membre_departementRepository;
import com.example.projectge.models.Besoin;
import com.example.projectge.models.Departement;
import com.example.projectge.models.Membre_departement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BesionsServiceImp {
    @Autowired
    private BesionRepository besionRepository;
    @Autowired
    private Membre_departementRepository membre_departementRepository;
    @Autowired
    private DepartementService departementService;

    public List<String[]> test(Besoin besoin){
        if (besoin == null) return null;
        String[] ressources = besoin.getRessource().split(";");
        List<String[]> listRessources = new ArrayList<>();
        for (String ressource : ressources) {
            listRessources.add(ressource.split(","));
        }
        return listRessources;
    }
    public List<String[]> ressource_resume(List<Besoin> besoins){
        List<String[]> listRessources = new ArrayList<>();
        List<String[]> sortedListRessources = new ArrayList<>();
        List<String> sortedString = new ArrayList<>();
        List<String> sortedString1 = new ArrayList<>();
        besoins.forEach(besoin -> listRessources.addAll(test(besoin)));

        listRessources.forEach(item -> {
            String str = "";
            //pc,45,lcd,i3,8,1000;  imp,1,1080,100;
            if (item[0].equals("pc"))
              str = item[0]+",ecran: "+item[2]+"\tcpu: "+item[3]+"\tram: "+item[4]+"go\tdisq: "+item[5]+"go;"+item[1];
            else
              str = item[0]+",resolution: "+item[2]+"\tvitess: "+item[3]+";"+item[1];
            str = str.toLowerCase();
            if (!str.equals(""))  sortedString.add(str);
        });
        sortedString.forEach(item -> System.out.println("in service :"+item));
        sortedString1 = sortedString.stream().sorted().collect(Collectors.toList());
        sortedString1.forEach(item -> System.out.println("in service sorted:"+item));
        int count = 0;
        String oldItem = sortedString1.get(0).split(";")[0];
        for (String item : sortedString1) {
            String[] record = new String[2];
            String currentItem = item.split(";")[0];
            int quantity = Integer.parseInt(item.split(";")[1]);
            if (oldItem.equals(currentItem))
                count +=quantity;
            else {
                record[0] = oldItem;
                record[1] = String.valueOf(count);
                sortedListRessources.add(record);
                count = quantity;
                oldItem = currentItem;
            }

        }
        String[] record = new String[2];
        record[0] = oldItem;
        record[1] = String.valueOf(count);
        sortedListRessources.add(record);
        return sortedListRessources;
    }
    public Besoin findByMember(Long id){
        Membre_departement membre_departement = membre_departementRepository.findById(id).orElse(null);
        return besionRepository.findByMembreAndEtatFalse(membre_departement);
    }
    public Long save(Besoin besoin){
        besoin.setDate(String.valueOf(new Date()));
        return besionRepository.save(besoin).getId();
    }
    public Besoin getMemberBesoin(Long idMember){
        var member = membre_departementRepository.findById(idMember).orElse(null);
        return besionRepository.findByMembreAndEtatFalse(member);
    }
    public Besoin getDepartementBesoin(Departement departement){
        return besionRepository.findBesoinByDeparetementAndEtatFalseAndMembreNull(departement);
    }

    public boolean isMemHasNeed(Membre_departement membre_departement){
        var besoin = besionRepository.findByMembreAndEtatFalse(membre_departement);
        return besoin != null;
    }
    public void delete(Long idMember){
        var mem = membre_departementRepository.findById(idMember).orElse(null);
        var besoin = besionRepository.findByMembreAndEtatFalse(mem);
        besionRepository.delete(besoin);
    }

    public List<Besoin> getAllDepartementNeed(Long id_dep){
        return besionRepository.findByDeparetementAndEtatFalse(departementService.find(id_dep));
    }

}
