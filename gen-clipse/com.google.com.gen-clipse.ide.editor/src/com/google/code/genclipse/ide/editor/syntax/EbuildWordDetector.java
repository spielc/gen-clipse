package com.google.code.genclipse.ide.editor.syntax;

import org.eclipse.jface.text.rules.IWordDetector;

public class EbuildWordDetector implements IWordDetector {
	private String startList="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private String wordList="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ_-";

	public boolean isWordPart(char c) {
		return wordList.indexOf(c) != -1;
	}

	public boolean isWordStart(char c) {
		return startList.indexOf(c) != -1;
	}

}
