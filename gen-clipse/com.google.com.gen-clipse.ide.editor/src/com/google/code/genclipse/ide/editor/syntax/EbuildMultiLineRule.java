/**
 * 
 */
package com.google.code.genclipse.ide.editor.syntax;

import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.MultiLineRule;

/**
 * @author christoph
 *
 */
public class EbuildMultiLineRule extends MultiLineRule {

	public EbuildMultiLineRule(String startSequence, String endSequence,
			IToken token) {
		super(startSequence, endSequence, token);
		// TODO Auto-generated constructor stub
	}
	
}
