/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.sammy1am.sdrplay.jna;


import com.sun.jna.DefaultTypeMapper;
import com.sun.jna.ToNativeContext;
import com.sun.jna.platform.EnumConverter;
import io.github.sammy1am.sdrplay.jna.SDRPlayAPI.*;

/**
 *
 * @author Sam
 */
public class SDRPlayTypeMapper extends DefaultTypeMapper {
    public SDRPlayTypeMapper() {
        EnumConverter<sdrplay_api_ErrT> errTConverter = new EnumConverter<>(sdrplay_api_ErrT.class);
        addFromNativeConverter(sdrplay_api_ErrT.class, errTConverter);
        addToNativeConverter(sdrplay_api_ErrT.class, errTConverter);
        
        addConverterForEnum(sdrplay_api_ReasonForUpdateT.class);
        addConverterForEnum(sdrplay_api_ReasonForUpdateExtension1T.class);
        addConverterForEnum(sdrplay_api_DbgLvl_t.class);
        
        
        //Device
        addConverterForEnum(sdrplay_api_RspDuoModeT.class);
        addConverterForEnum(sdrplay_api_EventT.class);
        addConverterForEnum(sdrplay_api_PowerOverloadCbEventIdT.class);
        addConverterForEnum(sdrplay_api_RspDuoModeCbEventIdT.class);
        
        addConverterForEnum(sdrplay_api_TransferModeT.class);
        addConverterForEnum(sdrplay_api_RspDx_AntennaSelectT.class);
        addConverterForEnum(sdrplay_api_Rsp2_AntennaSelectT.class);
        addConverterForEnum(sdrplay_api_Rsp2_AmPortSelectT.class);
        addConverterForEnum(sdrplay_api_RspDuo_AmPortSelectT.class);
        addConverterForEnum(sdrplay_api_RspDx_HdrModeBwT.class);
        
        
        //Tuner
        addConverterForEnum(sdrplay_api_Bw_MHzT.class);
        addConverterForEnum(sdrplay_api_If_kHzT.class);
        addConverterForEnum(sdrplay_api_LoModeT.class);
        addConverterForEnum(sdrplay_api_MinGainReductionT.class);
        addConverterForEnum(sdrplay_api_TunerSelectT.class);
        
        
        //Control
        addConverterForEnum(sdrplay_api_AgcControlT.class);
        addConverterForEnum(sdrplay_api_AdsbModeT.class);
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
