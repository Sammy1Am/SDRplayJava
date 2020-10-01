package io.github.sammy1am.sdrplay.jnr;

import jnr.ffi.Struct;
import jnr.ffi.Runtime;
import jnr.ffi.util.EnumMapper;

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

        public Double ppm;                      // default: 0.0
        public FsFreqT fsFreq;
        public SyncUpdateT syncUpdate;
        public ResetFlagsT resetFlags;
        public Struct.Enum<TransferModeT> mode; // default: sdrplay_api_ISOCH
        public Unsigned32 samplesPerPkt;        // default: 0 (output param)
        
// TODO
//        Rsp1aParamsT rsp1aParams;
//        Rsp2ParamsT rsp2Params;
//        RspDuoParamsT rspDuoParams;
//        RspDxParamsT rspDxParams;
        
        
        public DevParamsT(final Runtime runtime) {
            super(runtime);
            // Initialize here for acess to runtime
            ppm = new Double();
            fsFreq = inner(new FsFreqT(runtime));
            syncUpdate = inner(new SyncUpdateT(runtime));
            resetFlags = inner(new ResetFlagsT(runtime));
            mode = new Struct.Enum<>(TransferModeT.class);
            samplesPerPkt = new Unsigned32();
        }
    }
    
    public static enum TransferModeT implements EnumMapper.IntegerEnum {
        ISOCH(0),
        BULK(1);

        private final int val;

        TransferModeT(int val) {
            this.val = val;
        }
        
        @Override
        public int intValue() {
            return val;
        }
    }
    
    public static class FsFreqT extends Struct {
        public Double fsHz = new Double();              // default: 2000000.0
        public Unsigned8 syncUpdate = new Unsigned8();  // default: 0
        public Unsigned8 reCal = new Unsigned8();       // default: 0
                
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
    
    public static class ResetFlagsT extends Struct {
        public Unsigned32 resetGainUpdate = new Unsigned32();     // default: 0
        public Unsigned32 resetRfUpdate = new Unsigned32();       // default: 0
        public Unsigned32 resetFsUpdate = new Unsigned32();       // default: 0
                
        public ResetFlagsT(final Runtime runtime) {
            super(runtime);
        }
    }
    
    public static class RxChannelParamsT extends Struct {

        public TunerParamsT tunerParams;
        public ControlParamsT ctrlParams;
        
// TODO
//        Rsp1aTunerParamsT   rsp1aTunerParams; 
//        Rsp2TunerParamsT    rsp2TunerParams; 
//        RspDuoTunerParamsT  rspDuoTunerParams;    
//        RspDxTunerParamsT   rspDxTunerParams;
        
        public RxChannelParamsT(final Runtime runtime) {
            super(runtime);
            tunerParams = inner(new TunerParamsT(runtime));
            ctrlParams = inner(new ControlParamsT(runtime));
        }
    }
}
