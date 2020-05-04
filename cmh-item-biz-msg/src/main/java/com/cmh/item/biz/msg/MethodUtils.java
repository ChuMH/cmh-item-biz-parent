package com.cmh.item.biz.msg;

import com.google.common.base.Throwables;
import org.aspectj.lang.JoinPoint;
import org.springframework.asm.ClassReader;
import org.springframework.asm.ClassVisitor;
import org.springframework.asm.MethodVisitor;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.springframework.asm.Opcodes.*;

public class MethodUtils {

    private Map<Method, Method> cache = new ConcurrentHashMap<Method, Method>();


    private static final MethodUtils INSTANCE = new MethodUtils();

    public static MethodUtils getInstance() {
        return INSTANCE;
    }

    /**
     * 获取方法注解
     * @param joinPoint
     * @param clazz
     * @return
     */
    public static Object  geMethodAnnotation(JoinPoint joinPoint, Class clazz){
        Method     method     = getMethodFromTarget(joinPoint);
        Annotation annotation = null;
        if(method != null){
            annotation = method.getDeclaredAnnotation(clazz);
        }
        return annotation;
    }

    /**
     * 获取方法
     * @param joinPoint
     * @return
     */
    public static Method getMethodFromTarget(JoinPoint joinPoint) {
        Method method = null;
        if (joinPoint.getSignature() instanceof org.aspectj.lang.reflect.MethodSignature) {
            org.aspectj.lang.reflect.MethodSignature signature = (org.aspectj.lang.reflect.MethodSignature) joinPoint.getSignature();
            method = getDeclaredMethod(joinPoint.getTarget().getClass(), signature.getName(),
                    getParameterTypes(joinPoint));
            method.setAccessible(true);
        }
        return method;
    }

    /**
     * 获取参数
     * @param joinPoint
     * @return
     */
    public static Class[] getParameterTypes(JoinPoint joinPoint) {
        org.aspectj.lang.reflect.MethodSignature signature = (org.aspectj.lang.reflect.MethodSignature) joinPoint.getSignature();
        Method                                   method    = signature.getMethod();
        return method.getParameterTypes();
    }
    /**
     * Gets declared method from specified type by mame and parameters types.
     *
     * @param type           the type
     * @param methodName     the name of the method
     * @param parameterTypes the parameter array
     * @return a {@link Method} object or null if method doesn't exist
     */
    public static Method getDeclaredMethod(Class<?> type, String methodName, Class<?>... parameterTypes) {
        Method method = null;
        try {
            method = type.getDeclaredMethod(methodName, parameterTypes);
            if(method.isBridge()){
                method = MethodUtils.getInstance().unbride(method, type);
            }
        } catch (NoSuchMethodException e) {
            Class<?> superclass = type.getSuperclass();
            if (superclass != null) {
                method = getDeclaredMethod(superclass, methodName, parameterTypes);
            }
        } catch (ClassNotFoundException e) {
            Throwables.propagate(e);
        } catch (IOException e) {
            Throwables.propagate(e);
        }
        return method;
    }

    private static int getParameterCount(String desc) {
        return parseParams(desc).length;
    }

    private static String[] parseParams(String desc) {
        String params = desc.split("\\)")[0].replace("(", "");
        if (params.length() == 0) {
            return new String[0];
        }
        return params.split(";");
    }

    /**
     * Finds generic method for the given bridge method.
     *
     * @param bridgeMethod the bridge method
     * @param aClass       the type where the bridge method is declared
     * @return generic method
     * @throws IOException
     * @throws NoSuchMethodException
     * @throws ClassNotFoundException
     */
    public  Method unbride(final Method bridgeMethod, Class<?> aClass) throws IOException, NoSuchMethodException, ClassNotFoundException {
        if (bridgeMethod.isBridge() && bridgeMethod.isSynthetic()) {
            if (cache.containsKey(bridgeMethod)) {
                return cache.get(bridgeMethod);
            }
            ClassReader                          classReader     = new ClassReader(aClass.getName());
            final MethodSignature methodSignature = new MethodSignature();
            classReader.accept(new ClassVisitor(ASM5) {
                @Override
                public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
                    boolean bridge = (access & ACC_BRIDGE) != 0 && (access & ACC_SYNTHETIC) != 0;
                    if (bridge && bridgeMethod.getName().equals(name) && getParameterCount(desc) == bridgeMethod.getParameterTypes().length) {
                        return new MethodFinder(methodSignature);
                    }
                    return super.visitMethod(access, name, desc, signature, exceptions);
                }
            }, 0);
            Method method = aClass.getDeclaredMethod(methodSignature.name, methodSignature.getParameterTypes());
            cache.put(bridgeMethod, method);
            return method;
        } else {
            return bridgeMethod;
        }
    }

    private static class MethodSignature {
        String name;
        String desc;

        public Class<?>[] getParameterTypes() throws ClassNotFoundException {
            if (desc == null) {
                return new Class[0];
            }
            String[] params = parseParams(desc);
            Class<?>[] parameterTypes = new Class[params.length];

            for (int i = 0; i < params.length; i++) {
                String arg = params[i].substring(1).replace("/", ".");
                parameterTypes[i] = Class.forName(arg);
            }
            return parameterTypes;
        }
    }

    private static class MethodFinder extends MethodVisitor {
        private MethodSignature methodSignature;

        public MethodFinder(MethodSignature methodSignature) {
            super(ASM5);
            this.methodSignature = methodSignature;
        }

        @Override
        public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf) {
            methodSignature.name = name;
            methodSignature.desc = desc;
            super.visitMethodInsn(opcode, owner, name, desc, itf);
        }
    }
}