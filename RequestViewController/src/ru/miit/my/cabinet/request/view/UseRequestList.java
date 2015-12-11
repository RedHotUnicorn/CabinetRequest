package ru.miit.my.cabinet.request.view;

import javax.faces.event.ActionEvent;

import oracle.adf.model.BindingContext;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

public class UseRequestList {
    public UseRequestList() {
        super();
    }

    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    public String crateReq(ActionEvent actionEvent) {
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding1 = bindings.getOperationBinding("CreateReq");
        Object result1 = operationBinding1.execute();
        if (!operationBinding1.getErrors().isEmpty()) {
            return null;
        }
        OperationBinding operationBinding = bindings.getOperationBinding("TitleSet");

        Object result = operationBinding.execute();
        if (!operationBinding.getErrors().isEmpty()) {
            return null;
        }
        return "";
    }

   
}
