/**
 * This package contains all of the mixin classes used by ForageCraft to hook into Minecraft, Minecraft Forge, or any
 * other given mod it is injecting into, and changes it based on its needs. These mixins, in particular, are only ever
 * fired when the {@link me.jonathing.minecraft.foragecraft.info.ForageInfo#DATAGEN} property is enabled, as these
 * mixins only modify classes that generate specific JSON files during data generation.
 *
 * @see me.jonathing.minecraft.foragecraft.info.ForageInfo#DATAGEN
 */
package me.jonathing.minecraft.foragecraft.mixin.data;
