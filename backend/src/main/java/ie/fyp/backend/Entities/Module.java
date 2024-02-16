package ie.fyp.backend.Entities;


import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "modules")
public class Module {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer moduleId;
    private String moduleCode;
    private String moduleName;
    private String moduleDescription;
    private Integer moduleLevel;
    private Integer moduleCredits;
    private boolean modulePublished;


    @OneToMany(mappedBy = "module")
    private List<CourseModule> courseModules;
}
