package com.apple.iossystems.smp.reporting.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Toch
 */
public class IReporterDataReport implements SMPDataReport
{
    private static final Gson GSON = new GsonBuilder().create();

    // The event: Provision, Suspend, Resume, Delete
    private final String event;

    // Event Timestamp
    private final String timestamp;

    // DSID hash
    private final String user;

    // First six digits of FPAN
    private final String fpan;

    // user location: lat,lon
    private final String location;

    private IReporterDataReport(Builder builder)
    {
        event = builder.event;
        timestamp = builder.timestamp;
        user = builder.user;
        fpan = builder.fpan;
        location = builder.location;
    }

    public String getEvent()
    {
        return event;
    }

    public String getTimestamp()
    {
        return timestamp;
    }

    public String getUser()
    {
        return user;
    }

    public String getFPAN()
    {
        return fpan;
    }

    public String getLocation()
    {
        return location;
    }

    public static class Builder
    {
        private String event;
        private String timestamp;
        private String user;
        private String fpan;
        private String location;

        private Builder()
        {
        }

        public static Builder getInstance()
        {
            return new Builder();
        }

        public Builder event(String val)
        {
            event = val;
            return this;
        }

        public Builder timestamp(String val)
        {
            timestamp = val;
            return this;
        }

        public Builder user(String val)
        {
            user = val;
            return this;
        }

        public Builder fpan(String val)
        {
            fpan = val;
            return this;
        }

        public Builder location(String val)
        {
            location = val;
            return this;
        }

        public IReporterDataReport build()
        {
            return new IReporterDataReport(this);
        }
    }

    public static String toJSON(List<SMPDataReport> reports)
    {
        return GSON.toJson(DataReportsJSON.create(reports));
    }

    private static class DataReportsJSON
    {
        @SerializedName("Version")
        private static final String VERSION = "1.0";

        @SerializedName("txn")
        private List<DataReportOutput> reportsOutput = new ArrayList<DataReportOutput>();

        private DataReportsJSON()
        {
        }

        private static DataReportsJSON create(List<SMPDataReport> reports)
        {
            DataReportsJSON dataReportsJSON = new DataReportsJSON();

            for (SMPDataReport e : reports)
            {
                DataReportOutput dataReportOutput = new DataReportOutput();

                dataReportOutput.event = e.getEvent();
                dataReportOutput.timestamp = e.getTimestamp();
                dataReportOutput.user = e.getUser();
                dataReportOutput.fpan = e.getFPAN();
                dataReportOutput.location = e.getLocation();

                dataReportsJSON.reportsOutput.add(dataReportOutput);
            }

            return dataReportsJSON;
        }

        private static class DataReportOutput
        {
            private String event;
            private String timestamp;
            private String user;
            private String fpan;
            private String location;
        }
    }
}
