/** 
 * Copyright (c) Krapht, 2011
 * 
 * "LogisticsPipes" is distributed under the terms of the Minecraft Mod Public 
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://www.mod-buildcraft.com/MMPL-1.0.txt
 */

package logisticspipes.request;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import logisticspipes.interfaces.routing.ICraftItems;
import logisticspipes.interfaces.routing.IFilter;
import logisticspipes.interfaces.routing.IRelayItem;
import logisticspipes.interfaces.routing.IRequestItems;
import logisticspipes.routing.LogisticsPromise;
import logisticspipes.utils.ItemIdentifierStack;
import logisticspipes.utils.Pair;


public class CraftingTemplate implements Comparable<CraftingTemplate>{
	
	private ItemIdentifierStack _result;
	private ICraftItems _crafter;
	private ArrayList<Pair<ItemIdentifierStack, IRequestItems>> _required = new ArrayList<Pair<ItemIdentifierStack, IRequestItems>>(9);
	private final int priority;
	
	public CraftingTemplate(ItemIdentifierStack result, ICraftItems crafter, int priority) {
		_result = result;
		_crafter = crafter;
		this.priority = priority;
	}
	
	public void addRequirement(ItemIdentifierStack stack, IRequestItems crafter) {
		for(Pair<ItemIdentifierStack, IRequestItems> i : _required) {
			if(i.getValue1().getItem() == stack.getItem() && i.getValue2() == crafter) {
				i.getValue1().stackSize += stack.stackSize;
				return;
			}
		}
		_required.add(new Pair<ItemIdentifierStack, IRequestItems>(stack, crafter));
	}
	
	public LogisticsPromise generatePromise(int nResultSets, List<IRelayItem> relays) {
		LogisticsPromise promise = new LogisticsPromise();
		promise.item = _result.getItem();
		promise.numberOfItems = _result.stackSize * nResultSets;
		promise.sender = _crafter;
		promise.relayPoints = relays;
		return promise;
	}
	
	public List<Pair<ItemIdentifierStack,IRequestItems>> getSource() {
		return _required;
	}

	public ItemIdentifierStack getResultStack() {
		return _result;
	}
	
	public ICraftItems getCrafter(){
		return _crafter;
	}
	
	public int getPriority() {
		return priority;
	}

	@Override
	public int compareTo(CraftingTemplate o) {
		int c = this.priority-o.priority;
		if(c==0)
			c= _result.compareTo(o._result);
		if(c==0)
			c=_crafter.compareTo(o._crafter);
		return c;
	}
	
	public static class PairPrioritizer implements Comparator<Pair<CraftingTemplate,List<IFilter>>>{

		@Override
		public int compare(Pair<CraftingTemplate,List<IFilter>> o1, Pair<CraftingTemplate,List<IFilter>> o2) {
			return o2.getValue1().priority-o1.getValue1().priority;
		}
		
	}
}
