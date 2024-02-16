package ie.fyp.backend.Controllers;


import ie.fyp.backend.Entities.Course;
import ie.fyp.backend.Services.CourseService;
import org.keycloak.KeycloakSecurityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Role;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api/courses")
//@CrossOrigin(origins = "http://localhost:3000")
public class CourseController {

    @Autowired
    private CourseService courseService;

    private final HttpServletRequest request;


    @Autowired
    public CourseController(HttpServletRequest request,CourseService courseService){
        this.request = request;
        this.courseService = courseService;
    }


    @GetMapping
    public List<Course> getAllTheCourses(){
        return courseService.getAllCourses();
    }

    @GetMapping("/view/{courseId}")
    public Course getTheCourseByCourseId(@PathVariable Integer courseId){
        return courseService.getCourseByCourseId(courseId);
    }

    @PostMapping("/new")
    @PreAuthorize("hasRole('Application_Admin')")
    public Course createNewCourse(@RequestBody Course newCourse, Model model){
//        configCommonAttributes(model);
        return courseService.createCourse(newCourse);
    }


    @PutMapping("/update/{courseId}")
    @PreAuthorize("hasRole('Application_Admin')")
//    @RolesAllowed("Application_Admin")

    public Course updateTheCourse(@PathVariable Integer courseId, @RequestBody Course ediOldCourse){
        Course existingCourse = courseService.getCourseByCourseId(courseId);
        if (existingCourse != null) {
            ediOldCourse.setCourseId(courseId);
            courseService.updateCourse(ediOldCourse);
        }
        return courseService.updateCourse(ediOldCourse);
    }

    @DeleteMapping("/delete/{courseId}")
    @PreAuthorize("hasRole('Application_Admin')")
    public void deleteTheCourseByCourseId(@PathVariable Integer courseId){
        courseService.deleteCourseByCourseId(courseId);
    }

    @GetMapping("/published")
    public List<Course> getAllThePublished(){
        return courseService.getAllPublished(true);
    }

    @GetMapping("/unpublished")
    public List<Course> getAllTheUnpublished(){
        return courseService.getAllPublished(false);
    }

    @GetMapping("/search")
    public List<Course> searchCourses(@RequestParam("keyword") String keyword) {
        return courseService.searchCourses(keyword);
    }

//    private void configCommonAttributes(Model model) {
//        model.addAttribute("name", getKeycloakSecurityContext().getIdToken().getGivenName());
//    }
//
//    /**
//     * The KeycloakSecurityContext provides access to several pieces of information
//     * contained in the security token, such as user profile information.
//     */
//    private KeycloakSecurityContext getKeycloakSecurityContext() {
//        return (KeycloakSecurityContext) request.getAttribute(KeycloakSecurityContext.class.getName());
//    }
}
