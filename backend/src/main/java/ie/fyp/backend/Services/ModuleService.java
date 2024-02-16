package ie.fyp.backend.Services;

import ie.fyp.backend.Entities.Module;
import ie.fyp.backend.Entities.Module;
import ie.fyp.backend.Repositories.ModuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ModuleService {

    @Autowired
    private ModuleRepository moduleRepository;

    public List<Module> getAllModules(){
        return moduleRepository.findAll();
    }


    public Module getModuleByModuleId(Integer moduleId){
        return moduleRepository.findModuleByModuleId(moduleId).orElse(null);
    }

    public Module createModule(Module newModule){
        return moduleRepository.save(newModule);
    }

    public Module updateModule(Module editModule){
        return moduleRepository.save(editModule);
    }

    public void deleteModuleByModuleId(Integer moduleId){
        moduleRepository.deleteById(moduleId);
    }



    public List<Module> getAllPublished(boolean isPublished){
        return moduleRepository.findModuleByModulePublished(isPublished);
    }

    public List<Module> searchModules(String keyword) {
        List<Module> modulesByModuleName = moduleRepository.findModuleByModuleNameContainingIgnoreCase(keyword);
        List<Module> modulesByModuleCode = moduleRepository.findModuleByModuleCodeContainingIgnoreCase(keyword);

        // Combine the results of both queries and remove duplicates if necessary
        List<Module> combinedModules = new ArrayList<>();
        combinedModules.addAll(modulesByModuleName);
        combinedModules.addAll(modulesByModuleCode);

        return combinedModules;
    }


//    public Module getModuleByModuleId(Integer rowNumber) {
//        // Assuming you have a method to convert row number to ID
//        Integer moduleId = convertRowNumberToModuleId(rowNumber);
//        return moduleRepository.findModuleByModuleId(moduleId).orElse(null);
//    }
//
//    private Integer convertRowNumberToModuleId(Integer rowNumber) {
//
//        return (Integer) rowNumber;
//    }
//
//    public void deleteModuleByModuleId(Integer rowNumber) {
//        Integer moduleId = convertRowNumberToModuleId(rowNumber);
//        moduleRepository.deleteById(moduleId);
//    }

}
