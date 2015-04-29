package ch.daplab.yarn.poller.worker;

import ch.daplab.yarn.poller.NewDataListener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.TimerTask;

/**
 * Created by vincent on 4/29/15.
 */
public class GeneralPoller extends TimerTask implements Observable {

    private ArrayList<NewDataListener> observers = new ArrayList<NewDataListener>();

    private String url;
    private boolean isEtagCompatible;

    private String etag = "";
    private String lastModified = "";
    private URL urlObj;
    private HttpURLConnection httpConnection;

    /**
     * Constructor
     *
     * @param url
     *           Url to poll
     * @param isEtagCompatible
     *           True if the resource support the Etag.
     */
    public GeneralPoller(String url, boolean isEtagCompatible) {
        this.url = url;
        this.isEtagCompatible = isEtagCompatible;

    }

    public boolean cancel() {
        httpConnection.disconnect();

        return super.cancel();
    }

    /**
     * Begin the polling.
     *
     * To avoid duplicated content, it's recommended that the resource (server) provide an Etag, or use correctly the
     * last-Modified header field. If it's not the case, you may be have some duplicate data because the poller will not
     * be able to detect if the resource has changed or not.
     *
     */
    public void run() {
        try {
            urlObj = new URL(url);
            httpConnection = (HttpURLConnection) urlObj.openConnection();
            httpConnection.setRequestMethod("GET");
            if (isEtagCompatible) {
                httpConnection.setRequestProperty("If-None-Match", etag);
            }
            else {
                httpConnection.setRequestProperty("If-Modified-Since", lastModified);
            }

            int responseCode = httpConnection.getResponseCode();
            switch (responseCode) {
                case 200:
                    // New data are available
                    StringBuffer data = processStream(httpConnection.getInputStream());
                    notifyObservers(data);
                    // update etag or lastModified
                    if (isEtagCompatible) {
                        etag = httpConnection.getHeaderField("etag");
                    }
                    else {
                        lastModified = httpConnection.getHeaderField("Last-Modified");
                    }
                    break;
                case 304:
                    // Resource was not modified since last access.
                    // Do nothing
                    break;
                default:
                    // TODO manage others http code (404, 500, ...)
                    break;
            }
        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    /**
     * Process the stream and write the content in a StringBuffer.
     *
     * @param is
     * @return
     * @throws IOException
     */
    private StringBuffer processStream(InputStream is) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(is));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
            response.append("\n");
        }
        in.close();
        return response;

    }

    public void registerObserver(NewDataListener listner) {
        observers.add(listner);

    }

    public void removeObserver(NewDataListener listner) {
        observers.remove(listner);

    }

    public void notifyObservers(StringBuffer data) {
        for (NewDataListener listner : observers) {
            listner.onNewData(data);
        }

    }

}