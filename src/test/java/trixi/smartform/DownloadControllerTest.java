package trixi.smartform;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.w3c.dom.Document;
import trixi.smartform.controller.DownloadController;
import trixi.smartform.service.DataProcessingService;
import trixi.smartform.service.FileDownloadService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class DownloadControllerTest {

    @Mock
    private FileDownloadService fileDownloadService;

    @Mock
    private DataProcessingService dataProcessingService;

    @InjectMocks
    private DownloadController downloadController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void downloadAndProcess_shouldReturnOkWhenSuccessful() throws Exception {
        Document document = mock(Document.class);
        when(fileDownloadService.downloadAndExtractXml()).thenReturn(document);

        ResponseEntity<String> response = downloadController.downloadAndProcess();

        assertEquals("Download and processing completed.", response.getBody());
        assertEquals(200, response.getStatusCodeValue());
        verify(fileDownloadService, times(1)).downloadAndExtractXml();
        verify(dataProcessingService, times(1)).processAndSaveData(document);
    }

    @Test
    void downloadAndProcess_shouldReturnErrorWhenExceptionOccurs() throws Exception {
        when(fileDownloadService.downloadAndExtractXml()).thenThrow(new RuntimeException("Test Exception"));

        ResponseEntity<String> response = downloadController.downloadAndProcess();

        assertEquals("An error occurred during data processing: Test Exception", response.getBody());
        assertEquals(500, response.getStatusCodeValue());
    }
}