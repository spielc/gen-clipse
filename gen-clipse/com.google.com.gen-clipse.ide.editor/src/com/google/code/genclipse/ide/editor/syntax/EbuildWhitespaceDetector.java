package com.google.code.genclipse.ide.editor.syntax;

import org.eclipse.jface.text.rules.IWhitespaceDetector;

public class EbuildWhitespaceDetector implements IWhitespaceDetector {

	public boolean isWhitespace(char c) {
		return (c== ' ' || c=='\t' || c=='\n' || c=='\r');
	}

}
