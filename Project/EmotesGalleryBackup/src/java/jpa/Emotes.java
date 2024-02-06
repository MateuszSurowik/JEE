package jpa;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@Table(name = "EMOTES")
public class Emotes {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull(message = "ID cannot be null")
    @Column(name = "ID")
    private Integer id;
    @Column(name = "NAME")
    private String name;

    @Lob
    @Column(name = "EMOTE")
    private byte[] emote;

    public Emotes() {
    }

    public Emotes(Integer id) {
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

    public byte[] getEmote() {
        return emote;
    }

    public void setEmote(byte[] emote) {
        this.emote = emote;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Emotes)) {
            return false;
        }
        Emotes other = (Emotes) object;
        return (this.id == null && other.id == null) || (this.id != null && this.id.equals(other.id));
    }

    @Override
    public String toString() {
        return "jpa.Emotes[ id=" + id + " ]";
    }
}
