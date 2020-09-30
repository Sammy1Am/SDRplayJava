/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.sammy1am.sdrplay.jna;

import com.sun.jna.Callback;
import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.PointerType;
import com.sun.jna.Structure;
import com.sun.jna.ptr.DoubleByReference;
import com.sun.jna.ptr.FloatByReference;
import com.sun.jna.ptr.IntByReference;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Sam
 */
public interface SDRPlayAPI extends Library {

    public SDRPlayAPI INSTANCE = (SDRPlayAPI) Native.load("sdrplay_api.dll", SDRPlayAPI.class, Map.of(Library.OPTION_TYPE_MAPPER, new SDRPlayTypeMapper()));

    public static final float SDRPLAY_API_VERSION = (float) (3.07);

    public static final int SDRPLAY_MAX_DEVICES = 16;
    public static final int SDRPLAY_MAX_TUNERS_PER_DEVICE = 2;

    public static final int SDRPLAY_MAX_SER_NO_LEN = 64;
    public static final int SDRPLAY_MAX_ROOT_NM_LEN = 32;

    public static final int SDRPLAY_RSP1_ID = 1;
    public static final int SDRPLAY_RSP1A_ID = 255;
    public static final int SDRPLAY_RSP2_ID = 2;
    public static final int SDRPLAY_RSPduo_ID = 3;
    public static final int SDRPLAY_RSPdx_ID = 4;

    //<editor-fold desc="Enums">
    
    public static enum sdrplay_api_ErrT {
        sdrplay_api_Success(0),
        sdrplay_api_Fail(1),
        sdrplay_api_InvalidParam(2),
        sdrplay_api_OutOfRange(3),
        sdrplay_api_GainUpdateError(4),
        sdrplay_api_RfUpdateError(5),
        sdrplay_api_FsUpdateError(6),
        sdrplay_api_HwError(7),
        sdrplay_api_AliasingError(8),
        sdrplay_api_AlreadyInitialised(9),
        sdrplay_api_NotInitialised(10),
        sdrplay_api_NotEnabled(11),
        sdrplay_api_HwVerError(12),
        sdrplay_api_OutOfMemError(13),
        sdrplay_api_ServiceNotResponding(14),
        sdrplay_api_StartPending(15),
        sdrplay_api_StopPending(16),
        sdrplay_api_InvalidMode(17),
        sdrplay_api_FailedVerification1(18),
        sdrplay_api_FailedVerification2(19),
        sdrplay_api_FailedVerification3(20),
        sdrplay_api_FailedVerification4(21),
        sdrplay_api_FailedVerification5(22),
        sdrplay_api_FailedVerification6(23),
        sdrplay_api_InvalidServiceVersion(24);

        private int value;
        private static Map map = new HashMap<>();

        private sdrplay_api_ErrT(int value) {
            this.value = value;
        }

        static {
            for (sdrplay_api_ErrT errT : sdrplay_api_ErrT.values()) {
                map.put(errT.value, errT);
            }
        }

        public static sdrplay_api_ErrT valueOf(int errT) {
            return (sdrplay_api_ErrT) map.get(errT);
        }

        public int getValue() {
            return value;
        }
    }

    public static enum sdrplay_api_RspDuoModeT {
        sdrplay_api_RspDuoMode_Unknown(0),
        sdrplay_api_RspDuoMode_Single_Tuner(1),
        sdrplay_api_RspDuoMode_Dual_Tuner(2),
        sdrplay_api_RspDuoMode_Master(4),
        sdrplay_api_RspDuoMode_Slave(8);

        private int value;
        private static Map map = new HashMap<>();

        private sdrplay_api_RspDuoModeT(int value) {
            this.value = value;
        }

        static {
            for (sdrplay_api_RspDuoModeT errT : sdrplay_api_RspDuoModeT.values()) {
                map.put(errT.value, errT);
            }
        }

        public static sdrplay_api_RspDuoModeT valueOf(int errT) {
            return (sdrplay_api_RspDuoModeT) map.get(errT);
        }

        public int getValue() {
            return value;
        }
    }
    
    public static enum sdrplay_api_EventT {
        sdrplay_api_GainChange(0),
        sdrplay_api_PowerOverloadChange(1),
        sdrplay_api_DeviceRemoved(2),
        sdrplay_api_RspDuoModeChange(3);

        private int value;
        private static Map map = new HashMap<>();

        private sdrplay_api_EventT(int value) {
            this.value = value;
        }

        static {
            for (sdrplay_api_EventT errT : sdrplay_api_EventT.values()) {
                map.put(errT.value, errT);
            }
        }

        public static sdrplay_api_EventT valueOf(int errT) {
            return (sdrplay_api_EventT) map.get(errT);
        }

        public int getValue() {
            return value;
        }
    }
    
    public static enum sdrplay_api_PowerOverloadCbEventIdT {
        sdrplay_api_Overload_Detected(0),
        sdrplay_api_Overload_Corrected(1);

        private int value;
        private static Map map = new HashMap<>();

        private sdrplay_api_PowerOverloadCbEventIdT(int value) {
            this.value = value;
        }

        static {
            for (sdrplay_api_PowerOverloadCbEventIdT errT : sdrplay_api_PowerOverloadCbEventIdT.values()) {
                map.put(errT.value, errT);
            }
        }

        public static sdrplay_api_PowerOverloadCbEventIdT valueOf(int errT) {
            return (sdrplay_api_PowerOverloadCbEventIdT) map.get(errT);
        }

        public int getValue() {
            return value;
        }
    }

    public static enum sdrplay_api_RspDuoModeCbEventIdT {
        sdrplay_api_MasterInitialised(0),
        sdrplay_api_SlaveAttached(1),
        sdrplay_api_SlaveDetached(2),
        sdrplay_api_SlaveInitialised(3),
        sdrplay_api_SlaveUninitialised(4),
        sdrplay_api_MasterDllDisappeared(5),
        sdrplay_api_SlaveDllDisappeared(6);

        private int value;
        private static Map map = new HashMap<>();

        private sdrplay_api_RspDuoModeCbEventIdT(int value) {
            this.value = value;
        }

        static {
            for (sdrplay_api_RspDuoModeCbEventIdT errT : sdrplay_api_RspDuoModeCbEventIdT.values()) {
                map.put(errT.value, errT);
            }
        }

        public static sdrplay_api_RspDuoModeCbEventIdT valueOf(int errT) {
            return (sdrplay_api_RspDuoModeCbEventIdT) map.get(errT);
        }

        public int getValue() {
            return value;
        }
    }
    
    public static enum sdrplay_api_ReasonForUpdateT {
        sdrplay_api_Update_None(0x00000000),
        // Reasons for master only mode 
        sdrplay_api_Update_Dev_Fs(0x00000001),
        sdrplay_api_Update_Dev_Ppm(0x00000002),
        sdrplay_api_Update_Dev_SyncUpdate(0x00000004),
        sdrplay_api_Update_Dev_ResetFlags(0x00000008),
        sdrplay_api_Update_Rsp1a_BiasTControl(0x00000010),
        sdrplay_api_Update_Rsp1a_RfNotchControl(0x00000020),
        sdrplay_api_Update_Rsp1a_RfDabNotchControl(0x00000040),
        sdrplay_api_Update_Rsp2_BiasTControl(0x00000080),
        sdrplay_api_Update_Rsp2_AmPortSelect(0x00000100),
        sdrplay_api_Update_Rsp2_AntennaControl(0x00000200),
        sdrplay_api_Update_Rsp2_RfNotchControl(0x00000400),
        sdrplay_api_Update_Rsp2_ExtRefControl(0x00000800),
        sdrplay_api_Update_RspDuo_ExtRefControl(0x00001000),
        sdrplay_api_Update_Master_Spare_1(0x00002000),
        sdrplay_api_Update_Master_Spare_2(0x00004000),
        // Reasons for master and slave mode
        // Note: sdrplay_api_Update_Tuner_Gr MUST be the first value defined in this section!
        sdrplay_api_Update_Tuner_Gr(0x00008000),
        sdrplay_api_Update_Tuner_GrLimits(0x00010000),
        sdrplay_api_Update_Tuner_Frf(0x00020000),
        sdrplay_api_Update_Tuner_BwType(0x00040000),
        sdrplay_api_Update_Tuner_IfType(0x00080000),
        sdrplay_api_Update_Tuner_DcOffset(0x00100000),
        sdrplay_api_Update_Tuner_LoMode(0x00200000),
        sdrplay_api_Update_Ctrl_DCoffsetIQimbalance(0x00400000),
        sdrplay_api_Update_Ctrl_Decimation(0x00800000),
        sdrplay_api_Update_Ctrl_Agc(0x01000000),
        sdrplay_api_Update_Ctrl_AdsbMode(0x02000000),
        sdrplay_api_Update_Ctrl_OverloadMsgAck(0x04000000),
        sdrplay_api_Update_RspDuo_BiasTControl(0x08000000),
        sdrplay_api_Update_RspDuo_AmPortSelect(0x10000000),
        sdrplay_api_Update_RspDuo_Tuner1AmNotchControl(0x20000000),
        sdrplay_api_Update_RspDuo_RfNotchControl(0x40000000),
        sdrplay_api_Update_RspDuo_RfDabNotchControl(0x80000000);

        private int value;
        private static Map map = new HashMap<>();

        private sdrplay_api_ReasonForUpdateT(int value) {
            this.value = value;
        }

        static {
            for (sdrplay_api_ReasonForUpdateT valT : sdrplay_api_ReasonForUpdateT.values()) {
                map.put(valT.value, valT);
            }
        }

        public static sdrplay_api_ReasonForUpdateT valueOf(int valT) {
            return (sdrplay_api_ReasonForUpdateT) map.get(valT);
        }

        public int getValue() {
            return value;
        }
    }
    
    public static enum sdrplay_api_ReasonForUpdateExtension1T {
        sdrplay_api_Update_Ext1_None(0x00000000),
        // Reasons for master only mode 
        sdrplay_api_Update_RspDx_HdrEnable(0x00000001),
        sdrplay_api_Update_RspDx_BiasTControl(0x00000002),
        sdrplay_api_Update_RspDx_AntennaControl(0x00000004),
        sdrplay_api_Update_RspDx_RfNotchControl(0x00000008),
        sdrplay_api_Update_RspDx_RfDabNotchControl(0x00000010),
        sdrplay_api_Update_RspDx_HdrBw(0x00000020);

        private int value;
        private static Map map = new HashMap<>();

        private sdrplay_api_ReasonForUpdateExtension1T(int value) {
            this.value = value;
        }

        static {
            for (sdrplay_api_ReasonForUpdateExtension1T valT : sdrplay_api_ReasonForUpdateExtension1T.values()) {
                map.put(valT.value, valT);
            }
        }

        public static sdrplay_api_ReasonForUpdateExtension1T valueOf(int valT) {
            return (sdrplay_api_ReasonForUpdateExtension1T) map.get(valT);
        }

        public int getValue() {
            return value;
        }
    }
    
    public static enum sdrplay_api_DbgLvl_t {
        sdrplay_api_DbgLvl_Disable(0),
        sdrplay_api_DbgLvl_Verbose(1),
        sdrplay_api_DbgLvl_Warning(2),
        sdrplay_api_DbgLvl_Error(3),
        sdrplay_api_DbgLvl_Message(4);

        private int value;
        private static Map map = new HashMap<>();

        private sdrplay_api_DbgLvl_t(int value) {
            this.value = value;
        }

        static {
            for (sdrplay_api_DbgLvl_t valT : sdrplay_api_DbgLvl_t.values()) {
                map.put(valT.value, valT);
            }
        }

        public static sdrplay_api_DbgLvl_t valueOf(int valT) {
            return (sdrplay_api_DbgLvl_t) map.get(valT);
        }

        public int getValue() {
            return value;
        }
    }
    
    //</editor-fold>
    
    //<editor-fold desc="_dev">
    
    public static enum sdrplay_api_TransferModeT {
        sdrplay_api_ISOCH(0),
        sdrplay_api_BULK(1);

        private int value;
        private static Map map = new HashMap<>();

        private sdrplay_api_TransferModeT(int value) {
            this.value = value;
        }

        static {
            for (sdrplay_api_TransferModeT errT : sdrplay_api_TransferModeT.values()) {
                map.put(errT.value, errT);
            }
        }

        public static sdrplay_api_TransferModeT valueOf(int errT) {
            return (sdrplay_api_TransferModeT) map.get(errT);
        }

        public int getValue() {
            return value;
        }
    }
        
    public static enum sdrplay_api_RspDx_AntennaSelectT {
        sdrplay_api_RspDx_ANTENNA_A(0),
        sdrplay_api_RspDx_ANTENNA_B(1),
        sdrplay_api_RspDx_ANTENNA_C(2);

        private int value;
        private static Map map = new HashMap<>();

        private sdrplay_api_RspDx_AntennaSelectT(int value) {
            this.value = value;
        }

        static {
            for (sdrplay_api_RspDx_AntennaSelectT errT : sdrplay_api_RspDx_AntennaSelectT.values()) {
                map.put(errT.value, errT);
            }
        }

        public static sdrplay_api_RspDx_AntennaSelectT valueOf(int errT) {
            return (sdrplay_api_RspDx_AntennaSelectT) map.get(errT);
        }

        public int getValue() {
            return value;
        }
    }
    
    public static enum sdrplay_api_Rsp2_AntennaSelectT {
        sdrplay_api_Rsp2_ANTENNA_A(5),
        sdrplay_api_Rsp2_ANTENNA_B(6);

        private int value;
        private static Map map = new HashMap<>();

        private sdrplay_api_Rsp2_AntennaSelectT(int value) {
            this.value = value;
        }

        static {
            for (sdrplay_api_Rsp2_AntennaSelectT errT : sdrplay_api_Rsp2_AntennaSelectT.values()) {
                map.put(errT.value, errT);
            }
        }

        public static sdrplay_api_Rsp2_AntennaSelectT valueOf(int errT) {
            return (sdrplay_api_Rsp2_AntennaSelectT) map.get(errT);
        }

        public int getValue() {
            return value;
        }
    }
    
    public static enum sdrplay_api_Rsp2_AmPortSelectT {
        sdrplay_api_Rsp2_AMPORT_1(1),
        sdrplay_api_Rsp2_AMPORT_2(0);

        private int value;
        private static Map map = new HashMap<>();

        private sdrplay_api_Rsp2_AmPortSelectT(int value) {
            this.value = value;
        }

        static {
            for (sdrplay_api_Rsp2_AmPortSelectT valT : sdrplay_api_Rsp2_AmPortSelectT.values()) {
                map.put(valT.value, valT);
            }
        }

        public static sdrplay_api_Rsp2_AmPortSelectT valueOf(int valT) {
            return (sdrplay_api_Rsp2_AmPortSelectT) map.get(valT);
        }

        public int getValue() {
            return value;
        }
    }
    
    public static enum sdrplay_api_RspDuo_AmPortSelectT {
        sdrplay_api_RspDuo_AMPORT_1(1),
        sdrplay_api_RspDuo_AMPORT_2(0);

        private int value;
        private static Map map = new HashMap<>();

        private sdrplay_api_RspDuo_AmPortSelectT(int value) {
            this.value = value;
        }

        static {
            for (sdrplay_api_RspDuo_AmPortSelectT valT : sdrplay_api_RspDuo_AmPortSelectT.values()) {
                map.put(valT.value, valT);
            }
        }

        public static sdrplay_api_RspDuo_AmPortSelectT valueOf(int valT) {
            return (sdrplay_api_RspDuo_AmPortSelectT) map.get(valT);
        }

        public int getValue() {
            return value;
        }
    }
    
    public static enum sdrplay_api_RspDx_HdrModeBwT {
        sdrplay_api_RspDx_HDRMODE_BW_0_200(0),
        sdrplay_api_RspDx_HDRMODE_BW_0_500(1),
        sdrplay_api_RspDx_HDRMODE_BW_1_200(2),
        sdrplay_api_RspDx_HDRMODE_BW_1_700(3);

        private int value;
        private static Map map = new HashMap<>();

        private sdrplay_api_RspDx_HdrModeBwT(int value) {
            this.value = value;
        }

        static {
            for (sdrplay_api_RspDx_HdrModeBwT valT : sdrplay_api_RspDx_HdrModeBwT.values()) {
                map.put(valT.value, valT);
            }
        }

        public static sdrplay_api_RspDx_HdrModeBwT valueOf(int valT) {
            return (sdrplay_api_RspDx_HdrModeBwT) map.get(valT);
        }

        public int getValue() {
            return value;
        }
    }
        
    public static class sdrplay_api_DevParamsT extends Structure {

        public double ppm;                         // default: 0.0
        public sdrplay_api_FsFreqT fsFreq;
        public sdrplay_api_SyncUpdateT syncUpdate;
        public sdrplay_api_ResetFlagsT resetFlags;
        public sdrplay_api_TransferModeT mode;     // default: sdrplay_api_ISOCH
        public int samplesPerPkt;         // default: 0 (output param)
        public sdrplay_api_Rsp1aParamsT rsp1aParams;
        public sdrplay_api_Rsp2ParamsT rsp2Params;
        public sdrplay_api_RspDuoParamsT rspDuoParams;
        public sdrplay_api_RspDxParamsT rspDxParams;

        public sdrplay_api_DevParamsT() {
                super();
        }
        
        @Override
        protected List<String> getFieldOrder() {
            return Arrays.asList("ppm", "fsFreq", "syncUpdate", "resetFlags", "mode", "samplesPerPkt", "rsp1aParams", "rsp2Params", "rspDuoParams", "rspDxParams");
        }
        
        public static class ByReference extends sdrplay_api_DevParamsT implements Structure.ByReference{
            public ByReference() {
                    super();
            }
        };
    }
    
    public static class sdrplay_api_FsFreqT extends Structure {
        /** default: 2000000.0 */
        public double fsHz;
        /** default: 0 */
        public byte syncUpdate;
        /** default: 0 */
        public byte reCal;
        public sdrplay_api_FsFreqT() {
                super();
        }
        @Override
        protected List<String> getFieldOrder() {
                return Arrays.asList("fsHz", "syncUpdate", "reCal");
        }
    }
    
    public static class sdrplay_api_SyncUpdateT extends Structure {
        /** default: 0 */
        public int sampleNum;
        /** default: 0 */
        public int period;
        public sdrplay_api_SyncUpdateT() {
                super();
        }
        @Override
        protected List<String> getFieldOrder() {
                return Arrays.asList("sampleNum", "period");
        }
    }
    
    public static class sdrplay_api_ResetFlagsT extends Structure {
        /** default: 0 */
        public byte resetGainUpdate;
        /** default: 0 */
        public byte resetRfUpdate;
        /** default: 0 */
        public byte resetFsUpdate;
        public sdrplay_api_ResetFlagsT() {
                super();
        }
        @Override
        protected List<String> getFieldOrder() {
                return Arrays.asList("resetGainUpdate", "resetRfUpdate", "resetFsUpdate");
        }
    }
    
    public static class sdrplay_api_Rsp1aParamsT extends Structure {
        /** default: 0 */
        public byte rfNotchEnable;
        /** default: 0 */
        public byte rfDabNotchEnable;
        public sdrplay_api_Rsp1aParamsT() {
                super();
        }
        protected List<String> getFieldOrder() {
                return Arrays.asList("rfNotchEnable", "rfDabNotchEnable");
        }
    }
    
    public static class sdrplay_api_Rsp1aTunerParamsT extends Structure {
        /** default: 0 */
        public byte biasTEnable;
        public sdrplay_api_Rsp1aTunerParamsT() {
                super();
        }
        protected List<String> getFieldOrder() {
                return Arrays.asList("biasTEnable");
        }
    }
    
    public static class sdrplay_api_Rsp2ParamsT extends Structure {
        /** default: 0 */
        public byte extRefOutputEn;
        public sdrplay_api_Rsp2ParamsT() {
                super();
        }
        protected List<String> getFieldOrder() {
                return Arrays.asList("extRefOutputEn");
        }
    }
    
    public static class sdrplay_api_Rsp2TunerParamsT extends Structure {
		/** default: 0 */
		public byte biasTEnable;
		/** default: sdrplay_api_Rsp2_AMPORT_2 */
		public sdrplay_api_Rsp2_AmPortSelectT amPortSel;
		/** default: sdrplay_api_Rsp2_ANTENNA_A */
		public sdrplay_api_Rsp2_AntennaSelectT antennaSel;
		/** default: 0 */
		public byte rfNotchEnable;
		public sdrplay_api_Rsp2TunerParamsT() {
			super();
		}
		protected List<String> getFieldOrder() {
			return Arrays.asList("biasTEnable", "amPortSel", "antennaSel", "rfNotchEnable");
		}
    }
    
    public static class sdrplay_api_RspDuoParamsT extends Structure {
        /** default: 0 */
        public byte extRefOutputEn;
        public sdrplay_api_RspDuoParamsT() {
                super();
        }
        protected List<String> getFieldOrder() {
                return Arrays.asList("extRefOutputEn");
        }
    }

    public static class sdrplay_api_RspDuoTunerParamsT extends Structure {
		/** default: 0 */
		public byte biasTEnable;
		/** default: sdrplay_api_RspDuo_AMPORT_2 */
		public sdrplay_api_RspDuo_AmPortSelectT tuner1AmPortSel;
		/** default: 0 */
		public byte tuner1AmNotchEnable;
		/** default: 0 */
		public byte rfNotchEnable;
		/** default: 0 */
		public byte rfDabNotchEnable;
		public sdrplay_api_RspDuoTunerParamsT() {
			super();
		}
		protected List<String> getFieldOrder() {
			return Arrays.asList("biasTEnable", "tuner1AmPortSel", "tuner1AmNotchEnable", "rfNotchEnable", "rfDabNotchEnable");
		}
    }
    
    public static class sdrplay_api_RspDxParamsT extends Structure {
        /** default: 0 */
        public byte hdrEnable;
        /** default: 0 */
        public byte biasTEnable;
        /** default: sdrplay_api_RspDx_ANTENNA_A */
        public sdrplay_api_RspDx_AntennaSelectT antennaSel;
        /** default: 0 */
        public byte rfNotchEnable;
        /** default: 0 */
        public byte rfDabNotchEnable;
        public sdrplay_api_RspDxParamsT() {
                super();
        }
        protected List<String> getFieldOrder() {
                return Arrays.asList("hdrEnable", "biasTEnable", "antennaSel", "rfNotchEnable", "rfDabNotchEnable");
        }
    }
    
    public static class sdrplay_api_RspDxTunerParamsT extends Structure {
		/** default: sdrplay_api_RspDx_HDRMODE_BW_1_700 */
		public sdrplay_api_RspDx_HdrModeBwT hdrBw;
		public sdrplay_api_RspDxTunerParamsT() {
			super();
		}
		protected List<String> getFieldOrder() {
			return Arrays.asList("hdrBw");
		}
    }
    
    //</editor-fold>
    
    //<editor-fold desc="_tuner">
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
    //</editor-fold>
    
    //<editor-fold desc="_rx_channel">
    public static class sdrplay_api_RxChannelParamsT extends Structure {
		public sdrplay_api_TunerParamsT tunerParams;
		public sdrplay_api_ControlParamsT ctrlParams;
		public sdrplay_api_Rsp1aTunerParamsT rsp1aTunerParams;
		public sdrplay_api_Rsp2TunerParamsT rsp2TunerParams;
		public sdrplay_api_RspDuoTunerParamsT rspDuoTunerParams;
		public sdrplay_api_RspDxTunerParamsT rspDxTunerParams;
		public sdrplay_api_RxChannelParamsT() {
			super();
		}
		protected List<String> getFieldOrder() {
                    return Arrays.asList("tunerParams", "ctrlParams", "rsp1aTunerParams", "rsp2TunerParams", "rspDuoTunerParams", "rspDxTunerParams");
		}
                
                public static class ByReference extends sdrplay_api_RxChannelParamsT implements Structure.ByReference{};
    }
    //</editor-fold>
    
    //<editor-fold desc="_control">
    
    public static enum sdrplay_api_AgcControlT {
        sdrplay_api_AGC_DISABLE(0),
        sdrplay_api_AGC_100HZ(1),
        sdrplay_api_AGC_50HZ(2),
        sdrplay_api_AGC_5HZ(3),
        sdrplay_api_AGC_CTRL_EN(4);

        private int value;
        private static Map map = new HashMap<>();

        private sdrplay_api_AgcControlT(int value) {
            this.value = value;
        }

        static {
            for (sdrplay_api_AgcControlT valT : sdrplay_api_AgcControlT.values()) {
                map.put(valT.value, valT);
            }
        }

        public static sdrplay_api_AgcControlT valueOf(int valT) {
            return (sdrplay_api_AgcControlT) map.get(valT);
        }

        public int getValue() {
            return value;
        }
    }
    
    public static enum sdrplay_api_AdsbModeT {
        sdrplay_api_ADSB_DECIMATION(0),
        sdrplay_api_ADSB_NO_DECIMATION_LOWPASS(1),
        sdrplay_api_ADSB_NO_DECIMATION_BANDPASS_2MHZ(2),
        sdrplay_api_ADSB_NO_DECIMATION_BANDPASS_3MHZ(3);

        private int value;
        private static Map map = new HashMap<>();

        private sdrplay_api_AdsbModeT(int value) {
            this.value = value;
        }

        static {
            for (sdrplay_api_AdsbModeT valT : sdrplay_api_AdsbModeT.values()) {
                map.put(valT.value, valT);
            }
        }

        public static sdrplay_api_AdsbModeT valueOf(int valT) {
            return (sdrplay_api_AdsbModeT) map.get(valT);
        }

        public int getValue() {
            return value;
        }
    }
    
    public static class sdrplay_api_DcOffsetT extends Structure {
        /** default: 1 */
        public byte DCenable;
        /** default: 1 */
        public byte IQenable;
        public sdrplay_api_DcOffsetT() {
                super();
        }
        protected List<String> getFieldOrder() {
                return Arrays.asList("DCenable", "IQenable");
        }
    }
    
    public static class sdrplay_api_DecimationT extends Structure {
        /** default: 0 */
        public byte enable;
        /** default: 1 */
        public byte decimationFactor;
        /** default: 0 */
        public byte wideBandSignal;
        public sdrplay_api_DecimationT() {
                super();
        }
        protected List<String> getFieldOrder() {
                return Arrays.asList("enable", "decimationFactor", "wideBandSignal");
        }
    }
    
    public static class sdrplay_api_AgcT extends Structure {
        /**
         * default: sdrplay_api_AGC_50HZ<br>
         * C type : sdrplay_api_AgcControlT
         */
        public sdrplay_api_AgcControlT enable;
        /** default: -60 */
        public int setPoint_dBfs;
        /** default: 0 */
        public short attack_ms;
        /** default: 0 */
        public short decay_ms;
        /** default: 0 */
        public short decay_delay_ms;
        /** default: 0 */
        public short decay_threshold_dB;
        /** default: 0 */
        public int syncUpdate;
        public sdrplay_api_AgcT() {
                super();
        }
        protected List<String> getFieldOrder() {
                return Arrays.asList("enable", "setPoint_dBfs", "attack_ms", "decay_ms", "decay_delay_ms", "decay_threshold_dB", "syncUpdate");
        }
    }
    
    public static class sdrplay_api_ControlParamsT extends Structure {
        public sdrplay_api_DcOffsetT dcOffset;
        public sdrplay_api_DecimationT decimation;
        public sdrplay_api_AgcT agc;
        public sdrplay_api_AdsbModeT adsbMode;  //default: sdrplay_api_ADSB_DECIMATION

        public sdrplay_api_ControlParamsT() {
            super();
        }

        protected List<String> getFieldOrder() {
            return Arrays.asList("dcOffset", "decimation", "agc", "adsbMode");
        }
    }
    
    //</editor-fold>
    
    public static class HANDLE extends PointerType {

        public HANDLE(Pointer address) {
            super(address);
        }

        public HANDLE() {
            super();
        }
    };

    public class sdrplay_api_DeviceT extends Structure {
        public byte[] SerNo = new byte[SDRPLAY_MAX_SER_NO_LEN];
        public byte hwVer;
        public sdrplay_api_TunerSelectT tuner;
        public sdrplay_api_RspDuoModeT rspDuoMode;
        public double rspDuoSampleFreq;
        public HANDLE dev;
        
        @Override
        protected List<String> getFieldOrder() {
		return Arrays.asList("SerNo", "hwVer", "tuner", "rspDuoMode", "rspDuoSampleFreq", "dev");
	}
    }
    
    public static class sdrplay_api_ErrorInfoT extends Structure {
        /** C type : char[256] */
        public byte[] file = new byte[256];
        /** C type : char[256] */
        public byte[] function = new byte[256];
        public int line;
        /** C type : char[1024] */
        public byte[] message = new byte[1024];
        public sdrplay_api_ErrorInfoT() {
                super();
        }
        protected List<String> getFieldOrder() {
                return Arrays.asList("file", "function", "line", "message");
        }
    }
    
    //<editor-fold desc="Callback structures">
    
    public class sdrplay_api_CallbackFnsT extends Structure {
        public sdrplay_api_StreamCallback_t StreamACbFn;
        public sdrplay_api_StreamCallback_t StreamBCbFn;
        public sdrplay_api_EventCallback_t  EventCbFn;

        // Empty constructor for serialization
        public sdrplay_api_CallbackFnsT(){}
        
        // Convenient constructor for ease of use
        public sdrplay_api_CallbackFnsT(sdrplay_api_StreamCallback_t StreamACbFn, sdrplay_api_StreamCallback_t StreamBCbFn, sdrplay_api_EventCallback_t EventCbFn) {
            this.StreamACbFn = StreamACbFn;
            this.StreamBCbFn = StreamBCbFn;
            this.EventCbFn = EventCbFn;
        }
        
        @Override
        protected List<String> getFieldOrder() {
		return Arrays.asList("StreamACbFn", "StreamBCbFn", "EventCbFn");
	}
    }
    
    public class sdrplay_api_StreamCbParamsT extends Structure {

        public int firstSampleNum;
        public int grChanged;
        public int rfChanged;
        public int fsChanged;
        public int numSamples;

        @Override
        protected List<String> getFieldOrder() {
            return Arrays.asList("firstSampleNum", "grChanged", "rfChanged", "fsChanged", "numSamples");
        }
    }
    
    public class sdrplay_api_GainCbParamT extends Structure {

        public int gRdB;
        public int lnaGRdB;
        public double currGain;

        @Override
        protected List<String> getFieldOrder() {
            return Arrays.asList("gRdB", "lnaGRdB", "currGain");
        }
    }
    
    public class sdrplay_api_PowerOverloadCbParamT extends Structure {
        public sdrplay_api_PowerOverloadCbEventIdT powerOverloadChangeType;
    
    @Override
        protected List<String> getFieldOrder() {
            return Arrays.asList("powerOverloadChangeType");
        }
    }
    
    public class sdrplay_api_RspDuoModeCbParamT extends Structure {
        public sdrplay_api_RspDuoModeCbEventIdT modeChangeType;
    
    @Override
        protected List<String> getFieldOrder() {
            return Arrays.asList("modeChangeType");
        }
    }
    
    public class sdrplay_api_EventParamsT extends Structure {

        public sdrplay_api_GainCbParamT gainParams;
        public sdrplay_api_PowerOverloadCbParamT powerOverloadParams;
        public sdrplay_api_RspDuoModeCbParamT rspDuoModeParams;

        @Override
        protected List<String> getFieldOrder() {
            return Arrays.asList("gainParams", "powerOverloadParams", "rspDuoModeParams");
        }
    }
 
    //</editor-fold>
    
    //<editor-fold desc="Device Params Structures">
    
//    public class sdrplay_api_DeviceParamsT extends Structure {
//
//        public sdrplay_api_DevParamsT devParams;
//        public sdrplay_api_RxChannelParamsT rxChannelA;
//        public sdrplay_api_RxChannelParamsT rxChannelB;
//
//        @Override
//        protected List<String> getFieldOrder() {
//            return Arrays.asList("devParams", "rxChannelA", "rxChannelB");
//        }
//    }
    
    //</editor-fold>
    
    
    
    
    public interface sdrplay_api_StreamCallback_t extends Callback {

        void apply(Pointer xi, Pointer xq, sdrplay_api_StreamCbParamsT params, int numSamples, int reset, Pointer cbContext);
    };

    public interface sdrplay_api_EventCallback_t extends Callback {

        void apply(sdrplay_api_EventT eventId, sdrplay_api_TunerSelectT tuner, sdrplay_api_EventParamsT params, Pointer cbContext);
    };

    // Comman API function definitions
    sdrplay_api_ErrT sdrplay_api_Open();
    sdrplay_api_ErrT sdrplay_api_Close();
    sdrplay_api_ErrT sdrplay_api_ApiVersion(FloatByReference apiVer);
    sdrplay_api_ErrT sdrplay_api_LockDeviceApi();
    sdrplay_api_ErrT sdrplay_api_UnlockDeviceApi();
    sdrplay_api_ErrT sdrplay_api_GetDevices(sdrplay_api_DeviceT[] devices, IntByReference numDevs, int maxDevs);
    sdrplay_api_ErrT sdrplay_api_SelectDevice(sdrplay_api_DeviceT device);
    sdrplay_api_ErrT sdrplay_api_ReleaseDevice(sdrplay_api_DeviceT device);
    String sdrplay_api_GetErrorString(sdrplay_api_ErrT err);
    sdrplay_api_ErrorInfoT sdrplay_api_GetLastError(sdrplay_api_DeviceT device);
    sdrplay_api_ErrT sdrplay_api_DisableHeartbeat(); // Must be called before sdrplay_api_SelectDevice()

    // Device API function definitions
    sdrplay_api_ErrT        sdrplay_api_DebugEnable(HANDLE dev, sdrplay_api_DbgLvl_t enable); 
    //sdrplay_api_ErrT sdrplay_api_GetDeviceParams(HANDLE dev, sdrplay_api_DeviceParamsT deviceParams); 
    sdrplay_api_ErrT sdrplay_api_Init(HANDLE dev, sdrplay_api_CallbackFnsT callbackFns, Pointer cbContext); 
    sdrplay_api_ErrT sdrplay_api_Uninit(HANDLE dev);
    sdrplay_api_ErrT sdrplay_api_Update(HANDLE dev, sdrplay_api_TunerSelectT tuner, sdrplay_api_ReasonForUpdateT reasonForUpdate, sdrplay_api_ReasonForUpdateExtension1T reasonForUpdateExt1);
    sdrplay_api_ErrT sdrplay_api_Update(HANDLE dev, sdrplay_api_TunerSelectT tuner, int reasonForUpdate, sdrplay_api_ReasonForUpdateExtension1T reasonForUpdateExt1);
//    _SDRPLAY_DLL_QUALIFIER sdrplay_api_ErrT        sdrplay_api_SwapRspDuoActiveTuner(HANDLE dev, sdrplay_api_TunerSelectT *currentTuner, sdrplay_api_RspDuo_AmPortSelectT tuner1AmPortSel);
    sdrplay_api_ErrT sdrplay_api_SwapRspDuoDualTunerModeSampleRate(HANDLE dev, DoubleByReference currentSampleRate);
//    _SDRPLAY_DLL_QUALIFIER sdrplay_api_ErrT        sdrplay_api_SwapRspDuoMode(sdrplay_api_DeviceT *currDevice, sdrplay_api_DeviceParamsT **deviceParams,
//                                                                              sdrplay_api_RspDuoModeT rspDuoMode, double sampleRate, sdrplay_api_TunerSelectT tuner,
//                                                                              sdrplay_api_Bw_MHzT bwType, sdrplay_api_If_kHzT ifType, sdrplay_api_RspDuo_AmPortSelectT tuner1AmPortSel);

}
