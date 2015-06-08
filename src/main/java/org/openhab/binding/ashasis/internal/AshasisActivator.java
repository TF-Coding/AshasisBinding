package org.openhab.binding.ashasis.internal;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AshasisActivator implements BundleActivator {

	private static Logger logger = LoggerFactory.getLogger(AshasisActivator.class);

	public void start(BundleContext context) throws Exception {
		logger.debug("Ashasis binding has been started.");
	}

	public void stop(BundleContext context) throws Exception {
		logger.debug("Ashasis binding has been stopped.");
	}

}
