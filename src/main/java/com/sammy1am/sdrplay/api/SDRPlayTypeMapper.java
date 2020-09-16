/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sammy1am.sdrplay.api;

import com.sammy1am.sdrplay.api.SDRPlayAPI.sdrplay_api_ErrT;
import com.sammy1am.sdrplay.api.SDRPlayAPI.sdrplay_api_RspDuoModeT;
import com.sammy1am.sdrplay.api.SDRPlayAPI.sdrplay_api_TunerSelectT;
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
