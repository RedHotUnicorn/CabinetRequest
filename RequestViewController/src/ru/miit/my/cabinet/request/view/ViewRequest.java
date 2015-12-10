package ru.miit.my.cabinet.request.view;




import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;


import oracle.adf.model.BindingContext;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;

import oracle.adf.model.binding.DCBindingContainer;

import oracle.adf.view.rich.component.rich.input.RichInputText;

import oracle.binding.AttributeBinding;

import oracle.jbo.Row;

import ru.miit.my.cabinet.request.view.utils.ADFUtils;

public class ViewRequest {

    public ViewRequest() {
        super();
        System.out.println("Start Bean");
        System.out.println(buttonEditClicked);
    }

    static private boolean buttonEditClicked = false; //Нажали на кнопочку Редактировать
    private String forumMessage;
    private String currentDate;
    static private boolean dataOnPageChanged = false; //Что то изменили на странице кнопка Сохранить активировалась
    static private int numOfPage = 0;
    private String routerFacet = setRouter();


    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    public String sendMessage() {

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        currentDate = formatter.format(new java.sql.Date(calendar.getTime().getTime()));
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("ForumCreateInsert");
        operationBinding.getParamsMap().put("Text",
                                            getForumMessage()); // попробовал передавать значение параметра выполняемой функции

        Object result = operationBinding.execute();

        if (!operationBinding.getErrors().isEmpty()) {
            return null;
        }
        this.setForumMessage("");
        setDataOnPageChanged(true);
        return null;
    }


    public String saveChangesAndReturnToParentPage() {
        setDataOnPageChanged(false);
        setButtonEditClicked(false);
        return "Commit";
    }


    public String goBackWithNoChanges() {
        setDataOnPageChanged(false);
        setButtonEditClicked(false);
        return "RollBack";
    }

    public void edit() {
        setButtonEditClicked(!isButtonEditClicked());
    }
    
    public String deleteMessagesWithTrueCheckbox() {
        //Row[] rows = ADFUtils.findIterator("RequestforummessageView3Iterator").getAllRowsInRange();
        ArrayList<Row> rows = new ArrayList<Row>(Arrays.asList(ADFUtils.findIterator("RequestforummessageView3Iterator").getAllRowsInRange()));
        for (int i = 0; i < rows.size(); i++) {
            if (rows.get(i).getAttribute("YesNo").equals(true))
                rows.get(i).remove();
        }
        setDataOnPageChanged(true);
        return null;
    }

    

    public String setRouter() {
        BindingContainer bindings = getBindings();
        AttributeBinding Attrib = (AttributeBinding)bindings.get("Idrequesttype");
        switch ((Integer)Attrib.getInputValue()) {
        case 1:
            return "TestType1";

        case 2:
            return "TestType2";

        default:
            return "Default";
        }


    }
    //Setters and getters

    public void setButtonEditClicked(boolean enableDeleteCheckbox) {
        this.buttonEditClicked = enableDeleteCheckbox;
    }

    public boolean isButtonEditClicked() {
        return buttonEditClicked;
    }

    public void setCurrentDate(String currentDate) {
        this.currentDate = currentDate;
    }

    public String getCurrentDate() {
        return currentDate;
    }

    public void setDataOnPageChanged(boolean add) {
        this.dataOnPageChanged = add;
    }

    public boolean isDataOnPageChanged() {
        return dataOnPageChanged;
    }

    public void setForumMessage(String mes) {
        this.forumMessage = mes;
    }

    public String getForumMessage() {
        return forumMessage;
    }

    public void setRouterFacet(String routerFacet) {
        this.routerFacet = routerFacet;
    }

    public String getRouterFacet() {
        return routerFacet;
    }

    public void sbc2_validator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        // Add event code here...
    }
}
