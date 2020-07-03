package com.peper.blog.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Objects;

@Entity
@Table(name = "entry", schema = "blog", catalog = "")
public class EntryEntity {
    @NotBlank
    private String tittle;
    private String text;
    private Integer idEntry;
    @JsonIgnore
    private Integer fkPersonidPerson;
    @JsonIgnore
    private PersonEntity personByFkPersonidPerson;

    @Basic
    @Column(name = "tittle", nullable = true, length = 255)
    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    @Basic
    @Column(name = "text", nullable = true, length = 255)
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Id
    @Column(name = "id_entry", nullable = false)
    @GeneratedValue(strategy= GenerationType.AUTO,generator="native")
    @GenericGenerator(name = "native",strategy = "native")
    public Integer getIdEntry() {
        return idEntry;
    }

    public void setIdEntry(Integer idEntry) {
        this.idEntry = idEntry;
    }

    @Basic
    @Column(name = "fk_personid_person", nullable = false)
    public Integer getFkPersonidPerson() {
        return fkPersonidPerson;
    }

    public void setFkPersonidPerson(Integer fkPersonidPerson) {
        this.fkPersonidPerson = fkPersonidPerson;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EntryEntity that = (EntryEntity) o;
        return Objects.equals(tittle, that.tittle) &&
                Objects.equals(text, that.text) &&
                Objects.equals(idEntry, that.idEntry) &&
                Objects.equals(fkPersonidPerson, that.fkPersonidPerson);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tittle, text, idEntry, fkPersonidPerson);
    }

    @ManyToOne
    @JoinColumn(name = "fk_personid_person", referencedColumnName = "id_person", nullable = false, insertable = false, updatable = false)
    public PersonEntity getPersonByFkPersonidPerson() {
        return personByFkPersonidPerson;
    }

    public void setPersonByFkPersonidPerson(PersonEntity personByFkPersonidPerson) {
        this.personByFkPersonidPerson = personByFkPersonidPerson;
    }
}
