package io.github.sammy1am.sdrplay.jnr;

import jnr.ffi.Struct;
import jnr.ffi.util.EnumMapper;

/**
 * JNR class for ControlParamsT and sub-structures (sdrplay_api_control.h).
 * @author Sammy1Am
 */
public class ControlParamsT extends Struct {
    public DcOffsetT dcOffset;
    public DecimationT decimation;
    public AgcT agc;
    public Enum32<AdsbModeT> adsbMode;  //default: sdrplay_api_ADSB_DECIMATION
    
    public ControlParamsT(final jnr.ffi.Runtime runtime) {
        super(runtime);
        dcOffset = inner(new DcOffsetT(runtime));
        decimation = inner(new DecimationT(runtime));
        agc = inner(new AgcT(runtime));
        adsbMode = new Enum32<>(AdsbModeT.class);
    }
    
  

    public static enum AdsbModeT {
        ADSB_DECIMATION                 (0),
        ADSB_NO_DECIMATION_LOWPASS      (1),
        ADSB_NO_DECIMATION_BANDPASS_2MHZ(2),
        ADSB_NO_DECIMATION_BANDPASS_3MHZ(3);

        private final int val;

        AdsbModeT(int val) {
            this.val = val;
        }
        
        public static final AdsbModeT valueOf(int pattern) {
            return (AdsbModeT)EnumMapper.getInstance(AgcControlT.class).valueOf(pattern);
        }
        
        public int intValue() {
            return val;
        }
    }
    
    public static class DcOffsetT extends Struct {
        public Unsigned8 DCenable;    // default: 1
        public Unsigned8 IQenable;    // default: 1
        
        public DcOffsetT(final jnr.ffi.Runtime runtime) {
            super(runtime);
            DCenable = new Unsigned8();
            IQenable = new Unsigned8();
        }
    }
    
    public static class DecimationT extends Struct {
        public Unsigned8 enable;              // default: 0
        public Unsigned8 decimationFactor;    // default: 1
        public Unsigned8 wideBandSignal;      // default: 0
        
        public DecimationT(final jnr.ffi.Runtime runtime) {
            super(runtime);
            enable = new Unsigned8();
            decimationFactor = new Unsigned8();
            wideBandSignal = new Unsigned8();
        }
    }
    
    public static enum AgcControlT {
        AGC_DISABLE (0),
        AGC_100HZ   (1),
        AGC_50HZ    (2),
        AGC_5HZ     (3),
        AGC_CTRL_EN (4);

        private final int val;

        AgcControlT(int val) {
            this.val = val;
        }
        
        public static final AgcControlT valueOf(int pattern) {
            return (AgcControlT)EnumMapper.getInstance(AgcControlT.class).valueOf(pattern);
        }
        
        public int intValue() {
            return val;
        }
    }
    
    public static class AgcT extends Struct {
        public Enum32<AgcControlT> enable;               // default: AGC_EN
        public Signed32 setPoint_dBfs;                   // default: ??? depends on sample rate
        public Unsigned16 attack_ms;                     // default: 0
        public Unsigned16 decay_ms;                      // default: 0
        public Unsigned16 decay_delay_ms;                // default: 0
        public Unsigned16 decay_threshold_dB;            // default: 0
        public Signed32 syncUpdate;                      // default: 0
        
        public AgcT(final jnr.ffi.Runtime runtime) {
            super(runtime);
            enable = new Enum32<>(AgcControlT.class);
            setPoint_dBfs = new Signed32();
            attack_ms = new Unsigned16();
            decay_ms = new Unsigned16();
            decay_delay_ms = new Unsigned16();
            decay_threshold_dB = new Unsigned16();
            syncUpdate = new Signed32();
        }
    }
}
