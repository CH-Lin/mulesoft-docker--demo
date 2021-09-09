package com.employee.common;

import org.hibernate.jpa.HibernatePersistenceProvider;
import org.hibernate.jpa.boot.internal.ParsedPersistenceXmlDescriptor;
import org.hibernate.jpa.boot.internal.PersistenceXmlParser;
import org.hibernate.jpa.boot.spi.EntityManagerFactoryBuilder;
import org.hibernate.jpa.boot.spi.PersistenceUnitDescriptor;
import org.hibernate.jpa.boot.spi.ProviderChecker;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;

import javax.persistence.Entity;
import javax.persistence.MappedSuperclass;
import javax.persistence.PersistenceException;
import javax.persistence.spi.PersistenceProvider;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HibernateDynamicPersistenceProvider extends HibernatePersistenceProvider implements PersistenceProvider {

    private final List<Class<? extends Annotation>> entityAnnotationClasses;

    public HibernateDynamicPersistenceProvider() {
        entityAnnotationClasses = listEntityAnnotationClasses();
    }

    private static List<Class<? extends Annotation>> listEntityAnnotationClasses() {
        final List<Class<? extends Annotation>> result = new ArrayList<>(2);
        result.add(MappedSuperclass.class);
        result.add(Entity.class);
        return result;
    }

    @Override
    @SuppressWarnings("rawtypes")
    protected EntityManagerFactoryBuilder getEntityManagerFactoryBuilder(PersistenceUnitDescriptor persistenceUnitDescriptor, Map integration, ClassLoader providedClassLoader) {
        if (persistenceUnitDescriptor instanceof ParsedPersistenceXmlDescriptor) {
            ParsedPersistenceXmlDescriptor descriptor = (ParsedPersistenceXmlDescriptor) persistenceUnitDescriptor;
            final Reflections reflections = new Reflections(new ConfigurationBuilder()
                    .filterInputsBy(new FilterBuilder().include(FilterBuilder.prefix("com.employee")))
                    .setUrls(ClasspathHelper.forPackage("com.employee")).setScanners(new TypeAnnotationsScanner()).
                    setScanners(new TypeAnnotationsScanner(), new SubTypesScanner()));

            for (final Class<? extends Annotation> annotationClass : entityAnnotationClasses) {
                for (final Class<?> annotatedClass : reflections.getTypesAnnotatedWith(annotationClass)) {
                    descriptor.addClasses(annotatedClass.getName());
                }
            }
        }
        return super.getEntityManagerFactoryBuilder(persistenceUnitDescriptor, integration, providedClassLoader);
    }

    protected EntityManagerFactoryBuilder getEntityManagerFactoryBuilderOrNull(String persistenceUnitName, Map properties) {
        return getEntityManagerFactoryBuilderOrNullOverride(persistenceUnitName, properties, null);
    }

    protected EntityManagerFactoryBuilder getEntityManagerFactoryBuilderOrNull(String persistenceUnitName, Map properties, ClassLoader providedClassLoader) {
        return getEntityManagerFactoryBuilderOrNullOverride(persistenceUnitName, properties, providedClassLoader);
    }

    protected EntityManagerFactoryBuilder getEntityManagerFactoryBuilderOrNullOverride(String persistenceUnitName, Map properties, ClassLoader providedClassLoader) {
        final Map<?, ?> integration = wrap(properties);
        final List<ParsedPersistenceXmlDescriptor> units;
        try {
            units = PersistenceXmlParser.locatePersistenceUnits(integration);
        } catch (Exception e) {
            System.out.println("Unable to locate persistence units: " + e);
            throw new PersistenceException("Unable to locate persistence units", e);
        }

        if (persistenceUnitName == null && units.size() > 1) {
            throw new PersistenceException("No name provided and multiple persistence units found");
        }

        for (ParsedPersistenceXmlDescriptor persistenceUnit : units) {
            final boolean matches = persistenceUnitName == null || persistenceUnit.getName().equals(persistenceUnitName);
            if (!matches) {
                continue;
            }

            String extractRequestedProviderName = ProviderChecker.extractRequestedProviderName(persistenceUnit, integration);

            if (!ProviderChecker.isProvider(persistenceUnit, properties) && !(this.getClass().getName().equals(extractRequestedProviderName))) {
                System.out.println("Excluding from consideration due to provider mis-match");
                continue;
            }

            return getEntityManagerFactoryBuilder(persistenceUnit, integration, providedClassLoader);
        }

        System.out.println("Found no matching persistence units");
        return null;
    }

}
