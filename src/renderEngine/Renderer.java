package renderEngine;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;

import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;

import entities.Camera;
import entities.Entity;
import models.RawModel;
import models.TexturedModel;
import shaders.StaticShader;
import toolbox.Maths;

public class Renderer {
	
	private static final float FIELD_OF_VIEW=70;
	private static final float NEAR_PLANE =0.1f;
	private static final float FAR_PLANE = 1000;
	
	
	private Matrix4f projectionMatrix;
	Camera camera;
	
	public Renderer(long renderWindowID)
	{
		projectionMatrix=Maths.createProjectionMatrix(renderWindowID, FIELD_OF_VIEW, NEAR_PLANE, FAR_PLANE);
		camera = new Camera();
	}
	
	public void prepare()
	{
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		GL11.glClearColor(0,0,0,1);
	}
	
	public void render(Entity entity, StaticShader shader)
	{
		shader.start();
		
		RawModel model=entity.getModel().getRawModel();
		int texID=entity.getModel().getTexture().getID();
		
		camera.move();
		
		GL30.glBindVertexArray(model.getVaoID());
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		
		
		Matrix4f transformMatrix = Maths.createTransformMatrix(entity.getPosition(), entity.getRotX(), entity.getRotY(), entity.getRotZ(), entity.getScale());
		
		shader.loadTransformMatrix(transformMatrix);
		shader.loadProjectionMatrix(projectionMatrix);
		shader.loadViewMatrix(camera);
		
		
		GL13.glActiveTexture(0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texID);
		
		GL11.glDrawElements(GL11.GL_TRIANGLES, model.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
		
		//GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, model.getVertexCount());
		
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
		GL30.glBindVertexArray(0);
		
		shader.stop();
	}
}
