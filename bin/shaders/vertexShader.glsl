#version 400 core

in vec3 position;
in vec2 texCoords;

out vec3 color;
out vec2 passTexCoords;

uniform mat4 transformationMatrix;
uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;


void main(void)
{
	//where to render the vertex on the screen
	gl_Position = projectionMatrix * viewMatrix * transformationMatrix * vec4(position, 1.0);
	
	
	color= vec3(position.x+0.5, 1.0, position.y+0.5);
	passTexCoords=texCoords;
}