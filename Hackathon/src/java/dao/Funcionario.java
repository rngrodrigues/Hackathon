    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
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
@Table(name = "funcionario")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Funcionario.findAll", query = "SELECT u FROM Funcionario u"),
    @NamedQuery(name = "Funcionario.findById", query = "SELECT u FROM Funcionario u WHERE u.id = :id"),
    @NamedQuery(name = "Funcionario.findByName", query = "SELECT u FROM Funcionario u WHERE u.name = :name"),
    @NamedQuery(name = "Funcionario.findByCargo", query = "SELECT u FROM Funcionario u WHERE u.cargo = :cargo"),
    @NamedQuery(name = "Funcionario.findByEmail", query = "SELECT u FROM Funcionario u WHERE u.email = :email"),
    @NamedQuery(name = "Funcionario.findByCpf", query = "SELECT u FROM Funcionario u WHERE u.cpf = :cpf")})


public class Funcionario implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "cargo")
    private String cargo;
    
    @Basic(optional = false)
    @Size(min = 1, max = 50)
    @Column(name = "name")
    private String name;
    
    @Basic(optional = false)
    @Size(min = 1, max = 50)
    @Column(name = "cpf")
    @CPF
    private String cpf;
    
    @Basic
    @Size(min = 1, max = 50)
    @Column(name= "email")
    private String email;
    
    
    public Funcionario() {
    }

    public Funcionario(Long id) {
        this.id = id;
    }

    public Funcionario(Long id, String name, String cpf, String cargo, String email) {
        this.id = id;
        this.name = name;
        this.cpf = cpf;
        this.cargo = cargo;
        this.email = email;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

     public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
    
    public String getCargo () {
        return cargo;
    }

    public void setCargo (String cargo) {
        this.cargo = cargo;
    }
    
    public void setEmail(String email){
        this.email = email;
    }
    
    public String getEmail (){
        return email;
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
        if (!(object instanceof Funcionario)) {
            return false;
        }
        Funcionario other = (Funcionario) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "dao.Funcionario[ id=" + id + " ]";
    }
    
}
