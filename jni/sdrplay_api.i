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

%include carrays.i
%array_class(sdrplay_api_DeviceT, deviceTArray)

%typemap(javacode) HANDLE * %{
  /** Not recommended, but couldn't find a workaround */
  public long getPointer() {
      return swigCPtr;
  }
%}

//%typemap(jtype) HANDLE dev "long";

%include "sdrplay_api.h"
