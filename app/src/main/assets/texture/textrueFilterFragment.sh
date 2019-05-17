precision mediump float;

uniform sampler2D u_TextureUnit;

varying vec2 v_TextureCoordinates;

void main(){
      vec4 myColor=texture2D(u_TextureUnit,v_TextureCoordinates);
      gl_FragColor=vec4(myColor.r * 0.6,myColor.g * 0.6,myColor.b * 0.1,myColor.a);
}