package com.google.code.genclipse.ide.projectmanager.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;

import com.google.code.genclipse.ide.projectmanager.wizards.StartNewEbuildWizard;

/**
 * A Delegate-object to handle the right-click on a ebuild-category!
 * 
 * @author Christoph
 * 
 */
public class NewEbuildActionDelegate implements org.eclipse.ui.IObjectActionDelegate{

	private IStructuredSelection selection;
	
	/**
	 * @see org.eclipse.ui.IActionDelegate#run(org.eclipse.jface.action.IAction)
	 * @author Christoph
	 */
	public void run(IAction action) {
		StartNewEbuildWizard wizard=new StartNewEbuildWizard();
		wizard.init(PlatformUI.getWorkbench(), this.selection);
		wizard.addPages();
		WizardDialog dialog=new WizardDialog(wizard.getShell(),wizard);
		dialog.create();
		dialog.open();
	}
	
	/**
	 * @see org.eclipse.ui.IActionDelegate#selectionChanged(org.eclipse.jface.action.IAction, org.eclipse.jface.viewers.ISelection)
	 * @author Christoph
	 */
	public void selectionChanged(IAction action, ISelection sel) {
		this.selection=(IStructuredSelection)sel;
	}
	
	/**
	 * @see org.eclipse.ui.IObjectActionDelegate#setActivePart(org.eclipse.jface.action.IAction, org.eclipse.ui.IWorkbenchPart)
	 * @author Christoph
	 */
	public void setActivePart(IAction arg0, IWorkbenchPart arg1) {
	
	}



}
