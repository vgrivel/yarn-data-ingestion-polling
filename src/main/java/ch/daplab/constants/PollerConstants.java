package ch.daplab.constants;

import ch.daplab.utils.Context;
import ch.daplab.utils.DefaultFileProcessing;
import ch.daplab.utils.FileProcessing;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;


/**
 * Created by vincent on 4/29/15.
 */
public class PollerConstants {


    private long intervalMS;
    private String url;
    private boolean etagSupport;
    private String rootFolder;
    private String fileSuffix;
    private String partitionFormat;
    private FileProcessing fileObject;

    private static PollerConstants ourInstance = new PollerConstants();

    public static PollerConstants getInstance() {
        return ourInstance;
    }

    private PollerConstants() {

    }

    public void load(Context context){
        url = context.getString("url");
        etagSupport = Boolean.valueOf(context.getString("etagSupport"));
        intervalMS = Long.valueOf(context.getString("intervalMS"));
        rootFolder = context.getString("rootFolder");
        fileSuffix = context.getString("fileSuffix");
        partitionFormat = context.getString("partitionFormat");
        instantiateClass(context.getString("fileProcessing"));

    }

    private void instantiateClass(String fqn) {
        if (fqn != null || !fqn.equals("")) {
            try {
                Class cl = Class.forName(fqn);
                fileObject = (FileProcessing) cl.newInstance();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        } else {
            fileObject = new DefaultFileProcessing();
        }
    }


    public String getUrl() {
        return url;
    }

    public long getIntervalMS() {
        return intervalMS;
    }

    public boolean isEtagSupported() {
        return etagSupport;
    }

    public String getRootFolder() {
        return rootFolder;
    }

    public String getFileSuffix() {
        return fileSuffix;
    }

    public String getPartitionFormat() {
        return partitionFormat;
    }

    public FileProcessing getProcessingClass() {
        return fileObject;
    }


}