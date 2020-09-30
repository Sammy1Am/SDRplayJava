/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.sammy1am.sdrplay.jnr;

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
    
    public static interface StreamCallback {
        @Delegate
        public void call(jnr.ffi.Pointer xi, jnr.ffi.Pointer xq, StreamCbParamsT params, int numSamples, int reset, jnr.ffi.Pointer cbContext);
    }

    public static interface EventCallback {
        @Delegate
        public void call(EventT eventId, TunerSelectT tuner, EventParamsT params, jnr.ffi.Pointer cbContext);
    }
    
    public static enum PowerOverloadCbEventIdT implements EnumMapper.IntegerEnum {
        Overload_Detected(0),
        Overload_Corrected(1);

        private final int val;

        PowerOverloadCbEventIdT(int val) {
            this.val = val;
        }
        
        @Override
        public int intValue() {
            return val;
        }
    }
    
    public static enum RspDuoModeCbEventIdT implements EnumMapper.IntegerEnum {
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
        
        @Override
        public int intValue() {
            return val;
        }
    }
    
    public static enum EventT implements EnumMapper.IntegerEnum {
        GainChange(0),
        PowerOverloadChange(1),
        DeviceRemoved(2),
        RspDuoModeChange(3);

        private final int val;

        EventT(int val) {
            this.val = val;
        }
        
        @Override
        public int intValue() {
            return val;
        }
    }
    
    public static class GainCbParamT extends Struct {
        //TODO
        public GainCbParamT(final jnr.ffi.Runtime runtime) {
            super(runtime);
        }
    }
    
    public static class PowerOverloadCbParamT extends Struct {
        //TODO
        public PowerOverloadCbParamT(final jnr.ffi.Runtime runtime) {
            super(runtime);
        }
    }
    
    public static class RspDuoModeCbParamT extends Struct {
        //TODO
        public RspDuoModeCbParamT(final jnr.ffi.Runtime runtime) {
            super(runtime);
        }
    }
    
    public static class EventParamsT extends Union {
        //TODO
        public EventParamsT(final jnr.ffi.Runtime runtime) {
            super(runtime);
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
