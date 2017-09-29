package com.don.library.json;

import android.os.Parcel;
import android.os.Parcelable;

import com.don.library.json.annotation.Ignore;
import com.don.library.json.annotation.Name;
import com.don.library.util.EmptyUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public abstract class Json implements Parcelable {

    @Ignore
    private JSONObject mJsonObject;
    @Ignore
    private Object mEntity;

    public abstract Object getEntity();

    public Json() {
        mEntity = getEntity();
    }

    public void parse(String json) {
        JSONObject object = null;
        try {
            object = new JSONObject(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        parse(object);
    }

    public void parse(JSONObject object) {
        if (object == null) {
            return;
        }
        if (mEntity == null) {
            return;
        }
        mEntity = getEntity();
        mJsonObject = object;
        fromJsonObject();
    }

    private void fromJsonObject() {
        try {
            Class<?> cls = mEntity.getClass();
            Field[] fields = cls.getDeclaredFields();
            for (Field field : fields) {
                Class<?> fieldType = field.getType();
                if (fieldType.isPrimitive()
                        || fieldType.getName().startsWith("java.lang")) {
                    setFieldValue(field);
                }
                if (Json.class.isAssignableFrom(fieldType)) {
                    setEntityValue(field);
                }
                if (fieldType.isAssignableFrom(List.class)) {
                    Type type = field.getGenericType();
                    if (type == null) {
                        continue;
                    }
                    if (type instanceof ParameterizedType) {
                        ParameterizedType pt = (ParameterizedType) type;
                        Class<?> parameterizedType = (Class<?>) pt
                                .getActualTypeArguments()[0];
                        setListValue(field, parameterizedType);
                    }
                }
            }
        } catch (Exception e) {
        }
    }

    private void setFieldValue(Field field) {
        try {
            field.setAccessible(true);
            String name = null;
            if (field.isAnnotationPresent(Name.class)) {
                name = field.getAnnotation(Name.class).value();
            } else {
                name = field.getName();
            }
            Class<?> fieldType = field.getType();
            if ((Integer.TYPE == fieldType) || (Integer.class == fieldType)
                    || (int.class == fieldType)) {
                field.set(mEntity, mJsonObject.optInt(name));
            } else if (String.class == fieldType) {
                String value = mJsonObject.optString(name);
                field.set(mEntity, EmptyUtil.isEmpty(value) ? "" : value);
            } else if ((Long.TYPE == fieldType) || (Long.class == fieldType)
                    || (long.class == fieldType)) {
                field.set(mEntity, mJsonObject.optLong(name));
            } else if ((Float.TYPE == fieldType) || (Float.class == fieldType)
                    || (float.class == fieldType)) {
                String value = mJsonObject.optString(name);
                field.set(mEntity,
                        EmptyUtil.isEmpty(value) ? 0.0F : Float.valueOf(value));
            } else if ((Double.TYPE == fieldType)
                    || (Double.class == fieldType)
                    || (double.class == fieldType)) {
                field.set(mEntity, mJsonObject.optDouble(name));
            } else if ((Boolean.TYPE == fieldType)
                    || (Boolean.class == fieldType)
                    || (boolean.class == fieldType)) {
                field.set(mEntity, mJsonObject.optInt(name) == 1 ? true : false);
            } else {
                field.set(mEntity, mJsonObject.opt(name));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setEntityValue(Field field) {
        try {
            field.setAccessible(true);
            String name = null;
            if (field.isAnnotationPresent(Name.class)) {
                name = field.getAnnotation(Name.class).value();
            } else {
                name = field.getName();
            }
            Class<?> fieldType = field.getType();
            JSONObject object = mJsonObject.optJSONObject(name);
            Object newEntity = fieldType.newInstance();
            if (Json.class.isAssignableFrom(fieldType)) {
                Method method = newEntity.getClass().getSuperclass()
                        .getMethod("parse", JSONObject.class);
                method.invoke(newEntity, object);
                field.set(mEntity, newEntity);
            } else {
                field.set(mEntity, newEntity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setListValue(Field field, Class<?> parameterizedType) {
        try {
            field.setAccessible(true);
            String name = null;
            if (field.isAnnotationPresent(Name.class)) {
                name = field.getAnnotation(Name.class).value();
            } else {
                name = field.getName();
            }
            List list = (List) field.get(mEntity);
            if (list == null) {
                list = new ArrayList();
            }
            JSONArray array = mJsonObject.optJSONArray(name);
            if (array == null) {
                return;
            }
            if (parameterizedType.isPrimitive()
                    || parameterizedType.getName().startsWith("java.lang")) {
                for (int i = 0; i < array.length(); i++) {
                    if ((Integer.TYPE == parameterizedType)
                            || (Integer.class == parameterizedType)
                            || (int.class == parameterizedType)) {
                        list.add(array.optInt(i));
                    } else if (String.class == parameterizedType) {
                        list.add(array.optString(i));
                    } else if ((Long.TYPE == parameterizedType)
                            || (Long.class == parameterizedType)
                            || (long.class == parameterizedType)) {
                        list.add(array.optLong(i));
                    } else if ((Float.TYPE == parameterizedType)
                            || (Float.class == parameterizedType)
                            || (float.class == parameterizedType)) {
                        list.add(EmptyUtil.isEmpty(array.optString(i)) ? 0.0F
                                : Float.valueOf(array.optString(i)));
                    } else if ((Double.TYPE == parameterizedType)
                            || (Double.class == parameterizedType)
                            || (double.class == parameterizedType)) {
                        list.add(array.optDouble(i));
                    } else if ((Boolean.TYPE == parameterizedType)
                            || (Boolean.class == parameterizedType)
                            || (boolean.class == parameterizedType)) {
                        list.add(array.optInt(i) == 1 ? true : false);
                    } else {
                        list.add(array.opt(i));
                    }
                }
                field.set(mEntity, list);
            }
            if (Json.class.isAssignableFrom(parameterizedType)) {
                for (int i = 0; i < array.length(); i++) {
                    JSONObject object = array.optJSONObject(i);
                    Object newEntity = parameterizedType.newInstance();
                    Method method = newEntity.getClass().getSuperclass()
                            .getMethod("parse", JSONObject.class);
                    method.invoke(newEntity, object);
                    list.add(newEntity);
                }
            }

        } catch (Exception e) {
        }
    }

    public JSONObject toJsonObject() {
        JSONObject object = new JSONObject();
        try {
            Class<?> cls = mEntity.getClass();
            Field[] fields = cls.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                String name = null;
                if (field.isAnnotationPresent(Name.class)) {
                    name = field.getAnnotation(Name.class).value();
                } else {
                    name = field.getName();
                }
                Class<?> fieldType = field.getType();
                if (fieldType.isPrimitive()
                        || fieldType.getName().startsWith("java.lang")) {
                    Object fieldValue = null;
                    fieldValue = field.get(mEntity);
                    object.put(name, fieldValue);
                }
                if (Json.class.isAssignableFrom(fieldType)) {
                    Method method = mEntity.getClass().getSuperclass()
                            .getMethod("toJsonObject");
                    Object o = method.invoke(field.get(mEntity));
                    object.put(name, o);
                }
                if (fieldType.isAssignableFrom(List.class)) {
                    Type type = field.getGenericType();
                    if (type == null) {
                        continue;
                    }
                    ArrayList list = (ArrayList) field.get(mEntity);
                    if (type instanceof ParameterizedType) {
                        ParameterizedType pt = (ParameterizedType) type;
                        Class<?> parameterizedType = (Class<?>) pt
                                .getActualTypeArguments()[0];
                        JSONArray array = new JSONArray();
                        if (parameterizedType.isPrimitive()
                                || parameterizedType.getName().startsWith(
                                "java.lang")) {
                            for (int i = 0; i < list.size(); i++) {
                                array.put(list.get(i));
                            }
                        }
                        if (Json.class.isAssignableFrom(parameterizedType)) {
                            for (int i = 0; i < list.size(); i++) {
                                Method method = mEntity.getClass()
                                        .getSuperclass()
                                        .getMethod("toJsonObject");
                                Object o = method.invoke(list.get(i));
                                array.put(o);
                            }
                        }
                        object.put(name, array);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return object;
    }

    public static <T extends Json> T parseJsonObject(Class<T> cls, String json) {
        JSONObject object = null;
        try {
            object = new JSONObject(json);
        } catch (Exception e) {
        }
        return parseJsonObject(cls, object);
    }

    public static <T extends Json> T parseJsonObject(Class<T> cls, JSONObject object) {
        T entity = null;
        try {
            entity = cls.newInstance();
            if (cls == null || object == null) {
                return entity;
            }
            Method method = entity.getClass()
                    .getSuperclass().getMethod("parse", JSONObject.class);
            method.invoke(entity, object);
        } catch (Exception e) {
        }
        return entity;
    }

    public static <T extends Json> ArrayList<T> parseJsonArray(Class<T> cls, String json) {
        try {
            JSONArray array = new JSONArray(json);
            return parseJsonArray(cls, array);
        } catch (Exception e) {
        }
        return new ArrayList<>();
    }

    public static <T extends Json> ArrayList<T> parseJsonArray(Class<T> cls, JSONArray array) {
        ArrayList<T> list = new ArrayList<>();
        if (cls == null || array == null) {
            return list;
        }
        try {
            for (int i = 0; i < array.length(); i++) {
                T entity = cls.newInstance();
                Method method = entity.getClass()
                        .getSuperclass().getMethod("parse", JSONObject.class);
                method.invoke(entity, array.optJSONObject(i));
                list.add(entity);
            }
        } catch (Exception e) {
        }
        return list;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(getClass().getName());
        try {
            Class<?> cls = mEntity.getClass();
            Field[] fields = cls.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                Object object = field.get(mEntity);
                dest.writeValue(object);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Ignore
    public static final Parcelable.Creator<Json> CREATOR = new Creator<Json>() {

        @Override
        public Json createFromParcel(Parcel source) {
            Object newEntity = null;
            try {
                String clsName = source.readString();
                Class<?> cls = Class.forName(clsName);
                newEntity = cls.newInstance();
                Field[] fields = cls.getDeclaredFields();
                for (Field field : fields) {
                    field.setAccessible(true);
                    ClassLoader loader = null;
                    Class<?> fieldType = field.getType();
                    if (fieldType.isPrimitive()
                            || fieldType.getName().startsWith("java.lang")) {
                        loader = null;
                    }
                    if (Json.class.isAssignableFrom(fieldType)) {
                        loader = Json.class.getClassLoader();
                    }
                    if (fieldType.isAssignableFrom(List.class)) {
                        Type type = field.getGenericType();
                        if (type instanceof ParameterizedType) {
                            ParameterizedType pt = (ParameterizedType) type;
                            Class<?> parameterizedType = (Class<?>) pt
                                    .getActualTypeArguments()[0];
                            loader = parameterizedType.getClassLoader();
                        }
                    }
                    Object value = source.readValue(loader);
                    field.set(newEntity, value);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return newEntity == null ? null : (Json) newEntity;
        }

        @Override
        public Json[] newArray(int size) {
            return new Json[size];
        }

    };
}
