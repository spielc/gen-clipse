package com.google.code.genclipse.ide.projectmanager.wizards;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.dialogs.WizardNewProjectCreationPage;

/**
 * WizardPage-class for the creation of a new Ebuild-category.
 * @author Christoph
 *
 */
public class NewEbuildCategoryWizard extends WizardNewProjectCreationPage {
	
	/**
	 * Creates a new NewEbuildCategoryWizard-object
	 * @param name, the name for this WizardPage
	 * @author Christoph
	 */
	public NewEbuildCategoryWizard(String name) {
		super(name);
	}

	/**
	 * @see org.eclipse.ui.dialogs.WizardNewProjectCreationPage#createControl(org.eclipse.swt.widgets.Composite)
	 * @author Christoph
	 */
	@Override
	public void createControl(Composite comp) {
		super.createControl(comp);
	}
	
}
