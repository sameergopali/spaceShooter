
#version 300 es
in vec3 position;
in vec2 texCoord;
in vec3 normal;
out vec2 color;
out vec3 surfaceNormal;
out vec3 toLightVector;
out vec3 toCameraVector;

uniform mat4 modelMatrix;
uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;
uniform vec3 lightPosition;




void main(void){
 vec4 worldPosition = projectionMatrix* viewMatrix*modelMatrix*vec4(position,1.0);
 gl_Position =  worldPosition;
 color  = texCoord;

 surfaceNormal = (modelMatrix * vec4(normal,0.0)).xyz;
 toLightVector = lightPosition - worldPosition.xyz;
 toCameraVector = (inverse(viewMatrix)* vec4(0.0,0.0,0.0,1.0)).xyz -worldPosition.xyz;
 }