package io.github.sammy1am.sdrplay;

import io.github.sammy1am.sdrplay.jnr.SDRplayAPIJNR;
import jnr.ffi.LibraryLoader;
import jnr.ffi.Runtime;
import jnr.ffi.byref.FloatByReference;

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
}
