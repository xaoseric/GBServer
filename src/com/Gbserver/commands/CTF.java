package com.Gbserver.commands;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.entity.Sheep;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.Gbserver.Utilities;
import com.Gbserver.variables.ChatWriter;
import com.Gbserver.variables.ChatWriterType;
import com.Gbserver.variables.HelpTable;

public class CTF implements CommandExecutor{
	//VARIABLES ARE LOCATED HERE
	//BEGIN OF VARIABLES
	public static World world = Bukkit.getWorld("CTF");
	public static boolean isRunning = false;
	public static Sheep redFlag;
	public static Sheep blueFlag;
	public static Location redFlagLocation = new Location(world,29.5,106,-88.5);
	public static Location blueFlagLocation = new Location(world, 31.5,106,-88.5);
	public static List<Player> red = new LinkedList<Player>();
	public static List<Player> blue = new LinkedList<Player>();
	public static Collection<Integer> tasks = new LinkedList<>();
	public static int frozenid;
	//PRIVATE
	
	private static HelpTable ht = new HelpTable("/ctf [stop/stats]", "Capture-the-flag commands", "", "ctf");
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(label.equalsIgnoreCase("ctf")){
			//Game stopping & statistics.
			if(args.length == 0){
				ht.show(sender);
				return true;
			}
			switch(args[0]){
			case "dk3addblue":
				blue.add(Bukkit.getPlayer(args[1]));
				sender.sendMessage("Player debuggedly added.");
				break;
			case "dk3start":
				startGame();
				sender.sendMessage("Game debuggedly started.");
				break;
			case "stop":
				stopGame();
				ChatWriter.writeTo(sender, ChatWriterType.GAME, "Successfully stopped the CTF game.");
				break;
			case "stats":
				//Statistics here.
				//Collections of statistics...??
				sender.sendMessage("A list of all tasks:");
				for(Integer i : tasks){
					sender.sendMessage(i+"");
				}
				sender.sendMessage("--------------------");
				{
					String output = "Red team players: ";
					for(Player p : red){
						output += p.getName() + ", ";
					}
					sender.sendMessage(output);
				}
				{
					String output = "Blue team players: ";
					for(Player p : blue){
						output += p.getName() + ", ";
					}
					sender.sendMessage(output);
				}
				sender.sendMessage("--------------------");
				sender.sendMessage("Is CTF Running? : "+isRunning);
				break;
			}
			return true;
		}
		return false;
	}
	
	public static void getVariables() {
		redFlag = (Sheep) world.spawnEntity(redFlagLocation, EntityType.SHEEP);
		redFlag.setColor(DyeColor.RED);
		blueFlag = (Sheep) world.spawnEntity(blueFlagLocation, EntityType.SHEEP);
		blueFlag.setColor(DyeColor.BLUE);
		frozenid = Utilities.setFrozen(redFlag, blueFlag);
		//flags are spawned.
	}
	//subvoid
	public static Team getLocationTeam(Player p){
		Location l = p.getLocation();
		//Negative: blue, positive: red
		if(l.getZ() < 0){
			return Team.BLUE;
		}
		if(l.getZ() > 0){
			return Team.RED;
		}
		return Team.undefined;
	}
	
	public static Team getOriginatedTeam(Player p){
		if(red.contains(p) && !blue.contains(p)){
			return Team.RED;
		}
		if(blue.contains(p) && !red.contains(p)){
			return Team.BLUE;
		}
		return Team.undefined;
	}
	
	public static List<Player> allPlayers() {
		List<Player> l = new LinkedList<>();
		l.addAll(red);
		l.addAll(blue);
		return l;
	}
	
	public static void stopGame() {
		isRunning = false;
		redFlag.remove();
		blueFlag.remove();
		red.clear();
		blue.clear();
		for(Integer i : tasks){
			Bukkit.getScheduler().cancelTask(i);
		}
		Bukkit.getScheduler().cancelTask(frozenid);
	}
	
	public static void startGame() {
		isRunning = true;
		ItemStack scissors = new ItemStack(Material.SHEARS);
		{
			ItemMeta sciim;
			(sciim = scissors.getItemMeta()).setDisplayName("Flag Capturer");
			scissors.setItemMeta(sciim);
		}
		ItemStack inksac = new ItemStack(Material.INK_SACK);
		{
			ItemMeta inkim;
			(inkim = inksac.getItemMeta()).setDisplayName("Opponent Capturer");
			inksac.setItemMeta(inkim);
		}
		for(Player p : allPlayers()){
			p.setGameMode(GameMode.SURVIVAL);
			p.getInventory().clear();
			p.getInventory().addItem(scissors, inksac);
		}
	}
}