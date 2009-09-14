package org.bitbrushers.piccbuilder;

import org.eclipse.core.runtime.ILog;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Plugin;
import org.eclipse.core.runtime.Status;
import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.IConsoleManager;
import org.eclipse.ui.console.MessageConsole;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class PICCBuilderPlugin extends Plugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "org.bitbrushers.piccbuilder";
	public static final String DEFAULT_LOG = "PIC C Builder Eclipse Plugin Log";

	// The shared instance
	private static PICCBuilderPlugin m_oPlugin;

	/**
	 * The constructor
	 */
	public PICCBuilderPlugin() {
		//System.out.println("PICCBuilderPlugin()");
	}

	public void start(BundleContext oContext) throws Exception {
		super.start(oContext);
		m_oPlugin = this;
	}

	public void stop(BundleContext oContext) throws Exception {
		m_oPlugin = null;
		super.stop(oContext);
	}

	public static PICCBuilderPlugin getDefault() {
		return m_oPlugin;
	}

	public void log(IStatus oStatus) {
		ILog oLog = getLog();
		if (oStatus.getSeverity() >= Status.WARNING) {
			oLog.log(oStatus);
		}
		if (isDebugging()) {
			System.err.print(PLUGIN_ID + ": " + oStatus.getMessage());
			if (oStatus.getCode() != 0) {
				System.err.print("(" + oStatus.getCode() + ")");
			}
			System.out.println("");
			if (oStatus.getException() != null) {
				oStatus.getException().printStackTrace();
			}
		}
	}

	public static void log(String sMsg, Exception oException) {
		getDefault().getLog().log(
				((IStatus) (new Status(Status.ERROR, PLUGIN_ID, sMsg,
						((Throwable) (oException))))));
	}

	public MessageConsole getDefaultConsole() {
		return getConsole(DEFAULT_LOG);
	}

	public MessageConsole getConsole(String sName) {
		// Get a list of all known Consoles from the Global Console Manager and
		// see if a Console with the given name already exists.
		IConsoleManager oConMan = ConsolePlugin.getDefault().getConsoleManager();
		IConsole[] aoConsoles = oConMan.getConsoles();
		for (IConsole oConsole : aoConsoles) {
			if (oConsole.getName().equals(sName)) {
				return (MessageConsole) oConsole;
			}
		}
		// Console not found - create a new one
		MessageConsole oNewConsole = new MessageConsole(sName, null);
		oConMan.addConsoles(new IConsole[] { oNewConsole });
		return oNewConsole;
	}
}
