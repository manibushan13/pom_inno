package HA.Utilities;

import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.apache.axis2.AxisFault;
import org.testng.Assert;

import HA.WebServices.HostCPMServiceStub;
import HA.WebServices.HostCPMServiceStub.ArrayOfCOASegment;
import HA.WebServices.HostCPMServiceStub.ArrayOfSegment1;
import HA.WebServices.HostCPMServiceStub.GetAllSegmentsRequest;
import HA.WebServices.HostCPMServiceStub.GetAllSegmentsResponse;
import HA.WebServices.HostCPMServiceStub.GetUserTokenResponse;
import HA.WebServices.HostCPMServiceStub.Segment1Request;
import HA.WebServices.HostCPMServiceStub.Segment1Response;
import HA.WebServices.HostCPMServiceStub.Segment2Request;
import HA.WebServices.HostCPMServiceStub.Segment2Response;
import HA.WebServices.HostCPMServiceStub.Segment4Request;
import HA.WebServices.HostCPMServiceStub.Segment4Response;
import HA.WebServices.HostCPMServiceStub.UserTokenRequest;

public class APIUtil {
	static HostCPMServiceStub stub;
	static String currentToken;
	/**
	 * Initializes the stub of the api
	 * @author skhaleel
	 * @param apiURL
	 * @throws AxisFault
	 */
	public static void init_stub(String apiURL) throws AxisFault{		
		stub = new HostCPMServiceStub(apiURL);
		System.out.println("Stub is initialzed successfully...");
	}

	/**
	 * retrieves the token for the user login
	 * @author skhaleel
	 * @param userName
	 * @param passWord
	 * @param tenant
	 * @return
	 * @throws Exception
	 */

	public static String init_UserToken(String userName,String passWord,String tenant) throws Exception{		
		UserTokenRequest request =new UserTokenRequest();
		request.setUserLogin(userName);
		request.setUserPassword(passWord);
		request.setTenantCode(tenant);		
		GetUserTokenResponse res=stub.getUserToken(request);
		currentToken=res.getToken();
		if(currentToken==null || currentToken.equals("")){
			throw new Exception("Unbale to retreive token for the user-"+userName);
		}
		System.out.println("User token is initialzed successfully...and token -"+currentToken);
		return currentToken;
	}
	/**
	 * retrieves all segments for the given token
	 * @author skhaleel
	 * @param token
	 * @return
	 * @throws Exception
	 */
	public static ArrayOfCOASegment response_AllSegments(String token) throws Exception{
		GetAllSegmentsRequest request=new GetAllSegmentsRequest();
		request.setUserToken(token);

		stub.getAllSegments(request);		
		GetAllSegmentsResponse allSegResponse=stub.getAllSegments(request);

		ArrayOfCOASegment allSegments =  allSegResponse.getCOASegment();
		System.out.println("Retrived segments successfully...");
		return allSegments;
	}
	/**
	 * retrieves all segments for the given token
	 * @param token
	 * @return
	 * @throws Exception
	 */
	public static XMLStreamReader response_AllSegmentsInStream(String token) throws Exception{
		GetAllSegmentsRequest request=new GetAllSegmentsRequest();
		request.setUserToken(token);

		stub.getAllSegments(request);		
		GetAllSegmentsResponse allSegResponse=stub.getAllSegments(request);

		XMLStreamReader parser =allSegResponse.getPullParser(GetAllSegmentsResponse.MY_QNAME);
		System.out.println("Retrived all segments in stream successfully...");
		return parser;
	}
	/**
	 * retrieves Segment1 for the given token
	 * @param token
	 * @return
	 * @throws Exception
	 */
	public static XMLStreamReader response_segment1(String token) throws Exception{
		Segment1Request request= new Segment1Request();

		request.setUserToken(token);


		//stub..getAllSegments(request);	
		stub.segment1Retrieve(request);

		//GetAllSegmentsResponse allSegResponse=stub.getAllSegments(request);

		Segment1Response  response=stub.segment1Retrieve(request);

		XMLStreamReader parser =response.getPullParser(Segment1Response.MY_QNAME);


		System.out.println("Retrived Segment1 segments in stream successfully...");
		return parser;
	}

	/**
	 * retrieves Segment2 for the given token
	 * @param token
	 * @return
	 * @throws Exception
	 */
	public static XMLStreamReader response_segment2(String token) throws Exception{
		Segment2Request request= new Segment2Request();

		request.setUserToken(token);
		stub.segment2Retrieve(request);	

		Segment2Response  response=stub.segment2Retrieve(request);

		XMLStreamReader parser =response.getPullParser(Segment2Response.MY_QNAME);


		System.out.println("Retrived Segment2 segments in stream successfully...");
		return parser;
	}

	/**
	 * retrieves Segment4 for the given token
	 * @param token
	 * @return
	 * @throws Exception
	 */
	public static XMLStreamReader response_segment4(String token) throws Exception{
		Segment4Request request= new Segment4Request();

		request.setUserToken(token);
		stub.segment4Retrieve(request);	

		Segment4Response  response=stub.segment4Retrieve(request);

		XMLStreamReader parser =response.getPullParser(Segment4Response.MY_QNAME);


		System.out.println("Retrived Segment4 segments in stream successfully...");
		return parser;
	}


	public static ArrayOfSegment1 response_Segment1_aaray(String token) throws Exception{
		Segment1Request request=new Segment1Request();
		request.setUserToken(token);

		stub.segment1Retrieve(request);		
		Segment1Response response=stub.segment1Retrieve(request);

		ArrayOfSegment1 allSegment1 =  response.getSegment1();
		System.out.println("Retrived segments successfully...");
		return allSegment1;
	}
	/**
	 * validates the segments of response
	 * @author skhaleel
	 * @param name
	 * @param displayName
	 * @param type
	 * @param visibility
	 * @param activeStatus
	 * @throws Exception
	 */
	public static void response_validateSegment(String name,String displayName,String type,String visibility,String activeStatus) throws Exception{
		ArrayOfCOASegment allSegments =response_AllSegments(currentToken);
		int i=0;

		System.out.println("All Segments data -"+allSegments.getCOASegment().toString());


		if(allSegments.getCOASegment()[i].getName().equals(name)){
			System.out.println("Segment-"+i+" Name matches actual-"+allSegments.getCOASegment()[i].getName()+" expected-"+name);
		}else{
			Assert.assertEquals(allSegments.getCOASegment()[i].getName(), name," Segment-"+i+" Name not matching");
		}

		if(allSegments.getCOASegment()[i].getDisplayName().equals(displayName)){
			System.out.println("Segment-"+i+" Display Name matches actual-"+allSegments.getCOASegment()[i].getDisplayName()+" expected-"+displayName);
		}else{
			Assert.assertEquals(allSegments.getCOASegment()[i].getDisplayName(), displayName," Segment-"+i+" Display Name not matching");
		}

		if(allSegments.getCOASegment()[i].getType().equals(type)){
			System.out.println("Segment-"+i+" Display Name matches actual-"+allSegments.getCOASegment()[i].getType()+" expected-"+type);
		}else{
			Assert.assertEquals(allSegments.getCOASegment()[i].getType(), type," Segment -"+i+"Type not matching");
		}

		if(allSegments.getCOASegment()[i].getVisibility().equals(visibility)){
			System.out.println("Segment-"+i+" visisbility matches actual-"+allSegments.getCOASegment()[i].getType()+" expected-"+visibility);
		}else{
			Assert.assertEquals(allSegments.getCOASegment()[i].getVisibility(), type," Segment-"+i+" Visibility not matching");
		}


		if(allSegments.getCOASegment()[i].getActiveStatus().equals(activeStatus)){
			System.out.println("Segment-"+i+" Active status matches actual-"+allSegments.getCOASegment()[i].getType()+" expected-"+activeStatus);
		}else{
			Assert.assertEquals(allSegments.getCOASegment()[i].getActiveStatus(), activeStatus," Segment-"+i+" Active status not matching");
		}	

	}


	/**
	 * Generate the xml from the xmlstream response
	 * @author skhaleel
	 * @param xmlStreamReader
	 * @param parentTag
	 * @throws XMLStreamException
	 */
	public static void response_XML(XMLStreamReader xmlStreamReader,String parentTag) throws XMLStreamException{
		int counter=0;
		int  en=0;
		boolean root=false;
		boolean parent=false;
		String tagName="";
		String tagValue="";

		while(xmlStreamReader.hasNext()){
			int xmlEvent = xmlStreamReader.next();   //Process start element.

			if (xmlEvent == XMLStreamConstants.START_ELEMENT) {
				tagName=xmlStreamReader.getLocalName();

				if(root==false){
					XMLUtil.create_Rootnode(tagName);
					root=true;
				}else if (root==true && parent==false)

				{

					if(tagName.equalsIgnoreCase(parentTag)){
						XMLUtil.create_Parentnode(tagName);
					}

					parent=true;
				}

				en=0;
			}

			if (xmlEvent == XMLStreamConstants.CHARACTERS) {	
				if(en==0 && root==true && parent==true){
					tagValue=xmlStreamReader.getText();					
					XMLUtil.create_Childnode(tagName, tagValue);
				}


			} 

			if (xmlEvent == XMLStreamConstants.END_ELEMENT) { 
				String endTag=xmlStreamReader.getLocalName();
				if(parent=true && endTag.equalsIgnoreCase(parentTag)){
					parent=false;
				}
				en=1;

			} 

			counter=counter+1;


		}

	}	

}
