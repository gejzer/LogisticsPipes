package logisticspipes.network.packets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import logisticspipes.network.NetworkConstants;
import logisticspipes.utils.ItemMessage;

public class PacketItems extends LogisticsPipesPacket {
	
	public List<ItemMessage> items = new LinkedList<ItemMessage>();
	public boolean error = true;
	private int ID = NetworkConstants.MISSING_ITEMS;
	
	public PacketItems() {
		super();
	}
	
	public PacketItems(List<ItemMessage> errors) {
		this();
		this.items = errors;
	}
	
	public PacketItems(List<ItemMessage> errors, int packetID) {
		this(errors);
		ID = packetID;
	}
	
	public PacketItems(List<ItemMessage> items2, boolean flag) {
		this(items2);
		this.error = flag;
	}
		
	@Override
	public void writeData(DataOutputStream data) throws IOException {
		data.writeBoolean(error);
		for(ItemMessage error:items) {
			data.write(1);
			data.writeInt(error.id);
			data.writeInt(error.data);
			data.writeInt(error.amount);
		}
		data.write(0);
	}

	@Override
	public void readData(DataInputStream data) throws IOException {
		this.error = data.readBoolean();
		while(data.read() != 0) {
			ItemMessage error = new ItemMessage();
			error.id = data.readInt();
			error.data = data.readInt();
			error.amount = data.readInt();
			items.add(error);
		}
	}

	@Override
	public int getID() {
		return ID;
	}
}
