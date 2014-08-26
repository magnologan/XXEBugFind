/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bugfind.utils.misc;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.stream.StreamSource;

/**
 *
 * @author Mikosh
 */
public class XMLUtils {
    
    public static <T> T convertToPojo(File xmlfile, Class<T> classOfT) throws JAXBException, XMLStreamException, FileNotFoundException {
        JAXBContext jaxbContext = JAXBContext.newInstance(classOfT);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        
        XMLInputFactory xif = XMLInputFactory.newFactory();
        // settings to prevent xxe // would be funny if this tool is itsef is vulnerable to xxe :D
        xif.setProperty(XMLInputFactory.IS_SUPPORTING_EXTERNAL_ENTITIES, false);
        xif.setProperty(XMLInputFactory.SUPPORT_DTD, false);
        
        XMLStreamReader xsr = xif.createXMLStreamReader(new FileReader(xmlfile));
        T t = (T) jaxbUnmarshaller.unmarshal(xsr);//(xmlfile);
        
        return t;
    }
}