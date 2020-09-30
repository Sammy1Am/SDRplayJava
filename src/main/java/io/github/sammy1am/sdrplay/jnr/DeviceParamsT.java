package io.github.sammy1am.sdrplay.jnr;

import jnr.ffi.Struct;
import jnr.ffi.Runtime;

/**
 * JNR file containing sdrplay_api_DeviceParamsT and sub-structures (sdrplay_api_dev.h).
 */
public class DeviceParamsT extends Struct {
    
    public StructRef<DevParamsT> devParams = new StructRef<>(DevParamsT.class);
    public StructRef<RxChannelParamsT> rxChannelA = new StructRef<>(RxChannelParamsT.class);
    public StructRef<RxChannelParamsT> rxChannelB = new StructRef<>(RxChannelParamsT.class);
    
    public DeviceParamsT(final Runtime runtime) {
        super(runtime);
    }
    
    public static class DevParamsT extends Struct {

        public Double ppm;
        public FsFreqT fsFreq;
        public SyncUpdateT syncUpdate;
        
        
        public DevParamsT(final Runtime runtime) {
            super(runtime);
            // Initialize here for acess to runtime
            ppm = new Double();
            fsFreq = inner(new FsFreqT(runtime));
            syncUpdate = inner(new SyncUpdateT(runtime));
        }
    }
    
    public static class FsFreqT extends Struct {
        public Double fsHz = new Double();            // default: 2000000.0
        public Unsigned8 syncUpdate = new Unsigned8();   // default: 0
        public Unsigned8 reCal = new Unsigned8();        // default: 0
                
        public FsFreqT(final Runtime runtime) {
            super(runtime);
        }
    }
    
    public static class SyncUpdateT extends Struct {
        public Unsigned32 sampleNum = new Unsigned32();     // default: 0
        public Unsigned32 period = new Unsigned32();        // default: 0
                
        public SyncUpdateT(final Runtime runtime) {
            super(runtime);
        }
    }

    
    
    public static class RxChannelParamsT extends Struct {

        public TunerParamsT tunerParams;
        
        public RxChannelParamsT(final Runtime runtime) {
            super(runtime);
            tunerParams = inner(new TunerParamsT(runtime));
        }
    }
}
