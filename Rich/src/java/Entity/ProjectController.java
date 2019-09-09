package Entity;

import Entity.util.JsfUtil;
import Entity.util.PaginationHelper;

import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;

@Named("projectController")
@SessionScoped
public class ProjectController implements Serializable {

    private Project current;
    private DataModel items = null;
    @EJB
    private Entity.ProjectFacade ejbFacade;
    private PaginationHelper pagination;
    private PaginationHelper countPagination;
    private PaginationHelper tablePagination;
    private int selectedItemIndex;
    private List<Project> projectList;
    private int conditionChoose;
    
    public PaginationHelper getTablePagination(){
        return tablePagination;
    }
    
    public String search(Integer cata, Integer prov) {

        //getPagination().setPage(0);
        recreateModel();
        tablePagination=null;
        return "list";
    }

    public Integer countItems(Integer cata, Integer prov) {
        recreateModel();
        items = getPagination(cata, prov, "count").createPageDataModel();
        Integer c = items.getRowCount();
        recreateModel();
        return c;
    }

    public PaginationHelper getPagination(Integer id, Integer name, String s) {       
        countPagination = new PaginationHelper(10) {

            @Override
            public int getItemsCount() {
                return getFacade().count();
            }

            @Override
            public DataModel createPageDataModel() {

                return new ListDataModel(getFacade().findAll(new int[]{getPageFirstItem(), getPageFirstItem() + getPageSize()}, id, name));
            }
        };

        return countPagination;
    }

    public DataModel tableItems(Integer cata, Integer prov) {
        if (items == null) {
            items = getPagination(cata, prov).createPageDataModel();
        }
        return items;
    }

    public String next(Integer id, Integer name) {
        getPagination(id, name).nextPage();
        recreateModel();
        return "list";
    }

    public String previous(Integer id, Integer name) {
        getPagination(id,name).previousPage();
        recreateModel();
        return "list";
    }

    public PaginationHelper getPagination(Integer id, Integer name) {
        if (tablePagination == null) {

        tablePagination = new PaginationHelper(10) {

            @Override
            public int getItemsCount() {
                return getFacade().count();
            }

            @Override
            public DataModel createPageDataModel() {

                return new ListDataModel(getFacade().findRange(new int[]{getPageFirstItem(), getPageFirstItem() + getPageSize()}, id, name));
            }
        };
        }

        return tablePagination;
    }

    
    public ProjectController() {
    }

    public Project getSelected() {
        if (current == null) {
            current = new Project();
            selectedItemIndex = -1;
        }
        return current;
    }

    private ProjectFacade getFacade() {
        return ejbFacade;
    }

    public PaginationHelper getPagination() {
        if (pagination == null) {
            pagination = new PaginationHelper(10) {

                @Override
                public int getItemsCount() {
                    return getFacade().count();
                }

                @Override
                public DataModel createPageDataModel() {
                    return new ListDataModel(getFacade().findRange(new int[]{getPageFirstItem(), getPageFirstItem() + getPageSize()}));
                }
            };
        }
        return pagination;
    }

    public String prepareList() {
        recreateModel();
        return "List";
    }

    public String prepareView() {
        current = (Project) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new Project();
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("ProjectCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (Project) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("ProjectUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (Project) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        performDestroy();
        recreatePagination();
        recreateModel();
        return "List";
    }

    public String destroyAndView() {
        performDestroy();
        recreateModel();
        updateCurrentItem();
        if (selectedItemIndex >= 0) {
            return "View";
        } else {
            // all items were removed - go back to list
            recreateModel();
            return "List";
        }
    }

    private void performDestroy() {
        try {
            getFacade().remove(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("ProjectDeleted"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
    }

    private void updateCurrentItem() {
        int count = getFacade().count();
        if (selectedItemIndex >= count) {
            // selected index cannot be bigger than number of items:
            selectedItemIndex = count - 1;
            // go to previous page if last page disappeared:
            if (pagination.getPageFirstItem() >= count) {
                pagination.previousPage();
            }
        }
        if (selectedItemIndex >= 0) {
            current = getFacade().findRange(new int[]{selectedItemIndex, selectedItemIndex + 1}).get(0);
        }
    }

    public DataModel getItems() {
        if (items == null) {
            items = getPagination().createPageDataModel();
        }
        return items;
    }

    private void recreateModel() {
        items = null;
    }

    private void recreatePagination() {
        pagination = null;
    }

    public String next() {
        getPagination().nextPage();
        recreateModel();
        return "List";
    }

    public String previous() {
        getPagination().previousPage();
        recreateModel();
        return "List";
    }

    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), true);
    }

    public Project getProject(java.lang.Integer id) {
        return ejbFacade.find(id);
    }

    @FacesConverter(forClass = Project.class)
    public static class ProjectControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ProjectController controller = (ProjectController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "projectController");
            return controller.getProject(getKey(value));
        }

        java.lang.Integer getKey(String value) {
            java.lang.Integer key;
            key = Integer.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Integer value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Project) {
                Project o = (Project) object;
                return getStringKey(o.getProjId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Project.class.getName());
            }
        }

    }

  /*public void setProjectList(){
        
    }
    
    public List<Project> getProjectList(){
        projectList = this.getFacade().findAll();
        return projectList;
    }*/
    
    /*public List<Project> getProjectList(int condition){//string
        System.out.println("获得了projectList");
        projectList=this.getFacade().findAll(condition);
        return projectList;
    }*/
    
    //满足条件的projectList
    public List<Project> getProjectList(int condition) {
        projectList=getFacade().findByProjCondition(condition);
       
        return projectList;
    }
    
    public void setConditionChoose(){      
    }
    
    public int getConditionChoose(){
        return conditionChoose;
    }
    
    public void btnUnchecked(){
        System.out.println("点击了未审核按钮");
        conditionChoose=0;     
    }
    
    public void btnCheckSuccess(){
         System.out.println("点击了审核成功按钮");
        conditionChoose=1;
    }
    
    public void btnCheckFail(){
         System.out.println("点击了审核失败按钮");
        conditionChoose=-1;
    }
    
    public void btnAccept(Project prj,int pjID){      
                int condition=1;
                System.out.println("通过");
                getFacade().projectChange(prj, condition, pjID);
                System.out.println("btnAccept");
                
            
    }
    
    
    public void btnDeny(Project prj,int pjID){   
               int condition=-1;
                 System.out.println("未通过");
                getFacade().projectChange(prj, condition, pjID);
                 System.out.println("btnDeny");
             
    }  
}
