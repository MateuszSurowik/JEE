/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf;

import javax.inject.Named;
import javax.enterprise.context.RequestScoped;

/**
 *
 * @author student
 */
@Named(value = "logowanie")
@RequestScoped
public class logowanie {
private String nazwa;
private String hasło;
public String getNazwa() {
return nazwa;
}
public void setNazwa(String nazwa) {
this.nazwa = nazwa;
}
public String getHasło() {
return hasło;
}
public void setHasło(String hasło) {
this.hasło = hasło;
}
public Boolean sprawdź(){
if (nazwa.equals("student") && hasło.equals("wcy"))
return true;
else
    return false;
}
public logowanie() {
}
}
