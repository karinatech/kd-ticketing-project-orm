package com.cybertek.implementations;

import com.cybertek.dto.RoleDTO;
import com.cybertek.entity.Role;
import com.cybertek.mapper.RoleMapper;
import com.cybertek.repo.RoleeRepo;
import com.cybertek.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleServiceIimpl implements RoleService {

    private RoleeRepo roleeRepo;
    private RoleMapper roleMapper;
    public RoleServiceIimpl(RoleeRepo roleeRepo,RoleMapper roleMapper){
    this.roleeRepo=roleeRepo;
    this.roleMapper=roleMapper;

}

    @Override
    public List<RoleDTO> liistAllllRoles() {
        List<Role>list = roleeRepo.findAll();
        //convert to DTO and return list
        return list.stream().map(obj -> {return roleMapper.convertToDto(obj);}).collect(Collectors.toList());
    }

    @Override
    public RoleDTO findByd(long id) {
        Role role = roleeRepo.findById(id).get();
        return roleMapper.convertToDto(role);
    }
}
