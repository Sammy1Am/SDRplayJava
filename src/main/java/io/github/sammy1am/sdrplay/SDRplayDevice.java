package io.github.sammy1am.sdrplay;

import io.github.sammy1am.sdrplay.jnr.DeviceParamsT;
import io.github.sammy1am.sdrplay.jnr.SDRplayAPIJNR.DeviceT;

/**
 * Java wrapper representing a single SDRplay device.
 * @author Sammy1Am
 */
public class SDRplayDevice {
    /** Representation of this device in native API memory. */
    private DeviceT nativeDevice;
    
    /** Representation of parameters for this device in native API memory. */
    private DeviceParamsT nativeParams;
    
    /** Has this device been selected.  Used to optimize parameter retrieval,
     * and make sure we're not trying anything fishy with an un-selected device.
     */
    private boolean isSelected = false;
    
    /**
     * Creates a friendly Java wrapper around an SDRplay API device.
     * @param nativeDevice Native representation of the DeviceT struct.
     */
    public SDRplayDevice(DeviceT nativeDevice) {
        this.nativeDevice = nativeDevice;
    }
}
