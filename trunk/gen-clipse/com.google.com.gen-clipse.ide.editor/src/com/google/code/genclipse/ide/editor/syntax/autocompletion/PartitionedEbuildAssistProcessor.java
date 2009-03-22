package com.google.code.genclipse.ide.editor.syntax.autocompletion;

import java.util.ArrayList;

import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.contentassist.CompletionProposal;
import org.eclipse.jface.text.contentassist.ContextInformation;
import org.eclipse.jface.text.contentassist.ContextInformationValidator;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.jface.text.contentassist.IContentAssistProcessor;
import org.eclipse.jface.text.contentassist.IContextInformation;
import org.eclipse.jface.text.contentassist.IContextInformationValidator;

import com.google.code.genclipse.ide.editor.syntax.IEbuildDocumentPartitioner;
import com.google.code.genclipse.ide.editor.syntax.IEbuildSyntax;

public class PartitionedEbuildAssistProcessor implements
IContentAssistProcessor, IEbuildSyntax {

	@Override
	public ICompletionProposal[] computeCompletionProposals(ITextViewer viewer,
			int offset) {
		// TODO Continue to rework content assistant
		try {
			String partType=viewer.getDocument().getContentType(offset);
			System.out.println(partType);
			String text = viewer.getDocument().get().substring(0, offset);
			//viewer.getDocument().
			String word = getLastWord(text);
			
			ArrayList<ICompletionProposal> list = new ArrayList<ICompletionProposal>();
			if (word!=null && partType.equals(IEbuildDocumentPartitioner.EBUILD_FUNCTION)) {
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
			else if (word!=null && partType.equals(IEbuildDocumentPartitioner.EBUILD_INHERITS)) {
				for (String eClass:PartitionedEbuildAssistProcessor.SYNTAX.getEClasses()) {
					if (eClass.startsWith(word)) {
						list.add(new CompletionProposal(
								eClass,
								offset - word.length(),
								word.length(),
								eClass.length(),
								null,
								eClass,
								null,null));
					}
				}
			}
			else {
				return new ICompletionProposal[]{};
			}
			ICompletionProposal[] prop = (ICompletionProposal[])list.toArray(new ICompletionProposal[list.size()]);
			return prop;
		} catch (Exception e) {
			return new ICompletionProposal[]{};
		}
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

	@Override
	public IContextInformation[] computeContextInformation(ITextViewer viewer,int offset) {
		ContextInformation[] info = new ContextInformation[0];
		return info;
	}

	@Override
	public char[] getCompletionProposalAutoActivationCharacters() {
		return new char[0];
	}

	@Override
	public char[] getContextInformationAutoActivationCharacters() {
		return new char[0];
	}

	@Override
	public String getErrorMessage() {
		return "error";
	}

	@Override
	public IContextInformationValidator getContextInformationValidator() {
		return new ContextInformationValidator(this);
	}
}
