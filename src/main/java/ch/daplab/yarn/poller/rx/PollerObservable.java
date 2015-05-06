package ch.daplab.yarn.poller.rx;

import ch.daplab.utils.DefaultFileProcessing;
import ch.daplab.utils.FileProcessing;
import ch.daplab.yarn.poller.NewDataListener;
import ch.daplab.yarn.poller.worker.GeneralPoller;
import com.google.common.base.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rx.Observable;
import rx.Subscriber;

import java.util.Timer;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by vincent on 4/28/15.
 */
public class PollerObservable implements Observable.OnSubscribe<byte[]>, NewDataListener {

    private static final Logger LOG = LoggerFactory.getLogger(PollerObservable.class);

    private final AtomicBoolean close = new AtomicBoolean(false);
    private CountDownLatch countDownLatch = new CountDownLatch(1);
    private volatile AtomicReference<Subscriber<? super byte[]>> subscriberRef = new AtomicReference<>(null);
    private GeneralPoller poller;
    private FileProcessing processor;

    private String url;
    private boolean etagSupport;
    private long intervalMS;

    public PollerObservable(String url, boolean etagSupport, long intervalMs, String processorClass){
        this.url = url;
        this.etagSupport = etagSupport;
        this.intervalMS = intervalMs;
        instantiateClass(processorClass);
    }


    @Override
    public void call(Subscriber<? super byte[]> subscriber) {
        if (!subscriberRef.compareAndSet(null, subscriber)) {
            return;
        }

        poller = new GeneralPoller(url, etagSupport);
        poller.registerObserver(this);

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(poller, 0, intervalMS);

    }


    @Override
    public void onNewData(StringBuffer buffer) {
        if (close.get()) {
            return;
        }

        Subscriber<? super byte[]> subscriber = subscriberRef.get();
        Preconditions.checkNotNull(subscriber, "Function OnSubscribe.call must be called first!");

        if (subscriber.isUnsubscribed()) {
            internalClose();

        } else {
            //process the file by removing the header and change the csv separator from pipe to comma
            byte[] payload = processor.process(buffer.toString().getBytes());
            subscriber.onNext(payload);

        }
    }

    private void instantiateClass(String fqn) {
        if (fqn != null || !fqn.equals("")) {
            try {
                Class cl = Class.forName(fqn);
                processor = (FileProcessing) cl.newInstance();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        } else {
            processor = new DefaultFileProcessing();
        }
    }

    private void internalClose() {

        if (close.compareAndSet(false, true)) {

            poller.cancel();

            Subscriber<? super byte[]> subscriber = subscriberRef.get();
            if (subscriber != null && !subscriber.isUnsubscribed()) {
                subscriber.onCompleted();
                subscriber.unsubscribe();
            }

            countDownLatch.countDown();
        }
    }
}
