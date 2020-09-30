/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.sammy1am.sdrplay.jnr;

import jnr.ffi.annotations.Delegate;



/**
 * JNR class for providing Java callbacks to SDRplay API (sdrplay_api_callback.h).
 * @author Sammy1Am
 */
public class CallbackFnsT extends BaseStruct {
    public final Func<StreamCallback> StreamACbFn = func(StreamCallback.class);
    public final Func<StreamCallback> StreamBCbFn = func(StreamCallback.class);
    public final Func<EventCallback> EventCbFn = func(EventCallback.class);

    public CallbackFnsT(final jnr.ffi.Runtime runtime) {
        super(runtime);
    }
    
    public static interface StreamCallback {
        @Delegate
        public void call(jnr.ffi.Pointer xi, jnr.ffi.Pointer xq, jnr.ffi.Pointer params, int numSamples, int reset, jnr.ffi.Pointer cbContext);
    }

    public static interface EventCallback {
        @Delegate
        public void call(int eventId, int tuner, jnr.ffi.Pointer params, jnr.ffi.Pointer cbContext);
    }
}
