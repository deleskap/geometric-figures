package pl.kurs.geometricfigures.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.kurs.geometricfigures.security.AppRole;

import java.util.Optional;

public interface AppRoleRepository extends JpaRepository<AppRole, Integer> {
    Optional<AppRole> findFirstByName(String name);
}
