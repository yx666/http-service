package com.cyx.aopdemo.aop;

import com.alibaba.fastjson.JSON;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;

@Component
@Aspect
public class HttpServiceAop {

    @Pointcut("execution(* com.cyx.aopdemo.service.impl.*.*(..))")
    private void replaceHttpPoint(){}

    @Pointcut(value = "@within(com.cyx.aopdemo.annotation.FirmHttpService) || @annotation(com.cyx.aopdemo.annotation.FirmHttpService)")
    private void pc(){}

    @Around("pc()")
    public <T>T test(ProceedingJoinPoint joinPoint){
        System.out.println("before");
        try {

            String classType = joinPoint.getTarget().getClass().getName();
            Class<?> clazz = Class.forName(classType);
            String clazzName = clazz.getName();
            String methodName = joinPoint.getSignature().getName(); //获取方法名称

            // url
            //根据clazzName，获取包名+controller名  包名和url可能存在映射 com.cyx.cash-->/cash
            //根据methodName 获取方法名
            //url= 前缀 + 包名 + controller名
            // preUrl + /cash+ "/" + methodName
            String[] split = StringUtils.split(clazzName, ".");

            //参数
            HashMap<String, Class> map = new HashMap<String, Class>() {
                {
                    put("java.lang.Integer", int.class);
                    put("java.lang.Double", double.class);
                    put("java.lang.Float", float.class);
                    put("java.lang.Long", long.class);
                    put("java.lang.Short", short.class);
                    put("java.lang.Boolean", boolean.class);
                    put("java.lang.Char", char.class);
                }
            };
            Object[] args = joinPoint.getArgs();
            Class<?>[] classes = new Class[args.length];
            for (int k = 0; k < args.length; k++) {
                if (!args[k].getClass().isPrimitive()) {
                    // 获取的是封装类型而不是基础类型
                    String result = args[k].getClass().getName();
                    Class s = map.get(result);
                    classes[k] = s == null ? args[k].getClass() : s;
                }
            }

            ParameterNameDiscoverer pnd = new DefaultParameterNameDiscoverer();
            // 获取指定的方法，第二个参数可以不传，但是为了防止有重载的现象，还是需要传入参数的类型
            Method method = Class.forName(classType).getMethod(methodName, classes);
            // 参数名
            String[] parameterNames = pnd.getParameterNames(method);
            // 通过map封装参数和参数值
            HashMap<String, Object> paramMap = new HashMap();
            for (int i = 0; i < parameterNames.length; i++) {
                paramMap.put(parameterNames[i], args[i]);
            }


            //返回值类型
            //Signature signature =  joinPoint.getSignature();
            //Class returnType = ((MethodSignature) signature).getReturnType();
            //需要判断各种类型，list，map，void，复杂对象等，还有包装类和基本类型

            Type type = method.getGenericReturnType();// 获取返回值类型
            //这个type已经带上了泛型，可以直接给fastjson使用
          /*  if (type instanceof ParameterizedType) { // 判断获取的类型是否是参数类型
                System.out.println(type);
                Type[] typesto = ((ParameterizedType) type).getActualTypeArguments();// 强制转型为带参数的泛型类型，
                // getActualTypeArguments()方法获取类型中的实际类型，如map<String,Integer>中的
                // String，integer因为可能是多个，所以使用数组
                for (Type type2 : typesto) {
                    System.out.println("泛型类型" + type2);
                }
            }*/

            if(type.equals(void.class)){
                //为空可以直接返回null，反正调用方永不倒
                //httpclient.post(param,url);
                return null;
            }else{
                //return httpclient.post(param,url,type);
            }

            System.out.println(1);

            //调用http工具发起请求，获取data
            //url + 请求参数map + 返回值类型

            Object aaa = JSON.parseObject("aaa", type);
            return (T) aaa;



            //这里就不再调用原方法，直接重写
            //joinPoint.proceed();


        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

        return null;

    }
}
