package com.google.code.genclipse.ide.editor.syntax;

import java.util.*;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Display;


public class EbuildColorProvider {
	public static final RGB EBUILD_VARIABLES = new RGB(0, 0, 128);
	public static final RGB EBUILD_COMMENT = new RGB(0, 128, 0);
	public static final RGB EBUILD_BUILDTIN_FUNCTIONS = new RGB(190,128,0);
	public static final RGB EBUILD_METHODES = new RGB(0,128,255);
	public static final RGB STRING = new RGB(128,0,0);
	public static final RGB DEFAULT = new RGB(0, 0, 0);
	public static final RGB BASH_KEYWORDS = new RGB(0,0,0);
	public static final RGB ECLASSES = new RGB(0,0,0);

	protected Map<RGB, Color> fColorTable = new HashMap<RGB, Color> (4);
	
	public void dispose() {
	       Iterator e = fColorTable.values().iterator();
	       while (e.hasNext())
	                ((Color)e.next()).dispose();
	}
	
	public Color getColor(RGB rgb) {
	       Color color = fColorTable.get(rgb);
	       if (color == null) {
	               color= new Color(Display.getCurrent(), rgb);
	               fColorTable.put(rgb, color);
	       }
	       return color;
	}

}
