package ch.daplab.fs.sink.partition;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by mil2048 on 4/22/15.
 */
public class Partitioner {


    private static final Logger LOG = LoggerFactory.getLogger(Partitioner.class);
    private final TimeZone timezone;
    private final String simpleDateFormatString;
    private final SimpleDateFormat simpleDateFormat;
    private final String prefix;
    private final String suffix;

    public Partitioner(String prefix, String simpleDateFormatString, TimeZone timezone, String suffix) {
        LOG.info("simpleDateFormatString: "+ simpleDateFormatString);
        this.prefix = prefix;
        this.simpleDateFormatString = simpleDateFormatString;
        this.simpleDateFormat = new SimpleDateFormat(simpleDateFormatString);
        this.simpleDateFormat.setTimeZone(timezone);
        this.timezone = timezone;
        this.suffix = suffix;
    }

    public String getPartition(Date date) {
        final StringBuilder sb = new StringBuilder(prefix);
        sb.append(simpleDateFormat.format(date));
        sb.append(suffix);
        return sb.toString();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder(getClass().getCanonicalName());
        sb.append("[")
                .append(prefix)
                .append("|")
                .append(simpleDateFormatString)
                .append("|")
                .append(suffix);
        return sb.toString();
    }
}
