#version 300 es
in vec2 color;
in vec3 surfaceNormal;
in vec3 toLightVector;
in vec3 toCameraVector;
in float visibility;
out vec4 colorOut;
uniform sampler2D textureSampler;
uniform vec3 lightColor;
uniform float shineDamper;
uniform float reflectivity;
uniform vec3 skyColor;
void main(void){

vec3 unitNormal = normalize(surfaceNormal);
vec3 unitLightVetor  = normalize(toLightVector);
vec3 unitCameraVector = normalize(toCameraVector);

float nDot1 = dot(unitNormal,unitLightVetor);
float brightness = max(nDot1,0.2);
vec3 diffuse = brightness * lightColor;
vec3 lightDirection = - unitLightVetor;
vec3 reflectedLightDirection = reflect(lightDirection, unitNormal);

float specularFactor = dot(reflectedLightDirection, unitCameraVector);
specularFactor = max(specularFactor,0.0);
float dampedFactor = pow(specularFactor,shineDamper);
vec3 finalSpecular  = dampedFactor * reflectivity*lightColor;

colorOut = vec4(diffuse,1.0)*texture(textureSampler,color) + vec4(finalSpecular,1.0);
colorOut = mix(vec4(skyColor,1.0),colorOut,visibility);


 }