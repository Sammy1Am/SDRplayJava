/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SDRPlayAPIJava;

import com.sun.jna.Pointer;
import com.sun.jna.ptr.FloatByReference;
import com.sun.jna.ptr.IntByReference;
import io.github.sammy1am.sdrplay.jna.SDRPlayAPI;
import io.github.sammy1am.sdrplay.jna.SDRPlayAPI.sdrplay_api_CallbackFnsT;
//import io.github.sammy1am.sdrplay.jna.SDRPlayAPI.sdrplay_api_DeviceParamsT;
import io.github.sammy1am.sdrplay.jna.SDRPlayAPI.sdrplay_api_DeviceT;
import io.github.sammy1am.sdrplay.jna.SDRPlayAPI.sdrplay_api_ErrT;

/**
 *
 * @author Sam
 */
public class JNAMain {

    public static void main(String[] args) {
        SDRPlayAPI api = SDRPlayAPI.INSTANCE;
        
        sdrplay_api_ErrT returnCode;
        
        returnCode = api.sdrplay_api_Open();
        
        FloatByReference apiVer = new FloatByReference();
        returnCode = api.sdrplay_api_ApiVersion(apiVer);
        System.out.println("API Ver: " + apiVer.getValue());
        
        IntByReference numDevices = new IntByReference();
        sdrplay_api_DeviceT[] devices = new sdrplay_api_DeviceT[16];
        returnCode = api.sdrplay_api_GetDevices(devices, numDevices, 16);
        
        sdrplay_api_DeviceT testDevice = devices[0];
        returnCode = api.sdrplay_api_SelectDevice(testDevice);
        
        returnCode = api.sdrplay_api_DebugEnable(testDevice.dev, SDRPlayAPI.sdrplay_api_DbgLvl_t.sdrplay_api_DbgLvl_Verbose);
        
        //sdrplay_api_DeviceParamsT devParams = new sdrplay_api_DeviceParamsT();
        //returnCode = api.sdrplay_api_GetDeviceParams(testDevice.dev, devParams);
        
        sdrplay_api_CallbackFnsT callbacks = new sdrplay_api_CallbackFnsT();
        callbacks.StreamACbFn = (xi, xq, params, numSamples, reset, cbContext) -> {System.out.println("StrA");};
        callbacks.StreamBCbFn = (xi, xq, params, numSamples, reset, cbContext) -> {System.out.println("StrB");};
        callbacks.EventCbFn = (eventId, tuner, params, cbContext) -> {
            System.out.println("Event: " + eventId);
        };
        Pointer p = Pointer.NULL;
        returnCode = api.sdrplay_api_Init(testDevice.dev, callbacks, p);
        
        returnCode = api.sdrplay_api_Update(testDevice.dev, 
                SDRPlayAPI.sdrplay_api_TunerSelectT.sdrplay_api_Tuner_Neither, 
                SDRPlayAPI.sdrplay_api_ReasonForUpdateT.sdrplay_api_Update_Tuner_Frf, 
                SDRPlayAPI.sdrplay_api_ReasonForUpdateExtension1T.sdrplay_api_Update_Ext1_None);
        
        //returnCode = api.sdrplay_api_GetDeviceParams(testDevice.dev, devParams);
        
        returnCode = api.sdrplay_api_Uninit(testDevice.dev);
        
        returnCode = api.sdrplay_api_Close();
        
        
        System.out.println("Done!");
    }
}
