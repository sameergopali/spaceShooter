
#version 300 es
in vec3 position;
in vec2 texCoord;

out vec2 color;





void main(void){
 vec4 worldPosition = vec4(position,1.0);
 gl_Position =  worldPosition;
  color = texCoord;
 }