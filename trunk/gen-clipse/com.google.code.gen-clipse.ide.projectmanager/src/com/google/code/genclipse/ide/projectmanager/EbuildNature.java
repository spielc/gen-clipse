package com.google.code.genclipse.ide.projectmanager;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectNature;
import org.eclipse.core.runtime.CoreException;

/**
 * This class defines a new Project-Nature-object. Can be used to annotate Projects.
 * @author Christoph
 *
 */
public class EbuildNature implements IProjectNature {

	/**
	 * @see org.eclipse.core.resources.IProjectNature#configure()
	 * @author Christoph
	 */
	public void configure() throws CoreException {
	}

	/**
	 * @see org.eclipse.core.resources.IProjectNature#deconfigure()
	 * @author Christoph
	 */
	public void deconfigure() throws CoreException {
		
	}

	/**
	 * @see org.eclipse.core.resources.IProjectNature#getProject()
	 * @author Christoph
	 */
	public IProject getProject() {
		return null;
	}

	/**
	 * @see org.eclipse.core.resources.IProjectNature#setProject(org.eclipse.core.resources.IProject)
	 * @author Christoph
	 */
	public void setProject(IProject arg0) {
	}

}
