package trixi.smartform.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import trixi.smartform.module.exceptions.UrlNotFoundException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.zip.ZipInputStream;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileDownloadService {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private static final String TOPIC = "downloaded_data";

    @SneakyThrows
    public void downloadAndSendData() {
        Document document = downloadAndExtractXml();
        String xmlContent = convertDocumentToString(document);
        kafkaTemplate.send(TOPIC, xmlContent);
        log.info("Data sent to Kafka topic: {}", TOPIC);
    }

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

    private String convertDocumentToString(Document document) throws TransformerException {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(document);
        StringWriter writer = new StringWriter();
        StreamResult result = new StreamResult(writer);
        transformer.transform(source, result);
        return writer.toString();
    }
}