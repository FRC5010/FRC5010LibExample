// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package org.frc5010.common.sensors.camera;

import static edu.wpi.first.units.Units.Rotation;

import org.frc5010.common.drive.pose.PoseProvider;

import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Quaternion;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.networktables.DoubleSubscriber;
import edu.wpi.first.networktables.FloatArraySubscriber;
import edu.wpi.first.networktables.IntegerEntry;
import edu.wpi.first.networktables.IntegerSubscriber;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.PubSubOption;

/** Add your docs here. */
public class QuestNav implements PoseProvider {
    private boolean initializedPosition = false;
    private String networkTableRoot = "oculus";
    private NetworkTableInstance networkTableInstance = NetworkTableInstance.getDefault();
    private NetworkTable networkTable;
    private Transform3d robotToQuest;

    private IntegerEntry miso;

    private IntegerSubscriber frameCount;
    private DoubleSubscriber timestamp;
    private FloatArraySubscriber position;
    private FloatArraySubscriber quaternion;
    private FloatArraySubscriber eulerAngles;
    private DoubleSubscriber battery;

    public enum QuestCommand {
        RESET(1);

        public final int questRequestCode;

        private QuestCommand(int command) {
            this.questRequestCode = command;
        }

        public int getQuestRequest() {
            return questRequestCode;
        }
    }

    public QuestNav(Transform3d robotToQuest) {
        this.robotToQuest = robotToQuest;
        setupNetworkTables(networkTableRoot);
    }

    public QuestNav(Transform3d robotToQuest, String networkTableRoot) {
        this.robotToQuest = robotToQuest;
        this.networkTableRoot = networkTableRoot;
        setupNetworkTables(networkTableRoot);
    }


    private void setupNetworkTables(String root) {
        networkTable = networkTableInstance.getTable(root);
        miso = networkTable.getIntegerTopic("miso").getEntry(0);
        frameCount = networkTable.getIntegerTopic("frameCount").subscribe(0);
        timestamp = networkTable.getDoubleTopic("timestamp").subscribe(0.0);
        position = networkTable.getFloatArrayTopic("position").subscribe(new float[3]);
        quaternion = networkTable.getFloatArrayTopic("quaternion").subscribe(new float[4]);
        eulerAngles = networkTable.getFloatArrayTopic("eulerAngles").subscribe(new float[3]);
        battery = networkTable.getDoubleTopic("battery").subscribe(0.0);
    }


    public Translation3d getQuestPosition() {
        return new Translation3d(position.get()[2], -position.get()[0], position.get()[1]).plus(robotToQuest.getTranslation().times(-1));
    }

    public Rotation3d getQuestRotation() {
        Quaternion q = new Quaternion(quaternion.get()[0], quaternion.get()[1], quaternion.get()[2], quaternion.get()[3]);
        return new Rotation3d(q).plus(robotToQuest.getRotation().times(-1));
    }

    public Pose3d getRobotPose() {
        return new Pose3d(getPosition(), getRotation());
    }

    public Translation3d getPosition() {
        return getQuestPosition();
    }

    public Rotation3d getRotation() {
        return getQuestRotation();
    }

    public double getConfidence() {
        return 0.1;
    }

    public boolean isActive() {
        if (timestamp.get() == 0.0) {
            return false;
        } 
        return initializedPosition;
    }

    public boolean processQuestCommand(QuestCommand command) {
        if (miso.get() == 99) {
            return false;
        }
        miso.set(command.getQuestRequest());
        return true;
    }

    private void resetQuestPose() {
        processQuestCommand(QuestCommand.RESET);
    }

    public void resetPose(Pose3d pose) {
        initializedPosition = true;
    }

    public void resetPose() {
        initializedPosition = true;
        resetQuestPose();
    }


}
