package ru.miit.my.cabinet.request.view;

import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import javax.faces.validator.ValidatorException;

import oracle.adf.model.BindingContext;

import oracle.adf.view.rich.component.rich.input.RichInputText;

import oracle.adf.view.rich.component.rich.nav.RichCommandImageLink;

import oracle.binding.AttributeBinding;
import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.Row;

import ru.miit.my.cabinet.request.view.utils.ADFUtils;
import ru.miit.my.cabinet.request.view.utils.JSFUtils;

public class CreateRequest {
    //Переменные

    private boolean dataOnPageChanged = false; //Что то изменили на странице кнопка Сохранить активировалась
    private String forumMessage = "";
    private String test1Message = "";
    private String test2Message = "";
    private int numOfFacet = 0;

    private String routerFacet = setRouter();
    private RichInputText itForum;
    private RichInputText itTest2;
    private RichInputText itTest1;
    private RichCommandImageLink b;
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


    //Создать сообщение

    public boolean putParameterInBinding(String binding, String param, Object value) {
        BindingContainer bindings = getBindings();
        OperationBinding createForumMessageBinding = bindings.getOperationBinding(binding);
        createForumMessageBinding.getParamsMap().put(param, value);
        createForumMessageBinding.execute();
        if (!createForumMessageBinding.getErrors().isEmpty()) {
            return false;
        }
        return true;
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
   /* public void addE(UIComponent ui, String mes) {
        /*Данный код выдвет ошибку если поле пустое. Для этого нужно указать в свойстве bindings текстового объекта
        * #{viewScope.CreateRequest.it2}. Создается переменная Richtext it2. уже к ней мы и обращаемся
        * *}/
        String messageText = mes;
        FacesMessage fm = new FacesMessage(messageText);
        fm.setSeverity(FacesMessage.SEVERITY_ERROR);
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(ui.getClientId(), fm);
    }*/
    
    

    public UIComponent find(String id) {
        UIComponent component = null;
        FacesContext facesContext = FacesContext.getCurrentInstance();
        if (facesContext != null) {
            UIComponent root = facesContext.getViewRoot();
            component = findComponent(root, id);
        }
        
        return component;

    }
    public static UIComponent findComponent(UIComponent base, String id)
    {
            if (id.equals(base.getId()))
                return base;

            UIComponent children = null;
            UIComponent result = null;
            Iterator childrens = base.getFacetsAndChildren();
            while (childrens.hasNext() && (result == null)) {
                children = (UIComponent)childrens.next();
                if (id.equals(children.getId())) {
                    result = children;
                    break;
                }
                result = findComponent(children, id);
                if (result != null) {
                    break;
                }
            }
            return result;
        }
    public String sendMessage() {
        if (!forumMessage.isEmpty()) {

 
            putParameterInBinding("ForumCreateInsert", "Text", getForumMessage());
            this.setForumMessage("");
        } else {
            addErrorMessageToRichInputText(itForum, "Поле должно быть заполнено");
        }
        return "";
    }
    // Валидатор текстового поля

    public void it2_validator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        {
            String name = object.toString();
            name=name.trim();
            String expression = "\\d|\\w";
            CharSequence inputStr = name;
            Pattern pattern = Pattern.compile(expression);
            Matcher matcher = pattern.matcher(inputStr);
            String msg = "Сообщение введено неверно";
            if (matcher.matches()) {

            } else {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
            }
        }
    }

    //Создать объект 2 го типа

    public String createTest2Obj() {
        if (!this.test2Message.isEmpty()) {
            putParameterInBinding("Createwithparameters2", "Addchar", getTest2Message().substring(0, 2));
            this.setTest2Message("");
            setDataOnPageChanged(true);
        } else {
            addErrorMessageToRichInputText(itTest2, "Поле должно быть заполнено");
        }
        return "";
    }
    //Создать объект 1 го типа

    public String createTest1Obj() {
        if (!this.test1Message.isEmpty()) {
            putParameterInBinding("Createwithparameters1", "Chooseint", Integer.parseInt(getTest1Message()));
            this.setTest1Message("");
            setDataOnPageChanged(true);
        } else {
            addErrorMessageToRichInputText(itTest1, "Поле должно быть заполнено");
        }
        return "";
    }

    // При удалении последнего элемента на странице создания рапорт скрывает кнопку сохрнаить

    public String deleteTypes() {

        String str = "";
        switch (numOfFacet) {
        case 1:
            str = "Typetest1View_POCHTA1Iterator";
            break;
        case 2:
            str = "Typetest2View2Iterator";
            break;
        }
        if ((new ArrayList<Row>(Arrays.asList(ADFUtils.findIterator(str).getAllRowsInRange()))).size() == 0) {

            setDataOnPageChanged(false);
        }
        return "";
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


    public void setNumOfFacet(int numOfFacet) {
        this.numOfFacet = numOfFacet;
    }

    public int getNumOfFacet() {
        return numOfFacet;
    }


    public void setItTest2(RichInputText itTest2) {
        this.itTest2 = itTest2;
    }

    public RichInputText getItTest2() {
        return itTest2;
    }

    public void setItTest1(RichInputText itTest1) {
        this.itTest1 = itTest1;
    }

    public RichInputText getItTest1() {
        return itTest1;
    }

    public void setItForum(RichInputText itForum) {
        this.itForum = itForum;
    }

    public RichInputText getItForum() {
        return itForum;
    }

    public void setB(RichCommandImageLink b) {
        this.b = b;
    }

    public RichCommandImageLink getB() {
        return b;
    }
}

