package trixi.smartform.service;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class FileDownloadService {

    @SneakyThrows
    public Document downloadAndExtractXml() {
        URL url = getUrl("https://www.smartform.cz/download/kopidlno.xml.zip");

        ZipInputStream inputStream = getZipInputStream(url);

        Document document = parseXmlFromZip(inputStream);

        document.getDocumentElement().normalize();
        log.info("XML document downloaded and extracted successfully.");
        return document;
    }

    private URL getUrl(String urlString) {
        try {
            log.info("Creating URL from string: {}", urlString);
            return new URL(urlString);
        } catch (MalformedURLException e) {
            log.error("URL is malformed: {}", urlString);
            throw new UrlNotFoundException(format("URL not found: {}", e));
        }
    }

    @SneakyThrows
    private ZipInputStream getZipInputStream(URL url) {
        log.info("Opening connection to URL: {}", url);
        ZipInputStream inputStream = new ZipInputStream(url.openStream());
        inputStream.getNextEntry();
        log.info("ZIP input stream obtained from URL.");
        return inputStream;
    }

    @SneakyThrows
    private Document parseXmlFromZip(ZipInputStream inputStream) {
        log.info("Parsing XML from ZIP input stream.");
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        return dBuilder.parse(inputStream);
    }
}