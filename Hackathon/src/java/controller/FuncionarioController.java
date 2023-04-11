package controller;





import dao.Funcionario;
import controller.util.JsfUtil;
import controller.util.JsfUtil.PersistAction;
import bean.FuncionarioFacade;
import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@Named("funcionarioController")
@SessionScoped
public class FuncionarioController implements Serializable {

    @EJB
    private FuncionarioFacade ejbFacade;
    private List<Funcionario> items = null;
    private Funcionario selected;

    public FuncionarioController() {
        prepareCreate();
    }

    public Funcionario getSelected() {
        return selected;
    }

    public void setSelected(Funcionario selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private FuncionarioFacade getFacade() {
        return ejbFacade;
    }

    public Funcionario prepareCreate() {
        selected = new Funcionario();
        initializeEmbeddableKey();
        return selected;
    }
    
    public boolean autenticarFun (Funcionario funcionario){
        Boolean autenticar = true;
        
        if(!funcionario.getEmail().contains("@stefanini.com")){
            JsfUtil.addErrorMessage("Erro: Email inválido");
            autenticar = false;
            
        }else{
            
            List<Funcionario> fun = getItemsAvailableSelectMany();
            for (Funcionario l: fun){
                if(l.getEmail().equals(funcionario.getEmail())){
                    JsfUtil.addErrorMessage("Email já cadastrado");
                    autenticar = false;
                    break;
                }
            }
        }
        
        /*Verifica a quantidade de caracteres inseridos.
        Caso tenha algo diferente de 11, exibirá um erro.
        */
        String cpf = funcionario.getCpf();
        cpf = cpf.replaceAll("[^0-9]", "");
        funcionario.setCpf(cpf);
        if (funcionario.getCpf().length() != 11){
            JsfUtil.addErrorMessage("CPF Inválido");
            autenticar = false;
            
        }else{
            // cria uma lista e preenche com os valores retornados do banco
            List <Funcionario> funcionarios = getItemsAvailableSelectMany();
            // loop que percorre todos os elementos da lista funcionarios
            for (Funcionario f : funcionarios){
                /* se ID ou CPF digitado já constar no banco de dados, exibirá
                a mensagem, autenticar receberá false e o loop será encerrado. */
                if (f.getCpf().equals(funcionario.getCpf())){
                    JsfUtil.addErrorMessage("CPF já cadastrado");
                    autenticar = false;
                    break;
                }
            }
        }
        
        List <Funcionario> code = getItemsAvailableSelectMany();
        
        for (Funcionario c : code){
        if (c.getId().equals(funcionario.getId())){
            JsfUtil.addErrorMessage("ID já cadastrado");
                    autenticar = false;
                    break;
        }
            }
        String nome = funcionario.getName();
        /*
        Exige que o nome digitado possua nome e sobrenome
        através de uma Expressão Regular.
        */
        if (nome.matches("^[a-zA-Z]+ [a-zA-Z]+$")){
            return autenticar;
    }else{
            JsfUtil.addErrorMessage("Nome Inválido");
            return false;
        }
                        
    }
    
    public void create() {
       
       if(autenticarFun(selected))
       { persist(PersistAction.CREATE, "Funcionário incluído com sucesso.");
       }
       
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
        
        limparIncluir();
        
    }

        public void limparIncluir(){
            selected.setCargo("");
            selected.setCpf("");
            selected.setEmail("");
            selected.setName("");
            
        }
    public void update() {
        
            persist(PersistAction.UPDATE, "Funcionario atualizado.");
        
    }
    

    public void destroy() {
        persist(PersistAction.DELETE, "Funcionário excluído com sucesso");
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Funcionario> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }

    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            setEmbeddableKeys();
            try {
                if (persistAction != PersistAction.DELETE) {
                    getFacade().edit(selected);
                } else {
                    getFacade().remove(selected);
                }
                JsfUtil.addSuccessMessage(successMessage);
            } catch (EJBException ex) {
                String msg = "";
                Throwable cause = ex.getCause();
                if (cause != null) {
                    msg = cause.getLocalizedMessage();
                }
                if (msg.length() > 0) {
                    JsfUtil.addErrorMessage(msg);
                } else {
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            }
        }
    }

    public Funcionario getFuncionario(java.lang.Long id) {
        return getFacade().find(id);
    }

    public List<Funcionario> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Funcionario> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = Funcionario.class)
    public static class FuncionarioControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            FuncionarioController controller = (FuncionarioController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "funcionarioController");
            return controller.getFuncionario(getKey(value));
        }

        java.lang.Long getKey(String value) {
            java.lang.Long key;
            key = Long.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Long value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Funcionario) {
                Funcionario o = (Funcionario) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Funcionario.class.getName()});
                return null;
            }
        }

    }

}
