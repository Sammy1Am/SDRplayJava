package io.github.sammy1am.sdrplay;

import io.github.sammy1am.sdrplay.jnr.CallbackFnsT.EventParamsT;
import io.github.sammy1am.sdrplay.jnr.CallbackFnsT.GainCbParamT;
import io.github.sammy1am.sdrplay.jnr.CallbackFnsT.PowerOverloadCbEventIdT;
import io.github.sammy1am.sdrplay.jnr.CallbackFnsT.PowerOverloadCbParamT;
import io.github.sammy1am.sdrplay.jnr.CallbackFnsT.RspDuoModeCbParamT;

/**
 * Java-tized EventParamsT to allow access to inner structures without JNR library.
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
        // TODO
        public GainCbParam(GainCbParamT gP) {
        }
    }

    public static class PowerOverloadCbParam {
        public final PowerOverloadCbEventIdT powerOverloadChangeType;
        
        public PowerOverloadCbParam(PowerOverloadCbParamT pOP) {
            powerOverloadChangeType = pOP.powerOverloadChangeType.get();
        }
    }

    public static class RspDuoModeCbParam {
        // TODO
        public RspDuoModeCbParam(RspDuoModeCbParamT rDMP) {
        }
    }
}
