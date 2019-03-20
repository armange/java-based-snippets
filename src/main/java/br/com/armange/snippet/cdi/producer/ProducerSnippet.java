package br.com.armange.snippet.cdi.producer;

import java.lang.reflect.ParameterizedType;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

import br.com.armange.snippet.cdi.impl.InstanceWithOneGenericTypeImpl;

@ApplicationScoped
public class ProducerSnippet {

    @Produces
    public <T> InstanceWithOneGenericTypeImpl<T> produceWithGenericTypeFromInjectionPoint(final InjectionPoint injectionPoint) {
        final ParameterizedType type = (ParameterizedType) injectionPoint.getType();
        
        @SuppressWarnings("unchecked")
        final Class<T> classe = (Class<T>) type.getActualTypeArguments()[0];
        
        return new InstanceWithOneGenericTypeImpl<>(classe);
    }
}
