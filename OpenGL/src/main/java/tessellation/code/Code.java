package tessellation.code;

import graphicslib3D.*;
import javax.swing.*;
import static com.jogamp.opengl.GL4.*;
import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.GLCanvas;

public class Code extends JFrame implements GLEventListener
{	private GLCanvas myCanvas;
	private GLSLUtils util = new GLSLUtils();
	private int rendering_program;
	private int vao[] = new int[1];
	
	private Point3D terLoc = new Point3D(0,0,0);
	private Point3D cameraLoc = new Point3D(0.5, -0.5, 2);
	
	private Matrix3D proj_matrix = new Matrix3D();
	private Matrix3D m_matrix = new Matrix3D();
	private Matrix3D v_matrix = new Matrix3D();
	private Matrix3D mvp_matrix = new Matrix3D();
	
	private float tessInner = 30.0f;
	private float tessOuter = 20.0f;

	public Code()
	{	setTitle("Chapter 12 - program 1");
		setSize(600, 600);
		myCanvas = new GLCanvas();
		myCanvas.addGLEventListener(this);
		getContentPane().add(myCanvas);
		this.setVisible(true);
	}

	public void display(GLAutoDrawable drawable)
	{	GL4 gl = (GL4) GLContext.getCurrentGL();
		gl.glUseProgram(rendering_program);
		int mvp_location = gl.glGetUniformLocation(rendering_program, "mvp");
		
		float aspect = (float) myCanvas.getWidth() / (float) myCanvas.getHeight();
		proj_matrix = perspective(50.0f, aspect, 0.1f, 1000.0f);
	
		m_matrix.setToIdentity();
		m_matrix.translate(terLoc.getX(),terLoc.getY(),terLoc.getZ());
		m_matrix.rotateX(35.0f);
		
		v_matrix.setToIdentity();
		v_matrix.translate(-cameraLoc.getX(),-cameraLoc.getY(),-cameraLoc.getZ());
		
		mvp_matrix.setToIdentity();
		mvp_matrix.concatenate(proj_matrix);
		mvp_matrix.concatenate(v_matrix);
		mvp_matrix.concatenate(m_matrix);
		
		gl.glUniformMatrix4fv(mvp_location, 1, false, mvp_matrix.getFloatValues(), 0);
	
		gl.glFrontFace(GL_CCW);

		gl.glPatchParameteri(GL_PATCH_VERTICES, 1);
		gl.glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);  // FILL or LINE
		gl.glDrawArrays(GL_PATCHES, 0, 1);
	}

	public void init(GLAutoDrawable drawable)
	{	GL4 gl = (GL4) GLContext.getCurrentGL();
		rendering_program = createShaderPrograms();
		
		gl.glGenVertexArrays(vao.length, vao, 0);
		gl.glBindVertexArray(vao[0]);
	}

//--------------------------------------------------------------------------------------------
	
	public static void main(String[] args) { new Code(); }
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {}
	public void dispose(GLAutoDrawable drawable) {}
	
//-----------------

	private int createShaderPrograms()
	{	GL4 gl = (GL4) GLContext.getCurrentGL();

		String vshaderSource[]  = util.readShaderSource("code/vert.shader");
		String tcshaderSource[] = util.readShaderSource("code/tessC.shader");
		String teshaderSource[] = util.readShaderSource("code/tessE.shader");
		String fshaderSource[]  = util.readShaderSource("code/frag.shader");

		int vShader  = gl.glCreateShader(GL_VERTEX_SHADER);
		int tcShader = gl.glCreateShader(GL_TESS_CONTROL_SHADER);
		int teShader = gl.glCreateShader(GL_TESS_EVALUATION_SHADER);
		int fShader  = gl.glCreateShader(GL_FRAGMENT_SHADER);

		gl.glShaderSource(vShader, vshaderSource.length, vshaderSource, null, 0);
		gl.glShaderSource(tcShader, tcshaderSource.length, tcshaderSource, null, 0);
		gl.glShaderSource(teShader, teshaderSource.length, teshaderSource, null, 0);
		gl.glShaderSource(fShader, fshaderSource.length, fshaderSource, null, 0);

		gl.glCompileShader(vShader);
		gl.glCompileShader(tcShader);
		gl.glCompileShader(teShader);
		gl.glCompileShader(fShader);

		int vtfprogram = gl.glCreateProgram();
		gl.glAttachShader(vtfprogram, vShader);
		gl.glAttachShader(vtfprogram, tcShader);
		gl.glAttachShader(vtfprogram, teShader);
		gl.glAttachShader(vtfprogram, fShader);
		gl.glLinkProgram(vtfprogram);
		return vtfprogram;
	}

	private Matrix3D perspective(float fovy, float aspect, float n, float f)
	{	float q = 1.0f / ((float) Math.tan(Math.toRadians(0.5f * fovy)));
		float A = q / aspect;
		float B = (n + f) / (n - f);
		float C = (2.0f * n * f) / (n - f);
		Matrix3D r = new Matrix3D();
		r.setElementAt(0,0,A);
		r.setElementAt(1,1,q);
		r.setElementAt(2,2,B);
		r.setElementAt(3,2,-1.0f);
		r.setElementAt(2,3,C);
		r.setElementAt(3,3,0.0f);
		return r;
	}
}