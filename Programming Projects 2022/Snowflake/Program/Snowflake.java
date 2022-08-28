import java.awt.*;
import java.awt.geom.Line2D;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.*;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import java.math.*;
import javax.swing.*;   
import java.awt.event.*;     

/**
 * This program draw a Koch Snowflake for a given order using an interactive interface
 * @author Mohammed El Saka
 *
 */
public class Snowflake extends JFrame {

  private static double startOfX = 0;
  private static double startOfY = 0;
  private static double length = 0;
  private static double angle = 0;
  private static int depth = 1;
  private static int prevOrder = 0;
  private static double endOfX = 0;
  private static double endOfY = 0;
  private static double direction = 0;
  private static ArrayList<ArrayList<Double>> lines = new ArrayList<ArrayList<Double>>();
  private static ArrayList<ArrayList<Double>> prevLines = new ArrayList<ArrayList<Double>>();
  private static DecimalFormat df = new DecimalFormat();
  private static JButton b,close;
  private static JTextField t;
  private static JLabel l,er;
  private static int order = 1;
  private static boolean less = false;
  private  static int currOrder = 0;
/**
 * This sets the user interface layout.
 */
  public Snowflake() {
    super("Koch Snowflake");
    //super.setUndecorated(true);
    setSize(1000, 1300);
    setVisible(true);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
    b = new JButton("Enter");   // Enter button 
    b.setBounds(800,100,100, 40);    
    super.add(b); 
    close = new JButton("EXIT");   // Exit button 
    close.setBounds(0,0,100, 40); 
    close.setForeground(Color.RED);     
    super.add(close);          
    t =new JTextField("");  // text field
    t.setBounds(700,100,100, 40);    
    super.add(t);  
    super.setLayout(null);   
    l = new JLabel(""); //label
    Font font = new Font("SansSerif", Font.BOLD, 30);
    l.setText("Order = " + Integer.toString(order));
    l.setFont(font);
    l.setBounds(400,30,200, 40);    
    super.add(l);  
    super.setLayout(null); 

    er = new JLabel("error"); //error message label
    er.setBounds(350,100,400, 40);  
    er.setForeground(Color.RED);  
    super.add(er);
    er.setVisible(false); 

   /**
 * actions performed when button is clicked
 */
    b.addActionListener(new ActionListener(){  
      public void actionPerformed(ActionEvent e){  
          setVisible(false);
          if((ContainsJustDigits(t.getText())) == false|| t.getText().equals("")){//checks if input is only a number
            er.setText("ERROR: Input is not a number. ");  // shows error
            er.setVisible(true); 
            setVisible(true);
         }else if(Integer.valueOf(t.getText())>11){ //checks if order is only a number
          er.setText("ERROR: Program can only draw until the 11th order");  
          er.setVisible(true); 
          setVisible(true);
         }else{
          order = Integer.valueOf(t.getText());  
          l.setText("Order = " + Integer.toString(order));  
          Snowflake.main(new String[] {});     // run main method
         }
        }  
      }); 
      close.addActionListener(new ActionListener(){  //click close
        public void actionPerformed(ActionEvent e){  
          System.exit(0); // exit program
          }  
        }); 
  }
/**
     * Draws all the lines that make the shape
     * 
     * @param g 
     */
  void drawLines(Graphics g) {
    Graphics2D g2d = (Graphics2D) g;

    for (int i = 0; i < lines.size(); i++) {
      //setting parameters of the line
      startOfX = lines.get(i).get(0); 
      startOfY = lines.get(i).get(1);
      length = lines.get(i).get(2);
      angle = lines.get(i).get(3);
      df.setMaximumFractionDigits(3);
      endOfX = startOfX + Double.valueOf(df.format(Math.cos(Math.toRadians(angle)) * length));
      endOfY = startOfY + Double.valueOf(df.format(Math.sin(Math.toRadians(angle)) * length));

      g2d.setRenderingHint(
        RenderingHints.KEY_ANTIALIASING,
        RenderingHints.VALUE_ANTIALIAS_ON
      );
      //g2d.setColor(Color.red);
      Shape l = new Line2D.Double(startOfX, startOfY, endOfX, endOfY);
      g2d.draw(l);
    }
  }

  public void paint(Graphics g) {
    super.paint(g);
    drawLines(g);
  }
/**
     * sets the dimensions of the the triangle for order 0
     * calls method for generating child shape from parent shape
     * @param args 
     */
  public static void main(String[] args) {

 if ( order< prevOrder){ // checks if the previous order is less than the current order
  currOrder = order; 
  order = 1;
   less = true;
 }
    if(order == 1){ // if its the first order add the lines for the trinagle in arraylist
    depth=1;
    lines.clear();
    prevLines.clear();
    //base line
    lines.add(new ArrayList<Double>());
    lines.get(0).add(0, 200.0);
    lines.get(0).add(1, 800.0);
    lines.get(0).add(2, 600.0);
    lines.get(0).add(3, 360.0);
    lines.get(0).add(4, 1.0);
    // left line
    lines.add(new ArrayList<Double>());
    lines.get(1).add(0, 200.0);
    lines.get(1).add(1, 800.0);
    lines.get(1).add(2, 600.0);
    lines.get(1).add(3, 300.0);
    lines.get(1).add(4, 0.0);
    //right line
    lines.add(new ArrayList<Double>());
    lines.get(2).add(0, 800.0);
    lines.get(2).add(1, 800.0);
    lines.get(2).add(2, 600.0);
    lines.get(2).add(3, 240.0);
    lines.get(2).add(4, 1.0);
  }else{   
    while (depth < order) { // while the current depth is not the selected Order
      prevLines.clear();
      
      for (int i = 0; i < lines.size(); i++) { //store current lines as previous
        prevLines.add(new ArrayList<Double>());
        prevLines.get(i).add(0, lines.get(i).get(0));
        prevLines.get(i).add(1, lines.get(i).get(1));
        prevLines.get(i).add(2, lines.get(i).get(2));
        prevLines.get(i).add(3, lines.get(i).get(3));
        prevLines.get(i).add(4, lines.get(i).get(4));
      }
      
      lines.clear();
      nexShape(prevLines); // call method to generate child lines
      depth++;
      }
    }
      if (less == true){ // if the selected order is lower than the current order
        order = currOrder;
        while (depth < order) {
          prevLines.clear();
          
          for (int i = 0; i < lines.size(); i++) {
            prevLines.add(new ArrayList<Double>());
            prevLines.get(i).add(0, lines.get(i).get(0));
            prevLines.get(i).add(1, lines.get(i).get(1));
            prevLines.get(i).add(2, lines.get(i).get(2));
            prevLines.get(i).add(3, lines.get(i).get(3));
            prevLines.get(i).add(4, lines.get(i).get(4));
          }
          lines.clear();
          nexShape(prevLines); 
          depth++;
      }
      less = false;
    }

    
      
  SwingUtilities.invokeLater( 
    new Runnable() {
      @Override
      public void run() {
        new Snowflake().setVisible(true);
      }
    }
  );
      prevOrder = order; 

  }
/**
     * for every parent line, 4 children lines will be generated
     * creating the next shape
     * @param parentLines 
     */
  public static void nexShape(ArrayList<ArrayList<Double>> parentLines) {
    int index = 0;
    for (int i = 0; i < parentLines.size(); i++) { //iterate through every parent line
      startOfX = parentLines.get(i).get(0); // x coordinate for parent line
      startOfY = parentLines.get(i).get(1); // y coordinate for parent line
      length = parentLines.get(i).get(2); // the lenght of the parent line
      angle = parentLines.get(i).get(3); // the angle of the parent line, 
      direction = parentLines.get(i).get(4); // which direction the line faces
      //flat line (1) 
      lines.add(new ArrayList<Double>());
      lines.get(index).add(0, startOfX);
      lines.get(index).add(1, startOfY);
      df.setMaximumFractionDigits(3);
      lines.get(index).add(2, Double.valueOf(df.format(length / 3.0))); //divide the length by 3
      lines.get(index).add(3, angle);

    if (direction == 0.0) { //if the parent line faces right
        lines.get(index).add(4, 0.0); //child line faces right
      } else {
        lines.get(index).add(4, 1.0);// face left
      }
      index++;
      //angled line (2)
      lines.add(new ArrayList<Double>());
      df.setMaximumFractionDigits(3);
      double endOfX1 =
        startOfX + Double.valueOf(df.format(Math.cos(Math.toRadians(angle)) * length / 3));
      double endOfY1 =
        startOfY + Double.valueOf(df.format(Math.sin(Math.toRadians(angle)) * length / 3));
      lines.get(index).add(0, endOfX1);
      lines.get(index).add(1, endOfY1);
      lines.get(index).add(2, length / 3.0);
      if (direction == 0.0) {
        lines.get(index).add(3, angle - 60.0);
        lines.get(index).add(4, 0.0);
      } else {
        lines.get(index).add(3, angle + 60.0);
        lines.get(index).add(4, 1.0);
      }
      index++;
      //angled line (3)
      lines.add(new ArrayList<Double>());
      df.setMaximumFractionDigits(3);
      double endOfX2 =
        endOfX1 + Double.valueOf(df.format(Math.cos(Math.toRadians(angle)) * length / 3));
      double endOfY2 =
        endOfY1 + Double.valueOf(df.format(Math.sin(Math.toRadians(angle)) * length / 3));
      lines.get(index).add(0, endOfX2);
      lines.get(index).add(1, endOfY2);
      df.setMaximumFractionDigits(3);
      lines.get(index).add(2, Double.valueOf(df.format(length / 3.0)));
      if (direction == 0.0) {
        lines.get(index).add(3, angle - 120.0);
        lines.get(index).add(4, 1.0);
      } else {
        lines.get(index).add(3, angle + 120.0);
        lines.get(index).add(4, 0.0);
      }
      index++;
      //flat line (4)
      lines.add(new ArrayList<Double>());
      lines.get(index).add(0, endOfX2);
      lines.get(index).add(1, endOfY2);
      df.setMaximumFractionDigits(3);
      lines.get(index).add(2, Double.valueOf(df.format(length / 3.0)));
      lines.get(index).add(3, angle);

      if (direction == 0.0) {
        lines.get(index).add(4, 0.0);
      } else {
        lines.get(index).add(4, 1.0);
      }
      index++;
    }
  }
      /**
     * checks if an arguments contains only digits
     * 
     * @param s arguments of the date
     */
   public static Boolean ContainsJustDigits(String s){
        String str = "";
        for(int i=0; i<s.length(); i++){
            if(i < s.length() -1){
            str = s.substring(i, i+1);
            }else{
             str =   s.substring(i);
            }
         if(str.matches("[^0-9]+")){
            return false;
         }
        }
        return true;
    }

}
