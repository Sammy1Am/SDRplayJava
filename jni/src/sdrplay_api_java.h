#ifndef SDRPLAY_API_JAVA
#define SDRPLAY_API_JAVA

#include "sdrplay_api.h"
#include <jni.h>

void streamCallbackA(short *xi, short *xq, sdrplay_api_StreamCbParamsT *params, unsigned int numSamples, unsigned int reset, void *cbContext);
void streamCallbackB(short *xi, short *xq, sdrplay_api_StreamCbParamsT *params, unsigned int numSamples, unsigned int reset, void *cbContext);
void eventCallback(sdrplay_api_EventT eventId, sdrplay_api_TunerSelectT tuner, sdrplay_api_EventParamsT *params, void *cbContext);

#endif