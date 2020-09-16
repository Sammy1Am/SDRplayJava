/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.sammy1am.sdrplay.api;

import io.github.sammy1am.sdrplay.api.SDRPlayAPI.sdrplay_api_ErrT;
import io.github.sammy1am.sdrplay.api.SDRPlayAPI.sdrplay_api_EventT;
import io.github.sammy1am.sdrplay.api.SDRPlayAPI.sdrplay_api_PowerOverloadCbEventIdT;
import io.github.sammy1am.sdrplay.api.SDRPlayAPI.sdrplay_api_RspDuoModeCbEventIdT;
import io.github.sammy1am.sdrplay.api.SDRPlayAPI.sdrplay_api_RspDuoModeT;
import io.github.sammy1am.sdrplay.api.SDRPlayAPI.sdrplay_api_TunerSelectT;
import com.sun.jna.DefaultTypeMapper;
import com.sun.jna.ToNativeContext;
import com.sun.jna.platform.EnumConverter;

/**
 *
 * @author Sam
 */
public class SDRPlayTypeMapper extends DefaultTypeMapper {
    public SDRPlayTypeMapper() {
        EnumConverter<sdrplay_api_ErrT> errTConverter = new EnumConverter<>(sdrplay_api_ErrT.class);
        addFromNativeConverter(sdrplay_api_ErrT.class, errTConverter);
        addToNativeConverter(sdrplay_api_ErrT.class, errTConverter);
        
        EnumConverter<sdrplay_api_TunerSelectT> tunerSelectTConverter = new NullAwareEnumConverter<>(sdrplay_api_TunerSelectT.class);
        addFromNativeConverter(sdrplay_api_TunerSelectT.class, tunerSelectTConverter);
        addToNativeConverter(sdrplay_api_TunerSelectT.class, tunerSelectTConverter);
        
        EnumConverter<sdrplay_api_RspDuoModeT> rspDuoModeTConverter = new NullAwareEnumConverter<>(sdrplay_api_RspDuoModeT.class);
        addFromNativeConverter(sdrplay_api_RspDuoModeT.class, rspDuoModeTConverter);
        addToNativeConverter(sdrplay_api_RspDuoModeT.class, rspDuoModeTConverter);
        
        EnumConverter<sdrplay_api_EventT> eventTConverter = new NullAwareEnumConverter<>(sdrplay_api_EventT.class);
        addFromNativeConverter(sdrplay_api_EventT.class, eventTConverter);
        addToNativeConverter(sdrplay_api_EventT.class, eventTConverter);
        
        EnumConverter<sdrplay_api_PowerOverloadCbEventIdT> powerOverloadCbEventIdTConverter = new NullAwareEnumConverter<>(sdrplay_api_PowerOverloadCbEventIdT.class);
        addFromNativeConverter(sdrplay_api_PowerOverloadCbEventIdT.class, powerOverloadCbEventIdTConverter);
        addToNativeConverter(sdrplay_api_PowerOverloadCbEventIdT.class, powerOverloadCbEventIdTConverter);
        
        EnumConverter<sdrplay_api_RspDuoModeCbEventIdT> rspDuoModeCbEventIdTConverter = new NullAwareEnumConverter<>(sdrplay_api_RspDuoModeCbEventIdT.class);
        addFromNativeConverter(sdrplay_api_RspDuoModeCbEventIdT.class, rspDuoModeCbEventIdTConverter);
        addToNativeConverter(sdrplay_api_RspDuoModeCbEventIdT.class, rspDuoModeCbEventIdTConverter);
    }
    
    /**
     * Avoids NPEs by returning 0 when the input is null.  (This seems to be done normally
     * by JNA if the field is an int instead of an enum anyway).
     * @param <U> 
     */
    private class NullAwareEnumConverter<U extends Enum<U>> extends EnumConverter<U> {
        
        private final Class<U> clazz;
        
        public NullAwareEnumConverter(Class<U> clazz) {
            super(clazz);
            this.clazz = clazz;
        }
        
        @Override
        public Integer toNative(Object input, ToNativeContext context) {
        U t = clazz.cast(input);

        return t == null ? 0 : t.ordinal();
    }
        
    }
}
