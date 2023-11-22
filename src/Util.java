public class Util {
    public static PIDController.CONTROLLER_TYPE getControllerTypeByLabel(String label){
        switch(label){
            case "P":
                return PIDController.CONTROLLER_TYPE.P;

            case "PI":
                return PIDController.CONTROLLER_TYPE.PI;

            case "PID":
                return PIDController.CONTROLLER_TYPE.PID;

            case "I":
                return PIDController.CONTROLLER_TYPE.I;

            case "ID":
                return PIDController.CONTROLLER_TYPE.ID;

            case "D":
                return PIDController.CONTROLLER_TYPE.D;

            case "PD":
                return PIDController.CONTROLLER_TYPE.PD;

            default:
                return PIDController.CONTROLLER_TYPE.UNCONTROLLED;
        }
    }
}
