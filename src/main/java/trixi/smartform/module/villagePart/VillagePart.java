package trixi.smartform.module.villagePart;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import trixi.smartform.module.village.Village;

@Entity
@Getter
@Setter
public class VillagePart {

    @Id
    private Long code;

    private String name;

    @ManyToOne
    @JoinColumn(name = "village_code")
    private Village village;
}
