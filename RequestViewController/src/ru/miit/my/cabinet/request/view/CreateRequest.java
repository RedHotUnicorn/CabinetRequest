package ru.miit.my.cabinet.request.view;

import java.text.SimpleDateFormat;

import java.util.Calendar;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import oracle.adf.model.BindingContext;

import oracle.binding.AttributeBinding;
import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import ru.miit.my.cabinet.request.view.utils.ADFUtils;

public class CreateRequest {
    //Переменные
    static private boolean buttonEditClicked = false; //Нажали на кнопочку Редактировать
    private String forumMessage = "";
    private String currentDate;
    static private boolean dataOnPageChanged = false; //Что то изменили на странице кнопка Сохранить активировалась
    static private int numOfPage = 0;
   // private String routerFacet = setRouter();
    //Конструктор

    public CreateRequest() {
        super();
        System.out.println("Start Bean");
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
        operationBinding.getParamsMap().put("Text",
                                            getForumMessage());
        operationBinding.getParamsMap().put("Creationdate",
                                            currentDate);// попробовал передавать значение параметра выполняемой функции 

        Object result = operationBinding.execute();

        if (!operationBinding.getErrors().isEmpty()) {
            return null;
        }
        this.setForumMessage("");
        setDataOnPageChanged(true);
        return null;
    }
    //Роутер

    /*public String setRouter() {
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
    }*/

    public void edit() {
        setButtonEditClicked(!isButtonEditClicked());
    }

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

    public static boolean isDataOnPageChanged() {
        return dataOnPageChanged;
    }

    public static void setButtonEditClicked(boolean b) {
        buttonEditClicked = b;
    }

    public static boolean isButtonEditClicked() {
        return buttonEditClicked;
    }


}

