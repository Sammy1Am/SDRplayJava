package SDRplayJava;


import io.github.sammy1am.sdrplay.jnr.CallbackFnsT;
import io.github.sammy1am.sdrplay.jnr.CallbackFnsT.EventCallback;
import io.github.sammy1am.sdrplay.jnr.CallbackFnsT.EventT;
import io.github.sammy1am.sdrplay.jnr.CallbackFnsT.StreamCallback;
import io.github.sammy1am.sdrplay.jnr.DeviceParamsT;
import io.github.sammy1am.sdrplay.jnr.DeviceParamsT.DevParamsT;
import io.github.sammy1am.sdrplay.jnr.DeviceParamsT.RxChannelParamsT;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import io.github.sammy1am.sdrplay.EventParameters;
import io.github.sammy1am.sdrplay.SDRplayAPI;
import io.github.sammy1am.sdrplay.jnr.CallbackFnsT.StreamCbParamsT;
import io.github.sammy1am.sdrplay.jnr.ControlParamsT.AgcControlT;
import io.github.sammy1am.sdrplay.jnr.SDRplayAPIJNR;
import io.github.sammy1am.sdrplay.jnr.SDRplayAPIJNR.DbgLvl_t;
import io.github.sammy1am.sdrplay.jnr.SDRplayAPIJNR.DeviceT;
import io.github.sammy1am.sdrplay.jnr.SDRplayAPIJNR.ErrT;
import io.github.sammy1am.sdrplay.jnr.SDRplayAPIJNR.ReasonForUpdateExtension1T;
import io.github.sammy1am.sdrplay.jnr.SDRplayAPIJNR.ReasonForUpdateT;
import io.github.sammy1am.sdrplay.jnr.TunerParamsT.Bw_MHzT;
import io.github.sammy1am.sdrplay.jnr.TunerParamsT.If_kHzT;
import io.github.sammy1am.sdrplay.jnr.TunerParamsT.LoModeT;
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
        
    	ErrT err = ErrT.Success;
    	
        SDRplayAPIJNR API = SDRplayAPI.getJNRInstance();
        jnr.ffi.Runtime runtime = SDRplayAPI.getJNRRuntime();
        
        // Open and get API
        err = API.sdrplay_api_Open();
        FloatByReference apiVer = new FloatByReference();
        err = API.sdrplay_api_ApiVersion(apiVer);
        System.out.println("API: "+ apiVer.floatValue());
        
        // List devices
        DeviceT[] devices = Struct.arrayOf(runtime, DeviceT.class, 16);
        IntByReference numDevices = new IntByReference();
        err = API.sdrplay_api_GetDevices(devices, numDevices, 16);

        //Disable heartbeats - to allow debugging without timing out
        //err = API.sdrplay_api_DisableHeartbeat();
        //System.out.println("Disable Heartbeat: "+ err.name());
        
        // Select device
        err = API.sdrplay_api_SelectDevice(devices[0]);
        
        // Set Debug
        err = API.sdrplay_api_DebugEnable(devices[0].dev.get(), DbgLvl_t.DbgLvl_Verbose);
        //err = API.sdrplay_api_DebugEnable(devices[0].dev.get(), DbgLvl_t.DbgLvl_Message);
        //err = API.sdrplay_api_DebugEnable(devices[0].dev.get(), DbgLvl_t.DbgLvl_Warning);
        
        CallbackFnsT callbacks = new CallbackFnsT(runtime);
     
        
        StreamCallback scba =  new StreamCallback() {
            @Override
            public void call(Pointer xi, Pointer xq, StreamCbParamsT params, int numSamples, int reset, Pointer cbContext) {
                //System.out.println("Got A!");
            }
        };
        
        StreamCallback scbb =  new StreamCallback() {
            @Override
            public void call(Pointer xi, Pointer xq, StreamCbParamsT params, int numSamples, int reset, Pointer cbContext) {
                System.out.println("Got B!");
            }
        };
        
        EventCallback ecb = new EventCallback() {
            
            @Override
            public void call(EventT eventId, TunerSelectT tuner, EventParameters params) {
            	switch(eventId)
            	{
            	case GainChange:
	                System.out.printf("%s gRdB=%d lnaGRdB=%d systemGain=%.2f\n",
	    			eventId.name(), params.gainParams.gRdB, params.gainParams.lnaGRdB,
	    			params.gainParams.currGain);
				default:
					break;
            	}
            }
        };
        
        // Get Params
        PointerByReference deviceParamsPBR = new PointerByReference();
        
        err = API.sdrplay_api_GetDeviceParams(devices[0].dev.get(), deviceParamsPBR);
        
        DeviceParamsT deviceParams = new DeviceParamsT(runtime);
        deviceParams.useMemory(deviceParamsPBR.getValue());

        //Set starting device parameters
        deviceParams.devParams.get().fsFreq.fsHz.set(8_000_000);
        
        //Set tuner parameters
        deviceParams.rxChannelA.get().tunerParams.rfFreq.rfHz.set(700_000_000);
        deviceParams.rxChannelA.get().tunerParams.gain.gRdB.set(20);
        deviceParams.rxChannelA.get().tunerParams.gain.LNAstate.set(5);
        deviceParams.rxChannelA.get().ctrlParams.agc.enable.set(AgcControlT.AGC_DISABLE);
        deviceParams.rxChannelA.get().tunerParams.ifType.set(If_kHzT.IF_Zero);
        deviceParams.rxChannelA.get().tunerParams.bwType.set(Bw_MHzT.BW_8_000);
        
        //Set callback functions
        callbacks.StreamACbFn.set(scba);
        callbacks.StreamBCbFn.set(scbb);
        callbacks.EventCbFn.set(ecb);
        
        //Init device
        err = API.sdrplay_api_Init(devices[0].dev.get(), callbacks, null);
        System.out.println("Error Return from sdrplay_api_Init: " + err.name());
        

        int gr = 20;
        java.util.Scanner s = new java.util.Scanner(System.in);
        String c = "!";
        while(!c.contentEquals("q"))
        {
        	System.out.println("Enter one command [GRU][GRD][BN]:");
        	c = s.nextLine();
	        switch(c)
	        {
		        case "GRU":
		        {
			        //Set IF Gain up - this value is automatically changed if AGC is on, so disable AGC first
		        	if (gr < 59) {
		        		gr += 1;
		        		deviceParams.rxChannelA.get().tunerParams.gain.gRdB.set(gr) ;
		        		err = API.sdrplay_api_Update(devices[0].dev.get(), TunerSelectT.Tuner_A, ReasonForUpdateT.Update_Tuner_Gr, ReasonForUpdateExtension1T.Update_Ext1_None);
		        		System.out.println("Error Return from IF Gain Update: " + err.name());
		        	}
			        break;
		        }
		        case "GRD":
		        {
			        //Set IF Gain down - this value is automatically changed if AGC is on, so disable AGC first
		        	if (gr > 20) {
		        		gr -= 1;
				        deviceParams.rxChannelA.get().tunerParams.gain.gRdB.set(gr) ;
				        err = API.sdrplay_api_Update(devices[0].dev.get(), TunerSelectT.Tuner_A, ReasonForUpdateT.Update_Tuner_Gr, ReasonForUpdateExtension1T.Update_Ext1_None);
				        System.out.println("Error Return from IF Gain Update: " + err.name());
		        	}
		        	break;
		        }
		        case "BN":
		        {
			        //Enable RSPdx rfNotch
			      	deviceParams.devParams.get().rspdxParams.rfNotchEnable.set(1);
			      	err = API.sdrplay_api_Update(devices[0].dev.get(), TunerSelectT.Tuner_A, ReasonForUpdateT.Update_None, ReasonForUpdateExtension1T.Update_RspDx_RfNotchControl);
			      	System.out.println("Error return from rfNotch Update: "+err.name());
			      	break;
		        }
	        }
	        
        }
        
        // Examine raw channel data after we have updated
        RxChannelParamsT channelA = deviceParams.rxChannelA.get();
        Pointer ptr = Struct.getMemory(channelA);
        byte tunerdata[] = new byte[Struct.size(channelA)];
        ptr.get(0, tunerdata, 0, Struct.size(channelA)  );
        System.out.println("Should have byte array now.");
        WriteRawFile(tunerdata, "tunerdata.txt");
        
	
        System.out.println("Closing API");
        err = API.sdrplay_api_Uninit(devices[0].dev.get());
        err = API.sdrplay_api_UnlockDeviceApi();
        err = API.sdrplay_api_Close();
      	
        System.out.println("We're done!");
        System.exit(0);
    }
    
    protected static void WriteRawFile(byte[] buf, String binaryOut)
    {
    	try (OutputStream imageFileOut = Files.newOutputStream(Paths.get(binaryOut), StandardOpenOption.CREATE))
    	{
    		System.out.println("About to write to: " + binaryOut);
    	
    			if(buf.length > 0) {
    				imageFileOut.write(buf, 0, buf.length);
    			}
    	}
    	catch (IOException ex) {
    		System.err.println(ex);
    	}
    }
    
    // These values are important for determining machine alignment and necessary padding in the Structs.
    // The JNR library does not fully implement machine alignment for Structs, it has a bug that leaves
    // necessary padding off the end of structs which causes problems when the structs are stacked.
    // Compare to actual c sizes the SDRPlay API is compiled to, add padding as required.
    protected static void ShowStructSizes(DeviceParamsT deviceParams)
    {
        //Examine Dev Parms Sizes
        DevParamsT dev = deviceParams.devParams.get();
        
        System.out.println("Size of devParams:" + Integer.toString( Struct.size(dev)) + ":" + Integer.toString(Struct.alignment(dev)));
        
        System.out.println("Size of fsFreq:" + Integer.toString( Struct.size(dev.fsFreq)) + ":" + Integer.toString(Struct.alignment(dev.fsFreq)));
        System.out.println("Size of SyncUpdateT:" + Integer.toString( Struct.size(dev.syncUpdate)) + ":" + Integer.toString(Struct.alignment(dev.syncUpdate)));
        System.out.println("Size of ResetFlagsT:" + Integer.toString( Struct.size(dev.resetFlags)) + ":" + Integer.toString(Struct.alignment(dev.resetFlags)));
        
        System.out.println("Size of Rsp1aParamsT:" + Integer.toString(Struct.size(dev.rsp1aParams)) + ":" + Integer.toString(Struct.alignment(dev.rsp1aParams)));
        System.out.println("Size of Rsp2ParamsT:" + Integer.toString(Struct.size(dev.rsp2Params)) + ":" + Integer.toString(Struct.alignment(dev.rsp2Params)));
        System.out.println("Size of RspDuoParamsT:" + Integer.toString(Struct.size(dev.rspduoParams)) + ":" + Integer.toString(Struct.alignment(dev.rspduoParams)) );
        System.out.println("Size of RspDxParamsT:" + Integer.toString(Struct.size(dev.rspdxParams)) + ":" + Integer.toString(Struct.alignment(dev.rspdxParams)));
        
        RxChannelParamsT channelA = deviceParams.rxChannelA.get();
        
        System.out.println("Size of channel1A:" + Integer.toString( Struct.size(channelA)) );
        System.out.println("Size of sdrplay_api_TunerParamsT:" + Integer.toString( Struct.size(channelA.tunerParams)) + ":" + Integer.toString(Struct.alignment(channelA.tunerParams)));
        System.out.println("Size of sdrplay_api_ControlParamsT:" + Integer.toString( Struct.size(channelA.ctrlParams)) + ":" + Integer.toString(Struct.alignment(channelA.ctrlParams)));
        System.out.println("Size of sdrplay_api_Rsp1aTunerParamsT:" + Integer.toString( Struct.size(channelA.rsp1aTunerParams)) + ":" + Integer.toString(Struct.alignment(channelA.rsp1aTunerParams)));
        System.out.println("Size of sdrplay_api_Rsp2TunerParamsT:" + Integer.toString( Struct.size(channelA.rsp2TunerParams)) + ":" + Integer.toString(Struct.alignment(channelA.rsp2TunerParams)));
        System.out.println("Size of sdrplay_api_RspDuoTunerParamsT:" + Integer.toString( Struct.size(channelA.rspduoTunerParams)) + ":" + Integer.toString(Struct.alignment(channelA.rspduoTunerParams)));
        System.out.println("Size of sdrplay_api_RspDxTunerParamsT:" + Integer.toString( Struct.size(channelA.rspdxTunerParams)) + ":" + Integer.toString(Struct.alignment(channelA.rspdxTunerParams)));
        
        System.out.println("Size of sdrplay_api_GainT:" + Integer.toString( Struct.size(channelA.tunerParams.gain)) + ":" + Integer.toString(Struct.alignment(channelA.tunerParams.gain)));
        System.out.println("Size of sdrplay_api_GainValuesT:" + Integer.toString( Struct.size(channelA.tunerParams.gain.gainVals)) + ":" + Integer.toString(Struct.alignment(channelA.tunerParams.gain.gainVals)));
        
        System.out.println("Size of sdrplay_api_RfFreqT:" + Integer.toString( Struct.size(channelA.tunerParams.rfFreq)) + ":" + Integer.toString(Struct.alignment(channelA.tunerParams.rfFreq)));
        System.out.println("Size of sdrplay_api_DcOffsetTunerT:" + Integer.toString( Struct.size(channelA.tunerParams.dcOffsetTuner)) + ":" + Integer.toString(Struct.alignment(channelA.tunerParams.dcOffsetTuner)));
        System.out.println("Size of sdrplay_api_DcOffsetT:" + Integer.toString( Struct.size(channelA.ctrlParams.dcOffset)) + ":" + Integer.toString(Struct.alignment(channelA.ctrlParams.dcOffset)));
        System.out.println("Size of sdrplay_api_DecimationT:" + Integer.toString( Struct.size(channelA.ctrlParams.decimation)) + ":" + Integer.toString(Struct.alignment(channelA.ctrlParams.decimation)));
        System.out.println("Size of sdrplay_api_AgcT:" + Integer.toString( Struct.size(channelA.ctrlParams.agc)) + ":" + Integer.toString(Struct.alignment(channelA.ctrlParams.agc)));

    }
    
}
