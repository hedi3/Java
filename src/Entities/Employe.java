/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import java.util.Date;

/**
 *
 * @author Administrateur
 */



public class Employe {
    private int id;
    private String nom;
        private String prenom;
            private Date dat_embauche;
                private String mail;
                    private String pass;
                        private String img;

    public Employe() {
    }

 
    public Employe(int id, String nom, String prenom, Date dat_embauche, String mail, String pass, String img) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.dat_embauche = dat_embauche;
        this.mail = mail;
        this.pass = pass;
        this.img = img;
    }

    public Employe(String nom, String prenom, Date dat_embauche, String mail, String pass, String img) {
        this.nom = nom;
        this.prenom = prenom;
        this.dat_embauche = dat_embauche;
        this.mail = mail;
        this.pass = pass;
        this.img = img;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public Date getDat_embauche() {
        return dat_embauche;
    }

    public void setDat_embauche(Date dat_embauche) {
        this.dat_embauche = dat_embauche;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
                        
                        
                        
                        
}
