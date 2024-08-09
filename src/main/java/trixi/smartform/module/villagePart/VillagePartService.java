package trixi.smartform.module.villagePart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VillagePartService {

    @Autowired
    private VillagePartRepository villagePartRepository;

    public void saveVillagePart(VillagePart villagePart) {
        villagePartRepository.save(villagePart);
    }
}
