package com.google.code.genclipse.ide.editor.syntax;

import org.eclipse.jface.text.TextAttribute;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;
import org.eclipse.jface.text.rules.EndOfLineRule;
import org.eclipse.jface.text.rules.IRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.MultiLineRule;
import org.eclipse.jface.text.rules.RuleBasedScanner;
import org.eclipse.jface.text.rules.SingleLineRule;
import org.eclipse.jface.text.rules.Token;
import org.eclipse.jface.text.rules.WhitespaceRule;
import org.eclipse.jface.text.rules.WordRule;

import java.util.*;

public class EbuildCodeScanner extends RuleBasedScanner implements
		IEbuildSyntax {

	public EbuildCodeScanner(EbuildColorProvider provider){
		IToken string = new Token(new TextAttribute(provider.getColor(EbuildColorProvider.STRING)));
		//IToken comment hjgjgjhgjh=  new Token(new TextAttribute(provider.getColor(EbuildColorProvider.EBUILD_COMMENT)));
		IToken variable = new Token(new TextAttribute(provider.getColor(EbuildColorProvider.EBUILD_VARIABLES),new Color(Display.getCurrent(),new RGB(255,255,255)),1));
		IToken other = new Token(new TextAttribute(provider.getColor(EbuildColorProvider.DEFAULT)));
		IToken buildtin_function = new Token(new TextAttribute(provider.getColor(EbuildColorProvider.EBUILD_BUILDTIN_FUNCTIONS),new Color(Display.getCurrent(),new RGB(255,255,255)),1));
		IToken methodes = new Token(new TextAttribute(provider.getColor(EbuildColorProvider.EBUILD_METHODS),new Color(Display.getCurrent(),new RGB(255,255,255)),1));
		IToken bash_keyword = new Token(new TextAttribute(provider.getColor(EbuildColorProvider.BASH_KEYWORDS),new Color(Display.getCurrent(),new RGB(255,255,255)),1));
		IToken eclass = new Token(new TextAttribute(provider.getColor(EbuildColorProvider.ECLASSES),new Color(Display.getCurrent(),new RGB(255,255,255)),1));
				
		List<Object> rules = new ArrayList<Object>();	
				
		// rule for line comment
		//rules.add(new EndOfLineRule("#", comment));
		
		// Add a rule for double quotes
		rules.add(new MultiLineRule("\"", "\"", string,'\\'));
		
		// rule for generic whitspaces
		rules.add(new WhitespaceRule(new EbuildWhitespaceDetector()));
						
		//	Add a rule for single quotes
		rules.add(new SingleLineRule("'", "'", string, '\\'));

		// keywords
		
		WordRule wr = new WordRule(new EbuildWordDetector(),other);
				
		for(int i=0; i < SYNTAX.getEBUILD_VARIABLES().length;i++){
			wr.addWord(SYNTAX.getEBUILD_VARIABLES()[i], variable);
		}//for
		
		for(int i=0; i < SYNTAX.getEBUILD_BUILDTIN_FUNCTIONS().length;i++){
			wr.addWord(SYNTAX.getEBUILD_BUILDTIN_FUNCTIONS()[i], buildtin_function);
		}//for
		
		for(int i=0; i < SYNTAX.getEBUILD_METHODES().length;i++){
			wr.addWord(SYNTAX.getEBUILD_METHODES()[i], methodes);
		}//for

		for(int i=0; i < SYNTAX.getBASH_KEYWORDS().length;i++){
			wr.addWord(SYNTAX.getBASH_KEYWORDS()[i], bash_keyword);
		}//for
		
		for (String eClass:SYNTAX.getEClasses()) {
			wr.addWord(eClass, eclass);
		}
		
		rules.add(wr);
		
		IRule[] result = new IRule[rules.size()];
		rules.toArray(result);
		setRules(result);		
	}//EbuildCodeScanner
}
;
