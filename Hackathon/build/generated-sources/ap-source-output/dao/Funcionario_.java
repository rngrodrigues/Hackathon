package dao;

import dao.Equipamento;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2023-04-12T21:15:16")
@StaticMetamodel(Funcionario.class)
public class Funcionario_ { 

    public static volatile SingularAttribute<Funcionario, String> name;
    public static volatile SingularAttribute<Funcionario, String> cpf;
    public static volatile SingularAttribute<Funcionario, Long> id;
    public static volatile SingularAttribute<Funcionario, String> cargo;
    public static volatile ListAttribute<Funcionario, Equipamento> equipamentos;
    public static volatile SingularAttribute<Funcionario, String> email;

}