package com.peper.blog.entities;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "person", schema = "blog", catalog = "")
public class PersonEntity  {

    private String email;
    private String password;
    private Integer idPerson;
    private Collection<EntryEntity> entriesByIdPerson;

    @Email
    @Basic
    @Column(name = "email", nullable = false, length = 255)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @NotBlank
    @Basic
    @Column(name = "password", nullable = false, length = 255)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Id
    @Column(name = "id_person", nullable = false)
    @GeneratedValue(strategy= GenerationType.AUTO,generator="native")
    @GenericGenerator(name = "native",strategy = "native")
    public Integer getIdPerson() {
        return idPerson;
    }

    public void setIdPerson(Integer idPerson) {
        this.idPerson = idPerson;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PersonEntity that = (PersonEntity) o;
        return Objects.equals(email, that.email) &&
                Objects.equals(password, that.password) &&
                Objects.equals(idPerson, that.idPerson);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, password, idPerson);
    }

    //@Column(insertable = false, updatable = false)
    @OneToMany(mappedBy = "personByFkPersonidPerson")
    public Collection<EntryEntity> getEntriesByIdPerson() {
        return entriesByIdPerson;
    }

    public void setEntriesByIdPerson(Collection<EntryEntity> entriesByIdPerson) {
        this.entriesByIdPerson = entriesByIdPerson;
    }
}
