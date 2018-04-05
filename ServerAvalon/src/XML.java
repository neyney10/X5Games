import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class XML {

	public static Character[] getData(String filename)
	{
		Character[] d = new Character[1];
		////
		InputSource is = new InputSource();
			try {
				  InputStream inputStream= new FileInputStream(filename);
			        Reader reader;
				reader = new InputStreamReader(inputStream,"ISO-8859-8");
		         is = new InputSource(reader);
		        is.setEncoding("UTF-8");
			} catch (UnsupportedEncodingException | FileNotFoundException e1) {
				
				e1.printStackTrace();
			}



		////
		//File fXmlFile = new File(filename); 
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;
		try {
			dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(is);
			
			doc.getDocumentElement().normalize();
			System.out.println("Getting data in :" + doc.getDocumentElement().getNodeName());
			org.w3c.dom.NodeList nList = doc.getElementsByTagName("char");
			Character[] data = new Character[nList.getLength()];
			int index =0;
			System.out.println("----------------------------");
			for (int temp = 0; temp < nList.getLength(); temp++) {

				org.w3c.dom.Node nNode = nList.item(temp);


				if (nNode.getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;
					
					String name = eElement.getElementsByTagName("name").item(0).getTextContent();
					int loyalty = Integer.parseInt(eElement.getElementsByTagName("loyalty").item(0).getTextContent());
					int id = Integer.parseInt(eElement.getElementsByTagName("id").item(0).getTextContent());
					String gamedata = eElement.getElementsByTagName("gamedata").item(0).getTextContent();
					String lore = eElement.getElementsByTagName("lore").item(0).getTextContent();
					int power = Integer.parseInt(eElement.getElementsByTagName("power").item(0).getTextContent());
					String img = eElement.getElementsByTagName("img").item(0).getTextContent();
					data[index] = new Character(name, loyalty, id, lore, gamedata, power,img);
					index++;
				}
			}
			d= data;
			System.out.println("Done! got: "+data.length+" items." );

		} catch (ParserConfigurationException e) {
			
			e.printStackTrace();
			
		} catch (SAXException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		System.out.println();
		return d;
		
	}

	public static ArrayList<Set> getDataSet(String filename) 
	{
	
		ArrayList<Set> d = new ArrayList<>();
		////
		InputSource is = new InputSource();
			try {
				  InputStream inputStream= new FileInputStream(filename);
			        Reader reader;
				reader = new InputStreamReader(inputStream,"ISO-8859-8");
		         is = new InputSource(reader);
		        is.setEncoding("UTF-8");
			} catch (UnsupportedEncodingException | FileNotFoundException e1) {
				e1.printStackTrace();
			}

		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;
		try {
			dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(is);
			
			doc.getDocumentElement().normalize();
			System.out.println("Getting data in :" + doc.getDocumentElement().getNodeName());
			org.w3c.dom.NodeList nList = doc.getElementsByTagName("set");
			System.out.println("----------------------------");
			for (int temp = 0; temp < nList.getLength(); temp++) {

				org.w3c.dom.Node nNode = nList.item(temp);

				if (nNode.getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;
					String name = eElement.getAttribute("name");
					
					NodeList nListChar = eElement.getChildNodes();
					int[] ids = new int[nListChar.getLength()];
					for(int i=0;i<nListChar.getLength();i++)
					{
						Node eNodeChild = nListChar.item(i);
						if (eNodeChild.getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {
							ids[i] = Integer.parseInt(eNodeChild.getTextContent());
						}
					}
					
					d.add(new Set(name,ids));
				}
			}
			System.out.println("Done! got: "+d.size()+" items." );

		} catch (ParserConfigurationException e) {
			
			e.printStackTrace();
			
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println();
		return d;
		
	}

	
	public static void saveDataSetAppend(String filename ,Set set)
	{
		InputSource is = new InputSource();
		try {
			  InputStream inputStream= new FileInputStream(filename);
		        Reader reader;
			reader = new InputStreamReader(inputStream,"ISO-8859-8");
	         is = new InputSource(reader);
	        is.setEncoding("UTF-8");
		} catch (UnsupportedEncodingException | FileNotFoundException e1) {
			e1.printStackTrace();
		}


	DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	DocumentBuilder dBuilder;
	try {
		dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(is);
		
		doc.getDocumentElement().normalize();
		System.out.println("Saving 'Set' data in :" + doc.getDocumentElement().getNodeName());
		org.w3c.dom.NodeList nList = doc.getElementsByTagName("set");
		System.out.println("----------------------------");
		org.w3c.dom.Node newNode = doc.createElement("set");
		((Element)newNode).setAttribute("name", set.name);
		
		if(set.characters!=null)
		for(int id : set.characters)
		{
			org.w3c.dom.Node newNode_child_char = doc.createElement("char");
			newNode_child_char.setTextContent(Integer.toString(id));
			newNode.appendChild(newNode_child_char);
		}
		
		nList.item(0).getParentNode().appendChild(newNode);
		
		try {
			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			transformer.setOutputProperty(OutputKeys.ENCODING, "ISO-8859-8");
			StreamResult output = new StreamResult(new File(filename));
			Source input = new DOMSource(doc);

			transformer.transform(input, (javax.xml.transform.Result) output);
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerFactoryConfigurationError e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		}
		
		
		System.out.println("Done! set Saved. [Append]" );

	} catch (ParserConfigurationException e) {
		e.printStackTrace();
		
	} catch (SAXException e) {
		e.printStackTrace();
	} catch (IOException e) {
		e.printStackTrace();
	}
	System.out.println();
	
	}
	
}
