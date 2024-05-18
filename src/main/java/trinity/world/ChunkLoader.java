package trinity.world;

import com.google.common.collect.ListMultimap;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeChunkManager;

import java.util.List;

public class ChunkLoader implements ForgeChunkManager.PlayerOrderedLoadingCallback {
	
	private static ChunkLoader INSTANCE;
	
	public static ChunkLoader getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new ChunkLoader();
		}
		
		return INSTANCE;
	}
	
	@Override
	public ListMultimap<String, ForgeChunkManager.Ticket> playerTicketsLoaded(ListMultimap<String, ForgeChunkManager.Ticket> tickets, World world) {
		return tickets;
	}
	
	@Override
	public void ticketsLoaded(List<ForgeChunkManager.Ticket> tickets, World world) {
	
	}
}
