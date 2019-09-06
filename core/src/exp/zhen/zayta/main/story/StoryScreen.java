package exp.zhen.zayta.main.story;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Logger;

import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.plaf.synth.Region;

import exp.zhen.zayta.main.RPG;
import exp.zhen.zayta.main.ScreenBase;
import exp.zhen.zayta.main.assets.AssetDescriptors;
import exp.zhen.zayta.main.assets.RegionNames;
import exp.zhen.zayta.main.game.characters.Undead;
import exp.zhen.zayta.main.game.characters.nur.NUR;
import exp.zhen.zayta.main.game.config.SizeManager;


public class StoryScreen extends ScreenBase {
    /*****
     * Note: all load functions are based on my file format and how I wrote the story
     *
     * ****/
    private static final Logger log = new Logger(StoryScreen.class.getName(),Logger.DEBUG);
    //plays a single episode, but stores all episode data
//    private FileHandle storyFile = Gdx.files.internal("story/records.txt");

    private String [] episodes = new String [10];
    private Drawable [] scenes = new Drawable[10];

    private int currentLine = 0;
    private ArrayList<String> speaker = new ArrayList<String>();
    private ArrayList<String> dialogue = new ArrayList<String>();
    private Table table; private Drawable currentScene;

    private NUR nur;

    public StoryScreen(RPG game) {
        super(game);
        loadFile(Gdx.files.internal("story/records.txt"));
        loadScenes(assetManager.get(AssetDescriptors.LAB));
        table = new Table();
        nur = game.getNur();
        loadEpisode(1);
    }

    private void loadFile(FileHandle storyFile){
        String fullStory = storyFile.readString();
//        episodes = fullStory.replaceAll("label","]").split("]");
        episodes = fullStory.split("label");


        for(int i = 0; i<episodes.length;i++){
            log.debug("Episode "+i+":\n"+episodes[i]+"\n\n\n");
        }

    }

    private void loadScenes(TextureAtlas textureAtlas){
        //todo set background for story here
        for(int ep = 0; ep<scenes.length;ep++){
            if(ep<3)
                scenes[ep] = new TextureRegionDrawable(textureAtlas.findRegion(RegionNames.SQUARE_FLOOR));
            else
                scenes[ep] = new TextureRegionDrawable(textureAtlas.findRegion(RegionNames.SQUARE_FLOOR));
        }
    }


    public void loadEpisode(int current_episode){

        log.debug("episodes are "+ Arrays.toString(episodes));
        speaker.clear();
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
//            if(strings.length%2==0){//make sure there is a speaker and a dialogue
                for (int i = 0; i < strings.length; i++) {//the first index is for chapter title
                    if (i % 2 == 0) {
                        speaker.add(strings[i]);
                    } else {
                        dialogue.add(strings[i]);
                    }
                }
//            }
        }
        if(speaker.size()!=dialogue.size()){
            log.debug("Error: speaker cannot be matched with dialogue");
        }

        currentScene = scenes[current_episode];
    }


    @Override
    protected Actor createUi() {
        Skin skin = assetManager.get(AssetDescriptors.UI_SKIN);

        //reset table
        table.clearChildren();
        currentLine = 0;
        //background picture
//        TextureRegion backgroundRegion = assetManager.get(AssetDescriptors.LAB).findRegion(RegionNames.SQUARE_FLOOR);
        table.setBackground(currentScene);

//        loadEpisode(1,new TextureRegionDrawable(backgroundRegion));//current Episode cannot be 0
        //speaker
//        final Label s = new Label(speaker.get(currentLine),skin);
//        table.add(s);

        table.add(dialogueTable(skin));
        table.center();
        table.setFillParent(true);
        table.pack();

        return table;
    }

    private Table dialogueTable(Skin skin){
        Table table = new Table(skin);
        table.setBackground(RegionNames.PANEL);
        final Image s = new Image();
        float scalar = 100;
        table.add(s).width(SizeManager.maxObjWidth*scalar).height(SizeManager.maxObjHeight*scalar);
        table.row();
        //dialogue
        final Label d = new Label(dialogue.get(currentLine),skin);
        d.setWrap(true);
        d.setWidth(10f);
        d.setAlignment(Align.center);
        table.add(d).width(0.8f*SizeManager.WIDTH);
        table.row();

        TextButton nextLineButton = new TextButton("->",skin);
        nextLineButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                goToNextLine(s,d);
            }
        });
        table.add(nextLineButton);
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
            image.setDrawable(nur.nighters.get(Undead.Lorale).getAvatar());
        }
        else if(speaker.get(currentLine).equals("lt")){
            image.setDrawable(nur.nighters.get(Undead.Letra).getAvatar());
        }
        else{
            log.debug("Image is "+image);
            log.debug("Nighter is "+nur.nighters.get(Undead.Anonymous));
            image.setDrawable(nur.nighters.get(Undead.Anonymous).getAvatar());
        }
    }

    private void updateDialogue(Label textCanvas){
        textCanvas.setText(dialogue.get(currentLine));
    }

}
