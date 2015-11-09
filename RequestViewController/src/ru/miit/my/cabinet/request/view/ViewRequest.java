
package ru.miit.my.cabinet.request.view;

import oracle.adf.model.BindingContext;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;


import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import javax.faces.event.ActionEvent;

import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCIteratorBinding;



import oracle.jbo.Row;
import oracle.jbo.ViewObject;

import ru.miit.my.cabinet.request.view.utils.ADFUtils;

public class ViewRequest {
    public ViewRequest() {
        super();
        checkbox=false;
        System.out.println("FUCKIN BEAN!!!");
    }
    private boolean checkbox ;
    
    private String mes;
    static private boolean add = false;
    public void setMes(String mes) {
        this.mes = mes;
    }

    public String getMes() {
        return mes;
    }

    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    public String cb2_action() {
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("ForumCreateInsert");
        Object result = operationBinding.execute();
        if (!operationBinding.getErrors().isEmpty()) {
            return null;
        }
        this.setMes("");
        System.out.println("cb2Bool "+add);
        setAdd(true);
        System.out.println("cb2Bool "+add);
        return null;
    }

    public void setAdd(boolean add) {
        this.add = add;
    }

    public boolean isAdd() {
        return add;
    }

    public String cb3_action() {
        System.out.println("cb3Bool "+add);
        setAdd(false);
        System.out.println("cb3Bool "+add);
        return "Commit";
    }


    public String cb1_action() {
        System.out.println("cb1Bool "+add);
        setAdd(false);
        System.out.println("cb1Bool "+add);
        return "RollBack";
    }

    public void setCheckbox(boolean checkbox) {
        this.checkbox = checkbox;
    }

    public boolean isCheckbox() {
        return checkbox;
    }

    public void checked(FacesContext facesContext, UIComponent uIComponent, Object object) {
        this.setCheckbox(!this.isCheckbox());
        System.out.println(this.isCheckbox());
        
    }


    public void sbc2_validator(FacesContext facesContext, UIComponent uIComponent, Object object) {
       // setAdd(true);
    }
    
    public DCBindingContainer getDCBindingsContainer() {
        DCBindingContainer bindingsContainer =
            (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();
        
        return bindingsContainer;
    }


    public String read() {
        /*try{  DCBindingContainer bindings = this.getDCBindingsContainer();
        DCIteratorBinding itorBinding =
            bindings.findIteratorBinding("Typetest1View_POCHTA1Iterator");
            System.out.println("Step1");
        ViewObject vo = itorBinding.getViewObject();
            System.out.println("Step2");
        Row[]  selectedRolesRows = vo.getFilteredRows("YesNo",1);//vo.getFilteredRows("YesNo", true);
           
        System.out.println(selectedRolesRows.length);
        for(Row row : selectedRolesRows){
            System.out.println("Text: "+row.getAttribute("Text"));
            
        }
        }catch(Exception e){
                System.out.println("fuck");
                
            }
        return null;*/
        
        Row row = ADFUtils.findIterator("RequestforummessageView3Iterator").getCurrentRow();
      //  System.out.println(row.getAttribute("Text")+" "+row.getAttribute("YesNo")+" "+row.getKey());
        
        Row[] rows = ADFUtils.findIterator("RequestforummessageView3Iterator").getAllRowsInRange();
        for(int i=0;i<rows.length;i++) {
            if(rows[i].getAttribute("YesNo").equals(true))
                rows[i].remove();
            //System.out.println(rows[i].getAttribute("Text")+" "+rows[i].getAttribute("YesNo"));
        }
        return null;
    }


    
}
