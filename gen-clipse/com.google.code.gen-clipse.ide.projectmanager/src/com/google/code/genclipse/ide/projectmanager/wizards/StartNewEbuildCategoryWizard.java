package com.google.code.genclipse.ide.projectmanager.wizards;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;

import com.google.code.genclipse.ide.projectmanager.wizards.util.Helper;

/**
 * Wizard that capsulates a NewEbuildCategoryWizard-object
 * @author Christoph
 *
 */
public class StartNewEbuildCategoryWizard extends Wizard implements INewWizard {

	private NewEbuildCategoryWizard wizard;
	private String errorMsg;
	
	/**
	 * @see org.eclipse.jface.wizard.Wizard#performFinish()
	 * @author Christoph
	 */
	@Override
	public boolean performFinish() {
		if (this.createProject()==null) {
			this.wizard.setErrorMessage("Creation of new Ebuild-Category failed!!\nReason: "+this.errorMsg);
			return false;
		}
		this.dispose();
		return true;
	}
	/**
	 * @see org.eclipse.ui.IWorkbenchWizard#init(org.eclipse.ui.IWorkbench, org.eclipse.jface.viewers.IStructuredSelection)
	 * @author Christoph
	 */
	public void init(IWorkbench wb, IStructuredSelection sel) {
		this.errorMsg=null;
		this.setWindowTitle("New Ebuild Category");
		this.setDefaultPageImageDescriptor(Helper.getImageDescriptor("glogo-small.png"));
		this.setNeedsProgressMonitor(true);
	}

	/**
	 * @see org.eclipse.jface.wizard.Wizard#addPages()
	 * @author Christoph
	 */
	@Override
	public void addPages() {
		this.wizard = new NewEbuildCategoryWizard("New Ebuild Category");
		this.wizard.setTitle("New Ebuild Category");
		this.wizard.setDescription("With this Wizard you can create a new Ebuild-Category!");
		this.addPage(this.wizard);
	}
	
	/**
	 * This method is used to create a new project. in the meaning of this project it means we create a new ebuild-category!
	 * 
	 * <br/><br/>First the name given by the user in the dialog is checked for validity. If the name is valid this method tries to setup a new project
	 * sets net.sf.geclipse.ide.projectmanager.EbuildNature as Nature of the new created project and returns the IProject object
	 * 
	 * @return, the newly created IProject-Object
	 */
	private IProject createProject() {
		this.errorMsg=null;
		String projectname=this.wizard.getProjectName();
		Pattern pattern=Pattern.compile("[a-z0-9]+[-][a-z0-9]+");
		Matcher match=pattern.matcher(projectname);
		if (!match.matches()) {
			this.errorMsg="Illegal Name for Ebuild Category!\nLegal examples would be media-video or dev-java";
			return null;
		}
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		IProject project = this.wizard.getProjectHandle();
		IProjectDescription description = workspace.newProjectDescription(project.getName());
		IPath targetPath = this.wizard.getLocationPath();
		targetPath = targetPath.append("/" + projectname);
		IPath workspacePath = Platform.getLocation();
		String tpString = targetPath.toString();
		String wpString = workspacePath.toString();
		if (!workspace.validateProjectLocation(project, targetPath).isOK()) {
			if (tpString.indexOf(wpString) > -1)
				description.setLocation(null);
			else
				return null;
		} else
			description.setLocation(targetPath);
		description.setNatureIds(new String[] { "net.sf.geclipse.ide.projectmanager.EbuildNature" });
		try {
			project.create(description, null);
			if (project.exists()) {
				project.open(null);
				project.refreshLocal(IResource.DEPTH_INFINITE, null);
			} 
			else
				return null;
		} catch (CoreException ce) {
			this.errorMsg=ce.getLocalizedMessage();
			return null;
		}
		return project;

	}
}

