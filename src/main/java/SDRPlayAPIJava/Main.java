/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SDRPlayAPIJava;

import com.sammy1am.sdrplay.api.SDRPlayAPI;
import com.sammy1am.sdrplay.api.SDRPlayAPI.sdrplay_api_DeviceT;
import com.sammy1am.sdrplay.api.SDRPlayAPI.sdrplay_api_ErrT;
import com.sammy1am.sdrplay.api.SDRPlayAPI.sdrplay_api_TunerSelectT;
import com.sun.jna.ToNativeContext;
import com.sun.jna.platform.EnumConverter;
import com.sun.jna.ptr.FloatByReference;
import com.sun.jna.ptr.IntByReference;

/**
 *
 * @author Sam
 */
public class Main {

    /**
     * @param args the command line arguments
     */
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
        
        returnCode = api.sdrplay_api_Close();
        
        
        System.out.println("Done!");
    }
    
}
