package at.petrak.hexlite.datagen;

import net.minecraft.data.recipes.RecipeBuilder;

public interface IXplatConditionsBuilder extends RecipeBuilder {
    IXplatConditionsBuilder whenModLoaded(String modid);

    IXplatConditionsBuilder whenModMissing(String modid);
}
