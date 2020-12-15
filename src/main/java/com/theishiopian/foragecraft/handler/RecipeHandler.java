package com.theishiopian.foragecraft.handler;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

//this whole class will be removed soon

@SuppressWarnings("unused")
public class RecipeHandler
{

    public static void Shapless()
    {
        Item stick = Items.STICK;
        Item bundle = Item.getByNameOrId("foragecraft:stick_bundle");
        Item fascine = Item.getByNameOrId("foragecraft:fascine");
        Item straw = Item.getByNameOrId("foragecraft:straw");
        Item bale = Item.getByNameOrId("foragecraft:straw_bale");
        Item scarecrow = Item.getByNameOrId("foragecraft:scarecrow");
        Item pumpkin = Item.getItemFromBlock(Blocks.PUMPKIN);
        Item leather = Items.LEATHER;
        Item rock = Item.getByNameOrId("foragecraft:rock_normal");
        Item flat = Item.getByNameOrId("foragecraft:rock_flat");
        // TODO cleanup a bit
//		GameRegistry.addShapelessRecipe(new ItemStack(Items.DYE,9,15),Items.SKULL);
//		GameRegistry.addShapelessRecipe(new ItemStack(bundle,1,0),stick,stick,stick,stick,stick,stick,stick,stick,stick);
//		GameRegistry.addShapelessRecipe(new ItemStack(stick,9,0),bundle);
//		GameRegistry.addShapelessRecipe(new ItemStack(fascine,1,0),bundle,bundle,bundle,bundle,bundle,bundle,bundle,bundle,bundle);
//		GameRegistry.addShapelessRecipe(new ItemStack(bundle,9,0),fascine);
//		GameRegistry.addSmelting(Item.getItemFromBlock(Blocks.TALLGRASS), new ItemStack(straw,1,0), 0.0f);
//		GameRegistry.addShapelessRecipe(new ItemStack(bale,1,0),straw,straw,straw,straw,straw,straw,straw,straw,straw);
//		GameRegistry.addShapelessRecipe(new ItemStack(straw,9,0),bale);
//		GameRegistry.addShapedRecipe(new ItemStack(scarecrow,1,0), "LPL", "BFB", "LSL",'L',leather,'P',pumpkin,'B',bundle,'F',fascine,'S',stick);
//		GameRegistry.addShapedRecipe(new ItemStack(scarecrow,1,0), "LPL", "BFB", "LSL",'L',leather,'P',pumpkin,'B',bundle,'F',bale,'S',stick);
//		GameRegistry.addShapelessRecipe(new ItemStack(Blocks.COBBLESTONE), rock, rock, rock, rock, rock, rock, rock, rock, rock);
//		GameRegistry.addShapelessRecipe(new ItemStack(rock, 9, 0), Blocks.COBBLESTONE);
//		GameRegistry.addShapelessRecipe(new ItemStack(ModBlocks.road_stone), flat, flat, flat, flat, flat, flat, flat, flat, flat);
//		GameRegistry.addShapelessRecipe(new ItemStack(flat, 9, 0), ModBlocks.road_stone);
    }
}
