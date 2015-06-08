/**
 * Copyright (c) 2010-2015, openHAB.org and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.ashasis.internal;

import org.openhab.core.events.AbstractEventSubscriber;
import org.openhab.core.items.Item;
import org.openhab.core.types.Command;
import org.openhab.core.types.State;
import org.openhab.model.item.binding.BindingConfigParseException;
import org.openhab.model.item.binding.BindingConfigReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
	

/**
 * Implement this class if you are going create an actively polling service
 * like querying a Website/Device.
 * 
 * @author TF-Coding Webmedia
 * @since 1.0.0
 */
public class AshasisBinding extends AbstractEventSubscriber implements BindingConfigReader {

	private static final Logger logger = 		LoggerFactory.getLogger(AshasisBinding.class);

	public AshasisBinding() {
	}
	
	public String getBindingType() {
		return "ashasis";
	}

	@Override
	public void validateItemType(Item item, String bindingConfig) throws BindingConfigParseException {
		
	}

	public void processBindingConfiguration(String context, Item item, String bindingConfig) throws BindingConfigParseException {
		//	throw new BindingConfigParseException("WoL configuration must contain two parts (ip and macaddress separated by a '#'");
			logger.debug("Binding to itm [{}]", item.getName());
	}

	public void removeConfigurations(String context) {
		
	}
	
	@Override
	public void receiveCommand(String itemName, Command command) {
		// TODO Auto-generated method stub
		this.relay(itemName, command.toString());
		
	}

	@Override
	public void receiveUpdate(String itemName, State newState) {
		// TODO Auto-generated method stub
		this.relay(itemName, newState.toString());
	}

	private void relay(String itemName, String newVal){
		logger.debug("Got event [{}] to [{}]", itemName, newVal);
	}
}
