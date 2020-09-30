/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.sammy1am.sdrplay.jni;

import io.github.sammy1am.sdrplay.jni.swig.SWIGTYPE_p_HANDLE;
import io.github.sammy1am.sdrplay.jni.swig.sdrplay_api_ErrT;

/**
 *
 * @author Sam
 */
public class SDRplayAPI {
    
    public static sdrplay_api_ErrT initDevice(SWIGTYPE_p_HANDLE handle, StreamCallback streamA, StreamCallback streamB, EventCallback event) {
        return initDevice(handle.getPointer(), streamA, streamB, event);
    }
    
    private static native sdrplay_api_ErrT initDevice(long handle, StreamCallback streamA, StreamCallback streamB, EventCallback event);
    
}
