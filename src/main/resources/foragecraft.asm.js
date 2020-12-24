var Opcodes = org.objectweb.asm.Opcodes;
var VarInsnNode = org.objectweb.asm.tree.VarInsnNode;
var FieldInsnNode = org.objectweb.asm.tree.FieldInsnNode;
var MethodInsnNode = org.objectweb.asm.tree.MethodInsnNode;
var JumpInsnNode = org.objectweb.asm.tree.JumpInsnNode;
var LabelNode = org.objectweb.asm.tree.LabelNode;
var InsnList = org.objectweb.asm.tree.InsnList;
var InsnNode = org.objectweb.asm.tree.InsnNode;

/**
 * This function is called by Forge's JavaScript ASM API to initalize all of the coremod functions in this file.
 *
 * @returns {{SoundSourceTransformer: {transformer: (function(*=): *), target: {name: string, type: string}}}} The
 * functions that have code that will coremod the game.
 */
function initializeCoreMod()
{
    return {
        "SoundSourceTransformer": {
            "target": {
                "type": "CLASS",
                "name": "net.minecraft.item.crafting.RecipeManager"
            },
            "transformer": patch_recipe_manager
        }
    }
}

/**
 * This function patches the apply() method in the RecipeManager class to add a check if the skipRecipe() method in the
 * RecipeManagerJS class returns true.
 *
 * @param class_node The class to be coremodded on game startup.
 * @returns {*} The transformed class.
 */
function patch_recipe_manager(class_node)
{
    // Call Forge's JS ASM API into a variable
    var api = Java.type('net.minecraftforge.coremod.api.ASMAPI');

    // Get the position at the apply method
    var set_pos_method = get_method(class_node, api.mapMethod("func_212853_a_")); // apply

    /*
     * This batch of code inputs the initial check for recipes.
     *
     * Without the code:
     * if (resourcelocation.getPath().startsWith("_")) continue;
     *
     * With the code:
     * if (resourcelocation.getPath().startsWith("_") || RecipeManagerJS.skipRecipe(resourcelocation)) continue;
     */
    var instructions = set_pos_method.instructions;
    var label = new LabelNode();
    for (var i = 0; i < instructions.size(); i++)
    {
        var insnAfter = instructions.get(i)
        if (insnAfter.getOpcode() == Opcodes.GOTO)
        {
            var insn = instructions.get(i - 1);
            instructions.insertBefore(insn, new JumpInsnNode(Opcodes.IFNE, label));
            instructions.insertBefore(insn, new VarInsnNode(Opcodes.ALOAD, 7));
            instructions.insertBefore(insn, new MethodInsnNode(Opcodes.INVOKESTATIC, "me/jonathing/minecraft/foragecraft/asm/js/RecipeManagerJS", "skipRecipe", "(Lnet/minecraft/util/ResourceLocation;)Z", false));
            instructions.insertBefore(insnAfter, label)
            break;
        }
    }

    /*
     * This second batch of code makes sure that the continue; keyword behaves properly and we don't ignore the jump
     * instruction that comes right before the goto instruction.
     */
    instructions = set_pos_method.instructions;
    for (var j = 0; j < instructions.size(); j++)
    {
        var insnGoto = instructions.get(j)
        if (insnGoto.getOpcode() == Opcodes.GOTO)
        {
            instructions.insertBefore(insnGoto, label)
            break;
        }
    }

    return class_node;
}

/**
 * Gets a method from a class for coremodding.
 *
 * @param class_node The class to get the method from.
 * @param name The unmapped SRG name of the method.
 * @returns {*} The method. If it is not found, an Exception is thrown.
 */
function get_method(class_node, name)
{
    for (var index in class_node.methods)
    {
        var method = class_node.methods[index];
        if (method.name.equals(name))
        {
            return method;
        }
    }
    throw "couldn't find method with name '" + name + "' in '" + class_node.name + "'"
}
