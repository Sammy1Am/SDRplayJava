/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.sammy1am.sdrplay.api;

import com.sun.jna.Pointer;
import io.github.sammy1am.sdrplay.api.SDRPlayAPI.sdrplay_api_StreamCallback_t;

/**
 *
 * @author Sam
 */
public abstract class StreamConsumer implements sdrplay_api_StreamCallback_t {

    //@Override
    public void apply(Pointer xi, Pointer xq, SDRPlayAPI.sdrplay_api_StreamCbParamsT params, int numSamples, int reset, Pointer cbContext) {
        consume(xi.getShortArray(0, numSamples), xq.getShortArray(0, numSamples), params, numSamples, reset, cbContext);
    }
    
    public abstract void consume(short[] iSamples, short[] qSamples, SDRPlayAPI.sdrplay_api_StreamCbParamsT params, int numSamples, int reset, Pointer cbContext);
    
}
