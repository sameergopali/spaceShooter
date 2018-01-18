#version 300 es
#extension GL_OES_EGL_image_external_essl3 : require

precision mediump float;
in vec2 color;

out vec4 colorOut;
uniform samplerExternalOES sTexture;


void main(void){
colorOut = texture(sTexture,color);

   }