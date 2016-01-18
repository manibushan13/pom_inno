/**
 * HostCPMServiceCallbackHandler.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.3  Built on : Jun 27, 2015 (11:17:49 BST)
 */
package HA.WebServices;


/**
 *  HostCPMServiceCallbackHandler Callback class, Users can extend this class and implement
 *  their own receiveResult and receiveError methods.
 */
public abstract class HostCPMServiceCallbackHandler {
    protected Object clientData;

    /**
     * User can pass in any object that needs to be accessed once the NonBlocking
     * Web service call is finished and appropriate method of this CallBack is called.
     * @param clientData Object mechanism by which the user can pass in user data
     * that will be avilable at the time this callback is called.
     */
    public HostCPMServiceCallbackHandler(Object clientData) {
        this.clientData = clientData;
    }

    /**
     * Please use this constructor if you don't want to set any clientData
     */
    public HostCPMServiceCallbackHandler() {
        this.clientData = null;
    }

    /**
     * Get the client data
     */
    public Object getClientData() {
        return clientData;
    }

    /**
     * auto generated Axis2 call back method for segment5Retrieve method
     * override this method for handling normal response from segment5Retrieve operation
     */
    public void receiveResultsegment5Retrieve(
        HA.WebServices.HostCPMServiceStub.Segment5Response result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from segment5Retrieve operation
     */
    public void receiveErrorsegment5Retrieve(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for getScenarios method
     * override this method for handling normal response from getScenarios operation
     */
    public void receiveResultgetScenarios(
        HA.WebServices.HostCPMServiceStub.GetScenariosResponse result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from getScenarios operation
     */
    public void receiveErrorgetScenarios(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for getGLData method
     * override this method for handling normal response from getGLData operation
     */
    public void receiveResultgetGLData(
        HA.WebServices.HostCPMServiceStub.GLDataResponse result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from getGLData operation
     */
    public void receiveErrorgetGLData(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for segment8Retrieve method
     * override this method for handling normal response from segment8Retrieve operation
     */
    public void receiveResultsegment8Retrieve(
        HA.WebServices.HostCPMServiceStub.Segment8Response result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from segment8Retrieve operation
     */
    public void receiveErrorsegment8Retrieve(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for getAllCurrencyTypes method
     * override this method for handling normal response from getAllCurrencyTypes operation
     */
    public void receiveResultgetAllCurrencyTypes(
        HA.WebServices.HostCPMServiceStub.GetAllCurrencyTypesResponse result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from getAllCurrencyTypes operation
     */
    public void receiveErrorgetAllCurrencyTypes(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for getAllCurrencies method
     * override this method for handling normal response from getAllCurrencies operation
     */
    public void receiveResultgetAllCurrencies(
        HA.WebServices.HostCPMServiceStub.GetAllCurrenciesResponse result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from getAllCurrencies operation
     */
    public void receiveErrorgetAllCurrencies(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for getAllSegments method
     * override this method for handling normal response from getAllSegments operation
     */
    public void receiveResultgetAllSegments(
        HA.WebServices.HostCPMServiceStub.GetAllSegmentsResponse result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from getAllSegments operation
     */
    public void receiveErrorgetAllSegments(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for getUsers method
     * override this method for handling normal response from getUsers operation
     */
    public void receiveResultgetUsers(
        HA.WebServices.HostCPMServiceStub.GetUsersResponse result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from getUsers operation
     */
    public void receiveErrorgetUsers(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for segment1Retrieve method
     * override this method for handling normal response from segment1Retrieve operation
     */
    public void receiveResultsegment1Retrieve(
        HA.WebServices.HostCPMServiceStub.Segment1Response result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from segment1Retrieve operation
     */
    public void receiveErrorsegment1Retrieve(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for segment6Retrieve method
     * override this method for handling normal response from segment6Retrieve operation
     */
    public void receiveResultsegment6Retrieve(
        HA.WebServices.HostCPMServiceStub.Segment6Response result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from segment6Retrieve operation
     */
    public void receiveErrorsegment6Retrieve(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for getBudgetEntities method
     * override this method for handling normal response from getBudgetEntities operation
     */
    public void receiveResultgetBudgetEntities(
        HA.WebServices.HostCPMServiceStub.GetBudgetEntitiesResponse result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from getBudgetEntities operation
     */
    public void receiveErrorgetBudgetEntities(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for segment7Retrieve method
     * override this method for handling normal response from segment7Retrieve operation
     */
    public void receiveResultsegment7Retrieve(
        HA.WebServices.HostCPMServiceStub.Segment7Response result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from segment7Retrieve operation
     */
    public void receiveErrorsegment7Retrieve(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for segment3Retrieve method
     * override this method for handling normal response from segment3Retrieve operation
     */
    public void receiveResultsegment3Retrieve(
        HA.WebServices.HostCPMServiceStub.Segment3Response result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from segment3Retrieve operation
     */
    public void receiveErrorsegment3Retrieve(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for getUserToken method
     * override this method for handling normal response from getUserToken operation
     */
    public void receiveResultgetUserToken(
        HA.WebServices.HostCPMServiceStub.GetUserTokenResponse result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from getUserToken operation
     */
    public void receiveErrorgetUserToken(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for segment4Retrieve method
     * override this method for handling normal response from segment4Retrieve operation
     */
    public void receiveResultsegment4Retrieve(
        HA.WebServices.HostCPMServiceStub.Segment4Response result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from segment4Retrieve operation
     */
    public void receiveErrorsegment4Retrieve(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for segment2Retrieve method
     * override this method for handling normal response from segment2Retrieve operation
     */
    public void receiveResultsegment2Retrieve(
        HA.WebServices.HostCPMServiceStub.Segment2Response result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from segment2Retrieve operation
     */
    public void receiveErrorsegment2Retrieve(java.lang.Exception e) {
    }
}
