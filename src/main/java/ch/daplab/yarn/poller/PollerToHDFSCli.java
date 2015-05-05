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
    public static long INTERVAL_MS = 1000;
    public static String URL = "";
    public static boolean ETAG_SUPPORT;
    public static String ROOT_FOLDER = "";
    public static String FILE_SUFFIX = "";
    public static String PARTITION_FORMAT = "";
    public static FileProcessing FILE_OBJECT;


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
        URL = context.getString("url");
        ETAG_SUPPORT = Boolean.valueOf(context.getString("etagSupport"));
        INTERVAL_MS = Long.valueOf(context.getString("intervalMS"));
        ROOT_FOLDER = context.getString("rootFolder");
        FILE_SUFFIX = context.getString("fileSuffix");
        PARTITION_FORMAT = context.getString("partitionFormat");
        instantiateClass(context.getString("fileProcessing"));


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

        TwillController controller = runnerService.prepare(new PollerToHDFSTwillApp())
                .withApplicationArguments(args.toArray(new String[0]))
                .addLogHandler(new PrinterLogHandler(new PrintWriter(System.out))) // write TwillRunnable logs in local System.out
                .start();

        // Wait until the app is started
        controller.startAndWait();

        // Good, let it simply run on his own.
        return ReturnCode.ALL_GOOD;
    }

    private void instantiateClass(String fqn) {
        if (fqn != null || !fqn.equals("")) {
            try {
                Class cl = Class.forName(fqn);
                FILE_OBJECT = (FileProcessing) cl.newInstance();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        } else {
            FILE_OBJECT = new DefaultFileProcessing();
        }
    }
}
