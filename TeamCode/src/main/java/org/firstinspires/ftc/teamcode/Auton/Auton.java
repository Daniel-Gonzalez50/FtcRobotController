package org.firstinspires.ftc.teamcode.Auton;

import android.app.Notification;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.robotcore.external.Telemetry;

@Autonomous(name = "Auton Test", group = "Autonamous")
public class Auton extends LinearOpMode{

    public class MoveRobot {
        private DcMotorEx frontRightMotor;
        private DcMotorEx frontLeftMotor;
        private DcMotorEx backRightMotor;
        private DcMotorEx backLeftMotor;

        public MoveRobot (HardwareMap hardwareMap){
            frontRightMotor = hardwareMap.get(DcMotorEx.class, "motorFR");
            frontLeftMotor = hardwareMap.get(DcMotorEx.class, "motorFL");
            backLeftMotor = hardwareMap.get(DcMotorEx.class, "motorBL");
            backRightMotor = hardwareMap.get(DcMotorEx.class, "motorBR");

            frontRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            frontLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            backLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            backRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

            frontRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
            frontLeftMotor.setDirection(DcMotorSimple.Direction.FORWARD);
            backLeftMotor.setDirection(DcMotorSimple.Direction.FORWARD);
            backRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        }

        public class MoveRobotUp implements Action {

            private boolean initialized = false;

            @Override
            public boolean run(@NonNull TelemetryPacket packet) {

                if (!initialized) {
                    frontRightMotor.setPower(0.5);
                    frontLeftMotor.setPower(0.5);
                    backRightMotor.setPower(0.5);
                    backLeftMotor.setPower(0.5);

                    initialized = true;
                }

                double pos = frontRightMotor.getCurrentPosition();
                packet.put("Robot Position", pos);
                if (pos < 3000) {

                    return true;
                } else {

                    frontRightMotor.setPower(0.0);
                    frontLeftMotor.setPower(0.0);
                    backRightMotor.setPower(0.0);
                    backLeftMotor.setPower(0.0);

                    return false;
                }
            }
        }

        public Action moveRobotUp() {
            return new MoveRobotUp();
        }

        public class MoveRobot2 implements Action {

            private boolean initialized = false;

            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                if(!initialized) {
                    frontRightMotor.setPower(-0.5);
                    frontLeftMotor.setPower(-0.5);
                    backRightMotor.setPower(-0.5);
                    backLeftMotor.setPower(-0.5);

                    initialized = true;
                }

                double pos = frontRightMotor.getCurrentPosition();
                packet.put("Robot Position", pos);
                if(pos >100){
                    return true;
                } else {
                    frontRightMotor.setPower(0.0);
                    frontLeftMotor.setPower(0.0);
                    backRightMotor.setPower(0.0);
                    backLeftMotor.setPower(0.0);

                    return false;
                }
            }
        }

        public Action moveRobotDown(){
            return new MoveRobot2();
        }
    }

    @Override
    public void runOpMode(){

    }
}