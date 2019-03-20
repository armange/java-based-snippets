package br.com.armange.snippet.cdi.impl;

import javax.enterprise.context.RequestScoped;

import br.com.armange.snippet.cdi.api.InstanceWithOneGenericType;

@RequestScoped
public class InstanceWithOneGenericTypeImpl<T> implements InstanceWithOneGenericType<T> {

    private final Class<T> genericClass;

    public InstanceWithOneGenericTypeImpl(final Class<T> genericClass) {
        this.genericClass = genericClass;
    }
    
    @Override
    public Class<T> getGenericClass() {
        return this.genericClass;
    }
}
