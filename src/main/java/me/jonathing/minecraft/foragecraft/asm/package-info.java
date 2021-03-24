/**
 * <h1>ForageCraft ASM Package</h1>
 * This package contains all of the classes that use ASM to modify the bytecode of Minecraft, Minecraft Forge, or any
 * other applicable mod of the game.
 *
 * <h2>SpongePowered Mixin</h2>
 * Mixin is SpongePowered's method of using a Java interface to modify bytecode instead of injecting raw ASM. In
 * ForageCraft, the main mixins can be found in the package {@link me.jonathing.minecraft.foragecraft.asm.mixin.main},
 * the development environment mixins can be found in the package
 * {@link me.jonathing.minecraft.foragecraft.asm.mixin.dev}, and the data generation mixins can be found in the package.
 * <p>
 * Everything in the mixin packages is documented as clearly as possible so that any viewers of the source code will be
 * able to understand what I am doing with ease and will be able to learn from the examples provided here.
 *
 * <h2>Minecraft Forge JavaScript ASM API</h2>
 * Minecraft Forge has its own ASM API (located in {@link net.minecraftforge.coremod.api.ASMAPI}) that allows a modder
 * to use JavaScript to inject raw ASM bytecode into any particular class. Forge's reasoning for having the ASM API only
 * be used in a JavaScript environment is to prevent any unnecessary tampering of Minecraft's classes that might cause
 * major issues. It is recommended to use SpongePowered Mixin instead of Forge's ASM API, but if SpongePowered Mixin
 * doesn't work for a particular problem, I will use this API.
 * <p>
 * Although there is not any code using Forge's ASM API, I actually have used it before in this project, and is included
 * in the {@code 2.1.2} release of ForageCraft. If for whatever reason you need to see an example of the JS ASM API in
 * use or in context, there is a link at the bottom of this Javadoc comment that will take you to the
 * {@code foragecraft.asm.js} before I deleted it. If I ever need to use the JS ASM API again, the package for the main
 * JS ASM classes will be located in {@code me.jonathing.minecraft.foragecraft.asm.js} and the package for the
 * development environment JS ASM classes will be located in {@code me.jonathing.minecraft.foragecraft.asm.dev.js}
 *
 * @see <a href="https://github.com/Jonathing/ForageCraft/blob/cc5c1482f5e945154d9d9611d4f650c64f6b0581/src/main/resources/foragecraft.asm.js">ForageCraft's JS ASM Coremodding</a>
 */
package me.jonathing.minecraft.foragecraft.asm;
