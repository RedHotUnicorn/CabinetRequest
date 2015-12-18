package ru.miit.my.cabinet.request.view;

import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import oracle.adf.model.BindingContext;

import oracle.binding.AttributeBinding;
import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.Row;

import ru.miit.my.cabinet.request.view.utils.ADFUtils;

public class CreateRequest {
    //Переменные
   
    private boolean dataOnPageChanged = false; //Что то изменили на странице кнопка Сохранить активировалась
    private String forumMessage = "";
    private String test1Message = "";
    private String test2Message = "";
    private String currentDate;
    private int numOfFacet = 0;

    private String routerFacet = setRouter();
    //Конструктор

    public CreateRequest() {
        super();
    }
    //Обращение к биндингс

    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    //Роутер

    public String setRouter() {
        BindingContainer bindings = getBindings();
        AttributeBinding Attrib = (AttributeBinding)bindings.get("Idrequesttype");
        switch ((Integer)Attrib.getInputValue()) {
        case 1:
            setNumOfFacet(1);
            return "TestType1";

        case 2:
            setNumOfFacet(2);
            return "TestType2";

        default:
            return "Default";
        }
    }

    //Кнопки редактирования

    /*public void editMessages() {
        setButtonEditMessagesClicked(!isButtonEditMessagesClicked());
    }

    public void editTypes() {
        setButtonEditTypesClicked(!isButtonEditTypesClicked());
    }*/

    //Создать сообщение

    public String sendMessage() {

        BindingContainer bindings = getBindings();
        OperationBinding createForumMessageBinding = bindings.getOperationBinding("ForumCreateInsert");
        createForumMessageBinding.getParamsMap().put("Text", getForumMessage());
      
        createForumMessageBinding.execute();
        if (!createForumMessageBinding.getErrors().isEmpty()) {
            return "";
        }
        this.setForumMessage("");
       // setDataOnPageChanged(true);
        return "";
    }


    //Создать объект 2 го типа

    public String createTest2Obj() {
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("Createwithparameters2");
        System.out.println(getTest2Message().substring(0, 2));
        operationBinding.getParamsMap().put("Addchar", getTest2Message().substring(0, 2));
        operationBinding.execute();
        if (!operationBinding.getErrors().isEmpty()) {
            return null;
        }
        this.setTest2Message("");
        setDataOnPageChanged(true);
        return null;
    }
    //Создать объект 1 го типа

    public String createTest1Obj() {
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("Createwithparameters1");
        operationBinding.getParamsMap().put("Chooseint", Integer.parseInt(getTest1Message()));
        operationBinding.execute();
        if (!operationBinding.getErrors().isEmpty()) {
            return "";
        }
        this.setTest1Message("");
        setDataOnPageChanged(true);
        return "";
    }

    //Удалить сообщение

   /* public String deleteMessagesWithTrueCheckbox() {

        ArrayList<Row> rows =
            new ArrayList<Row>(Arrays.asList(ADFUtils.findIterator("RequestforummessageView3Iterator").getAllRowsInRange()));
        for (int i = 0; i < rows.size(); i++) {
            if (rows.get(i).getAttribute("YesNo").equals(true))
                rows.get(i).remove();
        }
        setDataOnPageChanged(true);
        
        return null;
    }*/

    public String deleteTypes() {
        /*
        ArrayList<Row> rows = new ArrayList<Row>(Arrays.asList(ADFUtils.findIterator(str).getAllRowsInRange()));
        for (int i = 0; i < rows.size(); i++) {
            if (rows.get(i).getAttribute("YesNo").equals(true))
                rows.get(i).remove();
            
        
        }
        if (( new ArrayList<Row>(Arrays.asList(ADFUtils.findIterator(str).getAllRowsInRange()))).size()==0){
        
        setDataOnPageChanged(false);
        }
        setButtonEditTypesClicked(false);
        return null;

    */  String str = "";
        switch (numOfFacet) {
        case 1:
            str = "Typetest1View_POCHTA1Iterator";
            break;
        case 2:
            str = "Typetest2View2Iterator";
            break;
        }
        if (( new ArrayList<Row>(Arrays.asList(ADFUtils.findIterator(str).getAllRowsInRange()))).size()==0){
        
        setDataOnPageChanged(false);
        }
        return "";}
    //возврат

    public String Ret() {
        setDataOnPageChanged(false);
       // setButtonEditMessagesClicked(false);
        //setButtonEditTypesClicked(false);
        return "back";
    }

    
    //Геттеры и сеттеры

    public void setForumMessage(String forumMessage) {
        this.forumMessage = forumMessage;
    }

    public String getForumMessage() {
        return forumMessage;
    }

    public void setDataOnPageChanged(boolean dataOnPageChanged) {
        this.dataOnPageChanged = dataOnPageChanged;
    }

    public boolean isDataOnPageChanged() {
        return dataOnPageChanged;
    }


    public void setRouterFacet(String routerFacet) {
        this.routerFacet = routerFacet;
    }

    public String getRouterFacet() {
        return routerFacet;
    }

    public void setTest1Message(String teat1Message) {
        this.test1Message = teat1Message;
    }

    public String getTest1Message() {
        return test1Message;
    }

    public void setTest2Message(String test2Message) {
        this.test2Message = test2Message;
    }

    public String getTest2Message() {
        return test2Message;
    }

    public void sbc1_validator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        // Add event code here...
    }

    public void setNumOfFacet(int numOfFacet) {
        this.numOfFacet = numOfFacet;
    }

    public int getNumOfFacet() {
        return numOfFacet;
    }


    
}

