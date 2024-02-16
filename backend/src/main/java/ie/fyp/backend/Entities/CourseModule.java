package ie.fyp.backend.Entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "courses_modules")
public class CourseModule {

    @EmbeddedId
    private CourseModuleKey id;

    @ManyToOne
    @JsonBackReference
    @MapsId("course_id")
    @JoinColumn(name = "course_id")
    private Course course;

    @ManyToOne
    @JsonBackReference
    @MapsId("module_id")
    @JoinColumn(name = "module_id")
    private Module module;
}
