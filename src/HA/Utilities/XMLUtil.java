package HA.Utilities;



import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.xml.serializer.OutputPropertiesFactory;
import org.custommonkey.xmlunit.DetailedDiff;
import org.custommonkey.xmlunit.Diff;
import org.custommonkey.xmlunit.Difference;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class XMLUtil {
	static Document doc;
	static Element rootElement;
	static Element parentElement;
	static BufferedWriter writer = null;

	public static void init_xml() throws ParserConfigurationException{
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		doc = docBuilder.newDocument();
	}

	public static void create_Rootnode(String nodeName){

		rootElement = doc.createElement(nodeName);
		doc.appendChild(rootElement);
	}

	public static void create_Parentnode(String nodeName){

		parentElement = doc.createElement(nodeName);
		rootElement.appendChild(parentElement);		
	}

	public static void create_Childnode(String nodeName,String nodeValue){
		Element childNode = doc.createElement(nodeName);
		childNode.appendChild(doc.createTextNode(nodeValue));
		parentElement.appendChild(childNode);	
	}


	public static void save_xml(String filePath) throws Exception{
		// write the content into xml file
		TransformerFactory transformerFactory = TransformerFactory.newInstance();

		Transformer transformer = transformerFactory.newTransformer();
		transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
		transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		//transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "0");
		transformer.setOutputProperty(OutputPropertiesFactory.S_KEY_INDENT_AMOUNT, "3");

		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(new File(filePath));

		transformer.transform(source, result);

		System.out.println("Response XML File saved at "+filePath);
	}


	@SuppressWarnings("rawtypes")
	public static List init_xmlFiles(String masterFile,String testFile) {

		List differences = null;
		DetailedDiff detDiff = null;
		FileReader fr1 = null;
		FileReader fr2 = null;
		try {
			fr1 = new FileReader(masterFile);
			fr2 = new FileReader(testFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}


		Diff diff;
		try {
			diff = new Diff(fr1, fr2); 
			System.out.println("Similar? " + diff.similar());
			System.out.println("Identical? " + diff.identical());
			detDiff = new DetailedDiff(diff);
			differences= detDiff.getAllDifferences();
		} catch (SAXException | IOException e) {
			e.printStackTrace();
		}

		return differences;
	}

	public static boolean getXMLDifference(String masterFile,String testFile) throws IOException{
		boolean flag=true;
		@SuppressWarnings("rawtypes")
		List differences=init_xmlFiles(masterFile, testFile);


		init_wrtiteFile();
		writeFile("---------------------------------------------------------------------------------------------");
		writeFile("MasterFile:-"+masterFile);
		writeFile("TestFile:-"+testFile);
		writeFile("Differences found#-"+differences.size());			
		writeFile("---------------------------------------------------------------------------------------------");

		if(differences.size()==0){
			close_wrtiteFile();
			return flag;
		}else{
			/*init_wrtiteFile();
			writeFile("---------------------------------------------------------------------------------------------");
			writeFile("MasterFile:-"+masterFile);
			writeFile("TestFile:-"+testFile);
			writeFile("Differences found#-"+differences.size());			
			writeFile("---------------------------------------------------------------------------------------------");*/

			flag=false;
		}

		for (Object object : differences) {
			String actual_nodeName="Not Available";
			String actual_nodecontent="Not Available";

			Difference difference = (Difference)object;

			System.out.println("********************************	XML Difference*********************************");
			writeFile("");
			writeFile("");
			writeFile("********************************   XML Difference   ************************************");
			// System.out.println(difference);
			//System.out.println("Test Node details :- "+difference.getTestNodeDetail().getXpathLocation());    
			//writeFile("Master Node details :- "+difference.getTestNodeDetail().getXpathLocation());
			//System.out.println("Node Name :-"+difference.getTestNodeDetail().getNode().getNodeName()+"-- "+"Node Value:-"+difference.getTestNodeDetail().getNode().getNodeValue());
			//writeFile("Node Name :-"+difference.getTestNodeDetail().getNode().getNodeName()+"-- "+"Node Value:-"+difference.getTestNodeDetail().getNode().getNodeValue());


			/*	System.out.println("Test Node details :- "+difference.getTestNodeDetail().getXpathLocation());    
			System.out.println("Actual Node Name :-"+difference.getTestNodeDetail().getNode().getNodeName()+"-- "+"Node Value:-"+difference.getTestNodeDetail().getNode().getTextContent());
			writeFile("Test Node details :- "+difference.getTestNodeDetail().getXpathLocation());
			writeFile("Actual Node Name :-"+difference.getTestNodeDetail().getNode().getNodeName()+"-- "+"Node Text:-"+difference.getTestNodeDetail().getNode().getTextContent());
			writeFile("Expected Node Name :-"+difference.getControlNodeDetail().getNode().getNodeName()+"-- "+"Node Text:-"+difference.getControlNodeDetail().getNode().getTextContent());*/

			if(difference.getTestNodeDetail().getNode()== null){

			}else{
				actual_nodeName=difference.getTestNodeDetail().getNode().getNodeName();
				actual_nodecontent=difference.getTestNodeDetail().getNode().getTextContent();
			}

			/*if(difference.getTestNodeDetail().getNode().getTextContent()==null){

			}else{
				actual_nodecontent=difference.getTestNodeDetail().getNode().getTextContent();
			}*/

			System.out.println("Test Node details :- "+difference.getTestNodeDetail().getXpathLocation());    
			System.out.println("Actual Node Name :-"+actual_nodeName+"-- "+"Node Content:-"+actual_nodecontent);

			writeFile("Test Node details :- "+difference.getTestNodeDetail().getXpathLocation());
			writeFile("Actual Node Name :-"+actual_nodeName+"-- "+"Node Content:-"+actual_nodecontent);
			writeFile("Expected Node Name :-"+difference.getControlNodeDetail().getNode().getNodeName()+"-- "+"Node Content:-"+difference.getControlNodeDetail().getNode().getTextContent());

			System.out.println("*********************************************************************************");
			writeFile("*****************************************************************************************");
		}
		close_wrtiteFile();
		return flag;
	}



	public static void init_wrtiteFile(){

		try {
			//create a temporary file
			String timeLog = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
			String resultFile=System.getProperty("user.dir")+"\\TestLogs\\Compare Results\\XMLComparision_"+timeLog+".txt";
			// This will output the full path where the file will be written to...
			System.out.println(resultFile);
			//FileWriter fr = new FileWriter("C:\\FILES\\MyFile.txt", false);
			FileWriter fr = new FileWriter(resultFile, false);
			writer = new BufferedWriter(fr);


		} catch (Exception e) {
			e.printStackTrace();
		} 

	}
	public static void  writeFile(String data) throws IOException{		
		writer.write(data);
		writer.newLine();
	}
	public static void close_wrtiteFile() throws IOException{
		try{

		}catch(Exception e){

		}finally{
			writer.close();
		}

	}

	public static void formatXMLFile(String file) throws Exception{
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document document = builder.parse(new InputSource(new InputStreamReader(new FileInputStream(
				file))));

		Transformer xformer = TransformerFactory.newInstance().newTransformer();
		xformer.setOutputProperty(OutputKeys.METHOD, "xml");
		xformer.setOutputProperty(OutputKeys.INDENT, "yes");
		xformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "3");
		xformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
		Source source = new DOMSource(document);
		Result result = new StreamResult(new File(file));
		xformer.transform(source, result);
	}
	
	public static void save_xml(XMLStreamReader xmlStream,String filePath) throws Exception{
		System.setProperty("javax.xml.transform.TransformerFactory","com.sun.org.apache.xalan.internal.xsltc.trax.TransformerFactoryImpl");
		XMLOutputFactory factory      = XMLOutputFactory.newInstance();		
		factory.setProperty(XMLOutputFactory.IS_REPAIRING_NAMESPACES, true);

		//XMLStreamWriter xmlwriter = factory.createXMLStreamWriter(new FileOutputStream(filePath));

		//ConvertXmlReaderToWriter.writeAll(xmlStream, xmlwriter);

		System.out.println("File saved!");
	}
}
