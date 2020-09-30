package io.github.sammy1am.sdrplay.api;

import io.github.sammy1am.sdrplay.jnr.DeviceT;
import jnr.ffi.util.EnumMapper;

/**
 *
 * @author Sam
 */
public class ApiException extends RuntimeException {
    
    public static enum ErrT implements EnumMapper.IntegerEnum {
        sdrplay_api_Success(0),
        sdrplay_api_Fail(1),
        sdrplay_api_InvalidParam(2),
        sdrplay_api_OutOfRange(3),
        sdrplay_api_GainUpdateError(4),
        sdrplay_api_RfUpdateError(5),
        sdrplay_api_FsUpdateError(6),
        sdrplay_api_HwError(7),
        sdrplay_api_AliasingError(8),
        sdrplay_api_AlreadyInitialised(9),
        sdrplay_api_NotInitialised(10),
        sdrplay_api_NotEnabled(11),
        sdrplay_api_HwVerError(12),
        sdrplay_api_OutOfMemError(13),
        sdrplay_api_ServiceNotResponding(14),
        sdrplay_api_StartPending(15),
        sdrplay_api_StopPending(16),
        sdrplay_api_InvalidMode(17),
        sdrplay_api_FailedVerification1(18),
        sdrplay_api_FailedVerification2(19),
        sdrplay_api_FailedVerification3(20),
        sdrplay_api_FailedVerification4(21),
        sdrplay_api_FailedVerification5(22),
        sdrplay_api_FailedVerification6(23),
        sdrplay_api_InvalidServiceVersion(24);

        private final int val;

        ErrT(int val) {
            this.val = val;
        }
        
        @Override
        public int intValue() {
            return val;
        }
    }
    
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
        if (ErrT.sdrplay_api_Success == errorCode) {
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
            super(ErrT.sdrplay_api_AlreadyInitialised, message);
        }
    }
    
    public static class FailException extends ApiException {

        public FailException(String message) {
            super(ErrT.sdrplay_api_Fail, message);
        }
    }

    public static class InvalidParamException extends ApiException {

        public InvalidParamException(String message) {
            super(ErrT.sdrplay_api_InvalidParam, message);
        }
    }

    public static class OutOfRangeException extends ApiException {

        public OutOfRangeException(String message) {
            super(ErrT.sdrplay_api_OutOfRange, message);
        }
    }

    public static class GainUpdateErrorException extends ApiException {

        public GainUpdateErrorException(String message) {
            super(ErrT.sdrplay_api_GainUpdateError, message);
        }
    }

    public static class RfUpdateErrorException extends ApiException {

        public RfUpdateErrorException(String message) {
            super(ErrT.sdrplay_api_RfUpdateError, message);
        }
    }

    public static class FsUpdateErrorException extends ApiException {

        public FsUpdateErrorException(String message) {
            super(ErrT.sdrplay_api_FsUpdateError, message);
        }
    }

    public static class HwErrorException extends ApiException {

        public HwErrorException(String message) {
            super(ErrT.sdrplay_api_HwError, message);
        }
    }

    public static class AliasingErrorException extends ApiException {

        public AliasingErrorException(String message) {
            super(ErrT.sdrplay_api_AliasingError, message);
        }
    }

    public static class AlreadyInitialisedException extends ApiException {

        public AlreadyInitialisedException(String message) {
            super(ErrT.sdrplay_api_AlreadyInitialised, message);
        }
    }

    public static class NotInitialisedException extends ApiException {

        public NotInitialisedException(String message) {
            super(ErrT.sdrplay_api_NotInitialised, message);
        }
    }

    public static class NotEnabledException extends ApiException {

        public NotEnabledException(String message) {
            super(ErrT.sdrplay_api_NotEnabled, message);
        }
    }

    public static class HwVerErrorException extends ApiException {

        public HwVerErrorException(String message) {
            super(ErrT.sdrplay_api_HwVerError, message);
        }
    }

    public static class OutOfMemErrorException extends ApiException {

        public OutOfMemErrorException(String message) {
            super(ErrT.sdrplay_api_OutOfMemError, message);
        }
    }

    public static class ServiceNotRespondingException extends ApiException {

        public ServiceNotRespondingException(String message) {
            super(ErrT.sdrplay_api_ServiceNotResponding, message);
        }
    }

    public static class StartPendingException extends ApiException {

        public StartPendingException(String message) {
            super(ErrT.sdrplay_api_StartPending, message);
        }
    }

    public static class StopPendingException extends ApiException {

        public StopPendingException(String message) {
            super(ErrT.sdrplay_api_StopPending, message);
        }
    }

    public static class InvalidModeException extends ApiException {

        public InvalidModeException(String message) {
            super(ErrT.sdrplay_api_InvalidMode, message);
        }
    }

    public static class FailedVerification1Exception extends ApiException {

        public FailedVerification1Exception(String message) {
            super(ErrT.sdrplay_api_FailedVerification1, message);
        }
    }

    public static class FailedVerification2Exception extends ApiException {

        public FailedVerification2Exception(String message) {
            super(ErrT.sdrplay_api_FailedVerification2, message);
        }
    }

    public static class FailedVerification3Exception extends ApiException {

        public FailedVerification3Exception(String message) {
            super(ErrT.sdrplay_api_FailedVerification3, message);
        }
    }

    public static class FailedVerification4Exception extends ApiException {

        public FailedVerification4Exception(String message) {
            super(ErrT.sdrplay_api_FailedVerification4, message);
        }
    }

    public static class FailedVerification5Exception extends ApiException {

        public FailedVerification5Exception(String message) {
            super(ErrT.sdrplay_api_FailedVerification5, message);
        }
    }

    public static class FailedVerification6Exception extends ApiException {

        public FailedVerification6Exception(String message) {
            super(ErrT.sdrplay_api_FailedVerification6, message);
        }
    }

    public static class InvalidServiceVersionException extends ApiException {

        public InvalidServiceVersionException(String message) {
            super(ErrT.sdrplay_api_InvalidServiceVersion, message);
        }
    }
}
