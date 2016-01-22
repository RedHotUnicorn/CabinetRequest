package ru.miit.my.cabinet.request.view;


import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;

import oracle.adf.model.BindingContext;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;

import javax.faces.event.ActionEvent;
import javax.faces.validator.ValidatorException;

import oracle.adf.model.binding.DCBindingContainer;

import oracle.adf.view.rich.component.rich.input.RichInputText;

import oracle.adf.view.rich.util.FacesMessageUtils;

import oracle.binding.AttributeBinding;

import oracle.jbo.Row;

import ru.miit.my.cabinet.request.view.utils.ADFUtils;

public class ViewRequest {

    private RichInputText itForum;

    public ViewRequest() {
        super();

    }

    private boolean dataOnPageChanged = false; //Что то изменили на странице кнопка Сохранить активировалась
    private String forumMessage="";
    private String routerFacet = setRouter();


    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    public void addErrorMessageToRichInputText(RichInputText ui, String mes) {
        /*Данный код выдвет ошибку если поле пустое. Для этого нужно указать в свойстве bindings текстового объекта
        * #{viewScope.CreateRequest.it2}. Создается переменная Richtext it2. уже к ней мы и обращаемся
        * */
        String messageText = mes;
        FacesMessage fm = new FacesMessage(messageText);
        fm.setSeverity(FacesMessage.SEVERITY_ERROR);
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(ui.getClientId(), fm);
    }

    public String sendMessage() {
        if (!this.forumMessage.isEmpty()) {
            System.out.println("HO-HO-HO~~~");
            BindingContainer bindings = getBindings();
            OperationBinding createForumMessageBinding = bindings.getOperationBinding("ForumCreateInsert");
            createForumMessageBinding.getParamsMap().put("Text",
                                                         getForumMessage()); // попробовал передавать значение параметра выполняемой функции
            createForumMessageBinding.execute();
            if (!createForumMessageBinding.getErrors().isEmpty()) {
                return null;
            }
            this.setForumMessage("");
            setDataOnPageChanged(true);
        } else {
            System.out.println("NO-NO-NO~~~");
            addErrorMessageToRichInputText(itForum, "Сообщение не может быть пустым");
        }
        return "";
    }


    
    public String deleteMessages() {
        setDataOnPageChanged(true);
        return "";
    }


    public String setRouter() {
        BindingContainer bindings = getBindings();
        AttributeBinding Attrib = (AttributeBinding)bindings.get("Idrequesttype");
        switch ((Integer)Attrib.getInputValue()) {
        case 1:
            return "TestType1";
            
        case 2:
            return "TestType2";
        case 3:
            return "TestType3";
        default:
            return "Default";
        }


    }
    //Setters and getters


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


    public void setItForum(RichInputText itForum) {
        this.itForum = itForum;
    }

    public RichInputText getItForum() {
        return itForum;
    }

    public void it2_validator(FacesContext facesContext, UIComponent uIComponent, Object object) {
       /* String name = object.toString();
        String expression = "\\d|\\w";
        CharSequence inputStr = name;
        Pattern pattern = Pattern.compile(expression);
        Matcher matcher = pattern.matcher(inputStr);
        String msg = "Сообщение введено неверно";
        if (matcher.matches()) {

        } else {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
        }*/
    }
    public void buttonAction(ActionEvent actionEvent) {
           //FacesMessage msg = new FacesMessage("Button Action called"); //#{bindings.DeleteMessage.execute}
           //msg.setSeverity(FacesMessage.SEVERITY_ERROR);
           FacesMessage msg = FacesMessageUtils.getConfirmationMessage(new FacesMessage("confirm?"));
           msg.setDetail("fuck");
           msg.setSummary("You");
           FacesContext context = FacesContext.getCurrentInstance();
           context.addMessage(null, msg);
           
           
       }

}
