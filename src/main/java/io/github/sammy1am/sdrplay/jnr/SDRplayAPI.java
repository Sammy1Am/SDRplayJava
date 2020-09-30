package io.github.sammy1am.sdrplay.jnr;

import io.github.sammy1am.sdrplay.jnr.TunerParamsT.TunerSelectT;
import jnr.ffi.LibraryLoader;
import jnr.ffi.Pointer;
import jnr.ffi.Runtime;
import jnr.ffi.Struct;
import jnr.ffi.annotations.Direct;
import jnr.ffi.annotations.Out;
import jnr.ffi.byref.FloatByReference;
import jnr.ffi.byref.IntByReference;
import jnr.ffi.byref.PointerByReference;
import jnr.ffi.util.EnumMapper;

/**
 * Base JNR wrapper for sdrplay_api.h.
 * @author Sammy1Am
 */
public class SDRplayAPI {
    
    private static final SDRplayAPIJNR API = LibraryLoader.create(SDRplayAPIJNR.class).load("sdrplay_api");
    private static final Runtime RUNTIME = Runtime.getRuntime(API);
    
    public static SDRplayAPIJNR getInstance() {
        return API;
    }
    
    public static Runtime getRuntime() {
        return RUNTIME;
    }
    
    public static interface SDRplayAPIJNR {    
       
        ErrT sdrplay_api_Open();
        ErrT sdrplay_api_Close();
        ErrT sdrplay_api_ApiVersion(@Out FloatByReference apiVer);
        ErrT sdrplay_api_LockDeviceApi();    
        ErrT sdrplay_api_UnlockDeviceApi();
        
        ErrT sdrplay_api_GetDevices(@Out @Direct DeviceT[] devices, IntByReference numDevs, int maxDevs);
        ErrT sdrplay_api_SelectDevice(@Direct DeviceT device);
        ErrT sdrplay_api_ReleaseDevice(@Direct DeviceT device);
        
        ErrT sdrplay_api_DebugEnable(@Direct Pointer dev, DbgLvl_t enable);
        ErrT sdrplay_api_GetDeviceParams(@Direct Pointer dev, @Direct PointerByReference deviceParamsPBR);
        ErrT sdrplay_api_Init(@Direct Pointer dev, CallbackFnsT callbackFns, Pointer cbContext);
        ErrT sdrplay_api_Uninit(@Direct Pointer dev);
        ErrT sdrplay_api_Update(@Direct Pointer dev, TunerSelectT tuner, ReasonForUpdateT reasonForUpdate, ReasonForUpdateExtension1T reasonForUpdateExt1);
    }
    
    public static enum ErrT implements EnumMapper.IntegerEnum {
        Success(0),
        Fail(1),
        InvalidParam(2),
        OutOfRange(3),
        GainUpdateError(4),
        RfUpdateError(5),
        FsUpdateError(6),
        HwError(7),
        AliasingError(8),
        AlreadyInitialised(9),
        NotInitialised(10),
        NotEnabled(11),
        HwVerError(12),
        OutOfMemError(13),
        ServiceNotResponding(14),
        StartPending(15),
        StopPending(16),
        InvalidMode(17),
        FailedVerification1(18),
        FailedVerification2(19),
        FailedVerification3(20),
        FailedVerification4(21),
        FailedVerification5(22),
        FailedVerification6(23),
        InvalidServiceVersion(24);

        private final int val;

        ErrT(int val) {
            this.val = val;
        }
        
        @Override
        public int intValue() {
            return val;
        }
    }
    
    public static enum ReasonForUpdateT implements EnumMapper.IntegerEnum {
        Update_None                       (0x00000000),

        // Reasons for master only mode 
        Update_Dev_Fs                     (0x00000001),
        Update_Dev_Ppm                    (0x00000002),
        Update_Dev_SyncUpdate             (0x00000004),
        Update_Dev_ResetFlags             (0x00000008),

        Update_Rsp1a_BiasTControl         (0x00000010),
        Update_Rsp1a_RfNotchControl       (0x00000020),
        Update_Rsp1a_RfDabNotchControl    (0x00000040),

        Update_Rsp2_BiasTControl          (0x00000080),
        Update_Rsp2_AmPortSelect          (0x00000100),
        Update_Rsp2_AntennaControl        (0x00000200),
        Update_Rsp2_RfNotchControl        (0x00000400),
        Update_Rsp2_ExtRefControl         (0x00000800),

        Update_RspDuo_ExtRefControl       (0x00001000),

        Update_Master_Spare_1             (0x00002000),
        Update_Master_Spare_2             (0x00004000),

        // Reasons for master and slave mode
        // Note: Update_Tuner_Gr MUST be the first value defined in this section!
        Update_Tuner_Gr                   (0x00008000),
        Update_Tuner_GrLimits             (0x00010000),
        Update_Tuner_Frf                  (0x00020000),
        Update_Tuner_BwType               (0x00040000),
        Update_Tuner_IfType               (0x00080000),
        Update_Tuner_DcOffset             (0x00100000),
        Update_Tuner_LoMode               (0x00200000),

        Update_Ctrl_DCoffsetIQimbalance   (0x00400000),
        Update_Ctrl_Decimation            (0x00800000),
        Update_Ctrl_Agc                   (0x01000000),
        Update_Ctrl_AdsbMode              (0x02000000),
        Update_Ctrl_OverloadMsgAck        (0x04000000),

        Update_RspDuo_BiasTControl        (0x08000000),
        Update_RspDuo_AmPortSelect        (0x10000000),
        Update_RspDuo_Tuner1AmNotchControl(0x20000000),
        Update_RspDuo_RfNotchControl      (0x40000000),
        Update_RspDuo_RfDabNotchControl   (0x80000000);
         
        private final int val;

        ReasonForUpdateT(int val) {
            this.val = val;
        }
        
        @Override
        public int intValue() {
            return val;
        }
    }
    
    public static enum ReasonForUpdateExtension1T implements EnumMapper.IntegerEnum {
        Update_Ext1_None                  (0x00000000),

        // Reasons for master only mode 
        Update_RspDx_HdrEnable            (0x00000001),
        Update_RspDx_BiasTControl         (0x00000002),
        Update_RspDx_AntennaControl       (0x00000004),
        Update_RspDx_RfNotchControl       (0x00000008),
        Update_RspDx_RfDabNotchControl    (0x00000010),
        Update_RspDx_HdrBw                (0x00000020);

        private final int val;

        ReasonForUpdateExtension1T(int val) {
            this.val = val;
        }
        
        @Override
        public int intValue() {
            return val;
        }
    }
    
    public static enum DbgLvl_t implements EnumMapper.IntegerEnum {
        DbgLvl_Disable(0),
        DbgLvl_Verbose(1),
        DbgLvl_Warning(2),
        DbgLvl_Error(3),
        DbgLvl_Message(4);

        private final int val;

        DbgLvl_t(int val) {
            this.val = val;
        }
        
        @Override
        public int intValue() {
            return val;
        }
    }
    
    public static class DeviceT extends Struct {
        public jnr.ffi.Struct.Unsigned8[] SerNo = array(new Struct.Unsigned8[64]);
        public jnr.ffi.Struct.Unsigned8 hwVer = new Struct.Unsigned8();
        public Struct.Signed32 tuner = new Struct.Signed32();
        public Struct.Signed32 rspDuoMode = new Struct.Signed32();
        public Struct.Double rspDuoSampleFreq = new Struct.Double();
        public jnr.ffi.Struct.Pointer dev = new Struct.Pointer();

        public DeviceT(final Runtime runtime) {
            super(runtime);
        }
    }
    
    public static class ErrorInfoT extends Struct {
        
        jnr.ffi.Struct.AsciiString file = new jnr.ffi.Struct.AsciiString(256);
        jnr.ffi.Struct.AsciiString function = new jnr.ffi.Struct.AsciiString(256);
        jnr.ffi.Struct.Signed32 line = new jnr.ffi.Struct.Signed32();
        jnr.ffi.Struct.AsciiString message = new jnr.ffi.Struct.AsciiString(1024);
        
        public ErrorInfoT(final Runtime runtime) {
            super(runtime);
        }
    }
}
