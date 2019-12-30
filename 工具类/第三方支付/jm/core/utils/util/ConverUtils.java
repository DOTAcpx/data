package com.cn.jm.core.utils.util;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;

import com.jfinal.core.Injector;

/**
 * 
 * @author lgk 转化
 *
 */
public class ConverUtils {

	/**
	 * 
	 * @date 2017年5月16日 下午3:40:38
	 * @author lgk
	 * @Description: 把数组转换为一个用逗号分隔的字符串
	 * @param arrays
	 * @return
	 *
	 */
	public static String arraysToString(String[] arrays) {
		String str = "";
		if (arrays != null && arrays.length > 0) {
			for (int i = 0; i < arrays.length; i++) {
				str += arrays[i] + ",";
			}
		}
		str = str.substring(0, str.length() - 1);
		return str;
	}

	/**
	 * 
	 * @date 2017年5月16日 下午3:42:00
	 * @author lgk
	 * @Description: 把list转换为一个用逗号分隔的字符串
	 * @param list
	 * @return
	 *
	 */
	public static String listToString(@SuppressWarnings("rawtypes") List list) {
		StringBuilder sb = new StringBuilder();
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				if (i < list.size() - 1) {
					sb.append(list.get(i) + ",");
				} else {
					sb.append(list.get(i));
				}
			}
		}
		return sb.toString();
	}

	/**
	 * 
	 * @date 2017年5月16日 下午3:42:00
	 * @author lgk
	 * @Description: 把字段填充到对象
	 * @param list
	 * @return
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 *
	 */
	public static <T> T fullModel(Class<T> clazz, Map<String, String> param) throws Exception {

		if (param == null || param.isEmpty()) {
			return null;
		}

		// 处理时间格式
		DateConverter dateConverter = new DateConverter();
		// 设置日期格式
		dateConverter.setPatterns(new String[] { "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss" });
		// 注册格式
		ConvertUtils.register(dateConverter, Date.class);

		T obj = clazz.newInstance();

		BeanUtils.populate(obj, param);

		return obj;
	}

	/**
	 * 
	 * @date 2017年5月16日 下午3:42:00
	 * @author lgk
	 * @Description: 把字段填充到对象
	 * @param list
	 * @return
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 *
	 */
	public static <T> T full(Class<T> clazz, HttpServletRequest request) {

		// if (param == null || param.isEmpty()) {
		// return null;
		// }

		return Injector.injectModel(clazz, "", request, true);

		// // 处理时间格式
		// DateConverter dateConverter = new DateConverter();
		// // 设置日期格式
		// dateConverter.setPatterns(new String[] { "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss"
		// });
		// // 注册格式
		// ConvertUtils.register(dateConverter, Date.class);
		// T obj = null;
		// try {
		// obj = clazz.newInstance();
		// BeanUtils.populate(obj, param);
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		// return obj;
	}
	
	public static String getLanguageField(String language, String ... flieds) {
		String pre = "";
		String select = "";
		
		for(String flied:flieds) {
			String newflied = flied;
			if("en_US".equals(language)) {
				pre = "en";
			}
			
			
			int i = flied.indexOf(".");
			
			
			newflied = pre + flied.substring(i+1, i+2).toUpperCase() +flied.substring(i+2) ;
			
			if(i != -1) {
				newflied = flied.substring(0,i+1) + newflied;
				flied=flied.substring(i+1);
			}
				
			select += "," + newflied + " as `" +  flied+"` ";
		}
		
		return select;
		
	}
	
	public static void main(String[] args) {
		System.out.println(getLanguageField("en_US", "select g.*", "g.name","g.content","org.name"));;
		System.out.println(getLanguageField("en_US", "select g.*", "name","content","name2"));;
	}

}
