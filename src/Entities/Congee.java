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
public class Congee {
    private int id;
    private Date date_deb;
    private Date date_fin;
    private String employee;

    public Congee() {
    }

    public Congee(int id, Date date_deb, Date date_fin, String employee) {
        this.id = id;
        this.date_deb = date_deb;
        this.date_fin = date_fin;
        this.employee = employee;
    }

    public Congee(Date date_deb, Date date_fin, String employee) {
        this.date_deb = date_deb;
        this.date_fin = date_fin;
        this.employee = employee;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate_deb() {
        return date_deb;
    }

    public void setDate_deb(Date date_deb) {
        this.date_deb = date_deb;
    }

    public Date getDate_fin() {
        return date_fin;
    }

    public void setDate_fin(Date date_fin) {
        this.date_fin = date_fin;
    }

    public String getEmployee() {
        return employee;
    }

    public void setEmployee(String employee) {
        this.employee = employee;
    }
    
    
    
}
