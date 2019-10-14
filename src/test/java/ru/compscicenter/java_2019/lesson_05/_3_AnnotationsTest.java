package ru.compscicenter.java_2019.lesson_05;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.junit.Assert.assertEquals;
import static ru.compscicenter.java_2019.Util.__;

@FixMethodOrder(MethodSorters.JVM)
@_3_AnnotationsTest.ClassLevelRuntimeAnnotation
public class _3_AnnotationsTest {

    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.RUNTIME)
    @interface ClassLevelRuntimeAnnotation {
    }

    @Target(ElementType.FIELD)
    @Retention(RetentionPolicy.RUNTIME)
    @interface FieldLevelRuntimeAnnotation {
    }

    @Target(ElementType.FIELD)
    @Retention(RetentionPolicy.SOURCE)
    @interface FieldLevelSourceAnnotation {
    }

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @interface MethodLevelRuntimeAnnotation {
        boolean shouldRun() default false;
    }

    @FieldLevelRuntimeAnnotation
    @FieldLevelSourceAnnotation
    protected Object field1;

    @FieldLevelRuntimeAnnotation
    @FieldLevelSourceAnnotation
    private Object field2;

    @FieldLevelRuntimeAnnotation
    @FieldLevelSourceAnnotation
    Object field3;

    @FieldLevelRuntimeAnnotation
    @FieldLevelSourceAnnotation
    public Object field4;

    @Test
    @MethodLevelRuntimeAnnotation(shouldRun = true)
    public void testsRuntimeAnnotationAvailability() {
        Class<? extends _3_AnnotationsTest> clazz = this.getClass();
        assertEquals(__, clazz.getAnnotations().length);
        assertEquals(__, clazz.getAnnotationsByType(ClassLevelRuntimeAnnotation.class).length);
        int fieldsAnnotationsCount = 0;
        Field[] fields = clazz.getDeclaredFields();
        for (Field f : fields) {
            fieldsAnnotationsCount += f.getAnnotations().length;
        }
        assertEquals(__, fieldsAnnotationsCount);
    }

    @Test
    @MethodLevelRuntimeAnnotation
    public void testsIsSomething() throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Class<?> clazz = Class.forName("ru.compscicenter.java_2019.lesson_05._3_AnnotationsTest");
        Object obj = clazz.getDeclaredConstructor().newInstance();
        Method[] methods = clazz.getMethods();
        int methodsInvocationsCount = 0;
        for (Method m : methods) {
            MethodLevelRuntimeAnnotation annotation = m.getAnnotation(MethodLevelRuntimeAnnotation.class);
            if (annotation != null && annotation.shouldRun()) {
                m.invoke(obj);
                methodsInvocationsCount++;
            }
        }
        assertEquals(__, methodsInvocationsCount);
    }
}
