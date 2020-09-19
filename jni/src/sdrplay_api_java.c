#include "sdrplay_api_java.h"

JNIEXPORT jint JNICALL Java_io_github_sammy1am_sdrplay_jni_SDRplayAPI_initDevice(JNIEnv *env, jclass thisClass) {
    return 4;
}

JNIEXPORT void JNICALL Java_io_github_sammy1am_sdrplay_jni_SDRplayAPI_doTheThing(JNIEnv *env, jobject thisObject, jobject streamA, jobject streamB) {
    jclass scbClass = (*env)->FindClass(env, "io/github/sammy1am/sdrplay/jni/StreamCallback");
    jmethodID callMethodID = (*env)->GetMethodID(env, scbClass, "call", "([S[SI)V");

    short test1[16];
    short test2[16];
    int test3 = 7;

    (*env)->CallObjectMethod(env, streamA, callMethodID, test1, test2, test3);
}

void streamCallbackA(short *xi, short *xq, sdrplay_api_StreamCbParamsT *params, unsigned int numSamples, unsigned int reset, void *cbContext){};
void streamCallbackB(short *xi, short *xq, sdrplay_api_StreamCbParamsT *params, unsigned int numSamples, unsigned int reset, void *cbContext){};
void eventCallback(sdrplay_api_EventT eventId, sdrplay_api_TunerSelectT tuner, sdrplay_api_EventParamsT *params, void *cbContext){};