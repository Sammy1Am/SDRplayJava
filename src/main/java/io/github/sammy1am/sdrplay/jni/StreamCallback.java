/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.sammy1am.sdrplay.jni;

/**
 *
 * @author Sam
 */
public class StreamCallback {
    public void call(short[] xi, short[] xq, int numSamples, int Reset) {
        System.out.println("Got one!");
    }
}
