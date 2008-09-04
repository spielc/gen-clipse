package com.google.code.genclipse.debug.core.launching;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.LinkedList;
import java.util.Queue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.debug.core.DebugEvent;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.model.ILaunchConfigurationDelegate;


/**
 * This class is used to finally execute the in the configuration specified ebuild
 * @author Christoph
 *
 */
public class EbuildLaunchingConfiguration implements ILaunchConfigurationDelegate {

	/**
	 * @author Christoph
	 * @see org.eclipse.debug.core.model.ILaunchConfigurationDelegate#launch(org.eclipse.debug.core.ILaunchConfiguration, java.lang.String, org.eclipse.debug.core.ILaunch, org.eclipse.core.runtime.IProgressMonitor)
	 */
	public void launch(ILaunchConfiguration config, String mode, ILaunch launch,IProgressMonitor monitor) throws CoreException {
		String ebuildName=config.getAttribute(EbuildDebuggingConstants.EBUILD_NAME, "");
		String ebuildCategory=config.getAttribute(EbuildDebuggingConstants.EBUILD_CATEGORY,"");
		if (!(ebuildName.equals("") && ebuildCategory.equals(""))) {
			IWorkspace workspace=ResourcesPlugin.getWorkspace();
			IWorkspaceRoot root=workspace.getRoot();
			Pattern p=Pattern.compile(".+-[0-9]");
			Matcher matcher=p.matcher(ebuildName);
			if (matcher.find()) {
				IProject category=root.getProject(ebuildCategory);
				String folderName=matcher.group();
				folderName=folderName.substring(0, folderName.length()-2);
				IFolder folder=(IFolder)category.findMember(folderName);
				IFile ebuild=folder.getFile(ebuildName);
				String ebuildPath=ebuild.getLocationURI().toString();
				ebuildPath=ebuildPath.substring(5);
				this.executeEbuildApp(config.getAttribute(EbuildDebuggingConstants.EBUILD_COMMANDS, (Map<String,String>)null), ebuildPath);
			}
		}
	}
	/**
	 * In this method first a Queue is filled with the necessary commands
	 * Afterwards for every command in the Queue we allocate a ProcessBuilder-Object, 
	 * fill it with a command-string in this format: "/usr/bin/ebuild "+ebuildFilePath+" "+command, 
	 * start the Process and send the output of the Process back to System.out and print it out.
	 * @param commands, a Map-Object that contains Strings that map to String-representations of Boolean-objects ("true","false")
	 * @param ebuildFilePath, the absolute filepath to the given ebuild-file
	 */
	private void executeEbuildApp(Map<String,String> commands,String ebuildFilePath) {
		Queue<String> queue=new LinkedList<String>();
		if (Boolean.parseBoolean(commands.get(EbuildDebuggingConstants.EBUILD_CLEAN)))
			queue.offer("clean");
		if (Boolean.parseBoolean(commands.get(EbuildDebuggingConstants.EBUILD_FETCH)))
			queue.offer("fetch");
		if (Boolean.parseBoolean(commands.get(EbuildDebuggingConstants.EBUILD_DIGEST)))
			queue.offer("digest");
		if (Boolean.parseBoolean(commands.get(EbuildDebuggingConstants.EBUILD_MANIFEST)))
			queue.offer("manifest");
		if (Boolean.parseBoolean(commands.get(EbuildDebuggingConstants.EBUILD_UNPACK)))
			queue.offer("unpack");
		if (Boolean.parseBoolean(commands.get(EbuildDebuggingConstants.EBUILD_COMPILE)))
			queue.offer("compile");
		if (Boolean.parseBoolean(commands.get(EbuildDebuggingConstants.EBUILD_TEST)))
			queue.offer("test");
		if (Boolean.parseBoolean(commands.get(EbuildDebuggingConstants.EBUILD_PREINST)))
			queue.offer("preinst");
		if (Boolean.parseBoolean(commands.get(EbuildDebuggingConstants.EBUILD_INSTALL)))
			queue.offer("install");
		if (Boolean.parseBoolean(commands.get(EbuildDebuggingConstants.EBUILD_POSTINSTALL)))
			queue.offer("postinst");
		DebugEvent event=new DebugEvent(this,DebugEvent.CREATE);
		DebugPlugin plugin=DebugPlugin.getDefault();
		plugin.fireDebugEventSet(new DebugEvent[]{event});
		try {
			Thread.sleep(5000);
		} catch (InterruptedException ie) {
			ie.printStackTrace();
		}
		while (!queue.isEmpty()) {
			ProcessBuilder builder=new ProcessBuilder();
			builder.command("/usr/bin/ebuild",ebuildFilePath,queue.poll());
			event=new DebugEvent(this,DebugEvent.MODEL_SPECIFIC);
			String header="\nExecuting command: "+builder.command().get(0)+" "+builder.command().get(1)+" "+builder.command().get(2)+"\n";
			//System.out.println(header);
			try {
				Process proc=builder.start();
				BufferedReader reader = new BufferedReader(new InputStreamReader(proc.getInputStream()));
				String line=null;
				while(( line=reader.readLine() ) != null) {
					event.setData(header+line);
					header="";
					plugin.fireDebugEventSet(new DebugEvent[]{event});
				}
				if (!header.equals("")) {
					event.setData(header);
					plugin.fireDebugEventSet(new DebugEvent[]{event});
				}
				proc.waitFor();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
