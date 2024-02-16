package ie.fyp.backend.Entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class CourseModuleKey implements Serializable {

    @Column(name = "course_id")
    private Integer courseId;

    @Column(name = "module_id")
    private Integer moduleId;

    @Column(name = "course_code")
    private String courseCode;

    @Column(name = "module_code")
    private String moduleCode;

    // Constructors, getters, setters, and other methods
    // Ensure proper implementations of equals() and hashCode()

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CourseModuleKey that = (CourseModuleKey) o;
        return Objects.equals(courseId, that.courseId) &&
                Objects.equals(moduleId, that.moduleId) &&
                Objects.equals(courseCode, that.courseCode) &&
                Objects.equals(moduleCode, that.moduleCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(courseId, moduleId, courseCode, moduleCode);
    }
}
