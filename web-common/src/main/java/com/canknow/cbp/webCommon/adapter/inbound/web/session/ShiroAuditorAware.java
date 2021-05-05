package com.canknow.cbp.webCommon.adapter.inbound.web.session;

import com.canknow.cbp.base.runtime.session.IApplicationSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import java.util.Optional;

@Configuration
public class ShiroAuditorAware implements AuditorAware<String> {
    @Autowired(required = false)
    private IApplicationSession applicationSession;

    @Override
    public Optional<String> getCurrentAuditor() {
        if (applicationSession.getUserId()==null) {
            return Optional.empty();
        }
        return Optional.of(applicationSession.getUserId());
    }
}
