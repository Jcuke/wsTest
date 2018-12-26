package com.zzb.mongo.base;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.List;

public abstract class MongodbBaseDao<T> {

    @Autowired
    @Qualifier("mongoTemplate")
    protected MongoTemplate mongoTemplate;

    public static final int FIRST_PAGE_NUM = 1;
    public static final String ID = "_id";


    //保存一个对象到mongodb
    public T save(T bean) {
        mongoTemplate.save(bean);
        return bean;
    }

    // 根据id删除对象
    public void deleteById(T t) {
        mongoTemplate.remove(t);
    }

    // 根据对象的属性删除
    public void deleteByCondition(T t) {
        Query query = buildBaseQuery(t);
        mongoTemplate.remove(query, getEntityClass());
    }

    public void deleteByQuery(Query query){
        mongoTemplate.remove(query, getEntityClass());
    }

    public void deleteAll(){
        mongoTemplate.remove(new Query(), getEntityClass());
    }

    // 通过条件查询更新数据
    public void updateFirst(Query query, Update update) {
        mongoTemplate.updateFirst(query, update, this.getEntityClass());
    }

    // 通过条件查询更新数据
    public void update(Query query, Update update) {
        mongoTemplate.updateMulti(query, update, this.getEntityClass());
    }

    public void upsert(T tQuery, T tUpdate) {
        Query query = buildBaseQuery(tQuery);
        Update update = buildBaseUpdate(tUpdate);
        mongoTemplate.upsert(query, update, this.getEntityClass());
    }


    /**
     * 跟新单个属性，防止其他属性因为并发而覆盖成脏数据
     *
     * @param id
     * @param fieldName
     * @param fieldValue
     * @author my
     */
    public void updateFieldById(String id, String fieldName, Object fieldValue) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(id));
        Update update = new Update();
        update.set(fieldName, fieldValue);
        update(query, update);
    }


    // 根据id进行更新
    public void updateById(String id, T t) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(id));
        Update update = buildBaseUpdate(t);
        update(query, update);
    }

    // 通过条件查询实体(集合)
    public List<T> find(Query query) {
        return mongoTemplate.find(query, this.getEntityClass());
    }

    public Long count(Query query) {
        return mongoTemplate.count(query, this.getEntityClass());
    }

    public Long count(T t) {
        return mongoTemplate.count(buildBaseQuery(t), this.getEntityClass());
    }

    public List<T> findByCondition(T t) {
        Query query = buildBaseQuery(t);
        return mongoTemplate.find(query, getEntityClass());
    }

    // 通过一定的条件查询一个实体
    public T findOne(Query query) {
        return mongoTemplate.findOne(query, this.getEntityClass());
    }

    public T findOne(T t) {
        Query query = buildBaseQuery(t);
        return mongoTemplate.findOne(query, this.getEntityClass());
    }


    // 通过ID获取记录
    public T get(String id) {
        return mongoTemplate.findById(id, this.getEntityClass());
    }

    // 通过ID获取记录,并且指定了集合名(表的意思)
    public T get(String id, String collectionName) {
        return mongoTemplate.findById(id, this.getEntityClass(), collectionName);
    }

    // 根据vo构建查询条件Query
    private Query buildBaseQuery(T t) {
        Query query = new Query();

        Field[] fields = t.getClass().getDeclaredFields();
        copyAndAddCriterial((T) t, query, fields);
        return query;
    }

    private Update buildBaseUpdate(T t) {
        Update update = new Update();

        Field[] fields = t.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                Object value = field.get(t);
                if (value != null) {
                    update.set(field.getName(), value);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return update;
    }

    // 获取需要操作的实体类class
    @SuppressWarnings("unchecked")
    protected Class<T> getEntityClass() {
        return ((Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0]);
    }

    private <T> void copyAndAddCriterial(T t, Query query, Field[] fields) {
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                Object value = field.get(t);
                if (value != null) {
                    QueryField queryField = field.getAnnotation(QueryField.class);
                    if (queryField != null) {
                        query.addCriteria(queryField.type().buildCriteria(queryField, field, value));
                    }
                }
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }



}