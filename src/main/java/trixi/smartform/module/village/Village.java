package trixi.smartform.module.village;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;
import trixi.smartform.module.villagePart.VillagePart;

import java.util.List;

@Entity
@Getter
@Setter
public class Village {

    @Id
    private Long code;

    private String name;

    @OneToMany(mappedBy = "village")
    private List<VillagePart> villageParts;
}
