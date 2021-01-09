package com.cybertek.converter;

import com.cybertek.dto.RoleDTO;
import com.cybertek.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@ConfigurationPropertiesBinding
public class RoleDtoConveerter implements Converter<String,RoleDTO> {
@Autowired
RoleService roleService;

    public RoleDtoConveerter(RoleService roleService) {
        this.roleService = roleService;
    }

    @Override
    public RoleDTO convert(String s) {
        long id = Long.parseLong(s);
        return roleService.findByd(id);
    }
}
