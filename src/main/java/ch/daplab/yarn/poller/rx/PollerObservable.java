package ch.daplab.yarn.poller.rx;

import ch.daplab.yarn.twitter.rx.TwitterObservable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rx.Observable;
import rx.Subscriber;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by vincent on 4/28/15.
 */
public class PollerObservable implements Observable.OnSubscribe<byte[]> {

    private static final Logger LOG = LoggerFactory.getLogger(TwitterObservable.class);

    private final AtomicBoolean close = new AtomicBoolean(false);

    private volatile AtomicReference<Subscriber<? super byte[]>> subscriberRef = new AtomicReference<>(null);

    @Override
    public void call(Subscriber<? super byte[]> subscriber) {

    }


}
