package org.ferret;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.ferret.annotations.type.AdviceType;
import org.ferret.utils.ReflectUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Map;

@Aspect
@Component
public class Guard  {

    @Autowired
    private GuardMonitorLoader guardMonitorLoader;

    @Pointcut("@annotation(org.ferret.annotations.Prisoner)")
    public void aspect(){}


    @Around("aspect()")
    public void aspect(ProceedingJoinPoint joinPoint){


        if(null != this.guardMonitorLoader && !guardMonitorLoader.getGuards().isEmpty()){

            Map<String, Object> guards = guardMonitorLoader.getGuards();

            Object target = joinPoint.getTarget();

            String simpleName = target.getClass().getSimpleName();
            String name = simpleName.substring(0, 1).toLowerCase() + simpleName.substring(1) +"Guard";

            if(!guards.isEmpty()){

                Object guardObject = guards.get(name);

                Method[] methodsByAnnotation = ReflectUtil.getMethodsByAnnotation(org.ferret.annotations.Guard.class, guardObject.getClass());

                Method beforeMethod = null;

                Method afterMethod = null;

                Method errorMethod = null;

                for(Method method : methodsByAnnotation){

                    ReflectUtil.getAnnotationFromMethod(org.ferret.annotations.Guard.class,method);
                    org.ferret.annotations.Guard annotationInstance = ReflectUtil.getAnnotationInstance(
                            ReflectUtil.getAnnotationFromMethod(org.ferret.annotations.Guard.class, method),
                            org.ferret.annotations.Guard.class);

                    AdviceType adviceType = annotationInstance.value();

                    if(AdviceType.Before == adviceType){
                        beforeMethod = method;
                    }

                    if(AdviceType.After == adviceType){

                        afterMethod = method;
                    }

                    if(AdviceType.Error == adviceType){
                        errorMethod = method;
                    }

                }



                //############################################

                Parameters parameters = new Parameters();

                parameters.setArgs(joinPoint.getArgs());

                if(null != beforeMethod){
                    try {
                        beforeMethod.invoke(guardObject,parameters);
                    }catch (Exception e){
                        throw new RuntimeException(e);
                    }
                }

                try {

                    Object result = joinPoint.proceed();

                    if(null != afterMethod){
                        parameters.setResult(result);

                        try {
                            afterMethod.invoke(guardObject,parameters);
                        }catch (Exception e){
                            throw new RuntimeException(e);
                        }
                    }
                }catch (Throwable e){

                    parameters.setThrowable(e);

                    if(null != errorMethod){
                        try {
                            errorMethod.invoke(guardObject,parameters);
                        }catch (Exception ex){
                            throw new RuntimeException(ex);
                        }

                    }

                }

            }

            // 若添加@Prisoner 注解 咩有实现对应的监控 则该方法不会执行


        }

    }


}
