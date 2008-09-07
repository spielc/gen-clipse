package com.google.code.genclipse.ide.editor.syntax;

import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.rules.EndOfLineRule;
import org.eclipse.jface.text.rules.IPredicateRule;
import org.eclipse.jface.text.rules.IRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.MultiLineRule;
import org.eclipse.jface.text.rules.RuleBasedPartitionScanner;
import org.eclipse.jface.text.rules.RuleBasedScanner;
import org.eclipse.jface.text.rules.SingleLineRule;
import org.eclipse.jface.text.rules.Token;
import org.eclipse.jface.text.rules.WhitespaceRule;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Display;

public class EbuildDocumentPartitionScanner extends RuleBasedPartitionScanner {
	
	private CommentScanner cScanner;
	private EbuildFunctionScanner fScanner;
	
	public EbuildDocumentPartitionScanner() {
		/*IToken comment =  new Token(new TextAttribute(provider.getColor(EbuildColorProvider.EBUILD_COMMENT)));
		System.out.println(((TextAttribute)comment.getData()).getForeground());*/
		IToken comment=new Token(IEbuildDocumentPartitioner.EBUILD_COMMENT);
	    IToken function=new Token(IEbuildDocumentPartitioner.EBUILD_FUNCTION);
	    IPredicateRule[] pRules=new IPredicateRule[2];
	    //rules[0]=new MultiLineRule("", cmt);
	    pRules[0]=new EndOfLineRule("#", comment);
	    pRules[1]=new MultiLineRule("src_","}",function);
	    //rules[2]=new MultiLineRule("\"", "\"", string,'\\');
		
		// rule for generic whitspaces
		//rules[2]=new WhitespaceRule(new EbuildWhitespaceDetector());
						
		//	Add a rule for single quotes
		//rules.add(new SingleLineRule("'", "'", string, '\\'));
	    //rules[1]=new TagRule(tag);
	    //RuleBasedPartitionScanner scanner=new RuleBasedPartitionScanner();
		this.setPredicateRules(pRules);
	    
		this.cScanner=null;
		this.fScanner=null;
		
	    //return scanner;
	}
	private class CommentScanner extends RuleBasedScanner {
		CommentScanner(EbuildColorProvider provider) {
			IToken comment =  new Token(new TextAttribute(provider.getColor(EbuildColorProvider.EBUILD_COMMENT)));
			IRule[] rules = new IRule[] {new EndOfLineRule( "#", comment )};
			setRules(rules);
		}
	}
	private class EbuildFunctionScanner extends RuleBasedScanner {
		EbuildFunctionScanner(EbuildColorProvider provider) {
			IToken methods = new Token(new TextAttribute(provider.getColor(EbuildColorProvider.EBUILD_METHODES),new Color(Display.getCurrent(),new RGB(255,255,255)),1));
			IRule[] rules = new IRule[] {new MultiLineRule("src_",")",methods)};
			this.setRules(rules);			
		}
	}
	public RuleBasedScanner getCommentScanner(EbuildColorProvider provider) {
		if (this.cScanner==null)
			this.cScanner=new CommentScanner(provider);
		return this.cScanner;
	}
	public RuleBasedScanner getEbuildFunctionScanner(EbuildColorProvider provider) {
		if (this.fScanner==null)
			this.fScanner=new EbuildFunctionScanner(provider);
		return this.fScanner;
	}
}
