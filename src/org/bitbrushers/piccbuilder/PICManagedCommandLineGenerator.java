package org.bitbrushers.piccbuilder;

import java.util.ArrayList;
import java.util.Arrays;

import org.eclipse.cdt.managedbuilder.core.BuildException;
import org.eclipse.cdt.managedbuilder.core.IManagedCommandLineInfo;
import org.eclipse.cdt.managedbuilder.core.IOption;
import org.eclipse.cdt.managedbuilder.core.ITool;
import org.eclipse.cdt.managedbuilder.core.IToolChain;
import org.eclipse.cdt.managedbuilder.internal.core.ManagedCommandLineGenerator;

public class PICManagedCommandLineGenerator extends ManagedCommandLineGenerator {

	private static final String OPTION_SUFIX_PROCESSOR = ".option.target.processor";
	private static final String OPTION_SUFIX_OBJECT_FILE_FORMAT = ".option.general.objectfileformat";
	private static final String OPTION_SUFIX_THUMB = ".option.target.thumb";
	private static final String OPTION_SUFIX_THUMB_INTERWORK = ".option.target.thumbinterwork";
	private static final String OPTION_SUFIX_DEBUGGING_LEVEL = ".option.debugging.level";
	private static final String OPTION_SUFIX_DEBUGGING_FORMAT = ".option.debugging.format";
	private static final String OPTION_SUFIX_DEBUGGING_OTHER = ".option.debugging.other";
	private static final String OPTION_SUFIX_DEBUGGING_PROF = ".option.debugging.prof";
	private static final String OPTION_SUFIX_DEBUGGING_GPROF = ".option.debugging.gprof";

	private static final boolean DEBUG_LOCAL = false;

	public PICManagedCommandLineGenerator() {
		;
	}

	public IManagedCommandLineInfo generateCommandLineInfo(ITool oTool,
			String sCommandName, String asFlags[], String sOutputFlag,
			String sOutputPrefix, String sOutputName,
			String asInputResources[], String sCommandLinePattern) {

		return generateCommandLineInfo(oTool, sCommandName, asFlags,
				sOutputFlag, sOutputPrefix, sOutputName, asInputResources,
				sCommandLinePattern, false);
	}

	public IManagedCommandLineInfo generateCommandLineInfo(ITool oTool,
			String sCommandName, String asFlags[], String sOutputFlag,
			String sOutputPrefix, String sOutputName,
			String asInputResources[], String sCommandLinePattern, boolean bFlag) {

		ArrayList<String> oList = new ArrayList<String>();
		oList.addAll(((java.util.Collection<String>) (Arrays
				.asList(((asFlags))))));

		IToolChain oToolChain = (IToolChain) oTool.getParent();
		// IConfiguration iconfiguration = itoolchain.getParent();
		IOption aoOptions[] = oToolChain.getOptions();

		String sProcessor;
		sProcessor = null;
		
		String sObjectFileFormat;
		sObjectFileFormat = null;

		String sThumb;
		sThumb = null;

		String sThumbInterwork;
		sThumbInterwork = null;

		String sDebugLevel;
		sDebugLevel = null;

		String sDebugFormat;
		sDebugFormat = null;

		String sDebugOther;
		sDebugOther = null;

		String sDebugProf;
		sDebugProf = null;

		String sDebugGProf;
		sDebugGProf = null;

		for (int i = 0; i < aoOptions.length; ++i) {
			IOption oOption;
			oOption = aoOptions[i];

			String sID;
			sID = oOption.getId();

			Object oValue;
			oValue = oOption.getValue();

			String sCommand;
			sCommand = oOption.getCommand();

			if (oValue instanceof String) {
				String sVal;
				try {
					sVal = oOption.getStringValue();
				} catch (BuildException e) {
					sVal = null;
				}

				String sEnumCommand;
				try {
					sEnumCommand = oOption.getEnumCommand(sVal);
				} catch (BuildException e1) {
					sEnumCommand = null;
				}

				if (DEBUG_LOCAL)
					System.out.println(oOption.getName() + " " + sID + " "
							+ sVal + " " + sCommand + " " + sEnumCommand);

				if (sID.endsWith(OPTION_SUFIX_PROCESSOR)
						|| sID.indexOf(OPTION_SUFIX_PROCESSOR + ".") > 0) {
					sProcessor = sEnumCommand;
				} else if (sID.endsWith(OPTION_SUFIX_OBJECT_FILE_FORMAT)
						|| sID.indexOf(OPTION_SUFIX_OBJECT_FILE_FORMAT + ".") > 0) {
					sObjectFileFormat = sEnumCommand;
				} else if (sID.endsWith(OPTION_SUFIX_DEBUGGING_LEVEL)
						|| sID.indexOf(OPTION_SUFIX_DEBUGGING_LEVEL + ".") > 0) {
					sDebugLevel = sEnumCommand;
				} else if (sID.endsWith(OPTION_SUFIX_DEBUGGING_FORMAT)
						|| sID.indexOf(OPTION_SUFIX_DEBUGGING_FORMAT + ".") > 0) {
					sDebugFormat = sEnumCommand;
				} else if (sID.endsWith(OPTION_SUFIX_DEBUGGING_OTHER)
						|| sID.indexOf(OPTION_SUFIX_DEBUGGING_OTHER + ".") > 0) {
					sDebugOther = sVal;
				}
			} else if (oValue instanceof Boolean) {
				boolean bVal;
				try {
					bVal = oOption.getBooleanValue();
				} catch (BuildException e) {
					bVal = false;
				}

				if (DEBUG_LOCAL)
					System.out.println(oOption.getName() + " " + sID + " "
							+ bVal + " " + sCommand);

				if (sID.endsWith(OPTION_SUFIX_THUMB)
						|| sID.indexOf(OPTION_SUFIX_THUMB + ".") > 0) {
					if (bVal)
						sThumb = sCommand;
				} else if (sID.endsWith(OPTION_SUFIX_THUMB_INTERWORK)
						|| sID.indexOf(OPTION_SUFIX_THUMB_INTERWORK + ".") > 0) {
					if (bVal)
						sThumbInterwork = sCommand;
				} else if (sID.endsWith(OPTION_SUFIX_DEBUGGING_PROF)
						|| sID.indexOf(OPTION_SUFIX_DEBUGGING_PROF + ".") > 0) {
					if (bVal)
						sDebugProf = sCommand;
				} else if (sID.endsWith(OPTION_SUFIX_DEBUGGING_GPROF)
						|| sID.indexOf(OPTION_SUFIX_DEBUGGING_GPROF + ".") > 0) {
					if (bVal)
						sDebugGProf = sCommand;
				}
			} else {
				if (DEBUG_LOCAL)
					System.out.println(oOption.getName() + " " + sID + " "
							+ oValue + " " + sCommand);
			}
		}

		if (DEBUG_LOCAL)
			System.out.println(sProcessor + " " + sThumb + " "
					+ sThumbInterwork + " " + sDebugLevel + " " + sDebugFormat
					+ " " + sDebugOther + " " + sDebugProf + " " + sDebugGProf);

		if (sProcessor != null && sProcessor.length() > 0)
			oList.add(sProcessor);
		if (sObjectFileFormat != null && sObjectFileFormat.length() > 0)
			oList.add(sObjectFileFormat);
		if (sThumb != null && sThumb.length() > 0)
			oList.add(sThumb);
		if (sThumbInterwork != null && sThumbInterwork.length() > 0)
			oList.add(sThumbInterwork);
		if (sDebugLevel != null && sDebugLevel.length() > 0) {
			oList.add(sDebugLevel);

			if (sDebugFormat != null && sDebugFormat.length() > 0)
				oList.add(sDebugFormat);
		}
		if (sDebugOther != null && sDebugOther.length() > 0)
			oList.add(sDebugOther);
		if (sDebugProf != null && sDebugProf.length() > 0)
			oList.add(sDebugProf);
		if (sDebugGProf != null && sDebugGProf.length() > 0)
			oList.add(sDebugGProf);

		return super.generateCommandLineInfo(oTool, sCommandName, oList
				.toArray(new String[0]), sOutputFlag, sOutputPrefix,
				sOutputName, asInputResources, sCommandLinePattern);
	}
}
