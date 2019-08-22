package exp.zhen.zayta.main.story;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Logger;

import java.util.ArrayList;
import java.util.Arrays;

import exp.zhen.zayta.main.RPG;
import exp.zhen.zayta.main.ScreenBase;
import exp.zhen.zayta.main.UIAssetDescriptors;
import exp.zhen.zayta.main.UiRegionNames;
import exp.zhen.zayta.main.game.essence_lab.assets.WPAssetDescriptors;
import exp.zhen.zayta.main.game.essence_lab.assets.WPRegionNames;


public class StoryScreen extends ScreenBase {
    /*****
     * Note: all load functions are based on my file format and how I wrote the story
     *
     * ****/
    private static final Logger log = new Logger(StoryScreen.class.getName(),Logger.DEBUG);
    //plays a single episode, but stores all episode data
//    private FileHandle storyFile = Gdx.files.internal("story/records.txt");
    private TextureAtlas pictures;
    private String [] episodes = new String [30];
    private int currentLine = 0;
    private ArrayList<String> speaker = new ArrayList<String>();
    private ArrayList<String> dialogue = new ArrayList<String>();


    TextureAtlas textureAtlas;

    public StoryScreen(RPG game) {
        super(game);
        textureAtlas = assetManager.get(UIAssetDescriptors.LAB);
        loadFile(Gdx.files.internal("story/records.txt"));
    }
    private void loadFile(FileHandle storyFile){
        String fullStory = storyFile.readString();
//        episodes = fullStory.replaceAll("label","]").split("]");
        episodes = fullStory.split("label");


        for(int i = 0; i<episodes.length;i++){
            log.debug("Episode "+i+":\n"+episodes[i]+"\n\n\n");
        }

    }
    public void loadEpisode(int current_episode){

        log.debug("episodes are "+ Arrays.toString(episodes));
        dialogue.clear();
        if(current_episode>=episodes.length){ //account for array out of bounds
            current_episode = episodes.length-1;
        }
        String [] lines = episodes[current_episode].split("\n");

        log.debug("story lines are "+ Arrays.toString(lines));
        for(String l: lines) {
            log.debug("Line: " + l);


            String[] strings = l.split(":");
            log.debug("story full strings are " + Arrays.toString(strings));
            //speaker or dialogue
            if(strings.length%2==0){//make sure there is a speaker and a dialogue
                for (int i = 0; i < strings.length; i++) {//the first index is for chapter title
                    if (i % 2 == 0) {
                        speaker.add(strings[i]);
                    } else {
                        dialogue.add(strings[i]);
                    }
                }
            }
        }
        if(speaker.size()!=dialogue.size()){
            log.debug("Error: speaker cannot be matched with dialogue");
        }

    }


    @Override
    protected Actor createUi() {
        Table table = new Table();
        Skin skin = assetManager.get(UIAssetDescriptors.UI_SKIN);

        //background picture
        TextureRegion backgroundRegion = assetManager.get(UIAssetDescriptors.LAB).findRegion(WPRegionNames.BACKGROUND);
        table.setBackground(new TextureRegionDrawable(backgroundRegion));



        loadEpisode(1);//current Episode cannot be 0
        //speaker
//        final Label s = new Label(speaker.get(currentLine),skin);
//        table.add(s);
        final Image s = new Image();
        table.add(s);
        table.row();
        //dialogue
        final Label d = new Label(dialogue.get(currentLine),skin);
        table.add(d);
//        table.row();

        TextButton nextLineButton = new TextButton("->",skin);
        nextLineButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                goToNextLine(s,d);
            }
        });
        table.add(nextLineButton);
        table.center();
        table.setFillParent(true);
        table.pack();

        return table;
    }
    private void goToNextLine(Image s, Label d){
        currentLine++;
        if(currentLine<speaker.size()&&currentLine<dialogue.size()) {
            updateSpeaker(s);
            updateDialogue(d);
        }
        else{
            game.goToMain();
        }
    }
    private void updateSpeaker(Image image){
        if(speaker.get(currentLine).equals("ll")){
            image.setDrawable(new TextureRegionDrawable(new TextureRegion(textureAtlas.findRegion(WPRegionNames.LORALE))));
        }

        else{
            image.setDrawable(new TextureRegionDrawable(textureAtlas.findRegion(WPRegionNames.CIVILIAN)));
        }
    }

    private void updateDialogue(Label textCanvas){
        textCanvas.setText(dialogue.get(currentLine));
    }

}
