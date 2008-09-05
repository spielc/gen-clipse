package com.google.code.genclipse.ide.editor.syntax;

import com.google.code.genclipse.ide.editor.syntax.autocompletion.EbuildAssistProcessor;

import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.contentassist.ContentAssistant;
import org.eclipse.jface.text.contentassist.IContentAssistant;
import org.eclipse.jface.text.presentation.IPresentationReconciler;
import org.eclipse.jface.text.presentation.PresentationReconciler;
import org.eclipse.jface.text.rules.DefaultDamagerRepairer;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.SourceViewerConfiguration;

public class EbuildSourceViewerConfiguration extends SourceViewerConfiguration {
	private final static boolean ENABLE_ASSIST_AUTO_ACTIVATION = true;
	private final static int ASSIST_ACTIVATION_DELAY = 1;
	
	private EbuildColorProvider colorProvider;
	private EbuildCodeScanner scanner;
	private PresentationReconciler reconciler;
	
	public EbuildSourceViewerConfiguration(EbuildColorProvider prov){
		this.colorProvider = prov;
	}
	
	protected EbuildCodeScanner getEbuildCodeScanner(){
		if(scanner == null){
			scanner = new EbuildCodeScanner(colorProvider);
		}
		return scanner;
	}
	
	public IPresentationReconciler getPresentationReconciler(ISourceViewer sourceViewer){
		if(reconciler == null){
			reconciler = new PresentationReconciler();
			DefaultDamagerRepairer dr = new DefaultDamagerRepairer(getEbuildCodeScanner());
			reconciler.setDamager(dr, IDocument.DEFAULT_CONTENT_TYPE);
			reconciler.setRepairer(dr, IDocument.DEFAULT_CONTENT_TYPE);
		}//if
		return reconciler;
	}
	
	public IContentAssistant getContentAssistant(ISourceViewer sourceViewer) {
		ContentAssistant assistant = new ContentAssistant();
		assistant.enableAutoInsert(true);
		EbuildAssistProcessor processor = new EbuildAssistProcessor();
		assistant.setContentAssistProcessor(processor,IDocument.DEFAULT_CONTENT_TYPE);
		assistant.install(sourceViewer);
		
		//IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		assistant.enableAutoActivation(ENABLE_ASSIST_AUTO_ACTIVATION);
		assistant.setAutoActivationDelay(ASSIST_ACTIVATION_DELAY);
		
		return assistant;
	}
	
}
