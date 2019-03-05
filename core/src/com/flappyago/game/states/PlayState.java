package com.flappyago.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import com.flappyago.game.FlappyAgo;
import com.flappyago.game.sprites.Ago;
import com.flappyago.game.sprites.Tube;

import java.util.ArrayList;

public class PlayState extends State {
    // tubes
    private static final int SPACE_BETWEEN_TUBES = 125;  // space between tubes, not including tubes
    private static final int TUBE_COUNT = 4;
    private ArrayList<Tube> tubes;

    // Ago
    private Ago ago;

    // background
    private Texture background;

    // ground
    private static final int GROUND_Y_OFFSET = -50;
    private Texture ground;
    private Vector2 groundPosition1, groundPosition2;

    private Sound die;

    public PlayState(GameStateManager gameStateManager) {
        super(gameStateManager);

        ago = new Ago(50, 100);

        camera.setToOrtho(false, FlappyAgo.WIDTH / 2,
                FlappyAgo.HEIGHT / 2);

        background = new Texture("background.png");

        // ground
        ground = new Texture("ground.png");
        groundPosition1 = new Vector2(camera.position.x - camera.viewportWidth / 2,
                GROUND_Y_OFFSET);
        groundPosition2 = new Vector2((camera.position.x - camera.viewportWidth / 2)
                + ground.getWidth(), GROUND_Y_OFFSET);

        // tubes
        tubes = new ArrayList<Tube>();
        for (int i = 1; i <= TUBE_COUNT; i++) {
            tubes.add(new Tube(i * (SPACE_BETWEEN_TUBES + Tube.TUBE_WIDTH)));
        }

        die = Gdx.audio.newSound(Gdx.files.internal("dying.ogg"));
    }

    @Override
    protected void handleInput() {
        if (Gdx.input.justTouched() || Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            ago.jump();
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
        updateGround();
        ago.update(dt);

        camera.position.x = ago.getPosition().x + 80;  // camera follows Ago

        for (Tube tube: tubes) {
            if (camera.position.x - (camera.viewportWidth / 2) > tube.getPositionTopTube().x
                    + tube.getTopTube().getWidth()) {
                tube.reposition(tube.getPositionTopTube().x + ((Tube.TUBE_WIDTH
                        + SPACE_BETWEEN_TUBES) * TUBE_COUNT));
            }

            if (tube.collides(ago.getBounds())) {  // check collision with tubes
                die.play(0.5f);

                // gameStateManager.set(new PlayState(gameStateManager));

                gameStateManager.set(new GameOverState(gameStateManager));
            }
            /*
            if (tube.getPositionTopTube().x + (Tube.TUBE_WIDTH / 2) == ago.getPosition().x) {
                score += 1;
                System.out.println("lisasin skoooei" + score);
            }
            */
        }

        if (ago.getPosition().y <= ground.getHeight() + GROUND_Y_OFFSET) { // check collision with ground
            die.play(0.5f);

            gameStateManager.set(new GameOverState(gameStateManager));
        }

        camera.update();
    }

    private void updateGround() {
        if ((camera.position.x - (camera.viewportWidth / 2)) >
                (groundPosition1.x + ground.getWidth())) {
            groundPosition1.add(ground.getWidth() * 2, 0);
        }
        if ((camera.position.x - (camera.viewportWidth / 2)) >
                (groundPosition2.x + ground.getWidth())) {
            groundPosition2.add(ground.getWidth() * 2, 0);
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(camera.combined);
        // tells where in the game word we are,
        // so that only things, which camera is able to see, will be drawn
        sb.begin();

        sb.draw(background, camera.position.x - (camera.viewportWidth / 2), 0);

        sb.draw(ago.getTexture(), ago.getPosition().x, ago.getPosition().y);

        for(Tube tube : tubes) {
            sb.draw(tube.getTopTube(), tube.getPositionTopTube().x, tube.getPositionTopTube().y);
            sb.draw(tube.getBottomTube(), tube.getPositionBottomTube().x,
                    tube.getPositionBottomTube().y);
        }

        sb.draw(ground, groundPosition1.x, groundPosition1.y);
        sb.draw(ground, groundPosition2.x, groundPosition2.y);

        sb.end();
    }

    @Override
    public void dispose() {
        background.dispose();
        ago.dispose();
        ground.dispose();

        for (Tube tube : tubes) {
            tube.dispose();
        }
        System.out.println("Play state disposed");
    }
}
