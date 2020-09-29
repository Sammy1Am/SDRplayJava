/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.sammy1am.sdrplay.jnr;

import jnr.ffi.NativeType;
import jnr.ffi.Struct;

/**
 * With much inspiration from: SerCeMan/jnr-fuse
 */
public class BaseStruct extends Struct {
    protected BaseStruct(jnr.ffi.Runtime runtime) {
        super(runtime);
    }

    public class Func<T> extends AbstractMember {
        private final Class<? extends T> closureClass;
        private T instance;

        public Func(Class<? extends T> closureClass) {
            super(NativeType.ADDRESS);
            this.closureClass = closureClass;
        }

        public final void set(T value) {
            getMemory().putPointer(offset(), getRuntime().getClosureManager().getClosurePointer(closureClass, instance = value));
        }
    }

    protected final <T> Func<T> func(Class<T> closureClass) {
        return new Func<>(closureClass);
    }
}
