package me.jonathing.minecraft.foragecraft.common.util.verification;

public class SignatureInformation
{
    private int errorCode;
    private String fingerprint;
    private String expectedFingerprint;
    private String trustData;

    public SignatureInformation(String fingerprint, String expectedFingerprint, String trustData)
    {
        this.fingerprint = fingerprint;
        this.expectedFingerprint = expectedFingerprint;
        this.trustData = trustData;

        if (fingerprint.equals("UNSIGNED") || fingerprint.isEmpty())
        {
            this.errorCode = 2;
        }
        else if (fingerprint.equals(expectedFingerprint.toLowerCase()))
        {
            this.errorCode = 0;
        }
        else
        {
            this.errorCode = 1;
        }
    }

    public int getErrorCode()
    {
        return this.errorCode;
    }

    public String getFingerprint()
    {
        return fingerprint;
    }

    public String getExpectedFingerprint()
    {
        return expectedFingerprint;
    }

    public String getTrustData()
    {
        return trustData;
    }
}
