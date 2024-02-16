package ie.fyp.backend.Repositories;

import ie.fyp.backend.Entities.Module;
import ie.fyp.backend.Entities.Module;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface ModuleRepository extends JpaRepository<Module, Integer> {
    Optional<Module> findModuleByModuleId(Integer moduleId);


    List<Module> findModuleByModulePublished(boolean published);

    List<Module> findModuleByModuleNameContainingIgnoreCase(String moduleName);
    List<Module> findModuleByModuleCodeContainingIgnoreCase(String moduleCode);
}
