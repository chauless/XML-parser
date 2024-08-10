package trixi.smartform.module.village;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import trixi.smartform.module.villagePart.VillagePart;

import java.util.List;

@Entity
@Getter
@Setter
public class Village {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Village code cannot be null")
    private Long code;

    @NotBlank(message = "Village name cannot be blank")
    private String name;

    @OneToMany(mappedBy = "village", cascade = CascadeType.ALL)
    private List<VillagePart> villageParts;
}