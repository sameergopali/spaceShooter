#version 300 es
in vec2 color;

out vec4 colorOut;
uniform sampler2D textureSampler;
void main(void){
colorOut = texture(textureSampler,color);
 }