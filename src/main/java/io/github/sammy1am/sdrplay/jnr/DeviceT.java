package io.github.sammy1am.sdrplay.jnr;

import jnr.ffi.Struct;

/**
 *
 */
public class DeviceT extends Struct {
    public jnr.ffi.Struct.Unsigned8[] SerNo = array(new Struct.Unsigned8[64]);
    public jnr.ffi.Struct.Unsigned8 hwVer = new Struct.Unsigned8();
    public Struct.Signed32 tuner = new Struct.Signed32();
    public Struct.Signed32 rspDuoMode = new Struct.Signed32();
    public Struct.Double rspDuoSampleFreq = new Struct.Double();
    public jnr.ffi.Struct.Pointer dev = new Struct.Pointer();

    public DeviceT(final jnr.ffi.Runtime runtime) {
        super(runtime);
    }
}
