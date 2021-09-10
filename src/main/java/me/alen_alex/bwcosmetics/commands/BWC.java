package me.alen_alex.bwcosmetics.commands;

import me.alen_alex.bwcosmetics.BWCosmetics;
import me.alen_alex.bwcosmetics.cosmetics.victorydance.types.DragonRide;
import me.alen_alex.bwcosmetics.cosmetics.victorydance.types.EnergyDrawer;
import me.alen_alex.bwcosmetics.cosmetics.victorydance.types.Toystick;
import me.alen_alex.bwcosmetics.cosmetics.victorydance.types.WitherRide;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BWC implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        Player player = (Player) commandSender;
            if(strings[0].equalsIgnoreCase("wither"))
                new WitherRide(BWCosmetics.getPlugin(), player, player.getLocation()).startRide();
            if(strings[0].equalsIgnoreCase("toystick"))
                new Toystick(BWCosmetics.getPlugin(),player,player.getLocation()).startdance();
            if(strings[0].equalsIgnoreCase("dragon"))
                new DragonRide(BWCosmetics.getPlugin(),player,player.getLocation()).startRide();
            if(strings[0].equalsIgnoreCase("draw"))
                new EnergyDrawer(BWCosmetics.getPlugin(),player,player.getLocation()).startRide();
        return false;
    }
}
