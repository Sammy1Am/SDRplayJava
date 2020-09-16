/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sammy1am.sdrplay.api;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.PointerType;
import com.sun.jna.Structure;
import com.sun.jna.ptr.FloatByReference;
import com.sun.jna.ptr.IntByReference;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 * @author Sam
 */
public interface SDRPlayAPI extends Library {

    SDRPlayAPI INSTANCE = (SDRPlayAPI) Native.load("sdrplay_api.dll", SDRPlayAPI.class, Map.of(Library.OPTION_TYPE_MAPPER, new SDRPlayTypeMapper()));

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
            for (sdrplay_api_TunerSelectT errT : sdrplay_api_TunerSelectT.values()) {
                map.put(errT.value, errT);
            }
        }

        public static sdrplay_api_TunerSelectT valueOf(int errT) {
            return (sdrplay_api_TunerSelectT) map.get(errT);
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
            return (sdrplay_api_RspDuoModeT) map.getOrDefault(errT, sdrplay_api_RspDuoModeT.sdrplay_api_RspDuoMode_Unknown);
        }

        public int getValue() {
            return value;
        }
    }
    
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
        // Initialize these to avoid confusion when converting to native
        public sdrplay_api_TunerSelectT tuner;
        public sdrplay_api_RspDuoModeT rspDuoMode;
        public double rspDuoSampleFreq;
        public HANDLE dev;
        
        @Override
        protected List<String> getFieldOrder() {
		return Arrays.asList("SerNo", "hwVer", "tuner", "rspDuoMode", "rspDuoSampleFreq", "dev");
	}
    }

    sdrplay_api_ErrT sdrplay_api_Open();
    sdrplay_api_ErrT sdrplay_api_Close();
    sdrplay_api_ErrT sdrplay_api_ApiVersion(FloatByReference apiVer);
    sdrplay_api_ErrT sdrplay_api_LockDeviceApi();
    sdrplay_api_ErrT sdrplay_api_UnlockDeviceApi();
    sdrplay_api_ErrT sdrplay_api_GetDevices(sdrplay_api_DeviceT[] devices, IntByReference numDevs, int maxDevs);
    sdrplay_api_ErrT sdrplay_api_SelectDevice(sdrplay_api_DeviceT device);
    sdrplay_api_ErrT sdrplay_api_ReleaseDevice(sdrplay_api_DeviceT device);
//    _SDRPLAY_DLL_QUALIFIER const char*             sdrplay_api_GetErrorString(sdrplay_api_ErrT err);
//    _SDRPLAY_DLL_QUALIFIER sdrplay_api_ErrorInfoT* sdrplay_api_GetLastError(sdrplay_api_DeviceT *device);
//    _SDRPLAY_DLL_QUALIFIER sdrplay_api_ErrT        sdrplay_api_DisableHeartbeat(void); // Must be called before sdrplay_api_SelectDevice()

}
