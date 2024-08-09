package trixi.smartform.module.village;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VillageService {

    @Autowired
    private VillageRepository villageRepository;

    public void saveVillage(Village village) {
        villageRepository.save(village);
    }
}
