/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.sammy1am.sdrplay.jnr;

import jnr.ffi.Struct;
import jnr.ffi.NativeType;
import jnr.ffi.Runtime;
import jnr.ffi.util.EnumMapper;

/**
 * JNR class for TunerParamsT and sub-structures (sdrplay_api_tuner.h).
 * @author Sammy1Am
 */
public class TunerParamsT extends Struct{
    public Enum32<Bw_MHzT> bwType;          // default: BW_0_200
    public Enum32<If_kHzT> ifType;          // default: IF_Zero
    public Enum32<LoModeT> loMode;          // default: LO_Auto
    public GainT gain;
    public RfFreqT rfFreq;
    public DcOffsetTunerT dcOffsetTuner;
    public Padding pad;						// Machine Alignment
        
    public TunerParamsT(final Runtime runtime) {
        super(runtime);
        bwType = new Enum32<>(Bw_MHzT.class);
        ifType = new Enum32<>(If_kHzT.class);
        loMode = new Enum32<>(LoModeT.class);
        gain = inner(new GainT(runtime));
        rfFreq = inner(new RfFreqT(runtime));
        dcOffsetTuner = inner(new DcOffsetTunerT(runtime));
        pad = new Padding(NativeType.UCHAR, 4);
    }
    
    public static enum Bw_MHzT {
        BW_Undefined(0),
        BW_0_200    (200),
        BW_0_300    (300),
        BW_0_600    (600),
        BW_1_536    (1536),
        BW_5_000    (5000),
        BW_6_000    (6000),
        BW_7_000    (7000),
        BW_8_000    (8000);

        private final int val;

        Bw_MHzT(int val) {
            this.val = val;
        }
        
        public static final Bw_MHzT valueOf(int pattern) {
            return (Bw_MHzT)EnumMapper.getInstance(Bw_MHzT.class).valueOf(pattern);
        }
        
        public int intValue() {
            return val;
        }
    }
    
    public static enum If_kHzT {
        IF_Undefined (-1),
        IF_Zero      (0),
        IF_0_450     (450),
        IF_1_620     (1620),
        IF_2_048     (2048);

        private final int val;

        If_kHzT(int val) {
            this.val = val;
        }
        
        public static final If_kHzT valueOf(int pattern) {
            return (If_kHzT)EnumMapper.getInstance(If_kHzT.class).valueOf(pattern);
        }
        
        public int intValue() {
            return val;
        }
    }
    
    public static enum LoModeT {
        LO_Undefined(0),
        LO_Auto     (1),
        LO_120MHz   (2),
        LO_144MHz   (3),
        LO_168MHz   (4);

        private final int val;

        LoModeT(int val) {
            this.val = val;
        }
        
        public static final LoModeT valueOf(int pattern) {
            return (LoModeT)EnumMapper.getInstance(LoModeT.class).valueOf(pattern);
        }
        
        public int intValue() {
            return val;
        }
    }
    
    public static enum MinGainReductionT {
        EXTENDED_MIN_GR(0),
        NORMAL_MIN_GR  (20);

        private final int val;

        MinGainReductionT(int val) {
            this.val = val;
        }
        
        public static final MinGainReductionT valueOf(int pattern) {
            return (MinGainReductionT)EnumMapper.getInstance(MinGainReductionT.class).valueOf(pattern);
        }
        
        public int intValue() {
            return val;
        }
    }
    
    public static enum TunerSelectT {
        Tuner_Neither(0),
        Tuner_A(1),
        Tuner_B(2),
        Tuner_Both(3);

        private final int val;

        TunerSelectT(int val) {
            this.val = val;
        }
        
        public static final TunerSelectT valueOf(int pattern) {
            return (TunerSelectT)EnumMapper.getInstance(TunerSelectT.class).valueOf(pattern);
        }
        
        public int intValue() {
            return val;
        }
    }
    
    public static class GainValuesT extends Struct {
        public Float curr;
        public Float max;
        public Float min;
        
        public GainValuesT(final Runtime runtime) {
            super(runtime);
            curr = new Float();
            max = new Float();
            min = new Float();
        }
    }
    
    public static class GainT extends Struct {
        public int32_t gRdB;                    // default: 20-59
        public Unsigned8 LNAstate;               // default: 0
        public Unsigned8 syncUpdate;             // default: 0
        public Enum32<MinGainReductionT> minGr;  // default: NORMAL_MIN_GR
        public GainValuesT gainVals;             // output parameter
        
        public GainT(final Runtime runtime) {
            super(runtime);
            gRdB = new int32_t();
            LNAstate = new Unsigned8();
            syncUpdate = new Unsigned8();
            minGr = new Enum32<>(MinGainReductionT.class);
            gainVals = inner(new GainValuesT(runtime));
        }
    }
    
    public static class RfFreqT extends Struct {
        public Double rfHz;             	// default: 200000000.0
        public Unsigned8 syncUpdate; 		// default: 0
        public Padding pad;                	// Alignment padding.
        
        public RfFreqT(final Runtime runtime) {
            super(runtime);
            rfHz = new Double();
            syncUpdate = new Unsigned8();
            pad = new Padding(NativeType.UCHAR, 7);
        }
    }
    
    public static class DcOffsetTunerT extends Struct {
        public Unsigned8 dcCal;         // default: 3 (Periodic mode)
        public Unsigned8 speedUp;       // default: 0 (No speedup)
        public Signed32 trackTime;       // default: 1    (=> time in uSec = (72 * 3 * trackTime) / 24e6       = 9uSec)
        public Signed32 refreshRateTime; // default: 2048 (=> time in uSec = (72 * 3 * refreshRateTime) / 24e6 = 18432uSec)
        
        public DcOffsetTunerT(final Runtime runtime) {
            super(runtime);
            dcCal = new Unsigned8();
            speedUp = new Unsigned8();
            trackTime = new Signed32();
            refreshRateTime = new Signed32();
        }
    }
}
