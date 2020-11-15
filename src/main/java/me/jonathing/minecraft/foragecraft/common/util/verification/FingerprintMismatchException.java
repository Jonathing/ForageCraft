package me.jonathing.minecraft.foragecraft.common.util.verification;

public class FingerprintMismatchException extends RuntimeException
{
    public FingerprintMismatchException(String modId)
    {
        super(String.format("Fingerprint for mod %s does not match the given expected fingerprint!", modId));
    }
}
