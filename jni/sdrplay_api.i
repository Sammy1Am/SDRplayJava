%module sdrplay_api
%{
    #include "sdrplay_api.h"
%}

%include "enums.swg"

%include cpointer.i
%pointer_functions(float, floatp);
%pointer_functions(unsigned int, uintp);

%include carrays.i
%array_class(sdrplay_api_DeviceT, deviceTArray)

%include "sdrplay_api.h"