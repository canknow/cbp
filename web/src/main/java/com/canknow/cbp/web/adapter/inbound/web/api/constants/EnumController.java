package com.canknow.cbp.web.adapter.inbound.web.api.constants;

import com.canknow.cbp.base.application.dto.EnumDto;
import com.canknow.cbp.base.auditLog.annotation.AuditLogIgnore;
import com.canknow.cbp.base.enums.BaseEnum;
import com.canknow.cbp.base.enums.IApplicationPackageProvider;
import com.canknow.cbp.base.utils.BaseEnumUtil;
import com.canknow.cbp.web.adapter.inbound.web.controllers.ControllerBase;
import io.swagger.annotations.ApiModel;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.reflections.Reflections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@RestController
@Slf4j
@AuditLogIgnore
@RequestMapping("/common/enum")
@ApiModel("common")
public class EnumController extends ControllerBase {
    @Autowired
    private List<IApplicationPackageProvider> applicationPackageProviders;

    @GetMapping("/getEnumMap")
    public Map<String, Map<Integer, EnumDto<? extends BaseEnum>>> getEnumMap() {
        return BaseEnumUtil.getEnumMap();
    }

    @PostConstruct
    protected void initialize() {
        try {
            List<String> enumPackages = applicationPackageProviders.stream().map(IApplicationPackageProvider::getName).collect(Collectors.toList());

            // 获取BaseEnum接口的所有实现
            log.debug("enumPackages:" + enumPackages);
            Reflections reflections = new Reflections(enumPackages);
            Set<Class<? extends BaseEnum>> classSet = reflections.getSubTypesOf(BaseEnum.class);

            if (CollectionUtils.isEmpty(classSet)) {
                return;
            }

            // 循环获取BaseEnum枚举
            for (Class<? extends BaseEnum> clazz : classSet) {
                BaseEnum[] enumConstants = clazz.getEnumConstants();
                Map<Integer, EnumDto<? extends BaseEnum>> enumDtoMap = new ConcurrentHashMap<>(enumConstants.length);

                for (BaseEnum baseEnum : enumConstants) {
                    Integer code = baseEnum.getCode();
                    String name = baseEnum.getName();
                    String description = baseEnum.getDescription();
                    EnumDto<BaseEnum> enumDto = new EnumDto<BaseEnum>()
                            .setCode(code)
                            .setName(name)
                            .setDescription(description)
                            .setBaseEnum(baseEnum);
                    enumDtoMap.put(code, enumDto);
                }
                // 设置map
                BaseEnumUtil.getEnumMap().put(clazz.getName().substring(clazz.getName().lastIndexOf(".") + 1), enumDtoMap);
            }
            log.debug("enumMap:{}", BaseEnumUtil.getEnumMap());
        }
        catch (Exception e) {
            log.error("获取BaseEnum枚举map异常", e);
        }
    }
}
