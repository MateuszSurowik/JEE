/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Trwałość;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author student
 */
@Entity
@Table(name = "DATA")
@NamedQueries({
    @NamedQuery(name = "Data.findAll", query = "SELECT d FROM Data d"),
    @NamedQuery(name = "Data.findById", query = "SELECT d FROM Data d WHERE d.id = :id"),
    @NamedQuery(name = "Data.findByName", query = "SELECT d FROM Data d WHERE d.name = :name"),
    @NamedQuery(name = "Data.findBySurname", query = "SELECT d FROM Data d WHERE d.surname = :surname")})
public class Data implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "NAME")
    private String name;
    @Column(name = "SURNAME")
    private String surname;

    public Data() {
    }

    public Data(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Data)) {
            return false;
        }
        Data other = (Data) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Trwa\u0142o\u015b\u0107.Data[ id=" + id + " ]";
    }
    public String getListaHTML(List<Data> lista) {
String wiersz;
wiersz = ("<table><tr>");
wiersz = wiersz.concat(
"<td><b>ID</b></td>"
+ "<td><b>IMIE</b></td>"
+ "<td><b>NAZWISKO</b></td>");
wiersz = wiersz.concat("</tr>");
for (Data ldz : lista) {
wiersz = wiersz.concat("<tr>");
wiersz = wiersz.concat("<td>" + ldz.getId() + "</td>");
wiersz = wiersz.concat("<td>" + ldz.getName() + "</td>");
wiersz = wiersz.concat("<td>" + ldz.getSurname() + "</td>");
wiersz = wiersz.concat("</tr>");
}
wiersz = wiersz.concat("</table>");
return wiersz;
}
}
