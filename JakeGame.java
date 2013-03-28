import shapes.*;

import java.awt.Color;
import java.util.ArrayList;

public class JakeGame extends Game {

	ArrayList<Shape> shapes;
	Rectangle center1;
	Rectangle center2;
	Rectangle center3;
	Rectangle center4;
	Rectangle line1;    
	Rectangle line2;
	int level = 2;
	int score = 0;
	int priority[]; 
	String priorityNames[] = {" Square: ", " Blue: ", " Red: ", 
  							  " Circle: ", " Green: ", " Yellow: ", 
  							  " Rectangle: ", " Orange: ", " Magenta: ", 
  							  " Triangle: ", " Cyan: ", " Gray: ", 
							  " Black: "};
	int selectedIndex = 0;
	int givenIndex = 0;
	int inputPriority[];
	boolean pressed[] = {false, false, false, false};
	boolean hasWon;
	boolean hasStarted;
	
  @Override
  public void setup() {
	  setBackgroundColor(Color.WHITE);
	  center1 = new Rectangle();
	  center1.setCenter(new Point(WIDTH/4,HEIGHT/2-50));
	  center1.setColor(Color.PINK);
	  center1.setHeight(1);
	  center1.setWidth(1);
	  center2 = new Rectangle();
	  center2.setCenter(new Point(WIDTH/4,HEIGHT/2));
	  center2.setColor(Color.PINK);
	  center2.setHeight(1);
	  center2.setWidth(1);
	  center3 = new Rectangle();
	  center3.setCenter(new Point(WIDTH/4,HEIGHT/2+50));
	  center3.setColor(Color.PINK);
	  center3.setHeight(1);
	  center3.setWidth(1);
	  center4 = new Rectangle();
	  center4.setCenter(new Point(WIDTH/4,HEIGHT/2+100));
	  center4.setColor(Color.PINK);
	  center4.setHeight(1);
	  center4.setWidth(1);
	  line1 = new Rectangle();
	  line1.setCenter(new Point(0,10));
	  line1.setColor(Color.PINK);
	  line1.setHeight(1);
	  line1.setWidth(1);
	  line2 = new Rectangle();
	  line2.setCenter(new Point(0,0));
	  line2.setColor(Color.PINK);
	  line2.setHeight(1);
	  line2.setWidth(1);
	  //nextLevel();
  }
  
  public void nextLevel() {
	  ++level;
	  int numShapes = level/3;
	  inputPriority = new int[level];
	  priority = new int[level];
	  shapes = new ArrayList<Shape>();
	  for(int i = 0; i < 24; ++i){
		  Shape shape;
		  if((Math.random()*numShapes >= numShapes-1) && numShapes >= 3) shape = new Rectangle(new Point(Math.random()*WIDTH, Math.random()*HEIGHT), 10, 20);
		 //TODO: ADD TRIANGLE HERE
		  else if((Math.random()*numShapes >= numShapes-1) && numShapes >= 2) shape = new Circle(new Point(Math.random()*WIDTH, Math.random()*HEIGHT), 20);
		  else shape = new Rectangle(new Point(Math.random()*WIDTH, Math.random()*HEIGHT), 25,25);
		  if(i%(level-numShapes)==0)shape.setColor(Color.BLUE);
		  else if(i%(level-numShapes)==1)shape.setColor(Color.RED);
		  else if(i%(level-numShapes)==2)shape.setColor(Color.GREEN);
		  else if(i%(level-numShapes)==3)shape.setColor(Color.YELLOW);
		  else if(i%(level-numShapes)==4)shape.setColor(Color.ORANGE);
		  else if(i%(level-numShapes)==5)shape.setColor(Color.MAGENTA);
		  else if(i%(level-numShapes)==6)shape.setColor(Color.CYAN);  //PINK EVENTUALLY
		  else if(i%(level-numShapes)==7)shape.setColor(Color.GRAY); //also, darkGray and lightGray
		  else if(i%(level-numShapes)==8)shape.setColor(Color.BLACK);
		  shape.setSpeed(Math.random()*5+2);
		  shape.setDirection(new Direction(Math.random()*360));
		  shape.setSolid(false);
		  shape.setFilled(true);
		  shapes.add(shape);
	  }
	  ArrayList<Integer> nums = new ArrayList<Integer>();
	  for(int i = 0; i < level; ++i){
		  nums.add(i);
	  }
	  givenIndex = (int)Math.floor((Math.random() * level));
	  for(int i = 0; i < level; ++i){
		  priority[i] = nums.remove((int)Math.floor(Math.random()*nums.size()));
		  inputPriority[i] = 0;
	  }
	  inputPriority[givenIndex] = priority[givenIndex];
    // This code will be executed when the game begins. This is where you
    // can make the initial shapes, set the background color, etc.
  }

  @Override
  public void update() {
    // Put code here!
	  if(!hasStarted){
		  center4.say(" Each level, each shape and color is exclusively given a value 0 to (#shapes+#colors)");
		  center3.say(" The shapes fight when they collide and the shape with the lower shape+color value becomes an outline");
		  center2.say(" Think about what happens in a tie or when an already outlined shape hits a lower shape+color value");
		  center1.say(" Click anywhere to get started!");
	  }
	  else{
		  center1.say("");
		  center2.say("");
		  center3.say("");
		  center4.say("");
      System.out.println("Win!");
	  boolean hasWon = true;
	  for(int i = 0; i < level; ++i){
		  if(inputPriority[i] != priority[i]) {hasWon = false; System.out.println("Daww, lose"); }
	  }
	  
	  if(hasWon){ 
		  line1.say(" YOU WIN");
		  line2.say(" Click to play the next level!");
		  
		  //TODO: ADD clicking functionality
		  //nextLevel();
	  }
	  else {
		  line1.say(" Given Priority: " + priorityNames[givenIndex] + priority[givenIndex]);
		  String line2Response = " Priority Levels:";
		  for(int i = 0; i < level; ++i){
			  if(i != givenIndex) line2Response += (priorityNames[i] + inputPriority[i]);
		  }
		  line2.say(line2Response);
	  }
	  if(!hasWon){
	  if(Keyboard.keyIsPressed("Up")) pressed[0] = true;
	  else {
		  if(pressed[0]){
			  if(selectedIndex != givenIndex) ++inputPriority[selectedIndex];
			  if(inputPriority[selectedIndex] >= level) inputPriority[selectedIndex] = 0;
			  pressed[0] = false;
		  }
	  }
	  if(Keyboard.keyIsPressed("Down")) pressed[1] = true;
	  else {
		  if(pressed[1]){
			  if(selectedIndex != givenIndex) --inputPriority[selectedIndex];
			  if(inputPriority[selectedIndex] < 0) inputPriority[selectedIndex] = level-1;
			  pressed[1] = false;
		  }
	  }
	  if(Keyboard.keyIsPressed("Left")) pressed[2] = true;
	  else {
		  if(pressed[2]){
			  --selectedIndex;
			  if(selectedIndex == givenIndex) --selectedIndex;
			  if(selectedIndex < 0) selectedIndex = level-1;
			  if(selectedIndex == givenIndex) --selectedIndex;
			  pressed[2] = false;
		  }
	  }
	  if(Keyboard.keyIsPressed("Right")) pressed[3] = true;
	  else {
		  if(pressed[3]){
			  ++selectedIndex;
			  if(selectedIndex == givenIndex) ++selectedIndex;
			  if(selectedIndex >= level) selectedIndex = 0;
			  if(selectedIndex == givenIndex) ++selectedIndex;
			  pressed[3] = false;
		  }
	  }
	  }
	  checkForCollisions();
	  }
    System.out.println("hasWon = " + hasWon);
	  if((!hasStarted || hasWon) && Mouse.clickLocation() != null){
      System.out.println("Derp!");
		  hasStarted = true;
		  nextLevel();
	  }
    // This code will be executed once per frame. This is where you can make
    // shapes move around, say things, etc.
  }
  
  void checkForCollisions(){
	  for(Shape shapeA : shapes){
		  for(Shape shapeB : shapes){
			  if(shapeA != shapeB && shapeA.isTouching(shapeB)){
				  fight(shapeA, shapeB);
			  }
		  }
	  }
  }
  
  void fight(Shape shapeA, Shape shapeB){
	  int priorityA = getPriority(shapeA);
	  int priorityB = getPriority(shapeB);
	  if(priorityA > priorityB) shapeB.setFilled(false);
	  else if (priorityA < priorityB) shapeA.setFilled(false);
	  else{
		  shapeA.setFilled(false);
		  shapeB.setFilled(false);
	  }
	  
  }
  
  int getPriority(Shape shape){
	  int priorityLevel = 0;
	  if(shape instanceof Rectangle && ((Rectangle)shape).getHeight() == ((Rectangle)shape).getWidth()) priorityLevel += priority[0];
	  else if(shape instanceof Rectangle) priorityLevel += priority[6];
	  else if(shape instanceof Circle) priorityLevel += priority[3];
	//TODO: ADD else if(shape instanceof Triangle) priorityLevel += priority[9];
	  if(shape.getColor() == Color.BLUE) priorityLevel += priority[1];
	  else if(shape.getColor() == Color.RED) priorityLevel += priority[2];
	  else if(shape.getColor() == Color.GREEN) priorityLevel += priority[4];
	  else if(shape.getColor() == Color.YELLOW) priorityLevel += priority[5];
	  else if(shape.getColor() == Color.ORANGE) priorityLevel += priority[7];
	  else if(shape.getColor() == Color.MAGENTA) priorityLevel += priority[8];
	  else if(shape.getColor() == Color.CYAN) priorityLevel += priority[10];
	  else if(shape.getColor() == Color.GRAY) priorityLevel += priority[11];
	  else if(shape.getColor() == Color.BLACK) priorityLevel += priority[12];
	  return priorityLevel;
  }

  public static void main(String[] args) {
    new JakeGame();
  }
}
