package ru.miit.my.cabinet.request.model;

import oracle.adf.model.BindingContext;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

public class ForumMessage {
    
    public ForumMessage() {
        super();
        
    }
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
}
