package trinity.radiation;

import nc.config.NCConfig;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.storage.WorldSavedData;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class FalloutSavedData extends WorldSavedData {
	public Map<ChunkPos, FalloutSaveStructure> contamination = new HashMap<ChunkPos, FalloutSaveStructure>();
	
	//in order to reduce read operations
	//Drillgon200: I'm pretty sure this doesn't actually help since all the world saved datas are cached in a map anyway...
	private static FalloutSavedData openInstance;
	
    public World worldObj;

	public FalloutSavedData(String p_i2141_1_) {
		super(p_i2141_1_);
	}

    public FalloutSavedData(World p_i1678_1_)
    {
        super("fallout");
        this.worldObj = p_i1678_1_;
        this.markDirty();
    }
    
    public boolean doesEntryExist(int x, int y) {
    	
    	return getRadFromCoord(x, y) != null;
    }
    
    public void createEntry(int x, int y, float rad) {
    	
    	contamination.put(new ChunkPos(x, y), new FalloutSaveStructure(x, y, rad));
        this.markDirty();
    }
    
    public void deleteEntry(FalloutSaveStructure struct) {
    	
    	contamination.remove(struct);
        this.markDirty();
    }
    
    public void jettisonData() {
    	
    	contamination.clear();
        this.markDirty();
    }
    
    public void setRadForCoord(int x, int y, float radiation) {
    	ChunkPos pos = new ChunkPos(x, y);
    	FalloutSaveStructure entry = contamination.get(pos);
    	
    	if(entry == null) {

    		entry = new FalloutSaveStructure(x, y, radiation);
        	contamination.put(pos, entry);
    	}
    	
    	entry.radiation = radiation;
        this.markDirty();
    	
    }
    
    public FalloutSaveStructure getRadFromCoord(int x, int y) {
    	ChunkPos pos = new ChunkPos(x, y);
    	return contamination.get(pos);
    }
    
    public float getRadNumFromCoord(int x, int y) {
    	ChunkPos pos = new ChunkPos(x, y);
    	FalloutSaveStructure rad = contamination.get(pos);
    	if(rad != null)
    		return rad.radiation;
    	return 0F;
    }
    
    public void updateSystem() {
    	
    	Map<ChunkPos, FalloutSaveStructure> tempList = new HashMap<ChunkPos, FalloutSaveStructure>(contamination);
    	
    	contamination.clear();
    	
    	for(FalloutSaveStructure struct : tempList.values()) {
    		
    		if(struct.radiation != 0) {

				//struct.radiation *= 0.999F;
				struct.radiation *= 0.999F;
				struct.radiation -= 0.05F;
				
				if(struct.radiation <= 0) {
					struct.radiation = 0;
				}
				
				/*if(struct.radiation > RadiationConfig.fogRad && worldObj != null && worldObj.rand.nextInt(RadiationConfig.fogCh) == 0 && worldObj.getChunkFromChunkCoords(struct.chunkX, struct.chunkY).isLoaded()) {
					
					int x = struct.chunkX * 16 + worldObj.rand.nextInt(16);
					int z = struct.chunkY * 16 + worldObj.rand.nextInt(16);
					int y = worldObj.getHeight(x, z) + worldObj.rand.nextInt(5);
					
					//EntityFogFX fog = new EntityFogFX(worldObj);
					//fog.setPosition(x, y, z);
					//System.out.println(x + " " + y + " " + z);
					//worldObj.spawnEntity(fog);
					PacketDispatcher.wrapper.sendToAllAround(new AuxParticlePacket(x, y, z, 3), new TargetPoint(worldObj.provider.getDimension(), x, y, z, 100));
				}*/
    			
    			if(struct.radiation > 1) {
    				
    				float[] rads = new float[9];

    				rads[0] = getRadNumFromCoord(struct.chunkX + 1, struct.chunkY + 1);
    				rads[1] = getRadNumFromCoord(struct.chunkX, struct.chunkY + 1);
    				rads[2] = getRadNumFromCoord(struct.chunkX - 1, struct.chunkY + 1);
    				rads[3] = getRadNumFromCoord(struct.chunkX - 1, struct.chunkY);
    				rads[4] = getRadNumFromCoord(struct.chunkX - 1, struct.chunkY - 1);
    				rads[5] = getRadNumFromCoord(struct.chunkX, struct.chunkY - 1);
    				rads[6] = getRadNumFromCoord(struct.chunkX + 1, struct.chunkY - 1);
    				rads[7] = getRadNumFromCoord(struct.chunkX + 1, struct.chunkY);
    				rads[8] = getRadNumFromCoord(struct.chunkX, struct.chunkY);
    				
    				float main = 0.6F;
    				float side = 0.075F;
    				float corner = 0.025F;
    				
    				setRadForCoord(struct.chunkX + 1, struct.chunkY + 1, rads[0] + struct.radiation * corner);
    				setRadForCoord(struct.chunkX, struct.chunkY + 1, rads[1] + struct.radiation * side);
    				setRadForCoord(struct.chunkX - 1, struct.chunkY + 1, rads[2] + struct.radiation * corner);
    				setRadForCoord(struct.chunkX - 1, struct.chunkY, rads[3] + struct.radiation * side);
    				setRadForCoord(struct.chunkX - 1, struct.chunkY - 1, rads[4] + struct.radiation * corner);
    				setRadForCoord(struct.chunkX, struct.chunkY - 1, rads[5] + struct.radiation * side);
    				setRadForCoord(struct.chunkX + 1, struct.chunkY - 1, rads[6] + struct.radiation * corner);
    				setRadForCoord(struct.chunkX + 1, struct.chunkY, rads[7] + struct.radiation * side);
    				setRadForCoord(struct.chunkX, struct.chunkY, rads[8] + struct.radiation * main);
    				
    			} else {
    				
    				this.setRadForCoord(struct.chunkX, struct.chunkY, getRadNumFromCoord(struct.chunkX, struct.chunkY) + struct.radiation);
    			}
    		}
    	}
    	
        this.markDirty();
    }

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		if(!NCConfig.radiation_enabled_public) {
			return;
		}
		int count = nbt.getInteger("falloutLevel");
		
		for(int i = 0; i < count; i++) {
			FalloutSaveStructure struct = new FalloutSaveStructure();
			struct.readFromNBT(nbt, i);
			
			contamination.put(new ChunkPos(struct.chunkX, struct.chunkY), struct);
		}
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		nbt.setInteger("falloutLevel", contamination.size());
		int i = 0;
		Iterator<FalloutSaveStructure> itr = contamination.values().iterator();
		while(itr.hasNext()){
			itr.next().writeToNBT(nbt, i);
			i++;
		}
		return nbt;
	}
	
	public static FalloutSavedData getData(World worldObj) {
		
		if(openInstance != null && openInstance.worldObj == worldObj)
			return openInstance;

		FalloutSavedData data = (FalloutSavedData)worldObj.getPerWorldStorage().getOrLoadData(FalloutSavedData.class, "fallout");
	    if(data == null) {
	        worldObj.getPerWorldStorage().setData("fallout", new FalloutSavedData(worldObj));
	        
	        data = (FalloutSavedData)worldObj.getPerWorldStorage().getOrLoadData(FalloutSavedData.class, "fallout");
	    }
	    
	    data.worldObj = worldObj;
	    openInstance  = data;
	    
	    return openInstance;
	}
	
	public static void incrementRad(World worldObj, int x, int z, float rad, float maxRad) {
		
		FalloutSavedData data = getData(worldObj);
		
		Chunk chunk = worldObj.getChunk(new BlockPos(x, -1, z));
		
		float r = data.getRadNumFromCoord(chunk.x, chunk.z);
		
		if(r < maxRad) {
			
			data.setRadForCoord(chunk.x, chunk.z, r + rad);
		}
	}
	
	public static void decrementRad(World worldObj, int x, int z, float rad) {
		
		FalloutSavedData data = getData(worldObj);
		
		Chunk chunk = worldObj.getChunk(new BlockPos(x, -1, z));
		
		float r = data.getRadNumFromCoord(chunk.x, chunk.z);
		
		r -= rad;
		
		if(r > 0) {
			data.setRadForCoord(chunk.x, chunk.z, r);
		} else {
			data.setRadForCoord(chunk.x, chunk.z, 0);
		}
	}
}
