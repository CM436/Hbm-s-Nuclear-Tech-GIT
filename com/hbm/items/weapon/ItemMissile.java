package com.hbm.items.weapon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import com.hbm.items.ModItems;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;

public class ItemMissile extends Item {
	
	public PartType type;
	public PartSize top;
	public PartSize bottom;
	
	public static HashMap<Integer, ItemMissile> parts = new HashMap();
	
	/**
	 * == Chips ==
	 * [0]: inaccuracy
	 * 
	 * == Warheads ==
	 * [0]: type
	 * [1]: strength/radius/cluster count
	 * [2]: weight
	 * 
	 * == Fuselages ==
	 * [0]: type
	 * [1]: tank size
	 * 
	 * == Stability ==
	 * [0]: inaccuracy mod
	 * 
	 * == Thrusters ===
	 * [0]: type
	 * [1]: consumption
	 * [2]: lift strength
	 */
	public Object[] attributes;
	
	public enum PartType {
		CHIP,
		WARHEAD,
		FUSELAGE,
		FINS,
		THRUSTER
	}
	
	public enum PartSize {
		
		//for chips
		ANY,
		//for missile tips and thrusters
		NONE,
		//regular sizes, 1.0m, 1.5m and 2.0m
		SIZE_10,
		SIZE_15,
		SIZE_20
	}
	
	public enum WarheadType {
		
		HE,
		INC,
		BUSTER,
		CLUSTER,
		NUCLEAR,
		TX,
		N2,
		BALEFIRE,
		SCHRAB,
		TAINT,
		CLOUD
	}
	
	public enum FuelType {
		
		KEROSENE,
		SOLID,
		HYDROGEN,
		XENON,
		BALEFIRE
	}
	
	public ItemMissile makeChip(float inaccuracy) {
		
		this.type = PartType.CHIP;
		this.top = PartSize.ANY;
		this.bottom = PartSize.ANY;
		this.attributes = new Object[] { inaccuracy };
		
		parts.put(this.hashCode(), this);
		
		return this;
	}
	
	public ItemMissile makeWarhead(WarheadType type, float punch, float weight, PartSize size) {

		this.type = PartType.WARHEAD;
		this.top = PartSize.NONE;
		this.bottom = size;
		this.attributes = new Object[] { type, punch, weight };
		
		parts.put(this.hashCode(), this);
		
		return this;
	}
	
	public ItemMissile makeFuselage(FuelType type, float fuel, PartSize top, PartSize bottom) {

		this.type = PartType.FUSELAGE;
		this.top = top;
		this.bottom = bottom;
		attributes = new Object[] { type, fuel };
		
		parts.put(this.hashCode(), this);
		
		return this;
	}
	
	public ItemMissile makeStability(float inaccuracy, PartSize size) {

		this.type = PartType.FINS;
		this.top = size;
		this.bottom = size;
		this.attributes = new Object[] { inaccuracy };
		
		parts.put(this.hashCode(), this);
		
		return this;
	}
	
	public ItemMissile makeThruster(FuelType type, float consumption, float lift, PartSize size) {

		this.type = PartType.THRUSTER;
		this.top = size;
		this.bottom = PartSize.NONE;
		this.attributes = new Object[] { type, consumption, lift };
		
		parts.put(this.hashCode(), this);
		
		return this;
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool)
	{
		try {
			switch(type) {
			case CHIP:
				list.add(EnumChatFormatting.BOLD + "Inaccuracy: " + EnumChatFormatting.GRAY + (Float)attributes[0] * 100 + "%");
				break;
			case WARHEAD:
				list.add(EnumChatFormatting.BOLD + "Size: " + EnumChatFormatting.GRAY + getSize(bottom));
				list.add(EnumChatFormatting.BOLD + "Type: " + EnumChatFormatting.GRAY + getWarhead((WarheadType)attributes[0]));
				list.add(EnumChatFormatting.BOLD + "Strength: " + EnumChatFormatting.GRAY + (Float)attributes[1]);
				list.add(EnumChatFormatting.BOLD + "Weight: " + EnumChatFormatting.GRAY + (Float)attributes[2] + "t");
				break;
			case FUSELAGE:
				list.add(EnumChatFormatting.BOLD + "Top size: " + EnumChatFormatting.GRAY + getSize(top));
				list.add(EnumChatFormatting.BOLD + "Bottom size: " + EnumChatFormatting.GRAY + getSize(bottom));
				list.add(EnumChatFormatting.BOLD + "Fuel type: " + EnumChatFormatting.GRAY + getFuel((FuelType)attributes[0]));
				list.add(EnumChatFormatting.BOLD + "Fuel amount: " + EnumChatFormatting.GRAY + (Float)attributes[1] + "l");
				break;
			case FINS:
				list.add(EnumChatFormatting.BOLD + "Size: " + EnumChatFormatting.GRAY + getSize(top));
				list.add(EnumChatFormatting.BOLD + "Inaccuracy: " + EnumChatFormatting.GRAY + (Float)attributes[0] * 100 + "%");
				break;
			case THRUSTER:
				list.add(EnumChatFormatting.BOLD + "Size: " + EnumChatFormatting.GRAY + getSize(top));
				list.add(EnumChatFormatting.BOLD + "Fuel type: " + EnumChatFormatting.GRAY + getFuel((FuelType)attributes[0]));
				list.add(EnumChatFormatting.BOLD + "Fuel consumption: " + EnumChatFormatting.GRAY + (Float)attributes[1] + "l/tick");
				list.add(EnumChatFormatting.BOLD + "Max. payload: " + EnumChatFormatting.GRAY + (Float)attributes[2] + "t");
				break;
			}
		} catch(Exception ex) {
			list.add("### I AM ERROR ###");
		}
	}
	
	public String getSize(PartSize size) {
		
		switch(size) {
		case ANY:
			return "Any";
		case SIZE_10:
			return "1.0m";
		case SIZE_15:
			return "1.5m";
		case SIZE_20:
			return "2.0m";
		default:
			return "None";
		}
	}
	
	public String getWarhead(WarheadType type) {
		
		switch(type) {
		case HE:
			return EnumChatFormatting.YELLOW + "HE";
		case INC:
			return EnumChatFormatting.GOLD + "Incendiary";
		case CLUSTER:
			return EnumChatFormatting.GRAY + "Cluster";
		case BUSTER:
			return EnumChatFormatting.WHITE + "Bunker Buster";
		case NUCLEAR:
			return EnumChatFormatting.DARK_GREEN + "Nuclear";
		case TX:
			return EnumChatFormatting.DARK_PURPLE + "Thermonuclear (TX)";
		case N2:
			return EnumChatFormatting.RED + "N²";
		case BALEFIRE:
			return EnumChatFormatting.GREEN + "BF";
		case SCHRAB:
			return EnumChatFormatting.AQUA + "Schrabidium";
		case TAINT:
			return EnumChatFormatting.DARK_PURPLE + "Taint";
		case CLOUD:
			return EnumChatFormatting.LIGHT_PURPLE + "Cloud";
		default:
			return EnumChatFormatting.BOLD + "N/A";
		}
	}
	
	public String getFuel(FuelType type) {
		
		switch(type) {
		case KEROSENE:
			return EnumChatFormatting.LIGHT_PURPLE + "Kerosene / Peroxide";
		case SOLID:
			return EnumChatFormatting.GOLD + "Solid Fuel";
		case HYDROGEN:
			return EnumChatFormatting.DARK_AQUA + "Hydrogen / Oxygen";
		case XENON:
			return EnumChatFormatting.DARK_PURPLE + "Xenon Gas";
		case BALEFIRE:
			return EnumChatFormatting.GREEN + "BF Inferno Fuel / Peroxide";
		default:
			return EnumChatFormatting.BOLD + "N/A";
		}
	}

}