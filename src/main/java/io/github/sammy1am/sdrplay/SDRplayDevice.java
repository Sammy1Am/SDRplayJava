package io.github.sammy1am.sdrplay;

import io.github.sammy1am.sdrplay.ApiException.NotInitialisedException;
import io.github.sammy1am.sdrplay.SDRplayAPI.HWModel;
import io.github.sammy1am.sdrplay.jnr.CallbackFnsT;
import io.github.sammy1am.sdrplay.jnr.CallbackFnsT.EventParamsT;
import io.github.sammy1am.sdrplay.jnr.CallbackFnsT.EventT;
import io.github.sammy1am.sdrplay.jnr.CallbackFnsT.StreamCbParamsT;
import io.github.sammy1am.sdrplay.jnr.DeviceParamsT;
import io.github.sammy1am.sdrplay.jnr.SDRplayAPIJNR;
import io.github.sammy1am.sdrplay.jnr.SDRplayAPIJNR.DbgLvl_t;
import io.github.sammy1am.sdrplay.jnr.SDRplayAPIJNR.DeviceT;
import io.github.sammy1am.sdrplay.jnr.SDRplayAPIJNR.ReasonForUpdateExtension1T;
import io.github.sammy1am.sdrplay.jnr.SDRplayAPIJNR.ReasonForUpdateT;
import io.github.sammy1am.sdrplay.jnr.TunerParamsT;
import io.github.sammy1am.sdrplay.jnr.TunerParamsT.Bw_MHzT;
import io.github.sammy1am.sdrplay.jnr.TunerParamsT.If_kHzT;
import io.github.sammy1am.sdrplay.jnr.TunerParamsT.LoModeT;
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
            //TODO: There's probably a more efficient way to do this, but try this for now (as long as performance allows)
            short[] ia = new short[numSamples];
            short[] qa = new short[numSamples];
            xi.get(0, ia, 0, numSamples);
            xq.get(0, qa, 0, numSamples);
            
            
            streamsReceiver.receiveStreamA(ia, qa, params, numSamples, reset);
        }
    };

    CallbackFnsT.StreamCallback scbb =  new CallbackFnsT.StreamCallback() {
        @Override
        public void call(Pointer xi, Pointer xq, StreamCbParamsT params, int numSamples, int reset, Pointer cbContext) {
            //TODO: There's probably a more efficient way to do this, but try this for now (as long as performance allows)
            short[] ia = new short[numSamples];
            short[] qa = new short[numSamples];
            xi.get(0, ia, 0, numSamples);
            xq.get(0, qa, 0, numSamples);
            
            
            streamsReceiver.receiveStreamB(ia, qa, params, numSamples, reset);
        }
    };

    CallbackFnsT.EventCallback ecb = new CallbackFnsT.EventCallback() {
        @Override
        public void call(EventT eventId, TunerSelectT tuner, EventParamsT params, Pointer cbContext) {
            streamsReceiver.receiveEvent(eventId, tuner, new EventParameters(params));
        }
    };
    
    /** Has this device been selected.  Used to optimize parameter retrieval,
     * and make sure we're not trying anything fishy with an un-selected device.
     */
    private boolean isSelected = false;
    
    /**
     * Has this device been initialized.  Used to determine if update() needs to be
     * called after settings changes or not.
     */
    private boolean isInitialized = false;
    
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
    
    public String getSerialNumber() {
        return nativeDevice.SerNo.get();
    }
    
    public HWModel getHWModel() {
        return HWModel.valueOf((int)nativeDevice.hwVer.byteValue());
    }
    
    public TunerSelectT getTunerSelect() {
        return nativeDevice.tuner.get();
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
            } catch (ApiException releaseException) {
                // Don't worry about this
            }
            throw ae;
        }
    }
    
    public void release() {
        ApiException.checkErrorCode(JNRAPI.sdrplay_api_ReleaseDevice(nativeDevice));
        isSelected = false;
    }
    
    public void setStreamsReceiver(StreamsReceiver sr) {
        streamsReceiver = sr;
    }
    
    public void debugEnable(DbgLvl_t debugLevel) {
        ApiException.checkErrorCode(JNRAPI.sdrplay_api_DebugEnable(nativeDevice.dev.get(), debugLevel));
    }
    
    public void init() {
        if (!isSelected) {throw new RuntimeException("Device must be selected first!");}
        ApiException.checkErrorCode(JNRAPI.sdrplay_api_Init(nativeDevice.dev.get(), callbacks, null));
        isInitialized = true;
    }
    
    public void uninit() {
        try {
            ApiException.checkErrorCode(JNRAPI.sdrplay_api_Uninit(nativeDevice.dev.get()));
            isInitialized = false;
        } catch (NotInitialisedException nie) {
            // Well fine, that's what we wanted anyway.
        }
    }
    
    
    /*******************
     * DEVICE PARAMETERS
     * *****************
     * Methods to get and set device parameters.  There are a lot of these, so 
     * the most used ones will be added first.
     */
    
    private void doUpdate(ReasonForUpdateT reason) {
        ApiException.checkErrorCode(JNRAPI.sdrplay_api_Update(nativeDevice.dev.get(), 
                TunerSelectT.Tuner_A, // TODO: assume TunerA here for now-- maybe get this from nativeDevice?
                reason, 
                ReasonForUpdateExtension1T.Update_Ext1_None));
    }
    
    /**
     * 
     * @return Sample rate in Hz
     */
    public double getSampleRate() {
        return nativeParams.devParams.get().fsFreq.fsHz.get();
    }
    
    public void setSampleRate(double newSampleRate) {
        nativeParams.devParams.get().fsFreq.fsHz.set(newSampleRate);
        if (isInitialized) doUpdate(ReasonForUpdateT.Update_Dev_Fs);
    }
    
    public double getPPM() {
        return nativeParams.devParams.get().ppm.get();
    }
    
    public void setPPM(double newPPM) {
        nativeParams.devParams.get().ppm.set(newPPM);
        if (isInitialized) doUpdate(ReasonForUpdateT.Update_Dev_Ppm);
    }
    
    // TODO: All of these assume TunerA, but for a dual tuner would need to specify
    // Made create a Tuner object that Devices can contain up to two of??
    
    public Bw_MHzT getBwType() {
        return nativeParams.rxChannelA.get().tunerParams.bwType.get();
    }
    
    public void setBwType(Bw_MHzT newBwType) {
        nativeParams.rxChannelA.get().tunerParams.bwType.set(newBwType);
        if (isInitialized) doUpdate(ReasonForUpdateT.Update_Tuner_BwType);
    }
    
    public If_kHzT getIfType() {
        return nativeParams.rxChannelA.get().tunerParams.ifType.get();
    }
    
    public void setIfType(If_kHzT newIfType) {
        nativeParams.rxChannelA.get().tunerParams.ifType.set(newIfType);
        if (isInitialized) doUpdate(ReasonForUpdateT.Update_Tuner_IfType);
    }
    
    public LoModeT getLoMode() {
        return nativeParams.rxChannelA.get().tunerParams.loMode.get();
    }
    
    public void setLoMode(LoModeT newLoMode) {
        nativeParams.rxChannelA.get().tunerParams.loMode.set(newLoMode);
        if (isInitialized) doUpdate(ReasonForUpdateT.Update_Tuner_LoMode);
    }
    
    // TODO: Gain stuff
    
    public double getRfHz() {
        return nativeParams.rxChannelA.get().tunerParams.rfFreq.rfHz.get();
    }
    
    public void setRfHz(double newRfHz) {
        nativeParams.rxChannelA.get().tunerParams.rfFreq.rfHz.set(newRfHz);
        if (isInitialized) doUpdate(ReasonForUpdateT.Update_Tuner_Frf);
    }

    public void acknowledgeOverload() {
        if (isInitialized) doUpdate(ReasonForUpdateT.Update_Ctrl_OverloadMsgAck);
    }
}
