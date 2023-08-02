package com.bulianglin.springioc.strategyenumif;

public enum DayEnum {


    //指向内部枚举的同一个属性即可执行相同重复功能
    Monday("星期一", Type.ENGLISH),
    Tuesday("星期二", Type.ENGLISH),
    Wednesday("星期三", Type.ENGLISH),
    Thursday("星期四", Type.CHINESE);

    private final Type type;
    private final String day;

    DayEnum(String day,Type type) {
        this.type = type;
        this.day = day;
    }
    String toDo(){
        return type.toDo();
    }


    //方式1,单一if-else
    // Monday {
    //     @Override
    //     public String toDo() {
    //         // ......省略复杂语句
    //         return "今天上英语课";
    //     }
    // },
    // Tuesday {
    //     @Override
    //     public String toDo() {
    //         // ......省略复杂语句
    //         return "今天上语文课";
    //     }
    // },
    // Wednesday {
    //     @Override
    //     public String toDo() {
    //         // ......省略复杂语句
    //         return "今天上数学课";
    //     }
    // },
    // Thursday {
    //     @Override
    //     public String toDo() {
    //         // ......省略复杂语句
    //         return "今天上音乐课";
    //     }
    // };

    // 内部枚举
    private enum Type{
        ENGLISH{
            @Override
            public String toDo() {
                return "today is English course!";
            }
        },
        CHINESE{
            @Override
            public String toDo() {
                return "today is Chinese course";
            }
        };
        public abstract String toDo();
    }

}