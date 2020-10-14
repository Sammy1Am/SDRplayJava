package SDRplayJava;

import io.github.sammy1am.sdrplay.SDRplayAPI;
import io.github.sammy1am.sdrplay.SDRplayDevice;
import java.util.List;
import io.github.sammy1am.sdrplay.StreamsReceiver;
import io.github.sammy1am.sdrplay.jnr.CallbackFnsT.StreamCbParamsT;
import io.github.sammy1am.sdrplay.jnr.SDRplayAPIJNR;

/**
 * Basic sanity check for Java API.
 * @author Sammy1Am
 */
public class Main {
    public static void main(String[] args) {
        SDRplayAPI.open(); // Open API
        
        System.out.println(SDRplayAPI.getApiVersion()); // Quick version sanity check
        
        SDRplayAPI.lockDeviceApi(); // Lock devices until we've selected them
        
        // Get first device
        List<SDRplayDevice> devices = SDRplayAPI.getDevices(1);
        SDRplayDevice dev = devices.get(0);
        
        
        dev.select(); // Select device
        System.out.printf("Selected hwVer:%s serial:%s%n", dev.getHWModel(), dev.getSerialNumber());
        //dev.debugEnable(SDRplayAPIJNR.DbgLvl_t.DbgLvl_Verbose);
        SDRplayAPI.unlockDeviceApi(); // Unlock now that we've selected
        
        Processor p = new Processor();
        
        dev.setStreamsReceiver(p);
        
        dev.init();
        
        System.out.println("Cleaning up...");
        dev.uninit();
        dev.release();
        System.out.println("Finished!");
    }
    
    static class Processor implements StreamsReceiver {
        @Override
        public void receiveStreamA(short[] xi, short[] xq, StreamCbParamsT params, int numSamples, int reset) {
            System.out.printf("Rcv A %s:%s:%s%n", params.firstSampleNum.get(), numSamples, xi.length); 
        }
    }

    
    
    
}
