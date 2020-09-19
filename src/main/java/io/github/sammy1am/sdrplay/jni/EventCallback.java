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
public abstract class EventCallback {
    public void call(int thing1, int thing2) {
        System.out.println("Got one!");
    }
}
