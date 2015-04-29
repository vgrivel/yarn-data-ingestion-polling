package ch.daplab.yarn.poller.worker;

import ch.daplab.yarn.poller.NewDataListener;

/**
 * Created by vincent on 4/29/15.
 */
public interface Observable {
    void registerObserver(NewDataListener listner);

    void removeObserver(NewDataListener listner);

    void notifyObservers(StringBuffer data);

}