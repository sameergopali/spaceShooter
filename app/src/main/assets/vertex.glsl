
#version 300 es
in vec3 position;
in vec2 texCoord;
out vec2 color;

uniform mat4 modelMatrix;
void main(void){
 gl_Position = modelMatrix*vec4(position,1.0);
 color  = texCoord;
 }