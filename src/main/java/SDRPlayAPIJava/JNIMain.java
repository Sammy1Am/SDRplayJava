/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SDRPlayAPIJava;

import io.github.sammy1am.sdrplay.jni.SDRplayAPI;
import io.github.sammy1am.sdrplay.jni.swig.SWIGTYPE_p_HANDLE;
import io.github.sammy1am.sdrplay.jni.swig.SWIGTYPE_p_float;
import io.github.sammy1am.sdrplay.jni.swig.SWIGTYPE_p_sdrplay_api_CallbackFnsT;
import io.github.sammy1am.sdrplay.jni.swig.SWIGTYPE_p_unsigned_int;
import io.github.sammy1am.sdrplay.jni.swig.SWIGTYPE_p_void;
import io.github.sammy1am.sdrplay.jni.swig.deviceTArray;
import io.github.sammy1am.sdrplay.jni.swig.sdrplay_api;
import io.github.sammy1am.sdrplay.jni.swig.sdrplay_api_DeviceT;
import io.github.sammy1am.sdrplay.jni.swig.sdrplay_api_ErrT;

/**
 *
 * @author Sam
 */
public class JNIMain {
    
    static {
        //System.load("C:\\Users\\Sam\\Documents\\NetBeansProjects\\SDRPlayAPIJava\\src\\main\\resources\\win32-x86-64\\sdrplay_api_wrap.dll");
        System.loadLibrary("sdrplay_api_wrap");
    }
    
    
    public static void main(String[] args) {
        
        //int test = SDRplayAPI.initDevice();
        
        // Open
        sdrplay_api_ErrT err = sdrplay_api.sdrplay_api_Open();
        
        // Check API
        SWIGTYPE_p_float apiVer = sdrplay_api.new_floatp();
        err = sdrplay_api.sdrplay_api_ApiVersion(apiVer);
        
        // Get devices
        sdrplay_api_DeviceT devicesPtr = new sdrplay_api_DeviceT();
        SWIGTYPE_p_unsigned_int numDevs = sdrplay_api.new_uintp();
        err = sdrplay_api.sdrplay_api_GetDevices(devicesPtr, numDevs, 16);
        deviceTArray devices = deviceTArray.frompointer(devicesPtr);
        
        // Select device
        SWIGTYPE_p_HANDLE x = devices.getitem(0).getDev();
        sdrplay_api.sdrplay_api_SelectDevice(devices.getitem(0));
        SWIGTYPE_p_HANDLE y = devices.getitem(0).getDev();
        
        // Init device
        
        //..SWIGTYPE_p_sdrplay_api_CallbackFnsT callbackFunctions = sdrplay_api.;
        
        //sdrplay_api.sdrplay_api_Init(y, callbackFns, null);
        
        System.out.println(sdrplay_api.floatp_value(apiVer));
    }
}
