package shaders;

import org.lwjgl.util.vector.Matrix4f;

import entities.Camera;
import entities.Light;
import toolbox.Maths;

public class StaticShader extends ShaderProgram {
	
	private static final String VERTEX_FILE="src/shaders/vertexShader.glsl";
	private static final String FRAGMENT_FILE="src/shaders/fragmentShader.glsl";
	
	private int loc_transformMat;
	private int loc_projectionMat;
	private int loc_viewMat;
	private int loc_lightPosition;
	private int loc_lightColor;
	
	public StaticShader()
	{
		super(VERTEX_FILE, FRAGMENT_FILE);
	}
	
	@Override
	protected void bindAttributes() {
		// TODO Auto-generated method stub
		super.bindAttribute(0, "position");
		super.bindAttribute(1, "texCoords");
		super.bindAttribute(2, "normal");
		
	}
	
	@Override
	protected void getAllUniformLocations() {
		// TODO Auto-generated method stub
		loc_transformMat=super.getUniformLocation("transformationMatrix");
		loc_projectionMat=super.getUniformLocation("projectionMatrix");
		loc_viewMat=super.getUniformLocation("viewMatrix");
		loc_lightColor=super.getUniformLocation("lightColor");
		loc_lightPosition=super.getUniformLocation("lightPosition");
		
	}
	
	public void loadLight(Light light)
	{
		super.loadVector(loc_lightColor, light.getLightColor());
		super.loadVector(loc_lightPosition, light.getPosition());
	}
	
	public void loadViewMatrix(Matrix4f viewMatrix)
	{
		super.loadMatrix(loc_viewMat, viewMatrix);
	}
	
	public void loadViewMatrix(Camera camera)
	{
		Matrix4f viewMatrix=Maths.createViewMatrix(camera);
		super.loadMatrix(loc_viewMat, viewMatrix);
	}
	
	public void loadTransformMatrix(Matrix4f transformMatrix)
	{
		super.loadMatrix(loc_transformMat, transformMatrix);
	}
	
	public void loadProjectionMatrix(Matrix4f projectionMatrix)
	{
		super.loadMatrix(loc_projectionMat, projectionMatrix);
	}
}
