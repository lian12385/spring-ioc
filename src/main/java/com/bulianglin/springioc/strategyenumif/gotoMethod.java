package com.bulianglin.springioc.strategyenumif;

public class gotoMethod {
    //策略枚举判断
    public static String getToDo(String day){
        CheckDay checkDay=new CheckDay();
        return checkDay.day(DayEnum.valueOf(day));
    }

    public static String getToDoEn(String day){
        CheckDay checkDay=new CheckDay();
        return checkDay.day(DayEnum.valueOf(day));
    }


    public static void main(String[] args) {
        // System.out.println(getToDo("Monday"));
        System.out.println(getToDo("Monday"));
    }
}
