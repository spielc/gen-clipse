package com.google.code.genclipse.debug.ui.tabs;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.ui.AbstractLaunchConfigurationTab;
import org.eclipse.debug.ui.ILaunchConfigurationDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Button;
import com.google.code.genclipse.debug.core.launching.EbuildDebuggingConstants;

/**
 * LaunchConfigurationTab for the various ebuild-commands that are supported by GEclipse
 * @author Christoph
 *
 */
public class EbuildCommandsTab extends AbstractLaunchConfigurationTab {

	private Button ebuildClean;
	private Button ebuildManifest;
	private Button ebuildFetch;
	private Button ebuildDigest;
	private Button ebuildCompile;
	private Button ebuildUnpack;
	private Button ebuildTest;
	private Button ebuildPreInstall;
	private Button ebuildInstall;
	private Button ebuildPostInstall;
	private EbuildCommandsTabSelectionListener listener;
	
	/**
	 * Private class for a SelectionAdapter
	 * @author Christoph
	 *
	 */
	private class EbuildCommandsTabSelectionListener extends SelectionAdapter {

		/**
		 * @author Christoph
		 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
		 */
		@Override
		public void widgetSelected(SelectionEvent e) {
			updateLaunchConfigurationDialog();
		}
		
		
	}
	
	/**
	 * Constructor for EbuildCommandsTab-objects
	 * @author Christoph
	 */
	public EbuildCommandsTab() {
		super();
		this.listener=new EbuildCommandsTabSelectionListener();
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
		this.ebuildClean=new Button(comp,SWT.CHECK);
		this.ebuildClean.setText("Clean Ebuild");
		this.ebuildClean.addSelectionListener(this.listener);
		this.ebuildManifest=new Button(comp,SWT.CHECK);
		this.ebuildManifest.setText("Create Ebuild-Manifest");
		this.ebuildManifest.addSelectionListener(this.listener);
		this.ebuildFetch=new Button(comp,SWT.CHECK);
		this.ebuildFetch.setText("Fetch files for Ebuild");
		this.ebuildFetch.addSelectionListener(this.listener);
		this.ebuildDigest=new Button(comp,SWT.CHECK);
		this.ebuildDigest.setText("Create Ebuild-Digest");
		this.ebuildDigest.addSelectionListener(this.listener);
		this.ebuildCompile=new Button(comp,SWT.CHECK);
		this.ebuildCompile.setText("Compile Ebuild");
		this.ebuildCompile.addSelectionListener(this.listener);
		this.ebuildUnpack=new Button(comp,SWT.CHECK);
		this.ebuildUnpack.setText("Unpack Ebuild-files");
		this.ebuildUnpack.addSelectionListener(this.listener);
		this.ebuildTest=new Button(comp,SWT.CHECK);
		this.ebuildTest.setText("Test ebuild");
		this.ebuildTest.addSelectionListener(this.listener);
		this.ebuildPreInstall=new Button(comp,SWT.CHECK);
		this.ebuildPreInstall.setText("Perform pre-install actions");
		this.ebuildPreInstall.addSelectionListener(this.listener);
		this.ebuildInstall=new Button(comp,SWT.CHECK);
		this.ebuildInstall.setText("Install Ebuild");
		this.ebuildInstall.addSelectionListener(this.listener);
		this.ebuildPostInstall=new Button(comp,SWT.CHECK);
		this.ebuildPostInstall.setText("Perform post-installation actions");
		this.ebuildPostInstall.addSelectionListener(this.listener);
	}

	/**
	 * @author Christoph
	 * @see org.eclipse.debug.ui.ILaunchConfigurationTab#getName()
	 */
	public String getName() {
		return "Commands";
	}

	/**
	 * @author Christoph
	 * @see org.eclipse.debug.ui.ILaunchConfigurationTab#initializeFrom(org.eclipse.debug.core.ILaunchConfiguration)
	 */
	public void initializeFrom(ILaunchConfiguration configuration) {
		try {
			this.processMap((Map<String,String>)configuration.getAttribute(EbuildDebuggingConstants.EBUILD_COMMANDS, (Map)null));
		} catch (CoreException ce) {
			return;
		}
	}

	/**
	 * @author Christoph
	 * @see org.eclipse.debug.ui.ILaunchConfigurationTab#performApply(org.eclipse.debug.core.ILaunchConfigurationWorkingCopy)
	 */
	public void performApply(ILaunchConfigurationWorkingCopy copy) {
		try {
			Map<String,String> map=(Map<String,String>)copy.getAttribute(EbuildDebuggingConstants.EBUILD_COMMANDS, (Map)null);
			if (map!=null) {
				map.clear();
				map.put(EbuildDebuggingConstants.EBUILD_CLEAN, Boolean.toString(this.ebuildClean.getSelection()));
				map.put(EbuildDebuggingConstants.EBUILD_COMPILE, Boolean.toString(this.ebuildCompile.getSelection()));
				map.put(EbuildDebuggingConstants.EBUILD_DIGEST, Boolean.toString(this.ebuildDigest.getSelection()));
				map.put(EbuildDebuggingConstants.EBUILD_FETCH, Boolean.toString(this.ebuildFetch.getSelection()));
				map.put(EbuildDebuggingConstants.EBUILD_INSTALL, Boolean.toString(this.ebuildInstall.getSelection()));
				map.put(EbuildDebuggingConstants.EBUILD_MANIFEST, Boolean.toString(this.ebuildManifest.getSelection()));
				map.put(EbuildDebuggingConstants.EBUILD_POSTINSTALL, Boolean.toString(this.ebuildPostInstall.getSelection()));
				map.put(EbuildDebuggingConstants.EBUILD_PREINST, Boolean.toString(this.ebuildPreInstall.getSelection()));
				map.put(EbuildDebuggingConstants.EBUILD_TEST, Boolean.toString(this.ebuildTest.getSelection()));
				map.put(EbuildDebuggingConstants.EBUILD_UNPACK, Boolean.toString(this.ebuildUnpack.getSelection()));
				copy.setAttribute(EbuildDebuggingConstants.EBUILD_COMMANDS, map);
			}
			copy.doSave();
		} catch (CoreException e) {
			return;
		}
	}

	/**
	 * @author Christoph
	 * @see org.eclipse.debug.ui.ILaunchConfigurationTab#setDefaults(org.eclipse.debug.core.ILaunchConfigurationWorkingCopy)
	 */
	public void setDefaults(ILaunchConfigurationWorkingCopy copy) {
		Map<String,String> map=new HashMap<String,String>();
		map.put(EbuildDebuggingConstants.EBUILD_CLEAN, "false");
		map.put(EbuildDebuggingConstants.EBUILD_COMPILE, "true");
		map.put(EbuildDebuggingConstants.EBUILD_DIGEST, "true");
		map.put(EbuildDebuggingConstants.EBUILD_FETCH, "false");
		map.put(EbuildDebuggingConstants.EBUILD_INSTALL, "true");
		map.put(EbuildDebuggingConstants.EBUILD_MANIFEST, "false");
		map.put(EbuildDebuggingConstants.EBUILD_POSTINSTALL, "false");
		map.put(EbuildDebuggingConstants.EBUILD_PREINST, "false");
		map.put(EbuildDebuggingConstants.EBUILD_TEST, "false");
		map.put(EbuildDebuggingConstants.EBUILD_UNPACK, "true");
		copy.setAttribute(EbuildDebuggingConstants.EBUILD_COMMANDS, map);
	}

	/**
	 * This method iterates over the String-keys in the given map and updates the according checkboxes in this tab.
	 * 
	 * <br/><br/>For example,<br/> <code>if (s.equals(EbuildLaunchingConstants.EBUILD_CLEAN))
				this.ebuildClean.setSelection(b);</code> updates the ebuildClean-selectionstate
	 * @author Christoph
	 * @param map, a Map<String,String>-instance
	 */
	private void processMap(Map<String,String> map) {
		for(String s:map.keySet()) {
			boolean b=Boolean.parseBoolean(map.get(s));
			if (s.equals(EbuildDebuggingConstants.EBUILD_CLEAN))
				this.ebuildClean.setSelection(b);
			if (s.equals(EbuildDebuggingConstants.EBUILD_COMPILE))
				this.ebuildCompile.setSelection(b);
			if (s.equals(EbuildDebuggingConstants.EBUILD_DIGEST))
				this.ebuildDigest.setSelection(b);
			if (s.equals(EbuildDebuggingConstants.EBUILD_FETCH))
				this.ebuildFetch.setSelection(b);
			if (s.equals(EbuildDebuggingConstants.EBUILD_INSTALL))
				this.ebuildInstall.setSelection(b);
			if (s.equals(EbuildDebuggingConstants.EBUILD_MANIFEST))
				this.ebuildManifest.setSelection(b);
			if (s.equals(EbuildDebuggingConstants.EBUILD_POSTINSTALL))
				this.ebuildPostInstall.setSelection(b);
			if (s.equals(EbuildDebuggingConstants.EBUILD_PREINST))
				this.ebuildPreInstall.setSelection(b);
			if (s.equals(EbuildDebuggingConstants.EBUILD_TEST))
				this.ebuildTest.setSelection(b);
			if (s.equals(EbuildDebuggingConstants.EBUILD_UNPACK))
				this.ebuildUnpack.setSelection(b);
		}
	}
}
