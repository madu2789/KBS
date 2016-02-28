package champ2011client;


public class SensorModelJUnit implements SensorModel {

	double AngleToTrackAxis;
	double CurrentLapTime;
	double Damage;
	double DistanceFromStartLine;
	double DistanceRaced;
	double[] FocusSensors;
	double FuelLevel;
	int Gear;
	double LastLapTime;
	double LateralSpeed;
	String Message; 
	double[] OpponentSensors; 
	double RPM;
	int RacePosition;
	double Speed;
	double[] TrackEdgeSensors;
	double TrackPosition;
	double[] WheelSpinVelocity; 
	double Z;
	double ZSpeed;
	
	/**
	 * Constructs an empty sensor model scheme
	 */
	public SensorModelJUnit() {	
		AngleToTrackAxis=0;
		CurrentLapTime=0;
		Damage=0;
		DistanceFromStartLine=0;
		DistanceRaced=0;
		FocusSensors=new double[5];
		for (int i=0;i<5;i++)
			FocusSensors[i]=0;
		FuelLevel=0;
		Gear=0;
		LastLapTime=0;
		LateralSpeed=0;
		Message=""; 
		OpponentSensors=new double[36];
		for (int i=0;i<36;i++)
			OpponentSensors[i]=0;
		RPM=0;
		RacePosition=0;
		Speed=0;
		TrackEdgeSensors=new double[19];
		for (int i=0;i<19;i++)
			TrackEdgeSensors[i]=0;
		TrackPosition=0;
		WheelSpinVelocity=new double[4]; 
		for (int i=0;i<4;i++)
			WheelSpinVelocity[i]=0;
		Z=0;
		ZSpeed=0;
	}

	/**
	 * @return the angleToTrackAxis
	 */
	public double getAngleToTrackAxis() {
		return AngleToTrackAxis;
	}

	/**
	 * @param angleToTrackAxis the angleToTrackAxis to set
	 */
	public void setAngleToTrackAxis(double angleToTrackAxis) {
		AngleToTrackAxis = angleToTrackAxis;
	}

	/**
	 * @return the currentLapTime
	 */
	public double getCurrentLapTime() {
		return CurrentLapTime;
	}

	/**
	 * @param currentLapTime the currentLapTime to set
	 */
	public void setCurrentLapTime(double currentLapTime) {
		CurrentLapTime = currentLapTime;
	}

	/**
	 * @return the damage
	 */
	public double getDamage() {
		return Damage;
	}

	/**
	 * @param damage the damage to set
	 */
	public void setDamage(double damage) {
		Damage = damage;
	}

	/**
	 * @return the distanceFromStartLine
	 */
	public double getDistanceFromStartLine() {
		return DistanceFromStartLine;
	}

	/**
	 * @param distanceFromStartLine the distanceFromStartLine to set
	 */
	public void setDistanceFromStartLine(double distanceFromStartLine) {
		DistanceFromStartLine = distanceFromStartLine;
	}

	/**
	 * @return the distanceRaced
	 */
	public double getDistanceRaced() {
		return DistanceRaced;
	}

	/**
	 * @param distanceRaced the distanceRaced to set
	 */
	public void setDistanceRaced(double distanceRaced) {
		DistanceRaced = distanceRaced;
	}

	/**
	 * @return the focusSensors
	 */
	public double[] getFocusSensors() {
		return FocusSensors;
	}

	/**
	 * @param focusSensors the focusSensors to set
	 */
	public void setFocusSensors(double[] focusSensors) {
		FocusSensors = focusSensors;
	}

	/**
	 * @return the fuelLevel
	 */
	public double getFuelLevel() {
		return FuelLevel;
	}

	/**
	 * @param fuelLevel the fuelLevel to set
	 */
	public void setFuelLevel(double fuelLevel) {
		FuelLevel = fuelLevel;
	}

	/**
	 * @return the gear
	 */
	public int getGear() {
		return Gear;
	}

	/**
	 * @param gear the gear to set
	 */
	public void setGear(int gear) {
		Gear = gear;
	}

	/**
	 * @return the lastLapTime
	 */
	public double getLastLapTime() {
		return LastLapTime;
	}

	/**
	 * @param lastLapTime the lastLapTime to set
	 */
	public void setLastLapTime(double lastLapTime) {
		LastLapTime = lastLapTime;
	}

	/**
	 * @return the lateralSpeed
	 */
	public double getLateralSpeed() {
		return LateralSpeed;
	}

	/**
	 * @param lateralSpeed the lateralSpeed to set
	 */
	public void setLateralSpeed(double lateralSpeed) {
		LateralSpeed = lateralSpeed;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return Message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		Message = message;
	}

	/**
	 * @return the opponentSensors
	 */
	public double[] getOpponentSensors() {
		return OpponentSensors;
	}

	/**
	 * @param opponentSensors the opponentSensors to set
	 */
	public void setOpponentSensors(double[] opponentSensors) {
		OpponentSensors = opponentSensors;
	}

	/**
	 * @return the rPM
	 */
	public double getRPM() {
		return RPM;
	}

	/**
	 * @param rpm the rPM to set
	 */
	public void setRPM(double rpm) {
		RPM = rpm;
	}

	/**
	 * @return the racePosition
	 */
	public int getRacePosition() {
		return RacePosition;
	}

	/**
	 * @param racePosition the racePosition to set
	 */
	public void setRacePosition(int racePosition) {
		RacePosition = racePosition;
	}

	/**
	 * @return the speed
	 */
	public double getSpeed() {
		return Speed;
	}

	/**
	 * @param speed the speed to set
	 */
	public void setSpeed(double speed) {
		Speed = speed;
	}

	/**
	 * @return the trackEdgeSensors
	 */
	public double[] getTrackEdgeSensors() {
		return TrackEdgeSensors;
	}

	/**
	 * @param trackEdgeSensors the trackEdgeSensors to set
	 */
	public void setTrackEdgeSensors(double[] trackEdgeSensors) {
		TrackEdgeSensors = trackEdgeSensors;
	}

	/**
	 * @return the trackPosition
	 */
	public double getTrackPosition() {
		return TrackPosition;
	}

	/**
	 * @param trackPosition the trackPosition to set
	 */
	public void setTrackPosition(double trackPosition) {
		TrackPosition = trackPosition;
	}

	/**
	 * @return the wheelSpinVelocity
	 */
	public double[] getWheelSpinVelocity() {
		return WheelSpinVelocity;
	}

	/**
	 * @param wheelSpinVelocity the wheelSpinVelocity to set
	 */
	public void setWheelSpinVelocity(double[] wheelSpinVelocity) {
		WheelSpinVelocity = wheelSpinVelocity;
	}

	/**
	 * @return the z
	 */
	public double getZ() {
		return Z;
	}

	/**
	 * @param z the z to set
	 */
	public void setZ(double z) {
		Z = z;
	}

	/**
	 * @return the zSpeed
	 */
	public double getZSpeed() {
		return ZSpeed;
	}

	/**
	 * @param speed the zSpeed to set
	 */
	public void setZSpeed(double speed) {
		ZSpeed = speed;
	}
	
	
	

}
