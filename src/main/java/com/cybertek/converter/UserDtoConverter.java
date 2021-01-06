package com.cybertek.converter;

import com.cybertek.dto.UserDTO;
import com.cybertek.service.UserServicee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@ConfigurationPropertiesBinding
public class UserDtoConverter implements Converter<String, UserDTO> {
    @Autowired
    UserServicee userService;
    @Override
    public UserDTO convert(String s) {
        return userService.findByUserName(s) ;
    }
}
