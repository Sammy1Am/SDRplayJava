#include "sdrplay_api_java.h"

class IStreamConsumer
{
    public:
        virtual void call(short *xi, short *xq, sdrplay_api_StreamCbParamsT *params, unsigned int numSamples, unsigned int reset, void *cbContext) = 0;
};

class IEventConsumer {
    public:
        virtual void call(sdrplay_api_EventT eventId, sdrplay_api_TunerSelectT tuner, sdrplay_api_EventParamsT *params, void *cbContext) = 0;
};

void initDevice(HANDLE dev, IStreamConsumer &streamA, IStreamConsumer &streamB, IEventConsumer &event, void *cbContext){
    sdrplay_api_CallbackFnsT callbacks;
    callbacks.StreamACbFn = streamA.*call;
    callbacks.StreamBCbFn = streamB.call;
    callbacks.EventCbFn = event.call;

    sdrplay_api_Init(dev, callbacks &, cbContext);
};