package decimatepurge.game.module.modules;

import java.lang.reflect.InvocationTargetException;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.PacketType.Play.Server;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.WrappedChatComponent;

import decimatepurge.game.module.ModuleManager.ModuleID;

public class PlayerListHeaderFooterModule extends SimpleEventModule {

	public PlayerListHeaderFooterModule(ModuleID id) {
		super(id);
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent event){
		updateHeaderFooter(event.getPlayer());
	}
	
    private void updateHeaderFooter(Player player) {
        PacketContainer packet = new PacketContainer(Server.PLAYER_LIST_HEADER_FOOTER);
        packet.getChatComponents().write(0, WrappedChatComponent.fromText(ChatColor.RED + "DECIMATEPVP"));
        packet.getChatComponents().write(1, WrappedChatComponent.fromText(ChatColor.GRAY + "play.decimatepvp.com"));
        try {
            ProtocolLibrary.getProtocolManager().sendServerPacket(player, packet);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

}
