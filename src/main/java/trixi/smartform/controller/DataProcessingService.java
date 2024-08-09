package trixi.smartform.controller;

import lombok.RequiredArgsConstructor;
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
public class DataProcessingService {

    private final VillageService villageService;
    private final VillagePartService villagePartService;

    public void processAndSaveData(Document document) {

        NodeList villageList = document.getElementsByTagName("vf:Obec");
        if (villageList.getLength() > 0) {
            Element villageElement = (Element) villageList.item(0);

            Village village = new Village();
            village.setCode(Long.parseLong(villageElement.getElementsByTagName("obi:Kod").item(0).getTextContent()));
            village.setName(villageElement.getElementsByTagName("obi:Nazev").item(0).getTextContent());

            villageService.saveVillage(village);

            NodeList villagePartList = document.getElementsByTagName("vf:CastObce");
            for (int i = 0; i < villagePartList.getLength(); i++) {
                Element villagePartElement = (Element) villagePartList.item(i);

                VillagePart villagePart = new VillagePart();
                villagePart.setCode(Long.parseLong(villagePartElement.getElementsByTagName("coi:Kod").item(0).getTextContent()));
                villagePart.setName(villagePartElement.getElementsByTagName("coi:Nazev").item(0).getTextContent());
                villagePart.setVillage(village);

                villagePartService.saveVillagePart(villagePart);
            }
        }
    }
}