package com.apple.iossystems.smp.reporting.core.email;

import org.apache.xerces.util.XMLChar;

/**
 * @author Toch
 */
class XmlProcessor
{
    private XmlProcessor()
    {
    }

    static XmlProcessor getInstance()
    {
        return new XmlProcessor();
    }

    String getValidXmlString(String str)
    {
        StringBuilder stringBuilder = new StringBuilder();

        if (str != null)
        {
            for (int i = 0; i < str.length(); i++)
            {
                char c = str.charAt(i);

                if (XMLChar.isValid(c))
                {
                    stringBuilder.append(c);
                }
            }
        }

        return stringBuilder.toString();
    }
}