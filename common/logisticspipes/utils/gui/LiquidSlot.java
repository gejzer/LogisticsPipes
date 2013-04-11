package logisticspipes.utils.gui;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;

public class LiquidSlot extends Slot {
	public LiquidSlot(IInventory par1iInventory, int par2, int par3, int par4) {
		super(par1iInventory, par2, par3, par4);
	}

	public LiquidSlot(Slot slot) {
		super(slot.inventory, slot.getSlotIndex(), slot.xDisplayPosition, slot.yDisplayPosition);
	}
}
