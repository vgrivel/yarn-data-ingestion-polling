package ch.daplab.utils;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by vincent on 4/29/15.
 */
public class Context {
    Properties prop;

    Context( String file) throws Exception{
        prop = new Properties();
        InputStream is = new FileInputStream(file);
        prop.load(is);
    }

    public String getString(String key){
        return prop.getProperty(key);
    }

}
