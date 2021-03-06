package group03.project.repositories;

import group03.project.domain.Role;
import group03.project.services.required.RoleRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepoJPA extends JpaRepository <Role, String>, RoleRepository {
}
