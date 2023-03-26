#version 400 core

in vec3 position;
in vec2 texCoords;
in vec3 normal;

out vec2 passTexCoords;
out vec3 toLightVector;
out vec3 surfaceNormal;
out vec3 toCameraVector;

uniform mat4 transformationMatrix;
uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;
uniform vec3 lightPosition;


void main(void)
{
	vec3 worldPosition = (transformationMatrix * vec4(position, 1.0)).xyz;
	
	surfaceNormal = (transformationMatrix * vec4(normal, 0.0)).xyz;
	toLightVector = (lightPosition-worldPosition);
	
	//where to render the vertex on the screen
	gl_Position = projectionMatrix * viewMatrix * transformationMatrix * vec4(position, 1.0);
	
	//get CameraPosition
	vec3 cameraPosition=(inverse(viewMatrix)*vec4(0.0,0.0,0.0,1.0)).xyz;
	toCameraVector = cameraPosition-worldPosition;
	
	passTexCoords=texCoords;
}