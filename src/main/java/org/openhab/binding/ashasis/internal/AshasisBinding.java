/**
 * Copyright (c) 2010-2015, openHAB.org and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.ashasis.internal;

import java.io.InputStream;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.openhab.core.events.AbstractEventSubscriber;
import org.openhab.core.items.Item;
import org.openhab.core.types.Command;
import org.openhab.core.types.State;
import org.openhab.io.net.http.HttpUtil;
import org.openhab.model.item.binding.BindingConfigParseException;
import org.openhab.model.item.binding.BindingConfigReader;
import org.osgi.framework.BundleContext;
import org.osgi.service.cm.ConfigurationException;
import org.osgi.service.cm.ManagedService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author TF-Coding Webmedia
 * @since 1.8.0
 */
public class AshasisBinding extends AbstractEventSubscriber implements
		BindingConfigReader, ManagedService {

	private String apiUrl = "";
	
	private boolean isNumeric(String str) {
		try {
			Double.parseDouble(str);
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}

	private static final Logger logger = LoggerFactory
			.getLogger(AshasisBinding.class);

	public AshasisBinding() {
	}

	public String getBindingType() {
		return "ashasis";
	}

	@Override
	public void updated(Dictionary<String, ?> properties) throws ConfigurationException {
		logger.debug("called");
	}

	public void activate(final BundleContext bundleContext, final Map<String, Object> configuration) throws ConfigurationException {
		modified(configuration);
	}
		
	public void modified(final Map<String, Object> configuration) throws ConfigurationException {
		if(configuration!=null){
			apiUrl = (String)configuration.get("apiurl");
		}else{
			throw new ConfigurationException("ashasis","Missing configuration: ashasis:apiurl");
		}
	}

	@Override
	public void validateItemType(Item item, String bindingConfig)
			throws BindingConfigParseException {

	}

	HashMap<String, Item> registry = new HashMap<String, Item>();

	public void processBindingConfiguration(String context, Item item,
			String bindingConfig) throws BindingConfigParseException {
		// throw new
		// BindingConfigParseException("WoL configuration must contain two parts (ip and macaddress separated by a '#'");
		registry.put(item.getName(), item);
	}

	public void removeConfigurations(String context) {
		registry.remove(context);
	}

	@Override
	public void receiveCommand(String itemName, Command command) {
		if (isNumeric(command.toString())) {
			// it's a ready to-use-value
			this.relay(itemName, command.toString());

		} else if (command.toString().contentEquals("TOGGLE")) {
			// in future catch the commands here and react according
			this.relay(itemName, command.toString());
		}

	}

	@Override
	public void receiveUpdate(String itemName, State newState) {
		// this.relay(itemName, newState.toString());
	}

	private void relay(String itemName, String newVal) {

		logger.debug("Got event [{}] to [{}]", itemName, newVal);
		InputStream in = IOUtils.toInputStream("{\"item\":\"" + itemName
				+ "\",\"value\":\"" + newVal + "\"}");
		HttpUtil.executeUrl("POST", apiUrl + "/controller/push",
				in, "application/json", 2500);
		try {
			in.close();
		} catch (Exception e) {
			// dont care about exception here
		}
	}
}
