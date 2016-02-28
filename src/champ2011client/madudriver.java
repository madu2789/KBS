package champ2011client;

import javax.sound.midi.Track;

import jess.*;

public class madudriver extends Controller {

	String command;
	Rete engine = new Rete();
	Fact f;
	double rev = 0;
	double vel = 0;
	double fre = 0;
	double ang = 0;
	double pos = 0;
	int marxa = 0;
	double[] track;
	
	 
	public float[] initAngles()	{
			
			float[] angles = new float[19];

			/* set angles as {-90,-75,-60,-45,-30,-20,-15,-10,-5,0,5,10,15,20,30,45,60,75,90} */
			for (int i=0; i<5; i++)
			{
				angles[i]=-90+i*15;
				angles[18-i]=90-i*15;
			}

			for (int i=5; i<9; i++)
			{
					angles[i]=-20+(i-5)*5;
					angles[18-i]=20-(i-5)*5;
			}
			angles[9]=0;
			return angles;
	}
	public void iniJess() throws JessException{
    	try{
    		engine.executeCommand("(clear)");
    		engine.executeCommand("(reset)");
    	}catch (Exception e) {
	    	e.printStackTrace();
	    }
	}
	public void initRules () throws JessException {
	    	 		
			try{
	    	
				//if () then (store gear 2)else if () then (store gear 2)else 
				
				//regles del diagrama
				
				// recte pero veu una corba endavant
				
				//DE |-8º|  A |8| -> 0.17 a 0.17 
				
				//recte i no es desvia marxa1
				command = "(defrule direRectemarxa1 (angle ?a&: (and(< ?a 0.17)(> ?a -0.17)))(obsEsq ?oE&: (>= ?oE 15))(obsDret ?oD&: (>= ?oD 15))(velocitat ?v&: (< ?v 80))" +
						"=> (store steering 0)(store accel 0.5 )(store gear 1)(store brake 0))";
				engine.executeCommand(command);	
				
				//recte i no es desvia marxa2
				command = "(defrule direRectemarxa2 (angle ?a&: (and(< ?a 0.17)(> ?a -0.17)))(obsEsq ?oE&: (>= ?oE 15))(obsDret ?oD&: (>= ?oD 15))(velocitat ?v&: (and(> ?v 80)(< ?v 100)))" +
						"=> (store steering 0)(store accel 0.5 )(store gear 2)(store brake 0))";
				engine.executeCommand(command);	
				
				//recte i no es desvia marxa3
				command = "(defrule direRectemarxa3 (angle ?a&: (and(< ?a 0.17)(> ?a -0.17)))(obsEsq ?oE&: (>= ?oE 15))(obsDret ?oD&: (>= ?oD 15))(velocitat ?v&: (and(> ?v 100)(< ?v 140)))" +
						"=> (store steering 0)(store accel 0.5 )(store gear 3)(store brake 0))";
				engine.executeCommand(command);	
				
				//recte i no es desvia marxa4
				command = "(defrule direRectemarxa4 (angle ?a&: (and(< ?a 0.17)(> ?a -0.17)))(obsEsq ?oE&: (>= ?oE 15))(obsDret ?oD&: (>= ?oD 15))(velocitat ?v&: (> ?v 140))" +
						"=> (store steering 0)(store accel 0.5 )(store gear 4)(store brake 0))";
				engine.executeCommand(command);	
				
					//obs esq
				
				command = "(defrule direRecteCorbaDret (angle ?a&: (and(< ?a 0.17)(> ?a -0.17)))(obsEsq ?oE&: (> ?oE 15))(obsDret ?oD&: (< ?oD 15))" +
						"=> (store steering 0.1)(store accel 0.25 )(store gear 1)(store brake 0))";
				engine.executeCommand(command);
				
					//obs dreta
				
				command = "(defrule direRecteCorbaEsq (angle ?a&: (and(< ?a 0.17)(> ?a -0.17)))(obsEsq ?oE&: (< ?oE 15))(obsDret ?oD&: (> ?oD 15))" +
				"=> (store steering -0.1)(store accel 0.25 )(store gear 1)(store brake 0))";
				engine.executeCommand(command);
				
				//fora de pista recte
				
				command = "(defrule direRecteFora (angle ?a&: (and(< ?a 0.17)(> ?a -0.17)))(obsEsq ?oE&: (< ?oE 15))(obsDret ?oD&: (< ?oD 15))(trackpos ?pos&: (and(<= ?pos 0.8)(>= ?pos -0.8)))" +
						"=> (store steering 0)(store accel 0.2 )(store gear 1)(store brake 0))";
				engine.executeCommand(command);
							
					//fora de pista esq
				
				command = "(defrule direRecteParetEsq (angle ?a&: (and(< ?a 0.17)(> ?a -0.17)))(obsEsq ?oE&: (< ?oE 15))(obsDret ?oD&: (< ?oD 15))(trackpos ?pos&: (> ?pos 0.8))" +
				"=> (store steering -0.2)(store accel 0.2 )(store gear 1)(store brake 0))";
				engine.executeCommand(command);
				
					//fora de pista dret
				
				command = "(defrule direRecteParetDret (angle ?a&: (and(< ?a 0.17)(> ?a -0.17)))(obsEsq ?oE&: (< ?oE 15))(obsDret ?oD&: (< ?oD 15))(trackpos ?pos&: (< ?pos -0.8))" +
				"=> (store steering 0.2)(store accel 0.2 )(store gear 1)(store brake 0))";
				engine.executeCommand(command);
				
				//regles direccio altres angles

				
				//DE |8º|  A |36º| -> 0.17 a 0.5 
				command = "(defrule direccioDret (angle ?a&: (and(>= ?a 0.17)(< ?a 0.5)))(obsEsq ?oE&: (>= ?oE 15))(obsDret ?oD&: (>= ?oD 15))" +
						"=> (store steering 0.2)(store accel 0.2 )(store gear 1)(store brake 0))";
				engine.executeCommand(command);
				
				command = "(defrule direccioEsq (angle ?a&: (and(<= ?a -0.17)(> ?a -0.5)))(obsEsq ?oE&: (>= ?oE 15))(obsDret ?oD&: (>= ?oD 15))" +
						"=> (store steering -0.2)(store accel 0.2 )(store gear 1)(store brake 0))";
				engine.executeCommand(command);
				
				//desviat a la dreta amb corbes
				
				command = "(defrule direccioDretCorbaDret (angle ?a&: (and(>= ?a 0.17)(< ?a 0.5)))(obsEsq ?oE&: (> ?oE 15))(obsDret ?oD&: (< ?oD 15))" +
						"=> (store steering 0.2)(store accel 0.2 )(store gear 1)(store brake 0))";
				engine.executeCommand(command);
				
				command = "(defrule direccioDretCorbaEsq (angle ?a&: (and(>= ?a 0.17)(< ?a 0.5)))(obsEsq ?oE&: (< ?oE 15))(obsDret ?oD&: (> ?oD 15))" +
				"=> (store steering -0.5)(store accel 0.2 )(store gear 1)(store brake 0))";
				engine.executeCommand(command);
						
				//desviat a l'esquerra amb corbes
				
				command = "(defrule direccioEsqCorbaDret (angle ?a&: (and(<= ?a -0.17)(> ?a -0.5)))(obsEsq ?oE&: (> ?oE 15))(obsDret ?oD&: (< ?oD 15))" +
						"=> (store steering 0.5)(store accel 0.2 )(store gear 1)(store brake 0))";
				engine.executeCommand(command);
				
				command = "(defrule direccioEsqCorbaEsq (angle ?a&: (and(<= ?a -0.17)(> ?a -0.5)))(obsEsq ?oE&: (< ?oE 15))(obsDret ?oD&: (> ?oD 15))" +
						"=> (store steering -0.2)(store accel 0.2 )(store gear 1)(store brake 0))";
				engine.executeCommand(command);
						
				//fora de pista i segueix recte
				
				command = "(defrule direccioForaRecD (angle ?a&: (and(>= ?a 0.17)(< ?a 0.5)))(obsEsq ?oE&: (< ?oE 15))(obsDret ?oD&: (< ?oD 15))(trackpos ?pos&: (and(<= ?pos 0.8)(>= ?pos -0.8)))" +
						"=> (store steering 0.1)(store accel 0.2 )(store gear 1)(store brake 0))";
				engine.executeCommand(command);
				
				command = "(defrule direccioForaRecE (angle ?a&: (and(<= ?a -0.17)(> ?a -0.5)))(obsEsq ?oE&: (< ?oE 15))(obsDret ?oD&: (< ?oD 15))(trackpos ?pos&: (and(<= ?pos 0.8)(>= ?pos -0.8)))" +
						"=> (store steering -0.1)(store accel 0.2 )(store gear 1)(store brake 0))";
				engine.executeCommand(command);
				
					//dire dret,  fora dret i gira esq
				
				command = "(defrule direccioDretForaDretGirEsq (angle ?a&: (and(>= ?a 0.17)(< ?a 0.5)))(obsEsq ?oE&: (< ?oE 15))(obsDret ?oD&: (< ?oD 15))(trackpos ?pos&: (< ?pos -0.8))" +
				"=> (store steering 0.7)(store accel 0.3)(store gear 1)(store brake 0))";
				engine.executeCommand(command);
		
					//dire esq,	fora esq i gira dreta
				
				command = "(defrule direccioEsqForaEsqGirDret (angle ?a&: (and(<= ?a -0.17)(> ?a -0.5)))(obsEsq ?oE&: (< ?oE 15))(obsDret ?oD&: (< ?oD 15))(trackpos ?pos&: (> ?pos 0.8))" +
				"=> (store steering -0.7)(store accel 0.3)(store gear 1)(store brake 0))";
				engine.executeCommand(command);
				
					//dire dret,  fora esq i gira dret(poc)
				
				command = "(defrule direccioDretForaEsq (angle ?a&: (and(>= ?a 0.17)(< ?a 0.5)))(obsEsq ?oE&: (< ?oE 15))(obsDret ?oD&: (< ?oD 15))(trackpos ?pos&: (> ?pos 0.8))" +
				"=> (store steering -0.2)(store accel 0.2 )(store gear 1)(store brake 0))";
				engine.executeCommand(command);
		
					//dire esq,	fora dreta i gira esq (poc)
				
				command = "(defrule direccioEsqForaDret (angle ?a&: (and(<= ?a -0.17)(> ?a -0.5)))(obsEsq ?oE&: (< ?oE 15))(obsDret ?oD&: (< ?oD 15))(trackpos ?pos&: (< ?pos -0.8))" +
				"=> (store steering 0.2)(store accel 0.2 )(store gear 1)(store brake 0))";
				engine.executeCommand(command);
							
				
				//DE |36º|  A |80º| -> 0.5 a 1.6 
				command = "(defrule direccioDretExtrem (angle ?a&: (and(>= ?a 0.5)(<= ?a 1.6)))(trackpos ?pos&: (> ?pos -1.1))" +
						"=> (store steering 0.7)(store accel 0.4 )(store gear 1)(store brake 0))";
				engine.executeCommand(command);
				
				command = "(defrule direccioEsqExtrem (angle ?a&: (and(<= ?a -0.5)(>= ?a -1.6)))(trackpos ?pos&: (< ?pos 1.1))" +
						"=> (store steering -0.7)(store accel 0.4 )(store gear 1)(store brake 0))";
				engine.executeCommand(command);
				
					//fora de pista
				
				command = "(defrule direccioDretExtremFora (angle ?a&: (and(>= ?a 0.5)(<= ?a 1.6)))(trackpos ?pos&: (<= ?pos -1.1))" +
						"=> (store steering -1)(store accel 0.4 )(store gear -1)(store brake 0))";
				engine.executeCommand(command);
				
				command = "(defrule direccioEsqExtremFora (angle ?a&: (and(<= ?a -0.5)(>= ?a -1.6)))(trackpos ?pos&: (>= ?pos 1.1))" +
						"=> (store steering 1)(store accel 0.4 )(store gear -1)(store brake 0))";
				engine.executeCommand(command);
				
			
				//DE |63º|  A |110º| -> 1.5 a 2 -> si(paret) llavors (marxa enrere i giro el volant)
				//								   sino (gira volant i endavant)
				
				command = "(defrule desquenaDretExtrem (angle ?a&: (and(> ?a 1.6)(<= ?a 2)))(trackpos ?pos&: (>= ?pos -0.2))" +
						"=> (store steering 1)(store accel 0.4 )(store gear 1)(store brake 0))";
				engine.executeCommand(command);
				
				command = "(defrule desquenaEsqExtrem (angle ?a&: (and(< ?a -1.6)(>= ?a -2)))(trackpos ?pos&: (<= ?pos 0.2))" +
						"=> (store steering -1)(store accel 0.4 )(store gear 1)(store brake 0))";
				engine.executeCommand(command);		
				
					//fora de pista
				
				command = "(defrule desquenaDretExtremFora (angle ?a&: (and(> ?a 1.6)(<= ?a 2)))(trackpos ?pos&: (< ?pos -0.2))" +
						"=> (store steering -1)(store accel 0.5 )(store gear -1)(store brake 0))";
				engine.executeCommand(command);
				
				command = "(defrule desquenaEsqExtremFora (angle ?a&: (and(< ?a -1.6)(>= ?a -2)))(trackpos ?pos&: (> ?pos 0.2))" +
						"=> (store steering 1)(store accel 0.5 )(store gear -1)(store brake 0))";
				engine.executeCommand(command);		
				
				//DE |110º|  A |180º| -> 2 a PI ->  giro el volant
				
				command = "(defrule desquenaDret (angle ?a&: (and(> ?a 2)(<= ?a 3.1415)))(trackpos ?pos&: (>= ?pos -0.2))" +
						"=> (store steering 1)(store accel 0.5)(store gear 1)(store brake 0))";
				engine.executeCommand(command);
				
				command = "(defrule desquenaEsq (angle ?a&: (and(< ?a -2)(>= ?a -3.1415)))(trackpos ?pos&: (<= ?pos 0.2))" +
						"=> (store steering -1)(store accel 0.5)(store gear 1)(store brake 0))";
				engine.executeCommand(command);
				
				// fora de pista
				
				command = "(defrule desquenaDretFora (angle ?a&: (and(> ?a 2)(<= ?a 3.1415)))(trackpos ?pos&: (< ?pos -0.2))" +
						"=> (store steering -1)(store accel 0.5)(store gear -1)(store brake 0))";
				engine.executeCommand(command);
				
				command = "(defrule desquenaEsqFora (angle ?a&: (and(< ?a -2)(>= ?a -3.1415)))(trackpos ?pos&: (> ?pos 0.2))" +
						"=> (store steering 1)(store accel 0.5)(store gear -1)(store brake 0))";
				engine.executeCommand(command);
				

			 }catch (Exception e) {
			    	e.printStackTrace();
			 }
	}
	
	public void Facts (SensorModel sensor) throws JessException{
		
		try{
			// per si vull treureo per pantalla
			vel = sensor.getSpeed();
		    ang = (double)sensor.getAngleToTrackAxis();
		    rev = sensor.getRPM();
		    pos = sensor.getTrackPosition();
		    marxa = sensor.getGear();
		    track = sensor.getTrackEdgeSensors();
		    
		    
			//fact de velocitat
			f = new Fact("velocitat", engine);
			f.setSlotValue("__data", new Value(new ValueVector(). 
	
					add(new Value(vel, RU.FLOAT)), RU.LIST));
			engine.assertFact(f);
		
			//fact de revolucions
			f = new Fact("revolucions", engine);
			f.setSlotValue("__data", new Value(new ValueVector(). 
	
					add(new Value(rev, RU.FLOAT)), RU.LIST));
			engine.assertFact(f);
		
			//fact d'angle
			f = new Fact("angle", engine);
			f.setSlotValue("__data", new Value(new ValueVector(). 
	
					add(new Value(ang, RU.FLOAT)), RU.LIST));
			engine.assertFact(f);
			
			//fact fre
			f = new Fact("fre", engine);
			f.setSlotValue("__data", new Value(new ValueVector(). 
	
					add(new Value(fre, RU.FLOAT)), RU.LIST));
			engine.assertFact(f);
			
			//fact trackpos
			f = new Fact("trackpos", engine);
			f.setSlotValue("__data", new Value(new ValueVector(). 
	
					add(new Value(pos, RU.FLOAT)), RU.LIST));
			engine.assertFact(f);
			
			//fact marxa
			f = new Fact("marxa", engine);
			f.setSlotValue("__data", new Value(new ValueVector(). 
	
					add(new Value(marxa, RU.INTEGER)), RU.LIST));
			engine.assertFact(f);
			
			//facts de track
			
			f = new Fact("obsDavan", engine);
			f.setSlotValue("__data", new Value(new ValueVector(). 
	
					add(new Value(track[10], RU.FLOAT)), RU.LIST));
			engine.assertFact(f);
			
			f = new Fact("obsDret", engine);
			f.setSlotValue("__data", new Value(new ValueVector(). 
	
					add(new Value(track[12], RU.FLOAT)), RU.LIST));
			engine.assertFact(f);
			
			f = new Fact("obsEsq", engine);
			f.setSlotValue("__data", new Value(new ValueVector(). 
	
					add(new Value(track[8], RU.FLOAT)), RU.LIST));
			engine.assertFact(f);	
			
			
			
		 }catch (Exception e) {
		    	e.printStackTrace();
		 }
	}

	public Action control(SensorModel sensor) {
	     
		Action action = new Action ();   
		
		try{
		
	    Value v;    
		//System.out.println(ang);
	    	
	    
	    	iniJess();
	    	initRules();	
	    	Facts(sensor);
	    	System.out.println("angle:" +  ang + "\tPosicio: " + pos + "\tobsesq: " + track[8] + "\tobsdret: " + track[12] );
	    	
		    engine.executeCommand("(run)");
		    
		    v = engine.fetch("accel");		   
		    action.accelerate = v.floatValue(engine.getGlobalContext());
		    	
		    v = engine.fetch("gear");
		    action.gear = v.intValue(engine.getGlobalContext());
    	
		    v = engine.fetch("steering");
		    action.steering = v.floatValue(engine.getGlobalContext());
		    //System.out.println(v.floatValue(engine.getGlobalContext()));
		    
		    v = engine.fetch("brake");
		    action.brake = v.floatValue(engine.getGlobalContext());
    	
	    	
	    	
	    }catch (Exception e) {
	    	e.printStackTrace();
	    }
	    return action;
	}

	public void reset() {
		System.out.println("Restarting the race!");
			
	}

	public void shutdown() {
		System.out.println("Bye bye!");		
	}
	

	/**
	 * Additional functions for managing the JessEngine object
	 */

	/***
	 * Return the value of a variable stored in JESS
	 * @param name the value name
	 * @return the double value
	 */
	
	 public double getDoubleVariableFromJess (String name) {
	 	Value v;
	 	double variableValue;
	 	try {
		    	v = engine.fetch(name);
		    	variableValue= v.floatValue(engine.getGlobalContext());
	 	}catch (Exception e) {      //There is not any decision to do about, the value is not modified    		
	 		variableValue=0;
	 	}
	 	return variableValue;
	 }
	 
	 /***
	 * Return the value of a variable stored in JESS
	 * @param name the value name
	 * @return the double value
	 */
	 
	public int getIntVariableFromJess (String name) {
	  	Value v;
	  	int variableValue;
	  	try {
		    	v = engine.fetch(name);
		    	variableValue= v.intValue(engine.getGlobalContext());
	  	}catch (Exception e) {     		//There is not any decision to do about, the value is not modified
	  		variableValue=0;
	  	}
	  	return variableValue;
	  }
	 
	/**
	 * Retrieve the fact associated to a fact_Id
	 * @param factID is the identification of the fact in the working memory
	 * @return the fact associated
	 * @throws JessException
	 */

	public Fact findFactByIDinJess(int factID) throws JessException {
		return engine.findFactByID(factID);
	}
	


}
