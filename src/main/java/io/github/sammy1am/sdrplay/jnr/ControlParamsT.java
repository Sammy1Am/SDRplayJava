/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.sammy1am.sdrplay.jnr;

import jnr.ffi.Struct;

/**
 * JNR class for ControlParamsT and sub-structures (sdrplay_api_control.h).
 * @author Sammy1Am
 */
public class ControlParamsT extends Struct {
    
    
    public ControlParamsT(final jnr.ffi.Runtime runtime) {
        super(runtime);
    }
}
