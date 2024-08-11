package trixi.smartform;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import trixi.smartform.module.village.Village;
import trixi.smartform.module.village.VillageService;
import trixi.smartform.module.villagePart.VillagePart;
import trixi.smartform.module.villagePart.VillagePartService;
import trixi.smartform.service.DataProcessingService;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class DataProcessingServiceTest {

    @Mock
    private VillageService villageService;

    @Mock
    private VillagePartService villagePartService;

    @InjectMocks
    private DataProcessingService dataProcessingService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void processAndSaveData_shouldProcessVillageAndParts() throws Exception {
        String xmlContent = "<root>" +
                "<vf:Obec><obi:Kod>123</obi:Kod><obi:Nazev>VillageName</obi:Nazev></vf:Obec>" +
                "<vf:CastObce><coi:Kod>456</coi:Kod><coi:Nazev>VillagePartName</coi:Nazev></vf:CastObce>" +
                "</root>";

        Document document = convertStringToDocument(xmlContent);

        doNothing().when(villageService).saveVillage(any(Village.class));
        doNothing().when(villagePartService).saveVillagePart(any(VillagePart.class));

        dataProcessingService.processAndSaveData(xmlContent);

        verify(villageService, times(1)).saveVillage(any(Village.class));
        verify(villagePartService, times(1)).saveVillagePart(any(VillagePart.class));
    }

    @Test
    void processAndSaveData_shouldHandleNoVillageData() throws Exception {
        String xmlContent = "<root><vf:NoObec></vf:NoObec></root>";

        dataProcessingService.processAndSaveData(xmlContent);

        verify(villageService, never()).saveVillage(any(Village.class));
        verify(villagePartService, never()).saveVillagePart(any(VillagePart.class));
    }

    @Test
    void processAndSaveData_shouldThrowExceptionForInvalidXml() {
        String invalidXmlContent = "<root><vf:Obec><obi:Kod>123</obi:Kod>";

        assertThrows(RuntimeException.class, () -> {
            dataProcessingService.processAndSaveData(invalidXmlContent);
        });

        verify(villageService, never()).saveVillage(any(Village.class));
        verify(villagePartService, never()).saveVillagePart(any(VillagePart.class));
    }

    private Document convertStringToDocument(String xmlStr) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        return builder.parse(new InputSource(new StringReader(xmlStr)));
    }
}