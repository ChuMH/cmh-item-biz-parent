package test.reflection;

import com.cmh.item.biz.service.impl.neo4j.NeoPersonQueryServiceImpl;

import java.lang.reflect.Method;

/**
 * @author：初明昊
 * @data：2020/04/10
 * @description：类反射demo
 */
public class ClassReflectionTest {
    public static void main(String[] args) {
        //类反射获取类实例
        Class<NeoPersonQueryServiceImpl> clazz = NeoPersonQueryServiceImpl.class;
        Method[] declardsMethod = clazz.getDeclaredMethods();
        for(Method method : declardsMethod){
            try {
                Object o = method.getReturnType().newInstance();
                if(o instanceof String){
                    System.out.println("gl成功！");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
