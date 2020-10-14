package io.github.sammy1am.sdrplay.model;

import io.github.sammy1am.sdrplay.SDRplayDevice;
import io.github.sammy1am.sdrplay.jnr.SDRplayAPIJNR;

/**
 *
 * @author Sammy1Am
 */
public class RSPDuo extends SDRplayDevice {
    
    private final byte NUM_LNA_STATES = 10;
    private final byte NUM_LNA_STATES_AMPORT = 5;
    private final byte NUM_LNA_STATES_AM = 7;
    private final byte NUM_LNA_STATES_LBAND = 9;
    
    public RSPDuo(SDRplayAPIJNR.DeviceT nativeDevice) {
        super(nativeDevice);
    }

    @Override
    public byte getNumLNAStates() {
        double currentFreq = nativeParams.rxChannelA.get().tunerParams.rfFreq.rfHz.get();
        
        if (currentFreq <= 60000000) {
            // TODO: There are two values here based on which antenna port has been selected,
            // but we currently don't have support for determine which port is selected (so using the lower
            // of the two values for safety)
            return NUM_LNA_STATES_AMPORT;
        } else if (currentFreq <= 1000000000) {
            return NUM_LNA_STATES;
        } else {
            return NUM_LNA_STATES_LBAND;
        }
    }
    
    // TODO Implement model-specific functionality.
}
