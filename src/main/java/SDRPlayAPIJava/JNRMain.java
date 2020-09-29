/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SDRPlayAPIJava;

import jnr.ffi.LibraryLoader;
import jnr.ffi.Memory;
import jnr.ffi.ObjectReferenceManager;
import jnr.ffi.Pointer;
import jnr.ffi.Struct;
import jnr.ffi.annotations.Delegate;
import jnr.ffi.byref.FloatByReference;
import jnr.ffi.Runtime;
import jnr.ffi.Struct.BYTE;
import jnr.ffi.Struct.PointerField;
import jnr.ffi.Struct.StructRef;
import jnr.ffi.StructLayout;
import jnr.ffi.annotations.Direct;
import jnr.ffi.annotations.Out;
import jnr.ffi.byref.IntByReference;
import jnr.ffi.byref.PointerByReference;
import jnr.ffi.provider.DelegatingMemoryIO;
import jnr.ffi.provider.converters.StructArrayParameterConverter;

/**
 *
 * @author Sam
 */
public class JNRMain {
    
    public static interface _SDRplayAPI {
        int sdrplay_api_Open();
        int sdrplay_api_ApiVersion(@Out FloatByReference apiVer);
        
        int sdrplay_api_GetDevices(@Out @Direct _DeviceT[] devices, IntByReference numDevs, int maxDevs);
        int sdrplay_api_SelectDevice(@Direct _DeviceT device);
        int sdrplay_api_Init(Pointer dev, Pointer callbackFns, Pointer cbContext);
    }
    
    public static interface StreamCallback {
        @Delegate
        public void callback(Pointer xi, Pointer xq, Pointer params, int numSamples, int reset, Pointer cbContext);
    }
    
    public static interface EventCallback {
        @Delegate
        public void callback(int eventId, int tuner, Pointer params, Pointer cbContext);
    }
    
    public static class _CallbackFnsT extends Struct {
        
        public StreamCallback StreamACbFn;
        public StreamCallback StreamBCbFn;
        public EventCallback EventCbFn;
        
        public _CallbackFnsT(final jnr.ffi.Runtime runtime) {
            super(runtime);
        }
    }
    
    public static class _DeviceT extends Struct {
        jnr.ffi.Struct.Unsigned8[] SerNo = array(new Unsigned8[64]);
        jnr.ffi.Struct.Unsigned8 hwVer = new Unsigned8();
        Signed32 tuner = new Signed32();
        Signed32 rspDuoMode = new Signed32();
        Double rspDuoSampleFreq = new Double();
        jnr.ffi.Struct.Pointer dev = new Pointer();
        
        public _DeviceT(final jnr.ffi.Runtime runtime) {
            super(runtime);
        }
    }
    
    public static void main(String[] args) {
        _SDRplayAPI API = LibraryLoader.create(_SDRplayAPI.class).load("sdrplay_api");
        jnr.ffi.Runtime runtime = jnr.ffi.Runtime.getRuntime(API);
        
        
        // Open and get API
        int err = API.sdrplay_api_Open();
        FloatByReference apiVer = new FloatByReference();
        err = API.sdrplay_api_ApiVersion(apiVer);
        System.out.println("API: "+ apiVer.floatValue());
        
        // List devices
        _DeviceT[] devices = Struct.arrayOf(runtime, _DeviceT.class, 1);
        IntByReference numDevices = new IntByReference();
        err = API.sdrplay_api_GetDevices(devices, numDevices, 1);

        // Select device
        err = API.sdrplay_api_SelectDevice(devices[0]);
        
        // Init device
        _CallbackFnsT callbacks = new _CallbackFnsT(runtime);
//        
//        callbacks.StreamACbFn = new StreamCallback() {
//            @Override
//            public void callback(Pointer xi, Pointer xq, Pointer params, int numSamples, int reset, Pointer cbContext) {
//                System.out.println("Got A!");
//            }
//        };
//        
//        callbacks.StreamBCbFn = new StreamCallback() {
//            @Override
//            public void callback(Pointer xi, Pointer xq, Pointer params, int numSamples, int reset, Pointer cbContext) {
//                System.out.println("Got A!");
//            }
//        };
//        
//        callbacks.EventCbFn = new EventCallback() {
//            @Override
//            public void callback(int eventId, int tuner, Pointer params, Pointer cbContext) {
//                System.out.println("Got Event!");
//            }
//        };
        
        err = API.sdrplay_api_Init(devices[0].dev.get(), Struct.getMemory(callbacks), null);
        System.out.println("We're done!");
    }
}
