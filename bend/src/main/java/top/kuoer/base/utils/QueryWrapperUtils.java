package top.kuoer.base.utils;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import top.kuoer.base.annotation.NotFilter;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

public class QueryWrapperUtils {

    public static <T> void autoLikeIfNotNull(QueryWrapper<T> queryWrapper, T entity) {
        List<Field> fields = Arrays.asList(entity.getClass().getDeclaredFields());
        fields.forEach(field -> {
            field.setAccessible(true);
            try {
                if(!field.isAnnotationPresent(NotFilter.class)) {
                    Object value = field.get(entity);
                    if (value != null) {
                        if (value instanceof String && !((String) value).isEmpty()) {
                            String fieldName = field.getName();
                            TableField tableField = field.getAnnotation(TableField.class);
                            if(null != tableField) {
                                fieldName = tableField.value();
                            }
                            queryWrapper.like(fieldName, (String) value);
                        }
                        // 可以根据需要添加其他类型的判断和处理
                    }
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        });
    }



}
