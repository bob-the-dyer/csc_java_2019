package ru.compscicenter.java_2019.lesson_05;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static ru.compscicenter.java_2019.Util.__;

@FixMethodOrder(MethodSorters.JVM)
public class _2_ReflectionTest {

    protected Object field1;
    private Object field2;
    Object field3;
    public Object field4;

    @Test
    public void testsThisIntrospection() {
        Class<? extends _2_ReflectionTest> clazz = this.getClass();
        assertEquals(__, clazz.getName());
        assertEquals(__, clazz.getSimpleName());

        assertEquals(__, clazz.getPackageName());

        assertEquals(__, clazz.getAnnotations().length);

        assertEquals(__, clazz.getConstructors().length);
        assertEquals(__, clazz.getDeclaredConstructors().length);

        assertEquals(__, clazz.getFields().length);
        assertEquals(__, clazz.getDeclaredFields().length);

        assertEquals(__, clazz.getMethods().length);
        assertEquals(__, clazz.getDeclaredMethods().length);

        assertEquals(__, clazz.getClasses().length);
        assertEquals(__, clazz.getDeclaredClasses().length);

        assertEquals(__, clazz.getInterfaces().length);

        assertEquals(__, clazz.getSuperclass().getSimpleName());

        int modifiers = clazz.getModifiers();
        assertEquals(__, Modifier.isAbstract(modifiers));
        assertEquals(__, Modifier.isPublic(modifiers));
        assertEquals(__, Modifier.isStatic(modifiers));
        assertEquals(__, Modifier.isAbstract(modifiers));
        assertEquals(__, Modifier.isInterface(modifiers));
    }

    @Test
    public void testsIsSomething() {
        Class<? extends _2_ReflectionTest> clazz = this.getClass();
        assertEquals(__, clazz.isInterface());
        assertEquals(__, clazz.isAnnotation());
        assertEquals(__, clazz.isAnonymousClass());
        assertEquals(__, clazz.isArray());
        assertEquals(__, clazz.isAssignableFrom(Object.class));
        assertEquals(__, clazz.isAssignableFrom(_2_ReflectionTest.class));
        assertEquals(__, clazz.isInstance(this));
        assertEquals(__, clazz.isEnum());
        assertEquals(__, clazz.isLocalClass());
        assertEquals(__, clazz.isMemberClass());
        assertEquals(__, clazz.isPrimitive());
    }

    @Test
    public void testsObjectCreation() {
        Class<_2_ReflectionTest> clazz = _2_ReflectionTest.class;
        try {
            Constructor<_2_ReflectionTest> constructor = clazz.getConstructor();
            _2_ReflectionTest test = constructor.newInstance();
            test.testsThisIntrospection();
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            fail();
        }
    }

    @Test
    public void testsMethodInvocation() throws InvocationTargetException, IllegalAccessException, ClassNotFoundException, NoSuchMethodException, InstantiationException {
        Class<?> clazz = Class.forName("ru.compscicenter.java_2019.lesson_05._2_ReflectionTest");
        Object obj = clazz.getDeclaredConstructor().newInstance();
        Method[] methods = clazz.getMethods();
        for (Method m : methods) {
            if (!m.getName().contains("testsMethodInvocation")) {
                m.invoke(obj);
            }
        }
    }

}
