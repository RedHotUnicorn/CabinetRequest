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
    static private boolean buttonEditClicked = false; //Нажали на кнопочку Редактировать
    private String forumMessage = "";
    private String test1Message = "";
    private String test2Message = "";
    private String currentDate;
    static private boolean dataOnPageChanged = false; //Что то изменили на странице кнопка Сохранить активировалась
    static private int numOfPage = 0;
    private String routerFacet = setRouter();
    //Конструктор

    public CreateRequest() {
        super();
        System.out.println("Start Bean");
        System.out.println(buttonEditClicked);
    }
    //Добавление сообщения

    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }


    public String sendMessage() {

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        currentDate = formatter.format(new java.sql.Date(calendar.getTime().getTime()));
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("Createwithparameters");
        operationBinding.getParamsMap().put("Text", getForumMessage());
        operationBinding.getParamsMap().put("Creationdate",
                                            currentDate); // попробовал передавать значение параметра выполняемой функции

        Object result = operationBinding.execute();

        if (!operationBinding.getErrors().isEmpty()) {
            return null;
        }
        this.setForumMessage("");
        setDataOnPageChanged(true);
        return null;
    }
    //Роутер

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

    public void edit() {
        setButtonEditClicked(!isButtonEditClicked());
    }

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

    public String createTest1Obj() {
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("Createwithparameters1");
        operationBinding.getParamsMap().put("Chooseint", Integer.parseInt(getTest1Message()));


        Object result = operationBinding.execute();
        if (!operationBinding.getErrors().isEmpty()) {
            return null;
        }
        this.setTest1Message("");
        setDataOnPageChanged(true);
        return null;
    }

    public String deleteMessagesWithTrueCheckbox() {
        //Row[] rows = ADFUtils.findIterator("RequestforummessageView3Iterator").getAllRowsInRange();
        ArrayList<Row> rows =
            new ArrayList<Row>(Arrays.asList(ADFUtils.findIterator("RequestforummessageView3Iterator").getAllRowsInRange()));
        for (int i = 0; i < rows.size(); i++) {
            if (rows.get(i).getAttribute("YesNo").equals(true))
                rows.get(i).remove();
        }
        setDataOnPageChanged(true);
        return null;
    }

    public String Ret() {
        setDataOnPageChanged(false);
        setButtonEditClicked(false);
        return "back";
    }

    /* public String goBackWithNoChanges() {
        setDataOnPageChanged(false);
        setButtonEditClicked(false);
        return "RollBack";
    }

    public String saveChangesAndReturnToParentPage() {
        setDataOnPageChanged(false);
        setButtonEditClicked(false);
        return "Commit";
    }*/

    /*
    private class TestType1 { //представление таблицы ввиде класса
        private int idTestType;
        private int idEmployee;
        private int idRequest;
        private int chooseInt;

        public void setIdTestType(int idTestType) {
            this.idTestType = idTestType;
        }

        public int getIdTestType() {
            return idTestType;
        }

        public void setIdEmployee(int idEmployee) {
            this.idEmployee = idEmployee;
        }

        public int getIdEmployee() {
            return idEmployee;
        }

        public void setIdRequest(int idRequest) {
            this.idRequest = idRequest;
        }

        public int getIdRequest() {
            return idRequest;
        }

        public void setChooseInt(int chooseInt) {
            this.chooseInt = chooseInt;
        }

        public int getChooseInt() {
            return chooseInt;
        }
    }

    private class TestType2 { //представление таблицы ввиде класса
        private int idTestType;
        private int idEmployee;
        private int idRequest;
        private int addchar;

        public void setIdTestType(int idTestType) {
            this.idTestType = idTestType;
        }

        public int getIdTestType() {
            return idTestType;
        }

        public void setIdEmployee(int idEmployee) {
            this.idEmployee = idEmployee;
        }

        public int getIdEmployee() {
            return idEmployee;
        }

        public void setIdRequest(int idRequest) {
            this.idRequest = idRequest;
        }

        public int getIdRequest() {
            return idRequest;
        }

        public void setAddchar(int addchar) {
            this.addchar = addchar;
        }

        public int getAddchar() {
            return addchar;
        }
    }
*/
    //Геттеры и сеттеры

    public void setForumMessage(String forumMessage) {
        this.forumMessage = forumMessage;
    }

    public String getForumMessage() {
        return forumMessage;
    }

    public static void setDataOnPageChanged(boolean dataOnPageChanged) {
        CreateRequest.dataOnPageChanged = dataOnPageChanged;
    }

    public boolean isDataOnPageChanged() {
        return dataOnPageChanged;
    }

    public void setButtonEditClicked(boolean b) {
        this.buttonEditClicked = b;
    }

    public boolean isButtonEditClicked() {
        return this.buttonEditClicked;
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
}

