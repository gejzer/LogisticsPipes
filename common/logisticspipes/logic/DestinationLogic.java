package logisticspipes.logic;

import logisticspipes.LogisticsPipes;
import logisticspipes.network.GuiIDs;
import logisticspipes.proxy.MainProxy;
import net.minecraft.entity.player.EntityPlayer;

public class DestinationLogic extends BaseRoutingLogic {
	
	@Override
	public void onWrenchClicked(EntityPlayer entityplayer) {
		if (MainProxy.isServer(entityplayer.worldObj)) {
			entityplayer.openGui(LogisticsPipes.instance, GuiIDs.GUI_Freq_Card_ID, worldObj, xCoord, yCoord, zCoord);
		}
	}
	
	@Override
	public void destroy() {}
}
