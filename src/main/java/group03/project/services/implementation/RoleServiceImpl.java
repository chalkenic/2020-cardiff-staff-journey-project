package group03.project.services.implementation;

import group03.project.domain.Role;
import group03.project.services.required.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceImpl implements group03.project.services.offered.RoleService {

    final RoleRepository roleRepoJPA;

    @Autowired
    public RoleServiceImpl(RoleRepository aUserRepoJPA) {roleRepoJPA = aUserRepoJPA; };


    @Override
    public void addRole(Role aRole) {
        roleRepoJPA.save(aRole);
    }

    @Override
    public List<Role> findAllRoles() {
        return roleRepoJPA.findAll();
    }


    @Override
    public Optional<Role> findRoleById(String roleId) {
        return roleRepoJPA.findById(roleId);
    }
}
