package com.canknow.cbp.utils;

import lombok.experimental.UtilityClass;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Random;

@UtilityClass
public final class StringUtil {
	/**
	 * 汉字转换位汉语拼音首字母，英文字符不变
	 * 
	 * @param chines 汉字
	 * @return 拼音
	 */
	public String converterToFirstSpell(String chines) {
		StringBuilder pinyinName = new StringBuilder();
		char[] nameChar = chines.toCharArray();
		HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
		defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);

		for (char c : nameChar) {
			if (c > 128) {
				try {
					pinyinName.append(PinyinHelper.toHanyuPinyinStringArray(c, defaultFormat)[0].charAt(0));
				} catch (BadHanyuPinyinOutputFormatCombination e) {
					e.printStackTrace();
				}
			}
			else {
				pinyinName.append(c);
			}
		}
		return pinyinName.toString();
	}

	/**
	 * 汉字转换位汉语拼音，英文字符不变
	 * @author yangbochao
	 * @param chines 汉字
	 * @return 拼音
	 */
	public String converterToSpell(String chines) {
		StringBuilder pinyinName = new StringBuilder();
		char[] nameChar = chines.toCharArray();
		HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
		defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);

		for (char c : nameChar) {
			if (c > 128) {
				try {
					pinyinName.append(PinyinHelper.toHanyuPinyinStringArray(c, defaultFormat)[0]);
				} catch (BadHanyuPinyinOutputFormatCombination e) {
					e.printStackTrace();
				}
			}
			else {
				pinyinName.append(c);
			}
		}
		return pinyinName.toString();
	}

	public String iso2UTF8(String value) {
		if(StringUtils.isEmpty(value)) {
			return value;
		}
		return new String(value.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
	}

	/**
	 * 将文件名中的汉字转为UTF8编码的串,以便下载时能正确显示另存的文件名.
	 * @author zhaoqiang
	 * @param value 原文件名
	 * @return 重新编码后的文件名
	 */
	public String toUtf8String(String value) {
		StringBuilder stringBuilder = new StringBuilder();

		for (int i = 0; i < value.length(); i++) {
			char charAt = value.charAt(i);

			if (charAt <= 255) {
				stringBuilder.append(charAt);
			}
			else {
				byte[] bytes;
				try {
					bytes = Character.toString(charAt).getBytes(StandardCharsets.UTF_8);
				}
				catch (Exception e) {
					bytes = new byte[0];
				}
				for (int i1 : bytes) {
					int k = i1;
					if (k < 0)
						k += 256;
					stringBuilder.append("%").append(Integer.toHexString(k).toUpperCase());
				}
			}
		}
		return stringBuilder.toString();
	}

	/**
	 * 将utf-8编码的汉字转为中文
	 * @author zhaoqiang
	 * @param str
	 * @return
	 */
	public String utf8Decoding(String str) {
		String result = str;
		try {
			result = URLDecoder.decode(str, "UTF-8");
		}
		catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return result;
	}

    public String[] string2Array(String s){
		String[] strings = new String[]{};
		if(StringUtils.isEmpty(s))
			return strings;
		strings = s.split(",");
    	return strings;
    }

	public String getRandomString(int length) {
		return getRandomString("abcdefghijklmnopqrstuvwxyz0123456789", length);
	}

	public String getRandomString(String base, int length) {
		Random random = new Random();
		StringBuilder stringBuilder = new StringBuilder();

		for (int i = 0; i < length; i++) {
			int number = random.nextInt(base.length());
			stringBuilder.append(base.charAt(number));
		}
		return stringBuilder.toString();
	}

	public String getRandomVerifyCode(int length) {
		return getRandomString("0123456789", length);
	}
}
