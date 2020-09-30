package io.github.sammy1am.sdrplay;

import io.github.sammy1am.sdrplay.jnr.SDRplayAPIJNR.DeviceT;
import io.github.sammy1am.sdrplay.jnr.SDRplayAPIJNR.ErrT;

/**
 * Helper class to wrap SDRplay API error codes into proper Java exceptions.
 * @author Sammy1Am
 */
public class ApiException extends RuntimeException {
    
    private final ErrT errorCode;
    
    protected ApiException(ErrT ec, String message) {
        super(message);
        errorCode = ec;
    }
    
    public ErrT getErrorCode() {
        return errorCode;
    }
    
    public static void checkErrorCode(ErrT errorCode) {
        checkErrorCode(errorCode, null);
    }
    
    public static void checkErrorCode(ErrT errorCode, DeviceT device) {
        if (ErrT.Success == errorCode) {
            return; // No error, we're good.
        }
        
        String errorMessage = ""; // TODO fill this out
        String additionalInfo = ""; //TODO Get additional error info from API
        
//        try {
//            errorMessage = SDRPlayAPI.INSTANCE.sdrplay_api_GetErrorString(errorCode);
//        } catch (Exception ex) {
//            errorMessage = "No Description Available";
//        }

        switch(errorCode) {
            case Fail:
                throw new FailException(errorMessage);
            case InvalidParam:
                throw new InvalidParamException(errorMessage);
            case OutOfRange:
                throw new OutOfRangeException(errorMessage);
            case GainUpdateError:
                throw new GainUpdateErrorException(errorMessage);
            case RfUpdateError:
                throw new RfUpdateErrorException(errorMessage);
            case FsUpdateError:
                throw new FsUpdateErrorException(errorMessage);
            case HwError:
                throw new HwErrorException(errorMessage);
            case AliasingError:
                throw new AliasingErrorException(errorMessage);
            case AlreadyInitialised:
                throw new AlreadyInitialisedException(errorMessage);
            case NotInitialised:
                throw new NotInitialisedException(errorMessage);
            case NotEnabled:
                throw new NotEnabledException(errorMessage);
            case HwVerError:
                throw new HwVerErrorException(errorMessage);
            case OutOfMemError:
                throw new OutOfMemErrorException(errorMessage);
            case ServiceNotResponding:
                throw new ServiceNotRespondingException(errorMessage);
            case StartPending:
                throw new StartPendingException(errorMessage);
            case StopPending:
                throw new StopPendingException(errorMessage);
            case InvalidMode:
                throw new InvalidModeException(errorMessage);
            case FailedVerification1:
                throw new FailedVerification1Exception(errorMessage);
            case FailedVerification2:
                throw new FailedVerification2Exception(errorMessage);
            case FailedVerification3:
                throw new FailedVerification3Exception(errorMessage);
            case FailedVerification4:
                throw new FailedVerification4Exception(errorMessage);
            case FailedVerification5:
                throw new FailedVerification5Exception(errorMessage);
            case FailedVerification6:
                throw new FailedVerification6Exception(errorMessage);
            case InvalidServiceVersion:
                throw new InvalidServiceVersionException(errorMessage);
            default:
                throw new ApiException(errorCode, errorMessage);
        }
    }
    
    public static class AlreadyInitializedException extends ApiException {
        public AlreadyInitializedException(String message) {
            super(ErrT.AlreadyInitialised, message);
        }
    }
    
    public static class FailException extends ApiException {

        public FailException(String message) {
            super(ErrT.Fail, message);
        }
    }

    public static class InvalidParamException extends ApiException {

        public InvalidParamException(String message) {
            super(ErrT.InvalidParam, message);
        }
    }

    public static class OutOfRangeException extends ApiException {

        public OutOfRangeException(String message) {
            super(ErrT.OutOfRange, message);
        }
    }

    public static class GainUpdateErrorException extends ApiException {

        public GainUpdateErrorException(String message) {
            super(ErrT.GainUpdateError, message);
        }
    }

    public static class RfUpdateErrorException extends ApiException {

        public RfUpdateErrorException(String message) {
            super(ErrT.RfUpdateError, message);
        }
    }

    public static class FsUpdateErrorException extends ApiException {

        public FsUpdateErrorException(String message) {
            super(ErrT.FsUpdateError, message);
        }
    }

    public static class HwErrorException extends ApiException {

        public HwErrorException(String message) {
            super(ErrT.HwError, message);
        }
    }

    public static class AliasingErrorException extends ApiException {

        public AliasingErrorException(String message) {
            super(ErrT.AliasingError, message);
        }
    }

    public static class AlreadyInitialisedException extends ApiException {

        public AlreadyInitialisedException(String message) {
            super(ErrT.AlreadyInitialised, message);
        }
    }

    public static class NotInitialisedException extends ApiException {

        public NotInitialisedException(String message) {
            super(ErrT.NotInitialised, message);
        }
    }

    public static class NotEnabledException extends ApiException {

        public NotEnabledException(String message) {
            super(ErrT.NotEnabled, message);
        }
    }

    public static class HwVerErrorException extends ApiException {

        public HwVerErrorException(String message) {
            super(ErrT.HwVerError, message);
        }
    }

    public static class OutOfMemErrorException extends ApiException {

        public OutOfMemErrorException(String message) {
            super(ErrT.OutOfMemError, message);
        }
    }

    public static class ServiceNotRespondingException extends ApiException {

        public ServiceNotRespondingException(String message) {
            super(ErrT.ServiceNotResponding, message);
        }
    }

    public static class StartPendingException extends ApiException {

        public StartPendingException(String message) {
            super(ErrT.StartPending, message);
        }
    }

    public static class StopPendingException extends ApiException {

        public StopPendingException(String message) {
            super(ErrT.StopPending, message);
        }
    }

    public static class InvalidModeException extends ApiException {

        public InvalidModeException(String message) {
            super(ErrT.InvalidMode, message);
        }
    }

    public static class FailedVerification1Exception extends ApiException {

        public FailedVerification1Exception(String message) {
            super(ErrT.FailedVerification1, message);
        }
    }

    public static class FailedVerification2Exception extends ApiException {

        public FailedVerification2Exception(String message) {
            super(ErrT.FailedVerification2, message);
        }
    }

    public static class FailedVerification3Exception extends ApiException {

        public FailedVerification3Exception(String message) {
            super(ErrT.FailedVerification3, message);
        }
    }

    public static class FailedVerification4Exception extends ApiException {

        public FailedVerification4Exception(String message) {
            super(ErrT.FailedVerification4, message);
        }
    }

    public static class FailedVerification5Exception extends ApiException {

        public FailedVerification5Exception(String message) {
            super(ErrT.FailedVerification5, message);
        }
    }

    public static class FailedVerification6Exception extends ApiException {

        public FailedVerification6Exception(String message) {
            super(ErrT.FailedVerification6, message);
        }
    }

    public static class InvalidServiceVersionException extends ApiException {

        public InvalidServiceVersionException(String message) {
            super(ErrT.InvalidServiceVersion, message);
        }
    }
}
