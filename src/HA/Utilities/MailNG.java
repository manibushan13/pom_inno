package HA.Utilities;

import HA.Properties.logApp;
import HA.SeleniumLib.Common;

public class MailNG {

	public static void main(String[] args) throws Exception {
		MailTrigger.reportmail();
		Common.Regression_Run_Backup();
		logApp.logger.info("Regression Results Backup Taken Successfully.");
	}
}
