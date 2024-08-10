package trixi.smartform.module.villagePart;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VillagePartService {

    private final VillagePartRepository villagePartRepository;

    public void saveVillagePart(VillagePart villagePart) {
        villagePartRepository.save(villagePart);
    }
}
