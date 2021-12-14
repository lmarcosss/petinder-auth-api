package org.ifrs.view;

public abstract class BaseView<T> {
    public abstract BaseView<T> mapFromEntity(T model); 
}
