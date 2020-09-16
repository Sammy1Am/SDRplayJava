/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.sammy1am.sdrplay.api;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Structure;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Sam
 */
public interface SDRPlayAPITuner extends Library {

    public SDRPlayAPI INSTANCE = (SDRPlayAPI) Native.load("sdrplay_api.dll", SDRPlayAPI.class, Map.of(Library.OPTION_TYPE_MAPPER, new SDRPlayTypeMapper()));

    public static enum sdrplay_api_Bw_MHzT {
        sdrplay_api_BW_Undefined(0),
        sdrplay_api_BW_0_200(200),
        sdrplay_api_BW_0_300(300),
        sdrplay_api_BW_0_600(600),
        sdrplay_api_BW_1_536(1536),
        sdrplay_api_BW_5_000(5000),
        sdrplay_api_BW_6_000(6000),
        sdrplay_api_BW_7_000(7000),
        sdrplay_api_BW_8_000(8000);

        private int value;
        private static Map map = new HashMap<>();

        private sdrplay_api_Bw_MHzT(int value) {
            this.value = value;
        }

        static {
            for (sdrplay_api_Bw_MHzT valT : sdrplay_api_Bw_MHzT.values()) {
                map.put(valT.value, valT);
            }
        }

        public static sdrplay_api_Bw_MHzT valueOf(int valT) {
            return (sdrplay_api_Bw_MHzT) map.get(valT);
        }

        public int getValue() {
            return value;
        }
    }
    
    public static enum sdrplay_api_If_kHzT {
        sdrplay_api_IF_Undefined(-1),
        sdrplay_api_IF_Zero(0),
        sdrplay_api_IF_0_450(450),
        sdrplay_api_IF_1_620(1620),
        sdrplay_api_IF_2_048(2048);

        private int value;
        private static Map map = new HashMap<>();

        private sdrplay_api_If_kHzT(int value) {
            this.value = value;
        }

        static {
            for (sdrplay_api_If_kHzT valT : sdrplay_api_If_kHzT.values()) {
                map.put(valT.value, valT);
            }
        }

        public static sdrplay_api_If_kHzT valueOf(int valT) {
            return (sdrplay_api_If_kHzT) map.get(valT);
        }

        public int getValue() {
            return value;
        }
    }
    
    public static enum sdrplay_api_LoModeT {
        sdrplay_api_LO_Undefined(0),
        sdrplay_api_LO_Auto(1),
        sdrplay_api_LO_120MHz(2),
        sdrplay_api_LO_144MHz(3),
        sdrplay_api_LO_168MHz(4);

        private int value;
        private static Map map = new HashMap<>();

        private sdrplay_api_LoModeT(int value) {
            this.value = value;
        }

        static {
            for (sdrplay_api_LoModeT valT : sdrplay_api_LoModeT.values()) {
                map.put(valT.value, valT);
            }
        }

        public static sdrplay_api_LoModeT valueOf(int valT) {
            return (sdrplay_api_LoModeT) map.get(valT);
        }

        public int getValue() {
            return value;
        }
    }
    
    public static enum sdrplay_api_MinGainReductionT {
        sdrplay_api_EXTENDED_MIN_GR(0),
        sdrplay_api_NORMAL_MIN_GR(20);

        private int value;
        private static Map map = new HashMap<>();

        private sdrplay_api_MinGainReductionT(int value) {
            this.value = value;
        }

        static {
            for (sdrplay_api_MinGainReductionT valT : sdrplay_api_MinGainReductionT.values()) {
                map.put(valT.value, valT);
            }
        }

        public static sdrplay_api_MinGainReductionT valueOf(int valT) {
            return (sdrplay_api_MinGainReductionT) map.get(valT);
        }

        public int getValue() {
            return value;
        }
    }
    
    public static enum sdrplay_api_TunerSelectT {
        sdrplay_api_Tuner_Neither(0),
        sdrplay_api_Tuner_A(1),
        sdrplay_api_Tuner_B(2),
        sdrplay_api_Tuner_Both(3);

        private int value;
        private static Map map = new HashMap<>();

        private sdrplay_api_TunerSelectT(int value) {
            this.value = value;
        }

        static {
            for (sdrplay_api_TunerSelectT valT : sdrplay_api_TunerSelectT.values()) {
                map.put(valT.value, valT);
            }
        }

        public static sdrplay_api_TunerSelectT valueOf(int valT) {
            return (sdrplay_api_TunerSelectT) map.get(valT);
        }

        public int getValue() {
            return value;
        }
    }
    
    public static class sdrplay_api_GainValuesT extends Structure {
		public float curr;
		public float max;
		public float min;
		public sdrplay_api_GainValuesT() {
			super();
		}
		protected List<String> getFieldOrder() {
			return Arrays.asList("curr", "max", "min");
		}
    }
    
    public static class sdrplay_api_GainT extends Structure {
        public int gRdB;                            // default: 50
        public byte LNAstate;              // default: 0
        public byte syncUpdate;            // default: 0
        public sdrplay_api_MinGainReductionT minGr; // default: sdrplay_api_NORMAL_MIN_GR
        public sdrplay_api_GainValuesT gainVals;    // output parameter

        public sdrplay_api_GainT() {
            super();
        }

        protected List<String> getFieldOrder() {
            return Arrays.asList("gRdB", "LNAstate", "syncUpdate", "minGr", "gainVals");
        }
    }
    
    public static class sdrplay_api_RfFreqT extends Structure {
        public double rfHz;                         // default: 200000000.0
        public byte syncUpdate;            // default: 0

        public sdrplay_api_RfFreqT() {
            super();
        }

        protected List<String> getFieldOrder() {
            return Arrays.asList("rfHz", "syncUpdate");
        }
    }
    
    public static class sdrplay_api_DcOffsetTunerT extends Structure {

        /**
         * default: 3 (Periodic mode)
         */
        public byte dcCal;
        /**
         * default: 0 (No speedup)
         */
        public byte speedUp;
        /**
         * default: 1 (=> time in uSec = (72 * 3 * trackTime) / 24e6 = 9uSec)
         */
        public int trackTime;
        /**
         * default: 2048 (=> time in uSec = (72 * 3 * refreshRateTime) / 24e6 =
         * 18432uSec)
         */
        public int refreshRateTime;

        public sdrplay_api_DcOffsetTunerT() {
            super();
        }

        protected List<String> getFieldOrder() {
            return Arrays.asList("dcCal", "speedUp", "trackTime", "refreshRateTime");
        }
    }
    
    public static class sdrplay_api_TunerParamsT extends Structure {
		public sdrplay_api_Bw_MHzT bwType;          // default: sdrplay_api_BW_0_200
                public sdrplay_api_If_kHzT ifType;          // default: sdrplay_api_IF_Zero
                public sdrplay_api_LoModeT loMode;          // default: sdrplay_api_LO_Auto
                public sdrplay_api_GainT gain;
                public sdrplay_api_RfFreqT rfFreq;
                public sdrplay_api_DcOffsetTunerT dcOffsetTuner;
		public sdrplay_api_TunerParamsT() {
			super();
		}
		protected List<String> getFieldOrder() {
			return Arrays.asList("bwType", "ifType", "loMode", "gain", "rfFreq", "dcOffsetTuner");
		}
    }
}
