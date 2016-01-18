/**
 * HostAPI_StateFreeCallbackHandler.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.3  Built on : Jun 27, 2015 (11:17:49 BST)
 */
package HA.WebServices;


/**
 *  HostAPI_StateFreeCallbackHandler Callback class, Users can extend this class and implement
 *  their own receiveResult and receiveError methods.
 */
public abstract class HostAPI_StateFreeCallbackHandler {
    protected Object clientData;

    /**
     * User can pass in any object that needs to be accessed once the NonBlocking
     * Web service call is finished and appropriate method of this CallBack is called.
     * @param clientData Object mechanism by which the user can pass in user data
     * that will be avilable at the time this callback is called.
     */
    public HostAPI_StateFreeCallbackHandler(Object clientData) {
        this.clientData = clientData;
    }

    /**
     * Please use this constructor if you don't want to set any clientData
     */
    public HostAPI_StateFreeCallbackHandler() {
        this.clientData = null;
    }

    /**
     * Get the client data
     */
    public Object getClientData() {
        return clientData;
    }

    /**
     * auto generated Axis2 call back method for getVersion method
     * override this method for handling normal response from getVersion operation
     */
    public void receiveResultgetVersion(
        HA.WebServices.HostAPI_StateFreeStub.GetVersionWithLoginResponse result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from getVersion operation
     */
    public void receiveErrorgetVersion(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for getEmployees method
     * override this method for handling normal response from getEmployees operation
     */
    public void receiveResultgetEmployees(
        HA.WebServices.HostAPI_StateFreeStub.GetEmployeesResponse result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from getEmployees operation
     */
    public void receiveErrorgetEmployees(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for applyDimensionSecurity method
     * override this method for handling normal response from applyDimensionSecurity operation
     */
    public void receiveResultapplyDimensionSecurity(
        HA.WebServices.HostAPI_StateFreeStub.ApplyDimensionSecurityResponse result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from applyDimensionSecurity operation
     */
    public void receiveErrorapplyDimensionSecurity(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for update_Dimension_Security_ByUser method
     * override this method for handling normal response from update_Dimension_Security_ByUser operation
     */
    public void receiveResultupdate_Dimension_Security_ByUser(
        HA.WebServices.HostAPI_StateFreeStub.Update_Dimension_Security_ByUserResponse result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from update_Dimension_Security_ByUser operation
     */
    public void receiveErrorupdate_Dimension_Security_ByUser(
        java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for getApprovalRolesForUser method
     * override this method for handling normal response from getApprovalRolesForUser operation
     */
    public void receiveResultgetApprovalRolesForUser(
        HA.WebServices.HostAPI_StateFreeStub.GetApprovalRolesForUserResponse result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from getApprovalRolesForUser operation
     */
    public void receiveErrorgetApprovalRolesForUser(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for segment4_Retrieve method
     * override this method for handling normal response from segment4_Retrieve operation
     */
    public void receiveResultsegment4_Retrieve(
        HA.WebServices.HostAPI_StateFreeStub.Segment4_RetrieveWithLoginResponse result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from segment4_Retrieve operation
     */
    public void receiveErrorsegment4_Retrieve(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for removeApprovalRoleForBudgetEntity method
     * override this method for handling normal response from removeApprovalRoleForBudgetEntity operation
     */
    public void receiveResultremoveApprovalRoleForBudgetEntity(
        HA.WebServices.HostAPI_StateFreeStub.RemoveApprovalRoleForBudgetEntityResponse result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from removeApprovalRoleForBudgetEntity operation
     */
    public void receiveErrorremoveApprovalRoleForBudgetEntity(
        java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for loadEmployees method
     * override this method for handling normal response from loadEmployees operation
     */
    public void receiveResultloadEmployees(
        HA.WebServices.HostAPI_StateFreeStub.LoadEmployeesResponse result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from loadEmployees operation
     */
    public void receiveErrorloadEmployees(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for segment1_Retrieve method
     * override this method for handling normal response from segment1_Retrieve operation
     */
    public void receiveResultsegment1_Retrieve(
        HA.WebServices.HostAPI_StateFreeStub.Segment1_RetrieveWithLoginResponse result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from segment1_Retrieve operation
     */
    public void receiveErrorsegment1_Retrieve(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for getAllSegments method
     * override this method for handling normal response from getAllSegments operation
     */
    public void receiveResultgetAllSegments(
        HA.WebServices.HostAPI_StateFreeStub.GetAllSegmentsWithLoginResponse result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from getAllSegments operation
     */
    public void receiveErrorgetAllSegments(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for segment6_Retrieve method
     * override this method for handling normal response from segment6_Retrieve operation
     */
    public void receiveResultsegment6_Retrieve(
        HA.WebServices.HostAPI_StateFreeStub.Segment6_RetrieveWithLoginResponse result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from segment6_Retrieve operation
     */
    public void receiveErrorsegment6_Retrieve(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for segment3_Retrieve method
     * override this method for handling normal response from segment3_Retrieve operation
     */
    public void receiveResultsegment3_Retrieve(
        HA.WebServices.HostAPI_StateFreeStub.Segment3_RetrieveWithLoginResponse result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from segment3_Retrieve operation
     */
    public void receiveErrorsegment3_Retrieve(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for allowScenarioAccessToUser method
     * override this method for handling normal response from allowScenarioAccessToUser operation
     */
    public void receiveResultallowScenarioAccessToUser(
        HA.WebServices.HostAPI_StateFreeStub.AllowScenarioAccessToUserResponse result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from allowScenarioAccessToUser operation
     */
    public void receiveErrorallowScenarioAccessToUser(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for getALLCurrencies method
     * override this method for handling normal response from getALLCurrencies operation
     */
    public void receiveResultgetALLCurrencies(
        HA.WebServices.HostAPI_StateFreeStub.GetALLCurrenciesWithLoginResponse result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from getALLCurrencies operation
     */
    public void receiveErrorgetALLCurrencies(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for segment2_Retrieve method
     * override this method for handling normal response from segment2_Retrieve operation
     */
    public void receiveResultsegment2_Retrieve(
        HA.WebServices.HostAPI_StateFreeStub.Segment2_RetrieveWithLoginResponse result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from segment2_Retrieve operation
     */
    public void receiveErrorsegment2_Retrieve(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for processDimension method
     * override this method for handling normal response from processDimension operation
     */
    public void receiveResultprocessDimension(
        HA.WebServices.HostAPI_StateFreeStub.ProcessDimensionResponse result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from processDimension operation
     */
    public void receiveErrorprocessDimension(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for removeScenarioAccessFromUser method
     * override this method for handling normal response from removeScenarioAccessFromUser operation
     */
    public void receiveResultremoveScenarioAccessFromUser(
        HA.WebServices.HostAPI_StateFreeStub.RemoveScenarioAccessFromUserResponse result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from removeScenarioAccessFromUser operation
     */
    public void receiveErrorremoveScenarioAccessFromUser(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for segment8_Retrieve method
     * override this method for handling normal response from segment8_Retrieve operation
     */
    public void receiveResultsegment8_Retrieve(
        HA.WebServices.HostAPI_StateFreeStub.Segment8_RetrieveWithLoginResponse result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from segment8_Retrieve operation
     */
    public void receiveErrorsegment8_Retrieve(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for get_UserGroups_By_User method
     * override this method for handling normal response from get_UserGroups_By_User operation
     */
    public void receiveResultget_UserGroups_By_User(
        HA.WebServices.HostAPI_StateFreeStub.Get_UserGroups_By_UserResponse result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from get_UserGroups_By_User operation
     */
    public void receiveErrorget_UserGroups_By_User(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for getAllBudgetScenariosAccessible method
     * override this method for handling normal response from getAllBudgetScenariosAccessible operation
     */
    public void receiveResultgetAllBudgetScenariosAccessible(
        HA.WebServices.HostAPI_StateFreeStub.GetAllBudgetScenariosAccessibleResponse result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from getAllBudgetScenariosAccessible operation
     */
    public void receiveErrorgetAllBudgetScenariosAccessible(
        java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for updateUserInformation method
     * override this method for handling normal response from updateUserInformation operation
     */
    public void receiveResultupdateUserInformation(
        HA.WebServices.HostAPI_StateFreeStub.UpdateUserInformationResponse result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from updateUserInformation operation
     */
    public void receiveErrorupdateUserInformation(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for deleteEmployees method
     * override this method for handling normal response from deleteEmployees operation
     */
    public void receiveResultdeleteEmployees(
        HA.WebServices.HostAPI_StateFreeStub.DeleteEmployeesResponse result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from deleteEmployees operation
     */
    public void receiveErrordeleteEmployees(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for gLData_Retrieve method
     * override this method for handling normal response from gLData_Retrieve operation
     */
    public void receiveResultgLData_Retrieve(
        HA.WebServices.HostAPI_StateFreeStub.GLData_RetrieveWithLoginResponse result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from gLData_Retrieve operation
     */
    public void receiveErrorgLData_Retrieve(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for clear_Data method
     * override this method for handling normal response from clear_Data operation
     */
    public void receiveResultclear_Data(
        HA.WebServices.HostAPI_StateFreeStub.ClearDataWithLoginResponse result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from clear_Data operation
     */
    public void receiveErrorclear_Data(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for getAllApprovalRolesForAllUsers method
     * override this method for handling normal response from getAllApprovalRolesForAllUsers operation
     */
    public void receiveResultgetAllApprovalRolesForAllUsers(
        HA.WebServices.HostAPI_StateFreeStub.GetAllApprovalRolesForAllUsersResponse result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from getAllApprovalRolesForAllUsers operation
     */
    public void receiveErrorgetAllApprovalRolesForAllUsers(
        java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for getDataLoadStatus method
     * override this method for handling normal response from getDataLoadStatus operation
     */
    public void receiveResultgetDataLoadStatus(
        HA.WebServices.HostAPI_StateFreeStub.GetDataLoadStatusResponse result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from getDataLoadStatus operation
     */
    public void receiveErrorgetDataLoadStatus(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for segment5_Retrieve method
     * override this method for handling normal response from segment5_Retrieve operation
     */
    public void receiveResultsegment5_Retrieve(
        HA.WebServices.HostAPI_StateFreeStub.Segment5_RetrieveWithLoginResponse result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from segment5_Retrieve operation
     */
    public void receiveErrorsegment5_Retrieve(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for getBudgetScenariosAccessibleToUser method
     * override this method for handling normal response from getBudgetScenariosAccessibleToUser operation
     */
    public void receiveResultgetBudgetScenariosAccessibleToUser(
        HA.WebServices.HostAPI_StateFreeStub.GetBudgetScenariosAccessibleToUserResponse result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from getBudgetScenariosAccessibleToUser operation
     */
    public void receiveErrorgetBudgetScenariosAccessibleToUser(
        java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for transfer_Data method
     * override this method for handling normal response from transfer_Data operation
     */
    public void receiveResulttransfer_Data(
        HA.WebServices.HostAPI_StateFreeStub.TransferDataWithLoginResponse result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from transfer_Data operation
     */
    public void receiveErrortransfer_Data(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for segment7_Retrieve method
     * override this method for handling normal response from segment7_Retrieve operation
     */
    public void receiveResultsegment7_Retrieve(
        HA.WebServices.HostAPI_StateFreeStub.Segment7_RetrieveWithLoginResponse result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from segment7_Retrieve operation
     */
    public void receiveErrorsegment7_Retrieve(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for load_Data method
     * override this method for handling normal response from load_Data operation
     */
    public void receiveResultload_Data(
        HA.WebServices.HostAPI_StateFreeStub.BulkStringLoadWithLoginResponse result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from load_Data operation
     */
    public void receiveErrorload_Data(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for getAllCurrencyTypes method
     * override this method for handling normal response from getAllCurrencyTypes operation
     */
    public void receiveResultgetAllCurrencyTypes(
        HA.WebServices.HostAPI_StateFreeStub.GetAllCurrencyTypesWithLoginResponse result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from getAllCurrencyTypes operation
     */
    public void receiveErrorgetAllCurrencyTypes(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for get_Dimension_Security_By_Segment method
     * override this method for handling normal response from get_Dimension_Security_By_Segment operation
     */
    public void receiveResultget_Dimension_Security_By_Segment(
        HA.WebServices.HostAPI_StateFreeStub.Get_Dimension_Security_By_SegmentResponse result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from get_Dimension_Security_By_Segment operation
     */
    public void receiveErrorget_Dimension_Security_By_Segment(
        java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for getDataLoadHorizonExecutionState method
     * override this method for handling normal response from getDataLoadHorizonExecutionState operation
     */
    public void receiveResultgetDataLoadHorizonExecutionState(
        HA.WebServices.HostAPI_StateFreeStub.GetDataLoadHorizonExecutionStateResponse result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from getDataLoadHorizonExecutionState operation
     */
    public void receiveErrorgetDataLoadHorizonExecutionState(
        java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for addApprovalRoleForBudgetEntity method
     * override this method for handling normal response from addApprovalRoleForBudgetEntity operation
     */
    public void receiveResultaddApprovalRoleForBudgetEntity(
        HA.WebServices.HostAPI_StateFreeStub.AddApprovalRoleForBudgetEntityResponse result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from addApprovalRoleForBudgetEntity operation
     */
    public void receiveErroraddApprovalRoleForBudgetEntity(
        java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for mapToUserGroup method
     * override this method for handling normal response from mapToUserGroup operation
     */
    public void receiveResultmapToUserGroup(
        HA.WebServices.HostAPI_StateFreeStub.MapToUserGroupResponse result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from mapToUserGroup operation
     */
    public void receiveErrormapToUserGroup(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for updateFactlessFactSecurityForUser method
     * override this method for handling normal response from updateFactlessFactSecurityForUser operation
     */
    public void receiveResultupdateFactlessFactSecurityForUser(
        HA.WebServices.HostAPI_StateFreeStub.UpdateFactlessFactSecurityForUserResponse result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from updateFactlessFactSecurityForUser operation
     */
    public void receiveErrorupdateFactlessFactSecurityForUser(
        java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for get_Users method
     * override this method for handling normal response from get_Users operation
     */
    public void receiveResultget_Users(
        HA.WebServices.HostAPI_StateFreeStub.Get_UsersResponse result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from get_Users operation
     */
    public void receiveErrorget_Users(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for create_New_User method
     * override this method for handling normal response from create_New_User operation
     */
    public void receiveResultcreate_New_User(
        HA.WebServices.HostAPI_StateFreeStub.CreateNewUserResponse result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from create_New_User operation
     */
    public void receiveErrorcreate_New_User(java.lang.Exception e) {
    }
}
