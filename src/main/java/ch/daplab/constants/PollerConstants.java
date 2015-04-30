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


    private static long intervalMS;
    private static String url;
    private static boolean etagSupport;
    private static String rootFolder;
    private static String fileSuffix;
    private static String partitionFormat;
    private static FileProcessing fileObject;

    public PollerConstants(Context context) {
        System.out.println(context.getString("intervalMS"));
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


    public static String getUrl() {
        return url;
    }

    public static long getIntervalMS() {
        return intervalMS;
    }

    public static boolean isEtagSupported() {
        return etagSupport;
    }

    public static String getRootFolder() {
        return rootFolder;
    }

    public static String getFileSuffix() {
        return fileSuffix;
    }

    public static String getPartitionFormat() {
        return partitionFormat;
    }

    public static FileProcessing getProcessingClass() {
        return fileObject;
    }


}