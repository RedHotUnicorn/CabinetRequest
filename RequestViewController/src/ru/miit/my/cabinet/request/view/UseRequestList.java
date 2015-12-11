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
        OperationBinding createRequestBinding = bindings.getOperationBinding("CreateReq");
        createRequestBinding.execute();
        if (!createRequestBinding.getErrors().isEmpty()) {
            return null;
        }
        OperationBinding titleSetBinding = bindings.getOperationBinding("TitleSet");
        titleSetBinding.execute();
        if (!titleSetBinding.getErrors().isEmpty()) {
            return null;
        }
        return "";
    }

   
}
