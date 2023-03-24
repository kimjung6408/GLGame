package timer;

import org.lwjgl.glfw.GLFW;

public class GameTimer {
	private static double lastTime=0;
	private static float deltaTime=0;
	
	public static void updateDeltaTime()
	{
		double currentTime=GLFW.glfwGetTime();
		deltaTime = (float)(currentTime-lastTime);
		
		lastTime=currentTime;
	}
	
	public static float getDeltaTime()
	{
		return deltaTime;
	}
}
