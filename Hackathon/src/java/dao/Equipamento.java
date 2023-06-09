/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import org.hibernate.validator.constraints.br.CPF;

/**
 *
 * @author stefanini
 */
@Entity
@Table(name = "equipamento")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Equipamento.findAll", query = "SELECT u FROM Equipamento u"),
    @NamedQuery(name = "Equipamento.findByDescription", query = "SELECT u FROM Equipamento u WHERE u.description = :description"),
    @NamedQuery(name = "Equipamento.findByCpf", query = "SELECT u FROM Equipamento u WHERE u.funFk = :funFk"),
    @NamedQuery(name = "Equipamento.findById", query = "SELECT u FROM Equipamento u WHERE u.id = :idPatry"),
    @NamedQuery(name = "Equipamento.findByTipo", query = "SELECT u FROM Equipamento u WHERE u.tipo = :tipo"),
    @NamedQuery(name = "Equipamento.findByPatrimony", query = "SELECT u FROM Equipamento u WHERE u.patrimony = :patrimony")})


public class Equipamento implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    
    @Basic
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name= "description")
    private String description;
    
    @Basic
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name= "tipo")
    private String tipo;
    
    
    @Basic
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name= "patrimony")
    private String patrimony;
    
    @ManyToOne
    @JoinColumn (name = "funcionario_fk")
   private Funcionario funFk;
 
    
    public Equipamento() {
    }

    public Equipamento(Long id) {
        this.id = id;
    }

    public Equipamento(Long id, String patrimony, Funcionario funFk, String description, String tipo) {
        this.id = id;
        this.patrimony = patrimony;
        this.funFk = funFk;
        this.description = description;
        this.tipo = tipo;
      
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPatrimony() {
        return patrimony;
    }

    public void setPatrimony(String patrimony) {
        this.patrimony = patrimony;
    }

     public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    public Funcionario getFunFk () {
        return funFk;
    }

    public void setFunFk (Funcionario funFk) {
        this.funFk = funFk;
    }
    
    public String getTipo () {
        return tipo;
    }

    public void setTipo (String tipo) {
        this.tipo = tipo;
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
        if (!(object instanceof Equipamento)) {
            return false;
        }
        Equipamento other = (Equipamento) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "dao.Equipamento[ id=" + id + " ]";
    }
    
}
