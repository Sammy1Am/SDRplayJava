/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.sammy1am.sdrplay.jnr;

import io.github.sammy1am.sdrplay.EventParameters;
import io.github.sammy1am.sdrplay.SDRplayAPI;
import io.github.sammy1am.sdrplay.jnr.TunerParamsT.TunerSelectT;
import jnr.ffi.annotations.Delegate;
import jnr.ffi.Struct;
import jnr.ffi.Union;
import jnr.ffi.util.EnumMapper;



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
    
    // TODO Explicitly converting pointers to structs here-- not sure if this is most efficient,
    // seems like JNR should be able to do this for us
    public interface StreamCallback {
        @Delegate
        default public void call(jnr.ffi.Pointer xi, jnr.ffi.Pointer xq, jnr.ffi.Pointer params, int numSamples, int reset, jnr.ffi.Pointer cbContext)
        {
            StreamCbParamsT paramsStruct = new StreamCbParamsT(SDRplayAPI.getJNRRuntime());
            paramsStruct.useMemory(params);
            
            call(xi, xq, paramsStruct, numSamples,reset,cbContext);
        }
        
        public void call(jnr.ffi.Pointer xi, jnr.ffi.Pointer xq, StreamCbParamsT params, int numSamples, int reset, jnr.ffi.Pointer cbContext);
    }

    public interface EventCallback {
        @Delegate
        default public void call(int eventId, int tuner, jnr.ffi.Pointer params, jnr.ffi.Pointer cbContext) 
        {
        	try {
	            EventParamsT paramsStruct = new EventParamsT(SDRplayAPI.getJNRRuntime());
	            paramsStruct.useMemory(params);
	            EventParameters parms = new EventParameters(EventT.valueOf(eventId), paramsStruct);
	            
	            call(EventT.valueOf(eventId), TunerSelectT.valueOf(tuner), parms);
        	}
        	catch(Throwable ex) {
        		System.out.println(ex.toString());
        		ex.printStackTrace();
        	}
        };
        
        public void call(EventT eventId, TunerSelectT tuner, EventParameters params);
    }
    
    public static enum PowerOverloadCbEventIdT {
        Overload_Detected(0),
        Overload_Corrected(1);

        private final int val;

        PowerOverloadCbEventIdT(int val) {
            this.val = val;
        }
        
        public int intValue() {
            return val;
        }
    }
    
    public static enum RspDuoModeCbEventIdT {
        MasterInitialised     (0),
        SlaveAttached         (1),
        SlaveDetached         (2),
        SlaveInitialised      (3),
        SlaveUninitialised    (4),
        MasterDllDisappeared  (5),
        SlaveDllDisappeared   (6);

        private final int val;

        RspDuoModeCbEventIdT(int val) {
            this.val = val;
        }
        
        public int intValue() {
            return val;
        }
    }
    
    public static enum EventT {
        GainChange(0),
        PowerOverloadChange(1),
        DeviceRemoved(2),
        RspDuoModeChange(3);

        private final int val;

        EventT(int val) {
            this.val = val;
        }
        
        public static final EventT valueOf(int pattern) {
            return (EventT)EnumMapper.getInstance(EventT.class).valueOf(pattern);
        }
        
        public int intValue() {
            return val;
        }
    }
    
    public static class GainCbParamT extends Struct {
        public Unsigned32 gRdB = new Unsigned32();
        public Unsigned32 lnaGRdb = new Unsigned32();
        public Double currGain = new Double();
        
        public GainCbParamT(final jnr.ffi.Runtime runtime) {
            super(runtime);
        }
    }
    
    public static class PowerOverloadCbParamT extends Struct {
        public Enum<PowerOverloadCbEventIdT> powerOverloadChangeType = new Enum<>(PowerOverloadCbEventIdT.class);
        
        public PowerOverloadCbParamT(final jnr.ffi.Runtime runtime) {
            super(runtime);
        }
    }
    
    public static class RspDuoModeCbParamT extends Struct {
        public Enum<RspDuoModeCbEventIdT> modeChangeType = new Enum<>(RspDuoModeCbEventIdT.class);
        
        public RspDuoModeCbParamT(final jnr.ffi.Runtime runtime) {
            super(runtime);
        }
    }
    
    public static class EventParamsT extends Union {
        public GainCbParamT gainParams;
        public PowerOverloadCbParamT powerOverloadParams;
        public RspDuoModeCbParamT rspDuoModeParams;
        
        public EventParamsT(final jnr.ffi.Runtime runtime) {
            super(runtime);
            
            gainParams = inner(new GainCbParamT(runtime));
            powerOverloadParams = inner(new PowerOverloadCbParamT(runtime));
            rspDuoModeParams = inner(new RspDuoModeCbParamT(runtime));
        }
    }
    
    public static class StreamCbParamsT extends Struct {
        public Unsigned32 firstSampleNum = new Unsigned32();
        public Signed32 grChanged = new Signed32();
        public Signed32 rfChanged = new Signed32();
        public Signed32 fsChanged = new Signed32();
        public Unsigned32 numSamples = new Unsigned32();
        
        public StreamCbParamsT(final jnr.ffi.Runtime runtime) {
            super(runtime);
        }
    }
}
