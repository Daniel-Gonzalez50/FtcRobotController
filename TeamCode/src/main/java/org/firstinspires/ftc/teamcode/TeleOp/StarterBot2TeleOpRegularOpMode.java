package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;

@TeleOp()
public class StarterBot2TeleOpRegularOpMode extends OpMode{

    DcMotor armMotor = null;
    CRServo intake = null;
    Servo wrist = null;
    IMU imu = null;
    DcMotor frontLeftMotor = null;
    DcMotor backLeftMotor = null;
    DcMotor frontRightMotor = null;
    DcMotor backRightMotor = null;

    final double ARM_TICKS_PER_DEGREE = 19.7924893140647;

    final double ARM_COLLAPSED_INTO_ROBOT  = 0;
    final double ARM_COLLECT               = 250 * ARM_TICKS_PER_DEGREE;
    final double ARM_CLEAR_BARRIER         = 230 * ARM_TICKS_PER_DEGREE;
    final double ARM_SCORE_SPECIMEN        = 160 * ARM_TICKS_PER_DEGREE;
    final double ARM_SCORE_SAMPLE_IN_LOW   = 160 * ARM_TICKS_PER_DEGREE;
    final double ARM_ATTACH_HANGING_HOOK   = 120 * ARM_TICKS_PER_DEGREE;
    final double ARM_WINCH_ROBOT           = 15  * ARM_TICKS_PER_DEGREE;

    final double INTAKE_COLLECT    = -1.0;
    final double INTAKE_OFF        =  0.0;
    final double INTAKE_DEPOSIT    =  0.5;

    final double WRIST_FOLDED_IN   = 0;
    final double WRIST_FOLDED_OUT  = 0.3;

    final double FUDGE_FACTOR = 15 * ARM_TICKS_PER_DEGREE;

    double armPosition = (int) ARM_WINCH_ROBOT;
    double armPositionFudgeFactor;

    @Override
    public void init(){
        frontRightMotor = hardwareMap.dcMotor.get("motorFR");
        frontLeftMotor = hardwareMap.dcMotor.get("motorFL");
        backRightMotor = hardwareMap.dcMotor.get("motorBR");
        backLeftMotor = hardwareMap.dcMotor.get("motorBL");

        armMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        armMotor.setTargetPosition(0);
        ((DcMotorEx) armMotor).setCurrentAlert(5, CurrentUnit.AMPS);

        frontRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        backRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        armMotor = hardwareMap.get(DcMotor.class, "arm");
        intake = hardwareMap.get(CRServo.class, "intake");
        wrist  = hardwareMap.get(Servo.class, "wrist");

        intake.setPower(INTAKE_OFF);
        wrist.setPosition(WRIST_FOLDED_IN);

        imu = hardwareMap.get(IMU.class, "imu");

        IMU.Parameters parameters = new IMU.Parameters(new RevHubOrientationOnRobot(RevHubOrientationOnRobot.LogoFacingDirection.UP, RevHubOrientationOnRobot.UsbFacingDirection.FORWARD));

        imu.initialize(parameters);

        telemetry.addLine("Robot Ready.");
        telemetry.update();
    }

    @Override
    public void start(){

        wrist.setPosition(WRIST_FOLDED_OUT);
    }

    @Override
    public void loop(){

        double y = gamepad1.left_stick_y;
        double x = -gamepad1.left_stick_x;
        double rx = -gamepad1.right_stick_x;

        if(gamepad1.options) {
            imu.resetYaw();
        }

        double botHeading = imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);

        double rotX = x * Math.cos(-botHeading) - y * Math.sin(-botHeading);
        double rotY = x * Math.sin(-botHeading) + y * Math.cos(-botHeading);

        rotX = rotX * 1.1;  // Counteract imperfect strafing

        double denominator = Math.max(Math.abs(rotY) + Math.abs(rotX) + Math.abs(rx), 1);
        double frontLeftPower = (rotY + rotX + rx) / denominator;
        double backLeftPower = (rotY - rotX + rx) / denominator;
        double frontRightPower = (rotY - rotX - rx) / denominator;
        double backRightPower = (rotY + rotX - rx) / denominator;

        frontLeftMotor.setPower(frontLeftPower);
        backLeftMotor.setPower(backLeftPower);
        frontRightMotor.setPower(frontRightPower);
        backRightMotor.setPower(backRightPower);

        if (gamepad1.a) {
            intake.setPower(INTAKE_COLLECT);
        }
        else if (gamepad1.x) {
            intake.setPower(INTAKE_OFF);
        }
        else if (gamepad1.b) {
            intake.setPower(INTAKE_DEPOSIT);
        }

        armPositionFudgeFactor = FUDGE_FACTOR * (gamepad1.right_trigger + (-gamepad1.left_trigger));

        if(gamepad1.right_bumper){
            armPosition = ARM_COLLECT;
            wrist.setPosition(WRIST_FOLDED_OUT);
            intake.setPower(INTAKE_COLLECT);
        }

        else if (gamepad1.left_bumper){
            armPosition = ARM_CLEAR_BARRIER;
        }

        else if (gamepad1.y){
            armPosition = ARM_SCORE_SAMPLE_IN_LOW;
        }

        else if (gamepad1.dpad_left) {
            armPosition = ARM_COLLAPSED_INTO_ROBOT;
            intake.setPower(INTAKE_OFF);
            wrist.setPosition(WRIST_FOLDED_IN);
        }

        else if (gamepad1.dpad_right){
            armPosition = ARM_SCORE_SPECIMEN;
            wrist.setPosition(WRIST_FOLDED_IN);
        }

        else if (gamepad1.dpad_up){
            /* This sets the arm to vertical to hook onto the LOW RUNG for hanging */
            armPosition = ARM_ATTACH_HANGING_HOOK;
            intake.setPower(INTAKE_OFF);
            wrist.setPosition(WRIST_FOLDED_IN);
        }

        else if (gamepad1.dpad_down){
            /* this moves the arm down to lift the robot up once it has been hooked */
            armPosition = ARM_WINCH_ROBOT;
            intake.setPower(INTAKE_OFF);
            wrist.setPosition(WRIST_FOLDED_IN);
        }

        armMotor.setTargetPosition((int) (armPosition  +armPositionFudgeFactor));

        ((DcMotorEx) armMotor).setVelocity(2100);
        armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        if (((DcMotorEx) armMotor).isOverCurrent()){
            telemetry.addLine("MOTOR EXCEEDED CURRENT LIMIT!");
        }

        telemetry.addData("Heading: : ", botHeading);
        telemetry.addData("armTarget: ", armMotor.getTargetPosition());
        telemetry.addData("arm Encoder: ", armMotor.getCurrentPosition());
        telemetry.addData("imu", imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS));
        telemetry.update();
    }
}