package me.jonathing.minecraft.foragecraft.common.security;

/**
 * This exception is thrown when the expected fingerprint for a mod does not match the actual fingerprint.
 *
 * @author Jonathing
 */
// TODO: Add more documentation and move this into its own dependency along with VerificationUtil.
public class FingerprintMismatchException extends RuntimeException
{
    public FingerprintMismatchException(String modId)
    {
        super(String.format("Fingerprint found in mod %s does not match the expected fingerprint!", modId));
    }
}
