package com.bulianglin.spring.test.V1;


import com.bulianglin.spring.ioc.BeanDefinition;
import com.bulianglin.spring.ioc.PropertyValue;
import com.bulianglin.spring.ioc.RuntimeBeanReference;
import com.bulianglin.spring.ioc.TypeStringValue;
import com.bulianglin.spring.po.User;
import com.bulianglin.spring.service.UserService;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestIoc {

    private Map<String,BeanDefinition> beanDefinitionMap = new HashMap<>();

    private Map<String,Object> singletonObjects = new HashMap<>();

    @Test
    public void test(){
        //旧代码 , 需要手动设置
       // UserServiceImpl userService = new UserServiceImpl();
       // UserDaoImpl userDao = new UserDaoImpl();
       // BasicDataSource dataSource = new BasicDataSource();
       // dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
       // dataSource.setUrl("jdbc:mysql://127.0.0.1:3306/im_bird?characterEncoding=utf8");
       // dataSource.setUsername("root");
       // dataSource.setPassword("root");
       // userDao.setDataSource(dataSource);
       // userService.setUserDao(userDao);

        UserService userService = (UserService) getBean("userService");

        //使用者
        Map<String,Object> map = new HashMap<>();
        map.put("username","空度日");
        List<User> users = userService.queryUsers(map);
        System.out.println(users);
    }

    @Before
    public void Before(){
        // 获取资源对象三步骤
        //1 BeanDefinition注册流程
        // 1.1 获取对应资源的流对象
        String location = "beans.xml";
        InputStream inputStream = getResourceAsStream(location);
        // 1.2 获取Document文档对象
        Document document = getDocument(inputStream);
        // 1.3 按照一定的语义去解析Document文档对象
        registerBeanDefinitions(document.getRootElement());
        // 1.4 最终解析出来的每个bean标签都封装成BeanDefinition对象

        // 1.5 将解析出来的BeanDefinition对象，注册到Map集合中
    }

    private void registerBeanDefinitions(Element rootElement) {
        rootElement.get
    }

    private Document getDocument(InputStream inputStream){
        try{
            SAXReader saxReader = new SAXReader();
            Document read = saxReader.read(inputStream);
            return read;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    private InputStream getResourceAsStream(String location) {
        return this.getClass().getClassLoader().getResourceAsStream(location);
    }

    private Object getBean(String name) {
        Object bean = this.singletonObjects.get(name);
        if (bean != null){
            return bean;
        }
        BeanDefinition bd = this.beanDefinitionMap.get(name);
        if (bd == null){
            return null;
        }
        if ("singleton".equals(bd.getScope())){
            bean = creatBeanInstance(bd);
            this.singletonObjects.put(name,bean);
        }
        else if("prototype".equals(bd.getScope())){
            bean = creatBeanInstance(bd);
        }
        return bean;
    }

    private Object createBean(BeanDefinition bd){
        // bean的实例化 new
        Object bean = creatBeanInstance(bd);
        // bean的依赖注入 setter
        populateBean(bean,bd);
        // bean的初始化 调用初始化方法，比如aop代理对象
        initializeBean(bean,bd);
        return bean;
    }

    private void initializeBean(Object bean, BeanDefinition bd) {
        //     todo Aware接口的处理 == spring-mvc 有用到,


    //     todo InitializingBean接口的afterPropertiesSet方法的调用

    //     init-method标签属性对应的初始化方法的调用
        invokeInitMethod(bean,bd);

        //     tod初始化之后 aop产生代理对象流程的入口

    }

    private void invokeInitMethod(Object bean, BeanDefinition bd) {
        String initMethod = bd.getInitMethod();
        if (initMethod == null || initMethod.equals("")){
            return;
        }
        try{
            Class<?> clazzType = bd.getClazzType();
            Method declaredMethod = clazzType.getDeclaredMethod(initMethod);
            declaredMethod.invoke(bean);
        }catch (Exception e){
            e.printStackTrace();
        }


    }

    private void populateBean(Object bean, BeanDefinition bd) {
        List<PropertyValue> propertyValues = bd.getPropertyValues();
        for (PropertyValue propertyValue : propertyValues) {
            String name = propertyValue.getName();
            //runtimeBeanReference // typeStringValue
            Object value = propertyValue.getValue();

            // 真正需要依赖注入的值
            Object valueToUse = null;
            valueToUse = resoleValue(value);
            setProperty(bean,name,valueToUse);
        }
    }

    private Object resoleValue(Object value) {
        if (value instanceof RuntimeBeanReference){
            RuntimeBeanReference runtimeBeanReference = (RuntimeBeanReference) value;
            String name = runtimeBeanReference.getRef();
            // todo 此处会发生循环依赖问题
            return getBean(name);
        }
        else if (value instanceof TypeStringValue){
            TypeStringValue typeStringValue = (TypeStringValue) value;
            String stringValueValue = typeStringValue.getValue();
            Class targetType = typeStringValue.getTargetType();
            if (targetType != null){
            //     更具类型来得到他的值
                return handleType(stringValueValue,targetType);
            }
            // todo 此处会发生循环依赖问题
            return stringValueValue;
        }
        return null;
    }

    private Object handleType(String stringValueValue, Class targetType) {
        if (targetType == Integer.class){
            return Integer.parseInt(stringValueValue);
        } else if (targetType == Double.class) {
            return Double.parseDouble(stringValueValue);
        } else if (targetType == String.class) {
            return stringValueValue;
        }
        return null;
    }


    private void setProperty(Object bean, String name, Object valueToUse) {
        try {
            // spring 中是通过属性还是setter方法去依赖注入呢？
            Class<?> aClass = bean.getClass();
            Field declaredField = aClass.getDeclaredField(name);
            declaredField.setAccessible(true);
            declaredField.set(bean,valueToUse);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private Object creatBeanInstance(BeanDefinition bd){

        //todo 静态工厂，示例工程创建

        //构造器创建
        // 获取构造器
        Constructor constructor = getConstructor(bd);

        // 通过反射去创建Bean实例
        Object bean = newObject(constructor,bd);
        return null;
    }

    private Object newObject(Constructor constructor,BeanDefinition bd) {
        try{
            Object bean = constructor.newInstance();
            return bean;
        }catch (Exception e){
            e.printStackTrace();
        }
        return  null;
    }

    private Constructor getConstructor(BeanDefinition bd) {
        try{
            Class<?> clazzType = bd.getClazzType();
            Constructor<?> constructor = clazzType.getDeclaredConstructor();
            return constructor;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }


    //public Constructor get
}



