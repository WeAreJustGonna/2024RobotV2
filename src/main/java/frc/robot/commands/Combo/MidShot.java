// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Combo;

import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants.ArmConstants;
import frc.robot.Constants.ShooterConstants;
import frc.robot.commands.Arm.WaitForArmAngle;
import frc.robot.commands.Feeder.SetFeederSpeed;
import frc.robot.commands.Feeder.WaitForNoNote;
import frc.robot.commands.Feeder.WaitForNote;
import frc.robot.commands.Intake.IdleIntake;
import frc.robot.commands.Shooter.SetShooterSpeed;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Feeder;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;


public class MidShot extends SequentialCommandGroup {
  /** Creates a new MidShotTest. */
  public MidShot(Arm  m_arm, Shooter m_shooter, Intake m_intake, Feeder m_feeder) {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(
      new ParallelDeadlineGroup(
        new WaitForArmAngle(m_arm, ArmConstants.kSpeakerMidAngle),
        new SetShooterSpeed(m_shooter, ShooterConstants.kShooterSpeedMidRPS),
        new IdleIntake(m_intake)
      ),
      new ParallelDeadlineGroup(
        new WaitForNote(m_feeder), 
        new SetFeederSpeed(10, m_feeder)
      ),
      new ParallelDeadlineGroup(
        new WaitForNoNote(m_feeder), 
        new SetFeederSpeed(10, m_feeder)),
      new ReturnToBasic(m_arm, m_shooter, m_intake, m_feeder).withTimeout(0.1)
    );
  }
}
