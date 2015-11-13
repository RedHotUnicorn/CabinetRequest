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
        System.out.println("Start Bean");
    }
   
    static private boolean buttonEditClicked = false; //Нажали на кнопочку Редактировать
    private String forumMessage;
    private String currentDate;
    static private boolean dataOnPageChanged = false; //Что то изменили на странице кнопка Сохранить активировалась

    

    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    public String sendMessage() {

        Calendar calendar = Calendar.getInstance(); 
        SimpleDateFormat formatter =
            new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
        currentDate = formatter.format(new java.sql.Date(calendar.getTime().getTime()));
        System.out.println(currentDate);


        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("ForumCreateInsert");
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
        return "Commit";
    }


    public String goBackWithNoChanges() {
        setDataOnPageChanged(false);
        return "RollBack";
    }
   
    
    public String deleteMessagesWithTrueCheckbox() {
        Row[] rows = ADFUtils.findIterator("RequestforummessageView3Iterator").getAllRowsInRange();
        for (int i = 0; i < rows.length; i++) {
            if (rows[i].getAttribute("YesNo").equals(true))
                rows[i].remove();
        }
        return null;
    }

    public void cb5_action() {
        setButtonEditClicked(!isButtonEditClicked());
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
}
