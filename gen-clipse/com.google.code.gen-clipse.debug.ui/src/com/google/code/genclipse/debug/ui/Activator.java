package com.google.code.genclipse.debug.ui;

import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 * @author Christoph
 */
public class Activator extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "net.sf.geclipse.debug.ui";

	// The shared instancedd
	private static Activator plugin;
	
	private EbuildDebugEventSetListener listener;
	/**
	 * The constructor
	 * @author Christoph
	 */
	public Activator() {
		plugin = this;
		/**/
	}

	/**
	 * @author Christoph
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		this.listener=new EbuildDebugEventSetListener();
		DebugPlugin.getDefault().addDebugEventListener(this.listener);
	}

	/**
	 * @author Christoph
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
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
