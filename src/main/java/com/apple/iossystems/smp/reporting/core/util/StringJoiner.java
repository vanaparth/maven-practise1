package com.apple.iossystems.smp.reporting.core.util;

/**
 * @author Toch
 */
public class StringJoiner
{
    private StringBuilder value;

    private final String delimiter;

    public StringJoiner(String delimiter)
    {
        this.delimiter = (delimiter != null) ? delimiter : "";
    }

    public StringJoiner add(String str)
    {
        prepareBuilder().append(str);
        return this;
    }

    private StringBuilder prepareBuilder()
    {
        value = (value != null) ? value.append(delimiter) : new StringBuilder();

        return value;
    }

    @Override
    public String toString()
    {
        return ((value != null) ? value.toString() : "");
    }
}