/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Trwałość;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author student
 */
@Entity
@Table(name = "LOG")
@NamedQueries({
    @NamedQuery(name = "Log.findAll", query = "SELECT l FROM Log l"),
    @NamedQuery(name = "Log.findByLogid", query = "SELECT l FROM Log l WHERE l.logid = :logid"),
    @NamedQuery(name = "Log.findByActiontype", query = "SELECT l FROM Log l WHERE l.actiontype = :actiontype"),
    @NamedQuery(name = "Log.findByActiondescription", query = "SELECT l FROM Log l WHERE l.actiondescription = :actiondescription"),
    @NamedQuery(name = "Log.findByActiontimestamp", query = "SELECT l FROM Log l WHERE l.actiontimestamp = :actiontimestamp")})
public class Log implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "LOGID")
    private Integer logid;
    @Column(name = "ACTIONTYPE")
    private String actiontype;
    @Column(name = "ACTIONDESCRIPTION")
    private String actiondescription;
    @Column(name = "ACTIONTIMESTAMP")
    @Temporal(TemporalType.TIMESTAMP)
    private Date actiontimestamp;

    public Log() {
    }

    public Log(Integer logid) {
        this.logid = logid;
    }

    public Integer getLogid() {
        return logid;
    }

    public void setLogid(Integer logid) {
        this.logid = logid;
    }

    public String getActiontype() {
        return actiontype;
    }

    public void setActiontype(String actiontype) {
        this.actiontype = actiontype;
    }

    public String getActiondescription() {
        return actiondescription;
    }

    public void setActiondescription(String actiondescription) {
        this.actiondescription = actiondescription;
    }

    public Date getActiontimestamp() {
        return actiontimestamp;
    }

    public void setActiontimestamp(Date actiontimestamp) {
        this.actiontimestamp = actiontimestamp;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (logid != null ? logid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Log)) {
            return false;
        }
        Log other = (Log) object;
        if ((this.logid == null && other.logid != null) || (this.logid != null && !this.logid.equals(other.logid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Trwa\u0142o\u015b\u0107.Log[ logid=" + logid + " ]";
    }
    
    public static String getLogHTML(List<Log> logList) {
        StringBuilder html = new StringBuilder();
        html.append("<table border='1'>");
        html.append("<tr>");
        html.append("<th>ID</th>");
        html.append("<th>Typ akcji</th>");
        html.append("<th>Opis akcji</th>");
        html.append("<th>Data i czas</th>");
        html.append("</tr>");

        for (Log log : logList) {
            html.append("<tr>");
            html.append("<td>").append(log.getLogid()).append("</td>");
            html.append("<td>").append(log.getActiontype()).append("</td>");
            html.append("<td>").append(log.getActiondescription()).append("</td>");
            html.append("<td>").append(log.getActiontimestamp()).append("</td>");
            html.append("</tr>");
        }

        html.append("</table>");
        return html.toString();
    }
}
