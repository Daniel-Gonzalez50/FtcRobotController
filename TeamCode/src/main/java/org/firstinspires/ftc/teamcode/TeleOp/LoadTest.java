package org.firstinspires.ftc.teamcode.TeleOp;

import android.service.media.CameraPrewarmService;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp(name = "Load Test", group = "main")

public class LoadTest extends LinearOpMode {

    DcMotor motorToTest = null;

    @Override
    public void runOpMode(){

        double[] [] loadTest;

        waitForStart();

        while(opModeIsActive()){

            if(gamepad1.right_trigger > 0.1){
                motorToTest.setDirection(DcMotorSimple.Direction.FORWARD);
                motorToTest.setPower(gamepad1.left_trigger);
                ((DcMotorEx) motorToTest).getCurrent(CurrentUnit.AMPS);
                ((DcMotorEx) motorToTest).getVelocity();
            } else {
                motorToTest.setPower(0.0);
            }

        }
    }
}