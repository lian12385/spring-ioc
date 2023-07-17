package com.bulianglin.spring.ioc;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PropertyValue {
    private String name;
    private Object value;
}
