
attribute vec4 vPosition;
attribute vec2 a_TextureCoordinates;
uniform mat4 vMatrix;

varying vec2 v_TextureCoordinates;

void main(){
    v_TextureCoordinates = a_TextureCoordinates;
    gl_Position = vMatrix * vPosition;
}