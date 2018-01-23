#version 300 es
in vec2 color;
in vec3 surfaceNormal;
in vec3 toLightVector;
in float visibility;
in vec3 toCameraVector;

out vec4 colorOut;

uniform sampler2D backgroundSampler;
uniform sampler2D rTexture;
uniform sampler2D gTexture;
uniform sampler2D bTexture;
uniform sampler2D blendMap;

uniform vec3 lightColor;
uniform float shineDamper;
uniform float reflectivity;
uniform vec3 skyColor;


void main(void){
   vec4 blendMapColor = texture(blendMap,color);

   float backTextureAmount = 1.0 - (blendMapColor.r+ blendMapColor.g +blendMapColor.b);
   vec2 tileCoord = color* 40.0;
   vec4 backTextureColor = texture(backgroundSampler,tileCoord ) *backTextureAmount;


   vec4 rTextureColor = texture(rTexture,tileCoord) * blendMapColor.r;
   vec4 gTextureColor = texture(gTexture,tileCoord)*blendMapColor.g;
   vec4 bTextureColor = texture(bTexture,tileCoord)*blendMapColor.b;


   vec4 totalColor =backTextureColor+bTextureColor+rTextureColor+gTextureColor;

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

colorOut = vec4(diffuse,1.0)*totalColor + vec4(finalSpecular,1.0);
colorOut = mix(vec4(skyColor,1.0),colorOut,visibility);
 }