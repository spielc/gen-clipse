package com.google.code.genclipse.debug.ui;

import org.eclipse.debug.core.DebugEvent;
import org.eclipse.debug.core.IDebugEventSetListener;
import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.IConsoleManager;
import org.eclipse.ui.console.MessageConsole;
import org.eclipse.ui.console.MessageConsoleStream;

public class EbuildDebugEventSetListener implements IDebugEventSetListener {
	
	private MessageConsole ebuildConsole;
	private MessageConsoleStream geclipseConsoleStream;
	
	public EbuildDebugEventSetListener() {
		this.ebuildConsole=this.findConsole("EbuildConsole");
	}
	
	public void handleDebugEvents(DebugEvent[] events) {
		for (DebugEvent event:events) {
			if (event.getKind()==DebugEvent.CREATE) {//handle create
				this.ebuildConsole=this.findConsole("EbuildConsole");
				this.ebuildConsole.clearConsole();
				this.geclipseConsoleStream=this.ebuildConsole.newMessageStream();
				ConsolePlugin.getDefault().getConsoleManager().showConsoleView(this.ebuildConsole);
				break;
			}
			else if (event.getKind()==DebugEvent.MODEL_SPECIFIC) {
				this.geclipseConsoleStream.println(event.getData().toString());
			}
		}
	}
	private MessageConsole findConsole(String name) {
	      ConsolePlugin plugin = ConsolePlugin.getDefault();
	      IConsoleManager conMan = plugin.getConsoleManager();
	      IConsole[] existing = conMan.getConsoles();
	      for (int i = 0; i < existing.length; i++)
	         if (name.equals(existing[i].getName()))
	            return (MessageConsole) existing[i];
	      //no console found, so create a new one
	      MessageConsole myConsole = new MessageConsole(name, null);
	      conMan.addConsoles(new IConsole[]{myConsole});
	      return myConsole;
	   }

}
