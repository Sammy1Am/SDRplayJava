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
import com.sun.jna.DefaultTypeMapper;
import com.sun.jna.ToNativeContext;
import com.sun.jna.platform.EnumConverter;
import io.github.sammy1am.sdrplay.api.SDRPlayAPI.sdrplay_api_RspDx_AntennaSelectT;
import io.github.sammy1am.sdrplay.api.SDRPlayAPI.sdrplay_api_TransferModeT;
import io.github.sammy1am.sdrplay.api.SDRPlayAPI.sdrplay_api_TunerSelectT;
import io.github.sammy1am.sdrplay.api.SDRPlayAPI.sdrplay_api_Bw_MHzT;
import io.github.sammy1am.sdrplay.api.SDRPlayAPI.sdrplay_api_If_kHzT;
import io.github.sammy1am.sdrplay.api.SDRPlayAPI.sdrplay_api_LoModeT;
import io.github.sammy1am.sdrplay.api.SDRPlayAPI.sdrplay_api_MinGainReductionT;

/**
 *
 * @author Sam
 */
public class SDRPlayTypeMapper extends DefaultTypeMapper {
    public SDRPlayTypeMapper() {
        EnumConverter<sdrplay_api_ErrT> errTConverter = new EnumConverter<>(sdrplay_api_ErrT.class);
        addFromNativeConverter(sdrplay_api_ErrT.class, errTConverter);
        addToNativeConverter(sdrplay_api_ErrT.class, errTConverter);
        
        
        addConverterForEnum(sdrplay_api_RspDuoModeT.class);
        addConverterForEnum(sdrplay_api_EventT.class);
        addConverterForEnum(sdrplay_api_PowerOverloadCbEventIdT.class);
        addConverterForEnum(sdrplay_api_RspDuoModeCbEventIdT.class);
        
        addConverterForEnum(sdrplay_api_TransferModeT.class);
        addConverterForEnum(sdrplay_api_RspDx_AntennaSelectT.class);
        
        addConverterForEnum(sdrplay_api_Bw_MHzT.class);
        addConverterForEnum(sdrplay_api_If_kHzT.class);
        addConverterForEnum(sdrplay_api_LoModeT.class);
        addConverterForEnum(sdrplay_api_MinGainReductionT.class);
        addConverterForEnum(sdrplay_api_TunerSelectT.class);
    }
    
    private <T extends Enum<T>> void addConverterForEnum(Class<T> enumClass) {
        EnumConverter<T> converter = new NullAwareEnumConverter<>(enumClass);
        addFromNativeConverter(enumClass, converter);
        addToNativeConverter(enumClass, converter);
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
