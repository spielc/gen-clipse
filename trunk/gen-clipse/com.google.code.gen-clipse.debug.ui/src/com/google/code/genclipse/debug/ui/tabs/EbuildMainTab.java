package com.google.code.genclipse.debug.ui.tabs;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.ui.AbstractLaunchConfigurationTab;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import com.google.code.genclipse.debug.core.launching.EbuildDebuggingConstants;


/**
 * This class is the MainTab of the Ebuild-Launcher.
 * It contains combo-boxes to specify:
 * 		1. Project
 * 		2. Ebuild to launch
 * @author Christoph
 *
 */
public class EbuildMainTab extends AbstractLaunchConfigurationTab  {

	private Combo ebuildProjects;
	private Label ebuildProjectsLabel;
	private Combo ebuilds;
	private Label ebuildLabel;
	
	/**
	 * Private Listener-class, that is used to detect changes in this tab and update the the tabgroup accordingly.
	 * @author Christoph
	 *
	 */
	private class EbuildMainTabSelectionListener extends SelectionAdapter implements ModifyListener {

		/**
		 * @see org.eclipse.swt.events.ModifyListener#modifyText(org.eclipse.swt.events.ModifyEvent)
		 * @author Christoph
		 */
		public void modifyText(ModifyEvent e) {
			updateLaunchConfigurationDialog();
		}
		
		/**
		 * @see org.eclipse.swt.events.SelectionListener#widgetSelected(org.eclipse.swt.events.SelectionEvent)
		 * @author Christoph
		 */
		public void widgetSelected(SelectionEvent e) {
			Object source= e.getSource();
			if (source==ebuildProjects) {
				fillCombo(getEbuilds(ebuildProjects.getItem(ebuildProjects.getSelectionIndex())),ebuilds);
			}
		}
		
	}
	
	private EbuildMainTabSelectionListener selectionListener;
	
	/**
	 * Constructor for EbuildMainTab-objects
	 * @author Christoph
	 */
	public EbuildMainTab() {
		super();
		this.selectionListener=new EbuildMainTabSelectionListener();
	}
	
	/**
	 * @author Christoph
	 * @see org.eclipse.debug.ui.ILaunchConfigurationTab#createControl(org.eclipse.swt.widgets.Composite)
	 */
	public void createControl(Composite parent) {
		Composite comp=new Composite(parent,SWT.NONE);
		this.setControl(comp);
		GridLayout layout=new GridLayout(2,true);
		comp.setLayout(layout);
		this.ebuildProjectsLabel=new Label(comp,SWT.NONE);
		this.ebuildProjectsLabel.setText("Ebuild-category:");
		this.ebuildProjects=new Combo(comp,SWT.DROP_DOWN);
		this.ebuildLabel=new Label(comp,SWT.NONE);
		this.ebuildLabel.setText("Ebuild:");
		this.ebuilds=new Combo(comp,SWT.DROP_DOWN);
		this.ebuildProjects.addSelectionListener(this.selectionListener);
		this.ebuilds.addSelectionListener(this.selectionListener);
	}

	/**
	 * Get the Projects that are tagged with the EbuildNature of the current workspace
	 * @return a List-Object which contains the names of the ebuild-projects in the current workspace
	 */
	private List<String> getEbuildProjects() {
		ArrayList<String> projectNames=new ArrayList<String>();
		IWorkspace workspace=ResourcesPlugin.getWorkspace();
		for (IProject project:workspace.getRoot().getProjects()) {
			try {
				if (project.hasNature("net.sf.geclipse.ide.projectmanager.EbuildNature"))
					projectNames.add(project.getName());
			} catch (CoreException ce) {
				ce.printStackTrace();
				return null;
			}
		}
		return projectNames;
	}
	/**
	 * Gets all the ebuilds that are in the Project with Projectname projectname
	 * @author Christoph
	 * @param projectName, the name of the project
	 * @return a List-Object that contains the file-names of the ebuilds in the Ebuild-Project with the name projectname
	 */
	private List<String> getEbuilds(String projectName) {
		ArrayList<String> ebuildNames=new ArrayList<String>();
		IWorkspaceRoot root=ResourcesPlugin.getWorkspace().getRoot();
		IProject project=root.getProject(projectName);
		try {
			for (IResource res:project.members()) {
				if (res instanceof IFolder) {
					for (IResource res2:((IFolder)res).members()) {
						if (res2 instanceof IFile && res2.getFileExtension().equalsIgnoreCase("ebuild")) {
							ebuildNames.add(res2.getName());
						}
					}
				}
			}
		} catch (CoreException ce) {
			ce.printStackTrace();
			return null;
		}
		return ebuildNames;
	}
	/**
	 * @author Christoph
	 * @see org.eclipse.debug.ui.ILaunchConfigurationTab#getName()
	 */
	public String getName() {
		return "Main";
	}

	/**
	 * @author Christoph
	 * @see org.eclipse.debug.ui.ILaunchConfigurationTab#initializeFrom(org.eclipse.debug.core.ILaunchConfiguration)
	 */
	public void initializeFrom(ILaunchConfiguration config) {
		try {
			this.fillCombo((List<String>)config.getAttribute(EbuildDebuggingConstants.EBUILD_CATEGORIES, new ArrayList<String>()), this.ebuildProjects);
			this.fillCombo((List<String>)config.getAttribute(EbuildDebuggingConstants.EBUILD_NAMES, new ArrayList<String>()), this.ebuilds);
		} catch (CoreException ce) {
			return;
		}
		try {
			this.ebuildProjects.setText(config.getAttribute(EbuildDebuggingConstants.EBUILD_CATEGORY, ""));
			this.ebuilds.setText(config.getAttribute(EbuildDebuggingConstants.EBUILD_NAME, ""));
		} catch (CoreException ce) {
			return;
		}
		if (this.ebuildProjects.getText().equals(""))
			this.ebuildProjects.select(0);
		if (this.ebuilds.getText().equals(""))
			this.ebuilds.select(0);
	}

	/**
	 * @author Christoph
	 * @see org.eclipse.debug.ui.ILaunchConfigurationTab#performApply(org.eclipse.debug.core.ILaunchConfigurationWorkingCopy)
	 */
	public void performApply(ILaunchConfigurationWorkingCopy copy) {
		copy.setAttribute(EbuildDebuggingConstants.EBUILD_CATEGORY,this.ebuildProjects.getText());
		copy.setAttribute(EbuildDebuggingConstants.EBUILD_NAME, this.ebuilds.getText());
	}

	/**
	 * @author Christoph
	 * @see org.eclipse.debug.ui.ILaunchConfigurationTab#setDefaults(org.eclipse.debug.core.ILaunchConfigurationWorkingCopy)
	 */
	public void setDefaults(ILaunchConfigurationWorkingCopy copy) {
		List<String> projectNames=this.getEbuildProjects();
		List<String> ebuildNames=this.getEbuilds(projectNames.get(0));
		this.getEbuildProjects();
		try {
			copy.setAttribute(EbuildDebuggingConstants.EBUILD_CATEGORIES, projectNames);
			copy.setAttribute(EbuildDebuggingConstants.EBUILD_CATEGORY, projectNames.get(0));
		} catch (IndexOutOfBoundsException ioobe) {
			copy.setAttribute(EbuildDebuggingConstants.EBUILD_CATEGORY,"");
		}
		try {
			this.getEbuilds(projectNames.get(0));
			copy.setAttribute(EbuildDebuggingConstants.EBUILD_NAME, ebuildNames.get(0));
		} catch (IndexOutOfBoundsException ioobe) {
			copy.setAttribute(EbuildDebuggingConstants.EBUILD_NAME,"");
		}
	}

	/**
	 * In this method the Combobox combo gets filled with the Strings that values contains
	 * @author Christoph
	 * @param values, a List containing Strings
	 * @param combo, a Combo-Object
	 */
	private void fillCombo(List<String> values,Combo combo) {
		for (String s:values) {
			combo.add(s);
		}
			
	}
}
