package com.canknow.cbp.web.adapter.inbound.web.utils;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class PathUtils {
	/**
	 * 获取项目的绝对路径
	 * @return 项目的绝对路径
	 */
	public static String getProjectRootPath() {
		String classPath="",rootPath  = "";
		try {
			//防止有空格,%20等的出现
			classPath = URLDecoder.decode(PathUtils.class.getClassLoader().getResource("/").getPath(),"utf-8");
		}
		catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		if(!"".equals(classPath)){
			//windows下
			if("\\".equals(File.separator)){
				rootPath  = classPath.substring(1,classPath.indexOf("/WEB-INF/classes"));
				rootPath = rootPath.replace("/", "\\");
			}
			//linux下
			if("/".equals(File.separator)){
				rootPath  = classPath.substring(0,classPath.indexOf("/WEB-INF/classes"));
				rootPath = rootPath.replace("\\", "/");
			}
		}
		return rootPath;
	}

	public static String getIpAddress(HttpServletRequest request) {
	    String ip = request.getHeader("x-forwarded-for");
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	        ip = request.getHeader("Proxy-Client-IP");
	    }
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	        ip = request.getHeader("WL-Proxy-Client-IP");
	    }
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	        ip = request.getRemoteAddr();
	    }
	    return ip;
	}
}
