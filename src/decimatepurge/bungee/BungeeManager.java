package decimatepurge.bungee;

import org.bukkit.entity.Player;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import decimatepurge.core.Manager;
import decimatepurge.core.Purge;

public class BungeeManager implements Manager {

	public BungeeManager() {
		Purge.getInstance().getServer().getMessenger().registerOutgoingPluginChannel(Purge.getInstance(), "BungeeCord");
	}

	public void send(Player player, String server) {
		ByteArrayDataOutput out = ByteStreams.newDataOutput();
		out.writeUTF("Connect");
		out.writeUTF(server);
		player.sendPluginMessage(Purge.getInstance(), "BungeeCord", out.toByteArray());
	}	


	@Override
	public void onDisable() {

	}

}
