package trixi.smartform;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import trixi.smartform.module.village.Village;
import trixi.smartform.module.village.VillageService;
import trixi.smartform.module.villagePart.VillagePart;
import trixi.smartform.module.villagePart.VillagePartService;
import trixi.smartform.service.DataProcessingService;

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
    void processAndSaveData_shouldProcessVillageAndParts() {
        Document document = mock(Document.class);
        NodeList villageNodeList = mock(NodeList.class);
        NodeList villagePartNodeList = mock(NodeList.class);
        Element villageElement = mock(Element.class);
        Element villagePartElement = mock(Element.class);
        Node villageCodeNode = mock(Node.class);
        Node villageNameNode = mock(Node.class);
        Node villagePartCodeNode = mock(Node.class);
        Node villagePartNameNode = mock(Node.class);

        when(document.getElementsByTagName("vf:Obec")).thenReturn(villageNodeList);
        when(villageNodeList.getLength()).thenReturn(1);
        when(villageNodeList.item(0)).thenReturn(villageElement);
        when(villageElement.getElementsByTagName("obi:Kod")).thenReturn(mock(NodeList.class));
        when(villageElement.getElementsByTagName("obi:Nazev")).thenReturn(mock(NodeList.class));

        when(villageElement.getElementsByTagName("obi:Kod").item(0)).thenReturn(villageCodeNode);
        when(villageElement.getElementsByTagName("obi:Nazev").item(0)).thenReturn(villageNameNode);
        when(villageCodeNode.getTextContent()).thenReturn("123456");
        when(villageNameNode.getTextContent()).thenReturn("Test Village");

        when(document.getElementsByTagName("vf:CastObce")).thenReturn(villagePartNodeList);
        when(villagePartNodeList.getLength()).thenReturn(1);
        when(villagePartNodeList.item(0)).thenReturn(villagePartElement);

        when(villagePartElement.getElementsByTagName("coi:Kod").item(0)).thenReturn(villagePartCodeNode);
        when(villagePartElement.getElementsByTagName("coi:Nazev").item(0)).thenReturn(villagePartNameNode);
        when(villagePartCodeNode.getTextContent()).thenReturn("654321");
        when(villagePartNameNode.getTextContent()).thenReturn("Test Village Part");

        dataProcessingService.processAndSaveData(document);

        verify(villageService, times(1)).saveVillage(any(Village.class));
        verify(villagePartService, times(1)).saveVillagePart(any(VillagePart.class));
    }

    @Test
    void processAndSaveData_shouldWarnIfNoVillageFound() {
        Document document = mock(Document.class);
        NodeList villageNodeList = mock(NodeList.class);

        when(document.getElementsByTagName("vf:Obec")).thenReturn(villageNodeList);
        when(villageNodeList.getLength()).thenReturn(0);

        dataProcessingService.processAndSaveData(document);

        verify(villageService, never()).saveVillage(any(Village.class));
        verify(villagePartService, never()).saveVillagePart(any(VillagePart.class));
    }
}