package champ2011client;

import jess.Fact;
import jess.JessException;
import jess.Value;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import champ2011client.madudriver;
import junit.framework.TestCase;

public class TestJessController extends TestCase {
	madudriver car;
	SensorModelJUnit sensor;
	
	@Before
	public void setUp() {
		car=new madudriver();	
		sensor=new SensorModelJUnit();
	}
	
	@After
	public void tearDown () {	
	}
	
	/**
	 * Test if the information is well introduced in Jess 
	 */
	/*
	@Test public void testSetSensorInformationInJess() throws Exception{
		Fact f;
		
			//Add information regarding the environment
		sensor.setSpeed(10.0);
		sensor.setAngleToTrackAxis(0.0);
		
			//Call the method responsible for adding data into JESS
		car.Facts(sensor);
		
			//Speed information
		f=car.findFactByIDinJess(1);
		assertEquals(sensor.getSpeed(),Double.parseDouble(f.get(0).toString()));
		
			//AngleToTrackAxis information
		f=car.findFactByIDinJess(2);
		assertEquals(sensor.getAngleToTrackAxis(),Double.parseDouble(f.get(0).toString()));
	}*/
	
	/**
	 * Test if the decided actions are well retrieved
	 * @throws JessException 
	 */
	@Test public void testWhatDriverShouldDo() throws JessException {
		Action aTest, aExpected;
/*		
			//Create an action object for introducing it in JESS
		aTest=new Action();
		aTest.accelerate=0.1;
		aTest.brake=0.2;
		aTest.steering=0.3;
		aTest.clutch=0.4;
		aTest.gear=1;		

		String command = "(defrule add-Variables => (store ACCELERATE "+aTest.accelerate+") "+
		"(store STEERING "+aTest.steering+")"+
		"(store GEAR "+aTest.gear+")"+
		"(store CLUTCH "+aTest.clutch+")"+
		"(store BRAKE "+aTest.brake+"))";
		car.executeJessCommand(command);
		car.executeJessCommand("(run)");
		
			//Call the method responsible for retrieving the actions
		aExpected=car.whatDriverShouldDo();
				
			//Compare the values of both objects. Because equals is not defined in Action, they cannot be directly compared
		assertEquals(aTest.accelerate,aExpected.accelerate);
		assertEquals(aTest.steering,aExpected.steering);
		assertEquals(aTest.gear,aExpected.gear);
		assertEquals(aTest.clutch,aExpected.clutch);
		assertEquals(aTest.brake,aExpected.brake);			*/
	}

	/**
	 * Test the initRuleAccelerate
	 * @throws JessException
	 */
	@Test public void testinitRuleAccelerate() throws JessException {
/*		String command="(assert (currentSpeed 10.0))";
		car.executeJessCommand(command);
		car.executeJessCommand("(run)");
		assertEquals(1.0, car.getDoubleVariableFromJess("ACCELERATE"));
		
		car.clearJessEngine();
		car.resetJessEngine();
		command="(assert (currentSpeed 16.0))";
		car.executeJessCommand(command);
		car.executeJessCommand("(run)");
		assertEquals(0.0, car.getDoubleVariableFromJess("ACCELERATE"));*/
		
	}
	
	
//DE |-8�|  A |8| -> 0.17 a 0.17 
	/**
	 * Test the initRule: direRecte. (Recte i no es desvia)
	 * @throws JessException
	 */
	@Test public void testinitRuledireRectemarxa1() throws JessException {
		
		car.engine.reset();
		
		String command = "(defrule direRectemarxa1 (angle ?a&: (and(< ?a 0.17)(> ?a -0.17)))(obsEsq ?oE&: (>= ?oE 15))(obsDret ?oD&: (>= ?oD 15))(velocitat ?v&: (< ?v 80))" +
		"=> (store steering 0)(store accel 0.5 )(store gear 1)(store brake 0))";
		car.engine.executeCommand(command);	
		
		command="(assert (angle 0.15))(assert (obsEsq 20.0))(assert (obsDret 20.0))(assert (velocitat 20.0))";
		car.engine.executeCommand(command);
		car.engine.executeCommand("(run)");

		assertEquals(0.0, car.getDoubleVariableFromJess("steering"));
		assertEquals(0.5, car.getDoubleVariableFromJess("accel"));
		assertEquals(1, car.getIntVariableFromJess("gear"));
		assertEquals(0.0, car.getDoubleVariableFromJess("brake"));
		
		car.engine.clear();
		car.engine.reset();
		
		command = "(defrule direRectemarxa1 (angle ?a&: (and(< ?a 0.17)(> ?a -0.17)))(obsEsq ?oE&: (>= ?oE 15))(obsDret ?oD&: (>= ?oD 15))(velocitat ?v&: (< ?v 80))" +
		"=> (store steering 0)(store accel 0.5 )(store gear 1)(store brake 0))";
		car.engine.executeCommand(command);	
		
		command="(assert (angle 1))(assert (obsEsq 20))(assert (obsDret 14))(assert (velocitat 20))";
		car.engine.executeCommand(command);
		car.engine.executeCommand("(run)");
		
		assertEquals(0.0, car.getDoubleVariableFromJess("steering"));
		assertEquals(0.0, car.getDoubleVariableFromJess("accel"));
		assertEquals(0, car.getIntVariableFromJess("gear"));
		assertEquals(0.0, car.getDoubleVariableFromJess("brake"));	
		
	}
	
	/**
	 * Test the initRule: direRecteCorba Dret/Esq. (Obstacle a dreta i esquerra)
	 * @throws JessException
	 */
	@Test public void testinitRuledireRecteCorba() throws JessException {
		car.engine.reset();
		
		String command = "(defrule direRecteCorbaDret (angle ?a&: (and(< ?a 0.17)(> ?a -0.17)))(obsEsq ?oE&: (> ?oE 15))(obsDret ?oD&: (< ?oD 15))" +
		"=> (store steering 0.1)(store accel 0.25 )(store gear 1)(store brake 0))";
		car.engine.executeCommand(command);
		
		command="(assert (angle 0.0))(assert (obsEsq 21.0))(assert (obsDret 14.0))";
		car.engine.executeCommand(command);
		car.engine.executeCommand("(run)");
		
//		command = "(defrule direRecteCorbaDret (angle ?a&: (and(< ?a 0.17)(> ?a -0.17)))(obsEsq ?oE&: (> ?oE 15))(obsDret ?oD&: (< ?oD 15))" +
//		"=> (store steering 0.1)(store accel 0.25 )(store gear 1)(store brake 0))";

		assertEquals(0.1, car.getDoubleVariableFromJess("steering"));
		assertEquals(0.25, car.getDoubleVariableFromJess("accel"));
		assertEquals(1, car.getIntVariableFromJess("gear"));
		assertEquals(0.0, car.getDoubleVariableFromJess("brake"));
		
		car.engine.clear();
		car.engine.reset();
		
		command = "(defrule direRecteCorbaDret (angle ?a&: (and(< ?a 0.17)(> ?a -0.17)))(obsEsq ?oE&: (> ?oE 15))(obsDret ?oD&: (< ?oD 15))" +
		"=> (store steering 0.1)(store accel 0.25 )(store gear 1)(store brake 0))";
		car.engine.executeCommand(command);
		
		command="(assert (angle 0.0))(assert (obsEsq 14.0))(assert (obsDret 19.0))";
		car.engine.executeCommand(command);
		car.engine.executeCommand("(run)");

		assertEquals(0.0, car.getDoubleVariableFromJess("steering"));
		assertEquals(0.0, car.getDoubleVariableFromJess("accel"));
		assertEquals(0, car.getIntVariableFromJess("gear"));
		assertEquals(0.0, car.getDoubleVariableFromJess("brake"));
	}
	
	/**
	 * Test the initRule: direRecteFora i direRecteParet Esq/Dret. (Fora de pista)
	 * @throws JessException
	 */
	@Test public void testinitRuledireccioFora() throws JessException {
		
		car.engine.reset();
		
		String command = "(defrule direRecteFora (angle ?a&: (and(< ?a 0.17)(> ?a -0.17)))(obsEsq ?oE&: (< ?oE 15))(obsDret ?oD&: (< ?oD 15))(trackpos ?pos&: (and(<= ?pos 0.8)(>= ?pos -0.8)))" +
		"=> (store steering 0)(store accel 0.2 )(store gear 1)(store brake 0))";
		car.engine.executeCommand(command);
		
		command="(assert (angle 0.0))(assert (obsEsq 14.0))(assert (obsDret 14.0))(assert (trackpos 0.0))";
		car.engine.executeCommand(command);
		car.engine.executeCommand("(run)");

		assertEquals(0.0, car.getDoubleVariableFromJess("steering"));
		assertEquals(0.2, car.getDoubleVariableFromJess("accel"));
		assertEquals(1, car.getIntVariableFromJess("gear"));
		assertEquals(0.0, car.getDoubleVariableFromJess("brake"));
		
		car.engine.clear();
		car.engine.reset();
		
		command = "(defrule direRecteFora (angle ?a&: (and(< ?a 0.17)(> ?a -0.17)))(obsEsq ?oE&: (< ?oE 15))(obsDret ?oD&: (< ?oD 15))(trackpos ?pos&: (and(<= ?pos 0.8)(>= ?pos -0.8)))" +
		"=> (store steering 0)(store accel 0.2 )(store gear 1)(store brake 0))";
		car.engine.executeCommand(command);
		
		command="(assert (angle 0.0))(assert (obsEsq 14.0))(assert (obsDret 14.0))(assert (trackpos 1.0))";
		car.engine.executeCommand(command);
		car.engine.executeCommand("(run)");
		

		assertEquals(0.0, car.getDoubleVariableFromJess("steering"));
		assertEquals(0.0, car.getDoubleVariableFromJess("accel"));
		assertEquals(0, car.getIntVariableFromJess("gear"));
		assertEquals(0.0, car.getDoubleVariableFromJess("brake"));
		
//		command = "(defrule direRecteParetDret (angle ?a&: (and(< ?a 0.17)(> ?a -0.17)))(obsEsq ?oE&: (< ?oE 15))(obsDret ?oD&: (< ?oD 15))(trackpos ?pos&: (< ?pos -0.8))" +
//		"=> (store steering 0.2)(store accel 0.2 )(store gear 1)(store brake 0))";
		
		car.engine.clear();
		car.engine.reset();
	
		command = "(defrule direRecteParetDret (angle ?a&: (and(< ?a 0.17)(> ?a -0.17)))(obsEsq ?oE&: (< ?oE 15))(obsDret ?oD&: (< ?oD 15))(trackpos ?pos&: (< ?pos -0.8))" +
		"=> (store steering 0.2)(store accel 0.2 )(store gear 1)(store brake 0))";
		car.engine.executeCommand(command);
		
		command="(assert (angle 0.0))(assert (obsEsq 14.0))(assert (obsDret 14.0))(assert (trackpos -1.0))";
		car.engine.executeCommand(command);
		car.engine.executeCommand("(run)");
		
		assertEquals(0.2, car.getDoubleVariableFromJess("steering"));
		assertEquals(0.2, car.getDoubleVariableFromJess("accel"));
		assertEquals(1, car.getIntVariableFromJess("gear"));
		assertEquals(0.0, car.getDoubleVariableFromJess("brake"));
		
		car.engine.clear();
		car.engine.reset();
		
		command = "(defrule direRecteParetDret (angle ?a&: (and(< ?a 0.17)(> ?a -0.17)))(obsEsq ?oE&: (< ?oE 15))(obsDret ?oD&: (< ?oD 15))(trackpos ?pos&: (< ?pos -0.8))" +
		"=> (store steering 0.2)(store accel 0.2 )(store gear 1)(store brake 0))";
		car.engine.executeCommand(command);
		
		command="(assert (angle 1.0))(assert (obsEsq 14.0))(assert (obsDret 14.0))(assert (trackpos -1.0))";
		car.engine.executeCommand(command);
		car.engine.executeCommand("(run)");

		assertEquals(0.0, car.getDoubleVariableFromJess("steering"));
		assertEquals(0.0, car.getDoubleVariableFromJess("accel"));
		assertEquals(0, car.getIntVariableFromJess("gear"));
		assertEquals(0.0, car.getDoubleVariableFromJess("brake"));
	}
	
	
//DE |8�|  A |36�| -> 0.17 a 0.5 
	/**
	 * Test the initRule: direccio Dret/Esq. 
	 * @throws JessException
	 */
	@Test public void testinitRuledireccio() throws JessException {
			
		car.engine.reset();	
		
		String command = "(defrule direccioDret (angle ?a&: (and(>= ?a 0.17)(< ?a 0.5)))(obsEsq ?oE&: (>= ?oE 15))(obsDret ?oD&: (>= ?oD 15))" +
		"=> (store steering 0.2)(store accel 0.2 )(store gear 1)(store brake 0))";
		car.engine.executeCommand(command);
		
		command="(assert (angle 0.3))(assert (obsEsq 21.0))(assert (obsDret 21.0))";
		car.engine.executeCommand(command);
		car.engine.executeCommand("(run)");
		
		assertEquals(0.2, car.getDoubleVariableFromJess("steering"));
		assertEquals(0.2, car.getDoubleVariableFromJess("accel"));
		assertEquals(1, car.getIntVariableFromJess("gear"));
		assertEquals(0.0, car.getDoubleVariableFromJess("brake"));
		
		car.engine.clear();
		car.engine.reset();
		
		command = "(defrule direccioDret (angle ?a&: (and(>= ?a 0.17)(< ?a 0.5)))(obsEsq ?oE&: (>= ?oE 15))(obsDret ?oD&: (>= ?oD 15))" +
		"=> (store steering 0.2)(store accel 0.2 )(store gear 1)(store brake 0))";
		car.engine.executeCommand(command);
		
		command="(assert (angle 0.0))(assert (obsEsq 21.0))(assert (obsDret 16.0))";
		car.engine.executeCommand(command);
		car.engine.executeCommand("(run)");

		assertEquals(0.0, car.getDoubleVariableFromJess("steering"));
		assertEquals(0.0, car.getDoubleVariableFromJess("accel"));
		assertEquals(0, car.getIntVariableFromJess("gear"));
		assertEquals(0.0, car.getDoubleVariableFromJess("brake"));
	}
	
	/**
	 * Test the initRule: direccioDretCorba Dret/Esq. (Desviat a la dreta amb corbes)
	 * @throws JessException
	 */
	@Test public void testinitRuledireccioDretCorba() throws JessException {
		
		String command = "(defrule direccioDretCorbaDret (angle ?a&: (and(>= ?a 0.17)(< ?a 0.5)))(obsEsq ?oE&: (> ?oE 15))(obsDret ?oD&: (< ?oD 15))" +
		"=> (store steering 0.2)(store accel 0.2 )(store gear 1)(store brake 0))";
		car.engine.executeCommand(command);
		
		command="(assert (angle 0.3))";
		car.engine.executeCommand(command);
		command="(assert (obsEsq 21.0))";
		car.engine.executeCommand(command);
		command="(assert (obsDret 14.0))";
		car.engine.executeCommand(command);
		car.engine.executeCommand("(run)");
		
		assertEquals(0.2, car.getDoubleVariableFromJess("steering"));
		assertEquals(0.2, car.getDoubleVariableFromJess("accel"));
		assertEquals(1, car.getIntVariableFromJess("gear"));
		assertEquals(0.0, car.getDoubleVariableFromJess("brake"));

		car.engine.clear();
		car.engine.reset();
		
		command = "(defrule direccioDretCorbaDret (angle ?a&: (and(>= ?a 0.17)(< ?a 0.5)))(obsEsq ?oE&: (> ?oE 15))(obsDret ?oD&: (< ?oD 15))" +
		"=> (store steering 0.2)(store accel 0.2 )(store gear 1)(store brake 0))";
		car.engine.executeCommand(command);
		
		command="(assert (angle 1.3))";
		car.engine.executeCommand(command);
		command="(assert (obsEsq 14.0))";
		car.engine.executeCommand(command);
		command="(assert (obsDret 19.0))";
		car.engine.executeCommand(command);
		car.engine.executeCommand("(run)");
		
		assertEquals(0.0, car.getDoubleVariableFromJess("steering"));
		assertEquals(0.0, car.getDoubleVariableFromJess("accel"));
		assertEquals(0, car.getIntVariableFromJess("gear"));
		assertEquals(0.0, car.getDoubleVariableFromJess("brake"));
	}
		
	/**
	 * Test the initRule: direccioEsqCorba Dret/Esq. (Desviat a la esquerra amb corbes)
	 * @throws JessException
	 */
	@Test public void testinitRuledireccioEsqCorba() throws JessException {
		
		car.engine.reset();	
		
		String command =  "(defrule direccioEsqCorbaDret (angle ?a&: (and(<= ?a -0.17)(> ?a -0.5)))(obsEsq ?oE&: (> ?oE 15))(obsDret ?oD&: (< ?oD 15))" +
		"=> (store steering 0.5)(store accel 0.2 )(store gear 1)(store brake 0))";
		car.engine.executeCommand(command);
		
		command="(assert (angle -0.3))";
		car.engine.executeCommand(command);
		command="(assert (obsEsq 21.0))";
		car.engine.executeCommand(command);
		command="(assert (obsDret 14.0))";
		car.engine.executeCommand(command);

		car.engine.executeCommand("(run)");
		assertEquals(0.5, car.getDoubleVariableFromJess("steering"));
		assertEquals(0.2, car.getDoubleVariableFromJess("accel"));
		assertEquals(1, car.getIntVariableFromJess("gear"));
		assertEquals(0.0, car.getDoubleVariableFromJess("brake"));
		
		car.engine.clear();
		car.engine.reset();
		
		command =  "(defrule direccioEsqCorbaDret (angle ?a&: (and(<= ?a -0.17)(> ?a -0.5)))(obsEsq ?oE&: (> ?oE 15))(obsDret ?oD&: (< ?oD 15))" +
		"=> (store steering 0.5)(store accel 0.2 )(store gear 1)(store brake 0))";
		car.engine.executeCommand(command);
		
		command="(assert (angle -1.3))";
		car.engine.executeCommand(command);
		command="(assert (obsEsq 14.0))";
		car.engine.executeCommand(command);
		command="(assert (obsDret 19.0))";
		car.engine.executeCommand(command);

		car.engine.executeCommand("(run)");
		assertEquals(0.0, car.getDoubleVariableFromJess("steering"));
		assertEquals(0.0, car.getDoubleVariableFromJess("accel"));
		assertEquals(0, car.getIntVariableFromJess("gear"));
		assertEquals(0.0, car.getDoubleVariableFromJess("brake"));
		
		car.engine.clear();
		car.engine.reset();
		
		command = "(defrule direccioEsqCorbaEsq (angle ?a&: (and(<= ?a -0.17)(> ?a -0.5)))(obsEsq ?oE&: (< ?oE 15))(obsDret ?oD&: (> ?oD 15))" +
		"=> (store steering -0.2)(store accel 0.2 )(store gear 1)(store brake 0))";
		car.engine.executeCommand(command);
		
		command="(assert (angle -0.3))";
		car.engine.executeCommand(command);
		command="(assert (obsEsq 14.0))";
		car.engine.executeCommand(command);
		command="(assert (obsDret 16.0))";
		car.engine.executeCommand(command);

		car.engine.executeCommand("(run)");
		assertEquals(-0.2, car.getDoubleVariableFromJess("steering"));
		assertEquals(0.2, car.getDoubleVariableFromJess("accel"));
		assertEquals(1, car.getIntVariableFromJess("gear"));
		assertEquals(0.0, car.getDoubleVariableFromJess("brake"));
		
		car.engine.clear();
		car.engine.reset();
		
		command = "(defrule direccioEsqCorbaEsq (angle ?a&: (and(<= ?a -0.17)(> ?a -0.5)))(obsEsq ?oE&: (< ?oE 15))(obsDret ?oD&: (> ?oD 15))" +
		"=> (store steering -0.2)(store accel 0.2 )(store gear 1)(store brake 0))";
		car.engine.executeCommand(command);
		
		command="(assert (angle -1.3))";
		car.engine.executeCommand(command);
		command="(assert (obsEsq 14.0))";
		car.engine.executeCommand(command);
		command="(assert (obsDret 16.0))";
		car.engine.executeCommand(command);
		
		car.engine.executeCommand("(run)");
		assertEquals(0.0, car.getDoubleVariableFromJess("steering"));
		assertEquals(0.0, car.getDoubleVariableFromJess("accel"));
		assertEquals(0, car.getIntVariableFromJess("gear"));
		assertEquals(0.0, car.getDoubleVariableFromJess("brake"));
	}

	/**
	 * Test the initRule: direccioForaRec D/E. (Fora de pista i segueix recte)
	 * @throws JessException
	 */
	@Test public void testinitRuledireccioForaRec() throws JessException {
		car.engine.reset();	
		
		String command = "(defrule direccioForaRecD (angle ?a&: (and(>= ?a 0.17)(< ?a 0.5)))(obsEsq ?oE&: (< ?oE 15))(obsDret ?oD&: (< ?oD 15))(trackpos ?pos&: (and(<= ?pos 0.8)(>= ?pos -0.8)))" +
		"=> (store steering 0.1)(store accel 0.2 )(store gear 1)(store brake 0))";
		car.engine.executeCommand(command);
		
		command="(assert (angle 0.3))";
		car.engine.executeCommand(command);
		command="(assert (obsEsq 14.0))";
		car.engine.executeCommand(command);
		command="(assert (obsDret 14.0))";
		car.engine.executeCommand(command);
		command="(assert (trackpos 0.5))";
		car.engine.executeCommand(command);

		car.engine.executeCommand("(run)");
		assertEquals(0.1, car.getDoubleVariableFromJess("steering"));
		assertEquals(0.2, car.getDoubleVariableFromJess("accel"));
		assertEquals(1, car.getIntVariableFromJess("gear"));
		assertEquals(0.0, car.getDoubleVariableFromJess("brake"));
		
		car.engine.clear();
		car.engine.reset();
		
		command = "(defrule direccioForaRecD (angle ?a&: (and(>= ?a 0.17)(< ?a 0.5)))(obsEsq ?oE&: (< ?oE 15))(obsDret ?oD&: (< ?oD 15))(trackpos ?pos&: (and(<= ?pos 0.8)(>= ?pos -0.8)))" +
		"=> (store steering 0.1)(store accel 0.2 )(store gear 1)(store brake 0))";
		car.engine.executeCommand(command);
		
		command="(assert (angle -1.3))";
		car.engine.executeCommand(command);
		command="(assert (obsEsq 14.0))";
		car.engine.executeCommand(command);
		command="(assert (obsDret 19.0))";
		car.engine.executeCommand(command);

		car.engine.executeCommand("(run)");
		assertEquals(0.0, car.getDoubleVariableFromJess("steering"));
		assertEquals(0.0, car.getDoubleVariableFromJess("accel"));
		assertEquals(0, car.getIntVariableFromJess("gear"));
		assertEquals(0.0, car.getDoubleVariableFromJess("brake"));

	}
	
	/**
	 * Test the initRule: direccioDretForaDretGirEsq i direccioDretForaEsq.
	 * @throws JessException
	 */
	@Test public void testinitRuledireccioDretFora() throws JessException {
		car.engine.reset();	
		String command = "(defrule direccioDretForaDretGirEsq (angle ?a&: (and(>= ?a 0.17)(< ?a 0.5)))(obsEsq ?oE&: (< ?oE 15))(obsDret ?oD&: (< ?oD 15))(trackpos ?pos&: (< ?pos -0.8))" +
		"=> (store steering 0.5)(store accel 0.3)(store gear 1)(store brake 0))";
		car.engine.executeCommand(command);
		
		command="(assert (angle 0.3))";
		car.engine.executeCommand(command);
		command="(assert (obsEsq 14.0))";
		car.engine.executeCommand(command);
		command="(assert (obsDret 14.0))";
		car.engine.executeCommand(command);
		command="(assert (trackpos -0.9))";
		car.engine.executeCommand(command);

		car.engine.executeCommand("(run)");
		assertEquals(0.5, car.getDoubleVariableFromJess("steering"));
		assertEquals(0.3, car.getDoubleVariableFromJess("accel"));
		assertEquals(1, car.getIntVariableFromJess("gear"));
		assertEquals(0.0, car.getDoubleVariableFromJess("brake"));
		
		car.engine.clear();
		car.engine.reset();
		
		command = "(defrule direccioDretForaDretGirEsq (angle ?a&: (and(>= ?a 0.17)(< ?a 0.5)))(obsEsq ?oE&: (< ?oE 15))(obsDret ?oD&: (< ?oD 15))(trackpos ?pos&: (< ?pos -0.8))" +
		"=> (store steering 0.5)(store accel 0.3)(store gear 1)(store brake 0))";
		car.engine.executeCommand(command);
		
		command="(assert (angle -1.3))";
		car.engine.executeCommand(command);
		command="(assert (obsEsq 14.0))";
		car.engine.executeCommand(command);
		command="(assert (obsDret 19.0))";
		car.engine.executeCommand(command);

		car.engine.executeCommand("(run)");
		assertEquals(0.0, car.getDoubleVariableFromJess("steering"));
		assertEquals(0.0, car.getDoubleVariableFromJess("accel"));
		assertEquals(0, car.getIntVariableFromJess("gear"));
		assertEquals(0.0, car.getDoubleVariableFromJess("brake"));
		
		car.engine.clear();
		car.engine.reset();

		command = "(defrule direccioDretForaEsq (angle ?a&: (and(>= ?a 0.17)(< ?a 0.5)))(obsEsq ?oE&: (< ?oE 15))(obsDret ?oD&: (< ?oD 15))(trackpos ?pos&: (> ?pos 0.8))" +
		"=> (store steering -0.2)(store accel 0.2 )(store gear 1)(store brake 0))";
		car.engine.executeCommand(command);
		
		command="(assert (angle 0.3))";
		car.engine.executeCommand(command);
		command="(assert (obsEsq 14.0))";
		car.engine.executeCommand(command);
		command="(assert (obsDret 14.0))";
		car.engine.executeCommand(command);
		command="(assert (trackpos 0.9))";
		car.engine.executeCommand(command);

		car.engine.executeCommand("(run)");
		assertEquals(-0.2, car.getDoubleVariableFromJess("steering"));
		assertEquals(0.2, car.getDoubleVariableFromJess("accel"));
		assertEquals(1, car.getIntVariableFromJess("gear"));
		assertEquals(0.0, car.getDoubleVariableFromJess("brake"));
		
		car.engine.clear();
		car.engine.reset();
		
		command = "(defrule direccioDretForaEsq (angle ?a&: (and(>= ?a 0.17)(< ?a 0.5)))(obsEsq ?oE&: (< ?oE 15))(obsDret ?oD&: (< ?oD 15))(trackpos ?pos&: (> ?pos 0.8))" +
		"=> (store steering -0.2)(store accel 0.2 )(store gear 1)(store brake 0))";
		car.engine.executeCommand(command);
		
		command="(assert (angle -1.3))";
		car.engine.executeCommand(command);
		command="(assert (obsEsq 14.0))";
		car.engine.executeCommand(command);
		command="(assert (obsDret 19.0))";
		car.engine.executeCommand(command);

		car.engine.executeCommand("(run)");
		assertEquals(0.0, car.getDoubleVariableFromJess("steering"));
		assertEquals(0.0, car.getDoubleVariableFromJess("accel"));
		assertEquals(0, car.getIntVariableFromJess("gear"));
		assertEquals(0.0, car.getDoubleVariableFromJess("brake"));
	}
	
	
//DE |36�| A |80�| -> 0.5 a 1.6 
	/**
	 * Test the initRule: direccioDretExtrem i direccioEsqExtrem.
	 * @throws JessException
	 */
	@Test public void testinitRuledireccioExtrem() throws JessException {
		
		car.engine.reset();	
		String command = "(defrule direccioDretExtrem (angle ?a&: (and(>= ?a 0.5)(<= ?a 1.6)))(trackpos ?pos&: (> ?pos -1.1))" +
		"=> (store steering 0.7)(store accel 0.4 )(store gear 1)(store brake 0))";
		car.engine.executeCommand(command);
		
		command="(assert (angle 0.6))";
		car.engine.executeCommand(command);
		command="(assert (trackpos -0.9))";
		car.engine.executeCommand(command);

		car.engine.executeCommand("(run)");
		assertEquals(0.7, car.getDoubleVariableFromJess("steering"));
		assertEquals(0.4, car.getDoubleVariableFromJess("accel"));
		assertEquals(1, car.getIntVariableFromJess("gear"));
		assertEquals(0.0, car.getDoubleVariableFromJess("brake"));
		
		car.engine.clear();
		car.engine.reset();
		
		command = "(defrule direccioDretExtrem (angle ?a&: (and(>= ?a 0.5)(<= ?a 1.6)))(trackpos ?pos&: (> ?pos -1.1))" +
		"=> (store steering 0.7)(store accel 0.4 )(store gear 1)(store brake 0))";
		car.engine.executeCommand(command);
		
		command="(assert (angle -1.3))";
		car.engine.executeCommand(command);
		command="(assert (trackpos -0.9))";
		car.engine.executeCommand(command);


		car.engine.executeCommand("(run)");
		assertEquals(0.0, car.getDoubleVariableFromJess("steering"));
		assertEquals(0.0, car.getDoubleVariableFromJess("accel"));
		assertEquals(0, car.getIntVariableFromJess("gear"));
		assertEquals(0.0, car.getDoubleVariableFromJess("brake"));
	}
	
	/**
	 * Test the initRule: direccioDretExtremFora i direccioEsqExtremFora. (Fora de pista)
	 * @throws JessException
	 */
	@Test public void testinitRuledireccioExtremFora() throws JessException {

		car.engine.reset();	
		String command = "(defrule direccioDretExtremFora (angle ?a&: (and(>= ?a 0.5)(<= ?a 1.6)))(trackpos ?pos&: (<= ?pos -1.1))" +
		"=> (store steering -1)(store accel 0.4 )(store gear -1)(store brake 0))";
		car.engine.executeCommand(command);
		
		command="(assert (angle 0.6))";
		car.engine.executeCommand(command);
		command="(assert (trackpos -2.0))";
		car.engine.executeCommand(command);

		car.engine.executeCommand("(run)");
		assertEquals(-1.0, car.getDoubleVariableFromJess("steering"));
		assertEquals(0.4, car.getDoubleVariableFromJess("accel"));
		assertEquals(-1, car.getIntVariableFromJess("gear"));
		assertEquals(0.0, car.getDoubleVariableFromJess("brake"));
		
		car.engine.clear();
		car.engine.reset();
		
		command = "(defrule direccioDretExtremFora (angle ?a&: (and(>= ?a 0.5)(<= ?a 1.6)))(trackpos ?pos&: (<= ?pos -1.1))" +
		"=> (store steering -1)(store accel 0.4 )(store gear -1)(store brake 0))";
		car.engine.executeCommand(command);
		
		command="(assert (angle -1.3))";
		car.engine.executeCommand(command);
		command="(assert (trackpos -0.9))";
		car.engine.executeCommand(command);


		car.engine.executeCommand("(run)");
		assertEquals(0.0, car.getDoubleVariableFromJess("steering"));
		assertEquals(0.0, car.getDoubleVariableFromJess("accel"));
		assertEquals(0, car.getIntVariableFromJess("gear"));
		assertEquals(0.0, car.getDoubleVariableFromJess("brake"));
	}
	
	
//DE |63�|  A |110�| -> 1.5 a 2 -> si(paret) llavors (marxa enrere i giro el volant)
//								   sino (gira volant i endavant)
	/**
	 * Test the initRule: desquenaDretExtrem i desquenaEsqExtrem.
	 * @throws JessException
	 */
	@Test public void testinitRuledesquenaExtrem() throws JessException {

		car.engine.reset();	
		String command = "(defrule desquenaDretExtrem (angle ?a&: (and(> ?a 1.6)(<= ?a 2)))(trackpos ?pos&: (>= ?pos -0.2))" +
		"=> (store steering 1)(store accel 0.4 )(store gear 1)(store brake 0))";
		car.engine.executeCommand(command);
		
		command="(assert (angle 1.8))";
		car.engine.executeCommand(command);
		command="(assert (trackpos 0.0))";
		car.engine.executeCommand(command);

		car.engine.executeCommand("(run)");
		assertEquals(1.0, car.getDoubleVariableFromJess("steering"));
		assertEquals(0.4, car.getDoubleVariableFromJess("accel"));
		assertEquals(1, car.getIntVariableFromJess("gear"));
		assertEquals(0.0, car.getDoubleVariableFromJess("brake"));
		
		car.engine.clear();
		car.engine.reset();
		
		command = "(defrule desquenaDretExtrem (angle ?a&: (and(> ?a 1.6)(<= ?a 2)))(trackpos ?pos&: (>= ?pos -0.2))" +
		"=> (store steering 1)(store accel 0.4 )(store gear 1)(store brake 0))";
		car.engine.executeCommand(command);
		
		command="(assert (angle -1.3))";
		car.engine.executeCommand(command);
		command="(assert (trackpos -0.9))";
		car.engine.executeCommand(command);

		car.engine.executeCommand("(run)");
		assertEquals(0.0, car.getDoubleVariableFromJess("steering"));
		assertEquals(0.0, car.getDoubleVariableFromJess("accel"));
		assertEquals(0, car.getIntVariableFromJess("gear"));
		assertEquals(0.0, car.getDoubleVariableFromJess("brake"));
	}
	
	/**
	 * Test the initRule: desquenaDretExtremFora i desquenaEsqExtremFora. (Fora de pista)
	 * @throws JessException
	 */
	@Test public void testinitRuledesquenaExtremFora() throws JessException {

		car.engine.reset();	
		String command = "(defrule desquenaDretExtremFora (angle ?a&: (and(> ?a 1.6)(<= ?a 2)))(trackpos ?pos&: (< ?pos -0.2))" +
		"=> (store steering -1)(store accel 0.5 )(store gear -1)(store brake 0))";
		car.engine.executeCommand(command);
		
		command="(assert (angle 1.8))";
		car.engine.executeCommand(command);
		command="(assert (trackpos -0.4))";
		car.engine.executeCommand(command);

		car.engine.executeCommand("(run)");
		assertEquals(-1.0, car.getDoubleVariableFromJess("steering"));
		assertEquals(0.5, car.getDoubleVariableFromJess("accel"));
		assertEquals(-1, car.getIntVariableFromJess("gear"));
		assertEquals(0.0, car.getDoubleVariableFromJess("brake"));
		
		car.engine.clear();
		car.engine.reset();
		
		command = "(defrule desquenaDretExtremFora (angle ?a&: (and(> ?a 1.6)(<= ?a 2)))(trackpos ?pos&: (< ?pos -0.2))" +
		"=> (store steering -1)(store accel 0.5 )(store gear -1)(store brake 0))";
		car.engine.executeCommand(command);
		
		command="(assert (angle -1.3))";
		car.engine.executeCommand(command);
		command="(assert (trackpos -0.9))";
		car.engine.executeCommand(command);

		car.engine.executeCommand("(run)");
		assertEquals(0.0, car.getDoubleVariableFromJess("steering"));
		assertEquals(0.0, car.getDoubleVariableFromJess("accel"));
		assertEquals(0, car.getIntVariableFromJess("gear"));
		assertEquals(0.0, car.getDoubleVariableFromJess("brake"));	
	}
	
	
//DE |110�|  A |180�| -> 2 a PI ->  giro el volant
	/**
	 * Test the initRule: desquena Dret/Esq.
	 * @throws JessException
	 */
	@Test public void testinitRuledesquena() throws JessException {

		car.engine.reset();	
		String command = "(defrule desquenaDret (angle ?a&: (and(> ?a 2)(<= ?a 3.1415)))(trackpos ?pos&: (>= ?pos -0.2))" +
		"=> (store steering 1)(store accel 0.5)(store gear 1)(store brake 0))";
		car.engine.executeCommand(command);
		
		command="(assert (angle 2.8))";
		car.engine.executeCommand(command);
		command="(assert (trackpos 0.0))";
		car.engine.executeCommand(command);

		car.engine.executeCommand("(run)");
		assertEquals(1.0, car.getDoubleVariableFromJess("steering"));
		assertEquals(0.5, car.getDoubleVariableFromJess("accel"));
		assertEquals(1, car.getIntVariableFromJess("gear"));
		assertEquals(0.0, car.getDoubleVariableFromJess("brake"));
		
		car.engine.clear();
		car.engine.reset();
		
		command = "(defrule desquenaDret (angle ?a&: (and(> ?a 2)(<= ?a 3.1415)))(trackpos ?pos&: (>= ?pos -0.2))" +
		"=> (store steering 1)(store accel 0.5)(store gear 1)(store brake 0))";
		car.engine.executeCommand(command);
		
		command="(assert (angle -1.3))";
		car.engine.executeCommand(command);
		command="(assert (trackpos -0.9))";
		car.engine.executeCommand(command);

		car.engine.executeCommand("(run)");
		assertEquals(0.0, car.getDoubleVariableFromJess("steering"));
		assertEquals(0.0, car.getDoubleVariableFromJess("accel"));
		assertEquals(0, car.getIntVariableFromJess("gear"));
		assertEquals(0.0, car.getDoubleVariableFromJess("brake"));	
	}
	
	/**
	 * Test the initRule: desquenaDretFora i desquenaEsqFora. (Fora de pista)
	 * @throws JessException
	 */
	@Test public void testinitRuledesquenaFora() throws JessException {

		car.engine.reset();	
		String command = "(defrule desquenaDretFora (angle ?a&: (and(> ?a 2)(<= ?a 3.1415)))(trackpos ?pos&: (< ?pos -0.2))" +
		"=> (store steering -1)(store accel 0.5)(store gear -1)(store brake 0))";
		car.engine.executeCommand(command);
		
		command="(assert (angle 2.8))";
		car.engine.executeCommand(command);
		command="(assert (trackpos -0.4))";
		car.engine.executeCommand(command);

		car.engine.executeCommand("(run)");
		assertEquals(-1.0, car.getDoubleVariableFromJess("steering"));
		assertEquals(0.5, car.getDoubleVariableFromJess("accel"));
		assertEquals(-1, car.getIntVariableFromJess("gear"));
		assertEquals(0.0, car.getDoubleVariableFromJess("brake"));
		
		car.engine.clear();
		car.engine.reset();
		
		command = "(defrule desquenaDretFora (angle ?a&: (and(> ?a 2)(<= ?a 3.1415)))(trackpos ?pos&: (< ?pos -0.2))" +
		"=> (store steering -1)(store accel 0.5)(store gear -1)(store brake 0))";
		car.engine.executeCommand(command);
		
		command="(assert (angle -1.3))";
		car.engine.executeCommand(command);
		command="(assert (trackpos -0.9))";
		car.engine.executeCommand(command);

		car.engine.executeCommand("(run)");
		assertEquals(0.0, car.getDoubleVariableFromJess("steering"));
		assertEquals(0.0, car.getDoubleVariableFromJess("accel"));
		assertEquals(0, car.getIntVariableFromJess("gear"));
		assertEquals(0.0, car.getDoubleVariableFromJess("brake"));	
		
	}
}

