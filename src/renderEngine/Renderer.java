package renderEngine;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;

import java.nio.IntBuffer;
import java.util.List;
import java.util.Map;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import entities.Camera;
import entities.Entity;
import entities.Light;
import models.RawModel;
import models.TexturedModel;
import shaders.ShaderProgram;
import shaders.StaticShader;
import textures.ModelTexture;
import toolbox.Maths;
import window.MainWindow;

public class Renderer {
	
	private static final float FIELD_OF_VIEW=70;
	private static final float NEAR_PLANE =0.1f;
	private static final float FAR_PLANE = 1000;
	
	
	private Matrix4f projectionMatrix;
	Camera camera;
	Light light;
	StaticShader shader;
	
	public Renderer(StaticShader shader)
	{
		//set shader program
		this.shader=shader;
		
		//Backface culling
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glCullFace(GL11.GL_BACK);
		
		//Load projection matrix
		projectionMatrix=Maths.createProjectionMatrix(MainWindow.HANDLE_ID, FIELD_OF_VIEW, NEAR_PLANE, FAR_PLANE);
	}
	
	public void prepare()
	{
		//enable depth test
		//enable color buffer
		
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		
		//clear screen
		GL11.glClearColor(0,0,0,1);
	}
	
	public void render(Map<TexturedModel, List<Entity>> entities)
	{
		for(TexturedModel model: entities.keySet())
		{
			prepareTexturedModel(model);
			List<Entity> batch = entities.get(model);
			
			for(Entity entity : batch)
			{
				prepareInstance(entity);
				
				//render
				GL11.glDrawElements(GL11.GL_TRIANGLES, entity.getModel().getRawModel().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
			}
			unbindTexturedModel();
		}
	}
	
	private void prepareTexturedModel(TexturedModel model)
	{
		int texID=model.getTexture().getTextureID();
		RawModel rawModel=model.getRawModel();
		
		
		GL30.glBindVertexArray(rawModel.getVaoID());
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		GL20.glEnableVertexAttribArray(2);
		
		//load material attributes
		ModelTexture textureMaterial =model.getTexture();
		shader.loadShineVariables(textureMaterial.getShineDamper(), textureMaterial.getReflectivity());
		
		//Activate uniform texture binding point
		GL13.glActiveTexture(0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texID);
	}
	
	private void unbindTexturedModel()
	{
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
		GL20.glDisableVertexAttribArray(2);
		GL30.glBindVertexArray(0);
	}
	
	private void prepareInstance(Entity entity)
	{
		Matrix4f transformMatrix = Maths.createTransformMatrix(entity.getPosition(), entity.getRotX(), entity.getRotY(), entity.getRotZ(), entity.getScale());
		
		shader.loadTransformMatrix(transformMatrix);
	}
}
