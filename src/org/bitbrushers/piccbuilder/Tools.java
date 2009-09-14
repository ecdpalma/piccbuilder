package org.bitbrushers.piccbuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.eclipse.cdt.core.envvar.IEnvironmentVariable;
import org.eclipse.cdt.managedbuilder.core.IConfiguration;
import org.eclipse.cdt.managedbuilder.core.ManagedBuildManager;
import org.eclipse.cdt.managedbuilder.gnu.ui.GnuUIPlugin;
import org.eclipse.cdt.utils.WindowsRegistry;
import org.eclipse.cdt.utils.spawner.ProcessFactory;

public class Tools {

	private static final String PROPERTY_OS_NAME = "os.name"; //$NON-NLS-1$
	public static final String PROPERTY_OS_VALUE_WINDOWS = "windows";//$NON-NLS-1$
	public static final String PROPERTY_OS_VALUE_LINUX = "linux";//$NON-NLS-1$
	public static final String PROPERTY_OS_VALUE_MACOSX = "macosx";//$NON-NLS-1$

	public static boolean isPlatform(String sPlatform) {
		return (System.getProperty(PROPERTY_OS_NAME).toLowerCase()
				.startsWith(sPlatform));
	}

	public static boolean isWindows() {
		return (System.getProperty(PROPERTY_OS_NAME).toLowerCase()
				.startsWith(PROPERTY_OS_VALUE_WINDOWS));
	}

	public static boolean isLinux() {
		return (System.getProperty(PROPERTY_OS_NAME).toLowerCase()
				.startsWith(PROPERTY_OS_VALUE_LINUX));
	}
	
	public static boolean isMacOSX() {
		return (System.getProperty(PROPERTY_OS_NAME).toLowerCase()
				.startsWith(PROPERTY_OS_VALUE_MACOSX));
	}

	private static final String SP = " "; //$NON-NLS-1$

	public static String[] exec(String cmd, IConfiguration cfg, String sBinPath) {
		try {
			IEnvironmentVariable vars[] = ManagedBuildManager
					.getEnvironmentVariableProvider().getVariables(cfg, true);
			String env[] = new String[vars.length];
			for (int i = 0; i < env.length; i++) {
				env[i] = vars[i].getName() + "=";
				String value = vars[i].getValue();
				if (value != null)
					env[i] += value;
			}
			Process proc = ProcessFactory.getFactory().exec(cmd.split(SP), env);
			if (proc != null) {

				InputStream ein = proc.getInputStream();
				BufferedReader d1 = new BufferedReader(new InputStreamReader(
						ein));
				ArrayList<String> ls = new ArrayList<String>(10);
				String s;
				while ((s = d1.readLine()) != null) {
					ls.add(s);
				}
				ein.close();
				return ls.toArray(new String[0]);
			}
		} catch (IOException e) {
			GnuUIPlugin.getDefault().log(e);
		}
		return null;
	}

	public static String getLocalMachineValue(String sKey, String sName) {
		WindowsRegistry registry = WindowsRegistry.getRegistry();
		if (null != registry) {
			String s;
			s = registry.getLocalMachineValue(sKey, sName);

			if (s != null) {
				return s;
			}
		}
		return null;
	}

}
