package exp.zhen.zayta.main.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;


public class Files {
    public static FileHandle story = Gdx.files.internal("story/taria/story.txt");
    private static String puzzlePath = "puzzles/";
    public static FileHandle [] puzzles = new FileHandle[]
            {Gdx.files.internal(puzzlePath+"simply_sokoban_lvls.txt"),
             Gdx.files.internal(puzzlePath+"AC_Selected Sokoban lvls.txt")};
}
