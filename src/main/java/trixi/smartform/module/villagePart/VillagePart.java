package trixi.smartform.module.villagePart;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import trixi.smartform.module.village.Village;

@Entity
@Getter
@Setter
public class VillagePart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "VillagePart code cannot be null")
    private Long code;

    @NotBlank(message = "VillagePart name cannot be blank")
    private String name;

    @ManyToOne
    @JoinColumn(name = "village_code", referencedColumnName = "code")
    private Village village;
}