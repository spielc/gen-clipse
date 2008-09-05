package com.google.code.genclipse.ide.editor.syntax;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Vector;

public class EbuildSyntaxBean {
	
	private class EclassFilenameFilter implements FilenameFilter {
		@Override
		public boolean accept(File dir, String name) {
			return name.endsWith(".eclass");
		}
	}
	
	private String[] BASH_KEYWORDS = {"if", "then", "elif","else", "fi"};
	
	private String[] EBUILD_VARIABLES = {"P","PN","PV","PR","PVR","PF","A",
		"DISTDIR","FILESDIR","WORKDIR","S","T","D","SLOT","LICENSE","KEYWORDS",
		"DESCRIPTION","SRC_URI","HOMEPAGE","IUSE","DEPEND","RDEPEND", "RESTRICT"};
	
	private String[] EBUILD_METHODES = {"pkg_setup", "pkg_nofetch",
		"src_unpack", "src_compile", "src_install", "src_test", "pkg_preinst", 
		"pkg_postinst", "pkg_prerm", "pkg_postrm", "pkg_config"};
	
	private String[] EBUILD_BUILDTIN_FUNCTIONS = {"use", "has_version",
		"best_version", "use_with", "use_enable", "check_KV", "keepdir", "econf",
		"einstall", "die", "elog", "einfo", "eerror"};
	
	private Vector<String> eClasses;
	
	public EbuildSyntaxBean() {
		this.eClasses=new Vector<String>();
		this.getEclasses();
	}

	public String[] getBASH_KEYWORDS() {
		return BASH_KEYWORDS;
	}

	public String[] getEBUILD_VARIABLES() {
		return EBUILD_VARIABLES;
	}

	public String[] getEBUILD_METHODES() {
		return EBUILD_METHODES;
	}

	public String[] getEBUILD_BUILDTIN_FUNCTIONS() {
		return EBUILD_BUILDTIN_FUNCTIONS;
	}

	public Vector<String> getEClasses() {
		return eClasses;
	}
	
	private void getEclasses() {
		File f=new File("/usr/portage/eclass");
		String[] eclasses=f.list(new EclassFilenameFilter());
		for (String eclassFile:eclasses) {
			eclassFile=eclassFile.substring(0, eclassFile.indexOf("."));
			this.eClasses.add(eclassFile);
		}
	}
}
