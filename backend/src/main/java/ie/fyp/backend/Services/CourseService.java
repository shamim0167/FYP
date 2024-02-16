package ie.fyp.backend.Services;

import ie.fyp.backend.Entities.Course;
import ie.fyp.backend.Repositories.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CourseService {


    @Autowired
    private CourseRepository courseRepository;

//    @Autowired
//    public CourseService(CourseRepository courseRepository){
//        this.courseRepository = courseRepository;
//    }

    public List<Course> getAllCourses(){
        return courseRepository.findAll();
    }

    public Course getCourseByCourseId(Integer courseId){
        return courseRepository.findCourseByCourseId(courseId).orElse(null);
    }

    public Course createCourse(Course newCourse){
        return courseRepository.save(newCourse);
    }

    public Course updateCourse(Course editCourse){
        return courseRepository.save(editCourse);
    }


    public void deleteCourseByCourseId(Integer courseId) {
        courseRepository.deleteById(courseId);
    }


    public List<Course> getAllPublished(boolean isPublished){
        return courseRepository.findCourseByCoursePublished(isPublished);
    }

    public List<Course> searchCourses(String keyword) {
        List<Course> coursesByCourseName = courseRepository.findCourseByCourseNameContainingIgnoreCase(keyword);
        List<Course> coursesByCourseCode = courseRepository.findCourseByCourseCodeContainingIgnoreCase(keyword);

        // Combine the results of both queries and remove duplicates if necessary
        List<Course> combinedCourses = new ArrayList<>();
        combinedCourses.addAll(coursesByCourseName);
        combinedCourses.addAll(coursesByCourseCode);

        return combinedCourses;
    }




//    public Course getCourseByCourseId(Integer rowNumber) {
//        // Assuming you have a method to convert row number to ID
//        Integer courseId = convertRowNumberToCourseId(rowNumber);
//        return courseRepository.findCourseByCourseId(courseId).orElse(null);
//    }
//
//    private Integer convertRowNumberToCourseId(Integer rowNumber) {
//
//        return (Integer) rowNumber;
//    }
//
//    public void deleteCourseByCourseId(Integer rowNumber) {
//        Integer courseId = convertRowNumberToCourseId(rowNumber);
//        courseRepository.deleteById(courseId);
//    }

}
