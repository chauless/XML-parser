package trixi.smartform.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import trixi.smartform.module.village.Village;
import trixi.smartform.module.village.VillageService;
import trixi.smartform.module.villagePart.VillagePart;
import trixi.smartform.module.villagePart.VillagePartService;

@Service
@RequiredArgsConstructor
@Slf4j
public class DataProcessingService {

    private final VillageService villageService;
    private final VillagePartService villagePartService;

    public void processAndSaveData(Document document) {
        log.info("Starting data processing.");

        Village village = extractVillage(document);
        if (village != null) {
            villageService.saveVillage(village);
            log.info("Village saved: {}", village.getName());

            NodeList villagePartList = document.getElementsByTagName("vf:CastObce");
            processVillageParts(villagePartList, village);
        } else {
            log.warn("No village data found in the document.");
        }

        log.info("Data processing completed.");
    }

    private Village extractVillage(Document document) {
        NodeList villageList = document.getElementsByTagName("vf:Obec");
        if (villageList.getLength() > 0) {
            Element villageElement = (Element) villageList.item(0);

            Village village = new Village();
            village.setCode(Long.parseLong(villageElement.getElementsByTagName("obi:Kod").item(0).getTextContent()));
            village.setName(villageElement.getElementsByTagName("obi:Nazev").item(0).getTextContent());

            log.info("Extracted village: {} with code {}", village.getName(), village.getCode());
            return village;
        } else {
            return null;
        }
    }

    private void processVillageParts(NodeList villagePartList, Village village) {
        for (int i = 0; i < villagePartList.getLength(); i++) {
            Element villagePartElement = (Element) villagePartList.item(i);

            VillagePart villagePart = new VillagePart();
            villagePart.setCode(Long.parseLong(villagePartElement.getElementsByTagName("coi:Kod").item(0).getTextContent()));
            villagePart.setName(villagePartElement.getElementsByTagName("coi:Nazev").item(0).getTextContent());
            villagePart.setVillage(village);

            villagePartService.saveVillagePart(villagePart);
            log.info("Saved village part: {} with code {}", villagePart.getName(), villagePart.getCode());
        }
    }
}