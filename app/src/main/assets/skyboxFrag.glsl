#version 300 es
in vec3 textureCoords;
out vec4 out_color;

const float lowerLimit =0.0;
const float upperLimit = 30.0;

uniform vec3 fogColor;

uniform samplerCube cubeMap;
uniform samplerCube cubeMap1;

uniform float blendFactor;

void main(void){
    vec4 texture1 = texture(cubeMap,textureCoords);
    vec4 texture2 = texture(cubeMap1,textureCoords);

    vec4  finalcolor = mix(texture1,texture2,blendFactor);
    float  factor = (textureCoords.y -lowerLimit)/(upperLimit- lowerLimit);
    factor  = clamp(factor,0.0,1.0);
    out_color = mix(vec4(fogColor,1.0),finalcolor,factor);
}