package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;

@TeleOp(name = "Toggle Switch", group = "main")

public class KaviTestCode extends LinearOpMode {

    DcMotor motor1 = null;

    @Override
    public void runOpMode(){

        Gamepad currentGamepad1 = new Gamepad();
        Gamepad previousGamepad1 = new Gamepad();

        motor1 = hardwareMap.dcMotor.get("motorBR");
        motor1.setDirection(DcMotorSimple.Direction.FORWARD);
        motor1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motor1.setPower(0.0);

        boolean motorToggle = false;

        waitForStart();

        if (isStopRequested()) return;

        while(opModeIsActive()){

            if(currentGamepad1.a && !previousGamepad1.a){

                motorToggle = !motorToggle;
            }

            if(motorToggle){
                motor1.setDirection(DcMotorSimple.Direction.FORWARD);
                motor1.setPower(0.0);
            } else {
                motor1.setDirection(DcMotorSimple.Direction.REVERSE);
                motor1.setPower(0.0);
            }

        }
    }
}
