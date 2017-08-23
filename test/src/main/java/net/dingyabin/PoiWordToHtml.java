package net.dingyabin;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.collections4.map.HashedMap;
import org.apache.commons.io.FileUtils;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.converter.WordToHtmlConverter;
import org.apache.poi.hwpf.usermodel.Picture;
import org.w3c.dom.Document;

/**
 * Created by MrDing
 * Date: 2017/8/24.
 * Time:0:11
 */
public class PoiWordToHtml {

    public static void main(String[] args) throws Throwable {
        final String srcFile = "C:\\Users\\MrDing\\Desktop\\知识分享\\交换平台开发文档.doc";
        final String outfile = "C:\\Users\\MrDing\\Desktop\\html\\";
        InputStream input = new FileInputStream(srcFile);
        HWPFDocument wordDocument = new HWPFDocument(input);
        WordToHtmlConverter wordToHtmlConverter = new WordToHtmlConverter(DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument());
        wordToHtmlConverter.setPicturesManager((content, pictureType, suggestedName, widthInches, heightInches) -> suggestedName);
        wordToHtmlConverter.processDocument(wordDocument);
        List<Picture> pics = wordDocument.getPicturesTable().getAllPictures();
        Map<String,String> map = new HashedMap<>();
        StringBuilder sb = new StringBuilder();
        pics.forEach(picture -> {
            sb.append("data:").append(picture.getMimeType()).append(";base64,")
                              .append(Base64.encodeBase64String(picture.getContent()));
            map.put(picture.suggestFullFileName(), sb.toString());
            sb.setLength(0);
        });
        Document htmlDocument = wordToHtmlConverter.getDocument();
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        DOMSource domSource = new DOMSource(htmlDocument);
        StreamResult streamResult = new StreamResult(outStream);
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer serializer = tf.newTransformer();
        serializer.setOutputProperty(OutputKeys.ENCODING, "utf-8");
        serializer.setOutputProperty(OutputKeys.INDENT, "yes");
        serializer.setOutputProperty(OutputKeys.METHOD, "html");
        serializer.transform(domSource, streamResult);
        outStream.close();
        String content = new String(outStream.toByteArray());
        for (Map.Entry<String, String> stringStringEntry : map.entrySet()) {
            content=content.replaceAll(stringStringEntry.getKey(),stringStringEntry.getValue());
        }
        FileUtils.write(new File(outfile, "1.html"), content, "utf-8");
    }
}
