package trinity.radiation;

import java.util.ArrayList;
import java.util.List;

import nc.init.NCBlocks;
import nc.recipe.ProcessorRecipeHandler;

public class FalloutEffects extends ProcessorRecipeHandler {
		
	public FalloutEffects() {
		super("falloutEffects", 1, 0, 1, 0);
	}
		
	@Override
	public void addRecipes() {
		addRecipe("dirt", NCBlocks.wasteland_earth, 0, 100, 1, false, false);
	}
	
	@Override
	public List fixExtras(List extras) {
		List fixed = new ArrayList(1);
		fixed.add(extras.size() > 0 && extras.get(0) instanceof Double ? (double) extras.get(0) : 0D);
		return fixed;
	}
}
