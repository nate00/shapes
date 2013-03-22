all :
	javac shapes/*.java;
	javac *.java;

run: all
	java NateGame

test: all
	java shapes.Test

docs: all
	javadoc shapes \
		-d docs \
		-windowtitle "Shapes documentation" \
		-linksource \
