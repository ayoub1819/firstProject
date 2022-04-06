package com.example.projectge.models;


import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.Optional;

@Entity
@DiscriminatorValue("ordinateur")
public class Ordinateur extends Ressource{

    private String CPU;
    private Long disque_dur;
    private String ecran;
    private String marque;
    private Long RAM;

    public Ordinateur(){

    }

    public Ordinateur(String date_liv, String garantie, String id_fournisseur, String CPU, Long disque_dur, String ecran, String marque, Long RAM) {
        super( date_liv, garantie, id_fournisseur);
        this.CPU = CPU;
        this.disque_dur = disque_dur;
        this.ecran = ecran;
        this.marque = marque;
        this.RAM = RAM;
    }

    public Ordinateur(Long id, Fournisseur fournisseur, String date_liv, String garantie, String CPU, Long disque_dur, String ecran, String marque, Long RAM) {
        super(id, fournisseur, date_liv, garantie);
        this.CPU = CPU;
        this.disque_dur = disque_dur;
        this.ecran = ecran;
        this.marque = marque;
        this.RAM = RAM;
    }


    public Ordinateur(Fournisseur fournisseur, String date_liv, String garantie, String CPU, Long disque_dur, String ecran, String marque, Long RAM) {
        super(fournisseur, date_liv, garantie);
        this.CPU = CPU;
        this.disque_dur = disque_dur;
        this.ecran = ecran;
        this.marque = marque;
        this.RAM = RAM;
    }




    public String getCPU() {
        return CPU;
    }

    public void setCPU(String CPU) {
        this.CPU = CPU;
    }

    public Long getDisque_dur() {
        return disque_dur;
    }

    public void setDisque_dur(Long disque_dur) {
        this.disque_dur = disque_dur;
    }

    public String getEcran() {
        return ecran;
    }

    public void setEcran(String ecran) {
        this.ecran = ecran;
    }

    public String getMarque() {
        return marque;
    }

    public void setMarque(String marque) {
        this.marque = marque;
    }

    public Long getRAM() {
        return RAM;
    }

    public void setRAM(Long RAM) {
        this.RAM = RAM;
    }
}
