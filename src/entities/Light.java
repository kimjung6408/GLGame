package entities;

import org.lwjgl.util.vector.Vector3f;

public class Light {
	Vector3f position;
	Vector3f lightColor;
	
	public Light(Vector3f position, Vector3f lightColor) {
		this.position = position;
		this.lightColor = lightColor;
	}

	public Vector3f getPosition() {
		return position;
	}

	public void setPosition(Vector3f position) {
		this.position = position;
	}

	public Vector3f getLightColor() {
		return lightColor;
	}

	public void setLightColor(Vector3f lightColor) {
		this.lightColor = lightColor;
	}
	
	
}
