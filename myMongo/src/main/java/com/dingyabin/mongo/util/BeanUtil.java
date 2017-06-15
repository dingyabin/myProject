package com.dingyabin.mongo.util;

import com.alibaba.fastjson.JSON;
import org.bson.Document;

import java.lang.reflect.Field;
import java.util.Date;

/**
 * Created by MrDing
 * Date: 2017/5/6.
 * Time:4:25
 */
public class BeanUtil {

    /**
     *   * 把实体bean对象转换成DBObject
     *   * @param bean
     *   * @return
     *   * @throws IllegalArgumentException
     *   * @throws IllegalAccessException
     *   
     */
    public static <T> Document bean2Document(T bean){
        if (bean == null) {
            return null;
        }
         Document dbObject = new Document();
        // 获取对象对应类中的所有属性域
        Field[] fields = bean.getClass().getDeclaredFields();
        for (Field field : fields) {
            String varName = field.getName();
            boolean accessFlag = field.isAccessible();
            if (!accessFlag) {
                field.setAccessible(true);
            }
            Object param = null;
            try {
                param = field.get(bean);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            if (param == null) {
                continue;
            } else if (param instanceof Integer) {//判断变量的类型
                int value = ((Integer) param).intValue();
                dbObject.put(varName, value);
            } else if (param instanceof String) {
                String value = (String) param;
                dbObject.put(varName, value);
            } else if (param instanceof Double) {
                double value = ((Double) param).doubleValue();
                dbObject.put(varName, value);
            } else if (param instanceof Float) {
                float value = ((Float) param).floatValue();
                dbObject.put(varName, value);
            } else if (param instanceof Long) {
                long value = ((Long) param).longValue();
                dbObject.put(varName, value);
            } else if (param instanceof Boolean) {
                boolean value = ((Boolean) param).booleanValue();
                dbObject.put(varName, value);
            } else if (param instanceof Date) {
                Date value = (Date) param;
                dbObject.put(varName, value);
            }else {
                dbObject.put(varName, param);
            }
            field.setAccessible(accessFlag);
        }
        return dbObject;
    }


//         /**
//   * 把DBObject转换成bean对象
//   * @param dbObject
//   * @param bean
//   * @return
//   * @throws IllegalAccessException
//   * @throws InvocationTargetException
//   * @throws NoSuchMethodException
//   */
//         public static <T> T dbObject2Bean(DBObject dbObject, T bean) throws IllegalAccessException,
//   InvocationTargetException, NoSuchMethodException {
//  if (bean == null) {
//   return null;
//  }
//  Field[] fields = bean.getClass().getDeclaredFields();
//  for (Field field : fields) {
//   String varName = field.getName();
//   Object object = dbObject.get(varName);
//   if (object != null) {
//    BeanUtils.setProperty(bean, varName, object);
//   }
//  }
//  return bean;
// }
}
