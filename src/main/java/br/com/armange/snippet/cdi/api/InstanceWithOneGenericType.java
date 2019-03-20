package br.com.armange.snippet.cdi.api;

public interface InstanceWithOneGenericType<T> {

    Class<T> getGenericClass();
}
