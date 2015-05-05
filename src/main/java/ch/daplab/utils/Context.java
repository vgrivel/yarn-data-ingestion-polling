package ch.daplab.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by vincent on 4/29/15.
 */
public class Context {

    private static final Logger LOG = LoggerFactory.getLogger(Context.class);
    Properties prop;

    public Context(String file) {
        prop = new Properties();
        InputStream is = null;
        try {
            is = new FileInputStream(file);
            prop.load(is);
        } catch (FileNotFoundException e) {
            LOG.error("File not found");
            e.printStackTrace();
        } catch (IOException e) {
            LOG.error("IO Exception");
            e.printStackTrace();
        }

    }

    public String getString(String key) {
        return prop.getProperty(key);
    }

}
