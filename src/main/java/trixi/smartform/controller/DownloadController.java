package trixi.smartform.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import trixi.smartform.service.FileDownloadService;


@RestController
@RequiredArgsConstructor
public class DownloadController {

    private final FileDownloadService fileDownloadService;

    @GetMapping("/download")
    public ResponseEntity<String> downloadAndProcess() {
        try {
            fileDownloadService.downloadAndSendData();
            return ResponseEntity.ok("Download initiated and data sent to processing.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred during data processing: " + e.getMessage());
        }
    }
}