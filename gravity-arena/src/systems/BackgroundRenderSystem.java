package systems;

import com.artemis.systems.VoidEntitySystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class BackgroundRenderSystem extends VoidEntitySystem {

	private OrthographicCamera camera;
	private ShapeRenderer renderer;
	
	public BackgroundRenderSystem(OrthographicCamera camera){
		this.camera = camera;
	}
	
	@Override
	protected void processSystem() {
		Gdx.gl20.glLineWidth(2);
	    renderer.setProjectionMatrix(camera.combined);
	    renderer.begin(ShapeType.Line);
	    renderer.setColor(70/255f, 70/255f, 70/255f, 1);
	    for(int i = 0; i < 10; i++){
	    	renderer.line(i*40 + 61, 0, i*40 + 61, Gdx.graphics.getHeight());
	    }
	    for(int i = 0; i < 16; i++){
	    	renderer.line(59, i*40 + 29, 417, i*40 + 29);
	    }
	    renderer.setColor(140/255f, 140/255f, 140/255f, 1);
	    for(int i = 0; i < 10; i++){
	    	renderer.line(i*40 + 59, 0, i*40 + 59, Gdx.graphics.getHeight());
	    }
	    for(int i = 0; i < 16; i++){
	    	renderer.line(59, i*40 + 31, 417, i*40 + 31);
	    }
	    renderer.end();
	}
	
	@Override
	protected void initialize() {
		renderer = new ShapeRenderer();
	}

}
