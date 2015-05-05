package ch.daplab.yarn.poller;

import ch.daplab.utils.Context;
import ch.daplab.utils.DefaultFileProcessing;
import ch.daplab.utils.FileProcessing;
import ch.daplab.yarn.AbstractAppLauncher;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;
import org.apache.hadoop.yarn.conf.YarnConfiguration;
import org.apache.twill.api.TwillController;
import org.apache.twill.api.TwillRunnerService;
import org.apache.twill.api.logging.PrinterLogHandler;
import org.apache.twill.yarn.YarnTwillRunnerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by vincent on 4/28/15.
 */
public class PollerToHDFSCli extends AbstractAppLauncher {
    public static String OPTION_FS_DEFAULTFS = "fs.defaultFS";
    public static String OPTION_INTERVAL_MS = "intervalms";
    public static String OPTION_URL = "url";
    public static String OPTION_ETAG_SUPPORT = "etagSupport";
    public static String OPTION_ROOT_FOLDER = "rootFolder";
    public static String OPTION_FILE_SUFFIX = "fileSuffix";
    public static String OPTION_PARTITION_FORMAT = "partitionFormat";
    public static String OPTION_PARSING_CLASS = "fileObject";

    private static final Logger LOG = LoggerFactory.getLogger(PollerToHDFSCli.class);

    public static void main(String[] args) throws Exception {

        int res = ToolRunner.run(new Configuration(), new PollerToHDFSCli(), args);
        System.exit(res);
    }

    /**
     * Called by {@link ToolRunner#run(Configuration, Tool, String[])} and in turn {@link AbstractAppLauncher#run(String[])},
     * i.e. local to the server where the command line is launched.
     */
    @Override
    protected int internalRun() throws Exception {

        Context context = new Context(getConfigFilePath());
        String url = context.getString("url");
        boolean etagSupport = Boolean.valueOf(context.getString("etagSupport"));
        long intervalms = Long.valueOf(context.getString("intervalMS"));
        String rootFolder = context.getString("rootFolder");
        String fileSuffix = context.getString("fileSuffix");
        String partitionFormat = context.getString("partitionFormat");
        String parsingClass = context.getString("fileProcessing");


        String defaultFS = (String) getOptions().valueOf(OPTION_FS_DEFAULTFS);
        if (defaultFS == null) {
            defaultFS = FileSystem.getDefaultUri(getConf()).toString();
        }

        // Instantiate TwillRunnerService, and waiting for it to start
        YarnConfiguration yarnConf = new YarnConfiguration(getConf());

        TwillRunnerService runnerService = new YarnTwillRunnerService(
                yarnConf, getZkConnect());

        runnerService.startAndWait();

        List<String> args = new ArrayList<>();

        args.add("--" + OPTION_FS_DEFAULTFS);
        args.add(defaultFS);

        args.add("--" + OPTION_INTERVAL_MS);
        args.add(String.valueOf(intervalms));

        args.add("--" + OPTION_URL);
        args.add(url);

        args.add("--" + OPTION_ETAG_SUPPORT);
        args.add(String.valueOf(etagSupport));

        args.add("--" + OPTION_ROOT_FOLDER);
        args.add(rootFolder);

        args.add("--" + OPTION_FILE_SUFFIX);
        args.add(fileSuffix);

        args.add("--" + OPTION_PARTITION_FORMAT);
        args.add(partitionFormat);

        args.add("--" + OPTION_PARSING_CLASS);
        args.add(parsingClass);


        TwillController controller = runnerService.prepare(new PollerToHDFSTwillApp())
                .withApplicationArguments(args.toArray(new String[0]))
                .addLogHandler(new PrinterLogHandler(new PrintWriter(System.out))) // write TwillRunnable logs in local System.out
                .start();

        // Wait until the app is started
        controller.startAndWait();

        // Good, let it simply run on his own.
        return ReturnCode.ALL_GOOD;
    }

}
