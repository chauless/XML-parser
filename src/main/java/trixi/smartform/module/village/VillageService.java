package trixi.smartform.module.village;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VillageService {

    private final VillageRepository villageRepository;

    public void saveVillage(@Valid Village village) {
        villageRepository.save(village);
    }
}
