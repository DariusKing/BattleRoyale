package com.klnsyf.battleroyale.command;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class CommandExecutor implements org.bukkit.command.CommandExecutor {

	public boolean onCommand(CommandSender sender, Command arg1, String label, String[] args) {
		if (label.equalsIgnoreCase("br")) {
			if (!sender.hasPermission("battleroyale.br")) {
				sender.sendMessage("[¡ì6¡ìlBattle Royale¡ìr] ¡ìcYou do not have permisson to use this command");
				return true;
			}
			if (args.length == 0) {
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6==========[&b Help&6 ]=========="));
				for (Method method : this.getClass().getDeclaredMethods()) {
					if (!method.isAnnotationPresent(SubCommand.class)) {
						continue;
					}
					SubCommand sub = method.getAnnotation(SubCommand.class);
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
							"&6/br &b" + sub.cmd() + " &3" + sub.arg() + "&6-&a " + sub.des()));
				}
				return true;
			}
			for (Method method : this.getClass().getDeclaredMethods()) {
				if (!method.isAnnotationPresent(SubCommand.class)) {
					continue;
				}
				SubCommand sub = method.getAnnotation(SubCommand.class);
				if (!sub.cmd().equalsIgnoreCase(args[0])) {
					continue;
				}

				try {
					method.invoke(this, sender, args);
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
				return true;
			}
			sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
					"[¡ì6¡ìlBattle Royale¡ìr] &aUndefinded SubCommand:&c" + args[0]));
			return true;
		}
		return false;
	}
	
	@SubCommand(cmd="start")
	public void start(CommandSender sender, String[] args){
		
		
	}

}
