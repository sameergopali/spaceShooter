
#version 300 es
in vec3 position;
in vec2 texCoord;
out vec2 color;

uniform mat4 modelMatrix;
uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;
void main(void){
 gl_Position =  projectionMatrix* viewMatrix*modelMatrix*vec4(position,1.0);
 color  = texCoord;
 }