#ifdef GL_ES
#define LOWP lowp
precision mediump float;
#else
#define LOWP
#endif

varying LOWP vec4 vColor;
varying vec2 vTexCoord;

//our texture samplers
uniform sampler2D u_texture; //diffuse map

void main() {
	vec4 DiffuseColor = texture2D(u_texture, vTexCoord);
        gl_FragColor = vColor * DiffuseColor;
   
        if(gl_FragColor.r > 0.9 && gl_FragColor.b > 0.9&& gl_FragColor.g > 0.9){

            gl_FragColor.r = 0.0;
            gl_FragColor.g = 0.0;
            gl_FragColor.b = 0.0;
        }
        else if(gl_FragColor.r ==0.0 && gl_FragColor.b ==0.0 && gl_FragColor.g ==0.0) {
            gl_FragColor.r = 1.0;
            gl_FragColor.g = 1.0;
            gl_FragColor.b = 1.0;
        }
}