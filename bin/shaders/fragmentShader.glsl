#version 330 core

in vec2 passTexCoords;
in vec3 toLightVector;
in vec3 surfaceNormal;

out vec4 outColor;

uniform sampler2D textureSampler;
uniform vec3 lightColor;

void main(void)
{
	vec3 unitNormal=normalize(surfaceNormal);
	vec3 unitToLightVector=normalize(toLightVector);
	
	float diffuseIntensity = max(0.0, dot(unitNormal, unitToLightVector));
	
	vec3 diffuseColor = diffuseIntensity * lightColor;

	outColor = vec4(diffuseColor, 1.0);
}