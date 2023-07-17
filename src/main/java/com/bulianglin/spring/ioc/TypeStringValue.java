package com.bulianglin.spring.ioc;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TypeStringValue {
    private String value;
    private Class<?> targetType;

    public TypeStringValue(String value){
        this.value = value;
    }
}
