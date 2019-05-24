attribute vec4 vPosition;
attribute vec2 a_TextureCoordinates;

uniform mat4 aMatrix;

uniform mat4 bMatrix;

varying vec2 v_TextureCoordinates;

uniform int triangleType;

void main(){
    v_TextureCoordinates = a_TextureCoordinates;
    if(triangleType == 1){
      gl_Position = aMatrix * vPosition;
    }else if(triangleType ==2){
      gl_Position = bMatrix * vPosition;
    }else{
      gl_Position = bMatrix * vPosition;
    }
}