package com.vcampus.dao.utils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * map类型转bean对象
 *
 * @author 刘骐
 * @date 2022/09/01
 *//*
此类的作用是将map转为JavaBean类型的数据
 */
public class mapToBean {
    /**
     * map转bean
     *
     * @param map map对象
     * @param clz 类
     * @return {@link T}
     * @throws Exception 异常
     */
    public static  <T>T map2Bean(Map<String,Object> map , Class<T> clz) throws Exception{
    //new 出一个对象
    T obj=clz.newInstance();
    // 获取person类的BeanInfo对象
    BeanInfo beanInfo = Introspector.getBeanInfo(clz);
    //获取属性描述器
    PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
    for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
        //获取属性名
        String key = propertyDescriptor.getName();
        Object value=map.get(key);
        if(key.equals("class"))
            continue;
        Method writeMethod = propertyDescriptor.getWriteMethod();
        //通过反射来调用Person的定义的setName()方法
        writeMethod.invoke(obj,value);
    }
    return obj;
}


}
