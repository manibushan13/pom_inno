/**
 * IHostCPMService_Segment1Retrieve_APIfaultDetailsFault_FaultMessage.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.3  Built on : Jun 27, 2015 (11:17:49 BST)
 */
package HA.WebServices;

public class IHostCPMService_Segment1Retrieve_APIfaultDetailsFault_FaultMessage
    extends java.lang.Exception {
    private static final long serialVersionUID = 1442314379877L;
    private HA.WebServices.HostCPMServiceStub.APIfaultDetailsE faultMessage;

    public IHostCPMService_Segment1Retrieve_APIfaultDetailsFault_FaultMessage() {
        super(
            "IHostCPMService_Segment1Retrieve_APIfaultDetailsFault_FaultMessage");
    }

    public IHostCPMService_Segment1Retrieve_APIfaultDetailsFault_FaultMessage(
        java.lang.String s) {
        super(s);
    }

    public IHostCPMService_Segment1Retrieve_APIfaultDetailsFault_FaultMessage(
        java.lang.String s, java.lang.Throwable ex) {
        super(s, ex);
    }

    public IHostCPMService_Segment1Retrieve_APIfaultDetailsFault_FaultMessage(
        java.lang.Throwable cause) {
        super(cause);
    }

    public void setFaultMessage(
        HA.WebServices.HostCPMServiceStub.APIfaultDetailsE msg) {
        faultMessage = msg;
    }

    public HA.WebServices.HostCPMServiceStub.APIfaultDetailsE getFaultMessage() {
        return faultMessage;
    }
}
