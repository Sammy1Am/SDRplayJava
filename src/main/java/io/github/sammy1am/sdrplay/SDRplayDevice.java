package io.github.sammy1am.sdrplay;

import io.github.sammy1am.sdrplay.ApiException.NotInitialisedException;
import io.github.sammy1am.sdrplay.SDRplayAPI.HWModel;
import io.github.sammy1am.sdrplay.jnr.CallbackFnsT;
import io.github.sammy1am.sdrplay.jnr.CallbackFnsT.EventParamsT;
import io.github.sammy1am.sdrplay.jnr.CallbackFnsT.EventT;
import io.github.sammy1am.sdrplay.jnr.CallbackFnsT.StreamCbParamsT;
import io.github.sammy1am.sdrplay.jnr.ControlParamsT.AgcControlT;
import io.github.sammy1am.sdrplay.jnr.DeviceParamsT;
import io.github.sammy1am.sdrplay.jnr.SDRplayAPIJNR;
import io.github.sammy1am.sdrplay.jnr.SDRplayAPIJNR.DbgLvl_t;
import io.github.sammy1am.sdrplay.jnr.SDRplayAPIJNR.DeviceT;
import io.github.sammy1am.sdrplay.jnr.SDRplayAPIJNR.ReasonForUpdateExtension1T;
import io.github.sammy1am.sdrplay.jnr.SDRplayAPIJNR.ReasonForUpdateT;
import io.github.sammy1am.sdrplay.jnr.TunerParamsT.Bw_MHzT;
import io.github.sammy1am.sdrplay.jnr.TunerParamsT.If_kHzT;
import io.github.sammy1am.sdrplay.jnr.TunerParamsT.LoModeT;
import io.github.sammy1am.sdrplay.jnr.TunerParamsT.TunerSelectT;
import jnr.ffi.Pointer;
import jnr.ffi.byref.PointerByReference;

/**
 * Java wrapper representing a single SDRplay device.  This base class should work
 * fine for any SDRplay device, but for model-specific functionality the classes
 * in io.github.sammy1am.sdrplay.model should be used.
 * @author Sammy1Am
 */
public class SDRplayDevice {
    private static SDRplayAPIJNR JNRAPI = SDRplayAPI.getJNRInstance();
    private static jnr.ffi.Runtime RUNTIME = SDRplayAPI.getJNRRuntime();
    
    
    /** Representation of this device in native API memory. */
    protected DeviceT nativeDevice;
    
    /** Representation of parameters for this device in native API memory. */
    protected DeviceParamsT nativeParams = new DeviceParamsT(RUNTIME);
    
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
    protected boolean isSelected = false;
    
    /**
     * Has this device been initialized.  Used to determine if update() needs to be
     * called after settings changes or not.
     */
    protected boolean isInitialized = false;
    
    /**
     * Specifies the number of LNA states available for this device.  At time of coding
     * the lowest number of states is 4 supported on the RSP1.  Higher numbers of states
     * are implemented by specific model classes.
     */
    private final byte NUM_LNA_STATES = 4;
    
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
    	return HWModel.valueOf((int)nativeDevice.hwVer.byteValue() & 0xff);
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
    
    /**
     * Determines the number of allowed LNA states based on the currently tuned frequency.
     * Should be overridden by specific device implementations.
     * @return Max number of LNA states.
     */
    public byte getNumLNAStates() {
        return this.NUM_LNA_STATES;
    }
    
    /*
     * DEVICE PARAMETERS
     * *****************
     * Methods to get and set device parameters.  There are a lot of these, so 
     * the most used ones will be added first.
     */
    
    protected void doUpdate(ReasonForUpdateT reason) {
        ApiException.checkErrorCode(JNRAPI.sdrplay_api_Update(nativeDevice.dev.get(), 
                TunerSelectT.Tuner_A, // TODO: assume TunerA here for now-- maybe get this from nativeDevice?
                reason, 
                ReasonForUpdateExtension1T.Update_Ext1_None));
    }
    
    /**
     *  sdrplay_api_DevParamsT.sdrplay_api_FsFreqT.fsHz
     * @return Sample rate in Hz
     */
    public double getSampleRate() {
        return nativeParams.devParams.get().fsFreq.fsHz.get();
    }
    
 
    public void setSampleRate(double newSampleRate) {
        nativeParams.devParams.get().fsFreq.fsHz.set(newSampleRate);
        if (isInitialized) doUpdate(ReasonForUpdateT.Update_Dev_Fs);
    }
    
    public void setSampleRateAndIF(double newSampleRate, Bw_MHzT newBwType, If_kHzT newIfType) {
    	nativeParams.devParams.get().fsFreq.fsHz.set(newSampleRate);
    	nativeParams.rxChannelA.get().tunerParams.bwType.set(newBwType);
    	nativeParams.rxChannelA.get().tunerParams.ifType.set(newIfType);
    	if (isInitialized) doUpdate(ReasonForUpdateT.Update_Custom_sdrtrunk_SampleRateChange);
    	
    }
    
    /**
     * sdrplay_api_DeviceParamsT.sdrplay_api_DevParamsT.ppm
     * @return Parts Per Million correction 
     */
    public double getPPM() {
        return nativeParams.devParams.get().ppm.get();
    }
    
    public void setPPM(double newPPM) {
        nativeParams.devParams.get().ppm.set(newPPM);
        if (isInitialized) doUpdate(ReasonForUpdateT.Update_Dev_Ppm);
    }
    
    // Assumes Tuner A for all following settings
    
    /**
     * sdrplay_api_TunerParamsT.bwType
     * @return Intermediate Frequency Bandwidth
     * 
     */
    public Bw_MHzT getBwType() {
        return nativeParams.rxChannelA.get().tunerParams.bwType.get();
    }
    
    public void setBwType(Bw_MHzT newBwType) {
        nativeParams.rxChannelA.get().tunerParams.bwType.set(newBwType);
        if (isInitialized) doUpdate(ReasonForUpdateT.Update_Tuner_BwType);
    }
    
    /**
     * sdrplay_api_TunerParamsT.ifType
     * An enum value from sdrplay_api_If_kHzT
     * 
     * @return Intermediate Frequency Type (see sdrplay_api_If_kHzT enum for values)
     */
    public If_kHzT getIfType() {
        return nativeParams.rxChannelA.get().tunerParams.ifType.get();
    }
    
    public void setIfType(If_kHzT newIfType) {
        nativeParams.rxChannelA.get().tunerParams.ifType.set(newIfType);
        if (isInitialized) doUpdate(ReasonForUpdateT.Update_Tuner_IfType);
    }
    
    /**
     * 
     * @return Local Oscillator Mode
     */
    public LoModeT getLoMode() {
    	return nativeParams.rxChannelA.get().tunerParams.loMode.get();
    }
    
    public void setLoMode(LoModeT newLoMode) {
    	nativeParams.rxChannelA.get().tunerParams.loMode.set(newLoMode);
    	if (isInitialized) doUpdate(ReasonForUpdateT.Update_Tuner_LoMode);
    }
    
    /**
     * DC Offset
     * @param enableDC enable or disable DC Offset
     */
    public void setDcOffset(boolean enableDC) {
    	nativeParams.rxChannelA.get().ctrlParams.dcOffset.DCenable.set(enableDC ? 1: 0);
    	nativeParams.rxChannelA.get().ctrlParams.dcOffset.IQenable.set(enableDC ? 1: 0);
    	
    	if(isInitialized) doUpdate(ReasonForUpdateT.Update_Ctrl_DCoffsetIQimbalance);
    }

    /**
     * Override in model specific subclass
     * @return FM Broadcast Notch state
     */
    public boolean getRfNotch() {
    	return false;
    }
    public void setRfNotch(boolean rfNotch) {
    	//defaults to no operation
    }
    
    /**
     * Override in model specific subclass
     * @return Digital Audio Broadcast Notch state
     */
    public boolean getDABNotch() {
    	return false;
    }
    public void setDABNotch(boolean dabNotch) {
    	//defaults to no operation
    }
    
    /**
     * Override in model specific subclass
     * @return BiasT - puts current on the antenna to power pre-amplifiers
     */
    public boolean getBiasT() {
    	return false;
    }
    public void setBiasT(boolean biasT) {
    	//defaults to no operation
    }
    
    /**
     * @return Automatic Gain Control
     */
    public boolean getAGCEnabled() {
    	return nativeParams.rxChannelA.get().ctrlParams.agc.enable.get() != AgcControlT.AGC_DISABLE;
    }
    
    public void setAGCEnabled(boolean enabled) {
    	if(enabled) {
    		nativeParams.rxChannelA.get().ctrlParams.agc.enable.set(AgcControlT.AGC_50HZ);
    		nativeParams.rxChannelA.get().ctrlParams.agc.setPoint_dBfs.set(-30);
    		nativeParams.rxChannelA.get().ctrlParams.agc.attack_ms.set(500);
    		nativeParams.rxChannelA.get().ctrlParams.agc.decay_delay_ms.set(200);
    		nativeParams.rxChannelA.get().ctrlParams.agc.decay_threshold_dB.set(5);
    		nativeParams.rxChannelA.get().ctrlParams.agc.syncUpdate.set(0);
    	}
    	else {
    		nativeParams.rxChannelA.get().ctrlParams.agc.enable.set(AgcControlT.AGC_DISABLE);
    	}
    	if (isInitialized) doUpdate(ReasonForUpdateT.Update_Ctrl_Agc);
    	
    }
    
    /**
     * sdrplay_api_TunerParamsT.sdrplay_api_GainT.LNAstate
     * Review tables in SDRplay API documentation for valid values
     * 
     * @return Low Noise Amplifier Gain Reduction State
     */
    public byte getLNAState() {
        return nativeParams.rxChannelA.get().tunerParams.gain.LNAstate.byteValue();
    }
    
    public void setLNAState(byte newLNAState) {
        nativeParams.rxChannelA.get().tunerParams.gain.LNAstate.set(newLNAState);
        if (isInitialized) doUpdate(ReasonForUpdateT.Update_Tuner_Gr);
    }
    
    /**
     * sdrplay_api_TunerParamsT.sdrplay_api_RfFreqT.rfHz
     * Tuned Radio Frequency - aka center frequency?
     * 
     * @return Tuned Frequency
     */
    public double getRfHz() {
        return nativeParams.rxChannelA.get().tunerParams.rfFreq.rfHz.get();
    }
    
    public void setRfHz(double newRfHz) {
    	//System.out.printf("Setting Frequency: %fl", newRfHz);
        nativeParams.rxChannelA.get().tunerParams.rfFreq.rfHz.set(newRfHz);
        if (isInitialized) doUpdate(ReasonForUpdateT.Update_Tuner_Frf);
    }
    
    /**
     * @return Decimation Factor
     */
    public int getDecFactor() {
    	return nativeParams.rxChannelA.get().ctrlParams.decimation.decimationFactor.get();
    }
    public void setDecFactor(int newDecFactor) {
    	boolean sampleRateBelow5M = getSampleRate() <= 5_000_000;
    	nativeParams.rxChannelA.get().ctrlParams.decimation.decimationFactor.set((newDecFactor > 1)  && sampleRateBelow5M ? newDecFactor : 0);
    	nativeParams.rxChannelA.get().ctrlParams.decimation.enable.set((newDecFactor > 1) && sampleRateBelow5M ? 1 : 0);
    	nativeParams.rxChannelA.get().ctrlParams.decimation.wideBandSignal.set(0);
    		
    	if (isInitialized) doUpdate(ReasonForUpdateT.Update_Ctrl_Decimation);
    }
 
    /**
     * Tells the SDRPlay device we have received its antenna overload callback
     */
    public void acknowledgeOverload() {
        if (isInitialized) doUpdate(ReasonForUpdateT.Update_Ctrl_OverloadMsgAck);
    }
}
