package io.github.sammy1am.sdrplay.model;

import io.github.sammy1am.sdrplay.SDRplayDevice;
import io.github.sammy1am.sdrplay.jnr.SDRplayAPIJNR;
import io.github.sammy1am.sdrplay.jnr.ControlParamsT.AgcControlT;
import io.github.sammy1am.sdrplay.jnr.DeviceParamsT.RspDx_AntennaSelectT;
import io.github.sammy1am.sdrplay.jnr.SDRplayAPIJNR.ReasonForUpdateExtension1T;
import io.github.sammy1am.sdrplay.jnr.SDRplayAPIJNR.ReasonForUpdateT;

/**
 *
 * @author neilharvey94044
 */
public class RSPDX extends SDRplayDevice {
    
    public RSPDX(SDRplayAPIJNR.DeviceT nativeDevice) {
        super(nativeDevice);
    }

    @Override
    public byte getNumLNAStates() {
        double currentFreq = nativeParams.rxChannelA.get().tunerParams.rfFreq.rfHz.get();
        byte numLNAStates = 18;
	
        if (currentFreq < 12_000_000)    {numLNAStates = 19;} else
        if (currentFreq < 60_000_000)    {numLNAStates = 20;} else
        if (currentFreq < 250_000_000)   {numLNAStates = 27;} else
        if (currentFreq < 420_000_000)   {numLNAStates = 28;} else
        if (currentFreq < 1_000_000_000) {numLNAStates = 21;} else
        if (currentFreq < 2_000_000_000) {numLNAStates = 19;}
        return numLNAStates;
    }
    
    // TODO Implement model-specific functionality.
    
    
    @Override
    public boolean getRfNotch() {
    	return (nativeParams.devParams.get().rspdxParams.rfNotchEnable.get() > 0);
    }
    
    @Override
    public void setRfNotch(boolean rfNotch) {
    	nativeParams.devParams.get().rspdxParams.rfNotchEnable.set(rfNotch ? 1 : 0);
    	if (isInitialized) doUpdate(ReasonForUpdateExtension1T.Update_RspDx_RfNotchControl);
    }
    
    @Override
    public boolean getDABNotch() {
    	return (nativeParams.devParams.get().rspdxParams.rfDabNotchEnable.get() > 0);
    }
    
    @Override
    public void setDABNotch(boolean dabNotch) {
    	nativeParams.devParams.get().rspdxParams.rfDabNotchEnable.set(dabNotch ? 1 : 0);
    	if (isInitialized) doUpdate(ReasonForUpdateExtension1T.Update_RspDx_RfDabNotchControl);
    }
    
    @Override
    public boolean getBiasT() {
    	return (nativeParams.devParams.get().rspdxParams.biasTEnable.get() > 0);
    }
    
    @Override
    public void setBiasT(boolean biasT) {
    	nativeParams.devParams.get().rspdxParams.biasTEnable.set(biasT ? 1 : 0);
    	if (isInitialized) doUpdate(ReasonForUpdateExtension1T.Update_RspDx_BiasTControl);
    }
    
    @Override
    public int getAntenna() {
    	return (nativeParams.devParams.get().rspdxParams.antennaSel.intValue());
    }
    
    public void setAntenna(int antSel) {
    	nativeParams.devParams.get().rspdxParams.antennaSel.set(antSel);
    	if (isInitialized) doUpdate(ReasonForUpdateExtension1T.Update_RspDx_AntennaControl);
    }
}
