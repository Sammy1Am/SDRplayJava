package io.github.sammy1am.sdrplay.model;

import io.github.sammy1am.sdrplay.SDRplayDevice;
import io.github.sammy1am.sdrplay.jnr.SDRplayAPIJNR;
import io.github.sammy1am.sdrplay.jnr.SDRplayAPIJNR.ReasonForUpdateT;

/**
 *
 * @author Sammy1Am
 */
public class RSP1A extends SDRplayDevice {
    
	private final byte NUM_LNA_STATES_BELOW_60_MHZ = 7;
    private final byte NUM_LNA_STATES_BELOW_1_GHZ = 10;
    private final byte NUM_LNA_STATES_LBAND = 9;
    
    public RSP1A(SDRplayAPIJNR.DeviceT nativeDevice) {
        super(nativeDevice);
    }

    @Override
    public byte getNumLNAStates() {
        double currentFreq = nativeParams.rxChannelA.get().tunerParams.rfFreq.rfHz.get();
        if (currentFreq < 60_000_000) {
        	return NUM_LNA_STATES_BELOW_60_MHZ;
        }
        else if (currentFreq < 1_000_000_000) {
            return NUM_LNA_STATES_BELOW_1_GHZ;
        } else {
            return NUM_LNA_STATES_LBAND;
        }
    }
    
    // TODO Implement model-specific functionality.
    @Override
    public boolean getRfNotch() {
    	return (nativeParams.devParams.get().rsp1aParams.rfNotchEnable.get() > 0);
    }
    
    @Override
    public void setRfNotch(boolean rfNotch) {
    	nativeParams.devParams.get().rsp1aParams.rfNotchEnable.set(rfNotch ? 1 : 0);
    	if (isInitialized) doUpdate(ReasonForUpdateT.Update_Rsp1a_RfNotchControl);
    }
    
    @Override
    public boolean getDABNotch() {
    	return (nativeParams.devParams.get().rsp1aParams.rfNotchEnable.get() > 0);
    }
    
    @Override
    public void setDABNotch(boolean dabNotch) {
    	nativeParams.devParams.get().rsp1aParams.rfNotchEnable.set(dabNotch ? 1 : 0);
    	if (isInitialized) doUpdate(ReasonForUpdateT.Update_Rsp1a_RfDabNotchControl);
    }
    
    @Override
    public boolean getBiasT() {
    	return (nativeParams.rxChannelA.get().rsp1aTunerParams.biasTEnable.get() > 0);
    }
    
    @Override
    public void setBiasT(boolean biasT) {
    	nativeParams.rxChannelA.get().rsp1aTunerParams.biasTEnable.set(biasT ? 1 : 0);
    	if (isInitialized) doUpdate(ReasonForUpdateT.Update_Rsp1a_BiasTControl);
    }
}
