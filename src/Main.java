import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;
import org.lwjgl.util.vector.Vector3f;

import entities.Camera;
import entities.Entity;
import entities.Light;
import models.RawModel;
import models.TexturedModel;
import renderEngine.Loader;
import renderEngine.MasterRenderer;
import renderEngine.OBJLoader;
import renderEngine.Renderer;
import shaders.StaticShader;
import textures.ModelTexture;
import timer.GameTimer;
import window.MainWindow;

import java.nio.*;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

public class Main {
	
	Loader loader;

	public void run() {
		System.out.println("Hello LWJGL " + Version.getVersion() + "!");

		init();
		loop();

		// Free the window callbacks and destroy the window
		glfwFreeCallbacks(MainWindow.HANDLE_ID);
		glfwDestroyWindow(MainWindow.HANDLE_ID);

		// Terminate GLFW and free the error callback
		glfwTerminate();
		glfwSetErrorCallback(null).free();
	}

	private void init() {
		// Setup an error callback. The default implementation
		// will print the error message in System.err.
		GLFWErrorCallback.createPrint(System.err).set();

		// Initialize GLFW. Most GLFW functions will not work before doing this.
		if ( !glfwInit() )
			throw new IllegalStateException("Unable to initialize GLFW");

		// Configure GLFW
		glfwDefaultWindowHints(); // optional, the current window hints are already the default
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
		glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE); // the window will be resizable

		// Create the window
		MainWindow.HANDLE_ID = glfwCreateWindow(1280, 1024, "Touhou Souls", NULL, NULL);
		if ( MainWindow.HANDLE_ID == NULL )
			throw new RuntimeException("Failed to create the GLFW window");

		// Setup a key callback. It will be called every time a key is pressed, repeated or released.
		glfwSetKeyCallback(MainWindow.HANDLE_ID, (window, key, scancode, action, mods) -> {
			if ( key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE )
				glfwSetWindowShouldClose(window, true); // We will detect this in the rendering loop
		});

		// Get the thread stack and push a new frame
		try ( MemoryStack stack = stackPush() ) {
			IntBuffer pWidth = stack.mallocInt(1); // int*
			IntBuffer pHeight = stack.mallocInt(1); // int*

			// Get the window size passed to glfwCreateWindow
			glfwGetWindowSize(MainWindow.HANDLE_ID, pWidth, pHeight);

			// Get the resolution of the primary monitor
			GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

			// Center the window
			glfwSetWindowPos(
					MainWindow.HANDLE_ID,
				(vidmode.width() - pWidth.get(0)) / 2,
				(vidmode.height() - pHeight.get(0)) / 2
			);
		} // the stack frame is popped automatically

		// Make the OpenGL context current
		glfwMakeContextCurrent(MainWindow.HANDLE_ID);
		// Enable v-sync
		glfwSwapInterval(1);

		// Make the window visible
		glfwShowWindow(MainWindow.HANDLE_ID);
		
		
		// This line is critical for LWJGL's interoperation with GLFW's
		// OpenGL context, or any context that is managed externally.
		// LWJGL detects the context that is current in the current thread,
		// creates the GLCapabilities instance and makes the OpenGL
		// bindings available for use.		
		GL.createCapabilities();
		
		
		loader=new Loader();
	}

	private void loop() {
		
		Vector3f sunPosition =new Vector3f(0.0f, 20f, 2f);
		Vector3f sunLightColor = new Vector3f(1.0f, 1.0f, 1.0f);
		Light sun =new Light(sunPosition, sunLightColor);
		
		Camera camera = new Camera();
		 
		 RawModel model =OBJLoader.loadObjModel("bunny", loader);
		 ModelTexture texture=new ModelTexture(loader.loadTexture("white"));
		 TexturedModel texturedModel = new TexturedModel(model, texture);
		 ModelTexture textureMaterial=texturedModel.getTexture();
		 
		 //set specular attribute
		 textureMaterial.setShineDamper(10);
		 textureMaterial.setReflectivity(1);
		 
		 
		 Entity entity=new Entity(texturedModel, new Vector3f(0,0,-2.5f), 0,0,0,1);
		 
		 entity.increasePosition(0,-5,-10);
		 entity.increaseRotation(0, 180, 0);

		 MasterRenderer renderer=new MasterRenderer();
		 
		// Run the rendering loop until the user has attempted to close
		// the window or has pressed the ESCAPE key.
		while ( !glfwWindowShouldClose(MainWindow.HANDLE_ID) ) {
			GameTimer.updateDeltaTime();
			camera.move();
			renderer.processEntity(entity);

			entity.increaseRotation(0, 180f*GameTimer.getDeltaTime(), 0);
			

			
			renderer.render(sun, camera);
			glfwSwapBuffers(MainWindow.HANDLE_ID); // swap the color buffers

			
			// Poll for window events. The key callback above will only be
			// invoked during this call.
			glfwPollEvents();
			
			
		}
		
		loader.cleanUp();
		renderer.cleanUp();
		
		
	}

	public static void main(String[] args) {
		new Main().run();
	}

}