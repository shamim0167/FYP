package ie.fyp.backend.Entities;



import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "courses" )
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Integer courseId;
    private String courseCode;
    private String courseName;
    private String courseDescription;
    private Integer courseLevel;
    private Integer courseCredits;
    private boolean coursePublished;



//   Integer courseId,
//   String courseCode,
//    String courseName,
//     String courseDescription,
//    Integer courseLevel,
//    Integer courseCredit,
//boolean coursePublished;





    @OneToMany(mappedBy = "course")
    @JsonManagedReference
    private List<CourseModule> courseModules;

//    @ManyToMany
//    @JoinTable(name = "courses_modules",
//            joinColumns = @JoinColumn(name = "course_id"),
//            inverseJoinColumns = @JoinColumn(name = "module_id"))
//    private List<Module> modules;
}
