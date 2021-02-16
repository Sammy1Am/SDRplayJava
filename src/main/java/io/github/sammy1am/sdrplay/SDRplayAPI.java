package io.github.sammy1am.sdrplay;

import io.github.sammy1am.sdrplay.model.RSPDuo;
import io.github.sammy1am.sdrplay.model.RSP1A;
import io.github.sammy1am.sdrplay.jnr.SDRplayAPIJNR;
import io.github.sammy1am.sdrplay.jnr.SDRplayAPIJNR.ErrT;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import jnr.ffi.LibraryLoader;
import jnr.ffi.Runtime;
import jnr.ffi.Struct;
import jnr.ffi.byref.FloatByReference;
import jnr.ffi.byref.IntByReference;

/**
 * Base JNR wrapper for sdrplay_api.h.
 * @author Sammy1Am
 */
public class SDRplayAPI {
    
    private static final SDRplayAPIJNR JNRAPI = LibraryLoader.create(SDRplayAPIJNR.class).load("sdrplay_api");
    private static final Runtime RUNTIME = Runtime.getRuntime(JNRAPI);
    
    public static SDRplayAPIJNR getJNRInstance() {
        return JNRAPI;
    }
    
    public static Runtime getJNRRuntime() {
        return RUNTIME;
    }
    
    public static void open() {
        ApiException.checkErrorCode(JNRAPI.sdrplay_api_Open());
    }
    
    public static void close() {
        ApiException.checkErrorCode(JNRAPI.sdrplay_api_Close());
    }
    
    public static float getApiVersion() {
        FloatByReference apiVer = new FloatByReference();
        ApiException.checkErrorCode(JNRAPI.sdrplay_api_ApiVersion(apiVer));
        return apiVer.floatValue();
    }
    
    public static String getErrorString(ErrT errorCode) {
        return JNRAPI.sdrplay_api_GetErrorString(errorCode);
    }
    
    public static void lockDeviceApi() {
        ApiException.checkErrorCode(JNRAPI.sdrplay_api_LockDeviceApi());
    }
    
    public static void unlockDeviceApi() {
        ApiException.checkErrorCode(JNRAPI.sdrplay_api_UnlockDeviceApi());
    }
    
    public static List<SDRplayDevice> getDevices(int maximumDevices) {
        SDRplayAPIJNR.DeviceT[] devices = Struct.arrayOf(RUNTIME, SDRplayAPIJNR.DeviceT.class, maximumDevices);
        IntByReference numDevices = new IntByReference();
        ApiException.checkErrorCode(JNRAPI.sdrplay_api_GetDevices(devices, numDevices, maximumDevices));
        
        ArrayList<SDRplayDevice> returnDevices = new ArrayList<>(numDevices.intValue());
        
        for (int d=0;d<numDevices.intValue();d++) {
            // Create a new SDRplayDevice using the native device and add it to the list
            switch ((int) devices[d].hwVer.byteValue() & 0xFF) {
                //TODO Add device classes for other models
                case 3:
                    returnDevices.add(new RSPDuo(devices[d]));
                    break;
                case 255:
                	returnDevices.add(new RSP1A(devices[d]));
                	break;
                case 1:
                default:
                    returnDevices.add(new SDRplayDevice(devices[d]));
            }
        }
        
        return returnDevices;
    }
    
    public static enum HWModel {
        UNKNOWN(0),
        RSP1(1),
        RSP1A(255),
        RSP2(2),
        RSPDUO(3),
        RSPDX(4);
        
        private final int val;
        
        HWModel(int val) {
            this.val = val;
        }
        
        public static HWModel valueOf(int val) {
            return Arrays.stream(values())
                    .filter(hwModel -> hwModel.val == val)
                    .findFirst().orElse(HWModel.UNKNOWN);
        }
    }
}
