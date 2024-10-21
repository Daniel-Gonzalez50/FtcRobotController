package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;
//import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "MotorTestCode", group = "Main")

public class MotorTestCode extends LinearOpMode {

    ElapsedTime motorTimer = new ElapsedTime();
    DcMotor motorToTest1 = null;
    DcMotor motorToTest2 = null;

    //Servo servoToTest = null;

    @Override
    public void runOpMode() {

        motorToTest1 = hardwareMap.dcMotor.get("backLeftMotor");
        motorToTest1.setDirection(DcMotorSimple.Direction.FORWARD);
        motorToTest1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motorToTest1.setPower(0.0);

        motorToTest2 = hardwareMap.dcMotor.get("frontRightMotor");
        motorToTest2.setDirection(DcMotorSimple.Direction.FORWARD);
        motorToTest2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motorToTest2.setPower(0.0);

        /*servoToTest = hardwareMap.servo.get("servo");
        servoToTest.setDirection(Servo.Direction.FORWARD);
        servoToTest.setPosition(0.0); */

        waitForStart();

        while (opModeIsActive()) {

            if(gamepad1.x || motorTimer.seconds() <= 30){
                motorToTest1.setPower(0.5);
            }

            if (motorTimer.seconds() >= 30){
                if (motorTimer.seconds() <= 45){
                    motorToTest1.setPower(0.0);
                    motorToTest2.setPower(0.5);
                }
            }

            if (motorTimer.seconds() >= 45){
                if (motorTimer.seconds() <= 60){
                    motorToTest2.setPower(0.0);
                    motorToTest1.setPower(0.5);
                }
            }

            if (motorTimer.seconds() >= 60){
                motorToTest2.setPower(0.5);
                motorToTest1.setPower(0.5);
                //servoToTest.setPosition(1.00);
            }
        }

    }
}