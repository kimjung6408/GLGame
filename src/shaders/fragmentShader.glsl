#version 330 core

in vec2 passTexCoords;
in vec3 toLightVector;
in vec3 surfaceNormal;
in vec3 toCameraVector;

out vec4 outColor;

uniform sampler2D textureSampler;
uniform vec3 lightColor;
uniform float shineDamper;
uniform float reflectivity;


void main(void)
{
	vec3 unitNormal=normalize(surfaceNormal);
	vec3 unitToLightVector=normalize(toLightVector);
	
	vec3 unitToCameraVector = normalize(toCameraVector);
	vec3 lightDirection = -unitToLightVector;
	vec3 reflectLightVector = reflect(lightDirection, unitNormal);
	
	
	//compute diffuse Reflection
	float diffuseIntensity = max(0.2, dot(unitNormal, unitToLightVector));
	
	vec3 diffuseColor = diffuseIntensity * lightColor;
	
	//compute specular Reflection
	float specularIntensity = pow( max(0.0, dot(unitToCameraVector, reflectLightVector)), shineDamper);
	vec3 specularColor = specularIntensity * vec3(1.0);

	outColor = vec4(diffuseColor, 1.0)*texture(textureSampler, passTexCoords) + vec4(specularColor, 1.0);
}
