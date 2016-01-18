package com.org.model.reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 用于反射时,临时存放数据的容器
 * @author Administrator
 *
 */
public class ReflectUtil {
	public static Object ref(String clazzName, String mtd, String key, String value){
		Object aim = null;
		Method method = null;
		try {
			Class<?> clazz = Class.forName(clazzName);
			Constructor<?> cons = clazz.getConstructor();
			method = clazz.getDeclaredMethod(mtd, new Class<?>[]{String.class, String.class});
			aim = cons.newInstance();
			return method.invoke(aim, key, value);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		return aim;
	}
}
