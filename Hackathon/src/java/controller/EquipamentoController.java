package controller;





import dao.Equipamento;
import controller.util.JsfUtil;
import controller.util.JsfUtil.PersistAction;
import bean.EquipamentoFacade;
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

@Named("equipamentoController")
@SessionScoped
public class EquipamentoController implements Serializable {

    @EJB
    private EquipamentoFacade ejbFacade;
    private List<Equipamento> items = null;
    private Equipamento selected;

    public EquipamentoController() {
       prepareCreate();
    }

    public Equipamento getSelected() {
        return selected;
    }

    public void setSelected(Equipamento selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private EquipamentoFacade getFacade() {
        return ejbFacade;
    }

    public Equipamento prepareCreate() {
        selected = new Equipamento();
        initializeEmbeddableKey();
        return selected;
    }
    
    public boolean autenticarPatri (Equipamento equipamento){
        Boolean autenticar = true;
        
        /*Se o Patrimônio digitado NÃO possuir "ST" seguido de 5 dígitos numéricos, exibirá um erro;
           */
        String patrimonio = equipamento.getPatrimony();
       if (!patrimonio.matches("^ST\\d{5}$")){
            JsfUtil.addErrorMessage("Patrimônio inválido.");
            autenticar = false;
            
        }else{
            List <Equipamento> equip = getItemsAvailableSelectMany();
            for (Equipamento e : equip){
                if (e.getPatrimony().equals(equipamento.getPatrimony())){
                    JsfUtil.addErrorMessage("Patrimônio já cadastrado");
                    autenticar = false;
                    break;
                }
            }
            
        }
       
       if (equipamento.getTipo().length() > 2){
           return autenticar;
           
       }else{
           JsfUtil.addErrorMessage("Tipo Inválido");
           autenticar = false;
    }
       
       if (equipamento.getDescription().length() > 5){
            return autenticar;
    }else{
            JsfUtil.addErrorMessage("Nome Inválido");
            return false;
        }
       
    }
    
    public void limparIncluirPat(){
            selected.setPatrimony("");
            selected.setDescription("");
            selected.setFunFk(null);
            selected.setTipo("");
            
        }
    
    
    public void create() {
       
        if (autenticarPatri(selected)){
        persist(PersistAction.CREATE, "Patrimônio incluído com sucesso.");
        }
       
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
        
        limparIncluirPat();
    }

    public void update() {
        
            persist(PersistAction.UPDATE, "Patrimônio atualizado.");
        
    }

     public void destroy() {
        persist(PersistAction.DELETE, "Patrimônio excluído com sucesso");
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Equipamento> getItems() {
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

    public Equipamento getEquipamento(java.lang.Long id) {
        return getFacade().find(id);
    }

    public List<Equipamento> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Equipamento> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = Equipamento.class)
    public static class EquipamentoControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            EquipamentoController controller = (EquipamentoController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "equipamentoController");
            return controller.getEquipamento(getKey(value));
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
            if (object instanceof Equipamento) {
                Equipamento o = (Equipamento) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Equipamento.class.getName()});
                return null;
            }
        }

    }

}
