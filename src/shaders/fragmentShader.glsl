#version 330 core

in vec3 color;
in vec2 passTexCoords;

out vec4 outColor;

uniform sampler2D textureSampler;

void main(void)
{

	outColor = texture(textureSampler, passTexCoords);
}