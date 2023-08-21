package trinity.world;


import java.util.List;
import net.minecraft.world.World;
import com.google.common.collect.ListMultimap;
import net.minecraftforge.common.ForgeChunkManager;


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
