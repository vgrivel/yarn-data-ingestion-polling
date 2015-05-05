package ch.daplab.yarn.twill;

import org.apache.hadoop.yarn.conf.YarnConfiguration;
import org.apache.twill.api.AbstractTwillRunnable;
import org.apache.twill.api.TwillController;
import org.apache.twill.api.TwillRunnerService;
import org.apache.twill.api.logging.PrinterLogHandler;
import org.apache.twill.yarn.YarnTwillRunnerService;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.PrintWriter;
import java.net.URISyntaxException;

/**
 * "Simple" Twill application which is launching a HelloWorld-like Runnable.
 */
public class SimpleTwillTest extends AbstractTwillLauncher {

    @Test(timeout = 40000)
    public void twillSimpleTest() throws InterruptedException, URISyntaxException {

        // Instantiate TwillRunnerService, and waiting for it to start
        YarnConfiguration yarnConf = new YarnConfiguration(miniCluster.getConfig());

        // Twill relies a lot on Zookeeper. Here we're passing the zookeeper connection
        // string generated by the EmbeddedZookeeper server, but in more real life
        // environment we could retrieve the zkConnect string via yarnConf.get("yarn.resourcemanager.zk-address")
        TwillRunnerService runnerService = new YarnTwillRunnerService(
                yarnConf, zkConnect);

        runnerService.startAndWait();

        // Prepare the controller responsible of launching the Twill Application Master.
        // Lots of options is available but not shown here. Please refer to the other,
        // more advanced use cases to see more options
        TwillController controller = runnerService.prepare(new SimpleTwillApp())
                .addLogHandler(new PrinterLogHandler(new PrintWriter(System.out))) // write TwillRunnable logs in local System.out
                .start();

        // Wait until the app is started
        controller.startAndWait();

        // Just wait until the app completes. A matter of seconds...
        // If your launcher application dies, the YARN application
        // will continue to run on is own, and you can reconnect
        // later to the application
        while (controller.isRunning()) {
            Thread.sleep(200);
        }

        // Shutting down properly
        controller.stopAndWait();

    }


    /**
     * TwillRunnable.
     * <p/>
     * Prints out something and completes.
     */
    public static class SimpleTwillApp extends AbstractTwillRunnable {

        public static Logger LOG = LoggerFactory.getLogger(SimpleTwillApp.class);

        @Override
        public void run() {
            System.out.println("Woot, I'm doing something awesome on YARN!");
            LOG.info("Woot, I'm doing something awesome on YARN!");
        }
    }
}
