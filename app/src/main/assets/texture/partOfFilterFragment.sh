precision mediump float;

uniform sampler2D u_TextureUnit;

varying vec2 v_TextureCoordinates;

varying vec4 aPos;

void main() {
        vec4 myColor = texture2D(u_TextureUnit, v_TextureCoordinates);

        if (aPos.x < 0.0 && aPos.y > 0.0) {
            gl_FragColor = vec4(myColor.r * 0.1, myColor.g * 0.6, myColor.b * 0.1, myColor.a);

        } else if (aPos.x > 0.0 && aPos.y > 0.0) {

            gl_FragColor = vec4(myColor.r * 0.6, myColor.g * 0.6, myColor.b * 0.1, myColor.a);

        } else if (aPos.x < 0.0 && aPos.y < 0.0) {

            gl_FragColor = vec4(myColor.r * 0.6, myColor.g * 0.6, myColor.b * 0.2, myColor.a);

        } else {
            gl_FragColor = vec4(myColor.r * 0.6, myColor.g * 0.4, myColor.b * 0.9, myColor.a);
        }

    }