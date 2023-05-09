JFLAGS = -cp lib/json-20230227.jar:.
JC = javac

.SUFFIXES: .java .class

.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = src/AudioRecorder.java src/ChatGPT.java src/FileWriter.java src/MainUI.java src/Whisper.java src/WhisperAPI.java src/SidebarUI.java src/History.java

default: classes

classes: $(CLASSES:.java=.class)

clean:
	$(RM) src/*.class
	
run:
	java -cp lib/json-20230227.jar:. src/MainUI