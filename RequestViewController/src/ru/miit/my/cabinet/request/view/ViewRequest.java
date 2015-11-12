
package ru.miit.my.cabinet.request.view;


import java.text.SimpleDateFormat;

import java.util.Calendar;

import oracle.adf.model.BindingContext;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import oracle.adf.model.binding.DCBindingContainer;

import oracle.jbo.Row;

import ru.miit.my.cabinet.request.view.utils.ADFUtils;

public class ViewRequest {
    public ViewRequest() {
        super();
        checkbox = false;

        System.out.println("-------->BEAN CREATED!!!");
    }

    private boolean checkbox;
    static private boolean enableDeleteCheckbox = false; //Нажали на кнопочку Редактировать
    private String mes;
    public java.sql.Date dateNow;
    public String dateNow2;
    static private boolean add = false; //Что то изменили на странице кнопка Сохранить активировалась

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

        Calendar currentDate = Calendar.getInstance(); //Get the current date
        SimpleDateFormat formatter =
            new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //format it as per your requirement HH:mm:ss
        dateNow =
                new java.sql.Date(currentDate.getTime().getTime()); //new java.sql.Date(System.currentTimeMillis()); //new java.sql.Date(currentDate.getTime().getTime());
        System.out.println(formatter.format(dateNow));
        dateNow2 = formatter.format(dateNow);
        System.out.println(dateNow2);


        BindingContainer bindings = getBindings();

        OperationBinding operationBinding = bindings.getOperationBinding("ForumCreateInsert");
        Object result = operationBinding.execute();

        if (!operationBinding.getErrors().isEmpty()) {
            return null;
        }

        this.setMes("");
        System.out.println("cb2Bool " + add);
        setAdd(true);
        System.out.println("cb2Bool " + add);

        return null;
    }

    public void setAdd(boolean add) {
        this.add = add;
    }

    public boolean isAdd() {
        return add;
    }

    public String cb3_action() {
        System.out.println("cb3Bool " + add);
        setAdd(false);
        System.out.println("cb3Bool " + add);
        return "Commit";
    }


    public String cb1_action() {
        System.out.println("cb1Bool " + add);
        setAdd(false);
        System.out.println("cb1Bool " + add);
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
        //Row row = ADFUtils.findIterator("RequestforummessageView3Iterator").getCurrentRow();
        Row[] rows = ADFUtils.findIterator("RequestforummessageView3Iterator").getAllRowsInRange();
        for (int i = 0; i < rows.length; i++) {
            if (rows[i].getAttribute("YesNo").equals(true))
                rows[i].remove();

        }
        return null;
    }


    public void cb5_action() {
        System.out.println("checbox was " + enableDeleteCheckbox);
        setEnableDeleteCheckbox(!isEnableDeleteCheckbox());
        System.out.println("now checbox is " + enableDeleteCheckbox);
    }

    public void setEnableDeleteCheckbox(boolean enableDeleteCheckbox) {
        //System.out.println("checbox was "+enableDeleteCheckbox);
        this.enableDeleteCheckbox = enableDeleteCheckbox;
        // System.out.println("now checbox is "+enableDeleteCheckbox);

    }

    public boolean isEnableDeleteCheckbox() {
        return enableDeleteCheckbox;
    }

    public void setDateNow(java.sql.Date dateNow) {
        this.dateNow = dateNow;
    }

    public java.sql.Date getDateNow() {
        return dateNow;
    }

    public void setDateNow2(String dateNow2) {
        this.dateNow2 = dateNow2;
    }

    public String getDateNow2() {
        return dateNow2;
    }
}
