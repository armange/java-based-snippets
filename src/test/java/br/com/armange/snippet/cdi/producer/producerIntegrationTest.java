package br.com.armange.snippet.cdi.producer;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Instance;
import javax.enterprise.inject.spi.CDI;
import javax.enterprise.util.TypeLiteral;

import org.apache.deltaspike.cdise.api.CdiContainer;
import org.apache.deltaspike.cdise.api.CdiContainerLoader;
import org.apache.deltaspike.cdise.api.ContextControl;
import org.assertj.core.api.SoftAssertions;
import org.junit.Test;

import br.com.armange.snippet.cdi.api.InstanceWithOneGenericType;
import br.com.armange.snippet.cdi.entity.EntityA;
import br.com.armange.snippet.cdi.entity.EntityB;

public class producerIntegrationTest {

    @Test
    public void itMustProduceInstanceWithGenericType() {
        final CdiContainer cdiContainer = CdiContainerLoader.getCdiContainer();
        
        cdiContainer.boot();

        final ContextControl contextControl = cdiContainer.getContextControl();
        
        contextControl.startContext(ApplicationScoped.class);
        contextControl.startContext(RequestScoped.class);

        testInstanceWithTypeA();
        
        contextControl.stopContext(RequestScoped.class);
        contextControl.startContext(RequestScoped.class);
        
        testInstanceWithTypeB();
        
        cdiContainer.shutdown();
    }

    private void testInstanceWithTypeB() {
        final TypeLiteral<InstanceWithOneGenericType<EntityB>> typeLiteralB = getInstanceTypedWithB();
        final Instance<InstanceWithOneGenericType<EntityB>> instanceB = CDI.current().select(typeLiteralB);
        
        SoftAssertions softAssertions = new SoftAssertions();
        
        softAssertions.assertThat(instanceB.isAmbiguous()).isFalse();
        softAssertions.assertThat(instanceB.isUnsatisfied()).isFalse();
        
        final InstanceWithOneGenericType<EntityB> instanceWithOneGenericTypeB = instanceB.get();
        
        softAssertions.assertThat(instanceWithOneGenericTypeB.getGenericClass()).isEqualTo(EntityB.class);
        
        softAssertions.assertAll();
    }

    private void testInstanceWithTypeA() {
        final TypeLiteral<InstanceWithOneGenericType<EntityA>> typeLiteralA = getInstanceTypedWithA();
        final Instance<InstanceWithOneGenericType<EntityA>> instanceA = CDI.current().select(typeLiteralA);
        
        SoftAssertions softAssertions = new SoftAssertions();
        
        softAssertions.assertThat(instanceA.isAmbiguous()).isFalse();
        softAssertions.assertThat(instanceA.isUnsatisfied()).isFalse();
        
        final InstanceWithOneGenericType<EntityA> instanceWithOneGenericTypeA = instanceA.get();
        
        softAssertions.assertThat(instanceWithOneGenericTypeA.getGenericClass()).isEqualTo(EntityA.class);
        
        softAssertions.assertAll();
    }

    private TypeLiteral<InstanceWithOneGenericType<EntityA>> getInstanceTypedWithA() {
        return new TypeLiteral<InstanceWithOneGenericType<EntityA>>() {
            private static final long serialVersionUID = 1L;
        };
    }
    
    private TypeLiteral<InstanceWithOneGenericType<EntityB>> getInstanceTypedWithB() {
        return new TypeLiteral<InstanceWithOneGenericType<EntityB>>() {
            private static final long serialVersionUID = 1L;
        };
    }
}
