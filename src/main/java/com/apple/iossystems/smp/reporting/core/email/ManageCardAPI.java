package com.apple.iossystems.smp.reporting.core.email;

/**
 * @author Toch
 */
public enum ManageCardAPI
{
    MANAGE_CARD("1"),
    MANAGE_DEVICE("2");

    private final String code;

    private ManageCardAPI(String code)
    {
        this.code = code;
    }

    private static ManageCardAPI getUnknownAPI()
    {
        return MANAGE_CARD;
    }

    public String getCode()
    {
        return code;
    }

    public static ManageCardAPI getManageCardAPI(String code)
    {
        for (ManageCardAPI e : ManageCardAPI.values())
        {
            if (e.code.equals(code))
            {
                return e;
            }
        }

        return getUnknownAPI();
    }
}