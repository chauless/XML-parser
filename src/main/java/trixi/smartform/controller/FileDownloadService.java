package trixi.smartform.controller;

import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import trixi.smartform.module.exceptions.UrlNotFoundException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.zip.ZipInputStream;

import static java.lang.String.format;

@Service
public class FileDownloadService {

    @SneakyThrows
    public Document downloadAndExtractXml() {
        URL url;
        try {
            url = new URL("https://www.smartform.cz/download/kopidlno.xml.zip");
        } catch (MalformedURLException e) {
            throw new UrlNotFoundException(format("URL not found: {}", e));
        }

        ZipInputStream inputStream = new ZipInputStream(url.openStream());
        inputStream.getNextEntry();

        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document document = dBuilder.parse(inputStream);

        document.getDocumentElement().normalize();
        return document;
    }
}