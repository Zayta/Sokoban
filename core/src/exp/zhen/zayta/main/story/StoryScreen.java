package exp.zhen.zayta.main.story;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import exp.zhen.zayta.RPG;
import exp.zhen.zayta.main.UIAssetDescriptors;
import exp.zhen.zayta.main.ScreenBase;
import exp.zhen.zayta.main.menu.MenuScreen;


public class StoryScreen extends ScreenBase {


    private ButtonGroup<CheckBox> checkBoxGroup;
    private CheckBox easy;
    private CheckBox medium;
    private CheckBox hard;

    public StoryScreen(RPG game) {
        super(game);
    }


    @Override
    protected Actor createUi() {
        Skin uiSkin = assetManager.get(UIAssetDescriptors.UI_SKIN);
        TextButton backButton = new TextButton("BACK", uiSkin);
        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                back();
            }
        });
//
//        ChangeListener listener = new ChangeListener() {
//            @Override
//            public void changed(ChangeEvent event, Actor actor) {
//                difficultyChanged();
//            }
//        };
//
//        easy.addListener(listener);
//        medium.addListener(listener);
//        hard.addListener(listener);
//
//        // setup table
//        Table contentTable = new Table(uiSkin);
//        contentTable.defaults().pad(10);
//        contentTable.setBackground(UiRegionNames.PANEL);
//
//        contentTable.add(label).row();
//        contentTable.add(easy).row();
//        contentTable.add(medium).row();
//        contentTable.add(hard).row();
//        contentTable.add(backButton);
//
//        table.add(contentTable);
//        table.center();
//        table.setFillParent(true);
//        table.pack();
//
//        return table;
        return null;
    }

    private void back() {
        game.setScreen(new MenuScreen(game));
    }

    private void difficultyChanged() {
        CheckBox checked = checkBoxGroup.getChecked();

//        if (checked == easy) {
//            GameManager.INSTANCE.updateDifficulty(DifficultyLevel.EASY);
//        } else if (checked == medium) {
//            GameManager.INSTANCE.updateDifficulty(DifficultyLevel.MEDIUM);
//        } else if (checked == hard) {
//            GameManager.INSTANCE.updateDifficulty(DifficultyLevel.HARD);
//        }
    }

    private static CheckBox checkBox(String text, Skin skin) {
        CheckBox checkBox = new CheckBox(text, skin);
        checkBox.left().pad(8);
        checkBox.getLabelCell().pad(8);
        return checkBox;
    }
}
