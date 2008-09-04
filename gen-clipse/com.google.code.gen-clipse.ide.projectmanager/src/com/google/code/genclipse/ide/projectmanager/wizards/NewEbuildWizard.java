package com.google.code.genclipse.ide.projectmanager.wizards;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.dialogs.WizardNewFileCreationPage;

/**
 * This class extends WizardNewFileCreationPage and is used to create a new ebuild-file
 * @author Christoph
 *
 */
public class NewEbuildWizard extends WizardNewFileCreationPage {
	
	
	/**
	 * @author Christoph
	 * @param name
	 * @param sel
	 */
	public NewEbuildWizard(String name, IStructuredSelection sel) {
		super(name, sel);
	}
	

	/**
	 * @author Christoph
	 * @see org.eclipse.ui.dialogs.WizardNewFileCreationPage#createControl(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	public void createControl(Composite comp) {
		super.createControl(comp);
	}


		
}
