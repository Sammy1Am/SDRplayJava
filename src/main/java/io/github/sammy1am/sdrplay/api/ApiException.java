/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.sammy1am.sdrplay.api;

import io.github.sammy1am.sdrplay.jna.SDRPlayAPI;
import io.github.sammy1am.sdrplay.jna.SDRPlayAPI.sdrplay_api_DeviceT;
import io.github.sammy1am.sdrplay.jna.SDRPlayAPI.sdrplay_api_ErrT;
import static io.github.sammy1am.sdrplay.jna.SDRPlayAPI.sdrplay_api_ErrT.*;

/**
 *
 * @author Sam
 */
public class ApiException extends RuntimeException {
    
    private final sdrplay_api_ErrT errorCode;
    
    public ApiException(sdrplay_api_ErrT ec, String message) {
        super(message);
        errorCode = ec;
    }
    
    public sdrplay_api_ErrT getErrorCode() {
        return errorCode;
    }
    
    public static void checkErrorCode(sdrplay_api_ErrT errorCode) {
        checkErrorCode(errorCode, null);
    }
    
    public static void checkErrorCode(sdrplay_api_ErrT errorCode, sdrplay_api_DeviceT device) {
        if (sdrplay_api_Success == errorCode) {
            return; // No error, we're good.
        }
        
        String errorMessage = "";
        String additionalInfo = ""; //TODO Get additional error info from API
        
        try {
            errorMessage = SDRPlayAPI.INSTANCE.sdrplay_api_GetErrorString(errorCode);
        } catch (Exception ex) {
            errorMessage = "No Description Available";
        }

        switch(errorCode) {
            case sdrplay_api_Fail:
                throw new FailException(errorMessage);
            case sdrplay_api_InvalidParam:
                throw new InvalidParamException(errorMessage);
            case sdrplay_api_OutOfRange:
                throw new OutOfRangeException(errorMessage);
            case sdrplay_api_GainUpdateError:
                throw new GainUpdateErrorException(errorMessage);
            case sdrplay_api_RfUpdateError:
                throw new RfUpdateErrorException(errorMessage);
            case sdrplay_api_FsUpdateError:
                throw new FsUpdateErrorException(errorMessage);
            case sdrplay_api_HwError:
                throw new HwErrorException(errorMessage);
            case sdrplay_api_AliasingError:
                throw new AliasingErrorException(errorMessage);
            case sdrplay_api_AlreadyInitialised:
                throw new AlreadyInitialisedException(errorMessage);
            case sdrplay_api_NotInitialised:
                throw new NotInitialisedException(errorMessage);
            case sdrplay_api_NotEnabled:
                throw new NotEnabledException(errorMessage);
            case sdrplay_api_HwVerError:
                throw new HwVerErrorException(errorMessage);
            case sdrplay_api_OutOfMemError:
                throw new OutOfMemErrorException(errorMessage);
            case sdrplay_api_ServiceNotResponding:
                throw new ServiceNotRespondingException(errorMessage);
            case sdrplay_api_StartPending:
                throw new StartPendingException(errorMessage);
            case sdrplay_api_StopPending:
                throw new StopPendingException(errorMessage);
            case sdrplay_api_InvalidMode:
                throw new InvalidModeException(errorMessage);
            case sdrplay_api_FailedVerification1:
                throw new FailedVerification1Exception(errorMessage);
            case sdrplay_api_FailedVerification2:
                throw new FailedVerification2Exception(errorMessage);
            case sdrplay_api_FailedVerification3:
                throw new FailedVerification3Exception(errorMessage);
            case sdrplay_api_FailedVerification4:
                throw new FailedVerification4Exception(errorMessage);
            case sdrplay_api_FailedVerification5:
                throw new FailedVerification5Exception(errorMessage);
            case sdrplay_api_FailedVerification6:
                throw new FailedVerification6Exception(errorMessage);
            case sdrplay_api_InvalidServiceVersion:
                throw new InvalidServiceVersionException(errorMessage);
            default:
                throw new ApiException(errorCode, errorMessage);
        }
    }
    
    public static class AlreadyInitializedException extends ApiException {
        public AlreadyInitializedException(String message) {
            super(sdrplay_api_ErrT.sdrplay_api_AlreadyInitialised, message);
        }
    }
    
    public static class FailException extends ApiException {

        public FailException(String message) {
            super(sdrplay_api_ErrT.sdrplay_api_Fail, message);
        }
    }

    public static class InvalidParamException extends ApiException {

        public InvalidParamException(String message) {
            super(sdrplay_api_ErrT.sdrplay_api_InvalidParam, message);
        }
    }

    public static class OutOfRangeException extends ApiException {

        public OutOfRangeException(String message) {
            super(sdrplay_api_ErrT.sdrplay_api_OutOfRange, message);
        }
    }

    public static class GainUpdateErrorException extends ApiException {

        public GainUpdateErrorException(String message) {
            super(sdrplay_api_ErrT.sdrplay_api_GainUpdateError, message);
        }
    }

    public static class RfUpdateErrorException extends ApiException {

        public RfUpdateErrorException(String message) {
            super(sdrplay_api_ErrT.sdrplay_api_RfUpdateError, message);
        }
    }

    public static class FsUpdateErrorException extends ApiException {

        public FsUpdateErrorException(String message) {
            super(sdrplay_api_ErrT.sdrplay_api_FsUpdateError, message);
        }
    }

    public static class HwErrorException extends ApiException {

        public HwErrorException(String message) {
            super(sdrplay_api_ErrT.sdrplay_api_HwError, message);
        }
    }

    public static class AliasingErrorException extends ApiException {

        public AliasingErrorException(String message) {
            super(sdrplay_api_ErrT.sdrplay_api_AliasingError, message);
        }
    }

    public static class AlreadyInitialisedException extends ApiException {

        public AlreadyInitialisedException(String message) {
            super(sdrplay_api_ErrT.sdrplay_api_AlreadyInitialised, message);
        }
    }

    public static class NotInitialisedException extends ApiException {

        public NotInitialisedException(String message) {
            super(sdrplay_api_ErrT.sdrplay_api_NotInitialised, message);
        }
    }

    public static class NotEnabledException extends ApiException {

        public NotEnabledException(String message) {
            super(sdrplay_api_ErrT.sdrplay_api_NotEnabled, message);
        }
    }

    public static class HwVerErrorException extends ApiException {

        public HwVerErrorException(String message) {
            super(sdrplay_api_ErrT.sdrplay_api_HwVerError, message);
        }
    }

    public static class OutOfMemErrorException extends ApiException {

        public OutOfMemErrorException(String message) {
            super(sdrplay_api_ErrT.sdrplay_api_OutOfMemError, message);
        }
    }

    public static class ServiceNotRespondingException extends ApiException {

        public ServiceNotRespondingException(String message) {
            super(sdrplay_api_ErrT.sdrplay_api_ServiceNotResponding, message);
        }
    }

    public static class StartPendingException extends ApiException {

        public StartPendingException(String message) {
            super(sdrplay_api_ErrT.sdrplay_api_StartPending, message);
        }
    }

    public static class StopPendingException extends ApiException {

        public StopPendingException(String message) {
            super(sdrplay_api_ErrT.sdrplay_api_StopPending, message);
        }
    }

    public static class InvalidModeException extends ApiException {

        public InvalidModeException(String message) {
            super(sdrplay_api_ErrT.sdrplay_api_InvalidMode, message);
        }
    }

    public static class FailedVerification1Exception extends ApiException {

        public FailedVerification1Exception(String message) {
            super(sdrplay_api_ErrT.sdrplay_api_FailedVerification1, message);
        }
    }

    public static class FailedVerification2Exception extends ApiException {

        public FailedVerification2Exception(String message) {
            super(sdrplay_api_ErrT.sdrplay_api_FailedVerification2, message);
        }
    }

    public static class FailedVerification3Exception extends ApiException {

        public FailedVerification3Exception(String message) {
            super(sdrplay_api_ErrT.sdrplay_api_FailedVerification3, message);
        }
    }

    public static class FailedVerification4Exception extends ApiException {

        public FailedVerification4Exception(String message) {
            super(sdrplay_api_ErrT.sdrplay_api_FailedVerification4, message);
        }
    }

    public static class FailedVerification5Exception extends ApiException {

        public FailedVerification5Exception(String message) {
            super(sdrplay_api_ErrT.sdrplay_api_FailedVerification5, message);
        }
    }

    public static class FailedVerification6Exception extends ApiException {

        public FailedVerification6Exception(String message) {
            super(sdrplay_api_ErrT.sdrplay_api_FailedVerification6, message);
        }
    }

    public static class InvalidServiceVersionException extends ApiException {

        public InvalidServiceVersionException(String message) {
            super(sdrplay_api_ErrT.sdrplay_api_InvalidServiceVersion, message);
        }
    }
}
