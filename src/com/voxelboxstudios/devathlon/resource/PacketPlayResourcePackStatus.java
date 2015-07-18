package com.voxelboxstudios.devathlon.resource;

import java.lang.reflect.Field;
import java.util.List;

import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import com.voxelboxstudios.devathlon.Main;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.NetworkManager;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayInResourcePackStatus;

public class PacketPlayResourcePackStatus implements IPacketPlayResourcePackStatus {

	/** Status **/
	
	private Status status;
	
	
	/** Hash **/
	
	private String hash;
	
	
	/** Field **/
	
	private static Field channelField;
	
	
	/** Get status **/
	
	public Status getStatus() {
		return this.status;
	}
	
	
	/** Inject **/
	
	@Override
	public void inject() throws NoSuchFieldException, IllegalAccessException {}


	/** Hash **/
	
	@Override
	public String getHash() {
		return this.hash;
	}

	
	/** Packet receive **/
	
	@Override
	public void onPacketReceive(Object packet, Player p) {
		/** Check object **/
		
		if(!(packet instanceof Packet)) return;
		
		
		/** Field **/
		
		try {
			/** Field **/
			
			Field field = PacketPlayInResourcePackStatus.class.getDeclaredField("b");
			
			field.setAccessible(true);
			
			
			/** Status **/
			
			this.status = Status.byID(((PacketPlayInResourcePackStatus.EnumResourcePackStatus) field.get(packet)).ordinal());
			
			
			/** Field **/
			
			field = PacketPlayInResourcePackStatus.class.getDeclaredField("a");
			
			field.setAccessible(true);
			
			
			/** Hash **/
			
			this.hash = ((String) field.get(packet));
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
		
		if(getStatus() != null && p != null) {
			Main.resourcePackResult(p, getStatus());
		}
	}
	
	
	/** Add channel **/
	
	@Override
	public void addChannelForPlayer(final Player p) {
		/** Check channel field **/
		
		if(channelField == null) {
			/** Get field **/
			
			try {
				channelField = NetworkManager.class.getDeclaredField("channel");
			} catch (NoSuchFieldException | SecurityException e) {
				e.printStackTrace();
			}
			
			
			/** Set accessible **/
			
			channelField.setAccessible(true);
	    } 
		
		try {
	    	/** Player **/
	    	
	    	EntityPlayer ep = ((CraftPlayer) p).getHandle();
	    	
	    	
	    	/** Channel **/
	    	
	    	final Channel channel = (Channel) channelField.get(ep.playerConnection.networkManager);
	    	
	    	
	    	/** Thread **/
	    	
	    	new Thread(new Runnable() {
	    		public void run() {
	    			try {
	    				channel.pipeline().addBefore("packet_handler", "RPApi", new PacketPlayResourcePackStatus.ChannelHandler(p));
	    			} catch (Exception localException) {}
	    		}
	    	}, "Aviation adder").start();
	    } catch(Exception e) {
	    	e.printStackTrace();
	    }
	}

	
	
	/** Remove channel **/
	
	@Override
	public void removeChannelForPlayer(Player p) {
		/** Check channel field **/
		
		if(channelField == null) {
			/** Get field **/
			
			try {
				channelField = NetworkManager.class.getDeclaredField("channel");
			} catch (NoSuchFieldException|SecurityException e){
	    	  	e.printStackTrace();
			}
			
			
			/** Set accessible **/
			
			channelField.setAccessible(true);
	    } 
		
		try {
			/** Player **/
			
			EntityPlayer ep = ((CraftPlayer) p).getHandle();
			
			
			/** Channel **/
			
			final Channel channel = (Channel) channelField.get(ep.playerConnection.networkManager);
			
			
			/** Thread **/
			
			new Thread(new Runnable() {
				public void run() {
					try {
						channel.pipeline().remove("RPApi");
					} catch (Exception localException) {}
				}
			}, "Aviation remover").start();
		} catch(Exception e) {
	      e.printStackTrace();
	    }
	}

	
	/** Channel handler **/
	
	public class ChannelHandler extends ByteToMessageDecoder {
		/** Player **/
		
		private Player p;
    
		
		/** Constructor **/
		
		public ChannelHandler(Player p) {
			this.p = p;
		}
    
		
		/** Read **/
		
		public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
			/** Packet receive **/
			
			if(PacketPlayInResourcePackStatus.class.isAssignableFrom(msg.getClass())) {
				PacketPlayResourcePackStatus.this.onPacketReceive(msg, this.p);
			}
			
			
			/** Super constructor **/
			
			super.channelRead(ctx, msg);
		}
    
		protected void decode(ChannelHandlerContext paramChannelHandlerContext, ByteBuf paramByteBuf, List<Object> paramList) throws Exception {}
	}
}
