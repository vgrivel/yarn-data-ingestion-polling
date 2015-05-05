package ch.daplab.yarn.poller;

import ch.daplab.fs.sink.PartitionedObserver;
import ch.daplab.yarn.poller.rx.PollerObservable;
import joptsimple.OptionParser;
import joptsimple.OptionSet;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.twill.api.AbstractTwillRunnable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rx.Observable;

import java.io.IOException;

import static ch.daplab.yarn.poller.PollerToHDFSCli.*;

/**
 * Created by vincent on 4/29/15.
 */
public class PollerToHDFSTwillApp extends AbstractTwillRunnable {

    private static final Logger LOG = LoggerFactory.getLogger(PollerToHDFSTwillApp.class);

    /**
     * Called by YARN nodeManager, i.e. remote (not on the same JVM) from the command line
     * which startsd it.
     */
    @Override
    public void run() {

        final OptionParser parser = new OptionParser();
        parser.allowsUnrecognizedOptions();
        final OptionSet optionSet = parser.parse(getContext().getApplicationArguments());

        final Configuration conf = new Configuration();

        String defaultFs = (String) optionSet.valueOf(OPTION_FS_DEFAULTFS);

        String url = (String) optionSet.valueOf(OPTION_URL);
        boolean etagSupport = (boolean) optionSet.valueOf(OPTION_ETAG_SUPPORT);
        long intervalms = (long) optionSet.valueOf(OPTION_INTERVAL_MS);
        String rootFolder = (String) optionSet.valueOf(OPTION_ROOT_FOLDER);
        String fileSuffix = (String) optionSet.valueOf(OPTION_FILE_SUFFIX);
        String partitionFormat = (String) optionSet.valueOf(OPTION_PARTITION_FORMAT);
        String parsingClass = (String) optionSet.valueOf(OPTION_PARSING_CLASS);

        if (defaultFs != null) {
            conf.set("fs.defaultFS", defaultFs);
        }

        FileSystem fs = null;
        try {
            fs = FileSystem.get(FileSystem.getDefaultUri(conf), conf);

            LOG.info("PartitionFormat "+ partitionFormat);
            Observable.create(new PollerObservable(url, etagSupport, intervalms, parsingClass)).subscribe(new PartitionedObserver(rootFolder, partitionFormat, fileSuffix, fs));

        } catch (IOException e) {
            LOG.error("Got an IOException", e);
        } finally {
            if (fs != null) {
                //TODO issue: by closing the filesytem there, it is not possible to write in the PartitionedObserver!
                // Have to find a better way to close properly the fs!
              //  try {
              //     fs.close();
              //  } catch (IOException e) {
              //  }
            }
        }
    }
}

