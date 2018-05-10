package fun_calc;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.math.BigDecimal;
import java.util.*;

class Calculator implements ActionListener {
	JTextField display;
	JLabel history;
	JButton b1,b2,b3,b4,b5,b6,b7,b8,b9,b0,b_add,b_mul,b_sub,b_div,b_del,b_clear,b_openp,b_closep,b_dot,b_eql;
	JRadioButton b_on,b_off;
	JPanel buttonpanel;
	Label author;
	char operation;
	BigDecimal num1,num2,result;
	final int CALCULATE = -1;
	
	boolean on= true; //calculator will be on
	boolean error=false; //becomes true when user enters wrong input
	//arrlist used to store equation entered by user
	String equation = new String("");
	Stack<Character> op = new Stack<Character>();
	Stack<Integer>priority=new Stack<Integer>();
	Stack<BigDecimal> numbers = new Stack<BigDecimal>();
	
	/*create frame, create buttons,add listeners to the buttons
	 * add buttons to the group
	 */
	Calculator ()
    {
	  
	  JFrame jfrm = new JFrame ("Calculator");
	  jfrm.setLayout(new FlowLayout());
	  jfrm.setSize(300,400);
	  jfrm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	  display = new JTextField();
	  display.setColumns(20);
	  display.setHorizontalAlignment(JTextField.RIGHT);
	  jfrm.add(display,BorderLayout.NORTH);
	  history= new JLabel("");
	  history.setBounds(CALCULATE, CALCULATE, CALCULATE, CALCULATE);
	  jfrm.add(history,BorderLayout.EAST);
	  ButtonGroup on_off= new ButtonGroup();
	  buttonpanel = new JPanel();
	  buttonpanel.setLayout(new GridLayout(6,4));
	  jfrm.add(buttonpanel, BorderLayout.CENTER);
	  
	  CreateButtons(); //create buttons
	  AddListener(); //add listeners to them
	  AddRadioButtonsToPanel(on_off); //radiobuttons for on/off
	  AddButtonsToPanel(buttonpanel); //regular buttons for calculator
	  
	  jfrm.setVisible(true);  
  }
  public void actionPerformed(ActionEvent ae)
  {   
	//if user presses a number button then grab the digits they already inputed and add the button they pressed i.e. if user already pressed 1 and now
	  //user presses 0 then update number to 10 rather than just 0
	  if(ae.getActionCommand().equals("0") & on) 
	   {
		  equation= equation+"0";
		  display.setText(display.getText()+"0");
		  history.setText(equation);
	   }
	  if(ae.getActionCommand().equals("1")& on)
	  {
		  equation= equation+"1";
	      display.setText(display.getText()+"1");
	      history.setText(equation);
	  }
	  if(ae.getActionCommand().equals("2")& on)
	  {
		  equation= equation+"2";
	      display.setText(display.getText()+"2");
	      history.setText(equation);
	  }
	  if(ae.getActionCommand().equals("3")& on)
	  {
		  equation= equation+"3";
		  history.setText(equation);
	    display.setText(display.getText()+"3");
	  }
	  if(ae.getActionCommand().equals("4")& on)
	  {
		equation= equation+"4";
		history.setText(equation);
	    display.setText(display.getText()+"4");
	  }
	  if(ae.getActionCommand().equals("5")& on)
	  {
		equation= equation+"5";
		history.setText(equation);
	    display.setText(display.getText()+"5");
	  }
	  if(ae.getActionCommand().equals("6")& on)
	  {
		equation= equation+"6";
		history.setText(equation);
	    display.setText(display.getText()+"6");
	  }
	  if(ae.getActionCommand().equals("7")& on)
	  {
		  equation= equation+"7";
		  history.setText(equation);
	   display.setText(display.getText()+"7");
	  }
	  if(ae.getActionCommand().equals("8")& on)
	  {
		  equation= equation+"8";
		  history.setText(equation);
	   display.setText(display.getText()+"8");
	  }
	  if(ae.getActionCommand().equals("9")& on)
	  {
		  equation= equation+"9";
		  history.setText(equation);
		display.setText(display.getText()+"9");
	  }
	  if(ae.getActionCommand().equals(".")& on)
	  {
		  equation= equation+".";
		  history.setText(equation);
		  display.setText(display.getText()+".");
	  }
	  if(ae.getActionCommand().equals("(")& on)
	  {
		  equation= equation+"(";
		  history.setText(equation);
		  display.setText(display.getText()+"(");
	  }
	  if(ae.getActionCommand().equals(")")& on)
	  {
		  equation= equation+")";
		  history.setText(equation);
		  display.setText(display.getText()+")");
	  }
	  if(ae.getActionCommand().equals("clear")& on)
	  {
		  equation= "";
		  history.setText(equation);
		  error=false;
		  display.setText("");
	  }
	  if(ae.getActionCommand().equals("delete")& on)
	  {
		  //if user presses delete even tho nothing is there then print empty string
		  // if the field is not empty then delete a character
		  if(equation.isEmpty())  display.setText("");
		 else 
		 {
			 //delete last character from equation
		   equation= equation.substring(0,equation.length()-1);
		   history.setText(equation);
		   display.setText(equation);
		 }
	  }
	  if(ae.getActionCommand().equals("+") & on)
	  {
		equation= equation+"+";
		display.setText("");
		history.setText(equation);
		//System.out.println(equation);
	  }
	  if(ae.getActionCommand().equals("-")& on)
	  {  
	    equation= equation+"-";
	    history.setText(equation);
	    display.setText("");
	  }
	  if(ae.getActionCommand().equals("*") & on)
	  {
		equation= equation+"*";
		history.setText(equation);
		display.setText("");
	  }
	  if(ae.getActionCommand().equals("/")& on)
	  {
		equation= equation+"/";
		history.setText(equation);
		display.setText("");
	  }
	  if(ae.getActionCommand().equals("=") & on)
	  {
		int counter=0; //used to know when a number ends
		String grab_text= display.getText();
		if(grab_text.length()> equation.length()) equation= grab_text;
		String org_eq= new String(equation);
		//check to ensure user input does not have 2 operators consectively
		error= checkInput();
		if(equation.isEmpty()){
			error=true;
		}
		while(!equation.isEmpty()& !error)
		{
			while(counter< equation.length()-1 & !(isOperator(equation.charAt(counter)))) counter++; //counter holds where an operator occurs in string 
			if(isOperator(equation.charAt(counter)) & counter==0)
			  {
				char temp2= equation.charAt(counter);
				if(temp2 !='('&& numbers.isEmpty()) error=true;
				else
				{
				 if(temp2!='('&& !priority.isEmpty()&& priority.peek() > priorityop(temp2)) performcalc(priorityop(temp2));
				 priority.add(priorityop(temp2));
				 op.add(temp2);
				 equation= equation.substring(counter+1,equation.length());
				 counter=0;
				 //System.out.println("updated equation--"+equation);
				 //System.out.println("updated stack op is--"+op);
			    }
			  }
			else if(isOperator(equation.charAt(counter)))												// we can derive where # starts and ends.
			{
			  //read numbers
			 //System.out.println(counter);
			 String temp="";
			 temp = temp+ equation.substring(0,counter); //hold first digit in temp
			 BigDecimal num = new BigDecimal(temp);
			 numbers.add(num);
		     ////System.out.println("peek:"+numbers.peek());
		     char operator = equation.charAt(counter);
		      //if-elseif...
		     if(op.isEmpty())
		     {
		       op.add(operator);
		       priority.add(priorityop(operator));
		     } 
		     else if(operator =='+') 
		     {
		      if(priority.peek()> priorityop(operator)) performcalc(priorityop(operator));
		      op.push(operator);
		      priority.push(priorityop(operator));
		     }
		     else if(operator=='-')
		     {
		    	 if(priority.peek()==1 && op.peek()=='+') performcalc(priorityop('-'));
		    	 else if (priority.peek()> priorityop(operator)) performcalc(priorityop(operator));
		    	 op.push(operator);
		    	 priority.push(priorityop(operator));
;		     }
		     else if(operator=='(')
		     {
		    	 op.push(operator);
		    	 priority.push(priorityop(operator));
		     }
		     else if(operator=='*')
		     {
		   	  op.add(operator);
		      priority.add(priorityop(operator));
		   	 }
		     else if(operator=='/')
		     {
		      if(priority.peek()>= priorityop('/')) performcalc(priorityop(operator));
		      op.push(operator);
		      priority.push(priorityop(operator));
		     }
		     else if(operator==')') performcalc(priorityop(operator));
		     
		     //if the equation is not completely computed
		     
		     if(!error && counter+1<=equation.length())equation= equation.substring(counter+1,equation.length());
			 //System.out.println("updated numbers"+numbers);
			 //System.out.println("updated equation"+equation);
			 //System.out.println("updated stack op is"+op);
			 counter=0;	
		   }
			//this state only gets invoked when 1 character digit is @ the end i.e. 45-3
		   if(!error && counter==equation.length()-1 &&(!isOperator(equation.charAt(counter))))
		   {
			   BigDecimal term= new BigDecimal(equation);
			   numbers.push(term);
			   //System.out.println("peek into numbers"+numbers);
			   equation="";
		   }
		}//done of while loop
		//if op is empty then all calculations have been performed
		if (op.isEmpty() & !error) result= numbers.pop();
		else if(!op.isEmpty() && !error){
			performcalc(CALCULATE); //compute the result
			result=numbers.pop();
		}
		if(error) display.setText("Error.Bad input");
		else
		{
		  display.setText(result.toString());
		  equation=result.toString();
		}
		clear_history();
	  }//end of actioncommand for = sign
	  
	  if(ae.getActionCommand().equals("off")){
		  on=false;
		  equation="";
		  display.setText("OFF");
		  history.setText("");
	  }
	  if(ae.getActionCommand().equals("on")){
		  on= true;
		  display.setText("");
		  history.setText("");
	  }
  }
  void performcalc(int oper)
  {
	  boolean done=false;
	  char top_op;
	  int p;
	  while(!priority.isEmpty() && priority.peek()>=oper && !done)
 	  {
		 //System.out.println("performingcalc");
 		 priority.pop();
 		 top_op=op.pop();
 		 //System.out.println("top op is"+top_op);
 		 p = findop(top_op);// returns 3 for multi,4 for div,5 for closed paren.
 		 switch(p)
 		 {
 		  case 0:
			 num2= numbers.pop();
		     num1=numbers.pop();
		     result= num1.add(num2);
		     numbers.push(result);
		     //System.out.println("added 2 numbers."+num1+" "+num2+" result: "+ result);
		     break;
		   
 		  case 1:
		   num2= numbers.pop();
		   num1=numbers.pop();
		   result= num1.subtract(num2);
		   numbers.push(result);
		   //System.out.println("subtracted 2 numbers."+num1+" "+num2+" result: "+ result);
		   break;
		   
		  case 2:
		   done= true;
		   break;

 		  case 3:
 		   num2= numbers.pop();
 		   num1= numbers.pop();
 		   num1= num1.multiply(num2);
 		   numbers.push(num1);
 		   //System.out.println("number in stack"+numbers);
 		  //System.out.println("multiplied 2 numbers."+num1+" "+num2+" result: "+ result);
 		   break;
 		  
 		  case 4:
 		   num2= numbers.pop();
 		   num1=numbers.pop();
 		   try
 		   {
 			  num1= num1.divide(num2);   
 		   }catch(ArithmeticException ae)
 		   {
 			   error=true;
 		   }
		   numbers.push(num1);
		   //System.out.println("number in stack"+numbers);
		   //System.out.println("added 2 numbers."+num1+" "+num2+" result: "+ result);
 		   break;
 		 }
 	  }
  }
  private void CreateButtons()
  {
   b_on= new JRadioButton("on",true);
   b_off= new JRadioButton("off");
   author = new Label("By: Amit Dahal");
   b_openp = new JButton("(");
   b_closep = new JButton(")");
   b_div = new JButton("/");
   b_mul = new JButton("*");
   b_sub = new JButton("-");
   b_add = new JButton("+");
   b_eql = new JButton("=");
   b_dot = new JButton(".");
   b_del = new JButton("delete");
   b_clear = new JButton("clear");
   b1= new JButton("1");
   b2= new JButton("2");
   b3= new JButton("3");
   b4= new JButton("4");
   b5= new JButton("5");
   b6= new JButton("6");
   b7= new JButton("7");
   b8= new JButton("8");
   b9= new JButton("9");
   b0= new JButton("0");
  }
  private void AddListener()
  {
   b1.addActionListener(this);
   b2.addActionListener(this);
   b3.addActionListener(this);
   b4.addActionListener(this);
   b5.addActionListener(this);
   b6.addActionListener(this);
   b7.addActionListener(this);
   b8.addActionListener(this);
   b9.addActionListener(this);
   b0.addActionListener(this);
   b_del.addActionListener(this);
   b_clear.addActionListener(this);
   b_on.addActionListener(this);
   b_off.addActionListener(this);
   b_add.addActionListener(this);
   b_sub.addActionListener(this);
   b_div.addActionListener(this);
   b_mul.addActionListener(this);
   b_eql.addActionListener(this);
   b_dot.addActionListener(this);
   b_openp.addActionListener(this);
   b_closep.addActionListener(this);
  }
  private void AddButtonsToPanel(JPanel buttonpanel)
  {
   buttonpanel.add(b_on);
   buttonpanel.add(b_off);
   buttonpanel.add(b_clear);
   buttonpanel.add(b_del);
   buttonpanel.add(b1);
   buttonpanel.add(b2);
   buttonpanel.add(b3);
   buttonpanel.add(b_add);
   buttonpanel.add(b4);
   buttonpanel.add(b5);
   buttonpanel.add(b6);
   buttonpanel.add(b_sub);
   buttonpanel.add(b7);
   buttonpanel.add(b8);
   buttonpanel.add(b9);
   buttonpanel.add(b_mul);
   buttonpanel.add(b0);
   buttonpanel.add(b_openp);
   buttonpanel.add(b_closep);
   buttonpanel.add(b_dot);
   buttonpanel.add(b_eql);
   buttonpanel.add(b_div);
  }
  private void AddRadioButtonsToPanel(ButtonGroup on_off)
  {
	on_off.add(b_on);
	on_off.add(b_off);
  }
  //checks to see if user inputed 2 operators back to back i.e.1**3 or 1/+3
  boolean checkInput()
  {
	 error=false;
	 if(!equation.isEmpty())
     {
 	  char temp1;
	  char temp2;
	  for(int i=0; i <equation.length()-1& !error;i++)
	   {
		 temp1=equation.charAt(i);
		 temp2=equation.charAt(i+1);
		 if(isOperator(temp1) && isOperator(temp2))
		 {
		  if(priorityop(temp1)>0 && priorityop(temp2)>0) error=true;
		 }
	   }
	  }
	 return error;
  }
  void clear_history()
  {
	  if(!numbers.isEmpty())  numbers.clear();
	  if(!op.isEmpty())  op.clear();
	  if(!priority.isEmpty())  priority.clear();
  }
  boolean isOperator(char c)
  {
	if(c=='+') return true;
	else if(c=='-') return true;
	else if(c=='*') return true;
	else if(c=='/') return true;
	else if(c=='(') return true;
	else if(c==')') return true;
	else return false;
  }
  
  int priorityop(char c)
  {
	  if(c=='+') return 1;
	  else if(c=='-') return 2;
	  else if(c=='(') return 0;
	  else if(c=='*') return 3;
	  else if(c=='/') return 3;
	  else if (c==')') return -1;
	  else return -4;
  }
  int findop(char c)
  {
	if(c=='+') return 0;
	else if(c=='-') return 1;
	else if(c=='(') return 2;
	else if(c=='*') return 3;
	else if(c=='/') return 4;
	else if (c==')') return 5;
	else return -1;
  }
}
