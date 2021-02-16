# SDRplayJava
 
A Java wrapper for the [SDRplay API v3](https://www.sdrplay.com/) using [JNR-FFI](https://github.com/jnr/jnr-ffi/).

## Notes
* This project provides two Java interfaces: A thin, JNR-based wrapper to the raw API calls; and a "more Java-like" wrapper that doesn't expose any JNR.
  * The JNR wrapper will provide access to all the functionality of the API.
  * The Java-like wrapper will only expose limited functionality at first to the extent that I'm able to test it.
* I only have an RSP1, so it's possible some of the features that work for me may not work on other hardware.
* The JNR wrapper is largely complete except for some model-specifc features.  The friendly Java API is less complete and only contains a few core controls, but should be sufficient for basic use.
* http://jnrproject.org/jnr-ffi/apidocs/index.html NDH: found these which may or may not be useful

### Modifications v0.7.0 Neil Harvey 2021
* Updated structs to cover all device parameters for all API supported SDRplay devices
* Provided methods for updating the following parameters:
 * Automatic Gain Control (AGC)
 * Decimation
 * DC Offset
 * Broadcast FM Notch (RSP1A only)
 * Digital Audio Broadcast (DAB) Notch (RSP1A only)
 * Bias-T (RSP1A only) 

#### With Thanks To
The [SerCeMan/jnr-fuse](https://github.com/SerCeMan/jnr-fuse) project for being an excellent example of working, complex JNR code.
