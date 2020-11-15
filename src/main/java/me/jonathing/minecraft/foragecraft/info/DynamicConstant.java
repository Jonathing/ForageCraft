package me.jonathing.minecraft.foragecraft.info;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.SOURCE)
public @interface DynamicConstant
{
    /**
     * The constant name to inject, set in the Gradle buildscript.
     */
    String value();
}
