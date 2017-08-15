package com.topsec.tsm.generic.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.springframework.util.StringUtils;

/**
 * Map转化辅助类
 * Created by yue_tf on 2017/3/28
 */
public class MapUtil {

    public static Map<String, String> reqParamterToMap(HttpServletRequest request) {
        Map<String, String[]> requsetMap = request.getParameterMap();
        Map<String, String> map = new HashMap<String, String>();
        int size = requsetMap.size() - 2;
        int count = 0;
        Iterator<String> iterator = requsetMap.keySet().iterator();
        while (iterator.hasNext()) {
            String key = iterator.next();
            String[] strs = requsetMap.get(key);
            String value = null;
            if (strs.length > 0) {
                value = strs[0];
            }
            if (value.isEmpty()) {
                count++;
            }
            map.put(key, value);
        }
        if (size == count) {
            return null;
        }
        return map;
    }

    public static Object parseMapToObject(String classFullName, Map<String, String> paramMap) throws
            InstantiationException, IllegalAccessException, ClassNotFoundException, NoSuchFieldException,
            SecurityException, NoSuchMethodException, IllegalArgumentException, InvocationTargetException {

        Class<?> clazz = Class.forName(classFullName);
        Object obj = clazz.newInstance();
        Iterator<Entry<String, String>> iterator = paramMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Entry<String, String> entry = iterator.next();
            if (StringUtils.isEmpty(entry.getValue())) {
                continue;
            }
            if ("dataId".equals(entry.getKey())) {
                Field field = clazz.getDeclaredField("id");
                field.setAccessible(true);
                field.set(obj, Integer.valueOf(entry.getValue()));
                continue;
            }
            String getName = "get" + captureName(entry.getKey());
            Method[] methods = clazz.getDeclaredMethods();
            for (Method method : methods) {
                if (method.getName().equals(getName)) {
                    String type = method.getGenericReturnType().toString();
                    if ("class java.lang.String".equals(type)) {
                        String setName = "set" + captureName(entry.getKey());
                        Method setMeth = clazz.getMethod(setName, String.class);
                        setMeth.invoke(obj, entry.getValue());
                    } else if ("class java.lang.Integer".equals(type)) {
                        String setName = "set" + captureName(entry.getKey());
                        Method setMeth = clazz.getMethod(setName, Integer.class);
                        setMeth.invoke(obj, Integer.valueOf(entry.getValue()));
                    } else if ("int".equals(type)) {
                        String setName = "set" + captureName(entry.getKey());
                        Method setMeth = clazz.getMethod(setName, int.class);
                        setMeth.invoke(obj, Integer.valueOf(entry.getValue()));
                    }
                }
            }
        }
        return obj;
    }

    public static String captureName(String name) {
        name = name.substring(0, 1).toUpperCase() + name.substring(1);
        return name;
    }
}
