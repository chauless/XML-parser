package trixi.smartform.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.w3c.dom.Document;
import trixi.smartform.service.DataProcessingService;
import trixi.smartform.service.FileDownloadService;


@RestController
@RequiredArgsConstructor
public class DownloadController {

    private final FileDownloadService fileDownloadService;
    private final DataProcessingService dataProcessingService;

    @GetMapping("/download")
    public ResponseEntity<String> downloadAndProcess() {
        try {
            Document xmlFile = fileDownloadService.downloadAndExtractXml();
            dataProcessingService.processAndSaveData(xmlFile);
            return ResponseEntity.ok("Download and processing completed.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred during data processing: " + e.getMessage());
        }
    }
}