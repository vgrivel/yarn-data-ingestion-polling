package ch.daplab.utils;

/**
 * Created by vincent on 4/30/15.
 */
public class DefaultFileProcessing implements FileProcessing {
    @Override
    public byte[] process(byte[] byteArray) {
        return byteArray;
    }
}
