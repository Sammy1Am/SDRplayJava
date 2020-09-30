/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.sammy1am.sdrplay.jnr;

import jnr.ffi.Struct;
import jnr.ffi.Runtime;
import jnr.ffi.util.EnumMapper;

/**
 * JNR class for TunerParamsT and sub-structures (sdrplay_api_tuner.h).
 * @author Sammy1Am
 */
public class TunerParamsT extends Struct{
    Unsigned32 bwType;
        
    public TunerParamsT(final Runtime runtime) {
        super(runtime);
        bwType = new Unsigned32();
    }
    
    public static enum TunerSelectT implements EnumMapper.IntegerEnum {
        Tuner_Neither(0),
        Tuner_A(1),
        Tuner_B(2),
        Tuner_Both(3);

        private final int val;

        TunerSelectT(int val) {
            this.val = val;
        }
        
        @Override
        public int intValue() {
            return val;
        }
    }
}
