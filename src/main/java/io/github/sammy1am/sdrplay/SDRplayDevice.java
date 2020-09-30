package io.github.sammy1am.sdrplay;

import io.github.sammy1am.sdrplay.jnr.CallbackFnsT;
import io.github.sammy1am.sdrplay.jnr.CallbackFnsT.EventParamsT;
import io.github.sammy1am.sdrplay.jnr.CallbackFnsT.EventT;
import io.github.sammy1am.sdrplay.jnr.CallbackFnsT.StreamCbParamsT;
import io.github.sammy1am.sdrplay.jnr.DeviceParamsT;
import io.github.sammy1am.sdrplay.jnr.SDRplayAPIJNR;
import io.github.sammy1am.sdrplay.jnr.SDRplayAPIJNR.DeviceT;
import io.github.sammy1am.sdrplay.jnr.TunerParamsT;
import io.github.sammy1am.sdrplay.jnr.TunerParamsT.TunerSelectT;
import jnr.ffi.Pointer;
import jnr.ffi.byref.PointerByReference;

/**
 * Java wrapper representing a single SDRplay device.
 * @author Sammy1Am
 */
public class SDRplayDevice {
    private static SDRplayAPIJNR JNRAPI = SDRplayAPI.getJNRInstance();
    private static jnr.ffi.Runtime RUNTIME = SDRplayAPI.getJNRRuntime();
    
    
    /** Representation of this device in native API memory. */
    private DeviceT nativeDevice;
    
    /** Representation of parameters for this device in native API memory. */
    private DeviceParamsT nativeParams = new DeviceParamsT(RUNTIME);
    
    /** Callbacks for receiving stream and event data */
    private CallbackFnsT callbacks = new CallbackFnsT(RUNTIME);
    private StreamsReceiver streamsReceiver = StreamsReceiver.NULL_RECEIVER;
    
    /*
    These callbacks serve only as a default wrapper to point to the user-set callbacks
    on this class (so that if callbacks are un-set, device can still be inited).
    */
    CallbackFnsT.StreamCallback scba =  new CallbackFnsT.StreamCallback() {
        @Override
        public void call(Pointer xi, Pointer xq, StreamCbParamsT params, int numSamples, int reset, Pointer cbContext) {
            System.out.println("DS A");
            //streamsReceiver.receiveStreamA(xi, xq, params, numSamples, reset);
        }
    };

    CallbackFnsT.StreamCallback scbb =  new CallbackFnsT.StreamCallback() {
        @Override
        public void call(Pointer xi, Pointer xq, StreamCbParamsT params, int numSamples, int reset, Pointer cbContext) {
            System.out.println("DS B");
            //streamsReceiver.receiveStreamB(xi, xq, params, numSamples, reset);
        }
    };

    CallbackFnsT.EventCallback ecb = new CallbackFnsT.EventCallback() {
        @Override
        public void call(EventT eventId, TunerSelectT tuner, EventParamsT params, Pointer cbContext) {
            System.out.println("DE");
            //streamsReceiver.receiveEvent(eventId, TunerParamsT.TunerSelectT.Tuner_A, params);
        }
    };
    
    /** Has this device been selected.  Used to optimize parameter retrieval,
     * and make sure we're not trying anything fishy with an un-selected device.
     */
    private boolean isSelected = false;
    
    /**
     * Creates a friendly Java wrapper around an SDRplay API device.
     * @param nativeDevice Native representation of the DeviceT struct.
     */
    public SDRplayDevice(DeviceT nativeDevice) {
        this.nativeDevice = nativeDevice;
        
        callbacks.StreamACbFn.set(scba);
        callbacks.StreamBCbFn.set(scbb);
        callbacks.EventCbFn.set(ecb);
    }
    
    public void select() {
        try {
            // Select the device in the API
            ApiException.checkErrorCode(JNRAPI.sdrplay_api_SelectDevice(nativeDevice));
            
            // Fetch parameters for this device
            PointerByReference deviceParamsPBR = new PointerByReference();
            ApiException.checkErrorCode(JNRAPI.sdrplay_api_GetDeviceParams(nativeDevice.dev.get(), deviceParamsPBR));
            nativeParams.useMemory(deviceParamsPBR.getValue());
            
            // Set selected
            isSelected = true;
        
        } catch (ApiException ae) {
            try {
                release(); // If we failed to select, at least try to release
            } finally {
                throw ae;
            }
        }
    }
    
    public void release() {
        ApiException.checkErrorCode(JNRAPI.sdrplay_api_ReleaseDevice(nativeDevice));
        isSelected = false;
    }
    
    public void setStreamsReceiver(StreamsReceiver sr) {
        streamsReceiver = sr;
    }
}
