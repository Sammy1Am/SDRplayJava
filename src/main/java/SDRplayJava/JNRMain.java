package SDRplayJava;


import io.github.sammy1am.sdrplay.jnr.CallbackFnsT;
import io.github.sammy1am.sdrplay.jnr.CallbackFnsT.EventCallback;
import io.github.sammy1am.sdrplay.jnr.CallbackFnsT.StreamCallback;
import io.github.sammy1am.sdrplay.jnr.DeviceParamsT;
import io.github.sammy1am.sdrplay.SDRplayAPI;
import io.github.sammy1am.sdrplay.jnr.CallbackFnsT.StreamCbParamsT;
import io.github.sammy1am.sdrplay.jnr.SDRplayAPIJNR;
import io.github.sammy1am.sdrplay.jnr.SDRplayAPIJNR.DbgLvl_t;
import io.github.sammy1am.sdrplay.jnr.SDRplayAPIJNR.DeviceT;
import io.github.sammy1am.sdrplay.jnr.SDRplayAPIJNR.ErrT;
import io.github.sammy1am.sdrplay.jnr.SDRplayAPIJNR.ReasonForUpdateExtension1T;
import io.github.sammy1am.sdrplay.jnr.SDRplayAPIJNR.ReasonForUpdateT;
import io.github.sammy1am.sdrplay.jnr.TunerParamsT.TunerSelectT;
import jnr.ffi.Pointer;
import jnr.ffi.Struct;
import jnr.ffi.byref.FloatByReference;
import jnr.ffi.byref.IntByReference;
import jnr.ffi.byref.PointerByReference;

/**
 * A quick sanity check for raw JNR calls.
 * @author Sammy1Am
 */
public class JNRMain {
    
    
    public static void main(String[] args) {
        
        SDRplayAPIJNR API = SDRplayAPI.getJNRInstance();
        jnr.ffi.Runtime runtime = SDRplayAPI.getJNRRuntime();
        
        // Open and get API
        ErrT err = API.sdrplay_api_Open();
        FloatByReference apiVer = new FloatByReference();
        err = API.sdrplay_api_ApiVersion(apiVer);
        System.out.println("API: "+ apiVer.floatValue());
        
        // List devices
        DeviceT[] devices = Struct.arrayOf(runtime, DeviceT.class, 16);
        IntByReference numDevices = new IntByReference();
        err = API.sdrplay_api_GetDevices(devices, numDevices, 16);

        // Select device
        err = API.sdrplay_api_SelectDevice(devices[0]);
        
        // Set Debug
        err = API.sdrplay_api_DebugEnable(devices[0].dev.get(), DbgLvl_t.DbgLvl_Verbose);
        
        // Init device
        CallbackFnsT callbacks = new CallbackFnsT(runtime);
     
        
        StreamCallback scba =  new StreamCallback() {
            @Override
            public void call(Pointer xi, Pointer xq, StreamCbParamsT params, int numSamples, int reset, Pointer cbContext) {
                System.out.println("Got A!");
            }
        };
        
        StreamCallback scbb =  new StreamCallback() {
            @Override
            public void call(Pointer xi, Pointer xq, StreamCbParamsT params, int numSamples, int reset, Pointer cbContext) {
                System.out.println("Got A!");
            }
        };
        
        EventCallback ecb = new EventCallback() {
            @Override
            public void call(CallbackFnsT.EventT eventId, TunerSelectT tuner, CallbackFnsT.EventParamsT params, jnr.ffi.Pointer cbContext) {
                System.out.println("Got Event!");
            }
        };
        
        
        
        callbacks.StreamACbFn.set(scba);
        callbacks.StreamBCbFn.set(scbb);
        callbacks.EventCbFn.set(ecb);

        
        err = API.sdrplay_api_Init(devices[0].dev.get(), callbacks, null);

        // Get Params
        PointerByReference deviceParamsPBR = new PointerByReference();
        
        err = API.sdrplay_api_GetDeviceParams(devices[0].dev.get(), deviceParamsPBR);
        
        DeviceParamsT deviceParams = new DeviceParamsT(runtime);
        deviceParams.useMemory(deviceParamsPBR.getValue());
        
        
        deviceParams.devParams.get().ppm.set(3.0);
        
        // Update
        err = API.sdrplay_api_Update(devices[0].dev.get(), TunerSelectT.Tuner_A, ReasonForUpdateT.Update_Dev_Ppm, ReasonForUpdateExtension1T.Update_Ext1_None);
        
        
        System.out.println("We're done!");
    }
}
