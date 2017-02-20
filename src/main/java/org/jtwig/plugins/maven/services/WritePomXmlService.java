package org.jtwig.plugins.maven.services;

import org.jtwig.plugins.maven.model.MavenDependency;
import org.jtwig.plugins.maven.model.PomFile;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;

public class WritePomXmlService {
    public void write(PomFile request, File outputFile) {
        try {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            XMLOutputFactory output = XMLOutputFactory.newInstance();
            XMLStreamWriter writer = output.createXMLStreamWriter(stream);
            writer.writeStartDocument();
            writer.writeStartElement("project");
            writer.writeNamespace("", "http://maven.apache.org/POM/4.0.0");
            writer.writeNamespace("xsi", "http://www.w3.org/2001/XMLSchema-instance");
            writer.writeAttribute("http://www.w3.org/2001/XMLSchema-instance", "schemaLocation", "http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd");

            writer.writeStartElement("modelVersion");
            writer.writeCharacters("4.0.0");
            writer.writeEndElement();

            writer.writeStartElement("groupId");
            writer.writeCharacters(request.getGroupId());
            writer.writeEndElement();

            writer.writeStartElement("artifactId");
            writer.writeCharacters(request.getArtifactId());
            writer.writeEndElement();

            writer.writeStartElement("version");
            writer.writeCharacters(request.getVersion());
            writer.writeEndElement();

            writer.writeStartElement("dependencies");
            for (MavenDependency dependency : request.getDependencies()) {
                writer.writeStartElement("dependency");

                writer.writeStartElement("groupId");
                writer.writeCharacters(dependency.getGroupId());
                writer.writeEndElement();

                writer.writeStartElement("artifactId");
                writer.writeCharacters(dependency.getArtifactId());
                writer.writeEndElement();

                writer.writeStartElement("version");
                writer.writeCharacters(dependency.getVersion());
                writer.writeEndElement();

                if (!"compile".equals(dependency.getScope())) {
                    writer.writeStartElement("scope");
                    writer.writeCharacters(dependency.getScope());
                    writer.writeEndElement();
                }
                writer.writeEndElement();
            }

            writer.writeEndDocument();
            writer.close();

            try (FileOutputStream outputStream = new FileOutputStream(outputFile)) {
                outputStream(new ByteArrayInputStream(stream.toByteArray()), outputStream);
                outputStream.close();
            }
        } catch (Exception e) {
            throw new RuntimeException("Unable to produce pom file", e);
        }
    }

    private void outputStream(InputStream stream, OutputStream output) throws TransformerException, IOException {
        TransformerFactory factory = TransformerFactory.newInstance();

        Transformer transformer = factory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

        transformer.transform(new StreamSource(stream), new StreamResult(output));

        output.close();
    }
}
