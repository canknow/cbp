package com.canknow.cbp.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.lionsoul.ip2region.DbConfig;
import org.lionsoul.ip2region.DbSearcher;
import org.lionsoul.ip2region.Util;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class IpUtil {
    private static byte[] fileByte;
    public static final String IP_URL = "http://ip.taobao.com/service/getIpInfo.php";

    static {
        try (InputStream input = IpUtil.class.getClassLoader().getResourceAsStream("ip2region.db")) {
            if (null == input) {
                throw new RuntimeException("ip2region.db not found");
            }
            fileByte = StreamUtils.copyToByteArray(input);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getLocalHostIP() {
        // 本地IP，如果没有配置外网IP则返回它
        String localIp = null;
        // 外网IP
        String netIp = null;
        try {
            Enumeration<NetworkInterface> netInterfaces = NetworkInterface.getNetworkInterfaces();
            InetAddress ip = null;
            // 是否找到外网IP
            boolean finded = false;

            while (netInterfaces.hasMoreElements() && ! finded) {
                NetworkInterface ni = netInterfaces.nextElement();
                Enumeration<InetAddress> address = ni.getInetAddresses();

                while (address.hasMoreElements()) {
                    ip = address.nextElement();
                    // 外网IP
                    if (! ip.isSiteLocalAddress() && ! ip.isLoopbackAddress() && !ip.getHostAddress().contains(":")) {
                        netIp = ip.getHostAddress();
                        finded = true;
                        break;
                    }
                    else if (ip.isSiteLocalAddress() && ! ip.isLoopbackAddress() && !ip.getHostAddress().contains(":")) {
                        // 内网IP
                        localIp = ip.getHostAddress();
                    }
                }
            }
        }
        catch (SocketException e) {
            e.getMessage();
        }
        if (netIp != null && ! "".equals(netIp)) {
            return netIp;
        }
        else {
            return localIp;
        }
    }

    @SneakyThrows
    public static String getRegion(String ip) {
        if (!Util.isIpAddress(ip)) {
            return "";
        }
        return new DbSearcher(new DbConfig(), fileByte).memorySearch(ip).getRegion();
    }


    public static String getLocationFromTaobao(String ip) {
        String location = "未知";

        if (StringUtils.isEmpty(ip)) {
            return location;
        }
        Map<String, String> param = new HashMap<>();
        param.put("ip", ip);

        try {
            String response = HttpUtil.get(IP_URL, param, null);

            if (StringUtils.isEmpty(response)) {
                return location;
            }
            JSONObject jsonObject = JSON.parseObject(response);
            String data = jsonObject.getString("data");
            JSONObject jsonData = JSON.parseObject(data);
            String region = jsonData.getString("region");
            String city = jsonData.getString("city");
            location = region + " " + city;

            if (location.contains("内网IP")) {
                location = "内网(" + ip + ")";
            }
        }
        catch (Exception e) {

        }
        return location;
    }
}
