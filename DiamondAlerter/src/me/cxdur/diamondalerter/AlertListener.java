package me.cxdur.diamondalerter;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class AlertListener implements Listener {

	public DiamondAlerter plugin;
	
	public AlertListener(DiamondAlerter instance) {
		plugin = instance;
	}
	
	@EventHandler
	public void onBlockBreakEvent(final BlockBreakEvent e) {
	Block b = e.getBlock();
	Player p = e.getPlayer();
		if (!plugin.enableCreative && p.getGameMode().equals(GameMode.CREATIVE)) {
			return;
		}
		if (!plugin.enableOp && (p.isOp())) {
			return;
		}
		if (!plugin.enablePermission && (p.hasPermission(plugin.ignorePermission))) {
			return;
		}
		if (b.getType() == Material.DIAMOND_ORE) {
			int x = b.getLocation().getBlockX();
			int y = b.getLocation().getBlockY();
			int z = b.getLocation().getBlockZ();
			plugin.alertStab(p, x, y, z);
		}
	
	}
}