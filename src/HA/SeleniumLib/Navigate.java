package HA.SeleniumLib;

import java.io.FileInputStream;
import java.util.Properties;

import HA.TestAutomation.Driver;
import HA.TestAutomation.Component.Common;

public class Navigate {
	
	public final static String PROPERTYFILENAME = "config/NAVIGATE.properties";
	private static Properties properties = new Properties();
	static String Screen = null;
	static String id = null;
	
	public static void navigateToPage(NavigatePage page) throws Exception
	{
		properties.load(new FileInputStream(PROPERTYFILENAME));
		Common.switchFrames(0);	
		Common.switchFrames(1);	
		switch (page)
		{
			case OperatingBudget:
				Sync.waitPresenceOfElementLocated("id", "leftNav11");
				Screen = "BUDGETCONTROLPANEL";
				break;
			case SalesForecasts:
				Sync.waitPresenceOfElementLocated("id", "leftNav592");
				Screen = "SALESCONTROLPANEL";
				break;
			case Consolidation:
				Sync.waitPresenceOfElementLocated("id", "topNav2");
				Screen = "CONSOLIDATIONCONTROLPANEL";
				break;
			case Reports:
				Sync.waitPresenceOfElementLocated("id", "topNav3");
				Screen = "REPORTS";
				break;
			case UserManagement:
				Sync.waitPresenceOfElementLocated("id", "leftNav541");
				Screen = "USERMANAGEMENT";
				break;
			case ScenarioSetup:
				Sync.waitPresenceOfElementLocated("id", "leftNav511");
				Screen = "SCENARIOSETUP";
				break;
			case SecurityAdministration:
				Sync.waitPresenceOfElementLocated("id", "leftNav542");
				Screen = "SECURITYADMINISTRATION";
				break;
			case ConfigurationTasks:
				Sync.waitPresenceOfElementLocated("id", "leftNav10000");
				Screen = "CONFIGURATIONTASKS";
				break;
			case WorkdaysSetup:
				Sync.waitPresenceOfElementLocated("id", "leftNav514");
				Screen = "WORKDAYSSETUP";
				break;
			case CloudScheduler:
				Sync.waitPresenceOfElementLocated("id", "leftNav544");
				Screen = "CLOUDSCHEDULER";
				break;
			case CurrencySetup:
				Sync.waitPresenceOfElementLocated("id", "leftNav533");
				Screen = "CURRENCYSETUP";
				break;
			case ExchangeRates:
				Sync.waitPresenceOfElementLocated("id", "leftNav535");
				Screen = "EXCHANGERATES";
				break;
			case CurrencyExceptions:
				Sync.waitPresenceOfElementLocated("id", "leftNav631");
				Screen = "CURRENCYEXCEPTIONS";
				break;
			case HierarchyManagement_SegmentHierarchies:
				Sync.waitPresenceOfElementLocated("id", "leftNav651");
				Screen = "SEGMENTHIERARCHIESUHM";
				break;
			case CommonCOA:
				Sync.waitPresenceOfElementLocated("id", "leftNav532");
				Screen = "COMMONCOA";
				break;
			case ChartOfAccounts_SegmentHierarchies:
				Sync.waitPresenceOfElementLocated("id", "leftNav534");
				Screen = "SEGMENTHIERARCHIESCOA";
				break;
			case BudgetHierarchies:
				Sync.waitPresenceOfElementLocated("id", "leftNav512");
				Screen = "BUDGETHIERARCHIES";
				break;
			case AttributeSetup:
				Sync.waitPresenceOfElementLocated("id", "leftNav536");
				Screen = "ATTRIBUTESETUP";
				break;
			case GlobalFields:
				Sync.waitPresenceOfElementLocated("id", "leftNav523");
				Screen = "GLOBALFIELDS";
				break;
			case LineItemCategory:
				Sync.waitPresenceOfElementLocated("id", "leftNav522");
				Screen = "LINEITEMCATEGORY";
				break;
			case Attributes:
				Sync.waitPresenceOfElementLocated("id", "leftNav543");
				Screen = "ATTRIBUTES";
				break;
			case BudgetTemplates_TemplateSetup:
				Sync.waitPresenceOfElementLocated("id", "leftNav521");
				Screen = "TEMPLATESETUPBUDGET";
				break;
			case DetailedHR:
				Sync.waitPresenceOfElementLocated("id", "leftNav571");
				Screen = "DETAILEDHR";
				break;
			case TemplateBasedHR:
				Sync.waitPresenceOfElementLocated("id", "leftNav588");
				Screen = "TEMPLATEBASEDHR";
				break;
			case ExportEmployeeData:
				Sync.waitPresenceOfElementLocated("id", "leftNav578");
				Screen = "EXPORTEMPLOYEEDATA";
				break;
			case AssetSetup:
				Sync.waitPresenceOfElementLocated("id", "leftNav601");
				Screen = "ASSETSETUP";
				break;
			case TopDownModel:
				Sync.waitPresenceOfElementLocated("id", "leftNav573");
				Screen = "TOPDOWNMODEL";
				break;
			case SimulationEngine:
				Sync.waitPresenceOfElementLocated("id", "leftNav524");
				Screen = "SIMULATIONENGINE";
				break;
			case ActualDataTemplates:
				Sync.waitPresenceOfElementLocated("id", "leftNav902");
				Screen = "ACTUALDATATEMPLATES";
				break;
			case TranslationsSetup:
				Sync.waitPresenceOfElementLocated("id", "leftNav903");
				Screen = "TRANSLATIONSSETUP";
				break;
			case ExportData:
				Sync.waitPresenceOfElementLocated("id", "leftNav911");
				Screen = "EXPORTDATA";
				break;
			case TransactionDetails:
				Sync.waitPresenceOfElementLocated("id", "leftNav913");
				Screen = "TRANSACTIONDETAILS";
				break;
			case DataLoadRules:
				Sync.waitPresenceOfElementLocated("id", "leftNav10014");
				Screen = "DATALOADRULES";
				break;
			case ClearData:
				Sync.waitPresenceOfElementLocated("id", "leftNav914");
				Screen = "CLEARDATA";
				break;
			case SalesForecastExport:
				Sync.waitPresenceOfElementLocated("id", "leftNav10011");
				Screen = "SALESFORECASTEXPORT";
				break;
			case SalesAdministration:
				Sync.waitPresenceOfElementLocated("id", "leftNav1008");
				Screen = "SALESADMINISTRATION";
				break;
			case SalesForecast_SegmentHierarchies:
				Sync.waitPresenceOfElementLocated("id", "leftNav591");
				Screen = "SEGMENTHIERARCHIESSALES";
				break;
			case SalesEntityHierarchy:
				Sync.waitPresenceOfElementLocated("id", "leftNav596");
				Screen = "SALESENTITYHIERARCHY";
				break;
			case SalesForecast_TemplateSetup:
				Sync.waitPresenceOfElementLocated("id", "leftNav594");
				Screen = "TEMPLATESETUPSALES";
				break;
			case MapToCOA:
				Sync.waitPresenceOfElementLocated("id", "leftNav1010");
				Screen = "MAPTOCOA";
				break;
			case PointOfView:
				Sync.waitPresenceOfElementLocated("id", "leftNav331");
				Screen = "POINTOFVIEW";
				break;
			case SetupReportingScenario:
				Sync.waitPresenceOfElementLocated("id", "leftNav332");
				Screen = "SETUPREPORTINGSCENARIO";
				break;
			case LockData:
				Sync.waitPresenceOfElementLocated("id", "leftNav333");
				Screen = "LOCKDATA";
				break;
			case ProcessCubeDimension:
				Sync.waitPresenceOfElementLocated("id", "leftNav334");
				Screen = "PROCESSCUBE&DIMENSION";
				break;
			case DimensionSecurity:
				Sync.waitPresenceOfElementLocated("id", "leftNav338");
				Screen = "DIMENSIONSECURITY";
				break;
			case CubeSettings:
				Sync.waitPresenceOfElementLocated("id", "leftNav340");
				Screen = "CUBESETTINGS";
				break;
			case StandardReports:
				Sync.waitPresenceOfElementLocated("id", "leftNav344");
				Screen = "STANDARDREPORTS";
				break;
			case VerifyData:
				Sync.waitPresenceOfElementLocated("id", "leftNav606");
				Screen = "VERIFYDATA";
				break;
			case ViewAuditLog:
				Sync.waitPresenceOfElementLocated("id", "leftNav59");
				Screen = "VIEWAUDITLOG";
				break;
			case SetupApplicationAudit:
				Sync.waitPresenceOfElementLocated("id", "leftNav58");
				Screen = "SETUPAPPLICATIONAUDIT";
				break;
			case DataLoadAuditLog:
				Sync.waitPresenceOfElementLocated("id", "leftNav62");
				Screen = "DATALOADAUDITLOG";
				break;
			case MySettings:
				Sync.waitPresenceOfElementLocated("id", "leftNav56");
				Screen = "MYSETTINGS";
				break;
			case MyHomePage:
				Sync.waitPresenceOfElementLocated("id", "leftNav431");
				Screen = "MYHOMEPAGE";
				break;
			case ScoreCardView:
				Sync.waitPresenceOfElementLocated("id", "leftNav432");
				Screen = "SCORECARDVIEW";
				break;
			case OutOfToleranceReport:
				Sync.waitPresenceOfElementLocated("id", "leftNav433");
				Screen = "OUTOFTOLERANCEREPORT";
				break;
			case InitiativesReports:
				Sync.waitPresenceOfElementLocated("id", "leftNav435");
				Screen = "INITIATIVESREPORTS";
				break;
			case GraphicalView:
				Sync.waitPresenceOfElementLocated("id", "leftNav436");
				Screen = "GRAPHICALVIEW";
				break;
			case KPI_GraphSetup:
				Sync.waitPresenceOfElementLocated("id", "leftNav445");
				Screen = "KPI/GRAPHSETUP";
				break;
			case ScoreCardSetup:
				Sync.waitPresenceOfElementLocated("id", "leftNav441");
				Screen = "SCORECARDSETUP";
				break;
			case ScoreCardAdministration:
				Sync.waitPresenceOfElementLocated("id", "leftNav451");
				Screen = "SCORECARDADMINISTRATION";
				break;
			case MyScoreCardSetup:
				Sync.waitPresenceOfElementLocated("id", "leftNav425");
				Screen = "MYSCORECARDSETUP";
				break;
		}
		System.out.println("screen name ::::::::::::::::::: "+Screen);
		System.out.println("url ::::::::::::::::::: "+properties.getProperty(Screen));
		id = Screen+"ID";
		System.out.println("ID name is :::::::::::"+id);
		Common.loadUrl(properties.getProperty(Screen), properties.getProperty(id));
		Driver.logger.info("Navigated to '"+page+"' page");
	}
	
	public enum NavigatePage {
		OperatingBudget, SalesForecasts, Consolidation, Reports, UserManagement, ScenarioSetup, SecurityAdministration, ConfigurationTasks, 
		WorkdaysSetup, CloudScheduler, CurrencySetup, ExchangeRates, CurrencyExceptions, HierarchyManagement_SegmentHierarchies, CommonCOA, 
		ChartOfAccounts_SegmentHierarchies, BudgetHierarchies, AttributeSetup, GlobalFields, LineItemCategory, BudgetTemplates_TemplateSetup, 
		DetailedHR, TemplateBasedHR, ExportEmployeeData, AssetSetup, TopDownModel, SimulationEngine, ActualDataTemplates, Attributes, 
		TranslationsSetup, ExportData, TransactionDetails, DataLoadRules, ClearData, SalesForecastExport, SalesAdministration, SalesForecast_SegmentHierarchies, 
		SalesEntityHierarchy, SalesForecast_TemplateSetup, MapToCOA, PointOfView, SetupReportingScenario, LockData, ProcessCubeDimension, 
		DimensionSecurity, CubeSettings, StandardReports, VerifyData, ViewAuditLog, SetupApplicationAudit, DataLoadAuditLog, MySettings,  MyScoreCardSetup,
		MyHomePage, ScoreCardView, OutOfToleranceReport, InitiativesReports, GraphicalView, KPI_GraphSetup, ScoreCardSetup, ScoreCardAdministration 
    }

}
