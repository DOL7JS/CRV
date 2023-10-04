package cz.upce.nnpro_backend.services;

import cz.upce.nnpro_backend.entities.Role;
import cz.upce.nnpro_backend.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {

    @Autowired
    RoleRepository roleRepository;


    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }
}
