package com.google.code.genclipse.ide.editor;

import java.util.ResourceBundle;

import com.google.code.genclipse.ide.editor.syntax.EbuildColorProvider;
import com.google.code.genclipse.ide.editor.syntax.EbuildSourceViewerConfiguration;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.projection.ProjectionSupport;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.editors.text.TextEditor;
import org.eclipse.ui.editors.text.TextFileDocumentProvider;
import org.eclipse.ui.texteditor.ContentAssistAction;
import org.eclipse.ui.texteditor.ITextEditorActionDefinitionIds;

public class EbuildEditor extends TextEditor {

	private final static String RESOURCE_BUNDLE = "net.sf.geclipse.ide.editor.EbuildEditor";
	private static EbuildEditor editor;
	private EbuildColorProvider colorProvider;
	private ProjectionSupport fProjectionSupport;
	
	public EbuildEditor(){
		super();
		editor = this;
		colorProvider = new EbuildColorProvider();
	}
	
	public void init(IEditorSite site, IEditorInput input) throws PartInitException{
		setSourceViewerConfiguration(new EbuildSourceViewerConfiguration(colorProvider));
		setDocumentProvider(new TextFileDocumentProvider());
		super.init(site, input);
	}//init
	
	public void dispose(){
		super.dispose();
		colorProvider.dispose();
	}//dispose
	
	public ISourceViewer getViewer() {
	       return getSourceViewer();
	}//getViewer
	
	public static EbuildEditor getEditor() {
	       return editor;
	}//getEditor

	protected void createActions(){
		super.createActions();
	    IAction action = new ContentAssistAction(
	    		ResourceBundle.getBundle(RESOURCE_BUNDLE),"ContentAssistProposal", this);
	    action.setActionDefinitionId(ITextEditorActionDefinitionIds.CONTENT_ASSIST_PROPOSALS);
	    setAction("ContentAssistProposal", action);		
	}
	
	
}
