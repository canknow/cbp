package com.canknow.cbp.web.adapter.inbound.web.clientInfoProvider;

import com.canknow.cbp.base.clientInfoProvider.IClientInfoProvider;
import com.canknow.cbp.web.adapter.inbound.web.utils.IpUtil;
import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.UserAgent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component("clientInfoProvider")
@Primary
public class ClientInfoProvider implements IClientInfoProvider {
    @Autowired
    private HttpServletRequest request;

    @Override
    public String getBrowserInfo() {
        UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
        Browser browser = userAgent.getBrowser();
        return browser.toString();
    }

    @Override
    public String getClientIpAddress() {
        return IpUtil.getRequestIp(request);
    }

    @Override
    public String getComputerName() {
        UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
        OperatingSystem os = userAgent.getOperatingSystem();
        return os.toString();
    }
}
