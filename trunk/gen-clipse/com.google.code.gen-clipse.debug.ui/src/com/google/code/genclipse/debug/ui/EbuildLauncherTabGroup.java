package com.google.code.genclipse.debug.ui;

import org.eclipse.debug.ui.AbstractLaunchConfigurationTabGroup;
import org.eclipse.debug.ui.CommonTab;
import org.eclipse.debug.ui.ILaunchConfigurationDialog;
import org.eclipse.debug.ui.ILaunchConfigurationTab;
import com.google.code.genclipse.debug.ui.tabs.*;

/**
 * This class represents the LauncherTabGroup for Ebuilds.
 * @author Christoph
 *
 */
public class EbuildLauncherTabGroup extends AbstractLaunchConfigurationTabGroup {

	/**
	 * @see org.eclipse.debug.ui.ILaunchConfigurationTabGroup#createTabs(org.eclipse.debug.ui.ILaunchConfigurationDialog, java.lang.String)
	 * @author Christoph
	 */
	public void createTabs(ILaunchConfigurationDialog dialog, String str) {
		try {
			ILaunchConfigurationTab[] tabs=new ILaunchConfigurationTab[] {
					new EbuildMainTab(),
					new EbuildCommandsTab(),
					new CommonTab()
			};
			this.setTabs(tabs);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
