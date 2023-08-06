package com.bulianglin.spring.ioc;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class BeanDefinition {
    private String clazzName;
    private Class<?> clazzType;
    private String beanName;
    private String initMethod;
    private String scope;
    private List<PropertyValue> propertyValues = new ArrayList<>();

    private static final String SCOPE_SINGLETON = "singleton";
    private static final String SCOPE_PROTOTYPE = "prototype";

    public BeanDefinition(String clazzName, String beanName) {
        this.clazzName = clazzName;
        this.beanName = beanName;
    }
    public void addPropertyValue(PropertyValue propertyValue){
        this.propertyValues.add(propertyValue);
    }

}
