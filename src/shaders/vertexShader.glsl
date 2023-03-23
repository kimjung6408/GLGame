#version 330 core

in vec3 position;
in vec2 texCoords;

out vec3 color;
out vec2 passTexCoords;

uniform mat4 transformationMatrix;


void main(void)
{
	//where to render the vertex on the screen
	gl_Position = vec4(position, 1.0);
	
	
	color= vec3(position.x+0.5, 1.0, position.y+0.5);
	passTexCoords=texCoords;
}