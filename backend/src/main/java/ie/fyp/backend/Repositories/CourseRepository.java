package ie.fyp.backend.Repositories;


import ie.fyp.backend.Entities.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course, Integer> {




    Optional<Course> findCourseByCourseId(Integer courseId);

//    void deleteCourseByCourseId(Integer courseId);

    List<Course> findCourseByCoursePublished(boolean published);

    List<Course> findCourseByCourseNameContainingIgnoreCase(String keyword);
    List<Course> findCourseByCourseCodeContainingIgnoreCase(String keyword);


}
