package io.github.sammy1am.sdrplay.jnr;

import jnr.ffi.NativeType;
import jnr.ffi.Struct;

/**
 * JNR file containing sdrplay_api_DeviceParamsT and sub-structures (sdrplay_api_dev.h).
 */
public class DeviceParamsT extends Struct {
    
	    public StructRef<DevParamsT> devParams = new StructRef<>(DevParamsT.class);
	    public StructRef<RxChannelParamsT> rxChannelA = new StructRef<>(RxChannelParamsT.class);
	    public StructRef<RxChannelParamsT> rxChannelB = new StructRef<>(RxChannelParamsT.class);
	    
	    public DeviceParamsT(final jnr.ffi.Runtime runtime) {
	        super(runtime);
	    }
	
    
    public static class DevParamsT extends Struct {

        public Double ppm;                      // default: 0.0
        public FsFreqT fsFreq;
        public SyncUpdateT syncUpdate;
        public ResetFlagsT resetFlags;
        public Struct.Enum32<TransferModeT> mode; // default: sdrplay_api_ISOCH
        public Unsigned32 samplesPerPkt;        // default: 0 (output param)
        
        // Device specific parameters
        public Rsp1aParamsT rsp1aParams;
        public Rsp2ParamsT   rsp2Params;
        public RspDuoParamsT rspduoParams;
        public RspDxParamsT rspdxParams;
        
        
        public DevParamsT(final jnr.ffi.Runtime runtime) {
            super(runtime);
            // Initialize here for access to runtime
            ppm = new Double();
            fsFreq = inner(new FsFreqT(runtime));
            syncUpdate = inner(new SyncUpdateT(runtime));
            resetFlags = inner(new ResetFlagsT(runtime));
            mode = new Struct.Enum32<>(TransferModeT.class);
            samplesPerPkt = new Unsigned32();
            rsp1aParams = inner(new Rsp1aParamsT(runtime));
            rsp2Params = inner(new Rsp2ParamsT(runtime));
            rspduoParams = inner(new RspDuoParamsT(runtime));
            rspdxParams = inner(new RspDxParamsT(runtime));
            
        }
    }
    
    public static class Rsp1aParamsT extends Struct {
    	public Unsigned8 rfNotchEnable;          // default: 0
        public Unsigned8 rfDabNotchEnable;
        
    	public Rsp1aParamsT(final jnr.ffi.Runtime runtime) {
    		super(runtime);
    		rfNotchEnable = new Unsigned8();
    		rfDabNotchEnable = new Unsigned8();
    	}
    }
    
    public static class Rsp2ParamsT extends Struct {
    	Unsigned8 extRefOutputEn;                // default: 0
    	
    	public Rsp2ParamsT(final jnr.ffi.Runtime runtime) {
    		super(runtime);
    		extRefOutputEn = new Unsigned8();
    	}
    }
    
    public static class RspDuoParamsT extends Struct {
    	Signed32 extRefOutputEn;                             // default: 0
    	
    	public RspDuoParamsT(final jnr.ffi.Runtime runtime) {
    		super(runtime);
    		extRefOutputEn = new Signed32();
    	}
    }
    
    public static class RspDxParamsT extends Struct {
    	public Unsigned8 hdrEnable;                            // default: 0
    	public Unsigned8 biasTEnable;                          // default: 0
    	public Struct.Enum32<RspDx_AntennaSelectT> antennaSel;    // default: sdrplay_api_RspDx_ANTENNA_A
    	public Unsigned8 rfNotchEnable;                        // default: 0
    	public Unsigned8 rfDabNotchEnable;                     // default: 0
    	public Padding pad;										//fix alignment that JNR doesn't seem to do
    	
    	public RspDxParamsT(final jnr.ffi.Runtime runtime) {
    		super(runtime);
    		hdrEnable = new Unsigned8();
    		biasTEnable = new Unsigned8();
    		antennaSel = new Struct.Enum32<>(RspDx_AntennaSelectT.class);
    		rfNotchEnable = new Unsigned8();
    		rfDabNotchEnable = new Unsigned8();
    		pad = new Padding(NativeType.UCHAR, 2);
    		
    	}
    }
    
    public static enum RspDx_AntennaSelectT {
    	RspDx_ANTENNA_A (0),
    	RspDx_ANTENNA_B (1),
    	RspDx_ANTENNA_C (2);
    	
    	private final int val;
    	
    	RspDx_AntennaSelectT(int val) {
    		this.val = val;
    	}

    	public int intValue() {
    		return val;
    	}
    	
    }
    
    public static enum TransferModeT {
        ISOCH(0),
        BULK(1);

        private final int val;

        TransferModeT(int val) {
            this.val = val;
        }
        
        public int intValue() {
            return val;
        }
    }
    
    public static class FsFreqT extends Struct {
        public Double fsHz;              // default: 2000000.0
        public Unsigned8 syncUpdate;  // default: 0
        public Unsigned8 reCal;       // default: 0
        public final Padding pad;		
                
        public FsFreqT(final jnr.ffi.Runtime runtime) {
            super(runtime);
            fsHz = new Double();
            syncUpdate = new Unsigned8();
            reCal = new Unsigned8();
            pad = new Padding(NativeType.UCHAR, 6);		// Fix alignment that JNR doesn't seem to do
            
        }
    }
    
    public static class SyncUpdateT extends Struct {
        public Unsigned32 sampleNum;     // default: 0
        public Unsigned32 period;        // default: 0
                
        public SyncUpdateT(final jnr.ffi.Runtime runtime) {
            super(runtime);
            sampleNum = new Unsigned32();
            period = new Unsigned32();
        }
    }
    
    public static class ResetFlagsT extends Struct {
        public Unsigned8 resetGainUpdate;     // default: 0
        public Unsigned8 resetRfUpdate;       // default: 0
        public Unsigned8 resetFsUpdate;       // default: 0
                
        public ResetFlagsT(final jnr.ffi.Runtime runtime) {
            super(runtime);
            resetGainUpdate = new Unsigned8();
            resetRfUpdate = new Unsigned8();
            resetFsUpdate = new Unsigned8();
        }
    }
    
    public static class RxChannelParamsT extends Struct {

        public TunerParamsT tunerParams;
        public ControlParamsT ctrlParams;
        public Rsp1aTunerParamsT rsp1aTunerParams;
        public Rsp2TunerParamsT rsp2TunerParams;
        public RspDuoTunerParamsT rspduoTunerParams;
        public RspDxTunerParamsT rspdxTunerParams;
       
        public RxChannelParamsT(final jnr.ffi.Runtime runtime) {
            super(runtime);
            tunerParams = inner(new TunerParamsT(runtime));
            ctrlParams = inner(new ControlParamsT(runtime));
            rsp1aTunerParams = inner(new Rsp1aTunerParamsT(runtime));
            rsp2TunerParams = inner(new Rsp2TunerParamsT(runtime));
            rspduoTunerParams = inner(new RspDuoTunerParamsT(runtime));
            rspdxTunerParams = inner(new RspDxTunerParamsT(runtime));
        }
    }
    
    public static class Rsp1aTunerParamsT extends Struct{
    	public  Unsigned8 biasTEnable;                   // default: 0
    	
    	public Rsp1aTunerParamsT(final jnr.ffi.Runtime runtime) {
    		super(runtime);
    		biasTEnable = new Unsigned8();
    	}
    }
    
    public static class Rsp2TunerParamsT extends Struct {
    	    public Unsigned8 biasTEnable;                   		// default: 0
    	    public Struct.Enum32<Rsp2_AmPortSelectT> amPortSel;    	// default: sdrplay_api_Rsp2_AMPORT_2
    	    public Struct.Enum32<Rsp2_AntennaSelectT> antennaSel;  	// default: sdrplay_api_Rsp2_ANTENNA_A
    	    public Unsigned8 rfNotchEnable;                 		// default: 0
    	    public Padding pad;									// Machine Alignment
    	    
    	    public Rsp2TunerParamsT (final jnr.ffi.Runtime runtime) {
    	    	super(runtime);
    	    	
    	    	biasTEnable = new Unsigned8();
    	    	amPortSel = new Struct.Enum32<>(Rsp2_AmPortSelectT.class);
    	    	antennaSel = new Struct.Enum32<>(Rsp2_AntennaSelectT.class);
    	    	rfNotchEnable = new Unsigned8();
    	    	pad = new Padding(NativeType.UCHAR, 3);
    	    	
    	    }
    }
    
    public static enum Rsp2_AmPortSelectT {
    	 Rsp2_AMPORT_1 (1),
    	 Rsp2_AMPORT_2 (0);
    	
    	 private final int val;
    	 
    	 Rsp2_AmPortSelectT(int val) {
    		 this.val = val;
    	 }
    	 
    	 public int intValue() {
    		 return val;
    	 }
    }
    
    public static enum Rsp2_AntennaSelectT {
    	Rsp2_ANTENNA_A (5),
    	Rsp2_ANTENNA_B (6);
   	
   	 private final int val;
   	 
   	 Rsp2_AntennaSelectT(int val) {
   		 this.val = val;
   	 }
   	 
   	 public int intValue() {
   		 return val;
   	 }
   }
   
    public static class RspDuoTunerParamsT extends Struct {
    	  public Unsigned8 biasTEnable;                    // default: 0
    	  public Struct.Enum32<RspDuo_AmPortSelectT> tuner1AmPortSel; 	// default: sdrplay_api_RspDuo_AMPORT_2
    	  public Unsigned8 tuner1AmNotchEnable;            // default: 0
    	  public Unsigned8 rfNotchEnable;                  // default: 0
    	  public Unsigned8 rfDabNotchEnable;               // default: 0
    	  public Padding pad;								// Machine alignment
    	  
    	  public RspDuoTunerParamsT(final jnr.ffi.Runtime runtime) {
    		  super(runtime);
    		  
    		  biasTEnable = new Unsigned8();
    		  tuner1AmPortSel = new Struct.Enum32<>(RspDuo_AmPortSelectT.class);
    		  tuner1AmNotchEnable = new Unsigned8();
    		  rfNotchEnable = new Unsigned8();
    		  rfDabNotchEnable = new Unsigned8();
    		  pad = new Padding(NativeType.UCHAR, 1);
    	  }
    }
    
    public static enum RspDuo_AmPortSelectT {
    	RspDuo_AMPORT_1 (1),
    	RspDuo_AMPORT_2 (0);
    	
    	final int val;
    	
    	RspDuo_AmPortSelectT(int val) {
    		this.val = val;
    	}
    	
    	public int intValue() {
    		return val;
    	}
    }
    
    public static class RspDxTunerParamsT extends Struct {
    	public Struct.Enum32<RspDx_HdrModeBwT> hdrBw;
    	
    	public RspDxTunerParamsT(final jnr.ffi.Runtime runtime) {
    		super(runtime);
    		
    		hdrBw = new Struct.Enum32<>(RspDx_HdrModeBwT.class);
    	}
    }
    
    public static enum RspDx_HdrModeBwT {
    	RspDx_HDRMODE_BW_0_200  (0),
    	RspDx_HDRMODE_BW_0_500  (1),
    	RspDx_HDRMODE_BW_1_200  (2),
    	RspDx_HDRMODE_BW_1_700  (3);
    	
    	final int val;
    	
    	RspDx_HdrModeBwT(int val) {
    		this.val = val;
    	}
    	
    	public int intValue() {
    		return val;
    	}
    	
    }
    
}
