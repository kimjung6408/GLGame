#version 330 core

in vec3 color;

out vec4 outColor;

void main(void)
{

	outColor = vec4(color, 1);
}