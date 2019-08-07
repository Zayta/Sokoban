package exp.zhen.zayta.main.story;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Logger;


public class StoryScreen implements Screen {

    private static final Logger log = new Logger(StoryScreen.class.getName(),Logger.DEBUG);
    //plays a single episode, but stores all episode data
    private FileHandle storyFile = Gdx.files.internal("story/story.txt");
    private TextureAtlas pictures;
    private String [] episodes = new String [30];
    private int currentEpisode;
//    StoryParser storyParser = new StoryParser(Gdx.files.internal("story/story.txt"));



    public String [] parse(String s){
        String delims = "[\"\t\n]+"; // use + to treat consecutive delims as one;
        // omit to treat consecutive delims separately
        String[] tokens = s.split(delims);

        return tokens;
    }




    @Override
    public void show() {

        String fullStory = storyFile.readString();
//        episodes = fullStory.replaceAll("label","]").split("]");
        episodes = fullStory.split("label");

        for(int i = 0; i<episodes.length;i++){
            //log.debug("Episode "+i+":\n"+episodes[i]+"\n\n\n");
        }

//        String string = "";
//        String [] tokens = parse();
//        for(int i = 0; i<tokens.length;i++){
//            string+=tokens[i]+",";
////            if(i%2==0)
////                string+="\n";
//        }
//        //log.debug("Story: \n"+string);
    }

    @Override
    public void render(float delta) {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
