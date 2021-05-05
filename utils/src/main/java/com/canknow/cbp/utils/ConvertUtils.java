package com.canknow.cbp.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Slf4j
public class ConvertUtils {
	public static boolean isEmpty(Object object) {
		if (object == null) {
			return (true);
		}
		if ("".equals(object)) {
			return (true);
		}
		return "null".equals(object);
	}
	
	public static boolean isNotEmpty(Object object) {
		return object != null && !object.equals("") && !object.equals("null");
	}

	public static String decode(String strIn, String sourceCode, String targetCode) {
		return code2code(strIn, sourceCode, targetCode);
	}

	public static String strToUTF(String input, String sourceCode, String targetCode) {
		input = "";
		try {
			input = new String(input.getBytes(StandardCharsets.ISO_8859_1), "GBK");
		}
		catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return input;

	}

	private static String code2code(String input, String sourceCode, String targetCode) {
		String output = null;
		if (input == null || (input.trim()).equals("")) {
			return input;
		}
		try {
			byte[] bytes = input.getBytes(sourceCode);
			for (byte value : bytes) {
				System.out.print(value + "  ");
			}
			output = new String(bytes, targetCode);
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return output;
	}

	public static int getInt(String s, int defaultValue) {
		if (s == null || s.equals("")) {
			return (defaultValue);
		}
		try {
			return (Integer.parseInt(s));
		}
		catch (NumberFormatException e) {
			return (defaultValue);
		}
	}

	public static int getInt(String s) {
		if (s == null || s.equals("")) {
			return 0;
		}
		try {
			return (Integer.parseInt(s));
		} catch (NumberFormatException e) {
			return 0;
		}
	}

	public static int getInt(String s, Integer defaultValue) {
		if (s == null || s.equals("")) {
			return defaultValue;
		}
		try {
			return (Integer.parseInt(s));
		} catch (NumberFormatException e) {
			return 0;
		}
	}

	public static Integer[] getInts(String[] s) {
		Integer[] integer = new Integer[s.length];
		for (int i = 0; i < s.length; i++) {
			integer[i] = Integer.parseInt(s[i]);
		}
		return integer;

	}

	public static double getDouble(String value, double defaultValue) {
		if (value == null || value.equals("")) {
			return (defaultValue);
		}
		try {
			return (Double.parseDouble(value));
		} catch (NumberFormatException e) {
			return (defaultValue);
		}
	}

	public static double getDou(Double value, double defaultValue) {
		if (value == null) {
			return (defaultValue);
		}
		return value;
	}

	public static int getInt(Object object, int defaultValue) {
		if (isEmpty(object)) {
			return (defaultValue);
		}
		try {
			return (Integer.parseInt(object.toString()));
		}
		catch (NumberFormatException e) {
			return (defaultValue);
		}
	}
	
	public static Integer getInt(Object object) {
		if (isEmpty(object)) {
			return null;
		}
		try {
			return (Integer.parseInt(object.toString()));
		}
		catch (NumberFormatException e) {
			return null;
		}
	}

	public static int getInt(BigDecimal value, int defaultValue) {
		if (value == null) {
			return (defaultValue);
		}
		return value.intValue();
	}

	public static Integer[] getIntegerArray(String[] object) {
		int len = object.length;
		Integer[] result = new Integer[len];
		try {
			for (int i = 0; i < len; i++) {
				result[i] = new Integer(object[i].trim());
			}
			return result;
		}
		catch (NumberFormatException e) {
			return null;
		}
	}

	public static String getString(String value, String defaultValue) {
		if (isEmpty(value)) {
			return (defaultValue);
		}
		return (value.trim());
	}

	public static String getString(Object value, String defaultValue) {
		if (isEmpty(value)) {
			return (defaultValue);
		}
		return (value.toString().trim());
	}

	public static long stringToLong(String value) {
		Long test = new Long(0);
		try {
			test = Long.valueOf(value);
		}
		catch (Exception ignored) {
		}
		return test.longValue();
	}


	/**
	 * 判断一个类是否为基本数据类型。
	 * 
	 * @param clazz
	 *            要判断的类。
	 * @return true 表示为基本数据类型。
	 */
	private static boolean isBaseDataType(Class<?> clazz) {
		return (clazz.equals(String.class) || clazz.equals(Integer.class) || clazz.equals(Byte.class) || clazz.equals(Long.class) || clazz.equals(Double.class) || clazz.equals(Float.class) || clazz.equals(Character.class) || clazz.equals(Short.class) || clazz.equals(BigDecimal.class) || clazz.equals(BigInteger.class) || clazz.equals(Boolean.class) || clazz.equals(Date.class) || clazz.isPrimitive());
	}

	/**
	 * java去除字符串中的空格、回车、换行符、制表符
	 * 
	 * @param value
	 * @return
	 */
	public static String replaceBlank(String value) {
		String dest = "";
		if (value != null) {
			Pattern p = Pattern.compile("\\s*|\t|\r|\n");
			Matcher m = p.matcher(value);
			dest = m.replaceAll("");
		}
		return dest;

	}

	/**
	 * 判断元素是否在数组内
	 * 
	 * @param substring
	 * @param source
	 * @return
	 */
	public static boolean isIn(String substring, String[] source) {
		if (source == null || source.length == 0) {
			return false;
		}
		for (String aSource : source) {
			if (aSource.equals(substring)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 获取Map对象
	 */
	public static Map<Object, Object> getHashMap() {
		return new HashMap<Object, Object>();
	}

	/**
	 * SET转换MAP
	 * 
	 * @param set
	 * @return
	 */
	public static Map<Object, Object> setToMap(Set<Object> set) {
		Map<Object, Object> map = getHashMap();
		for (Object o : set) {
			Map.Entry<Object, Object> entry = (Map.Entry<Object, Object>) o;
			map.put(entry.getKey().toString(), entry.getValue() == null ? "" : entry.getValue().toString().trim());
		}
		return map;

	}

	/**
	 * 将下划线大写方式命名的字符串转换为驼峰式。
	 * 如果转换前的下划线大写方式命名的字符串为空，则返回空字符串。</br>
	 * 例如：hello_world->helloWorld
	 * 
	 * @param name
	 *            转换前的下划线大写方式命名的字符串
	 * @return 转换后的驼峰式命名的字符串
	 */
	public static String camelName(String name) {
		StringBuilder result = new StringBuilder();
		// 快速检查
		if (name == null || name.isEmpty()) {
			// 没必要转换
			return "";
		}
		else if (!name.contains("_")) {
			// 不含下划线，仅将首字母小写
			return name.substring(0, 1).toLowerCase() + name.substring(1).toLowerCase();
		}
		// 用下划线将原始字符串分割
		String camels[] = name.split("_");
		for (String camel : camels) {
			// 跳过原始字符串中开头、结尾的下换线或双重下划线
			if (camel.isEmpty()) {
				continue;
			}
			// 处理真正的驼峰片段
			if (result.length() == 0) {
				// 第一个驼峰片段，全部字母都小写
				result.append(camel.toLowerCase());
			}
			else {
				// 其他的驼峰片段，首字母大写
				result.append(camel.substring(0, 1).toUpperCase());
				result.append(camel.substring(1).toLowerCase());
			}
		}
		return result.toString();
	}
	
	/**
	 * 将下划线大写方式命名的字符串转换为驼峰式。(首字母写)
	 * 如果转换前的下划线大写方式命名的字符串为空，则返回空字符串。</br>
	 * 例如：hello_world->HelloWorld
	 * 
	 * @param name
	 *            转换前的下划线大写方式命名的字符串
	 * @return 转换后的驼峰式命名的字符串
	 */
	public static String camelNameCapFirst(String name) {
		StringBuilder result = new StringBuilder();
		// 快速检查
		if (name == null || name.isEmpty()) {
			// 没必要转换
			return "";
		}
		else if (!name.contains("_")) {
			// 不含下划线，仅将首字母小写
			return name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
		}
		// 用下划线将原始字符串分割
		String camels[] = name.split("_");

		for (String camel : camels) {
			// 跳过原始字符串中开头、结尾的下换线或双重下划线
			if (camel.isEmpty()) {
				continue;
			}
			// 其他的驼峰片段，首字母大写
			result.append(camel.substring(0, 1).toUpperCase());
			result.append(camel.substring(1).toLowerCase());
		}
		return result.toString();
	}

	/**
	 * 将驼峰命名转化成下划线
	 * @param value
	 * @return
	 */
	public static String camelToUnderline(String value){
        if(value.length()<3){
        	return value.toLowerCase();
        }
        StringBuilder stringBuilder=new StringBuilder(value);
        int temp = 0;

        //从第三个字符开始 避免命名不规范 
        for(int i=2; i<value.length(); i++){
            if(Character.isUpperCase(value.charAt(i))){
                stringBuilder.insert(i+temp, "_");
                temp+=1;
            }
        }
        return stringBuilder.toString().toLowerCase();
	}
	
	/**
	 * 获取类的所有属性，包括父类
	 * 
	 * @param object
	 * @return
	 */
	public static Field[] getAllFields(Object object) {
		Class<?> clazz = object.getClass();
		List<Field> fieldList = new ArrayList<>();

		while (clazz != null) {
			fieldList.addAll(new ArrayList<>(Arrays.asList(clazz.getDeclaredFields())));
			clazz = clazz.getSuperclass();
		}
		Field[] fields = new Field[fieldList.size()];
		fieldList.toArray(fields);
		return fields;
	}
	
	/**
	  * 将map的key全部转成小写
	 * @param list
	 * @return
	 */
	public static List<Map<String, Object>> toLowerCasePageList(List<Map<String, Object>> list){
		List<Map<String, Object>> select = new ArrayList<>();

		for (Map<String, Object> row : list) {
			 Map<String, Object> resultMap = new HashMap<>();
			 Set<String> keySet = row.keySet();

			 for (String key : keySet) { 
				 String newKey = key.toLowerCase(); 
				 resultMap.put(newKey, row.get(key)); 
			 }
			 select.add(resultMap);
		}
		return select;
	}

	/**
	 * 判断 list 是否为空
	 *
	 * @param list
	 * @return true or false
	 * list == null		: true
	 * list.size() == 0	: true
	 */
	public static boolean listIsEmpty(Collection list) {
		return (list == null || list.size() == 0);
	}

	/**
	 * 判断 list 是否不为空
	 *
	 * @param list
	 * @return true or false
	 * list == null		: false
	 * list.size() == 0	: false
	 */
	public static boolean listIsNotEmpty(Collection list) {
		return !listIsEmpty(list);
	}
}
