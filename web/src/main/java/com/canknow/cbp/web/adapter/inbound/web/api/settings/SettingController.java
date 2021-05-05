package com.canknow.cbp.web.adapter.inbound.web.api.settings;

import com.canknow.cbp.base.auditLog.annotation.Audit;
import com.canknow.cbp.base.auditLog.annotation.AuditLogIgnore;
import com.canknow.cbp.base.settings.ISettingDefinitionManager;
import com.canknow.cbp.base.settings.SettingDefinition;
import com.canknow.cbp.base.settings.SettingValue;
import com.canknow.cbp.web.adapter.inbound.web.controllers.ControllerBase;
import io.swagger.annotations.ApiModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
@AuditLogIgnore
@RequestMapping("/common/setting")
@ApiModel("setting")
public class SettingController extends ControllerBase {
    @Autowired
    private ISettingDefinitionManager _settingDefinitionManager;

    @GetMapping("/getAllSettings")
    public List<SettingValue> getAllSettings() {
        List<SettingDefinition> settingDefinitions = _settingDefinitionManager.getAllSettingDefinitions();
        List<SettingValue> result = new ArrayList<>();

        for (SettingDefinition settingDefinition: settingDefinitions) {
            if (!settingDefinition.getClientVisibilityProvider().checkVisible(iocResolver)) {
                continue;
            }
            String settingValue =  _settingManager.getSettingValue(settingDefinition.getName());
            result.add(new SettingValue(settingDefinition.getName(), settingValue));
        }
        return result;
    }

    @PostMapping("/updateSetting")
    @Audit
    public void updateSetting (@RequestBody @Valid UpdateSettingDto input) {
        settingManager.changeSetting(input.getName(), input.getValue());
    }

    @PostMapping("/updateSettings")
    @Audit
    public void updateSettings (@RequestBody @Valid UpdateSettingsInput input) {
        for (UpdateSettingDto item : input.getItems()) {
            settingManager.changeSetting(item.getName(), item.getValue());
        }
    }
}
