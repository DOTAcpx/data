package com.cn.jm.core.tool;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.Map;
import java.util.Map.Entry;
import java.util.StringJoiner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.alibaba.druid.util.StringUtils;
import com.cn.jm.core.utils.util.Base64;
import com.cn.jm.core.utils.util.MapUtils;
import com.jfinal.core.JFinal;

/**
 * 字符串处理常用方法
 */
public abstract class JMToolString {

	@SuppressWarnings("unused")
	private static Logger log = Logger.getLogger(JMToolString.class);

	/**
	 * 字符编码
	 */
	public final static String encoding = "UTF-8";

	/**
	 * double精度调整
	 * 
	 * @param doubleValue
	 *            需要调整的值123.454
	 * @param format
	 *            目标样式".##"
	 * @return
	 */
	public static String decimalFormat(double doubleValue, String format) {
		DecimalFormat myFormatter = new DecimalFormat(format);
		String formatValue = myFormatter.format(doubleValue);
		return formatValue;
	}

	/**
	 * Url Base64编码
	 * 
	 * @param data
	 *            待编码数据
	 * @return String 编码数据
	 * @throws Exception
	 */
	public static String encode(String data) throws Exception {
		return Base64.encode(data.getBytes(encoding));
	}

	/**
	 * 根据内容类型判断文件扩展名
	 * 
	 * @param contentType
	 *            内容类型
	 * @return
	 */
	public static String getFileExt(String contentType) {
		String fileExt = "";
		if ("image/jpeg".equals(contentType)) {
			fileExt = ".jpg";

		} else if ("audio/mpeg".equals(contentType)) {
			fileExt = ".mp3";

		} else if ("audio/amr".equals(contentType)) {
			fileExt = ".amr";

		} else if ("video/mp4".equals(contentType)) {
			fileExt = ".mp4";

		} else if ("video/mpeg4".equals(contentType)) {
			fileExt = ".mp4";
		}

		return fileExt;
	}

	/**
	 * 获取bean名称
	 * 
	 * @param bean
	 * @return
	 */
	public static String beanName(Object bean) {
		String fullClassName = bean.getClass().getName();
		String classNameTemp = fullClassName.substring(fullClassName.lastIndexOf(".") + 1, fullClassName.length());
		return classNameTemp.substring(0, 1) + classNameTemp.substring(1);
	}

	public final static Pattern referer_pattern = Pattern.compile("@([^@^\\s^:]{1,})([\\s\\:\\,\\;]{0,1})");// @.+?[\\s:]

	/**
	 * 首字母转小写
	 * 
	 * @param s
	 * @return
	 */
	public static String toLowerCaseFirstOne(String s) {
		if (Character.isLowerCase(s.charAt(0))) {
			return s;
		} else {
			return (new StringBuilder()).append(Character.toLowerCase(s.charAt(0))).append(s.substring(1)).toString();
		}
	}

	/**
	 * 首字母转大写
	 * 
	 * @param s
	 * @return
	 */
	public static String toUpperCaseFirstOne(String s) {
		if (Character.isUpperCase(s.charAt(0))) {
			return s;
		} else {
			return (new StringBuilder()).append(Character.toUpperCase(s.charAt(0))).append(s.substring(1)).toString();
		}
	}

	public static boolean isEmpty(String str) {
		return (str == null) || ("".equals(str));
	}

	public static boolean isNotEmpty(String str) {
		return !isEmpty(str);
	}

	public static String toUpperCase(String instr) {
		return instr == null ? instr : instr.toUpperCase();
	}

	public static String toLowerCase(String instr) {
		return instr == null ? instr : instr.toLowerCase();
	}

	public static String toUpperCaseFirst(String str) {
		if (str == null)
			return null;
		if (str.length() == 0)
			return str;
		String pre = String.valueOf(str.charAt(0));
		return str.replaceFirst(pre, pre.toUpperCase());
	}

	public static String toLowerCaseFirst(String str) {
		if (str == null)
			return null;
		if (str.length() == 0)
			return str;
		String pre = String.valueOf(str.charAt(0));
		return str.replaceFirst(pre, pre.toLowerCase());
	}

	public static String trim(String str) {
		return str == null ? null : str.trim();
	}

	public static String nvl(String instr) {
		return nvl(instr, "");
	}

	public static String nvl(String instr, String defaultValue) {
		return (instr == null) || ("".equals(instr)) ? defaultValue : instr;
	}

	public static boolean equals(String str1, String str2) {
		if ((str1 == null) && (str2 == null))
			return true;
		if ((str1 != null) && (str1.equals(str2)))
			return true;
		return false;
	}

	public static String apadLeft(double a, int b, int len) {
		return apadLeft(String.valueOf(a), String.valueOf(b), len);
	}

	public static String apadRight(double a, int b, int len) {
		return apadRight(String.valueOf(a), String.valueOf(b), len);
	}

	public static String apadLeft(String str, String str2, int len) {
		if ((str == null) || (str.length() == len) || (str2 == null))
			return str;
		if (str.length() > len)
			return str.substring(str.length() - len, len);
		return apadpro(str, str2, len, true);
	}

	public static String apadRight(String str, String str2, int len) {
		if ((str == null) || (str.length() == len) || (str2 == null))
			return str;
		if (str.length() > len)
			return str.substring(0, len);
		return apadpro(str, str2, len, false);
	}

	private static String apadpro(String a, String b, int len, boolean appendleft) {
		int f = len - a.length();
		for (int i = 0; i < f; i++) {
			a = a + b;
		}
		return a;
	}

	public static String clear(String str) {
		return clear(str, " ");
	}

	public static String clear(String str, String str2) {
		if (str == null)
			return str;
		if (str2 == null)
			return str;
		String reg = "(" + str2 + ")+";
		Pattern p = Pattern.compile(reg);
		while (p.matcher(str).find()) {
			str = str.replaceAll(reg, "");
		}
		return str;
	}

	public static String suojin(String str, int c, String sub) {
		if (isEmpty(str))
			return str;
		if (str.length() <= c)
			return str;
		sub = nvl(sub);
		c -= sub.length();
		c = c > str.length() ? 0 : c;
		str = str.substring(0, c);
		return str + sub;
	}

	public static String suojin(String str, int length) {
		return suojin(str, length, "…");
	}

	public static String replaceOnce(String text, String searchString, String replacement) {
		return replace(text, searchString, replacement, 1);
	}

	public static String replace(String text, String searchString, String replacement) {
		return replace(text, searchString, replacement, -1);
	}

	public static String replace(String text, String searchString, String replacement, int max) {
		if ((isEmpty(text)) || (isEmpty(searchString)) || (replacement == null) || (max == 0))
			return text;
		int start = 0;
		int end = text.indexOf(searchString, start);
		if (end == -1)
			return text;
		int replLength = searchString.length();
		int increase = replacement.length() - replLength;
		increase = increase >= 0 ? increase : 0;
		increase *= (max >= 0 ? 64 : max <= 64 ? max : 16);
		StringBuffer buf = new StringBuffer(text.length() + increase);

		while (end != -1) {
			buf.append(text.substring(start, end)).append(replacement);
			start = end + replLength;
			max--;
			if (max == 0)
				break;
			end = text.indexOf(searchString, start);
		}
		buf.append(text.substring(start));
		return buf.toString();
	}

	public static String urlDecode(String string) {
		try {
			return URLDecoder.decode(string, JFinal.me().getConstants().getEncoding());
		} catch (UnsupportedEncodingException e) {
			log.error("urlDecode is error", e);
		}
		return string;
	}

	public static String urlEncode(String string) {
		try {
			return URLEncoder.encode(string, JFinal.me().getConstants().getEncoding());
		} catch (UnsupportedEncodingException e) {
			log.error("urlEncode is error", e);
		}
		return string;
	}

	public static String urlRedirect(String redirect) {
		try {
			redirect = new String(redirect.getBytes(JFinal.me().getConstants().getEncoding()), "ISO8859_1");
		} catch (UnsupportedEncodingException e) {
			log.error("urlRedirect is error", e);
		}
		return redirect;
	}

	public static boolean match(String string, String regex) {
		Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(string);
		return matcher.matches();
	}

	/**
	 * 所有String字符用buffer拼接
	 * 
	 * @param str
	 * @return
	 */
	public static StringBuffer toStr(String... str) {
		StringBuffer strbuf = new StringBuffer();
		if (str.length < 0) {
			return strbuf;
		}
		for (int i = 0; i < str.length; i++) {
			strbuf.append(str[i]);
		}
		return strbuf;
	}

	/**
	 * 检查指定的字符串列表是否不为空。
	 */
	public static boolean areNotEmpty(String... values) {
		boolean result = true;
		if (values == null || values.length == 0) {
			result = false;
		} else {
			for (String value : values) {
				result &= !isEmpty(value);
			}
		}
		return result;
	}

	/**
	 * 判断字符创是否是整数
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(str);
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}

	/**
	 * 
	 * @Description: String数组是否包含
	 * @param sb
	 * @param s
	 * @return
	 */
	public static boolean arrayisHas(String[] sb, String s) {

		for (String temp : sb) {
			if (s.equals(temp)) {
				return true;
			}

		}
		return false;
	}

	/**
	 * 
	 * @Description:切割，
	 * @param string
	 * @return
	 */
	public static String[] splitString(String string) {

		if (StringUtils.isEmpty(string)) {
			return null;
		}
		return string.split(",");

	}

	public static String joiner(String inner, Object... objs) {
		StringJoiner joinner = new StringJoiner(inner);
		if (objs != null) {
			for (Object obj : objs) {
				if (obj != null) {
					joinner.add(String.valueOf(obj));
				}
			}
		}
		return joinner.toString();
	}

	public static String joiner(String inner, Map<String, Object> map) {
		StringJoiner joinner = new StringJoiner(inner);
		if (!MapUtils.isEmpty(map)) {
			for (Entry<String, Object> entry : map.entrySet()) {
				if (entry.getValue() != null) {
					joinner.add(String.valueOf(entry.getKey() + ":" + entry.getValue()));
				}
			}
		}
		return joinner.toString();
	}

}
