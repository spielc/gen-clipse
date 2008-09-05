package com.google.code.genclipse.ide.projectmanager;

import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 * @author Christoph
 */
public class EbuildProjectPlugin extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "com.google.code.gen-clipse.ide.projectmanager";

	// The shared instance
	private static EbuildProjectPlugin plugin;
	
	/**
	 * The constructor
	 */
	public EbuildProjectPlugin() {
		super();
		EbuildProjectPlugin.plugin = this;
	}

	/**
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 * @author Christoph
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
	}

	/**
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 * @author Christoph
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 * @author Christoph
	 */
	public static EbuildProjectPlugin getDefault() {
		return plugin;
	}

}
