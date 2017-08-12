package com.fh.util;

import org.codehaus.jackson.map.util.JSONPObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class BeanUtil {

	public static Object mergeObj(Object getObj, Object setObj)
			throws IntrospectionException, IllegalArgumentException,
			IllegalAccessException, InvocationTargetException {

		Map<String, Object> map = new HashMap<String, Object>();

		//读数据
		Class getClazz = getObj.getClass(); //获取对象类型
		Field[] getFields = getClazz.getDeclaredFields();// 获得属性
		for (Field field : getFields) {
			PropertyDescriptor pd = new PropertyDescriptor(field.getName(), getClazz);
			Method getMethod = pd.getReadMethod();// 获得get方法
			Object o = getMethod.invoke(getObj);// 执行get方法返回一个Object
			if(null != o){
				map.put(getMethod.getName().split("get")[1], o);
			}
		}

		// 写数据
		Class setClazz = setObj.getClass();
		Field[] setFields = setClazz.getDeclaredFields();// 获得属性

		Iterator it=map.keySet().iterator();
		String key;
		Object value;
		while(it.hasNext()){
			key=(String) it.next();
			value=map.get(key);
			for (Field f : setFields) {
				PropertyDescriptor pd = new PropertyDescriptor(f.getName(), setClazz);
				Method setMethod = pd.getWriteMethod();// 获得set方法
				if(setMethod.getName().split("set")[1].equals(key)){
					setMethod.invoke(setObj,value);// 执行get方法返回一个Object
				}
			}
		}

		return setObj;
	}
}