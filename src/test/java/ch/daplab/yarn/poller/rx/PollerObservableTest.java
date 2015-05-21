package ch.daplab.yarn.poller.rx;

import org.junit.Test;
import org.mockito.Mockito;
import rx.Observable;
import rx.Observer;

import java.io.IOException;
import java.util.Random;

/**
 * Created by mil2048 on 4/22/15.
 */
public class PollerObservableTest {

    private final Random r = new Random();

    @Test
    public void test() throws IOException {

        final int numberOfUpdate = 1;

        final PollerObservable pollerObservable = new PollerObservable("", false, 100, "");
        Observer<byte[]> observerMock = Mockito.mock(Observer.class);

        Observable<byte[]> observable = Observable.create(pollerObservable);

        // block until onComplete is called.
        observable.limit(numberOfUpdate).subscribe(observerMock);

        Mockito.verify(observerMock, Mockito.times(numberOfUpdate)).onNext(Mockito.<byte[]>any());
        Mockito.verify(observerMock, Mockito.times(1)).onCompleted();
        Mockito.verify(observerMock, Mockito.never()).onError(Mockito.<Throwable>any());

    }
}
