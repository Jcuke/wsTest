package com.zzb.mongo.base;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.util.List;

public enum QueryType {
    EQUALS {  
        @Override  
        public Criteria buildCriteria(QueryField queryFieldAnnotation, Field field, Object value) {
            if (check(queryFieldAnnotation, field, value)) {  
                String queryField = getQueryFieldName(queryFieldAnnotation, field);
                //todo 待优化
                if(value instanceof Number){
                    return Criteria.where(queryField).is(value);
                } else {
                    return Criteria.where(queryField).is(value.toString());
                }

            }  
            return new Criteria();
        }  
    },  
    LIKE {  
        @Override  
        public Criteria buildCriteria(QueryField queryFieldAnnotation, Field field, Object value) {
            if (check(queryFieldAnnotation, field, value)) {  
                String queryField = getQueryFieldName(queryFieldAnnotation, field);  
                return Criteria.where(queryField).regex(value.toString());
            }  
            return new Criteria();
        }  
    },  
    IN {  
        @Override  
        public Criteria buildCriteria(QueryField queryFieldAnnotation, Field field, Object value) {
            if (check(queryFieldAnnotation, field, value)) {  
                if (value instanceof List) {  
                    String queryField = getQueryFieldName(queryFieldAnnotation, field);  
                    // 此处必须转型为List，否则会在in外面多一层[]  
                    return Criteria.where(queryField).in((List<?>)value);
                }  
            }  
            return new Criteria();
        }  
    };  
  
    private static boolean check(QueryField queryField, Field field, Object value) {  
        return !(queryField == null || field == null || value == null);  
    }  
  
    public abstract Criteria buildCriteria(QueryField queryFieldAnnotation, Field field, Object value);
  
    /** 
     * 如果实体bean的字段上QueryField注解没有设置attribute属性时，默认为该字段的名称 
     * @param queryField
     * @param field 
     * @return 
     */  
    private static String getQueryFieldName(QueryField queryField, Field field) {  
        String queryFieldValue = queryField.attribute();  
        if (!StringUtils.hasText(queryFieldValue)) {
            queryFieldValue = field.getName();  
        }  
        return queryFieldValue;  
    }  
}  