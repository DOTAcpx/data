package com.cn.jm.core.utils.util;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Record;

import net.sf.cglib.beans.BeanCopier;
import net.sf.cglib.beans.BeanMap;

/**
 * bean工具类 使用前请注意是否引入cglib依赖
 *
 * @author liuzhou create at 2017-04-02 16:49
 */
public class BeanUtil {

	private static final Map<Class, BeanMap> ATTR_CACHE = new HashMap<>();
	private static final Map<String, BeanCopier> COPY_CACHE = new HashMap<>();
	private static boolean modelExists = true;
	private static boolean recordExists = true;

	static {
		try {
			Class.forName("com.jfinal.plugin.activerecord.Model");
		} catch (ClassNotFoundException e) {
			modelExists = false;
		}
		try {
			Class.forName("com.jfinal.plugin.activerecord.Record");
		} catch (ClassNotFoundException e) {
			recordExists = false;
		}
	}

	/**
	 * 从bean里获取指定属性
	 *
	 * @param <T>
	 *            值的类型
	 * @param bean
	 *            目前支持的类型：框架无关【javabean，Map】；beetlsql【Tail】；jfinal【Model，Record】
	 * @param key
	 *            字段名
	 * @return 字段对应的值
	 */
	@SuppressWarnings("unchecked")
	public static <T> T get(Object bean, String key) {
		return (T) getObject(bean, key);
	}

	@SuppressWarnings("unchecked")
	public static void set(Object bean, String key, Object value) {
		if (bean instanceof Map) {
			((Map) bean).put(key, value);
			return;
		}
		BeanMap beanMap = getBeanMap(bean);
		beanMap.put(key, value);
	}

	private static Object getObject(Object bean, String key) {
		if (key == null) {
			return null;
		}
		if (bean instanceof Map) {
			return ((Map) bean).get(key);
		}
		if (modelExists && bean instanceof Model) {
			return ((Model) bean).get(key);
		}
		if (recordExists && bean instanceof Record) {
			return ((Record) bean).get(key);
		}
		BeanMap beanMap = getBeanMap(bean);
		Object value = beanMap.get(bean, key);
		if (value != null) {
			return value;
		}
		return value;
	}

	private static BeanMap getBeanMap(Object bean) {
		Class clazz = bean.getClass();
		BeanMap result = ATTR_CACHE.get(clazz);
		if (result != null) {
			return result.newInstance(bean);
		}
		synchronized (ATTR_CACHE) {
			result = ATTR_CACHE.get(clazz);
			if (result != null) {
				return result.newInstance(bean);
			}
			result = BeanMap.create(bean);
			ATTR_CACHE.put(clazz, result);
			return result;
		}
	}

	/**
	 * 复制bean中字段，两者需为规范的javabean，不匹配（包括名字和类型）的字段将被忽略 特别注意，值为null的字段也将复制
	 *
	 * @param source
	 *            源bean
	 * @param target
	 *            目标bean
	 */
	public static void copyBean(Object source, Object target) {
		BeanCopier copier = getBeanCopierByBean(source, target);
		copier.copy(source, target, null);
	}

	/**
	 * 复制bean中字段，两者需为规范的javabean，不匹配（包括名字和类型）的字段将被忽略 特别注意，值为null的字段也将复制
	 *
	 * @param source
	 *            源bean
	 * @param targetSupplier
	 *            目标bean提供器
	 */
	public static <T> T copyBean(Object source, Supplier<T> targetSupplier) {
		T target = targetSupplier.get();
		BeanCopier copier = getBeanCopierByBean(source, target);
		copier.copy(source, target, null);
		return target;
	}

	private static BeanCopier getBeanCopierByBean(Object source, Object target) {
		return getBeanCopierByClass(source.getClass(), target.getClass());
	}

	private static BeanCopier getBeanCopierByClass(Class<?> sourceClass, Class<?> targetClass) {
		String beanKey = generateKey(sourceClass, targetClass);
		BeanCopier result = COPY_CACHE.get(beanKey);
		if (result != null) {
			return result;
		}
		synchronized (COPY_CACHE) {
			result = COPY_CACHE.get(beanKey);
			if (result != null) {
				return result;
			}
			result = BeanCopier.create(sourceClass, targetClass, false);
			COPY_CACHE.put(beanKey, result);
			return result;
		}
	}

	private static String generateKey(Class<?> c1, Class<?> c2) {
		return c1.getName() + "_" + c2.getName();
	}

	/**
	 * 复制一个集合的bean 复制bean中字段，两者需为规范的javabean，不匹配（包括名字和类型）的字段将被忽略 特别注意，值为null的字段也将复制
	 *
	 * @param source
	 *            源bean
	 * @param targetElementSupplier
	 *            目标元素提供器
	 * @param targetSupplier
	 *            目标集合提供器
	 */
	public static <T, S, R extends Collection<S>> R copyCollection(Collection<T> source,
			Supplier<S> targetElementSupplier, Supplier<R> targetSupplier) {
		R target = targetSupplier.get();
		for (T t : source) {
			S s = copyBean(t, targetElementSupplier);
			target.add(s);
		}
		return target;
	}
}
