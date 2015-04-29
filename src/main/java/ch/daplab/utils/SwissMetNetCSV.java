package ch.daplab.utils;

/**
 * Created by vincent on 4/29/15.
 */
public class SwissMetNetCSV implements FileProcessing {
    @Override
    public byte[] process(byte[] byteArray) {
        String data = new String(byteArray);
        String finalData = changeCSVSeparator(removeHeader(data));

        return finalData.getBytes();
    }

    private String removeHeader(String data) {
        String resData = data.substring(data.indexOf('\n') + 1);
        resData = resData.trim();
        return resData;
    }

    private String changeCSVSeparator(String data) {
        //replace | by , (pipe by comma)
        return data.replaceAll("\\|", ",");
    }
}
