package decimatepurge.bungee;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

import com.google.common.collect.Iterables;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import decimatepurge.core.Manager;
import decimatepurge.core.Purge;
import decimatepurge.user.User;

public class BungeeManager implements Manager, PluginMessageListener {

	public BungeeManager() {
		Purge.getInstance().getServer().getMessenger().registerOutgoingPluginChannel(Purge.getInstance(), "BungeeCord");
		Purge.getInstance().getServer().getMessenger().registerIncomingPluginChannel(Purge.getInstance(), "BungeeCord",
				this);
	}

	public void send(Player player, String server) {
		ByteArrayDataOutput out = ByteStreams.newDataOutput();
		out.writeUTF("Connect");
		out.writeUTF(server);
		player.sendPluginMessage(Purge.getInstance(), "BungeeCord", out.toByteArray());
	}
	
	public void requestUserInformation(User user){
		ByteArrayDataOutput out = ByteStreams.newDataOutput();
		out.writeUTF("Forward");
		out.writeUTF("lobby");
		out.writeUTF("UserInformationRequest");

		ByteArrayOutputStream msgbytes = new ByteArrayOutputStream();
		DataOutputStream msgout = new DataOutputStream(msgbytes);
		
		try {
			msgout.writeUTF(user.getPlayer().getUniqueId().toString());
		} catch (IOException e) { }

		out.writeShort(msgbytes.toByteArray().length);
		out.write(msgbytes.toByteArray());
		Iterables.getFirst(Bukkit.getOnlinePlayers(), null).sendPluginMessage(Purge.getInstance(), "BungeeCord", out.toByteArray());
	}

	@Override
	public void onDisable() {

	}

	@Override
	public void onPluginMessageReceived(String channel, Player player, byte[] message) {
		if(!channel.equals("BungeeCord")){
			return;
		}
		ByteArrayDataInput input = ByteStreams.newDataInput(message);
		String subchannel = input.readUTF();
		Bukkit.broadcastMessage(subchannel);
		if(subchannel.equalsIgnoreCase("Forward")){
			String subChannel = input.readUTF();
			Bukkit.broadcastMessage(subChannel);
			if(subChannel.equalsIgnoreCase("UserInformationResult")){
				short len = input.readShort();
				byte[] msgbytes = new byte[len];
				input.readFully(msgbytes);

				DataInputStream msgin = new DataInputStream(new ByteArrayInputStream(msgbytes));
				try {
					String uuid = msgin.readUTF();
					String faction = msgin.readUTF();
					Bukkit.broadcastMessage(uuid + ": " + faction);
					Purge.getInstance().getUserManager().getUserByUUID(uuid).setFaction(faction);
				} catch (IOException e) { }
			}
		}
	}

}
