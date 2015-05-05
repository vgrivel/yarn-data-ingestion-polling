package ch.daplab.yarn.poller.rx;

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


import static ch.daplab.yarn.poller.PollerToHDFSCli.*;

/**
 * Created by vincent on 4/28/15.
 */
public class PollerObservable implements Observable.OnSubscribe<byte[]>, NewDataListener {

    private static final Logger LOG = LoggerFactory.getLogger(PollerObservable.class);

    private final AtomicBoolean close = new AtomicBoolean(false);
    private CountDownLatch countDownLatch = new CountDownLatch(1);
    private volatile AtomicReference<Subscriber<? super byte[]>> subscriberRef = new AtomicReference<>(null);
    private GeneralPoller poller;



    @Override
    public void call(Subscriber<? super byte[]> subscriber) {
        if (!subscriberRef.compareAndSet(null, subscriber)) {
            return;
        }

        poller = new GeneralPoller(URL, ETAG_SUPPORT);
        poller.registerObserver(this);

        LOG.info("Starting to read from the source");
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(poller, 0, INTERVAL_MS);

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
            byte[] payload = FILE_OBJECT.process(buffer.toString().getBytes());

            subscriber.onNext(payload);

            LOG.info("new data there!!!");

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
