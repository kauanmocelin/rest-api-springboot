package dev.kauanmocelin.springbootrestapi.appuser.role;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByType(RoleType roleType);
}
