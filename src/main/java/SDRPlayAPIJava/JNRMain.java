/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SDRPlayAPIJava;


import io.github.sammy1am.sdrplay.api.ApiException.ErrT;
import io.github.sammy1am.sdrplay.jnr.BaseStruct;
import io.github.sammy1am.sdrplay.jnr.DeviceParamsT;
import io.github.sammy1am.sdrplay.jnr.DeviceT;
import jnr.ffi.LibraryLoader;
import jnr.ffi.Pointer;
import jnr.ffi.Struct;
import jnr.ffi.annotations.Delegate;
import jnr.ffi.byref.FloatByReference;
import jnr.ffi.annotations.Direct;
import jnr.ffi.annotations.Out;
import jnr.ffi.byref.IntByReference;
import jnr.ffi.byref.PointerByReference;
import jnr.ffi.provider.ParameterFlags;

/**
 *
 * @author Sam
 */
public class JNRMain {
    
    public static interface _SDRplayAPI {
        
        
        ErrT sdrplay_api_Open();
        int sdrplay_api_Close();
        int sdrplay_api_ApiVersion(@Out FloatByReference apiVer);
        int sdrplay_api_LockDeviceApi();    
        int sdrplay_api_UnlockDeviceApi();
        
        int sdrplay_api_GetDevices(@Out @Direct DeviceT[] devices, IntByReference numDevs, int maxDevs);
        int sdrplay_api_SelectDevice(@Direct DeviceT device);
        int sdrplay_api_ReleaseDevice(@Direct DeviceT device);
        
        int sdrplay_api_DebugEnable(@Direct Pointer dev, int enable);
        ErrT sdrplay_api_GetDeviceParams(@Direct Pointer dev, @Direct PointerByReference deviceParamsPBR);
        int sdrplay_api_Init(@Direct Pointer dev, _CallbackFnsT callbackFns, Pointer cbContext);
        int sdrplay_api_Uninit(@Direct Pointer dev);
        int sdrplay_api_Update(@Direct Pointer dev, int tuner, int reasonForUpdate, int reasonForUpdateExt1);
    }
    
    public static interface StreamCallback {
        @Delegate
        public void call(Pointer xi, Pointer xq, Pointer params, int numSamples, int reset, Pointer cbContext);
    }

    public static interface EventCallback {
        @Delegate
        public void call(int eventId, int tuner, Pointer params, Pointer cbContext);
    }
    
    
    public static class _CallbackFnsT extends BaseStruct {
        
        public final Func<StreamCallback> StreamACbFn = func(StreamCallback.class);
        public final Func<StreamCallback> StreamBCbFn = func(StreamCallback.class);
        public final Func<EventCallback> EventCbFn = func(EventCallback.class);

        public _CallbackFnsT(final jnr.ffi.Runtime runtime) {
            super(runtime);
        }
    }
    
    public static void main(String[] args) {
        _SDRplayAPI API = LibraryLoader.create(_SDRplayAPI.class).load("sdrplay_api");
        jnr.ffi.Runtime runtime = jnr.ffi.Runtime.getRuntime(API);
        

        
        // Open and get API
        ErrT errorCode = API.sdrplay_api_Open();
        FloatByReference apiVer = new FloatByReference();
        int err = API.sdrplay_api_ApiVersion(apiVer);
        System.out.println("API: "+ apiVer.floatValue());
        
        // List devices
        DeviceT[] devices = Struct.arrayOf(runtime, DeviceT.class, 16);
        IntByReference numDevices = new IntByReference();
        err = API.sdrplay_api_GetDevices(devices, numDevices, 16);

        // Select device
        err = API.sdrplay_api_SelectDevice(devices[0]);
        
        // Set Debug
        err = API.sdrplay_api_DebugEnable(devices[0].dev.get(), 1);
        
        // Init device
        _CallbackFnsT callbacks = new _CallbackFnsT(runtime);
     
        
        StreamCallback scba =  new StreamCallback() {
            @Override
            public void call(Pointer xi, Pointer xq, Pointer params, int numSamples, int reset, Pointer cbContext) {
                System.out.println("Got A!");
            }
        };
        
        StreamCallback scbb =  new StreamCallback() {
            @Override
            public void call(Pointer xi, Pointer xq, Pointer params, int numSamples, int reset, Pointer cbContext) {
                System.out.println("Got A!");
            }
        };
        
        EventCallback ecb = new EventCallback() {
            @Override
            public void call(int eventId, int tuner, Pointer params, Pointer cbContext) {
                System.out.println("Got Event!");
            }
        };
        
        
        
        callbacks.StreamACbFn.set(scba);
        callbacks.StreamBCbFn.set(scbb);
        callbacks.EventCbFn.set(ecb);

        
        err = API.sdrplay_api_Init(devices[0].dev.get(), callbacks, null);

        // Get Params
        PointerByReference deviceParamsPBR = new PointerByReference();
        
        errorCode = API.sdrplay_api_GetDeviceParams(devices[0].dev.get(), deviceParamsPBR);
        
        DeviceParamsT deviceParams = new DeviceParamsT(runtime);
        deviceParams.useMemory(deviceParamsPBR.getValue());
        
        
        deviceParams.devParams.get().ppm.set(3.0);
        
        // Update
        err = API.sdrplay_api_Update(devices[0].dev.get(), 1, 0x00000002, 0x00000000);
        
        
        System.out.println("We're done!");
    }
}
