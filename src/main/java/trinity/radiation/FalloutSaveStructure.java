package trinity.radiation;

import net.minecraft.nbt.NBTTagCompound;

public class FalloutSaveStructure {
	
	public int chunkX;
	public int chunkY;
	public float radiation;
	
	public FalloutSaveStructure() {}
	
	public FalloutSaveStructure(int x, int y, float rad) {
		chunkX = x;
		chunkY = y;
		radiation = rad;
	}
	
	public void readFromNBT(NBTTagCompound nbt, int index) {
		chunkX = nbt.getInteger("rad_" + index + "_x");
		chunkY = nbt.getInteger("rad_" + index + "_y");
		radiation = nbt.getFloat("rad_" + index + "_level");
	}
	
	public void writeToNBT(NBTTagCompound nbt, int index) {
		nbt.setInteger("rad_" + index + "_x", chunkX);
		nbt.setInteger("rad_" + index + "_y", chunkY);
		nbt.setFloat("rad_" + index + "_level", radiation);
	}
}
