package com.love.common.util;

import sun.reflect.ConstructorAccessor;
import sun.reflect.FieldAccessor;
import sun.reflect.ReflectionFactory;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class DynamicEnumUtil {

    private static final ReflectionFactory REFLECTION_FACTORY = ReflectionFactory.getReflectionFactory();

    private static <T extends Enum<?>> void sanityChecks(Class<T> enumClass, String enumName) {
        if (!Enum.class.isAssignableFrom(enumClass)) {
            throw new RuntimeException(enumClass + " is not a enum class");
        }

        if (enumName == null || enumName.trim().length() == 0) {
            throw new RuntimeException("name cannot be null");
        }
    }

    @SuppressWarnings("all")
    public static <T extends Enum<?>> void addEnum(Class<T> enumClass, String enumName, Object... params) {
        sanityChecks(enumClass, enumName);

        Field valuesField = null;
        Field[] fields = enumClass.getDeclaredFields();
        List<Class<?>> paramTypes = new LinkedList<>();
        for (Field field : fields) {
            if (field.isEnumConstant() && field.getName().equals(enumName)) {
                return;
            }

            if (field.isSynthetic()) {
                valuesField = field;
            }

            if (!field.isSynthetic() && !field.isEnumConstant()) {
                paramTypes.add(field.getType());
            }
        }

        if (valuesField == null) {
            throw new RuntimeException("can not find values field");
        }

        AccessibleObject.setAccessible(new Field[]{valuesField}, true);
        try {
            T[] oldValues = ( T[] ) valuesField.get(enumClass);
            List<T> values = new ArrayList<>(Arrays.asList(oldValues));
            values.add(makeEnum(enumClass, enumName, values.size(), paramTypes.toArray(new Class[0]), params));
            setFailsafeFieldValue(valuesField, null, values.toArray(( T[] ) Array.newInstance(enumClass, 0)));
            cleanEnumCache(enumClass);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }


    private static <T> T makeEnum(Class<T> enumClass, String enumName, int ordinal, Class<?>[] additionalTypes, Object[] additionalValues) throws Exception {
        Object[] params = new Object[additionalValues.length + 2];
        params[0] = enumName;
        params[1] = ordinal;
        System.arraycopy(additionalValues, 0, params, 2, additionalValues.length);
        return enumClass.cast(getConstructorAccessor(enumClass, additionalTypes).newInstance(params));
    }

    private static ConstructorAccessor getConstructorAccessor(Class<?> enumClass, Class<?>[] additionalParameterTypes) throws NoSuchMethodException {
        Class<?>[] parameterTypes = new Class[additionalParameterTypes.length + 2];
        parameterTypes[0] = String.class;
        parameterTypes[1] = int.class;
        System.arraycopy(additionalParameterTypes, 0, parameterTypes, 2, additionalParameterTypes.length);
        return REFLECTION_FACTORY.newConstructorAccessor(enumClass.getDeclaredConstructor(parameterTypes));
    }

    private static void setFailsafeFieldValue(Field field, Object target, Object value) throws NoSuchFieldException, IllegalAccessException {
        field.setAccessible(true);
        Field modifiersField = Field.class.getDeclaredField("modifiers");
        modifiersField.setAccessible(true);
        int modifiers = modifiersField.getInt(field);
        modifiers &= ~Modifier.FINAL;
        modifiersField.setInt(field, modifiers);
        FieldAccessor fieldAccessor = REFLECTION_FACTORY.newFieldAccessor(field, false);
        fieldAccessor.set(target, value);
    }

    private static void cleanEnumCache(Class<?> enumClass) throws NoSuchFieldException, IllegalAccessException {
        blankField(enumClass, "enumConstantDirectory");
        blankField(enumClass, "enumConstants");
    }

    private static void blankField(Class<?> enumClass, String fieldName) throws NoSuchFieldException, IllegalAccessException {
        for (Field field : Class.class.getDeclaredFields()) {
            if (field.getName().contains(fieldName)) {
                AccessibleObject.setAccessible(new Field[]{field}, true);
                setFailsafeFieldValue(field, enumClass, null);
                break;
            }
        }
    }
}
