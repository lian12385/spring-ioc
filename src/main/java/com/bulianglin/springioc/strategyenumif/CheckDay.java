package com.bulianglin.springioc.strategyenumif;

public class CheckDay {
    public String day(DayEnum dayEnum) {
        return dayEnum.toDo();
    }
    public String dayEnum(DayEnum dayEnum) {
        return dayEnum.toDo();
    }
}
