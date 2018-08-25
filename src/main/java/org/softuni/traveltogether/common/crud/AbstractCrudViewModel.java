package org.softuni.traveltogether.common.crud;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public abstract class AbstractCrudViewModel<T> {
    protected String targetUrl;
    protected T bindingModel;

    public AbstractCrudViewModel(T bindingModel) {
        this.bindingModel = bindingModel;
    }

    public AbstractCrudViewModel(String targetUrl, T bindingModel) {
        this.targetUrl = targetUrl;
        this.bindingModel = bindingModel;
    }

    public String getAction() {
        if(this.targetUrl != null) {
            String[] splitted = this.targetUrl.split("/");
            if(splitted.length > 0) {
                return splitted[splitted.length-1];
            }
            return null;
        }
        return null;
    }

    public String getTargetUrl() {
        return targetUrl;
    }

    public void setTargetUrl(String targetUrl) {
        this.targetUrl = targetUrl;
    }

    public @NotNull @Valid T getBindingModel() {
        return this.bindingModel;
    }

    public void setBindingModel(T bindingModel) {
        this.bindingModel = bindingModel;
    }
}
