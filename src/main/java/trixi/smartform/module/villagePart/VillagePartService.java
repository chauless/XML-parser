package trixi.smartform.module.villagePart;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VillagePartService {

    private final VillagePartRepository villagePartRepository;

    public void saveVillagePart(@Valid VillagePart villagePart) {
        villagePartRepository.save(villagePart);
    }
}
