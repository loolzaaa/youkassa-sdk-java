package ru.loolzaaa.youkassa.helper;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ApiHelper {
    public static <T> void checkObjectType(Object object, String fieldName, Class<T> fieldClass, String typeClassName) {
        T currentType;
        try {
            Field typeField = object.getClass().getDeclaredField(fieldName);
            typeField.setAccessible(true);
            currentType = fieldClass.cast(typeField.get(object));

            Class<?> typeClass = Arrays.stream(object.getClass().getClasses())
                    .filter(c -> c.getSimpleName().equals(typeClassName))
                    .findFirst()
                    .orElse(null);
            if (typeClass == null) {
                throw new ClassNotFoundException(typeClassName + " nested class not found in " + object.getClass().getName());
            }

            Field[] typeFields = typeClass.getDeclaredFields();
            List<T> typeFieldValues = new ArrayList<>(typeFields.length);
            for (Field field : typeFields) {
                T fieldValue = fieldClass.cast(field.get(null));
                typeFieldValues.add(fieldValue);
            }
            if (!typeFieldValues.contains(currentType)) {
                throw new IllegalArgumentException("Incorrect type: %s. Available types [%s]: %s"
                        .formatted(currentType, typeClass.getName(), typeFieldValues));
            }
        } catch (ReflectiveOperationException e) {
            throw new IllegalArgumentException(e.getLocalizedMessage());
        }
    }
}
