package io.github.sammy1am.sdrplay;

import io.github.sammy1am.sdrplay.jnr.CallbackFnsT.EventParamsT;
import io.github.sammy1am.sdrplay.jnr.CallbackFnsT.EventT;
import io.github.sammy1am.sdrplay.jnr.CallbackFnsT.StreamCbParamsT;
import io.github.sammy1am.sdrplay.jnr.TunerParamsT.TunerSelectT;

/**
 * Abstract for Java classes that want to consume streams and events from SDRplay
 * @author Sammy1Am
 */
public interface StreamsReceiver {
    default public void receiveStreamA(short[] xi, short[] xq, StreamCbParamsT params, int numSamples, int reset){};
    default public void receiveStreamB(short[] xi, short[] xq, StreamCbParamsT params, int numSamples, int reset){};
    default public void receiveEvent(EventT eventId, TunerSelectT tuner, EventParameters params){};
    
    /** Consumes streams but does nothing with them */
    public static StreamsReceiver NULL_RECEIVER = new StreamsReceiver() {
    };
}
