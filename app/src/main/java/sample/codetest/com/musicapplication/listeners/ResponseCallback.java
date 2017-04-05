package sample.codetest.com.musicapplication.listeners;

/**
 * Created by Megha on 20-03-2017.
 */
public interface ResponseCallback {

    void onSuccess(Object response);
    void onError(Object error);
}
