package renderEngine;

import java.io.FileInputStream;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

//Loading 3D Model into memory
public class Loader {
	
	private List<Integer> VAOs = new ArrayList<Integer>();
	private List<Integer> VBOs = new ArrayList<Integer>();
	
	
	
	public RawModel loadToVAO(float[] positions, int[] indices)
	{
		int vaoID=createVAO();
		VAOs.add(vaoID);
		
		bindIndicesBuffer(indices);
		
		storeDataInAttributeList(0, positions);
		unbindVAO();
		
		return new RawModel(vaoID, indices.length);
	}
	
	public int loadTexture(String fileName)
	{
		Texture texture =null;
		
		texture = TextureLoader.getTexture("PNG", new FileInputStream("res/"+fileName+".png"));
	}
	
	private int createVAO()
	{
		int vaoID = GL30.glGenVertexArrays();
		GL30.glBindVertexArray(vaoID);
		return vaoID;
	}
	
	private void storeDataInAttributeList(int attributeNumber, float[] data)
	{
		int vboID = GL15.glGenBuffers();
		VBOs.add(vboID);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID);
		FloatBuffer buffer=storeDataInFloatBuffer(data);
		
		//Store data into buffer that is on the binding point 'GL_ARRAY_BUFFER'
		//GL_STATIC_DRAW : modified once and used many times
		//GL_DYNAMIC_DRAW : modified repeadtedly and used many times
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
		
		//define an array of generic vertex attribute data
		//GL_ARRAY_BUFFER에 있는 데이터의 Slot number, 1 데이터별 size, type,stride, o
		GL20.glVertexAttribPointer(attributeNumber, 3, GL11.GL_FLOAT, false, 0,0);
		
		//unbind
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
	}
	
	private void unbindVAO()
	{
		GL30.glBindVertexArray(0);
	}
	
	//generate index buffer and store index data into this
	private void bindIndicesBuffer(int[] indices)
	{
		int vboID = GL15.glGenBuffers();
		VBOs.add(vboID);
		
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboID);
		
		IntBuffer buffer=storeDataInIntBuffer(indices);
		
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
	}
	
	private IntBuffer storeDataInIntBuffer(int[] data)
	{
		IntBuffer buffer= BufferUtils.createIntBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		
		return buffer;
	}
	
	
	private FloatBuffer storeDataInFloatBuffer(float[] data)
	{
		FloatBuffer buffer=BufferUtils.createFloatBuffer(data.length);
		buffer.put(data);
		//move buffer pointer into zero and set last position to last value idx
		buffer.flip();
		
		return buffer;
	}
	
	public void cleanUp()
	{
		for (int vao : VAOs)
		{
			GL30.glDeleteVertexArrays(vao);
		}
		
		for (int vbo : VBOs)
		{
			GL30.glDeleteBuffers(vbo);
		}
		
	}
}
