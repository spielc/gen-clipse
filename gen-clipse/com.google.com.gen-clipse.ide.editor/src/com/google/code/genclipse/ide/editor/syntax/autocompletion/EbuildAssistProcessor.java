package com.google.code.genclipse.ide.editor.syntax.autocompletion;

import java.util.ArrayList;

import com.google.code.genclipse.ide.editor.syntax.IEbuildSyntax;

import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.contentassist.CompletionProposal;
import org.eclipse.jface.text.contentassist.ContextInformation;
import org.eclipse.jface.text.contentassist.ContextInformationValidator;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.jface.text.contentassist.IContentAssistProcessor;
import org.eclipse.jface.text.contentassist.IContextInformation;
import org.eclipse.jface.text.contentassist.IContextInformationValidator;

public class EbuildAssistProcessor implements IContentAssistProcessor, IEbuildSyntax {

	public ICompletionProposal[] computeCompletionProposals(ITextViewer viewer,
			int offset) {
		
		String text = viewer.getDocument().get().substring(0, offset);
		String word = getLastWord(text);
		
		ArrayList list = new ArrayList();
		if(word!=null){
			for(int i=0;i<SYNTAX.getEBUILD_BUILDTIN_FUNCTIONS().length;i++){
				if(SYNTAX.getEBUILD_BUILDTIN_FUNCTIONS()[i].startsWith(word)){
					list.add(new CompletionProposal(
							SYNTAX.getEBUILD_BUILDTIN_FUNCTIONS()[i],
							offset - word.length(),
							word.length(),
							SYNTAX.getEBUILD_BUILDTIN_FUNCTIONS()[i].length(),
							null,
							SYNTAX.getEBUILD_BUILDTIN_FUNCTIONS()[i],
							null,null));
				}
			}//for
			for(int i=0;i<SYNTAX.getEBUILD_METHODES().length;i++){
				if(SYNTAX.getEBUILD_METHODES()[i].startsWith(word)){
					list.add(new CompletionProposal(
							SYNTAX.getEBUILD_METHODES()[i]  + "(){\n\t\n}",
							offset - word.length(),
							word.length(),
							SYNTAX.getEBUILD_METHODES()[i].length() + 5,
							null,
							SYNTAX.getEBUILD_METHODES()[i],
							null,null));
				}
			}//for
			for(int i=0;i<SYNTAX.getEBUILD_VARIABLES().length;i++){
				if(SYNTAX.getEBUILD_VARIABLES()[i].startsWith(word)){
					list.add(new CompletionProposal(
							SYNTAX.getEBUILD_VARIABLES()[i] + " = \"\"",
							offset - word.length(),
							word.length(),
							SYNTAX.getEBUILD_VARIABLES()[i].length() + 4,
							null,
							SYNTAX.getEBUILD_VARIABLES()[i],
							null,null));
				}
			}//for
			for(int i=0;i< SYNTAX.getBASH_KEYWORDS().length;i++){
				if(SYNTAX.getBASH_KEYWORDS()[i].startsWith(word)){
					if(SYNTAX.getBASH_KEYWORDS()[i].equals("if")){
						list.add(new CompletionProposal(
								SYNTAX.getBASH_KEYWORDS()[i] + " (); then",
								offset - word.length(),
								word.length(),
								SYNTAX.getBASH_KEYWORDS()[i].length() + 2,
								null,
								SYNTAX.getBASH_KEYWORDS()[i],
								null,null));
					}
					else{
						list.add(new CompletionProposal(
								SYNTAX.getBASH_KEYWORDS()[i],
								offset - word.length(),
								word.length(),
								SYNTAX.getBASH_KEYWORDS()[i].length(),
								null,
								SYNTAX.getBASH_KEYWORDS()[i],
								null,null));
					}

				}
			}//for
		}
		
		ICompletionProposal[] prop = (ICompletionProposal[])list.toArray(new ICompletionProposal[list.size()]);
		//HTMLUtil.sortCompilationProposal(prop);
		return prop;
	}
	
	private String getLastWord(String text){
		String tmp = text.trim();
		
		int[] indices = new int[] {tmp.lastIndexOf(' '),tmp.lastIndexOf('\n'), tmp.lastIndexOf('\t'),tmp.lastIndexOf("}")};
		
		java.util.Arrays.sort(indices);
		
		int index = indices[indices.length-1];
		
		if(index > 0){
			index++;
			return tmp.substring(index);		
		}
		
		return null;
	}

	public IContextInformation[] computeContextInformation(ITextViewer viewer,int offset) {
		ContextInformation[] info = new ContextInformation[0];
		return info;
	}

	public char[] getCompletionProposalAutoActivationCharacters() {
		return new char[0];
	}

	public char[] getContextInformationAutoActivationCharacters() {
		return new char[0];
	}

	public String getErrorMessage() {
		return "error";
	}

	public IContextInformationValidator getContextInformationValidator() {
		return new ContextInformationValidator(this);
	}

}
