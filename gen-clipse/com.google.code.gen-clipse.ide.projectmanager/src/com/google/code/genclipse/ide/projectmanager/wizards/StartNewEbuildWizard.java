package com.google.code.genclipse.ide.projectmanager.wizards;

import java.io.ByteArrayInputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbench;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.ui.wizards.newresource.BasicNewFileResourceWizard;

import com.google.code.genclipse.ide.projectmanager.wizards.util.Helper;

/**
 * This class is used to capsulate the use of a NewEbuildWizard!
 * @author Christoph
 *
 */
public class StartNewEbuildWizard extends BasicNewFileResourceWizard {

	private NewEbuildWizard wizard;
	
	
	/**
	 * @author Christoph
	 * @see org.eclipse.ui.wizards.newresource.BasicNewFileResourceWizard#addPages()
	 */
	@Override
	public void addPages() {
		this.wizard=new NewEbuildWizard("com.google.code.gen-clipse.ide.projectmanager.NewEbuildWizard",this.getSelection());
		this.wizard.setTitle("Create new Ebuild");
		this.wizard.setDescription("Use this wizard to create a new ebuild!");
		this.addPage(this.wizard);	
		System.out.println(this.getPageCount());
	}
	/**
	 * This method is called when the user clicks the finish button in the dialog
	 * @author Christoph
	 * @return true if ebuild-creation was succesfull, false otherwise
	 */
	@Override
	public boolean performFinish() {
		return this.createEbuild();
	}
	/**
	 * This method is used to initialze  the wizard. It sets the window title of the dialog and adds the gentoo-logo
	 * @author Christoph
	 * @param workbench, the workbench
	 * @param sel, a IStructuredselection-Object
	 */
	@Override
	public void init(IWorkbench workbench, IStructuredSelection sel) {
		super.init(workbench, sel);
		this.setWindowTitle("New Ebuild");
		this.setDefaultPageImageDescriptor(Helper.getImageDescriptor("glogo-small.png"));
	}
	/**
	 * This method is usued to actually create a new ebuild-file.
	 * 
	 * <br/><br/>It first checks if the ebuild-name provided by the user is valid, creates the necessary folders afterwards and finally creates the ebuild-file!
	 * @author Christoph 
	 * @return true if creation of new ebuil was successfull, false otherwise
	 */
	protected boolean createEbuild() {
		Pattern namePattern=Pattern.compile("([a-z0-9]+[-+_]?)+");
		Pattern versionPattern=Pattern.compile(".*(-[0-9]+(\\.([0-9]+|ebuild))*[a-z]?((_alpha|_beta|_pre|_rc)[0-9]*)?(-r[0-9]+)?\\.ebuild)");
		Matcher versionMatcher=versionPattern.matcher(this.wizard.getFileName());
		if (!versionMatcher.matches()) { //is the specified version a correct version?
			wizard.setErrorMessage("Invalid ebuild version!\nCurrent ebuild-name: "+this.wizard.getFileName());
			return false;
		}
		String ebuildName=this.wizard.getFileName().substring(0, versionMatcher.start(1)); //the actual name of the ebuild (=user specified name-version information)
		Matcher nameMatcher=namePattern.matcher(ebuildName);
		if (!nameMatcher.matches()) { //is the specified ebuild name valid?
			wizard.setErrorMessage("Invalid ebuild name!\nCurrent ebuild-name: "+this.wizard.getFileName());
			return false;
		}
		IProject project=(IProject)this.getSelection().getFirstElement();
		try {
			IFolder folder=project.getFolder(ebuildName); //get folder with the name of the ebuild in the current ebuild-category!
			if (!folder.exists()) { //does folder exist?
				folder.create(true, true, null); //create new folder
				IFolder tmp=folder.getFolder("files"); //create new subfolder 'files' in the folder
				tmp.create(true, true, null);
			}
			IFile ebuild=folder.getFile(this.wizard.getFileName()); //get file with the complete user-specified name
			ebuild.create(new ByteArrayInputStream(this.initialContent().getBytes()), true, null); //create the new file and set initial content
		} catch (CoreException ce) {
			wizard.setErrorMessage("Creation of ebuild-folder failed!\n"+ce.getLocalizedMessage());
			return false;
		}
		return true;
	}
	/**
	 * This method creates the initial Content of a ebuild-file.
	 * @author Christoph
	 * @return String that contains the initial Content
	 */
	protected String initialContent() {
		StringBuffer content=new StringBuffer();
		content.append("# Copyright 1999-2005 Gentoo Foundation\n");
		content.append("# Distributed under the terms of the GNU General Public License v2\n");
		content.append("# $Header: $\n");
		content.append("\n");
		content.append("\n");
		content.append("\n");
		content.append("SLOT=\"\"\n");
		content.append("LICENSE=\"\"\n");
		content.append("KEYWORDS=\"\"\n");
		content.append("DESCRIPTION=\"\"\n");
		content.append("SRC_URI=\"\"\n");
		content.append("HOMEPAGE=\"\"\n");
		content.append("IUSE=\"\"\n");
		content.append("\n");
		return content.toString();
	}
}
