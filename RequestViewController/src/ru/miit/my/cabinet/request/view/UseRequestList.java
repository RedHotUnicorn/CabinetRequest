package ru.miit.my.cabinet.request.view;

import javax.faces.event.ActionEvent;

import oracle.adf.model.BindingContext;

import oracle.binding.AttributeBinding;
import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.dss.dataView.Title;

import ru.miit.my.cabinet.request.view.utils.JSFUtils;

public class UseRequestList {
    public UseRequestList() {
        super();
    }

    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    public String crateReq(ActionEvent actionEvent) {
        BindingContainer bindings = getBindings();
        
        /*OperationBinding titleSetBinding = bindings.getOperationBinding("TitleSet");
        titleSetBinding.execute();
        if (!titleSetBinding.getErrors().isEmpty()) {
            return null;
        }*/
       // int idType = (Integer)((AttributeBinding)bindings.get("TypeOfNewRequest")).getInputValue();
        
        Integer idkRequest = (Integer)JSFUtils.resolveExpression("#{row.Idrequesttype}");
        String Title = (String)JSFUtils.resolveExpression("#{row.Title}");
        OperationBinding createRequestBinding = bindings.getOperationBinding("CreateReq");
        createRequestBinding.getParamsMap().put("Idrequesttype",idkRequest);
        createRequestBinding.getParamsMap().put("Title",Title);
        createRequestBinding.execute();
        if (!createRequestBinding.getErrors().isEmpty()) {
            return null;
        }
        
        

        
        
        return "";
    }

   
}
