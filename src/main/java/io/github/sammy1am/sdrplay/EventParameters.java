package io.github.sammy1am.sdrplay;

import io.github.sammy1am.sdrplay.jnr.CallbackFnsT.EventParamsT;
import io.github.sammy1am.sdrplay.jnr.CallbackFnsT.GainCbParamT;
import io.github.sammy1am.sdrplay.jnr.CallbackFnsT.PowerOverloadCbEventIdT;
import io.github.sammy1am.sdrplay.jnr.CallbackFnsT.PowerOverloadCbParamT;
import io.github.sammy1am.sdrplay.jnr.CallbackFnsT.RspDuoModeCbEventIdT;
import io.github.sammy1am.sdrplay.jnr.CallbackFnsT.RspDuoModeCbParamT;

/**
 * Java-tized EventParamsT to allow access to inner structures without JNR library.  This is 
 * tenable because these are read-only values and we don't need to write them back to native
 * memory.
 * @author Sammy1Am
 */
public class EventParameters {
    public final GainCbParam gainParams;
    public final PowerOverloadCbParam powerOverloadParams;
    public final RspDuoModeCbParam  rspDuoModeParams;
    
    public EventParameters(EventParamsT eventParams) {
        gainParams = new GainCbParam(eventParams.gainParams);
        powerOverloadParams = new PowerOverloadCbParam(eventParams.powerOverloadParams);
        rspDuoModeParams = new RspDuoModeCbParam(eventParams.rspDuoModeParams);
    }

    public static class GainCbParam {
        public final long gRdB;
        public final long lnaGRdB;
        public final double currGain;
        public GainCbParam(GainCbParamT gP) {
            gRdB = gP.gRdB.get();
            lnaGRdB = gP.lnaGRdb.get();
            currGain = gP.currGain.get();
        }
    }

    public static class PowerOverloadCbParam {
        public final PowerOverloadCbEventIdT powerOverloadChangeType;
        
        public PowerOverloadCbParam(PowerOverloadCbParamT pOP) {
            powerOverloadChangeType = pOP.powerOverloadChangeType.get();
        }
    }

    public static class RspDuoModeCbParam {
        public final RspDuoModeCbEventIdT modeChangeType;
        public RspDuoModeCbParam(RspDuoModeCbParamT rDMP) {
            modeChangeType = rDMP.modeChangeType.get();
        }
    }
}
