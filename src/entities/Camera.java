package entities;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

import timer.GameTimer;
import window.MainWindow;


public class Camera {
	
	private Vector3f position =new Vector3f(0,0,0);
	private float pitch;
	private float yaw;
	private float roll;
	
	public Camera() {
		pitch=0;
		yaw=0;
		roll=0;
	}
	
	public void move()
	{
		if(GLFW.glfwGetKey(MainWindow.HANDLE_ID, GLFW.GLFW_KEY_W) == GLFW.GLFW_PRESS)
		{
			position.z-=10f*GameTimer.getDeltaTime();
			
		}
		
		if(GLFW.glfwGetKey(MainWindow.HANDLE_ID, GLFW.GLFW_KEY_S) == GLFW.GLFW_PRESS)
		{
			position.z+=10f*GameTimer.getDeltaTime();
		}
		
		if(GLFW.glfwGetKey(MainWindow.HANDLE_ID, GLFW.GLFW_KEY_A) == GLFW.GLFW_PRESS)
		{
			position.x-=10f*GameTimer.getDeltaTime();
		}
		
		if(GLFW.glfwGetKey(MainWindow.HANDLE_ID, GLFW.GLFW_KEY_D) == GLFW.GLFW_PRESS)
		{
			position.x+=10f*GameTimer.getDeltaTime();
		}
		
				
	}

	public Vector3f getPosition() {
		return position;
	}

	public float getPitch() {
		return pitch;
	}

	public float getYaw() {
		return yaw;
	}

	public float getRoll() {
		return roll;
	}
	
	
	

}
