all :
	javac shapes/*.java -d bin;
	javac *.java -d bin;

run: all
	java -cp bin MyGame;

maze: all
	java -cp bin MazeGame;

test: all
	java -cp bin shapes.Test;

docs: all
	javadoc shapes \
		-d docs \
		-windowtitle "Shapes documentation" \
		-linksource \
