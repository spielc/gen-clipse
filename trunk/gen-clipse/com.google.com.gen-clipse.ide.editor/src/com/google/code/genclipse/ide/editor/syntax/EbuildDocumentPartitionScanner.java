package com.google.code.genclipse.ide.editor.syntax;

import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.rules.EndOfLineRule;
import org.eclipse.jface.text.rules.IPredicateRule;
import org.eclipse.jface.text.rules.IRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.MultiLineRule;
import org.eclipse.jface.text.rules.RuleBasedPartitionScanner;
import org.eclipse.jface.text.rules.RuleBasedScanner;
import org.eclipse.jface.text.rules.Token;
import org.eclipse.jface.text.rules.WordRule;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Display;

public class EbuildDocumentPartitionScanner extends RuleBasedPartitionScanner {
	
	private CommentScanner cScanner;
	private EbuildFunctionScanner fScanner;
	private EbuildInheritScanner iScanner;
	
	public EbuildDocumentPartitionScanner() {
		/*IToken comment =  new Token(new TextAttribute(provider.getColor(EbuildColorProvider.EBUILD_COMMENT)));
		System.out.println(((TextAttribute)comment.getData()).getForeground());*/
		IToken comment=new Token(IEbuildDocumentPartitioner.EBUILD_COMMENT);
	    IToken function=new Token(IEbuildDocumentPartitioner.EBUILD_FUNCTION);
	    IToken inherit=new Token(IEbuildDocumentPartitioner.EBUILD_INHERITS);
	    IPredicateRule[] pRules=new IPredicateRule[4];
	    //rules[0]=new MultiLineRule("", cmt);
	    pRules[0]=new EndOfLineRule("#", comment);
	    pRules[1]=new MultiLineRule("src_","}",function);
	    pRules[2]=new MultiLineRule("pkg_","}",function);
	    pRules[3]=new EndOfLineRule("inherit",inherit);
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
			this.setRules(rules);
		}
	}
	private class EbuildFunctionScanner extends RuleBasedScanner {
		EbuildFunctionScanner(EbuildColorProvider provider) {
			IToken methods = new Token(new TextAttribute(provider.getColor(EbuildColorProvider.EBUILD_METHODS),new Color(Display.getCurrent(),new RGB(255,255,255)),1));
			IToken buildtin_function = new Token(new TextAttribute(provider.getColor(EbuildColorProvider.EBUILD_BUILDTIN_FUNCTIONS),new Color(Display.getCurrent(),new RGB(255,255,255)),1));
			IToken bash_keyword = new Token(new TextAttribute(provider.getColor(EbuildColorProvider.BASH_KEYWORDS),new Color(Display.getCurrent(),new RGB(255,255,255)),1));
			IToken variable = new Token(new TextAttribute(provider.getColor(EbuildColorProvider.EBUILD_VARIABLES),new Color(Display.getCurrent(),new RGB(255,255,255)),1));
			IToken other = new Token(new TextAttribute(provider.getColor(EbuildColorProvider.DEFAULT)));
			WordRule wr = new WordRule(new EbuildWordDetector(),other);
			for(int i=0; i < IEbuildSyntax.SYNTAX.getEBUILD_BUILDTIN_FUNCTIONS().length;i++){
				wr.addWord(IEbuildSyntax.SYNTAX.getEBUILD_BUILDTIN_FUNCTIONS()[i], buildtin_function);
			}//for
			for(int i=0; i < IEbuildSyntax.SYNTAX.getBASH_KEYWORDS().length;i++){
				wr.addWord(IEbuildSyntax.SYNTAX.getBASH_KEYWORDS()[i], bash_keyword);
			}//for
			for(int i=0; i < IEbuildSyntax.SYNTAX.getEBUILD_VARIABLES().length;i++){
				wr.addWord(IEbuildSyntax.SYNTAX.getEBUILD_VARIABLES()[i], variable);
			}//for
			IRule[] rules = new IRule[] {new MultiLineRule("src_",")",methods),new MultiLineRule("pkg_",")",methods),wr};
			this.setRules(rules);			
		}
	}
	private class EbuildInheritScanner extends RuleBasedScanner {
		EbuildInheritScanner(EbuildColorProvider provider) {
			IToken inherit=new Token(new TextAttribute(provider.getColor(EbuildColorProvider.EBUILD_METHODS),new Color(Display.getCurrent(),new RGB(255,255,255)),1));
			IToken other = new Token(new TextAttribute(provider.getColor(EbuildColorProvider.DEFAULT)));
			WordRule wr = new WordRule(new EbuildWordDetector(),other);
			wr.addWord("inherit", inherit);
			IRule[] rules =new IRule[] {wr};
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
	public RuleBasedScanner getEbuildInheritScanner(EbuildColorProvider provider) {
		if (this.iScanner==null)
			this.iScanner=new EbuildInheritScanner(provider);
		return this.iScanner;
	}
}
