package trixi.smartform.module.village;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VillageService {

    private final VillageRepository villageRepository;

    public void saveVillage(Village village) {
        villageRepository.save(village);
    }
}
