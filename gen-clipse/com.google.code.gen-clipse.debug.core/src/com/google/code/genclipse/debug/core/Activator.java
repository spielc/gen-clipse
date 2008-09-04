package com.google.code.genclipse.debug.core;

import org.eclipse.core.runtime.Plugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 * @author Christoph
 */
public class Activator extends Plugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "net.sf.geclipse.debug.core";

	// The shared instance
	private static Activator plugin;
	
	/**
	 * The constructor
	 * @author Christoph
	 */
	public Activator() {
		plugin = this;
	}

	/**
	 * @author Christoph
	 * @see org.eclipse.core.runtime.Plugins#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
	}

	/**
	 * @author Christoph
	 * @see org.eclipse.core.runtime.Plugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 * @author Christoph
	 * @return the shared instance
	 */
	public static Activator getDefault() {
		return plugin;
	}

}
