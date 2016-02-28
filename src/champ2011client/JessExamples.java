/**
 * 
 */
package champ2011client;

import jess.*;
import java.awt.Button;
import java.awt.Color;
import java.io.StringWriter;


/**
 * @author afornells
 *
 */
public class JessExamples {
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Rete engine=new Rete();
		
		try  {
			String command;
			Value v;
			Fact f;
			int intValue;
					
			//
			//Example about how to add a fact into memory and print the result
			//
			command="(assert (color red))";
			v=engine.executeCommand(command);
			f=v.factValue(engine.getGlobalContext());
			System.out.println("Return:"+f.toString());
			
			//
			//Example of passing a java object to JESS. First, the example is added. Second, the object is retrieved and printed inside JESS environment
			// The same in JESS: (store symbol value)—Stores a value in a hash table, from Jess
			// The same in JESS: (fetch symbol)—Retrieves a value from the hash table, from Jess
			//
			Color pink=new Color(255,200,200);
			engine.store("pink",pink);
			command="(assert (color (fetch pink)))";
			v=engine.executeCommand(command);
			f=v.factValue(engine.getGlobalContext());
			System.out.println("Return:"+f.toString());
			engine.reset();
			
			//
			//Example of retrieving information from JESS
			//
			command="(assert (numbers 2 2))";
			engine.executeCommand(command);
			
			command = "(defrule add-numbers" +
			"(numbers ?n1 ?n2)" +
			"=>" +
			"(store SUM (+ ?n1 ?n2)))";
				// Use executeCommand to define a rule
			engine.executeCommand(command);
				// The rule fires and stores the result
			engine.executeCommand("(run)");
			v = engine.fetch("SUM");
			intValue = v.intValue(engine.getGlobalContext()); 
			System.out.println("The sum of elements: "+intValue);
			engine.reset();
			
			//
			//Example of how to create a Deftemplate with two slots and then assert several facts that use it for, finally, retract some of them
			//
			Deftemplate d =	new Deftemplate("person", "A person", engine);
				//If you don’t specify a value for a slot, the default value is used when the Fact is asserted.
			d.addSlot("name", Funcall.NIL, "STRING");
			d.addSlot("address", Funcall.NIL, "STRING");
			engine.addDeftemplate(d);
				
			String[][] data = {
			{"Joe Smith", "123 Main Street"},
			{"Fred Jones", "333 Elm Circle"},
			{"Bob Weasley", "211 Planet Way"},
			};
			for (int i=0; i<data.length; ++i) {
				f = new Fact("person", engine);
				f.setSlotValue("name",new Value(data[i][0], RU.STRING));
				f.setSlotValue("address",new Value(data[i][1], RU.STRING));
				engine.assertFact(f);
			}
			engine.executeCommand("(facts)");
				//Each fact is retracted from several ways
			engine.retract(f);
			engine.retractString("(person (name \"Joe Smith\") (address \"123 Main Street\"))");
			engine.retract(engine.findFactByID(2));	//This is the slowest method
			engine.executeCommand("(facts)");		//This is the fastest method
			engine.reset();
			
			//
			//Example of how to work with multislots
			//
			d =	new Deftemplate("student", "A student", engine);
			d.addSlot("name", Funcall.NIL, "STRING");
			d.addMultiSlot("courses", Funcall.NILLIST);
			engine.addDeftemplate(d);
			f = new Fact("student", engine);
			f.setSlotValue("name", new Value("Fred Smith", RU.STRING));
			ValueVector courses = new ValueVector();
			courses.add(new Value("COMP 101", RU.STRING));
			courses.add(new Value("HISTORY 202", RU.STRING));
			f.setSlotValue("courses", new Value(courses, RU.LIST));
			engine.assertFact(f);
			engine.executeCommand("(facts)");
			engine.reset();
			
			//
			//Example of how to work with ordered facts
			//
			f = new Fact("shopping-list", engine);
			f.setSlotValue("__data", new Value(new ValueVector().
			add(new Value("bread", RU.ATOM)).
			add(new Value("milk", RU.ATOM)).
			add(new Value("jam", RU.ATOM)), RU.LIST));
			engine.assertFact(f);
			engine.executeCommand("(facts)");
			engine.reset();
			
			//
			//Example about how to work with JavaBeans
			//
				//You don’t want this defclass to extend another one, 
				//so you pass null as the last argument to the defclass function.
			engine.defclass("button", "java.awt.Button", null);
			Button b = new Button("OK");
			engine.definstance("button", b, true);
			 	//Because java.awt.Button sends PropertyChangeEvents, 
				//you can pass true as the last argument to definstance, making this a dynamic instance
			engine.executeCommand("(facts)");
			engine.reset();
			
			//
			//Example about how to call functions from Java
			//Comment:
			//  - Some Jess functions are avaible from Rete class. See Jess API
			//	- The rest are implemented through Userfunction class.
			//
			Context context = engine.getGlobalContext();
			Funcall func = new Funcall("watch", engine);
			func.arg(new Value("all"));
			func.execute(context);
			//Equivalent to new Funcall("watch", engine).arg("all").execute(context);
			
			//
			//Example about how to manage Jess exception
			//
			try {
				engine.executeCommand("(* 1 2)\n(* 3 4)\n(* a b)");
			} catch (JessException je) {
				System.out.print("An error occurred at line " +
						je.getLineNumber());
				System.out.println(" which looks like " +
						je.getProgramText ());
				System.out.println("Message: " + je.getMessage());
			}	
			
			//
			//Example about how to manage Nested Jess exception
			//
			try {
				engine.executeCommand("(new java.net.URL foo://bar)");
			} catch (JessException je) {
				System.out.println("Global message: "+je.getMessage());
				System.out.println("Accurate message:"+ je.getCause().getMessage());
			}
			
			
			try {
				int a=2/0;
				// use the socket ...
				} catch (Exception ex) {
					try {
						System.out.println("An exception is detected and, then, an exception is generated");
						throw new JessException("numbers", "Undefined value", ex);
					}catch(JessException je) {
						System.out.println("Current exception: "+je.getMessage());
						System.out.println("Original exception: "+je.getNextException());
					}
				}
			
			//
			//Example about how to redirect input/output of Jess
			//
			StringWriter sw = new StringWriter();
			engine.addOutputRouter("out", sw);
			command="(printout out 12345 crlf)";
			engine.executeCommand(command);
			command="(printout out jurjur crlf)";
			engine.executeCommand(command);
			System.out.println("The output is catched: "+sw.toString());
			
		}catch (Exception e) {
			e.printStackTrace();
		}

	}

}
