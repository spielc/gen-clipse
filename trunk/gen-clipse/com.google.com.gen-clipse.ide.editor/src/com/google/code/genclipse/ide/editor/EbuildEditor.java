package com.google.code.genclipse.ide.editor;

import java.util.ResourceBundle;

import com.google.code.genclipse.ide.editor.syntax.EbuildColorProvider;
import com.google.code.genclipse.ide.editor.syntax.EbuildDocumentPartitionScanner;
import com.google.code.genclipse.ide.editor.syntax.EbuildSourceViewerConfiguration;
import com.google.code.genclipse.ide.editor.syntax.IEbuildDocumentPartitioner;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.text.rules.FastPartitioner;
import org.eclipse.jface.text.rules.IPartitionTokenScanner;
import org.eclipse.jface.text.rules.IPredicateRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.MultiLineRule;
import org.eclipse.jface.text.rules.RuleBasedPartitionScanner;
import org.eclipse.jface.text.rules.SingleLineRule;
import org.eclipse.jface.text.rules.Token;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.projection.ProjectionSupport;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.editors.text.TextEditor;
import org.eclipse.ui.editors.text.TextFileDocumentProvider;
import org.eclipse.ui.texteditor.ContentAssistAction;
import org.eclipse.ui.texteditor.IDocumentProvider;
import org.eclipse.ui.texteditor.ITextEditorActionDefinitionIds;

public class EbuildEditor extends TextEditor {

	private final static String RESOURCE_BUNDLE = "com.google.code.genclipse.ide.editor.EbuildEditor";
	private static EbuildEditor editor;
	private EbuildColorProvider colorProvider;
	private static EbuildDocumentPartitionScanner scanner;
	//private ProjectionSupport fProjectionSupport;
	
	public static EbuildDocumentPartitionScanner getScanner() {
		return scanner;
	}

	public EbuildEditor(){
		super();
		editor = this;
		colorProvider = new EbuildColorProvider();
		scanner=new EbuildDocumentPartitionScanner();
	}
	
	public void init(IEditorSite site, IEditorInput input) throws PartInitException{
		setSourceViewerConfiguration(new EbuildSourceViewerConfiguration(colorProvider));
		setDocumentProvider(new TextFileDocumentProvider());
		IDocumentProvider provider=this.getDocumentProvider();
		try {
			provider.connect(input);
			FastPartitioner partitioner=new FastPartitioner(
					EbuildEditor.scanner,
					new String[]{IEbuildDocumentPartitioner.EBUILD_COMMENT,IEbuildDocumentPartitioner.EBUILD_FUNCTION,IEbuildDocumentPartitioner.EBUILD_INHERITS,IEbuildDocumentPartitioner.EBUILD_STRINGS}
					);
			partitioner.connect(provider.getDocument(input));
			provider.getDocument(input).setDocumentPartitioner(partitioner);
		} catch (Exception e) {
			throw new PartInitException(e.getMessage());
		}
		
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
