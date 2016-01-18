package HA.Utilities;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.stream.events.XMLEvent;

public class ConvertXmlReaderToWriter {

	 private ConvertXmlReaderToWriter() {
	  }

	  public static void writeAll(XMLStreamReader xmlr, XMLStreamWriter writer)
	      throws XMLStreamException {
	    while (xmlr.hasNext()) {
	      write(xmlr, writer);
	      xmlr.next();
	    }
	    write(xmlr, writer); // write the last element
	    writer.flush();
	  }

	  public static void write(XMLStreamReader xmlr, XMLStreamWriter writer) throws XMLStreamException {
		//  writer.setPrefix("xmlns",XMLConstants.XML_NS_PREFIX);
		//  writer.setDefaultNamespace(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		 /* writer.setPrefix("i", "xmlns");*/
		//  writer.setDefaultNamespace("http://www.w3.org/2001/XMLSchema-instance");
		  
	    switch (xmlr.getEventType()) {
	    case XMLEvent.START_ELEMENT:
	      final String localName = xmlr.getLocalName();
	      final String namespaceURI = xmlr.getNamespaceURI();
	      if (namespaceURI != null && namespaceURI.length() > 0) {
	        final String prefix = xmlr.getPrefix();
	       /* use this without mnamespace
	        writer.writeStartElement(localName);*/
	       
	        
	        if (prefix != null){
	        	 writer.writeStartElement(prefix, localName, namespaceURI);
	        	
	        }	        
	        else{
	          writer.writeStartElement(namespaceURI, localName);
	        }        
	        
	      } else {
	        writer.writeStartElement(localName);
	      }

	      for (int i = 0, len = xmlr.getNamespaceCount(); i < len; i++) {
	        writer.writeNamespace(xmlr.getNamespacePrefix(i), xmlr.getNamespaceURI(i));
	        
	      }

	      for (int i = 0, len = xmlr.getAttributeCount(); i < len; i++) {
	        String attUri = xmlr.getAttributeNamespace(i);
	      /*  if(xmlr.getAttributeLocalName(i)=="nil"){
	        	attUri="i:"+xmlr.getAttributeLocalName(i);
			}
	       
	        else{
				attUri=xmlr.getAttributeLocalName(i);
			}*/
	      /* if (attUri != null)
	        	writer.writeAttribute(attUri, xmlr.getAttributeLocalName(i), xmlr.getAttributeValue(i));
	        else*/
	        	writer.writeAttribute(xmlr.getAttributeLocalName(i), xmlr.getAttributeValue(i));
	       // writer.writeAttribute(attUri, xmlr.getAttributeValue(i));
	      }
	      break;
	    case XMLEvent.END_ELEMENT:
	      writer.writeEndElement();
	      break;
	    case XMLEvent.SPACE:
	    case XMLEvent.CHARACTERS:
	      writer.writeCharacters(xmlr.getTextCharacters(), xmlr.getTextStart(), xmlr.getTextLength());
	      break;
	    case XMLEvent.PROCESSING_INSTRUCTION:
	      writer.writeProcessingInstruction(xmlr.getPITarget(), xmlr.getPIData());
	      break;
	    case XMLEvent.CDATA:
	      writer.writeCData(xmlr.getText());
	      break;

	    case XMLEvent.COMMENT:
	      writer.writeComment(xmlr.getText());
	      break;
	    case XMLEvent.ENTITY_REFERENCE:
	      writer.writeEntityRef(xmlr.getLocalName());
	      break;
	    case XMLEvent.START_DOCUMENT:
	      String encoding = xmlr.getCharacterEncodingScheme();
	      String version = xmlr.getVersion();

	      if (encoding != null && version != null)
	        writer.writeStartDocument(encoding, version);
	      else if (version != null)
	        writer.writeStartDocument(xmlr.getVersion());
	      break;
	    case XMLEvent.END_DOCUMENT:
	      writer.writeEndDocument();
	      break;
	    case XMLEvent.DTD:
	      writer.writeDTD(xmlr.getText());
	      break;
	    }
	  }

}
