package com.sky.ioc;

import android.view.View;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class InjectUtils {
    private static String TAG = "InjectUtils";
    public static void init(Object context) {
        injectLayout(context);
        injectView(context);
        injectClick(context);
    }

    private static void injectClick(Object context) {
        Class<?> clazz = context.getClass();
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            Annotation[] annotations = method.getAnnotations();
            for (Annotation annotation : annotations) {
                Class<?> type = annotation.annotationType();
                EventBase eventBase = type.getAnnotation(EventBase.class);
                if(eventBase==null){
                    continue;
                }
                String listenerSetter = eventBase.listenerSetter();
                Class<?> listenerType = eventBase.listenerType();
                String callbackMethod = eventBase.CallbackMethod();
                try {
                    Method value = type.getDeclaredMethod("value");
                    int[] viewId = (int[]) value.invoke(annotation);
                    for (int id : viewId) {
                        Method findViewById = clazz.getMethod("findViewById", int.class);
                        View view = (View) findViewById.invoke(context, id);
                        if(view ==null){
                            continue;
                        }
                        ListenerInvocationHandler listenerInvcoationHandler =
                                new ListenerInvocationHandler(context,method);
                        Object proxy = Proxy.newProxyInstance(listenerType.getClassLoader(),new Class[]{listenerType},listenerInvcoationHandler);
                        Method onClickMethod = view.getClass().getMethod(listenerSetter, listenerType);
                        onClickMethod.invoke(view,proxy);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }


        }

    }


    /**
     * view inject
     * @param context
     */
    private static void injectView(Object context) {
        Class<?> clazz = context.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
//            Log.d(TAG, "injectView: "+field.isAccessible());
            ViewInject viewInject = field.getAnnotation(ViewInject.class);
            if(viewInject!=null){
                int viewId = viewInject.value();
                try {
                    Method method = clazz.getMethod("findViewById", int.class);
                    View view =(View) method.invoke(context, viewId);
                    field.setAccessible(true);
                    field.set(context,view);
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        }

    }

    /**
     * layout inject
     * @param context
     */
    private static void injectLayout(Object context) {
        int layoutId = 0;
        Class<?> clazz = context.getClass();
        ConentView conentView = clazz.getAnnotation(ConentView.class);
        if(conentView!=null){
            layoutId = conentView.value();
            try {
                Method setContentView = context.getClass().getMethod("setContentView", int.class);
                setContentView.invoke(context,layoutId);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
