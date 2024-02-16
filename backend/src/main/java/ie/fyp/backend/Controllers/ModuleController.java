package ie.fyp.backend.Controllers;


import ie.fyp.backend.Entities.Course;
import ie.fyp.backend.Entities.Module;
import ie.fyp.backend.Services.ModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import java.util.List;

@RestController
@RequestMapping("/api/modules")
public class ModuleController {

    @Autowired
    private ModuleService moduleService;

    @Autowired
    public ModuleController(ModuleService moduleService){
        this.moduleService = moduleService;
    }

    @GetMapping
    public List<Module> getAllTheModule(){
        return moduleService.getAllModules();
    }

    @GetMapping("/view/{moduleId}")
    public Module getTheModuleByModuleId(@PathVariable Integer moduleId){
        return moduleService.getModuleByModuleId(moduleId);
    }

    @PostMapping("/new")
    @PreAuthorize("hasRole('Application_Admin')")
    public Module createNewModule(@RequestBody Module newModule){
        return moduleService.createModule(newModule);
    }

    @PutMapping("/update/{moduleId}")
    @PreAuthorize("hasRole('Application_Admin')")
    public Module updateTheModule(@PathVariable Integer moduleId, @RequestBody Module editOldModule){
        Module existingModule = moduleService.getModuleByModuleId(moduleId);
        if (existingModule != null) {
            editOldModule.setModuleId(moduleId);
            moduleService.updateModule(editOldModule);
        }

        return moduleService.updateModule(editOldModule);
    }

    @DeleteMapping("/delete/{moduleId}")
    @PreAuthorize("hasRole('Application_Admin')")
    public void deleteTheModuleByModuleId(@PathVariable Integer moduleId){
        moduleService.deleteModuleByModuleId(moduleId);
    }

    @GetMapping("/published")
    public List<Module> getAllThePublished(){
        return moduleService.getAllPublished(true);
    }

    @GetMapping("/unpublished")
    public List<Module> getAllTheUnpublished(){
        return moduleService.getAllPublished(false);
    }

    @GetMapping("/search")
    public List<Module> searchModules(@RequestParam("keyword") String keyword) {
        return moduleService.searchModules(keyword);
    }

}
