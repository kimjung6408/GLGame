package shaders;

import org.lwjgl.util.vector.Matrix4f;

public class StaticShader extends ShaderProgram {
	
	private static final String VERTEX_FILE="src/shaders/vertexShader.glsl";
	private static final String FRAGMENT_FILE="src/shaders/fragmentShader.glsl";
	
	private int loc_transformMat;
	
	public StaticShader()
	{
		super(VERTEX_FILE, FRAGMENT_FILE);
	}
	
	@Override
	protected void bindAttributes() {
		// TODO Auto-generated method stub
		super.bindAttribute(0, "position");
		super.bindAttribute(1, "texCoords");
		
	}
	
	@Override
	protected void getAllUniformLocations() {
		// TODO Auto-generated method stub
		loc_transformMat=super.getUniformLocation("transformationMatrix");
		
	}
	
	public void loadTrasformMatrix(Matrix4f transformMatrix)
	{
		super.loadMatrix(loc_transformMat, transformMatrix);
	}
}
