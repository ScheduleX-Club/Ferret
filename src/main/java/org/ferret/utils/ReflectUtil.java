package org.ferret.utils;


import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class ReflectUtil {

    /**
     * 获取类注解
     * @param antClass
     * @param clazz
     * @return
     */
    public static Annotation getTypeAnnotationByName(Class<? extends Annotation> antClass,Class clazz){

        if(null == clazz || null == antClass){
            throw new RuntimeException("Class antClass or clazz is NULL");
        }

        if(clazz.isAnnotationPresent(antClass)){
            Annotation annotation = clazz.getAnnotation(antClass);
            return annotation;
        }
        return null;
    }

    /**
     * 获取所有实现了某个注解的class
     * @param antClass
     * @param classes
     * @return
     */
    public static List<Class> getClassesByAnnotation(Class<? extends Annotation> antClass,List<Class> classes){
        List<Class> clsList = new ArrayList<Class>();
        if(null != classes && !classes.isEmpty() && null != antClass){

            for(Class clazz : classes){
                if(clazz.isAnnotationPresent(antClass)){
                    clsList.add(clazz);
                }
            }
        }
        return clsList;
    }

    /**
     * 获取所有实现了某个注解的obj
     * @param antClass
     * @param ins
     * @return
     */
    public static List<Object> getInstancesByAnnotation(Class<? extends Annotation> antClass,List<Object> ins){
        List<Object> objList = new ArrayList<Object>();
        if(null != ins && !ins.isEmpty() && null != antClass){

            for(Object obj : ins){
                if(obj.getClass().isAnnotationPresent(antClass)){
                    objList.add(obj);
                }
            }
        }
        return objList;
    }



    /**
     * 获取类中所有实现了某个注解的方法
     * @param antClass
     * @param clazz
     * @return
     */
    public static Method[] getMethodsByAnnotation(Class<? extends Annotation> antClass,Class clazz){

        if(null == clazz || null == antClass){
            throw new RuntimeException("Class antClass or clazz is NULL");
        }


        Method[] methods = clazz.getDeclaredMethods();
        int lengthArr = methods.length;
        Method[] retMethods = new Method[lengthArr];
        int step = 0;
        for(Method method : methods){
            if(method.isAnnotationPresent(antClass)){
                retMethods[step] = method;
                step ++;
            }
        }
        if(step > 0){
            Method[] acatArray = new Method[step];
            System.arraycopy(retMethods,0,acatArray,0,step);
            return  acatArray;
        }
        return null;
    }

    /**
     * 从某个方法上获取给定的注解
     * @param antClass
     * @param method
     * @return
     */
    public static Annotation getAnnotationFromMethod(Class<? extends Annotation> antClass,Method method){

        if(method.isAnnotationPresent(antClass)){
            return  method.getAnnotation(antClass);
        }
        return null;
    }

    /**
     * 注解类强制转换
     * @param annotation
     * @param antClass
     * @param <T>
     * @return
     */
    public static <T> T getAnnotationInstance(Annotation annotation , Class<T> antClass){
        return  (T)annotation;
    }

    /**
     * 获取class的实例
     * @param classes
     * @return
     */
    public static List<Object> getInstanceFromClass(List<Class> classes){
        List<Object> objectList = new ArrayList<Object>();
        for(Class clazz : classes){
            try {
                objectList.add(clazz.newInstance());
            }catch (Exception e){
                throw new RuntimeException(e);
            }
        }
        return objectList;
    }

}
