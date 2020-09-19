// Include this to make SWIG happy with SDRplayAPI
#define _SDRPLAY_DLL_QUALIFIER

%module sdrplay_api
%{
    #include "sdrplay_api.h"
%}

%include "enums.swg"

%include cpointer.i
%pointer_functions(float, floatp);
%pointer_functions(unsigned int, uintp);
%pointer_functions(sdrplay_api_CallbackFnsT, callbackFnsp);

%include carrays.i
%array_class(sdrplay_api_DeviceT, deviceTArray)

%include "sdrplay_api.h"
