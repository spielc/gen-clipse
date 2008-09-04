package com.google.code.genclipse.ide.projectmanager.wizards.util;

import java.net.MalformedURLException;
import java.net.URL;

import org.eclipse.jface.resource.ImageDescriptor;

import com.google.code.genclipse.ide.projectmanager.EbuildProjectPlugin;

/**
 * This class contains static helper-methods that are used in more classes of this project
 * 
 * @author Christoph
 *
 */
public class Helper {
	
	/**
	 * This method create a ImageDescriptor for the given relative Path and returns the object (or null if something goes wrong)
	 * 
	 * @param relativePath, a String that denotes the relative Path to the Image
	 * @return, null if something goes wrong (e.g. image does not exist) or the ImageDescriptor object
	 */
	public static ImageDescriptor getImageDescriptor(String relativePath) {
		String iconFolder = "icons/";
		try {
			EbuildProjectPlugin plugin = EbuildProjectPlugin.getDefault();
			URL installURL = plugin.getBundle().getEntry("/");
			URL imgURL = new URL(installURL, iconFolder + relativePath);
			return ImageDescriptor.createFromURL(imgURL);
		} catch (MalformedURLException mue) {
			mue.printStackTrace();
		}
		return null;
	}
}
